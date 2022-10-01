package com.molitics.molitician.ui.dashboard.nationalNews.newsRelatedLeadre;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.molitics.molitician.MolticsApplication;
import com.molitics.molitician.R;
import com.molitics.molitician.databinding.HeadingnewsPagerBinding;
import com.molitics.molitician.interfaces.LeaderAdapterInterface;
import com.molitics.molitician.model.News;
import com.molitics.molitician.ui.dashboard.channels.ChannelBankActivity;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.nationalNews.YoutubePlayActivity;
import com.molitics.molitician.ui.dashboard.nationalNews.adapter.viewHolder.HeadingNewsViewHolder;
import com.molitics.molitician.ui.dashboard.newsDetail.DetailNewsActivity;
import com.molitics.molitician.ui.dashboard.termCondition.TermConditionActivity;
import com.molitics.molitician.ui.dashboard.voice.VoiceCreatorProfile;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.UlTagHandler;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.text.Html.FROM_HTML_MODE_LEGACY;
import static com.molitics.molitician.util.Constant.MOLITICS_MEDIA;
import static com.molitics.molitician.util.StringUtils.replaceHtml;
import static com.molitics.molitician.util.Util.formatNumberToShort;

/**
 * Created by Rahul on 3/10/2017.
 */

public class DetailNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<AllLeaderModel> allLeaderModels = new ArrayList<>();
    private NewsLeaderViewHolder leaderViewHolder;
    private Context mContext;
    private OnLeaderActionClick onLeaderClick;
    ////private OnLeaderClick onLeaderClick;
    private LeaderAdapterInterface adapterInterface;
    private int mCurrRotation = 0; // takes the place of getRotation()
    private int NEWS_VIEW = 1;
    private int LEADER_VIEW = 2;
    private Map<YouTubeThumbnailView, YouTubeThumbnailLoader> thumbnailViewToLoaderMap;
    private ThumbnailListener thumbnailListener;
    private ArrayList<News> relatedNewsList = new ArrayList<>();


    public DetailNewsAdapter(Context mContext, OnLeaderActionClick onLeaderClick, LeaderAdapterInterface adapterInterface) {
        this.mContext = mContext;
        this.onLeaderClick = onLeaderClick;
        this.adapterInterface = adapterInterface;
        thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();
        thumbnailListener = new ThumbnailListener();
    }

    public interface OnLeaderActionClick {
        void onLikeDisClick(int position, int candidate_id, int action);

        void onDeleteClick(int position, int candidate_id);
    }

    public void addNewsOnLeaderModel(News mNews) {
        AllLeaderModel localModel = new AllLeaderModel();
        localModel.setSingleNews(mNews);
        allLeaderModels.add(localModel);
        notifyDataSetChanged();
    }

    public void addRelatedNews(ArrayList<News> relatedNewsList) {
        if (relatedNewsList != null) {
            this.relatedNewsList.clear();
            this.relatedNewsList.addAll(relatedNewsList);
            notifyDataSetChanged();
        }
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LEADER_VIEW) {
            View leader_view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_news_leader, parent, false);
            return leaderViewHolder = new NewsLeaderViewHolder(leader_view);
        } else if (viewType == Constant.Condition.RELATED_NEWS) {
            HeadingnewsPagerBinding relatedNewsView = HeadingnewsPagerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            relatedNewsView.trendingTxtView.setText(parent.getContext().getString(R.string.related_news));
            return new HeadingNewsViewHolder(relatedNewsView);
        } else {
            View news_view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_news_detail, parent, false);
            return new NewsViewHolder(news_view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        if (holder.getItemViewType() == LEADER_VIEW) {
            AllLeaderModel leaderModel = allLeaderModels.get(position);

            final NewsLeaderViewHolder mHolder = (NewsLeaderViewHolder) holder;
            if (position == 1)
                mHolder.like_dis_txt.setVisibility(View.VISIBLE);
            else mHolder.like_dis_txt.setVisibility(View.GONE);

            Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_regular.ttf");

            mHolder.name_view.setTypeface(ExtraUtils.getRobotoMedium(mContext));
            mHolder.pos_view.setTypeface(ExtraUtils.getRobotoRegular(mContext));
            mHolder.leader_constituency_view.setTypeface(ExtraUtils.getRobotoRegular(mContext));
            mHolder.leader_constituency_view.setTypeface(ExtraUtils.getRobotoRegular(mContext));

            mHolder.name_view.setTypeface(tf);
            mHolder.name_view.setText(leaderModel.getName());
            mHolder.pos_view.setText(leaderModel.getPosition());
            mHolder.like_txt.setText(String.format(mContext.getString(R.string.upvote_string), formatNumberToShort(leaderModel.getLike_count())));
            mHolder.dislike_txt.setText(String.format(mContext.getString(R.string.downvote_string), formatNumberToShort(leaderModel.getDislike_count())));

            if (leaderModel.getIsVerify()==1)
             mHolder.verifiedLeaderView.setVisibility(View.VISIBLE);
            else
             mHolder.verifiedLeaderView.setVisibility(View.GONE);
            if (leaderModel.getUser_vote_action() == Constant.Action.LIKE) {
                mHolder.like_button.setImageResource(R.drawable.upvote_active);
                mHolder.dis_like_button.setImageResource(R.drawable.downvote_deactive);
            } else if (leaderModel.getUser_vote_action() == Constant.Action.DISLIKE) {
                mHolder.like_button.setImageResource(R.drawable.upvote_deactive);
                mHolder.dis_like_button.setImageResource(R.drawable.downvote_active);
            } else {
                mHolder.like_button.setImageResource(R.drawable.upvote_deactive);
                mHolder.dis_like_button.setImageResource(R.drawable.downvote_deactive);
            }

            Picasso.with(mContext).cancelRequest(mHolder.leaderImageView);
            if (leaderModel.getProfileImage() != null && !TextUtils.isEmpty(leaderModel.getProfileImage()) && leaderModel.getProfileImage().length() > 4) {
                Picasso.with(mContext).load(leaderModel.getProfileImage()).placeholder(R.drawable.sample_image).error(R.drawable.error_placeholder).into(mHolder.leaderImageView);
            } else {
                mHolder.leaderImageView.setImageResource(R.drawable.sample_image);
            }

            mHolder.leader_constituency_view.setText(leaderModel.getLocation());
            mHolder.news_count.setOnClickListener(v -> adapterInterface.onLeaderNewsClick(mHolder.getAdapterPosition(), leaderModel));
            mHolder.RL_main_layout.setOnClickListener(v -> adapterInterface.onLeaderClick(mHolder.getAdapterPosition(), leaderModel));
            mHolder.ll_like.setOnClickListener(v -> {
                if (leaderModel.getLike_action() == Constant.Action.LIKE) {
                    onDelete(mHolder, leaderModel.getDislike_count(), leaderModel.getLike_count());
                    onLeaderClick.onDeleteClick(position, leaderModel.getId());
                    leaderModel.setUser_vote_action(Constant.ZERO);
                } else {
                    onLike(mHolder, leaderModel.getDislike_count(), leaderModel.getLike_count());
                    onLeaderClick.onLikeDisClick(position, leaderModel.getId(), Constant.Action.LIKE);
                    leaderModel.setUser_vote_action(Constant.Action.LIKE);

                }
            });

            mHolder.ll_dis_like.setOnClickListener(v -> {
                if (leaderModel.getLike_action() == Constant.Action.DISLIKE) {
                    onDelete(mHolder, leaderModel.getDislike_count(), leaderModel.getLike_count());
                    onLeaderClick.onDeleteClick(position, leaderModel.getId());
                    leaderModel.setUser_vote_action(Constant.ZERO);

                } else {
                    onDisLike(mHolder, leaderModel.getDislike_count(), leaderModel.getLike_count());
                    onLeaderClick.onLikeDisClick(position, leaderModel.getId(), Constant.Action.DISLIKE);
                    leaderModel.setUser_vote_action(Constant.Action.DISLIKE);

                }
            });
        } else if (holder.getItemViewType() == Constant.Condition.RELATED_NEWS) {
            HeadingNewsViewHolder viewHolder = (HeadingNewsViewHolder) holder;
            viewHolder.onBind(position, relatedNewsList);
        } else {
            AllLeaderModel leaderModel = allLeaderModels.get(position);
            NewsViewHolder mHolder = (NewsViewHolder) holder;
            News newsDetailModel = leaderModel.getSingleNews();
            mHolder.time_text.setText(newsDetailModel.getTime());
            mHolder.headline_text.setText(newsDetailModel.getHeading().trim());
            mHolder.channelNameView.setText(newsDetailModel.getSource());
            if (!TextUtils.isEmpty(newsDetailModel.getImageSourceName())) {
                mHolder.imageSourceView.setText(String.format(mContext.getString(R.string.image_src_text), newsDetailModel.getImageSourceName()));
            } else mHolder.imageSourceView.setVisibility(View.GONE);
            mHolder.sourceCardView.setOnClickListener(v -> {

                if (newsDetailModel.getSourceId() != null && newsDetailModel.getSourceId() != MOLITICS_MEDIA) {
                    Intent intent = new Intent(mContext, ChannelBankActivity.class);
                    intent.putExtra(Constant.IntentKey.CHANNEL_ID, newsDetailModel.getSourceId());
                    intent.putExtra(Constant.IntentKey.SOURCE, newsDetailModel.getSource());
                    intent.putExtra(Constant.IntentKey.SOURCE_IMAGE, newsDetailModel.getSourceLogo());
                    mContext.startActivity(intent);
                }
            });

            if (!TextUtils.isEmpty(newsDetailModel.getSourceLogo())) {
                Picasso.with(mContext).load(newsDetailModel.getSourceLogo()).into(mHolder.channelLogoView);
            }
            //// mHolder.news_text.setText(Html.fromHtml(newsDetailModel.getContent(), null, new UlTagHandler()));
            stripUnderlines(mHolder.news_text, newsDetailModel.getContent());

            if (!TextUtils.isEmpty(newsDetailModel.getLocation())) {
                mHolder.location_txt.setVisibility(View.VISIBLE);
                mHolder.location_txt.setText(newsDetailModel.getLocation());
            } else {
                mHolder.location_txt.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(newsDetailModel.getAuthor_name())) {
                mHolder.author_name_txt.setText(newsDetailModel.getAuthor_name());
                mHolder.author_name_txt.setVisibility(View.VISIBLE);
            }
            if (newsDetailModel.getDisplayType() == 4) {
                mHolder.summaryTctView.setVisibility(View.GONE);
            } else
                mHolder.summaryTctView.setVisibility(View.VISIBLE);

            if (newsDetailModel.getType() == 2 || !TextUtils.isEmpty(newsDetailModel.getLink())) {
                mHolder.expand_image.setVisibility(View.GONE);
                mHolder.video_play_icon.setVisibility(View.VISIBLE);
                mHolder.thumbnail.setVisibility(View.VISIBLE);
                mHolder.thumbnail.setTag(newsDetailModel.getLink());
                YouTubeThumbnailLoader loader = thumbnailViewToLoaderMap.get(mHolder.thumbnail);
                if (loader == null) {
                    mHolder.thumbnail.setTag(newsDetailModel.getLink());
                } else {
                    mHolder.thumbnail.setImageResource(R.drawable.loading_thumbnail);
                    loader.setVideo(newsDetailModel.getLink());
                }
            } else if (newsDetailModel.getType() == 5) {
                loadImage(newsDetailModel.getImage(), mHolder);
                mHolder.time_text.setText(ExtraUtils.setBoldPinkLastString(mContext, newsDetailModel.getTime(), "(Published by leader)", 1.2f));
            } else {
                loadImage(newsDetailModel.getImage(), mHolder);
            }

            mHolder.author_name_txt.setOnClickListener(v -> {
                if (newsDetailModel.getAuthorId() != null) {
                    Intent mIntent = new Intent(mContext, VoiceCreatorProfile.class);
                    mIntent.putExtra(Constant.IntentKey.USER_ID, newsDetailModel.getAuthorId());
                    mContext.startActivity(mIntent);
                }
            });
            mHolder.thumbnail.setOnClickListener(v -> playVideo(newsDetailModel.getLink()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (allLeaderModels.size() == position && relatedNewsList.size() > 0) {
            return Constant.Condition.RELATED_NEWS;
        } else if (allLeaderModels.get(position).getSingleNews() == null) {
            return LEADER_VIEW;
        } else {
            return NEWS_VIEW;
        }
    }

    @Override
    public int getItemCount() {
        return relatedNewsList.size() > 0 ? allLeaderModels.size() + 1 : allLeaderModels.size();
    }

    public void addLeaderList(ArrayList<AllLeaderModel> allLeaderModels) {
        while (this.allLeaderModels.size() > 1) {
            this.allLeaderModels.remove(this.allLeaderModels.size() - 1);
        }
        this.allLeaderModels.addAll(allLeaderModels);
        notifyDataSetChanged();
    }

    public static class NewsLeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_view)
        TextView name_view;
        @BindView(R.id.pos_view)
        TextView pos_view;
        @BindView(R.id.leader_constituency_view)
        TextView leader_constituency_view;
        @BindView(R.id.news_count)
        TextView news_count;

        @BindView(R.id.leaderImageView)
        CircleImageView leaderImageView;
        @BindView(R.id.RL_main_layout)
        RelativeLayout RL_main_layout;
        @BindView(R.id.like_button)
        ImageView like_button;
        @BindView(R.id.like_txt)
        TextView like_txt;
        @BindView(R.id.dis_like_button)
        ImageView dis_like_button;
        @BindView(R.id.dislike_txt)
        TextView dislike_txt;
        @BindView(R.id.ll_like)
        LinearLayout ll_like;
        @BindView(R.id.ll_dis_like)
        LinearLayout ll_dis_like;
        @BindView(R.id.like_dis_txt)
        TextView like_dis_txt;
        @BindView(R.id.verifiedLeaderView)
        ImageView verifiedLeaderView;

        public NewsLeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.time_text)
        TextView time_text;
        @BindView(R.id.location_txt)
        TextView location_txt;
        @BindView(R.id.headline_text)
        TextView headline_text;
        @BindView(R.id.news_text)
        TextView news_text;
        @BindView(R.id.expand_image)
        ImageView expand_image;
        @BindView(R.id.thumbnail)
        YouTubeThumbnailView thumbnail;
        @BindView(R.id.video_play_icon)
        ImageView video_play_icon;

        @BindView(R.id.author_name_txt)
        TextView author_name_txt;
        @BindView(R.id.channelLogoView)
        ImageView channelLogoView;
        @BindView(R.id.channelNameView)
        TextView channelNameView;
        @BindView(R.id.sourceCardView)
        CardView sourceCardView;
        @BindView(R.id.imageSourceView)
        TextView imageSourceView;
        @BindView(R.id.summaryTctView)
        TextView summaryTctView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            thumbnail.initialize(MolticsApplication.YOUTUBE_KEY, thumbnailListener);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

    }

    public void followDone(int mPosition, String follow_count) {
        AllLeaderModel allLeaderModel = allLeaderModels.get(mPosition);
        allLeaderModel.setIsFollowing(1);
        allLeaderModel.setFollowers(follow_count);
        allLeaderModels.set(mPosition, allLeaderModel);
        notifyDataSetChanged();
    }

    public void unFollowDone(int mPosition, String follow_count) {
        AllLeaderModel allLeaderModel = allLeaderModels.get(mPosition);
        allLeaderModel.setIsFollowing(0);
        allLeaderModel.setFollowers(follow_count);
        allLeaderModels.set(mPosition, allLeaderModel);
        notifyDataSetChanged();
    }


    public void onUnSuccessFollow(int mPosition) {
        AllLeaderModel allLeaderModel = allLeaderModels.get(mPosition);
        allLeaderModel.setIsFollowing(0);
        allLeaderModels.set(mPosition, allLeaderModel);
        notifyItemChanged(mPosition);
    }

    public void onUnSuccessUnFollow(int mPosition) {
        AllLeaderModel allLeaderModel = allLeaderModels.get(mPosition);
        allLeaderModel.setIsFollowing(1);
        allLeaderModels.set(mPosition, allLeaderModel);
        notifyItemChanged(mPosition);

    }

    public void likeDone(int mPosition, int like, int dislike) {
        AllLeaderModel allLeaderModel = allLeaderModels.get(mPosition);
        allLeaderModel.setLike_action(Constant.Action.LIKE);
        allLeaderModel.setLike_count(like);
        allLeaderModel.setDislike_count(dislike);
        allLeaderModels.set(mPosition, allLeaderModel);
        // notifyItemChanged(mPosition);
        notifyDataSetChanged();
    }

    public void disLikeDone(int mPosition, int like, int dislike) {
        AllLeaderModel allLeaderModel = allLeaderModels.get(mPosition);
        allLeaderModel.setLike_action(Constant.Action.DISLIKE);
        allLeaderModel.setLike_count(like);
        allLeaderModel.setDislike_count(dislike);
        allLeaderModels.set(mPosition, allLeaderModel);
        //  notifyItemChanged(mPosition);
        notifyDataSetChanged();
    }

    public void deleteDone(int mPosition, int like, int dislike) {
        AllLeaderModel allLeaderModel = allLeaderModels.get(mPosition);
        allLeaderModel.setLike_action(0);
        allLeaderModel.setLike_count(like);
        allLeaderModel.setDislike_count(dislike);
        allLeaderModels.set(mPosition, allLeaderModel);
        //  notifyItemChanged(mPosition);
        notifyDataSetChanged();
    }

    public void onLike(NewsLeaderViewHolder mHolder, int dislike_count, int like_count) {
        mHolder.like_button.setImageResource(R.drawable.upvote_active);
        mHolder.like_txt.setText(String.format(mContext.getString(R.string.upvote_string), formatNumberToShort(like_count)));
        mHolder.dislike_txt.setText(String.format(mContext.getString(R.string.downvote_string), formatNumberToShort(dislike_count)));
        mHolder.dis_like_button.setImageResource(R.drawable.downvote_deactive);
    }

    public void onDelete(NewsLeaderViewHolder mHolder, int dislike_count, int like_count) {
        mHolder.like_button.setImageResource(R.drawable.upvote_deactive);
        mHolder.like_txt.setText(String.format(mContext.getString(R.string.upvote_string), formatNumberToShort(like_count)));
        mHolder.dislike_txt.setText(String.format(mContext.getString(R.string.downvote_string), formatNumberToShort(dislike_count)));
        mHolder.dis_like_button.setImageResource(R.drawable.downvote_deactive);
    }

    public void onDisLike(NewsLeaderViewHolder mHolder, int dislike_count, int like_count) {
        mHolder.dis_like_button.setImageResource(R.drawable.downvote_active);
        mHolder.like_txt.setText(String.format(mContext.getString(R.string.upvote_string), formatNumberToShort(like_count)));
        mHolder.dislike_txt.setText(String.format(mContext.getString(R.string.downvote_string), formatNumberToShort(dislike_count)));
        mHolder.like_button.setImageResource(R.drawable.upvote_deactive);
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

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        ///  releaseLoaders();
    }

    public void releaseLoaders() {
        for (YouTubeThumbnailLoader loader : thumbnailViewToLoaderMap.values()) {
            if (loader != null)
                loader.release();
        }
    }

    private void playVideo(String link) {
        Intent intent = new Intent(mContext, YoutubePlayActivity.class);
        intent.putExtra(Constant.IntentKey.LINK, link);
        mContext.startActivity(intent);
    }


    private void loadImage(String url, NewsViewHolder holder) {
        //show image
        holder.expand_image.setVisibility(View.VISIBLE);
        holder.video_play_icon.setVisibility(View.GONE);

        if (url != null && !TextUtils.isEmpty(url)) {
            Picasso.with(mContext).load(url).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.image_placeholder).error(R.drawable.internet_no_cloud).into(holder.expand_image, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError() {
                    Picasso.with(mContext).load(url).placeholder(R.drawable.image_placeholder).error(R.drawable.internet_no_cloud).into(holder.expand_image);
                }
            });
        }
        holder.video_play_icon.setVisibility(View.GONE);
    }

    private void stripUnderlines(TextView textView, String text) {
        if (!TextUtils.isEmpty(text)) {
            String htmlString = replaceHtml(text);
            CharSequence sequence = Html.fromHtml(htmlString, FROM_HTML_MODE_LEGACY, null, new UlTagHandler());
            SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
            URLSpan[] spans = strBuilder.getSpans(0, strBuilder.length(), URLSpan.class);
            for (URLSpan span : spans) {
                makeLinkClickable(strBuilder, span);
            }
            textView.setText(strBuilder);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    protected void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span) {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
                String link = span.getURL();
                int index = link.lastIndexOf('/');
                if (link.contains(Constant.ShareLinkTag.NEWS)) {
                    String linkID = link.substring(0, index);
                    int deepLinkId = Integer.parseInt(linkID.replaceFirst(".*//*(\\w+).*", "$1"));
                    startDetailNews(deepLinkId, 1);
                } else if (link.contains(Constant.ShareLinkTag.ARTICLE)) {
                    String linkID = link.substring(0, index);
                    int deepLinkId = Integer.parseInt(linkID.replaceFirst(".*//*(\\w+).*", "$1"));
                    startDetailNews(deepLinkId, 4);
                } else {

                    Intent mIntent = new Intent(mContext, TermConditionActivity.class);
                    mIntent.putExtra(Constant.IntentKey.TOOL_BAR, "Molitics");
                    mIntent.putExtra(Constant.IntentKey.URL, link);
                    mContext.startActivity(mIntent);
                    ///Util.showLog("handle click", span.getURL());
                    // Do something with span.getURL() to handle the link click...
                }
            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

    private void startDetailNews(int news_id, int display_type) {
        Intent newsIntent = new Intent(mContext, DetailNewsActivity.class);
        newsIntent.putExtra(Constant.IntentKey.NEWS_POSITION, 0);
        newsIntent.putExtra(Constant.IntentKey.DISPLAY_TYPE, display_type);
        newsIntent.putExtra(Constant.IntentKey.NEWS_ID, news_id);
        mContext.startActivity(newsIntent);
    }
}
