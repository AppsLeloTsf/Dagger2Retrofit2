package com.ca_dreamers.cadreamers.fragments.side_nav.my_orders.course_details;

import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.ca_dreamers.cadreamers.adapter.my_orders.course.AdapterCourseDetailsChapter;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.my_orders.course_details.ModelMyOrdersCourseDetails;
import com.ca_dreamers.cadreamers.models.my_orders.course_details.chapters.ModelCourseChapter;
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

public class PurchasedCourseDetailsFragment extends Fragment {

    private PurchasedCourseDetailsViewModel mViewModel;
    private SharedPrefManager sharedPrefManager;
    private String strProdId;
    private String strImage;
    private String strTitle;
    private String strDescription;
    private String strDescriptionFull;
    private String strPrice;
    private String strMrp;
    private String strDiscount;

    @BindView(R.id.ivPurchasedCourseDetailsView)
    protected ImageView ivPurchasedCourseDetailsView;
    @BindView(R.id.tvPurchasedCourseDescriptionShort)
    protected TextView tvPurchasedCourseDescriptionShort;
    @BindView(R.id.tvPurchasedCourseDescription)
    protected TextView tvPurchasedCourseDescription;
    @BindView(R.id.btnShowMoreDescriptionPurchased)
    protected AppCompatButton btnShowMoreDescriptionPurchased;
    @BindView(R.id.btnShowLessDescriptionPurchased)
    protected AppCompatButton btnShowLessDescriptionPurchased;
    @BindView(R.id.tvNamePurchasedCourse)
    protected TextView tvNamePurchasedCourse;
    @BindView(R.id.tvAuthorPurchasedCourse)
    protected TextView tvAuthorPurchasedCourse;
    @BindView(R.id.tvRatingPurchasedCourse)
    protected TextView tvRatingPurchasedCourse;
    @BindView(R.id.rvMyCourseChapters)
    protected RecyclerView rvMyCourseChapters;
    public static PurchasedCourseDetailsFragment newInstance() {
        return new PurchasedCourseDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.purchased_course_details_fragment, container, false);
        ButterKnife.bind(this, view);
        sharedPrefManager = new SharedPrefManager(getContext());
        strProdId = getArguments().getString(Constant.COURSE_ID);
        strTitle = getArguments().getString(Constant.COURSE_TITLE);
        strImage = getArguments().getString(Constant.COURSE_IMAGE);
        strDescription = getArguments().getString(Constant.COURSE_DESCRIPTION);
        tvPurchasedCourseDescriptionShort.setText(strDescription);
        tvPurchasedCourseDescriptionShort.setVisibility(View.VISIBLE);
        btnShowMoreDescriptionPurchased.setVisibility(View.VISIBLE);
        btnShowLessDescriptionPurchased.setVisibility(View.GONE);
        tvPurchasedCourseDescription.setVisibility(View.GONE);
        rvMyCourseChapters.setLayoutManager(new LinearLayoutManager(getContext()));
        callCourseApi(getActivity());
        callChapterApi();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PurchasedCourseDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

    private void callCourseApi(Context context){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", sharedPrefManager.getUserId());
            paramObject.put("prodid", strProdId);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelMyOrdersCourseDetails> userCall = api.getMyOrdersCourseDetails(gsonObject);

            userCall.enqueue(new Callback<ModelMyOrdersCourseDetails>() {
                @Override
                public void onResponse(Call<ModelMyOrdersCourseDetails> call, Response<ModelMyOrdersCourseDetails> response) {
                    ModelMyOrdersCourseDetails modelCourse = response.body();
                    tvNamePurchasedCourse.setText(modelCourse.getData().getName());
                    tvAuthorPurchasedCourse.setText(modelCourse.getData().getAuthor());
                    tvRatingPurchasedCourse.setText(modelCourse.getData().getRating());
                    tvPurchasedCourseDescription.setText(Html.fromHtml(modelCourse.getData().getShortDesc()));
                    Glide.with(context).load(modelCourse.getData().getThumbUrl()).into(ivPurchasedCourseDetailsView);
                }

                @Override
                public void onFailure(Call<ModelMyOrdersCourseDetails> call, Throwable t) {
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void callChapterApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", sharedPrefManager.getUserId());
            paramObject.put("prodid", strProdId);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelCourseChapter> userCall = api.getCourseChapters(gsonObject);

            userCall.enqueue(new Callback<ModelCourseChapter>() {
                @Override
                public void onResponse(Call<ModelCourseChapter> call, Response<ModelCourseChapter> response) {
                    ModelCourseChapter modelCourse = response.body();
                    AdapterCourseDetailsChapter adapterCourseDetailsChapter = new AdapterCourseDetailsChapter(modelCourse.getData(), getContext());
                    rvMyCourseChapters.setAdapter(adapterCourseDetailsChapter);
//                    tvNamePurchasedCourse.setText(modelCourse.getData().getName());
//                    tvAuthorPurchasedCourse.setText(modelCourse.getData().getAuthor());
//                    tvRatingPurchasedCourse.setText(modelCourse.getData().getRating());
//                    tvPurchasedCourseDescription.setText(Html.fromHtml(modelCourse.getData().getShortDesc()));
//                    Glide.with(getContext()).load(modelCourse.getData().getThumbUrl()).into(ivPurchasedCourseDetailsView);
                }

                @Override
                public void onFailure(Call<ModelCourseChapter> call, Throwable t) {
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.btnShowMoreDescriptionPurchased)
    public void btnShowMoreDescriptionPurchasedClicked(){
        tvPurchasedCourseDescriptionShort.setVisibility(View.GONE);
        btnShowMoreDescriptionPurchased.setVisibility(View.GONE);
        btnShowLessDescriptionPurchased.setVisibility(View.VISIBLE);
        tvPurchasedCourseDescription.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.btnShowLessDescriptionPurchased)
    public void btnShowLessDescriptionPurchasedClicked(){
        tvPurchasedCourseDescriptionShort.setVisibility(View.VISIBLE);
        btnShowMoreDescriptionPurchased.setVisibility(View.VISIBLE);
        btnShowLessDescriptionPurchased.setVisibility(View.GONE);
        tvPurchasedCourseDescription.setVisibility(View.GONE);
    }


}