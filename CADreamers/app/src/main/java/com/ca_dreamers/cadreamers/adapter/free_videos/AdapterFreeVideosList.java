package com.ca_dreamers.cadreamers.adapter.free_videos;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.models.free_videos.Datum;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterFreeVideosList extends RecyclerView.Adapter<AdapterFreeVideosList.CategoryViewHolder> {

    private final List<Datum> tModels;
    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public AdapterFreeVideosList(List<Datum> tModels) {
        this.tModels = tModels;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_free_videos_list, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, final int i) {
        final Datum tModel = tModels.get(i);
        final String strCourseTitle = tModel.getSubCategoryName();


            categoryViewHolder.tvFreeVideos.setText(strCourseTitle);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                        categoryViewHolder.rvFreeVideosDetails.getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setInitialPrefetchItemCount(tModel.getFreeVideos().size());


        AdapterFreeVideosDetails adapterCourseDetails
                = new AdapterFreeVideosDetails(tModel.getFreeVideos());
        categoryViewHolder.rvFreeVideosDetails.setLayoutManager(layoutManager);
        categoryViewHolder.rvFreeVideosDetails.setAdapter(adapterCourseDetails);
        categoryViewHolder.rvFreeVideosDetails.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvFreeVideosList)
        protected TextView tvFreeVideos;


        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.rvFreeVideosDetails)
        protected RecyclerView rvFreeVideosDetails;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
