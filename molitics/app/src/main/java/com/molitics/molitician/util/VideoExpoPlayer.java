package com.molitics.molitician.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.molitics.molitician.R;
import com.molitics.molitician.customView.AndroidBmpUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import im.ene.toro.exoplayer.ExoPlayerViewHelper;
import im.ene.toro.media.VolumeInfo;

import static com.facebook.FacebookSdk.getCacheDir;

/**
 * Created by om on 23/05/18.
 */

public class VideoExpoPlayer {
    public static final List<String> imageExtension = new ArrayList<>(Arrays.asList(".jpg", ".png", ".jpeg"));

    public static SimpleExoPlayer prepareVideo(Context context, PlayerView playerView, Uri path) {
        playerView.setVisibility(View.VISIBLE);

        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory();

        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        SimpleExoPlayer player =
                ExoPlayerFactory.newSimpleInstance(context, trackSelector);


        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, com.google.android.exoplayer2.util.Util.getUserAgent(context, "Molitics"));

        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(path);
        player.prepare(videoSource);
        playerView.setPlayer(player);
        player.setPlayWhenReady(true);
        player.setVolume(0.0f);
        return player;
    }

    public static void muteVideo(SimpleExoPlayer player) {
        if (PrefUtil.getBoolean(Constant.PreferenceKey.VIDEO_MUTE)) {
            PrefUtil.putBoolean(Constant.PreferenceKey.VIDEO_MUTE, false);
            if (player != null)
                player.setVolume(0.0f);
        } else {
            PrefUtil.putBoolean(Constant.PreferenceKey.VIDEO_MUTE, true);
            if (player != null)
                player.setVolume(1.0f);
        }
    }


    public static void muteVideo(ExoPlayerViewHelper helper, ImageView video_mute_button) {
        if (helper != null) {
            if (PrefUtil.getBoolean(Constant.PreferenceKey.VIDEO_MUTE)) {

                PrefUtil.putBoolean(Constant.PreferenceKey.VIDEO_MUTE, false);
                helper.setVolumeInfo(new VolumeInfo(false, 1.0f));
                video_mute_button.setImageResource(R.drawable.volume_white);

            } else {
                PrefUtil.putBoolean(Constant.PreferenceKey.VIDEO_MUTE, true);

                helper.setVolumeInfo(new VolumeInfo(true, 0.0f));
                video_mute_button.setImageResource(R.drawable.mute_white);
            }
        }
    }

    public static String createVideoPlaceholder(String path) {
        File file = new File(path);
        File outputDir = getCacheDir(); // context being the Activity pointer
        File outputFile = null;
        try {
            outputFile = File.createTempFile(generateUUID(), ".jpg", outputDir);
            outputFile.deleteOnExit();
            String filePath = outputFile.getAbsolutePath();
            if (file.exists()) {
                Bitmap bMap = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                try {
                    AndroidBmpUtil.save(bMap, filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return filePath;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createTempImage(Bitmap bitmap) {
        File outputDir = getCacheDir(); // context being the Activity pointer
        File outputFile = null;
        try {
            outputFile = File.createTempFile(generateUUID(), ".jpg", outputDir);
            outputFile.deleteOnExit();
            String filePath = outputFile.getAbsolutePath();
            try {
                AndroidBmpUtil.save(bitmap, filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }

    public static String getMimeType(String url) {
        String type = "";
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static String generateFileName(String url) {

        return generateUUID().replace("-", "") + getFileExtension(url);
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf("."));
        else return "";
    }

    public static String replaceFileToPlaceholder(String fileName) {
        return (fileName.replace(Constant.MP4, "") + Constant.VIDEO_PLACEHOLDER);
    }
}
