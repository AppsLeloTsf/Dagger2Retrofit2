package com.molitics.molitician.ui.dashboard.newsBookmark;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.RelativeLayout;

import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.database.Database;
import com.molitics.molitician.database.NewsBookMarkModel;
import com.molitics.molitician.model.News;
import com.molitics.molitician.ui.dashboard.localNews.LocalNewsAdapter;
import com.molitics.molitician.ui.dashboard.newsDetail.DetailNewsActivity;
import com.molitics.molitician.ui.dashboard.nationalNews.YoutubePlayActivity;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.OnNewsItemClick;
import com.molitics.molitician.util.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.molitics.molitician.util.ConvertUtils.convertToBookmarkNews;

/**
 * Created by Rahul on 2/27/2017.
 */

public class NewsBookMarkView extends BasicAcivity implements OnNewsItemClick, LocalNewsAdapter.OnLoadMoreResult {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.bookmark_toolbar)
    Toolbar bookmark_toolbar;
    @BindView(R.id.rl_error)
    RelativeLayout rl_error;

    private LocalNewsAdapter newsAdapter;
    private ArrayList<News> newsList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_bookmark);
        ButterKnife.bind(this);
        setToolBar();
        setAdapter();
    }

    private void setToolBar() {
        setSupportActionBar(bookmark_toolbar);
        showHeader(true, getResources().getString(R.string.bookmarked_header));
        bookmark_toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setAdapter() {
        newsAdapter = new LocalNewsAdapter(this, this, this);
        /// LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setAdapter(newsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        newsList.clear();
        newsAdapter.deleteLocalNewsList();
        handleUi();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void handleUi() {
        List<NewsBookMarkModel> newsBookMarkModels = Database.getAllBookMarkNews();

        if (newsBookMarkModels != null && newsBookMarkModels.size() != 0) {

            if (newsBookMarkModels.size() == 1) {
                showHeader(true, newsBookMarkModels.size()
                        + getResources().getString(R.string.bookmark));
            } else {
                showHeader(true, newsBookMarkModels.size()
                        + " " + getResources().getString(R.string.bookmarked_header));
            }

            newsAdapter.deleteLocalNewsList();
            rl_error.setVisibility(View.GONE);
            newsList = convertToBookmarkNews(newsBookMarkModels);
            newsAdapter.addLocalNews(newsList);
        } else {
            showHeader(true, getResources().getString(R.string.bookmark));
            rl_error.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onNewsRecyclerClick(int position, int type) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.IntentKey.NEWS_LIST, newsList);
        Intent intent = new Intent(NewsBookMarkView.this, DetailNewsActivity.class);
        intent.putExtra(Constant.IntentKey.NEWS_POSITION, position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onVideoClick(int position, String type) {
        Intent intent = new Intent(this, YoutubePlayActivity.class);
        intent.putExtra(Constant.IntentKey.LINK, type);
        startActivity(intent);
    }

    @Override
    public void onLoadMore(int totalItem) {
    }

    @Override
    protected void onStop() {
        super.onStop();
        newsAdapter.releaseLoaders();
    }
}
