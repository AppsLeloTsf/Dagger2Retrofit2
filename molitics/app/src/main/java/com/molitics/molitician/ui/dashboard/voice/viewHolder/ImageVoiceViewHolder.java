package com.molitics.molitician.ui.dashboard.voice.viewHolder;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.molitics.molitician.GoogleAnalytics.GAEventTracking;
import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.voice.ImageBigFragment;
import com.molitics.molitician.ui.dashboard.voice.adapter.ImageViewPager;
import com.molitics.molitician.ui.dashboard.voice.adapter.TrendingVoiceAdapter;
import com.molitics.molitician.ui.dashboard.voice.video_module.VoiceVideoViewHolder;
import com.molitics.molitician.ui.dashboard.voice.voiceDetail.VoiceDetailAdapter;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.VideoExpoPlayer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroUtil;
import im.ene.toro.exoplayer.ExoPlayerViewHelper;
import im.ene.toro.exoplayer.Playable;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.media.VolumeInfo;
import im.ene.toro.widget.Container;

import static com.molitics.molitician.ui.dashboard.cartoon.MoliticsImageUtils.displayLocalImage;
import static com.molitics.molitician.util.VideoExpoPlayer.muteVideo;

/**
 * Created by om on 21/05/18.
 */

public class ImageVoiceViewHolder extends RecyclerView.ViewHolder implements ToroPlayer, View.OnClickListener {

    @BindView(R.id.main_view)
    AspectRatioFrameLayout main_view;
    @BindView(R.id.fb_video_player)
    PlayerView fb_video_player;
    @BindView(R.id.video_progress_bar)
    ProgressBar video_progress_bar;
    @BindView(R.id.video_mute_button)
    ImageView video_mute_button;
    @BindView(R.id.issue_image)
    ImageView issue_image;
    @Nullable
    @BindView(R.id.download_image)
    ImageView download_image;
    @Nullable
    @BindView(R.id.share_image)
    ImageView share_image;
    @BindView(R.id.video_placeholder_view)
    ImageView video_placeholder_view;
    @BindView(R.id.play_video_button)
    ImageView play_video_button;

    String from;

    private ExoPlayerViewHelper helper;
    private Uri mediaUri;
    private Context mContext;

    ImageViewPager.VoiceImageListener voiceImageListener;
    private EventListener eventListener;

    private int holderPosition;
    private List<String> holderUrlList = new ArrayList<>();

