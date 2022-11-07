package com.ca_dreamers.cadreamers.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.utils.Constant;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoActivity extends AppCompatActivity {

    private int stopPosition;

    private MediaController mediaController;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.videoView)
    protected VideoView videoView;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_video);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ButterKnife.bind(this);
        if (mediaController == null) {
            mediaController = new MediaController(VideoActivity.this);
            mediaController.setAnchorView(mediaController);
        }
        videoView.setMediaController(mediaController);
        videoView.canPause();
        videoView.canSeekForward();
        videoView.canSeekBackward();
        String strTitle = getIntent().getStringExtra(Constant.MY_FILE_PATH);
        Uri video = Uri.fromFile(new File(strTitle));
        videoView.setVideoURI(video);
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            Toast.makeText(VideoActivity.this, "Video is playing ", Toast.LENGTH_SHORT).show();
            videoView.start();
        });
        videoView.setOnErrorListener((mp, what, extra) -> {
            Log.e("ERROR_VIDEO", "VIDEO: "+mp);
            Toast.makeText(VideoActivity.this, "Oops... Some error is there while playing video", Toast.LENGTH_SHORT).show();
            return false;

        });

        videoView.setOnCompletionListener(mp -> Toast.makeText(VideoActivity.this, "Thank you ...", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onPause() {
        super.onPause();
        stopPosition = videoView.getCurrentPosition(); //stopPosition is an int
        videoView.pause();
    }
    @Override
    public void onResume() {
        super.onResume();
        videoView.seekTo(stopPosition);
        videoView.start(); //Or use resume() if it doesn't work. I'm not sure
    }

}