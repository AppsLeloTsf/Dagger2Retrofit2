package com.molitics.molitician.ui.dashboard.nationalNews.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.molitics.molitician.R;
import com.molitics.molitician.customView.CustomViewPager;
import com.molitics.molitician.customView.LabeledSwitch;
import com.molitics.molitician.customView.OnToggledListener;
import com.molitics.molitician.customView.ToggleableView;
import com.molitics.molitician.interfaces.LeaderAdapterInterface;
import com.molitics.molitician.model.News;
import com.molitics.molitician.model.NewsVideoModel;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.channels.ChannelBankActivity;
import com.molitics.molitician.ui.dashboard.election.past_election.past_states.ElectionResultDetailActivity;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.nationalNews.homeSurvey.HomeSurveyAdapter;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.HomeSurveyInterface;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.OnNewsItemClick;
import com.molitics.molitician.ui.dashboard.nationalNews.model.HomeElectionModel;
import com.molitics.molitician.ui.dashboard.nationalNews.newsRelatedLeadre.newsVideo.NewsVideoAdapter;
import com.molitics.molitician.ui.dashboard.survey.model.AllSurveyModel;
import com.molitics.molitician.ui.dashboard.video.VideoActivity;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.ShareScreenShot;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.molitics.molitician.MolticsApplication.YOUTUBE_KEY;
import static com.molitics.molitician.util.Util.attachLeaderToLinearView;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> implements BranchShareClass.DeepLinkCallBack {
    private ArrayList<News> newsItemList = new ArrayList<>();
    private ArrayList<News> headingNewsList = new ArrayList<>();
    private List<NewsVideoModel> videoList = new ArrayList<>();
    private ArrayList<AllLeaderModel> newsLeadersList = new ArrayList<>();
    ;
    private Context mContext;
    private int headingNewsPosition = 1;
    private int global_news_position = 0;
    //private int video_position = 0;
    private int survey_position = 0;
    private int selectedLanguage = 0;


    private Boolean survey_visible = false;
    private OnNewsItemClick onNewsItemClick;
    private final Map<YouTubeThumbnailView, YouTubeThumbnailLoader> thumbnailViewToLoaderMap;
    private OnLoadMoreResult onLoadMoreResult;
    private NewsVideoAdapter newsVideoAdapter;
    private HomeSurveyInterface homeSurveyInterface;
    private List<AllSurveyModel> homeSurveyList;
    private HomeElectionModel homeElectionModel;
    private LeaderAdapterInterface.LeaderClickListener adapterInterface;


    public NewsAdapter(Context context, HomeSurveyInterface homeSurveyInterface, OnNewsItemClick onNewsItemClick, OnLoadMoreResult onLoadMoreResult, LeaderAdapterInterface.LeaderClickListener adapterInterface) {
        this.mContext = context;
        this.onNewsItemClick = onNewsItemClick;
        this.homeSurveyInterface = homeSurveyInterface;
        thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();
        this.onLoadMoreResult = onLoadMoreResult;
        this.adapterInterface = adapterInterface;
    }

/*
    public void setAdvPosition(List<Integer> adv_position_list) {
        this.ad_position_list = adv_position_list;
    }*/

    public Boolean getSurveyVisible() {
        return survey_visible;
    }

    public void setNewsVideo(int selectedLanguage, List<NewsVideoModel> videoList) {
        this.selectedLanguage = selectedLanguage;
        this.videoList = videoList;
        notifyDataSetChanged();
    }

    public void setElection(HomeElectionModel homeElectionModel) {
        this.homeElectionModel = homeElectionModel;
    }

    public List<NewsVideoModel> getVideoList() {
        return videoList;
    }

    public void addVerticalNews(List<News> newsItemList) {
        this.newsItemList.addAll(newsItemList);
        notifyDataSetChanged();
    }

    public void addNewsLeader(ArrayList<AllLeaderModel> newsLeadersList) {
        this.newsLeadersList.clear();
        this.newsLeadersList.addAll(newsLeadersList);
        notifyDataSetChanged();
    }

    public void addHeadingNews(ArrayList<News> headingNewsItem) {
        if (headingNewsItem != null) {
            this.headingNewsList.addAll(headingNewsItem);
            notifyDataSetChanged();
        }
    }

    public void addSurvey(List<AllSurveyModel> homeSurveyList) {
        survey_position = 6;

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

    public void deleteVerticalList() {
        newsItemList.clear();
        notifyDataSetChanged();
    }

    public void deleteNewsLeadersList() {
        newsLeadersList.clear();
        notifyDataSetChanged();
    }

    public void deleteNewsVideoList() {
        if (videoList != null) {
            videoList.clear();
            notifyDataSetChanged();
        }
    }

    public void deleteHeadingList() {
        headingNewsList.clear();
        notifyDataSetChanged();
    }

    public ArrayList<News> getVerticalNewsList() {
        return newsItemList;
    }

    public ArrayList<AllLeaderModel> getNewsLeadersList() {
        return newsLeadersList;
    }

    public AllLeaderModel getLeaderModel(int position) {
        return newsLeadersList.get(position);
    }

    public ArrayList<News> getHeadingNewsList() {
        return headingNewsList;
    }

    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        ShareScreenShot.takeScreenshot(mContext, mContext.getString(R.string.media_of_politics), url);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public int getNewsListSize() {
        return newsItemList.size();
    }

    public interface OnLoadMoreResult {
        void onLoadMore(int totalItem);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int viewType) {
        final View v;
        if (viewType == Constant.Condition.HEASDER_NEWS) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.headingnews_pager, viewGroup, false);
            return new HeadingNewsViewHolder(v);
        } else if (viewType == Constant.Condition.VERTICAL_NEWS) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.adapter_news_vertical, viewGroup, false);
            return new NewsViewHolder(v);
        } else if (viewType == Constant.Condition.ADV) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.adapter_adv_layout, viewGroup, false);
            return new AdvViewHolder(v);

        }/* else if (viewType == Constant.Condition.VIDEO) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recyclerview_news_video, viewGroup, false);
            return new NewsVideoViewHolder(v);
        }*/ else if (viewType == Constant.Condition.INTERNATIONAL_LEADER) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.trending_leader_recyclerview, viewGroup, false);
            return new NewsLeadersViewHolder(v);
        } else if (viewType == Constant.Condition.SURVEY) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.home_survey_view, viewGroup, false);
            return new HomeSurveyViewHolder(v);
        } else if (viewType == Constant.Condition.ELECTION) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.home_election, viewGroup, false);
            return new HomeElectionViewHolder(v);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        if (viewHolder.getItemViewType() == Constant.Condition.VERTICAL_NEWS) {
            if (newsItemList.size() != 0) {
                global_news_position = position;

                if (headingNewsList != null && headingNewsList.size() > 0)
                    global_news_position--;

                if (newsLeadersList.size() > 0) {
                    global_news_position--;
                }

                if (homeElectionModel != null && position > 1)
                    global_news_position--;

             /*   if (video_position < position) {
                    global_news_position--;
                }*/
                if (survey_visible && position > survey_position)
                    global_news_position--;
                if (global_news_position == -1)
                    global_news_position = 0;

                final NewsViewHolder holder = (NewsViewHolder) viewHolder;

                final News newsItem = newsItemList.get(global_news_position);

                holder.main_constraintLayout.setVisibility(View.VISIBLE);
                holder.main_constraintLayout.setTag(global_news_position);
                holder.news_headline.setTypeface(ExtraUtils.getRobotoMedium(mContext));
                holder.news_headline.setText(Html.fromHtml(newsItem.getHeading().trim()));
                holder.sponser_news_post_time.setText(newsItem.getTime());
                holder.channelNameView.setText(newsItem.getSource());

                holder.channelNameView.setTag(newsItem);
                holder.shareNewsView.setOnClickListener(view -> {
                    BranchShareClass.generateNewsShareLink(this, newsItem.getHeading(), newsItem.getShareLink());
                });
                attachLeaderToLinearView(mContext, holder.attachedLeaderView, newsItem.getTaggedLeader());

                if (!TextUtils.isEmpty(newsItem.getSourceLogo())) {
                    Picasso.with(holder.channelImageView.getContext()).load(newsItem.getSourceLogo()).into(holder.channelImageView);
                }

                if (position >= 9 && (position == (newsItemList.size() - 1) || position == (newsItemList.size()))) {
                    onLoadMoreResult.onLoadMore(newsItemList.size());
                }
                holder.article_tag_view.setVisibility(View.GONE);
                if (newsItem.getDisplayType() == 4) {
                    holder.article_tag_view.setVisibility(View.VISIBLE);
                }
                if (newsItem.getType() == 2) {
                    //show video_small_theme
                    holder.video_play_icon.setVisibility(View.VISIBLE);
                    holder.thumbnail.setVisibility(View.VISIBLE);
                    holder.thumbnail.setTag(newsItem.getLink());
                    YouTubeThumbnailLoader loader = thumbnailViewToLoaderMap.get(holder.thumbnail);
                    if (loader == null) {
                        holder.thumbnail.setTag(newsItem.getLink());
                    } else {
                        holder.thumbnail.setImageResource(R.drawable.video_loading_placeholder);
                        loader.setVideo(newsItem.getLink());
                    }
                } else {
                    if (newsItem.getImage() != null && !TextUtils.isEmpty(newsItem.getImage())) {

                        Picasso.with(mContext).load(newsItem.getImage()).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.image_placeholder).error(R.drawable.internet_no_cloud).into(holder.news_image, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                                Picasso.with(mContext).load(newsItem.getImage()).placeholder(R.drawable.image_placeholder).error(R.drawable.internet_no_cloud).into(holder.news_image);
                            }
                        });
                    }
                    holder.video_play_icon.setVisibility(View.GONE);
                    holder.thumbnail.setVisibility(View.GONE);
                }
                holder.main_constraintLayout.setOnClickListener(v -> {
                    int currentPosition = (int) holder.main_constraintLayout.getTag();
                    onNewsItemClick.onNewsRecyclerClick(currentPosition, Constant.Condition.VERTICAL_NEWS);
                });

                holder.thumbnail.setOnClickListener(v -> {
                    int currentPosition = (int) holder.main_constraintLayout.getTag();
                    onNewsItemClick.onNewsRecyclerClick(currentPosition, Constant.Condition.YoutubeVideo);
                });
            }
        } else if (viewHolder.getItemViewType() == Constant.Condition.INTERNATIONAL_LEADER) {
            NewsLeadersViewHolder holder = (NewsLeadersViewHolder) viewHolder;
            if (newsLeadersList != null) {
                try {
                    holder.ll_international.setVisibility(View.VISIBLE);
                    if (newsLeadersList.isEmpty()) {
                        // holder.ll_international.setVisibility(View.GONE);
                    } else {
                        holder.ll_international.setVisibility(View.VISIBLE);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                        holder.news_hor_recycler_view.setLayoutManager(mLayoutManager);
                        NewsLeadersAdapter newsLeadersAdapter = new NewsLeadersAdapter(mContext, newsLeadersList, onNewsItemClick, adapterInterface, Constant.Condition.INTERNATIONAL_LEADER);
                        holder.news_hor_recycler_view.setAdapter(newsLeadersAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (viewHolder.getItemViewType() == Constant.Condition.HEASDER_NEWS) {
            HeadingNewsViewHolder holder = (HeadingNewsViewHolder) viewHolder;
            if (headingNewsList.isEmpty()) {
                holder.rl_main.setVisibility(View.GONE);
            } else {
                holder.rl_main.setVisibility(View.VISIBLE);

                HeaderFragmentPagerAdapter headingAdapter = new HeaderFragmentPagerAdapter(mContext, headingNewsList, onNewsItemClick);
                holder.hor_recycler_view.setAdapter(headingAdapter);
                holder.tab_layout.setupWithViewPager(holder.hor_recycler_view);
            }
        } else if (viewHolder.getItemViewType() == Constant.Condition.ADV) {
            AdvViewHolder holder = (AdvViewHolder) viewHolder;

            final News newsItem = newsItemList.get(global_news_position);
            if (newsItem.getImage() != null && !TextUtils.isEmpty(newsItem.getImage()))
                Picasso.with(mContext).load(newsItem.getImage()).into(holder.adv_image);

        }/* else if (viewHolder.getItemViewType() == Constant.Condition.VIDEO) {
            NewsVideoViewHolder holder = (NewsVideoViewHolder) viewHolder;

            if (videoList == null || videoList.size() == 0) {
                holder.video_hor_recycler_view.setVisibility(View.GONE);
                holder.rl_no_data.setVisibility(View.VISIBLE);
            } else {
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                holder.video_hor_recycler_view.setLayoutManager(mLayoutManager);
                holder.video_hor_recycler_view.setVisibility(View.VISIBLE);

                newsVideoAdapter = new NewsVideoAdapter(onNewsItemClick);
                newsVideoAdapter.addData(videoList);
                holder.video_hor_recycler_view.setAdapter(newsVideoAdapter);
                holder.rl_no_data.setVisibility(View.GONE);

                if (selectedLanguage == Constant.Language.ENGLISH)
                    holder.switch_language.setOn(false);
                else
                    holder.switch_language.setOn(true);
            }

        } */else if (viewHolder.getItemViewType() == Constant.Condition.SURVEY) {

            HomeSurveyViewHolder holder = (HomeSurveyViewHolder) viewHolder;
            HomeSurveyAdapter homeNewsSurveyAdapter = new HomeSurveyAdapter(mContext, currentPosition ->
                    holder.home_survey_recycler.setCurrentItem(currentPosition + 1, true));
            holder.home_survey_recycler.setAdapter(homeNewsSurveyAdapter);
            homeNewsSurveyAdapter.addSurveyList(homeSurveyList);
            holder.home_survey_recycler.setOffscreenPageLimit(0);
        } else if (viewHolder.getItemViewType() == Constant.Condition.ELECTION) {

            HomeElectionViewHolder holder = (HomeElectionViewHolder) viewHolder;
            holder.electionTitle.setText(homeElectionModel.getTitle());
            holder.electionDateView.setText(homeElectionModel.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return newsItemList.size() + 1;
    }

    public class NewsViewHolder extends ViewHolder {
        @BindView(R.id.main_constraintLayout)
        ConstraintLayout main_constraintLayout;
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
        @BindView(R.id.article_tag_view)
        ImageView article_tag_view;
        @BindView(R.id.channelNameView)
        TextView channelNameView;
        @BindView(R.id.channelImageView)
        ImageView channelImageView;
        @BindView(R.id.shareNewsView)
        ImageView shareNewsView;
        @BindView(R.id.attachedLeaderView)
        LinearLayout attachedLeaderView;

        public NewsViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            thumbnail.initialize(YOUTUBE_KEY, new ThumbnailListener());

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

    public static class NewsLeadersViewHolder extends ViewHolder {
        @BindView(R.id.news_hor_recycler_view)
        RecyclerView news_hor_recycler_view;
        @BindView(R.id.ll_international)
        LinearLayout ll_international;

        public NewsLeadersViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public static class HomeSurveyViewHolder extends ViewHolder {
        @BindView(R.id.home_survey_recycler)
        CustomViewPager home_survey_recycler;

        public HomeSurveyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public class HomeElectionViewHolder extends ViewHolder implements View.OnClickListener {
        @BindView(R.id.electionTitle)
        TextView electionTitle;
        @BindView(R.id.electionDateView)
        TextView electionDateView;
        @BindView(R.id.seeElectionResult)
        Button seeElectionResult;

        public HomeElectionViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            seeElectionResult.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.seeElectionResult) {
                Intent mIntent = new Intent(v.getContext(), ElectionResultDetailActivity.class);
                mIntent.putExtra(Constant.IntentKey.ELECTION_ID, homeElectionModel.getElectionId());
                mIntent.putExtra(Constant.IntentKey.PAST_STATE_ID, 0);
                v.getContext().startActivity(mIntent);
            }
        }
    }

    public static class AdvViewHolder extends ViewHolder {
        @BindView(R.id.adv_image)
        ImageView adv_image;
        @BindView(R.id.adv_txt)
        TextView adv_txt;

        public AdvViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public class NewsVideoViewHolder extends ViewHolder implements View.OnClickListener, OnToggledListener {

        @BindView(R.id.video_hor_recycler_view)
        RecyclerView video_hor_recycler_view;
        @BindView(R.id.view_video_all_txt)
        TextView view_video_all_txt;
        @BindView(R.id.switch_language)
        LabeledSwitch switch_language;
        @BindView(R.id.rl_no_data)
        TextView rl_no_data;


        public NewsVideoViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            view_video_all_txt.setOnClickListener(this);
            switch_language.setOnToggledListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.view_video_all_txt) {
                mContext.startActivity(new Intent(mContext, VideoActivity.class));
            }
        }

        @Override
        public void onSwitched(ToggleableView toggleableView, boolean isOn) {
            if (isOn)
                onNewsItemClick.onLanguageSelectionClick(Constant.Language.HINDI, 1);
            else
                onNewsItemClick.onLanguageSelectionClick(Constant.Language.ENGLISH, 1);
        }
    }

    public static class HeadingNewsViewHolder extends ViewHolder {
        @BindView(R.id.rl_main)
        RelativeLayout rl_main;
        @BindView(R.id.hor_recycler_view)
        ViewPager hor_recycler_view;
        @BindView(R.id.tab_layout)
        TabLayout tab_layout;

        public HeadingNewsViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public class SurveyViewHolder extends ViewHolder {
        @BindView(R.id.take_survey_button)
        TextView take_survey_button;

        public SurveyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && newsLeadersList.size() != Constant.ZERO) {
            return Constant.Condition.INTERNATIONAL_LEADER;
        } else if (position == headingNewsPosition && headingNewsList.size() != Constant.ZERO) {
            return Constant.Condition.HEASDER_NEWS;
        }/* else if (ad_position_list.contains(position)) {
            return Constant.Condition.ADV;
        }*/ else if (survey_visible && position == survey_position) {
            return Constant.Condition.SURVEY;
        } else if (position == 1 && homeElectionModel != null) {
            return Constant.Condition.ELECTION;
        } else {
            return Constant.Condition.VERTICAL_NEWS;
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
            if (view != null)
                view.setImageResource(R.drawable.video_loading_placeholder);
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

    public void releaseLoader() {
        if (newsVideoAdapter != null)
            newsVideoAdapter.releaseLoaders();

        releaseLoaders();
    }

    public void releaseLoaders() {
        for (YouTubeThumbnailLoader loader : thumbnailViewToLoaderMap.values()) {
            if (loader != null)
                loader.release();
        }
    }
}