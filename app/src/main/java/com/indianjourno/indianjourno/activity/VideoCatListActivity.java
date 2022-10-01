package com.indainjourno.indianjourno.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.janatasuddi.janatasuddinews.R;
import com.janatasuddi.janatasuddinews.adapter.AdapterVideoCatList;
import com.janatasuddi.janatasuddinews.api.RetrofitClient;
import com.janatasuddi.janatasuddinews.model.video_cat.ModelVideoCat;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoCatListActivity extends AppCompatActivity {
    @BindView(R.id.rvVideoCatList)
    protected RecyclerView rvVideoCatList;
    @BindView(R.id.pbVideoCatList)
    protected ProgressBar pbVideoCatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_cat_list);
        initViews();
        callApi();
    }
    private void callApi(){
        Call<ModelVideoCat> call = RetrofitClient.getInstance().getApi().getVideoCatList();
        call.enqueue(new Callback<ModelVideoCat>() {
            @Override
            public void onResponse(Call<ModelVideoCat> call, Response<ModelVideoCat> response) {
                ModelVideoCat  tModelVideos = response.body();
                pbVideoCatList.setVisibility(View.GONE);
                AdapterVideoCatList tAdapterVideoCatList = new AdapterVideoCatList(VideoCatListActivity.this,tModelVideos.getVideoCategory());
                rvVideoCatList.setAdapter(tAdapterVideoCatList);
            }

            @Override
            public void onFailure(Call<ModelVideoCat> call, Throwable t) {

            }
        });    }
    private void initViews()
    {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvVideoCatList.setLayoutManager(gridLayoutManager);
    }

}