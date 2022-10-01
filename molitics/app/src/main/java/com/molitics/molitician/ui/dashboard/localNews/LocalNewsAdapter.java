package com.molitics.molitician.ui.dashboard.localNews;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.molitics.molitician.BuildConfig;
import com.molitics.molitician.MolticsApplication;
import com.molitics.molitician.R;
import com.molitics.molitician.customView.CustomViewPager;
import com.molitics.molitician.customView.LabeledSwitch;
import com.molitics.molitician.interfaces.LeaderAdapterInterface;
import com.molitics.molitician.model.News;
import com.molitics.molitician.model.NewsVideoModel;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.channels.ChannelBankActivity;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.nationalNews.homeSurvey.HomeSurveyAdapter;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.HomeSurveyInterface;
import com.molitics.molitician.ui.dashboard.nationalNews.adapter.NewsAdapter;
import com.molitics.molitician.ui.dashboard.nationalNews.adapter.NewsLeadersAdapter;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.OnNewsItemClick;
import com.molitics.molitician.ui.dashboard.nationalNews.newsRelatedLeadre.newsVideo.NewsVideoAdapter;
import com.molitics.molitician.ui.dashboard.survey.model.AllSurveyModel;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.ShareScreenShot;
import com.molitics.molitician.util.Util;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.molitics.molitician.util.Util.attachLeaderToLinearView;

/**
 * Created by Rahul on 2/24/2017.
 */

public class LocalNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements BranchShareClass.DeepLinkCallBack {

    private String TAG = "LocalNewsAdapter";
    private ArrayList<News> newsItemList = new ArrayList<>();
    private Context mContext;
    private OnNewsItemClick onNewsItemClick;
    private OnLoadMoreResult onLoadMoreResult;
    private ArrayList<AllLeaderModel> newsLeadersList = new ArrayList<>();
    private List<NewsVideoModel> videoList = new ArrayList<>();
    private int recycler_view_count = 0;
    private LeaderAdapterInterface.LeaderClickListener adapterInterface;
    private int survey_position = 3;
    private Boolean survey_visible = false;
    private List<AllSurveyModel> homeSurveyList;
    private Map<YouTubeThumbnailView, YouTubeThumbnailLoader> thumbnailViewToLoaderMap;

    public LocalNewsAdapter(Context context, HomeSurveyInterface homeSurveyInterface, OnNewsItemClick onNewsItemClick, OnLoadMoreResult onLoadMoreResult, LeaderAdapterInterface.LeaderClickListener adapterInterface) {
        this.mContext = context;
        this.onNewsItemClick = onNewsItemClick;
        this.onLoadMoreResult = onLoadMoreResult;
        this.adapterInterface = adapterInterface;
        thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();

    }

    public LocalNewsAdapter(Context context, OnNewsItemClick onNewsItemClick, OnLoadMoreResult onLoadMoreResult) {
        this.mContext = context;
        this.onNewsItemClick = onNewsItemClick;
        this.onLoadMoreResult = onLoadMoreResult;
        thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();

    }

    public void addLocalNews(List<News> newsItemLi) {
        this.newsItemList.addAll(newsItemLi);
        notifyDataSetChanged();
    }

    public List<NewsVideoModel> getVideoList() {
        return videoList;
    }

    public Boolean getSurveyVisible() {
        return survey_visible;
    }

    public void setTrendingNewsPosition(int trendingNewsPosition) {
        //  trendingNewsPosition1 = trendingNewsPosition;
    }

    public void addNewsLeader(ArrayList<AllLeaderModel> newsLeadersList) {
        this.newsLeadersList.addAll(newsLeadersList);
        notifyDataSetChanged();
    }

    public void addNewsVideo(List<NewsVideoModel> videoList) {
        this.videoList = videoList;
        notifyDataSetChanged();
    }

    public void deleteLocalNewsList() {
        newsItemList.clear();
        recycler_view_count = 0;
        notifyDataSetChanged();
    }

