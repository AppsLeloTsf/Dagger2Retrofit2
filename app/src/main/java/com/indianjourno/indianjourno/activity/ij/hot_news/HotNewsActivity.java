package com.indianjourno.indianjourno.activity.ij.hot_news;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indianjourno.indianjourno.adapter.ij.hot_news.AdapterHotNews;
import com.indianjourno.indianjourno.adapter.ij.hot_news.AdapterHotNewsAll;
import com.indianjourno.indianjourno.adapter.ij.stories_news.AdapterStoriesNewsAll;
import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.model.ij_news.ModelHotNews;
import com.indianjourno.indianjourno.model.ij_news.ModelStoriesNews;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotNewsActivity extends AppCompatActivity {

    @BindView(R.id.rvStoryNewsList)
    protected RecyclerView rvStoryNewsList;
    @BindView(R.id.tbToolbarStories)
    protected Toolbar tbToolbarStories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_news);
        ButterKnife.bind(this);
        setSupportActionBar(tbToolbarStories);

        rvStoryNewsList.setLayoutManager(new LinearLayoutManager(this));
        callHotNews();
    }


    private void callHotNews() {
        Call<List<ModelHotNews>> call = RetrofitClient.getInstance().getApi().getAllHotNewsIj();
        call.enqueue(new Callback<List<ModelHotNews>>() {
            @Override
            public void onResponse(Call<List<ModelHotNews>> call, Response<List<ModelHotNews>> response) {
                List<ModelHotNews> tModels = response.body();
                AdapterHotNewsAll tAdapter = new AdapterHotNewsAll(tModels);
                rvStoryNewsList.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelHotNews>> call, Throwable t) {

            }
        });
    }


}