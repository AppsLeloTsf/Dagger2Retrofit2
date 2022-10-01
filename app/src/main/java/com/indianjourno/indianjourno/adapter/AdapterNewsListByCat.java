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

import com.indianjourno.indianjourno.activity.NewsDetailCategoryActivity;
import com.indianjourno.indianjourno.model.category.NewsCategory;
import com.indianjourno.indianjourno.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;

public class AdapterNewsListByCat extends RecyclerView.Adapter<AdapterNewsListByCat.CategoryViewHolder> {

    private Context tContext;
    private List<NewsCategory> tModelAllNews;
    private String strCatId;

    public AdapterNewsListByCat(List<NewsCategory> tModelAllNews, String strCatId) {
        this.tModelAllNews = tModelAllNews;
        this.strCatId = strCatId;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_news_item, viewGroup, false);
        tContext = view.getContext();
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, final int i) {
        final NewsCategory tModelNewsCategory = tModelAllNews.get(i);
        final String strNewsId = tModelNewsCategory.getNewsId();
        final String strNewsTitle = tModelNewsCategory.getNewsTitle();
        final String strNewsDate = tModelNewsCategory.getNewsSchedule();
        final String strNewsContent = tModelNewsCategory.getNewsContent();
        final String strImgUrl = tModelNewsCategory.getNewsPhoto();
            categoryViewHolder.tvNewsTitle.setText(strNewsTitle);
        Glide.with(tContext)
                .load(Constant.IMG_URL+strImgUrl)
                .skipMemoryCache(true)
                .into(categoryViewHolder.ivNewsImage);
            categoryViewHolder.llFragMainItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent tIntent = new Intent(tContext, NewsDetailCategoryActivity.class);
                    tIntent.putExtra(Constant.NEWS_ID, strNewsId);
                    tIntent.putExtra(Constant.CAT_ID, strCatId);
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
        @BindView(R.id.tvNewsTitle)
        protected TextView tvNewsTitle;
        @BindView(R.id.ivNewsImage)
        protected ImageView ivNewsImage;
        @BindView(R.id.llFragMainItem)
        protected LinearLayout llFragMainItem;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