    public void deleteNewsLeadersList() {
        newsLeadersList.clear();
        notifyDataSetChanged();
    }

    public void addSurvey(List<AllSurveyModel> homeSurveyList) {
        survey_position = 3;

        if (homeSurveyList != null && homeSurveyList.size() != 0) {
            survey_visible = true;
            this.homeSurveyList = homeSurveyList;
        }
        notifyDataSetChanged();
    }

    public void deleteSurvey() {
        survey_visible = false;
        this.homeSurveyList = null;
    }

    public ArrayList<AllLeaderModel> getNewsLeadersList() {
        return newsLeadersList;
    }

    public int getNewsListSize() {
        return newsItemList.size();
    }

    public ArrayList<News> getLocalNewsList() {
        return newsItemList;
    }

    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        ShareScreenShot.takeScreenshot(mContext, mContext.getString(R.string.media_of_politics), url);
    }

    public interface OnLoadMoreResult {
        void onLoadMore(int totalItem);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int viewType) {
        final View mView;
        if (viewType == Constant.Condition.VERTICAL_NEWS) {
            mView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.adapter_news_vertical, viewGroup, false);
            return new NewsViewHolder(mView);
        } else if (viewType == Constant.Condition.SURVEY) {
            mView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.home_survey_view, viewGroup, false);
            return new HomeSurveyViewHolder(mView);
        } /*else if (viewType == Constant.Condition.VIDEO) {
            mView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recyclerview_news_video, viewGroup, false);
            return new NewsVideoViewHolder(mView);
        }*/ else {
            mView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.trending_leader_recyclerview, viewGroup, false);
            return new NewsLeadersViewHolder(mView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder.getItemViewType() == Constant.Condition.VERTICAL_NEWS) {

            int news_position = position - recycler_view_count;

            if (survey_visible && position > survey_position)
                news_position = news_position - 1;


            final News newsItem = newsItemList.get(news_position);
            final NewsViewHolder holder = (NewsViewHolder) viewHolder;
            holder.vertical_recycler_lay.setVisibility(View.VISIBLE);
            holder.vertical_recycler_lay.setTag(news_position);
            holder.news_headline.setText(newsItem.getHeading());
            holder.sponser_news_post_time.setText(newsItem.getTime());
            holder.channelNameView.setText(newsItem.getSource());

            holder.channelNameView.setTag(newsItem);

            attachLeaderToLinearView(mContext, holder.attachedLeaderView, newsItem.getTaggedLeader());

            holder.shareNewsView.setOnClickListener(view -> {
                BranchShareClass.generateNewsShareLink(this, newsItem.getHeading(), newsItem.getShareLink());
            });

            if (!TextUtils.isEmpty(newsItem.getSourceLogo())) {
                Picasso.with(mContext).load(newsItem.getSourceLogo()).into(holder.channelImageView);
            }

            if (news_position >= 8 - recycler_view_count && news_position == (newsItemList.size() - 1 - recycler_view_count)) {
                onLoadMoreResult.onLoadMore(newsItemList.size());
            }

            if (newsItem.getType() == 1) {
                holder.video_play_icon.setVisibility(View.GONE);
                holder.thumbnail.setVisibility(View.GONE);
                loadImage(newsItem.getImage(), holder);

                holder.thumbnail.setVisibility(View.GONE);
            } else if (newsItem.getType() == 2) {
                holder.video_play_icon.setVisibility(View.VISIBLE);
                holder.thumbnail.setVisibility(View.VISIBLE);
                YouTubeThumbnailLoader loader = thumbnailViewToLoaderMap.get(holder.thumbnail);
                if (loader == null) {
                    holder.thumbnail.setTag(newsItem.getLink());
                } else {
                    holder.thumbnail.setImageResource(R.drawable.loading_thumbnail);
                    try {
                        loader.setVideo(newsItem.getLink());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (newsItem.getType() != 0) {
                holder.video_play_icon.setVisibility(View.GONE);
                holder.thumbnail.setVisibility(View.GONE);
                loadImage(newsItem.getImage(), holder);
                holder.sponser_news_post_time.setText(ExtraUtils.setBoldPinkLastString(mContext, newsItem.getTime(), mContext.getString(R.string.publish_by_user), 1f));
            }
            holder.vertical_recycler_lay.setOnClickListener(v ->
                    {
                        int currentPosition = (int) holder.vertical_recycler_lay.getTag();
                        onNewsItemClick.onNewsRecyclerClick(currentPosition, Constant.Condition.VERTICAL_NEWS);
                    }
            );
            holder.thumbnail.setOnClickListener(v -> {
                int currentPosition = (int) holder.vertical_recycler_lay.getTag();
                onNewsItemClick.onNewsRecyclerClick(currentPosition, Constant.Condition.YoutubeVideo);
            });

        } else if (viewHolder.getItemViewType() == Constant.Condition.INTERNATIONAL_LEADER) {
            NewsLeadersViewHolder holder = (NewsLeadersViewHolder) viewHolder;
            if (newsLeadersList != null && newsLeadersList.size() != 0) {
                try {
                    recycler_view_count = 1;
                    holder.ll_international.setVisibility(View.VISIBLE);
                    if (newsLeadersList.isEmpty()) {
                        holder.ll_international.setVisibility(View.GONE);
                    } else {
                        holder.ll_international.setVisibility(View.VISIBLE);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                        holder.news_hor_recycler_view.setLayoutManager(mLayoutManager);
                        NewsLeadersAdapter newsLeadersAdapter = new NewsLeadersAdapter(mContext, newsLeadersList, onNewsItemClick, adapterInterface, Constant.Condition.INTERNATIONAL_LEADER);
                        holder.news_hor_recycler_view.setAdapter(newsLeadersAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }/* else if (viewHolder.getItemViewType() == Constant.Condition.VIDEO) {
            recycler_view_count++;
            NewsVideoViewHolder holder = (NewsVideoViewHolder) viewHolder;

            if (videoList == null || videoList.size() == 0) {
                holder.video_hor_recycler_view.setVisibility(View.GONE);
                holder.rl_no_data.setVisibility(View.VISIBLE);
            } else {
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                holder.video_hor_recycler_view.setLayoutManager(mLayoutManager);
                holder.video_hor_recycler_view.setVisibility(View.VISIBLE);

                NewsVideoAdapter newsVideoAdapter = new NewsVideoAdapter(onNewsItemClick);
                newsVideoAdapter.addData(videoList);
                holder.video_hor_recycler_view.setAdapter(newsVideoAdapter);
                holder.rl_no_data.setVisibility(View.GONE);
            }
        }*/ else if (viewHolder.getItemViewType() == Constant.Condition.SURVEY) {
            HomeSurveyViewHolder holder = (HomeSurveyViewHolder) viewHolder;
            HomeSurveyAdapter homeNewsSurveyAdapter = new HomeSurveyAdapter(mContext, currentPosition -> {
                holder.home_survey_recycler.setCurrentItem(currentPosition + 1, true);

            });
            holder.home_survey_recycler.setAdapter(homeNewsSurveyAdapter);
            homeNewsSurveyAdapter.addSurveyList(homeSurveyList);
            holder.home_survey_recycler.setOffscreenPageLimit(0);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && newsLeadersList.size() != 0) {
            return Constant.Condition.INTERNATIONAL_LEADER;
        } else if (survey_visible && position == survey_position) {
            return Constant.Condition.SURVEY;
        }  else {
            return Constant.Condition.VERTICAL_NEWS;
        }
    }

    @Override
    public int getItemCount() {
        return newsItemList.size() + recycler_view_count;
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

    public class NewsVideoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.video_hor_recycler_view)
        RecyclerView video_hor_recycler_view;
        @BindView(R.id.switch_language)
        LabeledSwitch switch_language;
        @BindView(R.id.rl_no_data)
        TextView rl_no_data;


        public NewsVideoViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            switch_language.setVisibility(View.GONE);
        }

    }


    public class HomeSurveyViewHolder extends NewsAdapter.ViewHolder {
        @BindView(R.id.home_survey_recycler)
        CustomViewPager home_survey_recycler;

        public HomeSurveyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.main_constraintLayout)
        ConstraintLayout vertical_recycler_lay;
        @BindView(R.id.news_image)
        ImageView news_image;
        @BindView(R.id.news_headline)
        TextView news_headline;

        @BindView(R.id.sponser_news_post_time)
        TextView sponser_news_post_time;
        @BindView(R.id.thumbnail)
        YouTubeThumbnailView thumbnail;

        @BindView(R.id.video_play_icon)
        ImageView video_play_icon;
        @BindView(R.id.channelImageView)
        ImageView channelImageView;
        @BindView(R.id.channelNameView)
        TextView channelNameView;
        @BindView(R.id.attachedLeaderView)
        LinearLayout attachedLeaderView;
        @BindView(R.id.shareNewsView)
        ImageView shareNewsView;

        public NewsViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            thumbnail.initialize(MolticsApplication.YOUTUBE_KEY, new ThumbnailListener());
            channelNameView.setOnClickListener((view) -> {
                News details = (News) channelNameView.getTag();
                if (!details.getSource().contains(Constant.MOLITICS))
                    openChannelPage(details);
            });
            channelImageView.setOnClickListener(view -> {
                News details = (News) channelNameView.getTag();
                if (!details.getSource().contains(Constant.MOLITICS))
                    openChannelPage(details);
            });
        }
    }

    private void openChannelPage(News details) {
        Intent intent = new Intent(mContext, ChannelBankActivity.class);
        intent.putExtra(Constant.IntentKey.CHANNEL_ID, details.getSourceId());
        intent.putExtra(Constant.IntentKey.SOURCE, details.getSource());
        intent.putExtra(Constant.IntentKey.SOURCE_IMAGE, details.getSourceLogo());
        mContext.startActivity(intent);
    }


    private final class ThumbnailListener implements
            YouTubeThumbnailView.OnInitializedListener,
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onInitializationSuccess(
                YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
            loader.setOnThumbnailLoadedListener(this);
            if (thumbnailViewToLoaderMap != null)
                thumbnailViewToLoaderMap.put(view, loader);
            view.setImageResource(R.drawable.video_placeholder);
            String videoId = (String) view.getTag();
            if (videoId != null && !videoId.isEmpty())
                loader.setVideo(videoId);
        }

        @Override
        public void onInitializationFailure(
                YouTubeThumbnailView view, YouTubeInitializationResult loader) {
            if (view != null)
                view.setImageResource(R.drawable.video_placeholder);
        }

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView view, String videoId) {

        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView view, YouTubeThumbnailLoader.ErrorReason errorReason) {
            if (view != null)
                view.setImageResource(R.drawable.video_placeholder);
        }
    }

    public void releaseLoaders() {
        for (YouTubeThumbnailLoader loader : thumbnailViewToLoaderMap.values()) {
            if (loader != null)
                loader.release();
        }
    }

    private void loadImage(String url, NewsViewHolder holder) {
        //show image
        if (url != null && !TextUtils.isEmpty(url)) {
            Picasso.with(mContext).load(url).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.image_placeholder).error(R.drawable.internet_no_cloud).into(holder.news_image, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError() {
                    Picasso.with(mContext).load(url).placeholder(R.drawable.image_placeholder).error(R.drawable.internet_no_cloud).into(holder.news_image);
                }
            });
        }
        holder.video_play_icon.setVisibility(View.GONE);
    }
}
