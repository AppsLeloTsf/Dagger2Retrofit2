package com.indianjourno.indianjourno.adapter.ij.breaking_news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.indianjourno.indianjourno.activity.ij.breaking_news.BreakingNewsDetailActivity;
import com.indianjourno.indianjourno.model.ij_news.ModelBreakingNew;
import com.indianjourno.indianjourno.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;

public class AdapterBreakingNews extends RecyclerView.Adapter<AdapterBreakingNews.CategoryViewHolder> {

    private Context tContext;
    private final List<ModelBreakingNew> tModels;

    public AdapterBreakingNews(List<ModelBreakingNew> tModels) {
        this.tModels = tModels;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_breaking_news, viewGroup, false);
        tContext = (Activity)view.getContext();
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, final int i) {
        final ModelBreakingNew tModel = tModels.get(i);
        final String strCatId = tModel.getCategory();
        final String strNewsId = tModel.getNewsId();
        final String strNewsTitle = tModel.getNewsTitle();
        final String strNewsDate = tModel.getNewsDate();
        final String strNewsContent = tModel.getNewsContent();
        final String strImgUrl = Constant.IMAGE_NEWS_IJ+tModel.getPhoto();

            categoryViewHolder.tvBreakingNewsTitle.setText(strNewsTitle);
        Glide.with(tContext)
                .load(strImgUrl)
                .skipMemoryCache(true)
                .into(categoryViewHolder.ivBreakingNewsImage);
            categoryViewHolder.rlBreakingNews.setOnClickListener(v -> {
                Intent tIntent = new Intent(tContext, BreakingNewsDetailActivity.class);
                tIntent.putExtra(Constant.CAT_ID, strCatId);
                tIntent.putExtra(Constant.NEWS_ID, strNewsId);
                tIntent.putExtra(Constant.NEWS_TITLE, strNewsTitle);
                tIntent.putExtra(Constant.NEWS_DATE, strNewsDate);
                tIntent.putExtra(Constant.NEWS_CONTENT, strNewsContent);
                tIntent.putExtra(Constant.NEWS_IMAGE, strImgUrl);
                tContext.startActivity(tIntent);
            });

    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvBreakingNewsTitle)
        protected TextView tvBreakingNewsTitle;
        @BindView(R.id.ivBreakingNewsImage)
        protected ImageView ivBreakingNewsImage;
        @BindView(R.id.rlBreakingNews)
        protected RelativeLayout rlBreakingNews;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
