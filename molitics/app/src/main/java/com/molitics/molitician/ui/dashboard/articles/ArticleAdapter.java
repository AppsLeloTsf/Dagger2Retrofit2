package com.molitics.molitician.ui.dashboard.articles;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.model.News;
import com.molitics.molitician.ui.dashboard.channels.ChannelBankActivity;
import com.molitics.molitician.ui.dashboard.nationalNews.adapter.NewsAdapter;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.OnNewsItemClick;
import com.molitics.molitician.util.Constant;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 7/14/2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<News> articleItemList = new ArrayList<>();
    private Context mContext;
    private OnNewsItemClick onNewsItemClick;
    private OnLoadMoreResult onLoadMoreResult;

    public ArticleAdapter(Context context, OnNewsItemClick onNewsItemClick, OnLoadMoreResult onLoadMoreResult) {
        this.mContext = context;
        this.onNewsItemClick = onNewsItemClick;
        this.onLoadMoreResult = onLoadMoreResult;
    }

    public void addLocalNews(ArrayList<News> articleItemList) {
        this.articleItemList.addAll(articleItemList);
        notifyDataSetChanged();
    }

    public int getArticleListSize() {
        return articleItemList.size();
    }

    public ArrayList<News> getArticleList() {
        return articleItemList;
    }

    public interface OnLoadMoreResult {
        public void onLoadMore(int totalItem);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int viewType) {
        final View mView;
        mView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_news_vertical, viewGroup, false);
        return new NewsViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder.getItemViewType() == Constant.Condition.VERTICAL_NEWS) {

            final News newsItem = articleItemList.get(position);
            final NewsViewHolder holder = (NewsViewHolder) viewHolder;
            holder.news_headline.setText(Html.fromHtml(newsItem.getHeading()));
            holder.sponser_news_post_time.setText(newsItem.getTime());
            holder.channelNameView.setText(newsItem.getSource());
            holder.channelNameView.setTag(newsItem);
            if (!TextUtils.isEmpty(newsItem.getSourceLogo())) {
                Picasso.with(mContext).load(newsItem.getSourceLogo()).into(holder.channelImageView);
            }

            ///holder.article_tag_view.setVisibility(View.VISIBLE);
            holder.main_constraintLayout.setVisibility(View.VISIBLE);
            if (position >= 58 && position == (articleItemList.size() - 1)) {
                onLoadMoreResult.onLoadMore(articleItemList.size());
            }

            if (newsItem.getImage() != null && !TextUtils.isEmpty(newsItem.getImage())) {
                Picasso.with(mContext).load(newsItem.getImage()).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.image_placeholder).error(R.drawable.error_placeholder).into(holder.news_image, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        Picasso.with(mContext).load(newsItem.getImage()).placeholder(R.drawable.image_placeholder).error(R.drawable.error_placeholder).into(holder.news_image);
                    }
                });
            }
            holder.main_constraintLayout.setOnClickListener(v -> onNewsItemClick.onNewsRecyclerClick(position, Constant.Condition.VERTICAL_NEWS));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return Constant.Condition.VERTICAL_NEWS;
    }

    @Override
    public int getItemCount() {
        return articleItemList.size();
    }

    public class NewsLeadersViewHolder extends NewsAdapter.ViewHolder {
        @BindView(R.id.news_hor_recycler_view)
        RecyclerView news_hor_recycler_view;
        @BindView(R.id.ll_international)
        LinearLayout ll_international;

        public NewsLeadersViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.main_constraintLayout)
        public ConstraintLayout main_constraintLayout;
        @BindView(R.id.news_image)
        public ImageView news_image;
        @BindView(R.id.news_headline)
        public TextView news_headline;
        @BindView(R.id.sponser_news_post_time)
        public TextView sponser_news_post_time;
        @BindView(R.id.news_vi_view)
        public RelativeLayout news_vi_view;
        @BindView(R.id.article_tag_view)
        public ImageView article_tag_view;
        @BindView(R.id.channelNameView)
        public TextView channelNameView;
        @BindView(R.id.channelImageView)
        public ImageView channelImageView;

        public NewsViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            channelNameView.setOnClickListener((view) -> {
                News details = (News) channelNameView.getTag();
                if (!details.getSource().contains(Constant.MOLITICS))
                    openChannelPage(channelImageView.getContext(), details);
            });
            channelImageView.setOnClickListener(view -> {
                News details = (News) channelNameView.getTag();
                if (!details.getSource().contains(Constant.MOLITICS))
                    openChannelPage(channelImageView.getContext(), details);
            });
        }

        private void openChannelPage(Context mContext, News details) {
            Intent intent = new Intent(mContext, ChannelBankActivity.class);
            intent.putExtra(Constant.IntentKey.CHANNEL_ID, details.getSourceId());
            intent.putExtra(Constant.IntentKey.SOURCE, details.getSource());
            intent.putExtra(Constant.IntentKey.SOURCE_IMAGE, details.getSourceLogo());
            mContext.startActivity(intent);
        }
    }
}
