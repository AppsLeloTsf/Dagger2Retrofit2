package com.ca_dreamers.cadreamers.adapter.books;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.books.Course;
import com.ca_dreamers.cadreamers.models.books.product_type.ModelBookMode;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdapterBooksDetails extends RecyclerView.Adapter<AdapterBooksDetails.CourseDetailsViewHolder> {

    private Context tContext;
    private List<Course> tModels;
    private String strCatId;

    public AdapterBooksDetails(List<Course> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public CourseDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_books_details, viewGroup, false);

        return new CourseDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseDetailsViewHolder courseDetailsViewHolder, final int i) {
        final Course tModel = tModels.get(i);
        final String strCourseId = tModel.getId();
        final String strCourseTitle = tModel.getName();
        final String strImage = tModel.getImage();


            courseDetailsViewHolder.tvBooksDetailTitle.setText(strCourseTitle);
        Glide.with(tContext)
                .load(strImage)
                .into(courseDetailsViewHolder.ivBooksDetails);
            courseDetailsViewHolder.rlBooksDetail.setOnClickListener(v -> {

                Bundle bundle = new Bundle();
                bundle.putString(Constant.BOOKS_ID, tModel.getId());
                bundle.putString(Constant.BOOKS_IMAGE, tModel.getImage());
                bundle.putString(Constant.BOOKS_TITLE, tModel.getName());
                bundle.putString(Constant.BOOKS_DESCRIPTION, tModel.getShortDesc());
                bundle.putString(Constant.BOOKS_DESCRIPTION_FULL, tModel.getDescription());
                bundle.putString(Constant.BOOKS_PRICE, tModel.getPrice());
                bundle.putString(Constant.BOOKS_MRP, tModel.getMrp());
                bundle.putString(Constant.BOOKS_DISCOUNT, tModel.getDiscount());
                bundle.putString(Constant.BOOKS_PURCHASED, tModel.getPurchaseStatus());
                Navigation.findNavController(courseDetailsViewHolder.itemView).navigate(R.id.nav_books_detail, bundle);
            });

    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class CourseDetailsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvBooksDetailTitle)
        protected TextView tvBooksDetailTitle;
        @BindView(R.id.ivBooksDetails)
        protected ImageView ivBooksDetails;
        @BindView(R.id.rlBooksDetail)
        protected LinearLayout rlBooksDetail;
        public CourseDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    private void callProductTypeApi(String strCourseId){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("product_id", strCourseId);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelBookMode> call = api.getBooksMode(gsonObject);
            call.enqueue(new Callback<ModelBookMode>() {
                @Override
                public void onResponse(Call<ModelBookMode> call, Response<ModelBookMode> response) {
                    ModelBookMode modelBookMode = response.body();

                }

                @Override
                public void onFailure(Call<ModelBookMode> call, Throwable t) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