    public ImageVoiceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        main_view.setOnClickListener(this);
        if (download_image != null)
            download_image.setOnClickListener(this);
        if (share_image != null)
            share_image.setOnClickListener(this);
        play_video_button.setOnClickListener(this);
        holderUrlList.clear();
    }

    private final Playable.EventListener listener = new Playable.DefaultEventListener() {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            super.onPlayerStateChanged(playWhenReady, playbackState);
            switch (playbackState) {

                case 2:
                    if (from.equalsIgnoreCase(VoiceVideoViewHolder.TAG)) {
                        video_progress_bar.setVisibility(View.GONE);
                    } else {
                        video_progress_bar.setVisibility(View.VISIBLE);
                    }

                    break;
                case 3:
                    if (from.equalsIgnoreCase(ImageBigFragment.TAG))
                        play_video_button.setVisibility(View.GONE);
                    video_progress_bar.setVisibility(View.GONE);
                    video_placeholder_view.setVisibility(View.GONE);
                    break;

                case 4:
                    play_video_button.setVisibility(View.GONE);
                    video_progress_bar.setVisibility(View.GONE);
                    fb_video_player.getPlayer().seekTo(0);
                    break;
            }
        }
    };


    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @NonNull
    @Override
    public View getPlayerView() {
        return fb_video_player;
    }

    @NonNull
    @Override
    public PlaybackInfo getCurrentPlaybackInfo() {
        return helper != null ? helper.getLatestPlaybackInfo() : new PlaybackInfo();
    }

    @Override
    public void initialize(@NonNull Container container, @NonNull PlaybackInfo playbackInfo) {
        if (helper == null) {
            helper = new ExoPlayerViewHelper(this, mediaUri);
        }
        if (listener != null) {
            helper.addEventListener(listener);
        }
        if (eventListener != null)
            helper.addPlayerEventListener(eventListener);
        helper.initialize(container, playbackInfo);
    }

    @Override
    public void play() {
        voiceImageListener.onVideoPLay();

        if (helper != null) {
            helper.play();

            if (from.equalsIgnoreCase(VoiceVideoViewHolder.TAG) || from.equalsIgnoreCase(ImageBigFragment.TAG) || from.equalsIgnoreCase(VoiceDetailAdapter.TAG)) {
                fb_video_player.getPlayer().setPlayWhenReady(true);
                fb_video_player.setUseController(true);
                checkVolumeOption();
            } else {
                fb_video_player.getPlayer().setPlayWhenReady(false);
            }
        }
    }

    @Override
    public void pause() {
        fb_video_player.setUseController(false);
        voiceImageListener.onVideoPause();
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
            helper.removePlayerEventListener(eventListener);
            helper.release();
            helper = null;
        }
    }

    @Override
    public boolean wantsToPlay() {
        // Util.showLog("wantsToPlay",itemView.getParent()+"");
        return ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 0.70;
    }

    @Override
    public int getPlayerOrder() {
        return getAdapterPosition();
    }


    @Override
    public String toString() {
        return "ExoPlayer{" + hashCode() + " " + getAdapterPosition() + "}";
    }

    @OnClick(R.id.video_mute_button)
    public void onVideoMutClick() {
        muteVideo(helper, video_mute_button);
    }


    private void checkVolumeOption() {
        if (helper != null) {
            video_mute_button.setVisibility(View.VISIBLE);
            if (PrefUtil.getBoolean(Constant.PreferenceKey.VIDEO_MUTE)) {
                video_mute_button.setImageResource(R.drawable.mute_white);
                helper.setVolumeInfo(new VolumeInfo(true, 0.0f));
            } else {
                video_mute_button.setImageResource(R.drawable.volume_white);
                helper.setVolumeInfo(new VolumeInfo(false, 1.0f));
            }
        }
    }

    public void bind(Context mContext, int position, String from, String stringUrl,
                     ImageViewPager.VoiceImageListener voiceImageListener) {

        this.mContext = mContext;
        this.from = from;

        holderPosition = position;
        holderUrlList.add(stringUrl);
        if (share_image != null)
            share_image.setTag(stringUrl);
        this.voiceImageListener = voiceImageListener;
        handleUrl(stringUrl);
    }

    private void handleUrl(String stringUrl) {
        if (stringUrl.contains(".")) {
            String extension = stringUrl.substring(stringUrl.lastIndexOf(".")).toLowerCase();

            if (VideoExpoPlayer.imageExtension.contains(extension)) {
                if (from.equalsIgnoreCase(TrendingVoiceAdapter.TAG)) {
                    main_view.getLayoutParams().height = ExtraUtils.convertDpToPx(mContext, 180);
                }
                issue_image.setVisibility(View.VISIBLE);
                fb_video_player.setVisibility(View.GONE);
                if (stringUrl.contains("http"))
                    Picasso.with(mContext).load(stringUrl).placeholder(R.drawable.image_placeholder).into(issue_image);
                else
                    displayLocalImage(issue_image, stringUrl);
            } else {
                mediaUri = Uri.parse(stringUrl);
                if (from.equalsIgnoreCase(TrendingVoiceAdapter.TAG)) {
                    fb_video_player.getLayoutParams().height = ExtraUtils.convertDpToPx(mContext, 100);
                }
                fb_video_player.setVisibility(View.VISIBLE);
                issue_image.setVisibility(View.GONE);
                if (from.equalsIgnoreCase(VoiceVideoViewHolder.TAG) || from.equalsIgnoreCase(VoiceDetailAdapter.TAG)) {

                    video_progress_bar.setVisibility(View.VISIBLE);
                    play_video_button.setVisibility(View.GONE);
                    setGAEvent(Constant.GoogleAnalyticsKey.VIDEO_PLAY + "  " + stringUrl);
                } else {
                    video_progress_bar.setVisibility(View.GONE);
                    play_video_button.setVisibility(View.VISIBLE);
                }
                showVideoPlaceholder();
            }
        }
    }

    private void showVideoPlaceholder() {
        video_placeholder_view.setVisibility(View.VISIBLE);
        String mediaUrl = mediaUri.toString();
        String extension = mediaUrl.substring(mediaUrl.lastIndexOf("."));
        String video_placeholder_url = mediaUrl.replace(extension, Constant.VIDEO_PLACEHOLDER);
        Picasso.with(mContext).load(video_placeholder_url).into(video_placeholder_view);
    }

    private void setGAEvent(String action) {
        GAEventTracking.googleEventTracker((Activity) mContext, Constant.GoogleAnalyticsKey.RYV, action);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_view:
            case R.id.play_video_button:
                voiceImageListener.onImageClick(holderPosition, Constant.ZERO, holderUrlList);
                break;
            case R.id.download_image:
                voiceImageListener.onDownloadClick(holderUrlList.get(Constant.ZERO));
                break;
            case R.id.share_image:
                voiceImageListener.onShareClick((String) (share_image != null ? share_image.getTag() : ""));
                break;
        }
    }
}
