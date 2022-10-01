package com.ca_dreamers.cadreamers.adapter.address;

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
import com.ca_dreamers.cadreamers.models.books.Course;
import com.ca_dreamers.cadreamers.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AdapterAdressFetch extends RecyclerView.Adapter<AdapterAdressFetch.CourseDetailsViewHolder> {

    private Context tContext;
    private List<Course> tModels;
    private String strCatId;

    public AdapterAdressFetch(List<Course> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
        this.strCatId = strCatId;
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
                bundle.putString(Constant.COURSE_ID, tModel.getId());
                bundle.putString(Constant.COURSE_IMAGE, tModel.getImage());
                bundle.putString(Constant.COURSE_TITLE, tModel.getName());
                bundle.putString(Constant.COURSE_DESCRIPTION, tModel.getShortDesc());
                bundle.putString(Constant.COURSE_DESCRIPTION_FULL, tModel.getDescription());
                bundle.putString(Constant.COURSE_PRICE, tModel.getPrice());
                bundle.putString(Constant.COURSE_MRP, tModel.getMrp());
                bundle.putString(Constant.COURSE_DISCOUNT, tModel.getDiscount());
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
}
