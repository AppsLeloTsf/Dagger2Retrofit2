package com.indianjourno.indianjourno.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.indianjourno.indianjourno.activity.VideoListActivity;
import com.indianjourno.indianjourno.activity.ij.breaking_news.BreakingNewsActivity;
import com.indianjourno.indianjourno.activity.ij.hot_news.HotNewsActivity;
import com.indianjourno.indianjourno.activity.ij.news.QuickNewsActivity;
import com.indianjourno.indianjourno.activity.ij.stories_news.StoriesNewsActivity;
import com.indianjourno.indianjourno.activity.ij.trending_news.TrendingNewsActivity;
import com.indianjourno.indianjourno.adapter.SliderAdapter;
import com.indianjourno.indianjourno.adapter.ij.breaking_news.AdapterBreakingNews;
import com.indianjourno.indianjourno.adapter.ij.hot_news.AdapterHotNews;
import com.indianjourno.indianjourno.adapter.ij.trending_news.AdapterTrendingNews;
import com.indianjourno.indianjourno.adapter.ij.stories_news.AdapterStoriesNews;
import com.indianjourno.indianjourno.adapter.ij.video.AdapterVideoList;
import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.model.ij_news.ModelBreakingNew;
import com.indianjourno.indianjourno.model.ij_news.ModelHotNews;
import com.indianjourno.indianjourno.model.ij_news.ModelStoriesNews;
import com.indianjourno.indianjourno.model.ij_news.ModelTrendingNews;
import com.indianjourno.indianjourno.model.ij_video.ModelVideo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import indianjourno.indianjourno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment {

    @BindView(R.id.marqueeText)
    protected TextView marqueeText;

    @BindView(R.id.cvQuickNews)
    protected CardView cvQuickNews;

    @BindView(R.id.tvVideoTitle)
    protected TextView tvVideoTitle;
    @BindView(R.id.tvVideosViewAll)
    protected TextView tvVideosViewAll;
    @BindView(R.id.rvHomeVideoCat)
    protected RecyclerView rvHomeVideoCat;

    @BindView(R.id.tvBreakingTitle)
    protected TextView tvBreakingTitle;
    @BindView(R.id.tvBreakingViewAll)
    protected TextView tvBreakingViewAll;
    @BindView(R.id.rvHomeBreakingNews)
    protected RecyclerView rvHomeBreakingNews;

    @BindView(R.id.tvHotTitle)
    protected TextView tvHotTitle;
    @BindView(R.id.tvHotViewAll)
    protected TextView tvHotViewAll;
    @BindView(R.id.rvHomeHotNews)
    protected RecyclerView rvHomeHotNews;

    @BindView(R.id.tvStoriesTitle)
    protected TextView tvStoriesTitle;
    @BindView(R.id.tvStoriesViewAll)
    protected TextView tvStoriesViewAll;
    @BindView(R.id.rvStoriesNews)
    protected RecyclerView rvStoriesNews;


    @BindView(R.id.tvTrendingTitle)
    protected TextView tvTopTitle;
    @BindView(R.id.tvTrendingViewAll)
    protected TextView tvTrendingViewAll;
    @BindView(R.id.rvHomeTrendingNews)
    protected RecyclerView rvHomeNewsCat;
     @BindView(R.id.viewPagerMain)
    protected ViewPager viewPagerMain;
    @BindView(R.id.vfMain)
    protected ViewFlipper vfMain;
    @BindView(R.id.banner)
    protected ImageView banner;
    private SliderAdapter sliderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        rvHomeVideoCat.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvHomeBreakingNews.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvHomeHotNews.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvStoriesNews.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvHomeNewsCat.setLayoutManager(new LinearLayoutManager(getContext()));


        tvTopTitle.setVisibility(View.GONE);
        tvTrendingViewAll.setVisibility(View.GONE);
        tvHotTitle.setVisibility(View.GONE);
        tvHotViewAll.setVisibility(View.GONE);
        tvVideoTitle.setVisibility(View.GONE);
        tvVideosViewAll.setVisibility(View.GONE);
        tvBreakingTitle.setVisibility(View.GONE);
        tvBreakingViewAll.setVisibility(View.GONE);
        tvStoriesTitle.setVisibility(View.GONE);
        tvStoriesViewAll.setVisibility(View.GONE);
        cvQuickNews.setVisibility(View.GONE);


        callHomeVideo(getContext());
        callBreakingNews();
        callHotNews();
        callStoriesNews();
//        callHomeCat(getContext());
        marqueeText.setSelected(true);
        callTrendingNews();
        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.cvQuickNews)
    public void cvQuickNewsClicked(){

        Intent intent = new Intent(getContext(), QuickNewsActivity.class);
        startActivity(intent);
    }


    private void callHomeVideo(Context context) {
        Call<ModelVideo> call = RetrofitClient.getInstance().getApi().getAllVideoIj();
        call.enqueue(new Callback<ModelVideo>() {
            @Override
            public void onResponse(Call<ModelVideo> call, Response<ModelVideo> response) {
                ModelVideo  tModelVideos = response.body();
                tvVideoTitle.setVisibility(View.VISIBLE);
                tvVideosViewAll.setVisibility(View.VISIBLE);
                cvQuickNews.setVisibility(View.VISIBLE);
                rvHomeVideoCat.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                AdapterVideoList tAdapter = new AdapterVideoList(tModelVideos.getVideo());
                rvHomeVideoCat.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<ModelVideo> call, Throwable t) {

            }
        });
    }

    private void callBreakingNews() {
        Call<List<ModelBreakingNew>> call = RetrofitClient.getInstance().getApi().getAllBreakingNewsIj();
        call.enqueue(new Callback<List<ModelBreakingNew>>() {
            @Override
            public void onResponse(Call<List<ModelBreakingNew>> call, Response<List<ModelBreakingNew>> response) {
                List<ModelBreakingNew> modelBreakingNewList = response.body();
                marqueeText.setSelected(true);


                marqueeText.setText(modelBreakingNewList.get(0).getNewsTitle());
                tvBreakingTitle.setVisibility(View.VISIBLE);
                tvBreakingViewAll.setVisibility(View.VISIBLE);
                AdapterBreakingNews adapterBreakingNews = new AdapterBreakingNews(modelBreakingNewList);
                rvHomeBreakingNews.setAdapter(adapterBreakingNews);
            }

            @Override
            public void onFailure(Call<List<ModelBreakingNew>> call, Throwable t) {

            }
        });
    }
    private void callHotNews() {
        Call<List<ModelHotNews>> call = RetrofitClient.getInstance().getApi().getAllHotNewsIj();
        call.enqueue(new Callback<List<ModelHotNews>>() {
            @Override
            public void onResponse(Call<List<ModelHotNews>> call, Response<List<ModelHotNews>> response) {
                List<ModelHotNews> tModels = response.body();
                tvHotTitle.setVisibility(View.VISIBLE);
                tvHotViewAll.setVisibility(View.VISIBLE);
                AdapterHotNews tAdapter = new AdapterHotNews(tModels);
                rvHomeHotNews.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelHotNews>> call, Throwable t) {

            }
        });
    }
    private void callTrendingNews(){
        Call<List<ModelTrendingNews>> call = RetrofitClient.getInstance().getApi().getAllTrendingNewsIj();
        call.enqueue(new Callback<List<ModelTrendingNews>>() {
            @Override
            public void onResponse(Call<List<ModelTrendingNews>> call, Response<List<ModelTrendingNews>> response) {
                List<ModelTrendingNews> tModel = response.body();

                tvTopTitle.setVisibility(View.VISIBLE);
                tvTrendingViewAll.setVisibility(View.VISIBLE);
                AdapterTrendingNews tAdapter = new AdapterTrendingNews(tModel);
                rvHomeNewsCat.setAdapter(tAdapter);

            }

            @Override
            public void onFailure(Call<List<ModelTrendingNews>> call, Throwable t) {

            }
        });
    }

    private void callStoriesNews() {
        Call<List<ModelStoriesNews>> call = RetrofitClient.getInstance().getApi().getAllStoriesNewsIj();
        call.enqueue(new Callback<List<ModelStoriesNews>>() {
            @Override
            public void onResponse(Call<List<ModelStoriesNews>> call, Response<List<ModelStoriesNews>> response) {
                List<ModelStoriesNews> tModel = response.body();
                tvStoriesTitle.setVisibility(View.VISIBLE);
                tvStoriesViewAll.setVisibility(View.VISIBLE);
                AdapterStoriesNews tAdapter = new AdapterStoriesNews(tModel);
                rvStoriesNews.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelStoriesNews>> call, Throwable t) {

            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.tvStoriesViewAll)
    public void tvStoriesViewAllClicked(){
        Intent intent = new Intent(getContext(), StoriesNewsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.tvVideosViewAll)
    public void tvVideosViewAllClicked(){
        Intent intent = new Intent(getContext(), VideoListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.tvBreakingViewAll)
    public void tvBreakingViewAllClicked(){
        Intent intent = new Intent(getContext(), BreakingNewsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.tvTrendingViewAll)
    public void tvTrendingViewAllClicked(){
        Intent intent = new Intent(getContext(), TrendingNewsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.tvHotViewAll)
    public void tvHotViewAllClicked(){
        Intent intent = new Intent(getContext(), HotNewsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}