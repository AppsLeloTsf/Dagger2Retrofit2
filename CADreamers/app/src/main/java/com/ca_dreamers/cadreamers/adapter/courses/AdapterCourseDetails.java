package com.ca_dreamers.cadreamers.adapter.courses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.models.courses.Course;
import com.ca_dreamers.cadreamers.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class AdapterCourseDetails extends RecyclerView.Adapter<AdapterCourseDetails.CourseDetailsViewHolder> {

    private final Context tContext;
    private final List<Course> tModels;

    public AdapterCourseDetails(List<Course> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public CourseDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course_details, viewGroup, false);

        return new CourseDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseDetailsViewHolder courseDetailsViewHolder, final int i) {
        final Course tModel = tModels.get(i);
        final String strCourseTitle = tModel.getName();
        final String strImage = tModel.getImage();


            courseDetailsViewHolder.tvCourseDetailTitle.setText(strCourseTitle);
        Glide.with(tContext)
                .load(strImage)
                .into(courseDetailsViewHolder.ivCourseDetails);
            courseDetailsViewHolder.llCourseDetail.setOnClickListener(v -> {

                Bundle bundle = new Bundle();
                bundle.putString(Constant.COURSE_ID, tModel.getId());
                bundle.putString(Constant.COURSE_IMAGE, tModel.getImage());
                bundle.putString(Constant.COURSE_TITLE, tModel.getName());
                bundle.putString(Constant.COURSE_DESCRIPTION, tModel.getShortDesc());
                bundle.putString(Constant.COURSE_PRICE, tModel.getPrice());
                bundle.putString(Constant.COURSE_MRP, tModel.getMrp());
                bundle.putString(Constant.COURSE_DISCOUNT, tModel.getDiscount());
                bundle.putString(Constant.COURSE_PURCHASED, tModel.getPurchaseStatus());
                Navigation.findNavController(courseDetailsViewHolder.itemView).navigate(R.id.nav_course_detail, bundle);
            });

    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public static class CourseDetailsViewHolder extends RecyclerView.ViewHolder{
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvCourseDetailTitle)
        protected TextView tvCourseDetailTitle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.ivCourseDetails)
        protected ImageView ivCourseDetails;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.llCourseDetail)
        protected RelativeLayout llCourseDetail;
        public CourseDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
