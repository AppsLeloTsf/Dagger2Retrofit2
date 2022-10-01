package com.molitics.molitician.ui.dashboard.hotTopics;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.molitics.molitician.BuildConfig;
import com.molitics.molitician.MolticsApplication;
import com.molitics.molitician.R;
import com.molitics.molitician.model.News;
import com.molitics.molitician.ui.dashboard.channels.ChannelBankActivity;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.OnNewsItemClick;
import com.molitics.molitician.ui.dashboard.voice.adapter.ImageViewPager;
import com.molitics.molitician.ui.dashboard.voice.adapter.VoiceAllAdapter;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.ui.dashboard.voice.viewHolder.VoiceAllViewHolder;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
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
 * Created by rahul on 11/12/17.
 */

public class HotTopicDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        ImageViewPager.VoiceImageListener, ExtraUtils.NotifyCustomDataSetChanged {

    String TAG = "HotTopicDetailAdapter";
    private ArrayList<News> newsItemList = new ArrayList<>();
    private Context mContext;
    private OnNewsItemClick onNewsItemClick;
    private OnLoadMoreResult onLoadMoreResult;
    private VoiceAllAdapter.VoiceInterFace voiceInterFace;

    private final ThumbnailListener thumbnailListener;

    private final Map<YouTubeThumbnailView, YouTubeThumbnailLoader> thumbnailViewToLoaderMap;

    @Override
    public void onImageClick(int position, int issue_id, List<String> imageList) {

    }

    @Override
    public void onVideoPLay() {

    }

    @Override
    public void onVideoPause() {

    }

    @Override
    public void onShareClick(String url) {

    }

    @Override
    public void onDownloadClick(String url) {

    }


    public HotTopicDetailAdapter(Context context, OnNewsItemClick onNewsItemClick, OnLoadMoreResult onLoadMoreResult,
                                 VoiceAllAdapter.VoiceInterFace voiceInterFace) {
        this.mContext = context;
        this.onNewsItemClick = onNewsItemClick;
        this.onLoadMoreResult = onLoadMoreResult;
        this.voiceInterFace = voiceInterFace;
        thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();
        thumbnailListener = new ThumbnailListener();

    }

    public void addLocalNewsOnTop(News news) {
        this.newsItemList.add(0, news);
        notifyDataSetChanged();
    }


    public void removeVoiceOnTop(int position) {
        newsItemList.remove(Constant.ZERO);
        notifyDataSetChanged();
    }

    public void addCommentCount(int position, int count) {
        VoiceAllModel voiceAllModel = newsItemList.get(position).getHotTopicIssue();
        voiceAllModel.setComments(voiceAllModel.getComments() + count);
        notifyDataSetChanged();
    }

    public void addLocalNews(ArrayList<News> newsItemList) {
        this.newsItemList.addAll(newsItemList);
        notifyDataSetChanged();
    }


    public void deleteLocalNewsList() {
        newsItemList.clear();
    }

    public int getNewsListSize() {
        return newsItemList.size();
    }

    public ArrayList<News> getLocalNewsList() {
        return newsItemList;
    }

    @Override
    public void notifyCustom(int image_position) {
    }

    public interface OnLoadMoreResult {
        void onLoadMore(int totalItem);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int viewType) {
        final View mView;
        mView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.hot_topic_detail_vertical, viewGroup, false);
        return new NewsViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        final int news_position = viewHolder.getAdapterPosition();
        final News newsItem = newsItemList.get(news_position);
        final NewsViewHolder holder = (NewsViewHolder) viewHolder;
        if (news_position >= 8 && news_position == (newsItemList.size() - 1)) {
            onLoadMoreResult.onLoadMore(newsItemList.size());
        }

        holder.channelNameView.setText(newsItem.getSource());
        holder.channelNameView.setTag(newsItem);
        holder.main_constraintLayout.setVisibility(View.VISIBLE);
        attachLeaderToLinearView(mContext, holder.attachedLeaderView, newsItem.getTaggedLeader());

