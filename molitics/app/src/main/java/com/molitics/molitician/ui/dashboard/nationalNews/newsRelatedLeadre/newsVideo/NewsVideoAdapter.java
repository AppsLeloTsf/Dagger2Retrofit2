package com.molitics.molitician.ui.dashboard.nationalNews.newsRelatedLeadre.newsVideo;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.molitics.molitician.BuildConfig;
import com.molitics.molitician.MolticsApplication;
import com.molitics.molitician.R;
import com.molitics.molitician.model.NewsVideoModel;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.OnNewsItemClick;
import com.molitics.molitician.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 01/11/17.
 */

public class NewsVideoAdapter extends RecyclerView.Adapter<NewsVideoAdapter.NewsVideoViewHolder> {

    private List<NewsVideoModel> newsVideoModelList = new ArrayList<>();
    private OnNewsItemClick onNewsItemClick;
    private Map<YouTubeThumbnailView, YouTubeThumbnailLoader> thumbnailViewToLoaderMap;
    private ThumbnailListener thumbnailListener;

    public NewsVideoAdapter(OnNewsItemClick onNewsItemClick) {
        this.onNewsItemClick = onNewsItemClick;
        thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();
        thumbnailListener = new ThumbnailListener();
    }

    public void addData(List<NewsVideoModel> newsVideoModelList) {
        this.newsVideoModelList.addAll(newsVideoModelList);
        notifyDataSetChanged();

    }

    public void clearList() {
        newsVideoModelList.clear();
    }

    @Override
    public NewsVideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_news_video, parent, false);
        return new NewsVideoViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(NewsVideoViewHolder holder, final int position) {

        if (newsVideoModelList != null) {
            final NewsVideoModel newsVideoModel = newsVideoModelList.get(position);
            holder.news_headline.setText(newsVideoModel.getSource());
            holder.thumbnail.setVisibility(View.VISIBLE);
            holder.thumbnail.setTag(newsVideoModel.getVideoLink());

            if (!TextUtils.isEmpty(newsVideoModel.getSourceLogo())) {
                Picasso.with(holder.channelLogoView.getContext()).load(newsVideoModel.getSourceLogo()).into(holder.channelLogoView);
            }
            YouTubeThumbnailLoader loader = thumbnailViewToLoaderMap.get(holder.thumbnail);
            if (loader == null) {
                holder.thumbnail.setTag(newsVideoModel.getVideoLink());
            } else {
                holder.thumbnail.setImageResource(R.drawable.video_loading_placeholder);
                try {
                    loader.setVideo(newsVideoModel.getVideoLink());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            holder.ll_main.setOnClickListener(v -> onNewsItemClick.onVideoClick(position, newsVideoModel.getVideoLink()));
        }
    }

    @Override
    public int getItemCount() {
        return newsVideoModelList.size();
    }

    public class NewsVideoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_main)
        ConstraintLayout ll_main;
        @BindView(R.id.thumbnail)
        YouTubeThumbnailView thumbnail;
        @BindView(R.id.news_headline)
        TextView news_headline;
        @BindView(R.id.news_vi_view)
        RelativeLayout news_vi_view;
        @BindView(R.id.channelLogoView)
        ImageView channelLogoView;


        public NewsVideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            thumbnail.initialize(MolticsApplication.YOUTUBE_KEY, new ThumbnailListener());
        }
    }

    public void releaseLoaders() {
        for (YouTubeThumbnailLoader loader : thumbnailViewToLoaderMap.values()) {
            if (loader != null)
                loader.release();
        }
    }

    private final class ThumbnailListener implements
            YouTubeThumbnailView.OnInitializedListener,
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onInitializationSuccess(
                YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
            loader.setOnThumbnailLoadedListener(this);
            thumbnailViewToLoaderMap.put(view, loader);
            view.setImageResource(R.drawable.youtube_icon_with_background);
            String videoId = (String) view.getTag();
            if (videoId != null && !videoId.isEmpty())
                loader.setVideo(videoId);
        }

        @Override
        public void onInitializationFailure(
                YouTubeThumbnailView view, YouTubeInitializationResult loader) {

        }

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView view, String videoId) {
        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView view, YouTubeThumbnailLoader.ErrorReason errorReason) {
            if (view != null)
                view.setImageResource(R.drawable.video_error_placeholder);
        }
    }
}
