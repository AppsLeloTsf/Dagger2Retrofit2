package com.indianjourno.indianjourno.activity.ij.trending_news;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indianjourno.indianjourno.adapter.ij.stories_news.AdapterStoriesNewsAll;
import com.indianjourno.indianjourno.adapter.ij.trending_news.AdapterTrendingNews;
import com.indianjourno.indianjourno.adapter.ij.trending_news.AdapterTrendingNewsAll;
import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.model.ij_news.ModelStoriesNews;
import com.indianjourno.indianjourno.model.ij_news.ModelTrendingNews;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingNewsActivity extends AppCompatActivity {

    @BindView(R.id.rvStoryNewsList)
    protected RecyclerView rvStoryNewsList;
    @BindView(R.id.tbToolbarStories)
    protected Toolbar tbToolbarStories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_news);
        ButterKnife.bind(this);
        setSupportActionBar(tbToolbarStories);

        rvStoryNewsList.setLayoutManager(new LinearLayoutManager(this));
        callTrendingNews();
    }


    private void callTrendingNews(){
        Call<List<ModelTrendingNews>> call = RetrofitClient.getInstance().getApi().getAllTrendingNewsIj();
        call.enqueue(new Callback<List<ModelTrendingNews>>() {
            @Override
            public void onResponse(Call<List<ModelTrendingNews>> call, Response<List<ModelTrendingNews>> response) {
                List<ModelTrendingNews> tModel = response.body();

                AdapterTrendingNewsAll tAdapter = new AdapterTrendingNewsAll(tModel);
                rvStoryNewsList.setAdapter(tAdapter);

            }

            @Override
            public void onFailure(Call<List<ModelTrendingNews>> call, Throwable t) {

            }
        });
    }


}