package com.ca_dreamers.cadreamers.ui.books;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.courses.AdapterCourseDetailsShow;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.course_details.ModelCourseDetail;
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


public class FragmentBooksDetails extends Fragment {

    private String strId;
    private String strImage;
    private String strTitle;
    private String strDescription;
    private String strDescriptionFull;
    private String strPrice;
    private String strMrp;
    private String strDiscount;

    @BindView(R.id.ivBooksDetailsView)
    protected ImageView ivBooksDetailsView;
    @BindView(R.id.tvBooksDescriptionShort)
    protected TextView tvBooksDescriptionShort;
    @BindView(R.id.tvBooksDescription)
    protected TextView tvBooksDescription;
    @BindView(R.id.btnShowMoreBooksDescription)
    protected AppCompatButton btnShowMoreBooksDescription;
    @BindView(R.id.btnShowLessBooksDescription)
    protected AppCompatButton btnShowLessBooksDescription;
    @BindView(R.id.tvMrpBooks)
    protected TextView tvMrpBooks;
    @BindView(R.id.tvPriceBooks)
    protected TextView tvPriceBooks;
    @BindView(R.id.tvDiscountBooks)
    protected TextView tvDiscountBooks;
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

        strId = getArguments().getString(Constant.COURSE_ID);
        strImage = getArguments().getString(Constant.COURSE_IMAGE);
        strTitle = getArguments().getString(Constant.COURSE_TITLE);
        strDescription = getArguments().getString(Constant.COURSE_DESCRIPTION);
        strDescriptionFull = getArguments().getString(Constant.COURSE_DESCRIPTION_FULL);
        strPrice = getArguments().getString(Constant.COURSE_PRICE);
        strMrp = getArguments().getString(Constant.COURSE_MRP);
        strDiscount = getArguments().getString(Constant.COURSE_DISCOUNT);
        tvBooksDescriptionShort.setText(strDescription);
        tvMrpBooks.setText(strMrp);
        tvPriceBooks.setText(strPrice);
        tvDiscountBooks.setText(strDiscount);
        tvBooksDescription.setText(Html.fromHtml(strDescriptionFull));
        Glide.with(getContext()).load(strImage).into(ivBooksDetailsView);
        rvBooks.setLayoutManager(new LinearLayoutManager(getContext()));
        tvBooksDescriptionShort.setVisibility(View.VISIBLE);
        tvBooksDescription.setVisibility(View.GONE);
        btnShowLessBooksDescription.setVisibility(View.GONE);
        btnShowMoreBooksDescription.setVisibility(View.VISIBLE);
        callCourseApi();
        return view;
    }

    private void callCourseApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", "4");
            paramObject.put("prod_id", strId);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelCourseDetail> call = api.getCourseDetails(gsonObject);

            call.enqueue(new Callback<ModelCourseDetail>() {
                @Override
                public void onResponse(Call<ModelCourseDetail> call, Response<ModelCourseDetail> response) {
                    ModelCourseDetail modelCourseDetail = response.body();
//                    strDescriptionFull = modelCourseDetail.getData().getShortDesc();


                    AdapterCourseDetailsShow adapterCourseDetailsShow = new AdapterCourseDetailsShow(modelCourseDetail.getData().getChapters(), getActivity().getApplicationContext());
                    rvBooks.setAdapter(adapterCourseDetailsShow);
                }


                @Override
                public void onFailure(Call<ModelCourseDetail> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btnShowMoreBooksDescription)
    public void btnShowMoreBooksDescriptionClicked() {
        tvBooksDescriptionShort.setVisibility(View.GONE);
        tvBooksDescription.setVisibility(View.VISIBLE);
        btnShowLessBooksDescription.setVisibility(View.VISIBLE);
        btnShowMoreBooksDescription.setVisibility(View.GONE);
    }
    @OnClick(R.id.btnShowLessBooksDescription)
    public void btnShowLessBooksDescriptionClicked() {
        tvBooksDescriptionShort.setVisibility(View.VISIBLE);
        tvBooksDescription.setVisibility(View.GONE);
        btnShowLessBooksDescription.setVisibility(View.GONE);
        btnShowMoreBooksDescription.setVisibility(View.VISIBLE);
    }
}