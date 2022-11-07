package com.ca_dreamers.cadreamers.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.ca_dreamers.cadreamers.views.CustomTextView;
import com.ct7ct7ct7.androidvimeoplayer.model.PlayerState;
import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayerView;


import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VimeoPlayerActivity extends AppCompatActivity implements Serializable{


    public static final String RESULT_STATE_VIDEO_PLAY_AT = "RESULT_STATE_VIDEO_PLAY_AT";
    public static final String RESULT_STATE_PLAYER_STATE = "RESULT_STATE_PLAYER_STATE";
    private int videoId;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.view)
    protected RelativeLayout view_data;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.vimeoPlayer)
    protected VimeoPlayerView vimeoPlayer;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvWaterMark)
    protected CustomTextView tvWaterMark;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_viemo_player);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ButterKnife.bind(this);

        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
        view_data.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
        view_data.getLayoutParams().width = RelativeLayout.LayoutParams.MATCH_PARENT;

        String strUserPhone = sharedPrefManager.getUserMobile();
        String getUserName = sharedPrefManager.getUserName();

        Intent intent = getIntent();
        if (intent.hasExtra(Constant.CONTENT_EMBED)) {
            videoId = Integer.parseInt(getIntent().getStringExtra(Constant.CONTENT_EMBED));
        }

        vimeoPlayer.setFullscreenClickListener(v -> onsetData());

        RUN_PLAYER();
        tvWaterMark.setText(getUserName +"\n"+ strUserPhone);
        tvWaterMark.bringToFront();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
        tvWaterMark.setAnimation(animation);

    }

    private void RUN_PLAYER() {
        vimeoPlayer.initialize(true, videoId);
        vimeoPlayer.setFullscreenVisibility(true);
        vimeoPlayer.getCurrentTimeSeconds();
        vimeoPlayer.pause();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int REQUEST_CODE = 1234;
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            float playAt = data.getFloatExtra(RESULT_STATE_VIDEO_PLAY_AT, 0f);
            vimeoPlayer.seekTo(playAt);

            PlayerState playerState = PlayerState.valueOf(data.getStringExtra(RESULT_STATE_PLAYER_STATE));
            switch (playerState) {
                case PLAYING:
                    vimeoPlayer.play();
                    break;

                case PAUSED:
                    vimeoPlayer.pause();
                    break;

            }
        }
    }

    private void onsetData() {
        Intent intent = new Intent();
        intent.putExtra(RESULT_STATE_VIDEO_PLAY_AT, vimeoPlayer.getCurrentTimeSeconds());
        intent.putExtra(RESULT_STATE_PLAYER_STATE, vimeoPlayer.getPlayerState().name());
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        vimeoPlayer.pause();
       // vimeoPlayer.initialize(true, 000000);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        vimeoPlayer.setFullscreenVisibility(true);
        vimeoPlayer.getCurrentTimeSeconds();
        vimeoPlayer.play();
    }

    @Override
    protected void onPause() {
        super.onPause();
        vimeoPlayer.pause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        vimeoPlayer.initialize(true, 000000);
        vimeoPlayer.setFullscreenVisibility(true);
        vimeoPlayer.getCurrentTimeSeconds();
        vimeoPlayer.pause();
        finish();
    }

}
