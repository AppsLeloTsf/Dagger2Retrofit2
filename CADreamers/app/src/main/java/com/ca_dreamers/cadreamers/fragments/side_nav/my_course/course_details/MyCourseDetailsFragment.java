package com.ca_dreamers.cadreamers.fragments.side_nav.my_course.course_details;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.my_course.AdapterCourseDetailsChapter;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.my_orders.course_details.ModelMyOrdersCourseDetails;
import com.ca_dreamers.cadreamers.models.my_orders.course_details.chapters.ModelCourseChapter;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCourseDetailsFragment extends Fragment {
    private SharedPrefManager sharedPrefManager;
    private Context tContext;
    private String strProdId;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ivPurchasedCourseDetailsView)
    protected ImageView ivPurchasedCourseDetailsView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvPurchasedCourseDescriptionShort)
    protected TextView tvPurchasedCourseDescriptionShort;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvNamePurchasedCourse)
    protected TextView tvNamePurchasedCourse;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvAuthorPurchasedCourse)
    protected TextView tvAuthorPurchasedCourse;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvRatingPurchasedCourse)
    protected TextView tvRatingPurchasedCourse;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvMyCourseChapters)
    protected RecyclerView rvMyCourseChapters;
    public static MyCourseDetailsFragment newInstance() {
        return new MyCourseDetailsFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_course_details, container, false);
        ButterKnife.bind(this, view);
        tContext = (Activity)getContext();
        sharedPrefManager = new SharedPrefManager(tContext);
        assert getArguments() != null;
        strProdId = getArguments().getString(Constant.COURSE_ID);
        String strDescription = getArguments().getString(Constant.COURSE_DESCRIPTION);
        tvPurchasedCourseDescriptionShort.setText(strDescription);
        tvPurchasedCourseDescriptionShort.setVisibility(View.VISIBLE);



        rvMyCourseChapters.setLayoutManager(new LinearLayoutManager(tContext));
        callCourseApi(getActivity());
        callChapterApi();
        return view;
    }

    private void callCourseApi(Context context){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", sharedPrefManager.getUserId());
            paramObject.put("prodid", strProdId);

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelMyOrdersCourseDetails> userCall = api.getMyOrdersCourseDetails(gsonObject);

            userCall.enqueue(new Callback<ModelMyOrdersCourseDetails>() {
                @Override
                public void onResponse(@NotNull Call<ModelMyOrdersCourseDetails> call, @NotNull Response<ModelMyOrdersCourseDetails> response) {
                    ModelMyOrdersCourseDetails modelCourse = response.body();
                    assert modelCourse != null;
                    tvNamePurchasedCourse.setText(modelCourse.getData().getName());
                    tvAuthorPurchasedCourse.setText(modelCourse.getData().getAuthor());
                    tvRatingPurchasedCourse.setText(modelCourse.getData().getRating());
                    Glide.with(context).load(modelCourse.getData().getThumbUrl()).into(ivPurchasedCourseDetailsView);
                }

                @Override
                public void onFailure(@NotNull Call<ModelMyOrdersCourseDetails> call, @NotNull Throwable t) {
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void callChapterApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", sharedPrefManager.getUserId());
            paramObject.put("prodid", strProdId);

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelCourseChapter> userCall = api.getCourseChapters(gsonObject);

            userCall.enqueue(new Callback<ModelCourseChapter>() {
                @Override
                public void onResponse(@NotNull Call<ModelCourseChapter> call, @NotNull Response<ModelCourseChapter> response) {
                    ModelCourseChapter modelCourse = response.body();
                    assert modelCourse != null;
                    AdapterCourseDetailsChapter adapterCourseDetailsChapter = new AdapterCourseDetailsChapter(modelCourse.getData(), tContext);
                    rvMyCourseChapters.setAdapter(adapterCourseDetailsChapter);
                }

                @Override
                public void onFailure(@NotNull Call<ModelCourseChapter> call, @NotNull Throwable t) {
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}