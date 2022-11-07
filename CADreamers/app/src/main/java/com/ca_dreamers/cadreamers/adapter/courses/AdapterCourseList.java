package com.ca_dreamers.cadreamers.adapter.courses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.models.courses.Datum;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterCourseList extends RecyclerView.Adapter<AdapterCourseList.CategoryViewHolder> {

    private final List<Datum> tModels;
    private Context tContext;
    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();


    public AdapterCourseList(List<Datum> tModels) {
        this.tModels = tModels;


    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course, viewGroup, false);
        tContext = view.getContext();
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, final int i) {
        final Datum tModel = tModels.get(i);
        final String strCourseTitle = tModel.getSubCategoryName();


            categoryViewHolder.tvNewsTitle.setText(strCourseTitle);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(categoryViewHolder.rvCourseDetail.getContext());
        layoutManager.setInitialPrefetchItemCount(tModel.getCourses().size());

        AdapterCourseDetails adapterCourseDetails
                = new AdapterCourseDetails(tModel.getCourses(), tContext);
        categoryViewHolder.rvCourseDetail.setLayoutManager(layoutManager);
        categoryViewHolder.rvCourseDetail.setAdapter(adapterCourseDetails);
        categoryViewHolder.rvCourseDetail.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvNewsTitle)
        protected TextView tvNewsTitle;


        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.rvCourseDetail)
        protected RecyclerView rvCourseDetail;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
