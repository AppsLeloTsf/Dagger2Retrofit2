package com.molitics.molitician.ui.dashboard.voice.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.exoplayer2.ui.PlayerView;
import com.molitics.molitician.R;
import com.molitics.molitician.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroUtil;
import im.ene.toro.exoplayer.ExoPlayerViewHelper;
import im.ene.toro.exoplayer.Playable;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.widget.Container;

import static java.lang.String.format;

/**
 * Created by rahul on 23/11/17.
 */

public class ImageViewPager extends PagerAdapter implements ToroPlayer {

    String TAG = "ImageViewPager";
    Context mContext;
    @BindView(R.id.issue_image)
    ImageView issue_image;
    @BindView(R.id.fb_video_player)
    PlayerView playerView;
    @BindView(R.id.player_state)
    TextView state;
    @BindView(R.id.video_progress_bar)
    ProgressBar video_progress_bar;
    @BindView(R.id.fb_item_middle)
    FrameLayout fb_item_middle;

    private List<String> imageList;
    private VoiceImageListener voiceImageListener;
    private int adapter_position;

    private ExoPlayerViewHelper helper;
    private Uri mediaUri;
    private View itemView;

    private final Playable.EventListener listener = new Playable.DefaultEventListener() {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            super.onPlayerStateChanged(playWhenReady, playbackState);
            if (playWhenReady)
                video_progress_bar.setVisibility(View.GONE);
            else video_progress_bar.setVisibility(View.VISIBLE);
            state.setText(format(Locale.getDefault(), "STATE: %d・PWR: %s", playbackState, playWhenReady));
        }
    };


    public ImageViewPager(Context mContext, View parentView, int adapter_position, List<String> imageList, VoiceImageListener voiceImageListener) {
        this.adapter_position = adapter_position;
        this.mContext = mContext;
        this.imageList = imageList;
        this.voiceImageListener = voiceImageListener;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        itemView = inflater.inflate(R.layout.voice_image_adapter, container, false);
        ButterKnife.bind(this, itemView);

        String url = imageList.get(position);
        if (!TextUtils.isEmpty(url) && !url.contains(".mp4")) {
            issue_image.setVisibility(View.VISIBLE);
            fb_item_middle.setVisibility(View.GONE);
            Picasso.with(mContext).load(url).into(issue_image);
        } else {
            fb_item_middle.setVisibility(View.VISIBLE);

            issue_image.setVisibility(View.GONE);
            mediaUri = Uri.parse(url);

        }

        issue_image.setOnClickListener(v -> voiceImageListener.onImageClick(position, Constant.ZERO, imageList));

        container.addView(itemView, 0);
        return itemView;
    }

    @NonNull
    @Override
    public View getPlayerView() {
        return this.playerView;
    }

    @NonNull
    @Override
    public PlaybackInfo getCurrentPlaybackInfo() {
        return helper != null ? helper.getLatestPlaybackInfo() : new PlaybackInfo();
    }

    @Override
    public void initialize(@NonNull Container container, @NonNull PlaybackInfo playbackInfo) {
        if (helper == null && mediaUri != null) {
            helper = new ExoPlayerViewHelper(this, mediaUri);
            helper.addEventListener(listener);
            helper.initialize(container, playbackInfo);
        }
    }

    @Override
    public void play() {
        voiceImageListener.onVideoPLay();
        if (helper != null) helper.play();
    }

    @Override
    public void pause() {
        voiceImageListener.onVideoPLay();

        if (helper != null) helper.pause();
    }

    @Override
    public boolean isPlaying() {
        return helper != null && helper.isPlaying();
    }

    @Override
    public void release() {
        if (helper != null) {
            helper.removeEventListener(listener);
            helper.release();
            helper = null;
        }
    }

    @Override
    public boolean wantsToPlay() {
        return ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 0.85;
    }

    @Override
    public int getPlayerOrder() {
        return adapter_position;
    }


    public interface VoiceImageListener {
        void onImageClick(int position, int issue_id, List<String> imageList);

        void onVideoPLay();

        void onVideoPause();

        void onShareClick(String url);

        void onDownloadClick(String url);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
