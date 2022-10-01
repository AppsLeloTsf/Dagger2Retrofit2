package com.ca_dreamers.cadreamers.fragments.combo_package;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.activity.MainActivity;
import com.ca_dreamers.cadreamers.activity.MakePaymentActivity;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.cart.add_cart.ModelAddCart;
import com.ca_dreamers.cadreamers.models.course_details.ModelCourseDetail;
import com.ca_dreamers.cadreamers.models.my_payment.package_payment.ModelComboPayment;
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

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class FragmentComboDetails extends Fragment {
    private SharedPrefManager sharedPrefManager;
    private String strUserId;
    private String strId;
    private String strImage;
    private String strTitle;
    private String strDescription;
    private String strDescriptionFull;
    private String strPrice;
    private String strMrp;
    private String strDiscount;
    private String strPurchased;
    private String strProdType;
    private String strProdLang;

    @BindView(R.id.btnBuyCombo)
    protected AppCompatButton btnBuyCombo;
    @BindView(R.id.btnViewCombo)
    protected AppCompatButton btnViewCombo;
    @BindView(R.id.ivComboDetailsView)
    protected ImageView ivComboDetailsView;
    @BindView(R.id.tvComboDescriptionShort)
    protected TextView tvComboDescriptionShort;
    @BindView(R.id.tvComboDescription)
    protected TextView tvComboDescription;
    @BindView(R.id.btnShowMoreDescription)
    protected AppCompatButton btnShowMoreDescription;
    @BindView(R.id.btnShowLessDescription)
    protected AppCompatButton btnShowLessDescription;
    @BindView(R.id.tvMrpCombo)
    protected TextView tvMrpCombo;
    @BindView(R.id.tvPriceCombo)
    protected TextView tvPriceCombo;
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
        ButterKnife.bind(this, view);

        sharedPrefManager = new SharedPrefManager(getContext());
        strUserId = sharedPrefManager.getUserId();
        strId = getArguments().getString(Constant.COURSE_ID);
        strImage = getArguments().getString(Constant.COURSE_IMAGE);
        strTitle = getArguments().getString(Constant.COURSE_TITLE);
        strDescription = getArguments().getString(Constant.COURSE_DESCRIPTION);
        strPrice = getArguments().getString(Constant.COURSE_PRICE);
        strMrp = getArguments().getString(Constant.COURSE_MRP);
        strDiscount = getArguments().getString(Constant.COURSE_DISCOUNT);
        strPurchased = getArguments().getString(Constant.COURSE_PURCHASED);
        tvComboDescriptionShort.setText(strDescription);
        tvMrpCombo.setText(strMrp);
        tvPriceCombo.setText(strPrice);
        tvDiscountCombo.setText(strDiscount);
        Glide.with(getContext()).load(strImage).into(ivComboDetailsView);
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
        callCourseApi();
        return view;
    }

    private void callCourseApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", strUserId);
            paramObject.put("prod_id", strId);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelCourseDetail> call = api.getCourseDetails(gsonObject);

            call.enqueue(new Callback<ModelCourseDetail>() {
                @Override
                public void onResponse(Call<ModelCourseDetail> call, Response<ModelCourseDetail> response) {
                    ModelCourseDetail modelCourseDetail = response.body();
                    strDescriptionFull = modelCourseDetail.getData().getShortDesc();
                    tvComboDescription.setText(Html.fromHtml(strDescriptionFull));
                    strProdType = modelCourseDetail.getData().getProductType();
                    strProdLang = modelCourseDetail.getData().getLanguage();
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
        tvComboDescriptionShort.setVisibility(View.GONE);
        tvComboDescription.setVisibility(View.VISIBLE);
        btnShowLessDescription.setVisibility(View.VISIBLE);
        btnShowMoreDescription.setVisibility(View.GONE);
    }
    @OnClick(R.id.btnShowLessDescription)
    public void btnShowLessDescriptionClicked() {
        tvComboDescriptionShort.setVisibility(View.VISIBLE);
        tvComboDescription.setVisibility(View.GONE);
        btnShowLessDescription.setVisibility(View.GONE);
        btnShowMoreDescription.setVisibility(View.VISIBLE);
    }

    private void callCartAddApi(View view){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("price", strPrice);
            paramObject.put("languagename", strProdLang);
            paramObject.put("producttype", strProdType);
            paramObject.put("productid", strId);
            paramObject.put("userid", strUserId);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelAddCart> call = api.addCart(gsonObject);
            call.enqueue(new Callback<ModelAddCart>() {
                @Override
                public void onResponse(Call<ModelAddCart> call, Response<ModelAddCart> response) {
                    ModelAddCart modelCourseDetail = response.body();
                    if (modelCourseDetail.getStatus().getStatuscode().equals("200")) {
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity)getActivity()).callFetchCartApi();
                        }
                        Navigation.findNavController(view).navigate(R.id.menu_cart);
                        btnViewCombo.setEnabled(true);

                    }
                }
                @Override
                public void onFailure(Call<ModelAddCart> call, Throwable t) {
                    Log.d("TSF_CART", t.getMessage());
                    btnViewCombo.setEnabled(true);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @OnClick(R.id.btnBuyCombo)
    public void btnBuyCourseClicked(View view) {
       callCartAddApi(view);
        btnViewCombo.setEnabled(false);

    }
    @OnClick(R.id.btnViewCombo)
    public void btnViewCourseClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.COURSE_ID, strId);
        bundle.putString(Constant.COURSE_IMAGE, strImage);
        bundle.putString(Constant.COURSE_TITLE, strTitle);
        bundle.putString(Constant.COURSE_DESCRIPTION, strDescription);
        Navigation.findNavController(view).navigate(R.id.nav_purchased_course_detail, bundle);
    }
    private void callPackagePaymentApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("product_id", strId);
            paramObject.put("payment_method", "instamojo");
            paramObject.put("userid", strUserId);
            paramObject.put("price", strPrice);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelComboPayment> call = api.getComboPayment(gsonObject);

            call.enqueue(new Callback<ModelComboPayment>() {
                @Override
                public void onResponse(Call<ModelComboPayment> call, Response<ModelComboPayment> response) {
                    ModelComboPayment modelComboPayment = response.body();

                    Intent intent = new Intent(getContext(), MakePaymentActivity.class);
                    intent.putExtra(Constant.PAYMENT_URL, modelComboPayment.getData().getRedirectLongUrl());
                   // intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<ModelComboPayment> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}