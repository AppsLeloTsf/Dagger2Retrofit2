/*
 * Copyright (c) 2017 Nam Nguyen, nam@ene.im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.molitics.molitician.facebook.timeline;

import android.net.Uri;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.ui.PlayerView;
import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroUtil;
import im.ene.toro.exoplayer.ExoPlayerViewHelper;
import im.ene.toro.exoplayer.Playable;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.widget.Container;

import static java.lang.String.format;

/**
 * @author eneim | 6/18/17.
 */

@SuppressWarnings("WeakerAccess") //
public class TimelineVideoViewHolder extends TimelineViewHolder implements ToroPlayer {

    @BindView(R.id.fb_video_player)
    PlayerView playerView;
    @BindView(R.id.player_state)
    TextView state;
    @BindView(R.id.video_progress_bar)
    ProgressBar video_progress_bar;

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
    @Nullable
    private ExoPlayerViewHelper helper;
    @Nullable
    private Uri mediaUri;

    TimelineVideoViewHolder(View itemView) {
        super(itemView);
        playerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setClickListener(View.OnClickListener clickListener) {
        super.setClickListener(clickListener);
        playerView.setOnClickListener(clickListener);
        userIcon.setOnClickListener(clickListener);
    }

    @Override
    void bind(VoiceTimelineAdapter adapter, VoiceAllModel item, List<Object> payloads) {
        super.bind(adapter, item, payloads);
        if (item instanceof VoiceAllModel) {
            for (String url : item.getImages()) {
                if (url.contains(".mp4"))
                    mediaUri = Uri.parse(url);
                break;
            }
            userProfile.setText(item.getLocation());
        }
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
        /////// if (mediaUri == null) throw new IllegalStateException("mediaUri is null.");
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
        return getAdapterPosition();
    }


}
