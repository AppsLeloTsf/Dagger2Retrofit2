package com.molitics.molitician.ui.dashboard.video;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.molitics.molitician.BuildConfig;
import com.molitics.molitician.MolticsApplication;
import com.molitics.molitician.R;
import com.molitics.molitician.model.NewsVideoModel;
import com.molitics.molitician.util.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 07/11/17.
 */

public class AllVideoAdapter extends RecyclerView.Adapter<AllVideoAdapter.VideoViewHolder> {

    List<NewsVideoModel> newsVideoModelList = new ArrayList<>();
    private Map<YouTubeThumbnailView, YouTubeThumbnailLoader> thumbnailViewToLoaderMap;
    private ThumbnailListener thumbnailListener;
    OnLoadMoreResult onLoadMoreResult;

    public AllVideoAdapter(OnLoadMoreResult onLoadMoreResult) {
        thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();
        thumbnailListener = new ThumbnailListener();
        this.onLoadMoreResult = onLoadMoreResult;
    }


    public void addVideoList(List<NewsVideoModel> newsVideoModelList) {
        this.newsVideoModelList.addAll(newsVideoModelList);
        notifyDataSetChanged();
    }

    public void clearList() {
        this.newsVideoModelList.clear();
        notifyDataSetChanged();
    }


    public int getVideoListSize() {
        return newsVideoModelList.size();
    }

    public List<NewsVideoModel> getVideoList() {
        return newsVideoModelList;
    }

    public interface OnLoadMoreResult {
        void onLoadMore(int totalItem);

        void onVideoClick(int position);

        void playVideo(String video_link);
    }


    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_all_news_video, parent, false);
        return new VideoViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, final int position) {

        if (newsVideoModelList != null) {
            final NewsVideoModel newsVideoModel = newsVideoModelList.get(position);
            holder.news_headline.setText(newsVideoModel.getHeading());

            holder.thumbnail.setVisibility(View.VISIBLE);
            holder.thumbnail.setTag(newsVideoModel.getVideoLink());
            YouTubeThumbnailLoader loader = thumbnailViewToLoaderMap.get(holder.thumbnail);
            if (loader == null) {
                // 2) The view is already created, and is currently being initialized. We store the
                //    current videoId in the tag.
                holder.thumbnail.setTag(newsVideoModel.getVideoLink());
            } else {
                // 3) The view is already created and already initialized. Simply set the right videoId
                //    on the loader.
                holder.thumbnail.setImageResource(R.drawable.loading_thumbnail);
                loader.setVideo(newsVideoModel.getVideoLink());
            }
            holder.news_vi_view.setOnClickListener(v -> onLoadMoreResult.playVideo(newsVideoModel.getVideoLink()));
            if (newsVideoModel.getSurveyId() != null) {
                holder.news_survey_view.setVisibility(View.VISIBLE);
            } else {
                holder.news_survey_view.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(newsVideoModel.getTime())) {
                holder.rl_time.setVisibility(View.VISIBLE);
                holder.sponser_news_post_time.setText(newsVideoModel.getTime());
            }
            holder.ll_main.setOnClickListener(v -> onLoadMoreResult.onVideoClick(position));

            if (position >= 9 && position == (newsVideoModelList.size() - 1)) {
                onLoadMoreResult.onLoadMore(newsVideoModelList.size());
            }
        }
    }

    @Override
    public int getItemCount() {
        return newsVideoModelList.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_main)
        LinearLayout ll_main;
        @BindView(R.id.thumbnail)
        YouTubeThumbnailView thumbnail;
        @BindView(R.id.news_tile)
        TextView news_tile;
        @BindView(R.id.news_headline)
        TextView news_headline;
        @BindView(R.id.sponser_news_post_time)
        TextView sponser_news_post_time;
        @BindView(R.id.news_survey_view)
        TextView news_survey_view;
        @BindView(R.id.news_vi_view)
        RelativeLayout news_vi_view;
        @BindView(R.id.rl_time)
        RelativeLayout rl_time;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            thumbnail.initialize(MolticsApplication.YOUTUBE_KEY, new ThumbnailListener());

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
            view.setImageResource(R.drawable.video_placeholder);
            String videoId = (String) view.getTag();
            if (videoId != null && !videoId.isEmpty())
                loader.setVideo(videoId);
            //      releaseLoaders();
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

    public void releaseLoaders() {
        for (YouTubeThumbnailLoader loader : thumbnailViewToLoaderMap.values()) {
            if (loader != null)
                loader.release();
        }
    }
}
