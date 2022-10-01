package com.cadreamrs.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.cadreamrs.R;
import com.cadreamrs.common.SharedPrefManager;
import com.cadreamrs.views.CustomTextView;
import com.ct7ct7ct7.androidvimeoplayer.model.PlayerState;
import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayerView;

public class VimeoPlayerActivity extends AppCompatActivity {
    private VimeoPlayerView vimeoPlayer;
    private int REQUEST_CODE = 1234;
    public static final String RESULT_STATE_VIDEO_PLAY_AT = "RESULT_STATE_VIDEO_PLAY_AT";
    public static final String RESULT_STATE_PLAYER_STATE = "RESULT_STATE_PLAYER_STATE";
    public static final String REQUEST_ORIENTATION_LANDSCAPE = "REQUEST_ORIENTATION_LANDSCAPE";
    public static final String REQUEST_ORIENTATION_PORTRAIT = "REQUEST_ORIENTATION_LANDSCAPE";

    private static final String EXTRA_ORIENTATION = "EXTRA_ORIENTATION";
    private FrameLayout view_data;
    private String getUserName = "", str_userID = "";
    private CustomTextView tvWaterMark;
    private String orientation;
    private String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //getSupportActionBar().hide();


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_viemo__player);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        /*getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        view_data = findViewById(R.id.view);

//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        view_data.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
        view_data.getLayoutParams().width = RelativeLayout.LayoutParams.MATCH_PARENT;

        str_userID = SharedPrefManager.getPreferences(this, "sharedPref_userID");
        getUserName = SharedPrefManager.getPreferences(this, "username");

        Intent intent = getIntent();
        if (intent.hasExtra("EMBDEDID")) {
            videoId = getIntent().getStringExtra("EMBDEDID");
        }

        vimeoPlayer = findViewById(R.id.vimeoPlayer);
        tvWaterMark = findViewById(R.id.tvWaterMark);

        vimeoPlayer.setFullscreenClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onsetData();
            }
        });

        RUN_PLAYER();
        tvWaterMark.setText(getUserName + "_" + str_userID);
        tvWaterMark.bringToFront();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
        tvWaterMark.setAnimation(animation);

    }

    private void RUN_PLAYER() {
        vimeoPlayer.initialize(Integer.parseInt(videoId));
        vimeoPlayer.setFullscreenVisibility(true);
        vimeoPlayer.getCurrentTimeSeconds();
        vimeoPlayer.pause();
    }


    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
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
        vimeoPlayer.initialize(000000);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        vimeoPlayer.initialize(000000);
//        vimeoPlayer.initialize(000000, "https://player.vimeo.com/video/");
        vimeoPlayer.setFullscreenVisibility(true);
        vimeoPlayer.getCurrentTimeSeconds();
        vimeoPlayer.pause();
        finish();

    }

}
