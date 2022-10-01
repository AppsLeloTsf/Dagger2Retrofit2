package com.ca_dreamers.cadreamers.adapter.my_orders;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.courses.AdapterCourseList;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.courses.ModelCourse;
import com.ca_dreamers.cadreamers.models.my_orders.books.Datum;
import com.ca_dreamers.cadreamers.models.my_orders.books.books_details_pdf.ModelBooksDetailsPdf;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
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

public class AdapterMyOrdersBooks extends RecyclerView.Adapter<AdapterMyOrdersBooks.MyOrdersViewHolder> {

    private SharedPrefManager sharedPrefManager;
    private List<Datum> tModels;
    private Context tContext;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private String strCatId;

    public AdapterMyOrdersBooks(List<Datum> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
        this.strCatId = strCatId;
    }

    @NonNull
    @Override
    public MyOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_orders_books, viewGroup, false);
        sharedPrefManager = new SharedPrefManager(tContext);
        return new MyOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersViewHolder myOrdersViewHolder, final int i) {
        final Datum tModel = tModels.get(i);
        final String strId = tModel.getId();
        final String strRating = tModel.getRating();

            Glide.with(tContext).load(tModel.getThumbUrl()).into(myOrdersViewHolder.ivMyOrders);
            myOrdersViewHolder.tvMyOrders.setText(tModel.getName());
            myOrdersViewHolder.tvMyOrderShortDesc.setText(tModel.getShortDesc());
            myOrdersViewHolder.rbMyOrders.setRating(Float.parseFloat(strRating));
            myOrdersViewHolder.llMyOrdersBooks.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.BOOKS_ID, tModel.getId());
                    bundle.putString(Constant.BOOKS_IMAGE, tModel.getThumbUrl());
                    bundle.putString(Constant.BOOKS_TITLE, tModel.getName());
                    bundle.putString(Constant.BOOKS_DESCRIPTION, tModel.getShortDesc());
                    bundle.putString(Constant.BOOKS_RATING, tModel.getRating());
                    Navigation.findNavController(myOrdersViewHolder.itemView).navigate(R.id.nav_purchased_Books_Details, bundle);
                }
            });
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class MyOrdersViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.llMyOrdersBooks)
        protected LinearLayout llMyOrdersBooks;
        @BindView(R.id.ivMyOrdersBooks)
        protected ImageView ivMyOrders;
        @BindView(R.id.tvMyOrdersBooks)
        protected TextView tvMyOrders;
        @BindView(R.id.tvMyOrderShortDescBooks)
        protected TextView tvMyOrderShortDesc;
        @BindView(R.id.rbMyOrdersBooks)
        protected RatingBar rbMyOrders;
        public MyOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
