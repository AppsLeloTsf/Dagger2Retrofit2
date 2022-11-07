package com.indianjourno.indianjourno.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.indianjourno.indianjourno.adapter.AdapterVideoList;
import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.model.videos.ModelVideos;
import com.indianjourno.indianjourno.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoPlayActivity extends AppCompatActivity {
    private AlertDialog.Builder tBuilder;
    @BindView(R.id.rvVideoPlayList)
    protected RecyclerView rvVideoPlayList;
    String strCatId;
    String strVideoCatId;
    String strCatName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        ButterKnife.    bind(this);
        tBuilder = new AlertDialog.Builder(this);
        strCatId = getIntent().getStringExtra(Constant.CAT_ID);
        strVideoCatId = getIntent().getStringExtra(Constant.VIDEO_ID);
        strCatName = getIntent().getStringExtra(Constant.CAT_NAME);
        callVideoApi(getApplicationContext());

    }
    private void callVideoApi(Context context) {

        {
            Call<ModelVideos> call = RetrofitClient.getInstance().getApi().getVideos(strVideoCatId);
            call.enqueue(new Callback<ModelVideos>() {
                @Override
                public void onResponse(Call<ModelVideos> call, Response<ModelVideos> response) {
                    ModelVideos tModel = response.body();
                    if (!(tModel != null ? tModel.getError() : null)) {
                        rvVideoPlayList.setLayoutManager(new LinearLayoutManager(context));
                        AdapterVideoList tAdapter = new AdapterVideoList(tModel.getVideos(), strVideoCatId);
                        rvVideoPlayList.setAdapter(tAdapter);
                    } else {
                        tBuilder.setMessage("News related to this will publish soon.")
                                .setPositiveButton("Back", (dialog, which) -> {
                                    startActivity(new Intent(VideoPlayActivity.this, VideoListActivity.class));
                                    finish();
                                });
                        AlertDialog tAlert = tBuilder.create();
                        tAlert.setTitle("Coming soon");
                        tAlert.show();
                        Toast.makeText(VideoPlayActivity.this, "News related to this will publish soon. ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelVideos> call, Throwable t) {

                }
            });
        }
    }

}