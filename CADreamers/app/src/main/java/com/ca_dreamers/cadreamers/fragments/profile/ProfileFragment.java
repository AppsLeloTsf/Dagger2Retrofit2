package com.ca_dreamers.cadreamers.fragments.profile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.activity.MainActivity;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.profile.ModelUserInfo;
import com.ca_dreamers.cadreamers.models.profile.profile_edit.ModelProfileEdit;
import com.ca_dreamers.cadreamers.models.profile.user_image.ModelProfileImage;
import com.ca_dreamers.cadreamers.models.profile.user_image.update_image.ModelUpdateImage;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.ca_dreamers.cadreamers.utils.Constant.TAG;

public class ProfileFragment extends Fragment {

    private SharedPrefManager sharedPrefManager;

    private Context context;
    private String strUserId;
    private String strUserName;
    private String strEmail;
    private String strPhone;
    private String strState;
    private String strPinCode;
    private String strAddress;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ivProfilePic)
    protected ImageView ivProfilePic;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ivProfilePicEdit)
    protected ImageView ivProfilePicEdit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.llProfileShow)
    protected LinearLayout llProfileShow;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ivProfileEdit)
    protected ImageView ivProfileEdit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvUserNameProfile)
    protected TextView tvUserNameProfile;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvEmailProfile)
    protected TextView tvEmailProfile;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvStateProfile)
    protected TextView tvStateProfile;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvPinProfile)
    protected TextView tvPinProfile;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvAddressProfile)
    protected TextView tvAddressProfile;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvPhoneProfile)
    protected TextView tvPhoneProfile;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.llProfileEdit)
    protected LinearLayout llProfileEdit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etUserNameProfileEdit)
    protected EditText etUserNameProfileEdit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvEmailProfileEdit)
    protected TextView tvEmailProfileEdit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etStateProfileEdit)
    protected EditText etStateProfileEdit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etPinProfileEdit)
    protected EditText etPinProfileEdit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etAddressProfileEdit)
    protected EditText etAddressProfileEdit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvPhoneProfileEdit)
    protected TextView tvPhoneProfileEdit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnSubmitProfileEdit)
    protected AppCompatButton btnSubmitProfileEdit;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        context = (Activity)view.getContext();
        sharedPrefManager = new SharedPrefManager(context);
        strUserId = sharedPrefManager.getUserId();
        Log.d(TAG, "User ID: "+ strUserId);
        llProfileEdit.setVisibility(View.GONE);
        llProfileShow.setVisibility(View.VISIBLE);
        callProfileImageApi(context);
        callProfileApi();
        return view;
    }


    private void callProfileImageApi(Context context){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", strUserId);
            paramObject.put("encryptfile", "abc");
            paramObject.put("type", "");

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelProfileImage> call = api.getUserImage(gsonObject);
            call.enqueue(new Callback<ModelProfileImage>() {
                @Override
                public void onResponse(@NonNull Call<ModelProfileImage> call, @NonNull Response<ModelProfileImage> response) {
                    ModelProfileImage modelProfileImage = response.body();
                    assert modelProfileImage != null;
                    String strUserImage = modelProfileImage.getData().getFileUrl();
                        Glide.with(context).load(strUserImage).into(ivProfilePic);
                        Glide.with(context).load(strUserImage).into(ivProfilePicEdit);

                }

                @Override
                public void onFailure(@NonNull Call<ModelProfileImage> call, @NonNull Throwable t) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void callProfileApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", sharedPrefManager.getUserId());

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelUserInfo> call = api.getUserInfo(gsonObject);
            call.enqueue(new Callback<ModelUserInfo>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<ModelUserInfo> call, @NonNull Response<ModelUserInfo> response) {
                    ModelUserInfo modelUserInfo = response.body();
                    assert modelUserInfo != null;
                    strUserName = modelUserInfo.getData().getUsername();
                    strEmail = modelUserInfo.getData().getEmail();
                    strPhone = modelUserInfo.getData().getPhone();
                    strState = modelUserInfo.getData().getState();
                    strPinCode = modelUserInfo.getData().getPincode();
                    strAddress = modelUserInfo.getData().getAddress();
                    tvUserNameProfile.setText(strUserName);
                    tvEmailProfile.setText(strEmail);
                    tvStateProfile.setText(strState);
                    tvPinProfile.setText(strPinCode);
                    tvAddressProfile.setText(strAddress);
                    tvPhoneProfile.setText(strPhone);

                    etUserNameProfileEdit.setText(strUserName);
                    tvEmailProfileEdit.setText(strEmail);
                    etStateProfileEdit.setText(strState);
                    etPinProfileEdit.setText(strPinCode);
                    etAddressProfileEdit.setText(strAddress);
                    tvPhoneProfileEdit.setText(strPhone);



                }

                @Override
                public void onFailure(@NonNull Call<ModelUserInfo> call, @NonNull Throwable t) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void callProfileEditApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();

            String strUserNameEdited = etUserNameProfileEdit.getText().toString().trim();
            String strStateEdited = etStateProfileEdit.getText().toString().trim();
            String strPinCodeEdited = etPinProfileEdit.getText().toString().trim();
            String strAddressEdited = etAddressProfileEdit.getText().toString().trim();
            paramObject.put("username", strUserNameEdited);
            paramObject.put("email", strEmail);
            paramObject.put("phone", strPhone);
            paramObject.put("state", strStateEdited);
            paramObject.put("userid", strUserId);
            paramObject.put("pincode", strPinCodeEdited);
            paramObject.put("address", strAddressEdited);


            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelProfileEdit> call = api.getUserInfoEdit(gsonObject);
            call.enqueue(new Callback<ModelProfileEdit>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<ModelProfileEdit> call, @NonNull Response<ModelProfileEdit> response) {
                    ModelProfileEdit modelUserInfo = response.body();
                    assert modelUserInfo != null;
                    if (modelUserInfo.getStatus().getStatuscode().equals("200")) {
                        llProfileEdit.setVisibility(View.GONE);
                        llProfileShow.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ModelProfileEdit> call, @NonNull Throwable t) {

                    Log.d("TSF_PRO", "Failure: "+t.getMessage());
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnSubmitProfileEdit)
    public void btnSubmitProfileEditClicked(){
        callProfileEditApi();
        callImageUpdateApi();


    }
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.ivProfileEdit)
    public void ivProfileEditClicked(){
        llProfileEdit.setVisibility(View.VISIBLE);
        llProfileShow.setVisibility(View.GONE);


    }
    ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    assert result.getData() != null;
                    Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                    ivProfilePicEdit.setImageBitmap(photo);


                }
            });
    ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    assert result.getData() != null;
                    Uri selectedImageURI = result.getData().getData();
                    Glide.with(context).load(selectedImageURI).centerCrop()
                            .into(ivProfilePicEdit);
                }
            });
    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo!");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo"))
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraActivityResultLauncher.launch(cameraIntent);
            }
            else if (options[item].equals("Choose from Gallery"))
            {
                Intent intent = new   Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryActivityResultLauncher.launch(intent);

            }
            else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.ivProfilePicEdit)
    public void ivProfilePicClicked(){
        Dexter.withContext(getActivity())
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            selectImage();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {

                    Toast.makeText(context, "Opened Setting", Toast.LENGTH_SHORT).show();

                }
            });

    private void openSomeActivityForResult() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        someActivityResultLauncher.launch(intent);
    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSomeActivityForResult();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }


    private void callImageUpdateApi(){
        String strProPic;
        BitmapDrawable drawable = (BitmapDrawable) ivProfilePicEdit.getDrawable();
        if (ivProfilePicEdit.getDrawable() != null){
        Bitmap bitmap = drawable.getBitmap();
            strProPic = BitMapToString(bitmap);
        }
        else {
            strProPic = "abc";
        }
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;

        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", strUserId);
            paramObject.put("encryptfile", strProPic);
            paramObject.put("type", "update");

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelUpdateImage> call = api.updateImage(gsonObject);
            call.enqueue(new Callback<ModelUpdateImage>() {
                @Override
                public void onResponse(@NonNull Call<ModelUpdateImage> call, @NonNull Response<ModelUpdateImage> response) {
                    ModelUpdateImage modelProfileImage = response.body();

                    assert modelProfileImage != null;
                    if (modelProfileImage.getStatus().getStatuscode().equals("200")){
                        llProfileEdit.setVisibility(View.GONE);
                        llProfileShow.setVisibility(View.VISIBLE);
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).callProfileImageApi();
                        }
                        callProfileImageApi(context);
                    }
                    Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(@NonNull Call<ModelUpdateImage> call, @NonNull Throwable t) {

                    Log.d(TAG, "Update Failure: "+t.getMessage());
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,10, baos);
        byte [] b=baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

}