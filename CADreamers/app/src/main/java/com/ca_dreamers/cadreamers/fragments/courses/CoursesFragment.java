package com.ca_dreamers.cadreamers.fragments.courses;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.activity.LoginActivity;
import com.ca_dreamers.cadreamers.activity.MainActivity;
import com.ca_dreamers.cadreamers.activity.SplashActivity;
import com.ca_dreamers.cadreamers.adapter.combo_package.AdapterComboPackage;
import com.ca_dreamers.cadreamers.adapter.courses.AdapterCourseList;
import com.ca_dreamers.cadreamers.adapter.courses.AdapterCoursesBanner;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.combo_package.ModelCombo;
import com.ca_dreamers.cadreamers.models.courses.ModelCourse;
import com.ca_dreamers.cadreamers.models.courses.banners.ModelCoursesBanner;
import com.ca_dreamers.cadreamers.models.logged_out.ModelLogout;
import com.ca_dreamers.cadreamers.models.user_token.ModelToken;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.ca_dreamers.cadreamers.utils.AutoScrollViewPager;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoursesFragment extends Fragment {
    private static final int REQUEST_READ_PHONE_STATE = 321;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private Context context;
    private String strUserId;
    private String strToken;
    private SharedPrefManager sharedPrefManager;
    private LayoutInflater layoutInflater;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvCourseList)
    protected RecyclerView rvCourseList;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvComboHeader)
    protected TextView tvComboHeader;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvComboPackage)
    protected RecyclerView rvComboPackage;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sliderCourses)
    protected AutoScrollViewPager sliderCourses;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tabsCourses)
    protected TabLayout tabsCourses;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnBrowsCourseBrows)
    protected AppCompatButton btnBrowsCourseBrows;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_course, container, false);
        layoutInflater = getLayoutInflater();
        ButterKnife.bind(this, root);
        context = (Activity)root.getContext();
        sharedPrefManager = new SharedPrefManager(context);
        strUserId = sharedPrefManager.getUserId();
        btnBrowsCourseBrows.setEnabled(false);
        requestPermissions();
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        requireActivity().setResult(REQUEST_READ_PHONE_STATE);
                    }
                });

        callBannersApi();
        callCourseApi();
        rvCourseList.setLayoutManager(new LinearLayoutManager(getContext()));
        tvComboHeader.setVisibility(View.GONE);
        rvComboPackage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        callComboApi();

        return root;
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnMyCourseBrows)
    public void btnMyCourseBrowsClicked(){
        Navigation.findNavController(requireView()).navigate(R.id.nav_my_courses);
    }
    private void callBannersApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        Call<ModelCoursesBanner> bannersCall = api.getCoursesBanners();
        bannersCall.enqueue(new Callback<ModelCoursesBanner>() {
            @Override
            public void onResponse(@NonNull Call<ModelCoursesBanner> call, @NonNull Response<ModelCoursesBanner> response) {
                ModelCoursesBanner modelCoursesBanner = response.body();
                assert modelCoursesBanner != null;
                AdapterCoursesBanner adapterCoursesBanner =
                        new AdapterCoursesBanner(modelCoursesBanner.getData(), getContext(), layoutInflater);
                sliderCourses.setAdapter(adapterCoursesBanner);

                tabsCourses.setupWithViewPager(sliderCourses);
                sliderCourses.startAutoScroll();
                sliderCourses.setInterval(Constant.SLIDER_SPEED);
                sliderCourses.setCycle(true);
            }

            @Override
            public void onFailure(@NotNull Call<ModelCoursesBanner> call, @NotNull Throwable t) {

            }
        });

    }


    private void callCourseApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", sharedPrefManager.getUserId());

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelCourse> userCall = api.getCourse(gsonObject);

            userCall.enqueue(new Callback<ModelCourse>() {
                @Override
                public void onResponse(@NonNull Call<ModelCourse> call, @NonNull Response<ModelCourse> response) {
                    ModelCourse modelCourse = response.body();
                    assert modelCourse != null;
                    AdapterCourseList adapterCourseList = new AdapterCourseList(modelCourse.getData());
                        rvCourseList.setAdapter(adapterCourseList);
                }

                @Override
                public void onFailure(@NonNull Call<ModelCourse> call, @NonNull Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void callComboApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("type", "package");

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelCombo> call = api.getComboPackage(gsonObject);

            call.enqueue(new Callback<ModelCombo>() {
                @Override
                public void onResponse(@NotNull Call<ModelCombo> call, @NotNull Response<ModelCombo> response) {
                    ModelCombo modelFetchCart = response.body();

                    assert modelFetchCart != null;
                    AdapterComboPackage adapterComboPackage = new AdapterComboPackage(modelFetchCart.getData());
                    rvComboPackage.setAdapter(adapterComboPackage);
                    tvComboHeader.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(@NotNull Call<ModelCombo> call, @NotNull Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void callTokenApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", strUserId);
            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelToken> call = api.getTokenApi(gsonObject);
            call.enqueue(new Callback<ModelToken>() {
                @Override
                public void onResponse(@NonNull Call<ModelToken> call, @NonNull Response<ModelToken> response) {
                    ModelToken modelToken = response.body();
                    assert modelToken != null;
                    strToken = modelToken.getData().getToken();
                    if (!strToken.equals(getUserDeviceId(context))){
                        callLogoutApi();
                        Toast.makeText(getActivity(), "Some on logged in with your credentials.", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<ModelToken> call, @NonNull Throwable t) {
                    Log.d("TSF_APPS", "TOKEN Error: "+t);

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void callLogoutApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", strUserId);
            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelLogout> call = api.getLogout(gsonObject);
            call.enqueue(new Callback<ModelLogout>() {
                @Override
                public void onResponse(@NonNull Call<ModelLogout> call, @NonNull Response<ModelLogout> response) {
                    sharedPrefManager.clearUserData();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }

                @Override
                public void onFailure(@NonNull Call<ModelLogout> call, @NonNull Throwable t) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void requestPermissions() {

        Dexter.withContext(getActivity())

                .withPermissions(Manifest.permission.READ_PHONE_STATE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            callTokenApi();
                        }
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).withErrorListener(error -> Toast.makeText(context, "Error Occurred! "+error.toString(), Toast.LENGTH_SHORT).show())
                .onSameThread().check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs phone state permission to protect your account from un authorize login. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSomeActivityForResult();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            Toast.makeText(context, "You must have to enable the phone state permission to protect your account from un authorize login.", Toast.LENGTH_SHORT).show();
            showSettingsDialog();
            dialog.cancel();
        });
        builder.show();
    }
    public void openSomeActivityForResult() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
       someActivityResultLauncher.launch(intent);
    }
    @SuppressLint("HardwareIds")
    public static String getUserDeviceId(Context context) {
        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
        }

        return deviceId;
    }

}