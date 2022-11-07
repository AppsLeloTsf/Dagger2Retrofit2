package com.ca_dreamers.cadreamers.adapter.my_books;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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
import com.ca_dreamers.cadreamers.models.my_orders.books.Datum;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.ca_dreamers.cadreamers.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterMyBooks extends RecyclerView.Adapter<AdapterMyBooks.MyOrdersViewHolder> {

    private SharedPrefManager sharedPrefManager;
    private List<Datum> tModels;
    private Context tContext;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public AdapterMyBooks(List<Datum> tModels) {
        this.tModels = tModels;
    }

    @NonNull
    @Override
    public MyOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_orders_books, viewGroup, false);
        sharedPrefManager = new SharedPrefManager(tContext);
        tContext = view.getContext();
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
            myOrdersViewHolder.rbMyOrders.setRating(5);
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

    public static class MyOrdersViewHolder extends RecyclerView.ViewHolder{
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.llMyOrdersBooks)
        protected LinearLayout llMyOrdersBooks;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.ivMyOrdersBooks)
        protected ImageView ivMyOrders;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvMyOrdersBooks)
        protected TextView tvMyOrders;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvMyOrderShortDescBooks)
        protected TextView tvMyOrderShortDesc;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.rbMyOrdersBooks)
        protected RatingBar rbMyOrders;
        public MyOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
