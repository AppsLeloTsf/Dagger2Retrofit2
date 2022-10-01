package com.molitics.molitician.ui.dashboard.nationalNews;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.molitics.molitician.MolticsApplication;
import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.customScrollItems.YouTubeFailureRecoveryActivity;
import com.molitics.molitician.util.Constant;

import static com.molitics.molitician.util.Util.getVideoKeyFromUrl;

/**
 * Created by rahul on 12/20/2016.
 */

public class YoutubePlayActivity extends YouTubeFailureRecoveryActivity {

    private String video_url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_news_video);
        Intent intent = getIntent();
        video_url = intent.getStringExtra(Constant.IntentKey.LINK);
        try {

            // Start the MediaController
            YouTubePlayerFragment youTubePlayerFragment =
                    (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
            youTubePlayerFragment.initialize(MolticsApplication.YOUTUBE_KEY, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            player.loadVideo(getVideoKeyFromUrl(video_url));
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
    }
}
