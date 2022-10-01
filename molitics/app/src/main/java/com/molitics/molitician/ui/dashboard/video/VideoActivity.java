package com.molitics.molitician.ui.dashboard.video;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.News;
import com.molitics.molitician.model.NewsVideoModel;
import com.molitics.molitician.ui.dashboard.newsDetail.DetailNewsActivity;
import com.molitics.molitician.ui.dashboard.nationalNews.YoutubePlayActivity;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 07/11/17.
 */

public class VideoActivity extends BasicAcivity implements VideoPresenter.VideoResponse,
        ViewRefreshListener, AllVideoAdapter.OnLoadMoreResult {
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

    private VideoHandler videoHandler;
    private int page_no = 0;
    private AllVideoAdapter videoAdapter;
    private boolean loadMore = false;
    private boolean newsApiCall = true;
    private TextView state_text;
    private ImageView remove_feed_state;
    private int requestType = 1;
    private int language = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internatinal_news);
        ButterKnife.bind(this);
        videoHandler = new VideoHandler(this);
        //set adapter
        bindUI();
        getToolBar();
    }

    private void getToolBar() {
        setSupportActionBar(toolbar);
        showHeader(true, "Molitics Videos");
        toolbar.setNavigationOnClickListener(v -> goBack());
    }


    private void bindUI() {
        main_progress_bar.setVisibility(View.VISIBLE);

        videoAdapter = new AllVideoAdapter(this);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(videoAdapter);
        getDataFromServer(requestType = 1, Constant.Language.DEFAULT);
        swipeContainer.setOnRefreshListener(() -> swipeContainer.setRefreshing(false));
    }

    private void getDataFromServer(int type, int language) {
        if (type == 1) {
            videoHandler.getAllVideo(page_no);
        } else if (type == 2) {
            videoHandler.getVideoByLanguage(language, page_no, 0, 1);
        }
    }

    @Override
    public void onVideoUi(APIResponse apiResponse) {
        loading_grid.setVisibility(View.GONE);
        main_container.setVisibility(View.GONE);
        main_progress_bar.setVisibility(View.GONE);
        page_no++;
        newsApiCall = true;
        loadMore = apiResponse.getData().getAllVideo().size() > 9;
        videoAdapter.addVideoList(apiResponse.getData().getAllVideo());
    }

    @Override
    public void onVideoApiError(ApiException apiException) {
        newsApiCall = true;
        loadMore = true;
        main_progress_bar.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
        loading_grid.setVisibility(View.GONE);
        if (apiException.getCode() == 100003) {
            if (videoAdapter.getVideoListSize() == 0) {
                Util.validateError(this, apiException, main_container, this, videoAdapter.getVideoListSize());
            } else {
                Util.addMiniNetworkFailView(this, main_container);
            }
        } else {
            Util.validateError(this, apiException, main_container, this, videoAdapter.getVideoListSize());
        }
    }

    @Override
    public void onVideoLanguageSelection(APIResponse apiResponse) {
        loading_grid.setVisibility(View.GONE);
        main_container.setVisibility(View.GONE);
        main_progress_bar.setVisibility(View.GONE);
        if (page_no == 0) {
            videoAdapter.clearList();
        }
        page_no++;
        newsApiCall = true;
        ArrayList<NewsVideoModel> arrayList = apiResponse.getData().getNews_video();
        loadMore = arrayList.size() > 9;
        videoAdapter.addVideoList(arrayList);
    }

    @Override
    public void onRefereshClick() {
        videoHandler.getAllVideo(page_no);
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoAdapter.releaseLoaders();
    }

    @Override
    public void onLoadMore(int totalItem) {
        if (loadMore && newsApiCall) {
            if (language == 0) {
                //// videoHandler.getAllVideo(page_no);
                getDataFromServer(requestType = 1, language);
            } else {
                getDataFromServer(requestType = 2, language);
            }
            loading_grid.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onVideoClick(int position) {

        Intent intent = new Intent(this, DetailNewsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.IntentKey.NEWS_LIST, convertNewsMobile(videoAdapter.getVideoList()));
        intent.putExtra(Constant.IntentKey.NEWS_POSITION, position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void playVideo(String video_link) {
        Intent intent = new Intent(this, YoutubePlayActivity.class);
        intent.putExtra(Constant.IntentKey.LINK, video_link);
        startActivity(intent);
    }

    private ArrayList<News> convertNewsMobile(List<NewsVideoModel> news_video) {
        ArrayList<News> send_news_list = new ArrayList<>();

        for (int i = 0; i < news_video.size(); i++) {
            News mNews = new News();
            mNews.setDisplayType(news_video.get(i).getDisplayType());
            mNews.setContent(news_video.get(i).getContent());
            mNews.setTime(news_video.get(i).getTime());
            mNews.setType(news_video.get(i).getType());
            mNews.setSource(news_video.get(i).getSource());
            mNews.setSourceId(news_video.get(i).getSource_id());
            mNews.setHeading(news_video.get(i).getHeading());
            mNews.setId(news_video.get(i).getId());
            mNews.setImage(news_video.get(i).getImage());
            mNews.setLink(news_video.get(i).getLink());
            mNews.setSourceLogo(news_video.get(i).getSourceLogo());
            mNews.setSurveyId(news_video.get(i).getSurveyId());
            mNews.setVideoLink(news_video.get(i).getVideoLink());
            send_news_list.add(mNews);
        }
        return send_news_list;
    }
}
