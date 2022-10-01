package com.ca_dreamers.cadreamers.fragment;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.courses.AdapterCourseDetailsShow;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.course_details.ModelCourseDetail;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentCourseDetails extends Fragment {
    private SharedPrefManager sharedPrefManager;
    private String strId;
    private String strImage;
    private String strTitle;
    private String strDescription;
    private String strDescriptionFull;
    private String strPrice;
    private String strMrp;
    private String strDiscount;

    @BindView(R.id.ivCourseDetailsView)
    protected ImageView ivCourseDetailsView;
    @BindView(R.id.tvCourseDescriptionShort)
    protected TextView tvCourseDescriptionShort;
    @BindView(R.id.tvCourseDescription)
    protected TextView tvCourseDescription;
    @BindView(R.id.btnShowMoreDescription)
    protected AppCompatButton btnShowMoreDescription;
    @BindView(R.id.btnShowLessDescription)
    protected AppCompatButton btnShowLessDescription;
    @BindView(R.id.tvMrpCourse)
    protected TextView tvMrpCourse;
    @BindView(R.id.tvPriceCourse)
    protected TextView tvPriceCourse;
    @BindView(R.id.tvDiscountCourse)
    protected TextView tvDiscountCourse;
    @BindView(R.id.rvChapter)
    protected RecyclerView rvChapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_details, container, false);
        ButterKnife.bind(this, view);

        sharedPrefManager = new SharedPrefManager(getContext());
        strId = getArguments().getString(Constant.COURSE_ID);
        strImage = getArguments().getString(Constant.COURSE_IMAGE);
        strTitle = getArguments().getString(Constant.COURSE_TITLE);
        strDescription = getArguments().getString(Constant.COURSE_DESCRIPTION);
        strPrice = getArguments().getString(Constant.COURSE_PRICE);
        strMrp = getArguments().getString(Constant.COURSE_MRP);
        strDiscount = getArguments().getString(Constant.COURSE_DISCOUNT);
        tvCourseDescriptionShort.setText(strDescription);
        tvMrpCourse.setText(strMrp);
        tvPriceCourse.setText(strPrice);
        tvDiscountCourse.setText(strDiscount);
        Glide.with(getContext()).load(strImage).into(ivCourseDetailsView);
        rvChapter.setLayoutManager(new LinearLayoutManager(getContext()));
        tvCourseDescriptionShort.setVisibility(View.VISIBLE);
        tvCourseDescription.setVisibility(View.GONE);
        btnShowLessDescription.setVisibility(View.GONE);
        btnShowMoreDescription.setVisibility(View.VISIBLE);
        callCourseApi();
        return view;
    }

    private void callCourseApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", sharedPrefManager.getUserId());
            paramObject.put("prod_id", strId);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelCourseDetail> call = api.getCourseDetails(gsonObject);

            call.enqueue(new Callback<ModelCourseDetail>() {
                @Override
                public void onResponse(Call<ModelCourseDetail> call, Response<ModelCourseDetail> response) {
                    ModelCourseDetail modelCourseDetail = response.body();
                    strDescriptionFull = modelCourseDetail.getData().getShortDesc();
                    tvCourseDescription.setText(Html.fromHtml(strDescriptionFull));

                    AdapterCourseDetailsShow adapterCourseDetailsShow = new AdapterCourseDetailsShow(modelCourseDetail.getData().getChapters(), getActivity().getApplicationContext());
//                    rvChapter.setAdapter(adapterCourseDetailsShow);
                }


                @Override
                public void onFailure(Call<ModelCourseDetail> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btnShowMoreDescription)
    public void btnShowMoreDescriptionClicked() {
        tvCourseDescriptionShort.setVisibility(View.GONE);
        tvCourseDescription.setVisibility(View.VISIBLE);
        btnShowLessDescription.setVisibility(View.VISIBLE);
        btnShowMoreDescription.setVisibility(View.GONE);
    }
    @OnClick(R.id.btnShowLessDescription)
    public void btnShowLessDescriptionClicked() {
        tvCourseDescriptionShort.setVisibility(View.VISIBLE);
        tvCourseDescription.setVisibility(View.GONE);
        btnShowLessDescription.setVisibility(View.GONE);
        btnShowMoreDescription.setVisibility(View.VISIBLE);
    }

}