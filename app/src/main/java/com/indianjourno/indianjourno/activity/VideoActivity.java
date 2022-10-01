package com.indainjourno.indianjourno.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.janatasuddi.janatasuddinews.R;
import com.janatasuddi.janatasuddinews.adapter.AdapterVideoListRelated;
import com.janatasuddi.janatasuddinews.api.RetrofitClient;
import com.janatasuddi.janatasuddinews.model.videos.ModelVideos;
import com.janatasuddi.janatasuddinews.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private String strVideoLink;
    private String strVideoId;
    private String strVideoCatId;
    private String strVideoTitle;
    private String strVideoDate;

    @BindView(R.id.tvVideoTitle)
    protected TextView tvVideoTitle;
    @BindView(R.id.tvVideoDate)
    protected TextView tvVideoDate;
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
        strVideoId = getIntent().getStringExtra(Constant.VIDEO_ID);
        strVideoCatId = getIntent().getStringExtra(Constant.CAT_ID);
        strVideoTitle = getIntent().getStringExtra(Constant.VIDEO_TITLE);
        strVideoDate = getIntent().getStringExtra(Constant.VIDEO_DATE);
        tvVideoTitle.setText(strVideoTitle);
        tvVideoDate.setText(strVideoDate);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Constant.DEVELOPER_KEY, this);
        callApi();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(strVideoLink); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo

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
        } else {
            String error = String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Constant.DEVELOPER_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }
    private void callApi(){
        Call<ModelVideos> call = RetrofitClient.getInstance().getApi().getVideos(strVideoCatId);
        call.enqueue(new Callback<ModelVideos>() {
            @Override
            public void onResponse(Call<ModelVideos> call, Response<ModelVideos> response) {
                ModelVideos  tModelVideos = response.body();
                pbVideoActivity.setVisibility(View.GONE);
                AdapterVideoListRelated tAdapterNewsList = new AdapterVideoListRelated(tModelVideos.getVideos(), strVideoCatId);
                rvVideoPlayerList.setAdapter(tAdapterNewsList);
            }

            @Override
            public void onFailure(Call<ModelVideos> call, Throwable t) {

            }
        });    }


}