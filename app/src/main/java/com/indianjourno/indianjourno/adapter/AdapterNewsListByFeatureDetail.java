package com.indianjourno.indianjourno.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.indianjourno.indianjourno.activity.NewsDetailFeatureActivity;
import com.indianjourno.indianjourno.model.feature.NewsFeature;
import com.indianjourno.indianjourno.utils.Constant;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;

public class AdapterNewsListByFeatureDetail extends RecyclerView.Adapter<AdapterNewsListByFeatureDetail.CategoryViewHolder> {

    private Context tContext;
    private List<NewsFeature> tModelAllNews;
    private String strCatId;

    public AdapterNewsListByFeatureDetail(List<NewsFeature> tModelAllNews, String strCatId) {
        this.tModelAllNews = tModelAllNews;
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
        final NewsFeature tModelNewsFeature = tModelAllNews.get(i);
        final String strNewsId = tModelNewsFeature.getNewsId();
        final String strNewsTitle = tModelNewsFeature.getNewsTitle();
        final String strNewsDate = tModelNewsFeature.getNewsSchedule();
        final String strNewsContent = tModelNewsFeature.getNewsContent();
        final String strImgUrl = tModelNewsFeature.getNewsPhoto();

            categoryViewHolder.tvNewsTitleDetail.setText(strNewsTitle);
        Glide.with(tContext)
                .load(Constant.IMG_URL+strImgUrl)
                .skipMemoryCache(true)
                .into(categoryViewHolder.ivNewsImageDetail);
            categoryViewHolder.llFragMainItemDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent tIntent = new Intent(tContext, NewsDetailFeatureActivity.class);
                    tIntent.putExtra(Constant.CAT_ID, strCatId);
                    tIntent.putExtra(Constant.NEWS_ID, strNewsId);
                    tIntent.putExtra(Constant.NEWS_TITLE, strNewsTitle);
                    tIntent.putExtra(Constant.NEWS_DATE, strNewsDate);
                    tIntent.putExtra(Constant.NEWS_CONTENT, strNewsContent);
                    tIntent.putExtra(Constant.NEWS_IMAGE, strImgUrl);
                    tContext.startActivity(tIntent);
                }
            });

    }

    @Override
    public int getItemCount() {
        return tModelAllNews.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvNewsTitleDetail)
        protected TextView tvNewsTitleDetail;
        @BindView(R.id.ivNewsImageDetail)
        protected ImageView ivNewsImageDetail;
        @BindView(R.id.llFragMainItemDetail)
        protected LinearLayout llFragMainItemDetail;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
