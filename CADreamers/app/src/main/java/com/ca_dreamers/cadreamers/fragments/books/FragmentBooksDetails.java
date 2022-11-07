package com.ca_dreamers.cadreamers.fragments.books;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.activity.MainActivity;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.books.book_details.ModelBooksDetails;
import com.ca_dreamers.cadreamers.models.books.product_type.ModelBookMode;
import com.ca_dreamers.cadreamers.models.cart.add_cart.ModelAddCart;
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

import static androidx.core.content.ContextCompat.*;


public class FragmentBooksDetails extends Fragment implements View.OnClickListener {

    private Context tContext;
    private String strUserId;
    private String strId;
    private String strImage;
    private String strTitle;
    private String strDescription;
    private String strPrice = " ";
    private String strProdType = " ";
    private String strProdLang;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnBuyBooks)
    protected AppCompatButton btnBuyBooks;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rgBookMode)
    protected RadioGroup rgBookMode;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnShowBookMode)
    protected AppCompatButton btnShowBookMode;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnHideBookMode)
    protected AppCompatButton btnHideBookMode;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnViewBooks)
    protected AppCompatButton btnViewBooks;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ivBooksDetailsView)
    protected ImageView ivBooksDetailsView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvBooksDescriptionShort)
    protected TextView tvBooksDescriptionShort;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvBooksDescription)
    protected TextView tvBooksDescription;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnShowMoreBooksDescription)
    protected AppCompatButton btnShowMoreBooksDescription;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnShowLessBooksDescription)
    protected AppCompatButton btnShowLessBooksDescription;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvBooks)
    protected RecyclerView rvBooks;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books_details, container, false);
        ButterKnife.bind(this, view);

        tContext = (Activity)view.getContext();
        SharedPrefManager sharedPrefManager = new SharedPrefManager(getContext());
        strUserId = sharedPrefManager.getUserId();
        assert getArguments() != null;
        strId = getArguments().getString(Constant.BOOKS_ID);
        strImage = getArguments().getString(Constant.BOOKS_IMAGE);
        strTitle = getArguments().getString(Constant.BOOKS_TITLE);
        strDescription = getArguments().getString(Constant.BOOKS_DESCRIPTION);
        String strDescriptionFull = getArguments().getString(Constant.BOOKS_DESCRIPTION_FULL);
        String strPurchased = getArguments().getString(Constant.BOOKS_PURCHASED);
        tvBooksDescriptionShort.setText(strDescription);

        tvBooksDescription.setText(Html.fromHtml(strDescriptionFull));
        Glide.with(tContext).load(strImage).into(ivBooksDetailsView);
        rvBooks.setLayoutManager(new LinearLayoutManager(getContext()));
        tvBooksDescriptionShort.setVisibility(View.VISIBLE);
        tvBooksDescription.setVisibility(View.GONE);
        btnShowLessBooksDescription.setVisibility(View.GONE);
        btnShowMoreBooksDescription.setVisibility(View.VISIBLE);
        if (strPurchased.equals("Purchased")){
            btnBuyBooks.setVisibility(View.GONE);
            btnViewBooks.setVisibility(View.VISIBLE);
        }else {
            btnBuyBooks.setVisibility(View.VISIBLE);
            btnViewBooks.setVisibility(View.GONE);
        }
        btnShowBookMode.setVisibility(View.VISIBLE);
        btnHideBookMode.setVisibility(View.GONE);
        rgBookMode.setVisibility(View.GONE);
        callBooksDetailsApi();
        callBookModeApi();
        return view;
    }

    private void callBooksDetailsApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", strUserId);
            paramObject.put("prod_id", strId);

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelBooksDetails> call = api.getBooksDetails(gsonObject);

            call.enqueue(new Callback<ModelBooksDetails>() {
                @Override
                public void onResponse(@NonNull Call<ModelBooksDetails> call, @NonNull Response<ModelBooksDetails> response) {
                    ModelBooksDetails modelCourseDetail = response.body();
                    assert modelCourseDetail != null;
                    strProdLang = modelCourseDetail.getData().getLanguage();

                }


                @Override
                public void onFailure(@NonNull Call<ModelBooksDetails> call, @NonNull Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnShowMoreBooksDescription)
    public void btnShowMoreBooksDescriptionClicked() {
        tvBooksDescriptionShort.setVisibility(View.GONE);
        tvBooksDescription.setVisibility(View.VISIBLE);
        btnShowLessBooksDescription.setVisibility(View.VISIBLE);
        btnShowMoreBooksDescription.setVisibility(View.GONE);
    }
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnShowLessBooksDescription)
    public void btnShowLessBooksDescriptionClicked() {
        tvBooksDescriptionShort.setVisibility(View.VISIBLE);
        tvBooksDescription.setVisibility(View.GONE);
        btnShowLessBooksDescription.setVisibility(View.GONE);
        btnShowMoreBooksDescription.setVisibility(View.VISIBLE);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnBuyBooks)
    public void btnBuyBooksClicked(View view) {
        if (strPrice.equals(" ")) {
            Toast.makeText(getContext(), "Please select the mode of Book", Toast.LENGTH_SHORT).show();
            btnShowBookMode.setVisibility(View.GONE);
            btnHideBookMode.setVisibility(View.VISIBLE);
            rgBookMode.setVisibility(View.VISIBLE);
        }
        else {
                callCartAddApi(view, strProdType);
                btnBuyBooks.setEnabled(false);
            btnShowBookMode.setVisibility(View.VISIBLE);
            btnHideBookMode.setVisibility(View.GONE);
            rgBookMode.setVisibility(View.VISIBLE);
        }

    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnShowBookMode)
    public void btnShowBookModeClicked() {
        btnShowBookMode.setVisibility(View.GONE);
        btnHideBookMode.setVisibility(View.VISIBLE);
        rgBookMode.setVisibility(View.VISIBLE);
    }
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnHideBookMode)
    public void btnHideBookModeClicked() {
        btnShowBookMode.setVisibility(View.VISIBLE);
        btnHideBookMode.setVisibility(View.GONE);
        rgBookMode.setVisibility(View.GONE);
    }
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnViewBooks)
    public void btnViewBooksClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.BOOKS_ID, strId);
        bundle.putString(Constant.BOOKS_IMAGE, strImage);
        bundle.putString(Constant.BOOKS_TITLE, strTitle);
        bundle.putString(Constant.BOOKS_DESCRIPTION, strDescription);
        Navigation.findNavController(view).navigate(R.id.nav_purchased_Books_Details, bundle);

    }


    private void callBookModeApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("product_id", strId);
            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelBookMode> call = api.getBooksMode(gsonObject);

            call.enqueue(new Callback<ModelBookMode>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<ModelBookMode> call, @NonNull Response<ModelBookMode> response) {
                    ModelBookMode modelBookMode = response.body();
                    rgBookMode.setOrientation(LinearLayout.VERTICAL);
                    assert modelBookMode != null;
                    int number = modelBookMode.getData().getList().size();
                    for (int i = 0; i < number; i++) {
                        AppCompatRadioButton rdbtn = new AppCompatRadioButton(tContext);
                        rdbtn.setId(View.generateViewId());
                        rdbtn.setText(modelBookMode.getData().getList().get(i).getModeD()+", ₹"+
                                modelBookMode.getData().getList().get(i).getPrice());
                        rdbtn.setTextSize(10);
                        rdbtn.setTextColor(getColor(tContext, R.color.black));
                        ColorStateList myColorStateList = new ColorStateList(
                                new int[][]{
                                        new int[]{ContextCompat.getColor(tContext, R.color.colorPrimaryDark)}
                                },
                                new int[]{ContextCompat.getColor(tContext, R.color.colorAccent)}
                        );

                        rdbtn.setButtonTintList(myColorStateList);

                        rgBookMode.setOnCheckedChangeListener((rg, checkedId) -> {
                            for (int i1 = 0; i1 < rg.getChildCount(); i1++) {
                                AppCompatRadioButton btn = (AppCompatRadioButton) rg.getChildAt(i1);
                                if (btn.getId() == checkedId) {

                                    strProdType = modelBookMode.getData().getList().get(i1).getMode();
                                    strPrice = modelBookMode.getData().getList().get(i1).getPrice();
                                    Log.d(Constant.TAG, "Price: " + strPrice + " " + strProdType + " \nChecked Id:" + checkedId);
                                    return;
                                }
                            }
                        });
                        rgBookMode.addView(rdbtn);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ModelBookMode> call, @NonNull Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void callCartAddApi(View view, String strProdType){
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
                        btnBuyBooks.setEnabled(true);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ModelAddCart> call, @NonNull Throwable t) {
                    Log.d("TSF_CART", t.getMessage());
                    btnBuyBooks.setEnabled(true);
                    btnShowBookMode.setVisibility(View.VISIBLE);
                    btnHideBookMode.setVisibility(View.GONE);
                    rgBookMode.setVisibility(View.GONE);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        Log.d(Constant.TAG, " Name " + ((RadioButton)v).getText() +" Id is "+v.getId());
    }

}