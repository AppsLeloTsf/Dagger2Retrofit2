package com.molitics.molitician.ui.dashboard.articles;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.News;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.OnNewsItemClick;
import com.molitics.molitician.ui.dashboard.newsDetail.DetailNewsActivity;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rahul on 3/15/2017.
 */

public class ArticleActivity extends BasicAcivity implements ArticlePresenter.ArticleApi, OnNewsItemClick, ArticleAdapter.OnLoadMoreResult, ViewRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_progress_bar)
    ProgressBar main_progress_bar;
    @BindView(R.id.loading_grid)
    LinearLayout loading_grid;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.news_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progressbarLayout)
    LinearLayout progressBar;
    @BindView(R.id.main_container)
    FrameLayout main_container;

    @BindView(R.id.RL_handler)
    RelativeLayout RL_handler;
    @BindView(R.id.ll_parent_lay_news)
    LinearLayout ll_parent_lay_news;

    private ArticleHandler newsHandler;
    private int page_no = 0;
    private ArticleAdapter newsAdapter;
    private boolean loadMore = false;
    private boolean newsApiCall = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internatinal_news);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        showHeader(true, getString(R.string.articles));

        toolbar.setNavigationOnClickListener(v -> goBack());
        newsHandler = new ArticleHandler(this);
        main_progress_bar.setVisibility(View.VISIBLE);
        //set adapter
        newsAdapter = new ArticleAdapter(this, this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(newsAdapter);
        newsHandler.getArticle(page_no);
        swipeContainer.setOnRefreshListener(() -> newsHandler.getArticle(page_no = 0));
    }

    @Override
    public void onArticleResponse(APIResponse apiResponse) {
        loading_grid.setVisibility(View.GONE);
        main_progress_bar.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
        page_no++;
        newsApiCall = true;
        loadMore = apiResponse.getData().getArticle().size() > 58;
        newsAdapter.addLocalNews(apiResponse.getData().getArticle());
    }

    @Override
    public void onArticleApiException(ApiException apiException) {
        newsApiCall = true;
        loadMore = true;
        main_progress_bar.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
        loading_grid.setVisibility(View.GONE);
        Util.validateError(this, apiException, main_container, this, newsAdapter.getArticleListSize());
    }

    @Override
    public void onArticleServerError(ServerException server_Exception) {

    }

    @Override
    public void onNewsRecyclerClick(int position, int type) {
        ArrayList<News> send_news_list = new ArrayList<>();
        if (Constant.Condition.VERTICAL_NEWS == type) {
            if (position < newsAdapter.getArticleList().size() - 10) {
                send_news_list = new ArrayList<News>(newsAdapter.getArticleList().subList(position, position + 10));
            } else {
                send_news_list = new ArrayList<News>(newsAdapter.getArticleList().subList(position, newsAdapter.getArticleList().size()));
            }
        }
        position = 0;
        Intent intent = new Intent(this, DetailNewsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.IntentKey.NEWS_LIST, send_news_list);
        intent.putExtra(Constant.IntentKey.NEWS_POSITION, position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onVideoClick(int position, String type) {
    }

    @Override
    public void onLoadMore(int totalItem) {

        if (loadMore && newsApiCall) {
            page_no = newsAdapter.getArticleListSize();
            newsHandler.getArticle(page_no);
            loading_grid.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefereshClick() {
        newsHandler.getArticle(page_no);
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }
}
