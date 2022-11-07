package com.ca_dreamers.cadreamers.fragments.courses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.activity.MainActivity;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.cart.add_cart.ModelAddCart;
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
    private String strUserId;
    private String strId;
    private String strImage;
    private String strTitle;
    private String strDescription;
    private String strDescriptionFull;
    private String strPrice;
    private String strProdType;
    private String strProdLang;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnBuyCourse)
    protected AppCompatButton btnBuyCourse;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnViewCourse)
    protected AppCompatButton btnViewCourse;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ivCourseDetailsView)
    protected ImageView ivCourseDetailsView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvCourseDescriptionShort)
    protected TextView tvCourseDescriptionShort;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvCourseDescription)
    protected TextView tvCourseDescription;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnShowMoreDescription)
    protected AppCompatButton btnShowMoreDescription;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnShowLessDescription)
    protected AppCompatButton btnShowLessDescription;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvMrpCourse)
    protected TextView tvMrpCourse;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvPriceCourse)
    protected TextView tvPriceCourse;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvDiscountCourse)
    protected TextView tvDiscountCourse;
    @SuppressLint("NonConstantResourceId")
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
        Context context = (Activity) view.getContext();

        SharedPrefManager sharedPrefManager = new SharedPrefManager(getContext());
        strUserId = sharedPrefManager.getUserId();
        assert getArguments() != null;
        strId = getArguments().getString(Constant.COURSE_ID);
        strImage = getArguments().getString(Constant.COURSE_IMAGE);
        strTitle = getArguments().getString(Constant.COURSE_TITLE);
        strDescription = getArguments().getString(Constant.COURSE_DESCRIPTION);
        strPrice = getArguments().getString(Constant.COURSE_PRICE);
        String strMrp = getArguments().getString(Constant.COURSE_MRP);
        String strDiscount = getArguments().getString(Constant.COURSE_DISCOUNT);
        String strPurchased = getArguments().getString(Constant.COURSE_PURCHASED);
        tvCourseDescriptionShort.setText(strDescription);
        tvMrpCourse.setText(strMrp);
        tvPriceCourse.setText(strPrice);
        tvDiscountCourse.setText(strDiscount);
        Glide.with(context).load(strImage).into(ivCourseDetailsView);
        rvChapter.setLayoutManager(new LinearLayoutManager(getContext()));
        tvCourseDescriptionShort.setVisibility(View.VISIBLE);
        tvCourseDescription.setVisibility(View.GONE);
        btnShowLessDescription.setVisibility(View.GONE);
        btnShowMoreDescription.setVisibility(View.VISIBLE);
        if (strPurchased.equals("Purchased"))
        {
            btnBuyCourse.setVisibility(View.GONE);
            btnViewCourse.setVisibility(View.VISIBLE);
        }
        else {
            btnBuyCourse.setVisibility(View.VISIBLE);
            btnViewCourse.setVisibility(View.GONE);
        }
        callCourseApi();
        return view;
    }

    private void callCourseApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", strUserId);
            paramObject.put("prod_id", strId);

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelCourseDetail> call = api.getCourseDetails(gsonObject);

            call.enqueue(new Callback<ModelCourseDetail>() {
                @Override
                public void onResponse(@NonNull Call<ModelCourseDetail> call, @NonNull Response<ModelCourseDetail> response) {
                    ModelCourseDetail modelCourseDetail = response.body();
                    assert modelCourseDetail != null;
                    strDescriptionFull = modelCourseDetail.getData().getShortDesc();
                    tvCourseDescription.setText(Html.fromHtml(strDescriptionFull));
                    strProdType = modelCourseDetail.getData().getProductType();
                    strProdLang = modelCourseDetail.getData().getLanguage();
                }


                @Override
                public void onFailure(@NonNull Call<ModelCourseDetail> call, @NonNull Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnShowMoreDescription)
    public void btnShowMoreDescriptionClicked() {
        tvCourseDescriptionShort.setVisibility(View.GONE);
        tvCourseDescription.setVisibility(View.VISIBLE);
        btnShowLessDescription.setVisibility(View.VISIBLE);
        btnShowMoreDescription.setVisibility(View.GONE);
    }
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnShowLessDescription)
    public void btnShowLessDescriptionClicked() {
        tvCourseDescriptionShort.setVisibility(View.VISIBLE);
        tvCourseDescription.setVisibility(View.GONE);
        btnShowLessDescription.setVisibility(View.GONE);
        btnShowMoreDescription.setVisibility(View.VISIBLE);
    }

    private void callCartAddApi(View view){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("price", strPrice);
            paramObject.put("languagename", strProdLang);
            paramObject.put("producttype", strProdType);
            paramObject.put("productid", strId);
            paramObject.put("userid", strUserId);

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelAddCart> call = api.addCart(gsonObject);
            call.enqueue(new Callback<ModelAddCart>() {
                @Override
                public void onResponse(@NonNull Call<ModelAddCart> call, @NonNull Response<ModelAddCart> response) {
                    ModelAddCart modelCourseDetail = response.body();
                    assert modelCourseDetail != null;
                    if (modelCourseDetail.getStatus().getStatuscode().equals("200")) {
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity)getActivity()).callFetchCartApi();
                        }
                        Navigation.findNavController(view).navigate(R.id.menu_cart);
                        btnBuyCourse.setEnabled(true);

                    }
                }
                @Override
                public void onFailure(@NonNull Call<ModelAddCart> call, @NonNull Throwable t) {
                    Log.d("TSF_CART", t.getMessage());
                    btnBuyCourse.setEnabled(true);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnBuyCourse)
    public void btnBuyCourseClicked(View view) {
       callCartAddApi(view);
       btnBuyCourse.setEnabled(false);

    }
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnViewCourse)
    public void btnViewCourseClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.COURSE_ID, strId);
        bundle.putString(Constant.COURSE_IMAGE, strImage);
        bundle.putString(Constant.COURSE_TITLE, strTitle);
        bundle.putString(Constant.COURSE_DESCRIPTION, strDescription);
        Navigation.findNavController(view).navigate(R.id.nav_purchased_course_detail, bundle);
    }

}