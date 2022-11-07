package com.indianjourno.indianjourno.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;


import com.indianjourno.indianjourno.adapter.ij.video.AdapterVideoAll;
import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.model.ij_video.ModelVideo;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoListActivity extends AppCompatActivity {

    @BindView(R.id.rvVideoList)
    protected RecyclerView rvVideoList;
    @BindView(R.id.tvToolbarVideo)
    protected TextView tvToolbarVideo;
    @BindView(R.id.tbToolbarVideo)
    protected Toolbar tbToolbarVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        ButterKnife.bind(this);
        setSupportActionBar(tbToolbarVideo);

        rvVideoList.setLayoutManager(new LinearLayoutManager(this));
        callHomeVideo();
    }


    private void callHomeVideo() {
        Call<ModelVideo> call = RetrofitClient.getInstance().getApi().getAllVideoIj();
        call.enqueue(new Callback<ModelVideo>() {
            @Override
            public void onResponse(Call<ModelVideo> call, Response<ModelVideo> response) {
                ModelVideo  tModelVideos = response.body();
                setProgressBarIndeterminate(false);
                AdapterVideoAll tAdapterVideoCatList = new AdapterVideoAll(tModelVideos.getVideo());
                rvVideoList.setAdapter(tAdapterVideoCatList);
            }

            @Override
            public void onFailure(Call<ModelVideo> call, Throwable t) {

            }
        });
    }


}