package com.indianjourno.indianjourno.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.indianjourno.indianjourno.activity.VideoActivity;
import com.indianjourno.indianjourno.model.ij_video.ModelVideo;
import com.indianjourno.indianjourno.model.ij_video.Video;

import com.indianjourno.indianjourno.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;

public class AdapterVideoListRelated extends RecyclerView.Adapter<AdapterVideoListRelated.CategoryViewHolder> {

    private Context tContext;
    private List<Video> tModels;
    private String strCatId;

    public AdapterVideoListRelated(List<Video> tModels) {
        this.tModels = tModels;
        this.strCatId = strCatId;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_news_item_detail, viewGroup, false);
        tContext = view.getContext();
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, final int i) {
        final Video tModel = tModels.get(i);
        final String strVideoId = tModel.getVideoId();
        final String strVideoTitle = tModel.getVideoTitle();
        final String strVideoUrl = tModel.getVideoLink();
        final String strVideoImage = Constant.IMAGE_VIDEO_IJ+tModel.getVideoThumbnail();

            categoryViewHolder.tvNewsTitleDetail.setText(strVideoTitle);
        Glide.with(tContext)
                .load(strVideoImage)
                .skipMemoryCache(true)
                .into(categoryViewHolder.ivNewsImageDetail);
            categoryViewHolder.llFragMainItemDetail.setOnClickListener(v -> {
                Intent tIntent = new Intent(tContext, VideoActivity.class);
                tIntent.putExtra(Constant.CAT_ID, strCatId);
                tIntent.putExtra(Constant.VIDEO_ID, strVideoId);
                tIntent.putExtra(Constant.VIDEO_TITLE, strVideoTitle);
                tIntent.putExtra(Constant.VIDEO_URL, strVideoUrl);
                tIntent.putExtra(Constant.VIDEO_IMAGE, strVideoImage);
                tContext.startActivity(tIntent);
            });

    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvNewsTitleDetail)
        protected TextView tvNewsTitleDetail;
        @BindView(R.id.ivNewsImageDetail)
        protected ImageView ivNewsImageDetail;
        @BindView(R.id.rlFragMainItemDetail)
        protected RelativeLayout llFragMainItemDetail;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
