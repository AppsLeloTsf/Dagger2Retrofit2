package com.molitics.molitician.ui.dashboard.voice.viewHolder;

import android.net.Uri;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ui.PlayerView;
import com.molitics.molitician.R;

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
 * Created by om on 22/05/18.
 */

public class ExpandVideoRecyclerViewHolder extends RecyclerView.ViewHolder implements ToroPlayer {

    static final int LAYOUT_RES = R.layout.issue_image;

    @BindView(R.id.fb_video_player)
    PlayerView playerView;
    @BindView(R.id.video_progress_bar)
    ProgressBar video_progress_bar;
    @BindView(R.id.player_state)
    TextView player_state;
    @BindView(R.id.fb_item_middle)
    FrameLayout fb_item_middle;
    @BindView(R.id.issue_image)
    ImageView issue_image;

    private ExoPlayerViewHelper helper;
    Uri mediaUri;

    private final Playable.EventListener listener = new Playable.DefaultEventListener() {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            super.onPlayerStateChanged(playWhenReady, playbackState);
            if (playWhenReady)
                video_progress_bar.setVisibility(View.GONE);
            else video_progress_bar.setVisibility(View.VISIBLE);
            player_state.setText(format(Locale.getDefault(), "STATE: %d・PWR: %s", playbackState, playWhenReady));
        }
    };


    public ExpandVideoRecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @NonNull
    @Override
    public View getPlayerView() {
        return playerView;
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
        if (helper != null) helper.play();
    }

    @Override
    public void pause() {
        if (helper != null) helper.pause();
    }

    @Override
    public boolean isPlaying() {
        return helper != null && helper.isPlaying();
    }

    @Override
    public void release() {
        if (helper != null) {
            helper.release();
            helper = null;
        }
    }

    @Override
    public boolean wantsToPlay() {
        boolean value = ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 0.85;
        return value;
    }

    @Override
    public int getPlayerOrder() {
        return getAdapterPosition();
    }


    @Override
    public String toString() {
        return "ExoPlayer{" + hashCode() + " " + getAdapterPosition() + "}";
    }
}

