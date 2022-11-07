package com.ca_dreamers.cadreamers.fragments.batch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.combo_package.package_details.ModelPackageDetails;
import com.ca_dreamers.cadreamers.utils.Constant;
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


public class FragmentBatchDetails extends Fragment {
    private String strId;
    private String strImage;
    private String strTitle;
    private String strDescription;
    private String strDescriptionFull;
    private String strPrice;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnBuyCombo)
    protected AppCompatButton btnBuyCombo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnViewCombo)
    protected AppCompatButton btnViewCombo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ivComboDetailsView)
    protected ImageView ivComboDetailsView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvComboDescriptionTag)
    protected TextView tvComboDescriptionTag;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvComboDescriptionShort)
    protected TextView tvComboDescriptionShort;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvComboDescription)
    protected TextView tvComboDescription;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnShowMoreDescription)
    protected AppCompatButton btnShowMoreDescription;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnShowLessDescription)
    protected AppCompatButton btnShowLessDescription;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvMrpCombo)
    protected TextView tvMrpCombo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvPriceCombo)
    protected TextView tvPriceCombo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvDiscountCombo)
    protected TextView tvDiscountCombo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_combo_details, container, false);
        Context context = (Activity) view.getContext();
        ButterKnife.bind(this, view);

        assert getArguments() != null;
        strId = getArguments().getString(Constant.COURSE_ID);
        strImage = getArguments().getString(Constant.COURSE_IMAGE);
        strTitle = getArguments().getString(Constant.COURSE_TITLE);
        strDescription = getArguments().getString(Constant.COURSE_DESCRIPTION);
        strPrice = getArguments().getString(Constant.COURSE_PRICE);
        String strMrp = getArguments().getString(Constant.COURSE_MRP);
        String strDiscount = getArguments().getString(Constant.COURSE_DISCOUNT);
        String strPurchased = getArguments().getString(Constant.COURSE_PURCHASED);
        tvComboDescriptionShort.setText(strDescription);
        tvMrpCombo.setText(strMrp);
        tvPriceCombo.setText(strPrice);
        tvDiscountCombo.setText(strDiscount);
        Glide.with(context).load(strImage).into(ivComboDetailsView);
        tvComboDescriptionShort.setVisibility(View.VISIBLE);
        tvComboDescription.setVisibility(View.GONE);
        btnShowLessDescription.setVisibility(View.GONE);
        btnShowMoreDescription.setVisibility(View.VISIBLE);
        if (strPurchased.equals("Purchased"))
        {
            btnBuyCombo.setVisibility(View.GONE);
            btnViewCombo.setVisibility(View.VISIBLE);
        }
        else {
            btnBuyCombo.setVisibility(View.VISIBLE);
            btnViewCombo.setVisibility(View.GONE);
        }
        callPackageDetailsApi();
        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnShowMoreDescription)
    public void btnShowMoreDescriptionClicked() {
        tvComboDescriptionShort.setVisibility(View.GONE);
        tvComboDescription.setVisibility(View.VISIBLE);
        btnShowLessDescription.setVisibility(View.VISIBLE);
        btnShowMoreDescription.setVisibility(View.GONE);
    }
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnShowLessDescription)
    public void btnShowLessDescriptionClicked() {
        tvComboDescriptionShort.setVisibility(View.VISIBLE);
        tvComboDescription.setVisibility(View.GONE);
        btnShowLessDescription.setVisibility(View.GONE);
        btnShowMoreDescription.setVisibility(View.VISIBLE);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnBuyCombo)
    public void btnBuyCourseClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.COURSE_ID, strId);
        bundle.putString(Constant.COURSE_PRICE, strPrice);
        Navigation.findNavController(view).navigate(R.id.nav_address_batch, bundle);
        btnViewCombo.setEnabled(false);

    }
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnViewCombo)
    public void btnViewCourseClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.COURSE_ID, strId);
        bundle.putString(Constant.COURSE_IMAGE, strImage);
        bundle.putString(Constant.COURSE_TITLE, strTitle);
        bundle.putString(Constant.COURSE_DESCRIPTION, strDescription);
        Navigation.findNavController(view).navigate(R.id.nav_purchased_course_detail, bundle);
    }
    private void callPackageDetailsApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("packageId", strId);

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelPackageDetails> call = api.getPackageDetails(gsonObject);

            call.enqueue(new Callback<ModelPackageDetails>() {
                @Override
                public void onResponse(@NotNull Call<ModelPackageDetails> call, @NotNull Response<ModelPackageDetails> response) {
                    ModelPackageDetails modelPackageDetails = response.body();
                    assert modelPackageDetails != null;
                    strDescriptionFull = modelPackageDetails.getData().getDescription();
                    tvComboDescription.setText(Html.fromHtml(strDescriptionFull));
                }

                @Override
                public void onFailure(@NotNull Call<ModelPackageDetails> call, @NotNull Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}