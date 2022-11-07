package com.indianjourno.indianjourno.adapter.ij.news;

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
import com.indianjourno.indianjourno.activity.ij.stories_news.StoriesNewsDetailActivity;
import com.indianjourno.indianjourno.model.ij_news.ModelNewsByCatId;
import com.indianjourno.indianjourno.model.ij_news.ModelStoriesNews;
import com.indianjourno.indianjourno.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;

public class AdapterNewsByCatDetails extends RecyclerView.Adapter<AdapterNewsByCatDetails.CategoryViewHolder> {

    private Context tContext;
    private List<ModelNewsByCatId> tModels;

    public AdapterNewsByCatDetails(List<ModelNewsByCatId> tModels) {
        this.tModels = tModels;
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
        final ModelNewsByCatId tModel = tModels.get(i);
        final String strCatId = tModel.getCategory();
        final String strNewsId = tModel.getNewsId();
        final String strNewsTitle = tModel.getNewsTitle();
        final String strNewsDate = tModel.getNewsDate();
        final String strNewsContent = tModel.getNewsContent();
        final String strImgUrl = Constant.IMAGE_NEWS_IJ+tModel.getPhoto();

            categoryViewHolder.tvNewsTitleDetail.setText(strNewsTitle);
        Glide.with(tContext)
                .load(strImgUrl)
                .skipMemoryCache(true)
                .into(categoryViewHolder.ivNewsImageDetail);
            categoryViewHolder.llFragMainItemDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent tIntent = new Intent(tContext, StoriesNewsDetailActivity.class);
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
