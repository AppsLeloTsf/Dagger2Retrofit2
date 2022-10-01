package com.molitics.molitician.ui.dashboard.nationalNews.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.model.ArticleModel;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.OnNewsItemClick;
import com.molitics.molitician.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 02/11/17.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private OnNewsItemClick onNewsItemClick;
    private List<ArticleModel> articleModelList;
    private Context mContext;

    public ArticleAdapter(Context mContext, List<ArticleModel> articleModelList, OnNewsItemClick onNewsItemClick) {
        this.articleModelList = articleModelList;
        this.onNewsItemClick = onNewsItemClick;
        this.mContext = mContext;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_article_layout, parent, false);
        return new ArticleViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, final int position) {

        ArticleModel model = articleModelList.get(position);
        holder.article_heading_txt.setText(model.getHeading());
        holder.article_txt.setText(Html.fromHtml(model.getContent()));
        holder.article_time.setText(model.getTime());
        holder.ll_main.setOnClickListener(v -> onNewsItemClick.onNewsRecyclerClick(position, Constant.Condition.ARTICLE));

        if (!TextUtils.isEmpty(model.getImage())) {
            Picasso.with(mContext).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(holder.article_image);
        }

    }

    @Override
    public int getItemCount() {
        return articleModelList.size();
    }

     class ArticleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.article_image)
        ImageView article_image;
        @BindView(R.id.article_txt)
        TextView article_txt;
        @BindView(R.id.article_heading_txt)
        TextView article_heading_txt;
        @BindView(R.id.ll_main)
        LinearLayout ll_main;
        @BindView(R.id.article_time)
        TextView article_time;

         ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