        if (!TextUtils.isEmpty(newsItem.getSourceLogo())) {
            Picasso.with(holder.channelImageView.getContext()).load(newsItem.getSourceLogo()).into(holder.channelImageView);
        }
        if (newsItem.getType() == 1) {

            holder.vertical_recycler_lay.setVisibility(View.VISIBLE);
            holder.include_voice_layout.setVisibility(View.GONE);
            holder.thumbnail.setVisibility(View.GONE);
            holder.video_play_icon.setVisibility(View.GONE);


            holder.news_headline.setText(newsItem.getHeading());

            holder.sponser_news_post_time.setText(newsItem.getTime());
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
            } else {
                holder.news_image.setImageResource(R.drawable.image_placeholder);
            }

        } else if (newsItem.getType() == 2) {
            holder.video_play_icon.setVisibility(View.VISIBLE);
            holder.include_voice_layout.setVisibility(View.GONE);

            holder.news_headline.setText(newsItem.getHeading());
            holder.sponser_news_post_time.setText(newsItem.getTime());
            holder.thumbnail.setVisibility(View.VISIBLE);
            holder.thumbnail.setTag(newsItem.getLink());

            YouTubeThumbnailLoader loader = thumbnailViewToLoaderMap.get(holder.thumbnail);
            if (loader == null) {
                // 2) The view is already created, and is currently being initialized. We store the
                //    current videoId in the tag.
                holder.thumbnail.setTag(newsItem.getLink());
            } else {
                // 3) The view is already created and already initialized. Simply set the right videoId
                //    on the loader.
                holder.thumbnail.setImageResource(R.drawable.loading_thumbnail);
                loader.setVideo(newsItem.getLink());
            }
        }
        if (newsItem.getEntityType() == 5) {
            holder.thumbnail.setVisibility(View.GONE);
            holder.vertical_recycler_lay.setVisibility(View.GONE);
            holder.include_voice_layout.setVisibility(View.VISIBLE);
            VoiceAllModel voiceModel = newsItem.getHotTopicIssue();

            if (voiceModel != null) {
                int image_position = 0;
                holder.bindView(mContext, TAG, holder, voiceModel,
                        holder.getAdapterPosition(), news_position, voiceInterFace, this, image_position);
            }
        }
        holder.vertical_recycler_lay.setOnClickListener(v -> onNewsItemClick.onNewsRecyclerClick(news_position, newsItem.getType()));
    }

    @Override
    public int getItemViewType(int position) {
        return Constant.Condition.VERTICAL_NEWS;
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    public class NewsViewHolder extends VoiceAllViewHolder {

        @BindView(R.id.news_image)
        ImageView news_image;
        @BindView(R.id.news_headline)
        TextView news_headline;
        @BindView(R.id.sponser_news_post_time)
        TextView sponser_news_post_time;
        /* @BindView(R.id.news_survey_view)
         TextView news_survey_view;*/
        @BindView(R.id.vertical_recycler_lay)
        RelativeLayout vertical_recycler_lay;
        @BindView(R.id.thumbnail)
        YouTubeThumbnailView thumbnail;
        @BindView(R.id.video_play_icon)
        ImageView video_play_icon;
        @BindView(R.id.include_voice_layout)
        View include_voice_layout;
        @BindView(R.id.description_txt)
        TextView description_txt;
        @BindView(R.id.issue_time)
        TextView issue_time;
        @BindView(R.id.main_constraintLayout)
        ConstraintLayout main_constraintLayout;
        @BindView(R.id.channelNameView)
        TextView channelNameView;
        @BindView(R.id.channelImageView)
        ImageView channelImageView;


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

        private void openChannelPage(News details) {
            Intent intent = new Intent(mContext, ChannelBankActivity.class);
            intent.putExtra(Constant.IntentKey.CHANNEL_ID, details.getSourceId());
            intent.putExtra(Constant.IntentKey.SOURCE, details.getSource());
            intent.putExtra(Constant.IntentKey.SOURCE_IMAGE, details.getSourceLogo());
            mContext.startActivity(intent);
        }
    }

    public void updateLikeDislike(int position, int like_count, int dislike_count, int my_action) {

        News mNews = newsItemList.get(position);
        VoiceAllModel voiceAllModel = mNews.getHotTopicIssue();

        if (voiceAllModel != null) {
            voiceAllModel.setLikes(like_count);
            voiceAllModel.setDislikes(dislike_count);
            voiceAllModel.setMyAction(my_action);
            mNews.setHotTopicIssue(voiceAllModel);
            newsItemList.set(position, mNews);
        }
    }

    public void onFollowResponse(int position, int count) {

        News mNews = newsItemList.get(position);
        if (mNews.getHotTopicIssue() != null) {
            VoiceAllModel voiceAllModel = mNews.getHotTopicIssue();
            voiceAllModel.setFollowing(count);
            voiceAllModel.setIsFollowing(1);
            mNews.setHotTopicIssue(voiceAllModel);
            newsItemList.set(position, mNews);
        }
    }

    public void onUnFollowResponse(int position, int count) {
        News mNews = newsItemList.get(position);
        if (mNews.getHotTopicIssue() != null) {
            VoiceAllModel voiceAllModel = mNews.getHotTopicIssue();
            voiceAllModel.setFollowing(count);
            voiceAllModel.setIsFollowing(0);
            mNews.setHotTopicIssue(voiceAllModel);
            newsItemList.set(position, mNews);
        }
    }

    public void deleteVoice(int position) {
        newsItemList.remove(position);
        notifyDataSetChanged();
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
 /*   public void releaseLoaders() {
        for (YouTubeThumbnailLoader loader : thumbnailViewToLoaderMap.values()) {
            if (loader != null)
                loader.release();
        }
    }*/
}
