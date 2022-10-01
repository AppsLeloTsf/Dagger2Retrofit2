package com.molitics.molitician.ui.dashboard.nationalNews.adapter;

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
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.molitics.molitician.BuildConfig;
import com.molitics.molitician.R;
import com.molitics.molitician.model.News;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.channels.ChannelBankActivity;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.OnNewsItemClick;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.molitics.molitician.MolticsApplication.YOUTUBE_KEY;
import static com.molitics.molitician.util.Util.attachLeaderToLinearView;


/**
 * Created by rahul  on 4/13/2016.
 */
public class HeaderFragmentPagerAdapter extends PagerAdapter implements BranchShareClass.DeepLinkCallBack {

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

    private Context mContext;
    private List<News> newsheadingItems;
    private OnNewsItemClick onItemTouchListener;
    private final Map<YouTubeThumbnailView, YouTubeThumbnailLoader> thumbnailViewToLoaderMap;
    private final ThumbnailListener thumbnailListener;

    public HeaderFragmentPagerAdapter(Context context, List<News> newsheadingItems, OnNewsItemClick onItemTouchListener) {
        this.mContext = context;
        this.newsheadingItems = newsheadingItems;
        this.onItemTouchListener = onItemTouchListener;
        thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();
        thumbnailListener = new ThumbnailListener();
    }

    @Override
    public int getCount() {
        return newsheadingItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        News newsItem = newsheadingItems.get(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.news_banner, container, false);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(view ->
                onItemTouchListener.onNewsRecyclerClick(position, Constant.Condition.HEASDER_NEWS)
        );
        thumbnail.setOnClickListener(v ->
                onItemTouchListener.onNewsRecyclerClick(position, Constant.Condition.HEASDER_NEWS_YOUTUBE)
        );
        news_headline.setTypeface(ExtraUtils.getRobotoMedium(mContext));
        news_headline.setText(Html.fromHtml(newsItem.getHeading().trim()));
        channelNameView.setText(newsItem.getSource().trim());
        sponser_news_post_time.setText(newsItem.getTime());
        attachLeaderToLinearView(mContext, attachedLeaderView, newsItem.getTaggedLeader());


        shareNewsView.setOnClickListener(view -> {
            BranchShareClass.generateNewsShareLink(this, newsItem.getHeading(), newsItem.getShareLink());
        });

        channelNameView.setOnClickListener(view -> {
            if (!newsItem.getSource().contains(Constant.MOLITICS)) {
                Intent intent = new Intent(mContext, ChannelBankActivity.class);
                intent.putExtra(Constant.IntentKey.CHANNEL_ID, newsItem.getSourceId());
                intent.putExtra(Constant.IntentKey.SOURCE, newsItem.getSource());
                intent.putExtra(Constant.IntentKey.SOURCE_IMAGE, newsItem.getSourceLogo());
                mContext.startActivity(intent);
            }
        });

        channelImageView.setOnClickListener(view -> {
            if (!newsItem.getSource().contains(Constant.MOLITICS)) {
                Intent intent = new Intent(mContext, ChannelBankActivity.class);
                intent.putExtra(Constant.IntentKey.CHANNEL_ID, newsItem.getSourceId());
                intent.putExtra(Constant.IntentKey.SOURCE, newsItem.getSource());
                intent.putExtra(Constant.IntentKey.SOURCE_IMAGE, newsItem.getSourceLogo());
                mContext.startActivity(intent);
            }
        });

        if (!TextUtils.isEmpty(newsItem.getSourceLogo())) {
            Picasso.with(mContext).load(newsItem.getSourceLogo()).into(channelImageView);
        }

        if (newsItem.getType() == 0 || newsItem.getType() == 1) {
            if (newsItem.getImage() != null && !TextUtils.isEmpty(newsItem.getImage())) {
                Picasso.with(mContext).load(newsItem.getImage()).placeholder(R.drawable.image_placeholder).error(R.drawable.internet_no_cloud).into(news_image);
            }
            video_play_icon.setVisibility(View.GONE);
            thumbnail.setVisibility(View.GONE);
        } else if (newsItem.getType() == 2) {
            thumbnail.initialize(YOUTUBE_KEY, thumbnailListener);
            news_image.setVisibility(View.GONE);
            video_play_icon.setVisibility(View.VISIBLE);
            thumbnail.setVisibility(View.VISIBLE);
            thumbnail.setTag(newsItem.getLink());
            YouTubeThumbnailLoader loader = thumbnailViewToLoaderMap.get(thumbnail);
            if (loader == null) {
                thumbnail.setTag(newsItem.getLink());
            } else {
                thumbnail.setImageResource(R.drawable.loading_thumbnail);
                loader.setVideo(newsItem.getLink());
            }
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String headLine;
        headLine = full_txt + "-" + "Molitics";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, headLine);
        shareIntent.putExtra(Intent.EXTRA_TITLE, "Molitics");
        String shareText = headLine + "\n" + url + "\n" + "via @MoliticsIndia";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        mContext.startActivity(Intent.createChooser(shareIntent, "Share link using"));
    }

    private final class ThumbnailListener implements
            YouTubeThumbnailView.OnInitializedListener,
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onInitializationSuccess(
                YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
            loader.setOnThumbnailLoadedListener(this);
            thumbnailViewToLoaderMap.put(view, loader);
            view.setImageResource(R.drawable.image_placeholder);
            String videoId = (String) view.getTag();
            loader.setVideo(videoId);
        }

        @Override
        public void onInitializationFailure(
                YouTubeThumbnailView view, YouTubeInitializationResult loader) {
            view.setImageResource(R.drawable.image_placeholder);
        }

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView view, String videoId) {

        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView view, YouTubeThumbnailLoader.ErrorReason errorReason) {
            view.setImageResource(R.drawable.error_placeholder);
        }
    }

    public void releaseLoaders() {
        for (YouTubeThumbnailLoader loader : thumbnailViewToLoaderMap.values()) {
            if (loader != null)
                loader.release();
        }
    }
}