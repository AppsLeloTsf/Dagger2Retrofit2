package com.ca_dreamers.cadreamers.fragments.courses;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.combo_package.AdapterComboPackage;
import com.ca_dreamers.cadreamers.adapter.courses.AdapterCourseList;
import com.ca_dreamers.cadreamers.adapter.courses.AdapterCoursesBanner;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.combo_package.ModelCombo;
import com.ca_dreamers.cadreamers.models.courses.ModelCourse;
import com.ca_dreamers.cadreamers.models.courses.banners.ModelCoursesBanner;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.ca_dreamers.cadreamers.utils.AutoScrollViewPager;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoursesFragment extends Fragment {

    private SharedPrefManager sharedPrefManager;
    private LayoutInflater layoutInflater;
    private HomeViewModel homeViewModel;
    @BindView(R.id.rvCourseList)
    protected RecyclerView rvCourseList;

    @BindView(R.id.tvComboHeader)
    protected TextView tvComboHeader;

    @BindView(R.id.rvComboPackage)
    protected RecyclerView rvComboPackage;


    @BindView(R.id.sliderCourses)
    protected AutoScrollViewPager sliderCourses;
    @BindView(R.id.tabsCourses)
    protected TabLayout tabsCourses;
    @BindView(R.id.btnBrowsCourseBrows)
    protected AppCompatButton btnBrowsCourseBrows;

    private Activity mActivity;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_course, container, false);
        layoutInflater = getLayoutInflater();
        ButterKnife.bind(this, root);
        sharedPrefManager = new SharedPrefManager(getContext());
        btnBrowsCourseBrows.setEnabled(false);
        callBannersApi();
        callCourseApi();
        rvCourseList.setLayoutManager(new LinearLayoutManager(getContext()));
        tvComboHeader.setVisibility(View.GONE);
        rvComboPackage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        callComboApi();
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }


    @OnClick(R.id.btnMyCourseBrows)
    public void btnMyCourseBrowsClicked(){
        Navigation.findNavController(getView()).navigate(R.id.nav_my_courses);
    }
    private void callBannersApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        Call<ModelCoursesBanner> bannersCall = api.getCoursesBanners();
        bannersCall.enqueue(new Callback<ModelCoursesBanner>() {
            @Override
            public void onResponse(Call<ModelCoursesBanner> call, Response<ModelCoursesBanner> response) {
                ModelCoursesBanner modelCoursesBanner = response.body();
                AdapterCoursesBanner adapterCoursesBanner =
                        new AdapterCoursesBanner(modelCoursesBanner.getData(), getContext(), layoutInflater);
                sliderCourses.setAdapter(adapterCoursesBanner);

                tabsCourses.setupWithViewPager(sliderCourses);
                // start auto scroll
                sliderCourses.startAutoScroll();
                // set auto scroll time in mili
                sliderCourses.setInterval(Constant.SLIDER_SPEED);
                // enable recycling using true
                sliderCourses.setCycle(true);
            }

            @Override
            public void onFailure(@NotNull Call<ModelCoursesBanner> call, @NotNull Throwable t) {

            }
        });

    }


    private void callCourseApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", sharedPrefManager.getUserId());

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelCourse> userCall = api.getCourse(gsonObject);

            userCall.enqueue(new Callback<ModelCourse>() {
                @Override
                public void onResponse(Call<ModelCourse> call, Response<ModelCourse> response) {
                    ModelCourse modelCourse = response.body();
                        AdapterCourseList adapterCourseList = new AdapterCourseList(modelCourse.getData());
                        rvCourseList.setAdapter(adapterCourseList);
                }

                @Override
                public void onFailure(Call<ModelCourse> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    private void doAction() {
        if (mActivity == null) {
            return;
        }
    }
    private void callComboApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("type", "package");

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
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

}