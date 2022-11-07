package com.ca_dreamers.cadreamers.adapter.my_course;

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
import com.ca_dreamers.cadreamers.models.my_orders.Datum;
import com.ca_dreamers.cadreamers.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterMyCourses extends RecyclerView.Adapter<AdapterMyCourses.MyOrdersViewHolder> {

    private final List<Datum> tModels;
    private final Context tContext;

    public AdapterMyCourses(List<Datum> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public MyOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_orders, viewGroup, false);
        return new MyOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersViewHolder myOrdersViewHolder, final int i) {
        final Datum tModel = tModels.get(i);

            Glide.with(tContext).load(tModel.getThumbUrl()).into(myOrdersViewHolder.ivMyOrders);
            myOrdersViewHolder.tvMyOrders.setText(tModel.getName());
            myOrdersViewHolder.tvMyOrderShortDesc.setText(tModel.getShortDesc());
            myOrdersViewHolder.rbMyOrders.setRating(5);
        myOrdersViewHolder.llMyOrdersCourse.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.COURSE_ID, tModel.getId());
            bundle.putString(Constant.COURSE_IMAGE, tModel.getImage());
            bundle.putString(Constant.COURSE_TITLE, tModel.getName());
            bundle.putString(Constant.COURSE_DESCRIPTION, tModel.getShortDesc());
            Navigation.findNavController(myOrdersViewHolder.itemView).navigate(R.id.nav_purchased_course_detail, bundle);
        });

    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public static class MyOrdersViewHolder extends RecyclerView.ViewHolder{
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.llMyOrdersCourse)
        protected LinearLayout llMyOrdersCourse;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.ivMyOrders)
        protected ImageView ivMyOrders;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvMyOrders)
        protected TextView tvMyOrders;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvMyOrderShortDesc)
        protected TextView tvMyOrderShortDesc;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.rbMyOrders)
        protected RatingBar rbMyOrders;
        public MyOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
