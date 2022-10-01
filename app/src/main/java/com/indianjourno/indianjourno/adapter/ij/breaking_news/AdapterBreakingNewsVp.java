package com.indianjourno.indianjourno.adapter.ij.news;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.indianjourno.indianjourno.model.ij_news.all_news.News;
import com.indianjourno.indianjourno.model.ij_video.Video;
import com.indianjourno.indianjourno.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;

public class AdapterAllNewsVp extends RecyclerView.Adapter<AdapterAllNewsVp.ViewHolder> {

    private Context ctx;
    private List<News> tModels;


    public AdapterAllNewsVp(Context ctx, List<News> tModels) {
        this.ctx = ctx;
        this.tModels = tModels;
    }

    // This method returns our layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_all_news_vp, parent, false);
        return new ViewHolder(view);
    }

    // This method binds the screen with the view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // This will set the images in imageview
        News tModel = tModels.get(position);
        String strImg = Constant.IMAGE_NEWS_IJ+tModel.getPhoto();

        holder.tvAllNewsTitle.setText(tModel.getNewsTitle());
        holder.tvAllNewsDate.setText(tModel.getNewsDate());
        Spanned htmlAsSpanned = Html.fromHtml(tModel.getNewsContent());
        holder.tvAllNewsDetail.setText(htmlAsSpanned);
        Glide.with(ctx)
                .load(strImg)
                .skipMemoryCache(true)
                .into(holder.ivAllNewsHeader);


    }

    // This Method returns the size of the Array
    @Override
    public int getItemCount() {
        return tModels.size();
    }

    // The ViewHolder class holds the view
    public static class ViewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.ivAllNewsHeader)
        protected ImageView ivAllNewsHeader;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvAllNewsTitle)
        protected TextView tvAllNewsTitle;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvAllNewsDate)
        protected TextView tvAllNewsDate;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvAllNewsDetail)
        protected TextView tvAllNewsDetail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
