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

import com.indianjourno.indianjourno.activity.NewsDetailCategoryActivity;
import com.indianjourno.indianjourno.model.category.NewsCategory;
import com.indianjourno.indianjourno.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;

public class AdapterNewsListByCatDetail extends RecyclerView.Adapter<AdapterNewsListByCatDetail.CategoryViewHolder> {

    private Context tContext;
    private List<NewsCategory> tModels;
    private String strCatId;

    public AdapterNewsListByCatDetail(List<NewsCategory> tModels, String strCatId) {
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
        final NewsCategory tModel = tModels.get(i);
        final String strNewsId = tModel.getNewsId();
        final String strNewsTitle = tModel.getNewsTitle();
        final String strNewsDate = tModel.getNewsSchedule();
        final String strNewsContent = tModel.getNewsContent();
        final String strImgUrl = tModel.getNewsPhoto();

            categoryViewHolder.tvNewsTitleDetail.setText(strNewsTitle);
        Glide.with(tContext)
                .load(Constant.IMG_URL+strImgUrl)
                .skipMemoryCache(true)
                .into(categoryViewHolder.ivNewsImageDetail);
            categoryViewHolder.llFragMainItemDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent tIntent = new Intent(tContext, NewsDetailCategoryActivity.class);
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
