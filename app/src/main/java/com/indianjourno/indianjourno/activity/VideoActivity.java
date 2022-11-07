package com.indianjourno.indianjourno.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import com.indianjourno.indianjourno.adapter.AdapterVideoListRelated;
import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.model.ij_video.ModelVideo;
import com.indianjourno.indianjourno.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private String strVideoLink;
    private String strVideoTitle;

    @BindView(R.id.tvVideoTitle)
    protected TextView tvVideoTitle;
    @BindView(R.id.rvVideoPlayerList)
    protected RecyclerView rvVideoPlayerList;
    @BindView(R.id.pbVideoActivity)
    protected ProgressBar pbVideoActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvVideoPlayerList.setLayoutManager(linearLayoutManager);
        strVideoLink = getIntent().getStringExtra(Constant.VIDEO_URL);
        strVideoTitle = getIntent().getStringExtra(Constant.VIDEO_TITLE);
        tvVideoTitle.setText(strVideoTitle);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Constant.DEVELOPER_KEY, this);
        callApi();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

        if (!wasRestored) {

            player.loadVideo(strVideoLink);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        callApi();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
            Log.d("TSF_APPS", "You Tube UserRecoverableError: "+ errorReason);
        } else {
            Log.d("TSF_APPS", "You Tube Error: "+ errorReason);

            String error = String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            getYouTubePlayerProvider().initialize(Constant.DEVELOPER_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

    private void callApi() {
        Call<ModelVideo> call = RetrofitClient.getInstance().getApi().getAllVideoIj();
        call.enqueue(new Callback<ModelVideo>() {
            @Override
            public void onResponse(Call<ModelVideo> call, Response<ModelVideo> response) {
                ModelVideo  tModelVideos = response.body();
                pbVideoActivity.setVisibility(View.GONE);
                AdapterVideoListRelated tAdapterNewsList = new AdapterVideoListRelated(tModelVideos.getVideo());
                rvVideoPlayerList.setAdapter(tAdapterNewsList);
            }

            @Override
            public void onFailure(Call<ModelVideo> call, Throwable t) {

            }
        });
    }


}