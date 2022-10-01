package com.indianjourno.indianjourno.activity.ij.stories_news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.indianjourno.indianjourno.adapter.ij.stories_news.AdapterStoriesNews;
import com.indianjourno.indianjourno.adapter.ij.stories_news.AdapterStoriesNewsAll;
import com.indianjourno.indianjourno.adapter.ij.stories_news.AdapterStoriesNewsDetails;
import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.model.ij_news.ModelStoriesNews;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoriesNewsActivity extends AppCompatActivity {

    @BindView(R.id.rvStoryNewsList)
    protected RecyclerView rvStoryNewsList;
    @BindView(R.id.tbToolbarStories)
    protected Toolbar tbToolbarStories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories_news);
        ButterKnife.bind(this);
        setSupportActionBar(tbToolbarStories);

        rvStoryNewsList.setLayoutManager(new LinearLayoutManager(this));
        callStoriesNews();
    }

    private void callStoriesNews() {
        Call<List<ModelStoriesNews>> call = RetrofitClient.getInstance().getApi().getAllStoriesNewsIj();
        call.enqueue(new Callback<List<ModelStoriesNews>>() {
            @Override
            public void onResponse(Call<List<ModelStoriesNews>> call, Response<List<ModelStoriesNews>> response) {
                List<ModelStoriesNews> tModel = response.body();
                AdapterStoriesNewsAll tAdapter = new AdapterStoriesNewsAll(tModel);
                rvStoryNewsList.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelStoriesNews>> call, Throwable t) {

            }
        });
    }

}