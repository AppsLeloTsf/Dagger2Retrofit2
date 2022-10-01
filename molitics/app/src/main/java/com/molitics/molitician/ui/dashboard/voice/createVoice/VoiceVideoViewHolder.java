package com.molitics.molitician.ui.dashboard.voice.createVoice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.util.CompressImage;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.VideoExpoPlayer;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.molitics.molitician.util.VideoExpoPlayer.muteVideo;

/**
 * Created by rahul on 05/06/18.
 */

public class VoiceVideoViewHolder extends RecyclerView.ViewHolder implements Player.EventListener, View.OnClickListener {

    @Nullable
    @BindView(R.id.issue_image)
    ImageView issue_image;
    @Nullable
    @BindView(R.id.remove_issue_image)
    ImageView remove_issue_image;
    @Nullable
    @BindView(R.id.voice_video)
    PlayerView voice_video;
    @Nullable
    @BindView(R.id.video_mute_button)
    ImageView video_mute_button;
    @Nullable
    @BindView(R.id.video_placeholder_view)
    ImageView video_placeholder_view;
    @Nullable
    @BindView(R.id.video_progress_bar)
    ProgressBar video_progress_bar;

    private SimpleExoPlayer player;

    private VoiceAllModel voiceAllModel;
    private int holderPosition;
    private CreateVoiceInterface holderVoiceInterface;


    public VoiceVideoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        if (video_mute_button != null)
            video_mute_button.setOnClickListener(this);
        if (remove_issue_image != null)
            remove_issue_image.setOnClickListener(this);

    }

    private void checkVolumeOption() {
        if (video_mute_button != null) {
            video_mute_button.setVisibility(View.VISIBLE);
        }
        if (player != null) {
            if (PrefUtil.getBoolean(Constant.PreferenceKey.VIDEO_MUTE)) {
                video_mute_button.setImageResource(R.drawable.volume_white);
                player.setVolume(1.0f);

            } else {
                video_mute_button.setImageResource(R.drawable.mute_white);
                player.setVolume(0.0f);
            }
        }
    }

    public void bind(Context mContext, int position, Object data, CreateVoiceInterface voiceInterface) {
        if (data instanceof VoiceAllModel) {
            holderPosition = position;
            holderVoiceInterface = voiceInterface;

            voiceAllModel = (VoiceAllModel) data;
            if (remove_issue_image != null) {
                remove_issue_image.setVisibility(View.VISIBLE);
            }
            String imagePath = voiceAllModel.getImages().get(position);
            if (imagePath.contains(Constant.MP4)) {
                if (issue_image != null) {
                    issue_image.setVisibility(View.GONE);
                }
                if (voice_video != null) {
                    voice_video.setVisibility(View.VISIBLE);
                }

                if (imagePath.contains("http")) {
                    if (video_progress_bar != null) {
                        video_progress_bar.setVisibility(View.VISIBLE);
                    }

                    Picasso.with(mContext).load(imagePath.replace(Constant.MP4, Constant.VIDEO_PLACEHOLDER)).into(video_placeholder_view);
                }
                player = VideoExpoPlayer.prepareVideo(mContext, voice_video, Uri.parse(imagePath));
                player.addListener(this);
                player.setPlayWhenReady(false);
                checkVolumeOption();

            } else {
                if (video_mute_button != null) {
                    video_mute_button.setVisibility(View.GONE);
                }
                if (voice_video != null) {
                    voice_video.setVisibility(View.GONE);
                }
                if (issue_image != null) {
                    issue_image.setVisibility(View.VISIBLE);
                    Glide.with(issue_image.getContext()).load(imagePath).into(issue_image);
                }
                //setCompressPic(mContext, imagePath);
            }
        }
    }

    private void setCompressPic(Context mContext, String file_path) {
        //setPic(CompressImage.compressImage(mContext, file_path));
    }

    private void setPic(String image_url) {
        int targetW = 0;
        int targetH = 0;
        if (issue_image != null) {
            targetW = issue_image.getWidth();
            targetH = issue_image.getHeight();
        }

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(image_url, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(image_url, bmOptions);

        issue_image.setImageBitmap(bitmap);
    }

    private void releasePlayer() {
        if (player != null)
            player.release();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        if (playbackState == Player.STATE_READY) {
            if (video_placeholder_view != null) {
                video_placeholder_view.setVisibility(View.GONE);
            }
            if (video_progress_bar != null) {
                video_progress_bar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_mute_button:
                muteVideo(player);
                checkVolumeOption();
                break;

            case R.id.remove_issue_image:
                String image = voiceAllModel.getImages().get(holderPosition);

                if (image.contains("http")) {
                    voiceAllModel.getDeleted_image().add(image);
                }
                releasePlayer();
                voiceAllModel.getImages().remove(image);
                holderVoiceInterface
                        .notifyAdapter(holderPosition);
                break;

        }
    }
}
