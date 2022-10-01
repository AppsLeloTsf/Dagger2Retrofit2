package com.molitics.molitician.ui.dashboard.leader.newleaderprofile.leaderNews;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ErrorViewHandler;
import com.molitics.molitician.interfaces.ViewNoDataActionInterface;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.News;
import com.molitics.molitician.ui.dashboard.constantData.RecyclerTouchWithType;
import com.molitics.molitician.ui.dashboard.localNews.LocalNewsAdapter;
import com.molitics.molitician.ui.dashboard.newsDetail.DetailNewsActivity;
import com.molitics.molitician.ui.dashboard.nationalNews.YoutubePlayActivity;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.OnNewsItemClick;
import com.molitics.molitician.ui.dashboard.youtubeMoreDetail.YoutubeMoreActivity;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.molitics.molitician.error.ErrorViewHandler.errorType.NO_RESULTS;

/**
 * Created by rahul on 7/1/2017.
 */

public class NewLeaderNewsFragment extends Fragment implements LeaderNewsPresenter.LeaderNewsView,
        OnNewsItemClick, RecyclerTouchWithType,
        ViewRefreshListener, LocalNewsAdapter.OnLoadMoreResult, ViewNoDataActionInterface {

    @BindView(R.id.main_progress_bar)
    ProgressBar main_progress_bar;
    @BindView(R.id.loading_grid)
    LinearLayout loading_grid;
    @BindView(R.id.news_recycler_view)
    RecyclerView news_recycler_view;
    /* @BindView(R.id.progressbarLayout)
     LinearLayout progressBar;*/
    @BindView(R.id.ll_parent_lay_news)
    LinearLayout ll_parent_lay_news;
    @BindView(R.id.main_container)
    FrameLayout main_container;


    private LocalNewsAdapter newsAdapter;
    private LeaderNewsHandler newsHandler;
    private int candidate_id = Constant.ZERO;
    private boolean loadMore = false;
    private boolean newsApiCall = true;


    public static Fragment getInstance(Integer candidate_id, String name) {
        Fragment mFragment = new NewLeaderNewsFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt(Constant.IntentKey.LEADER_PROFILE_ID, candidate_id);
        mBundle.putString(Constant.IntentKey.LEADER_PROFILE_NAME, name);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsHandler = new LeaderNewsHandler(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.leader_news_fragment, container, false);
        ButterKnife.bind(this, view);
        //   setRetainInstance(true);
        Bundle mBundle = getArguments();
        assert mBundle != null;
        candidate_id = mBundle.getInt(Constant.IntentKey.LEADER_PROFILE_ID, 0);
        setUi();
        int pageNo = Constant.ZERO;
        getNews(pageNo);
        return view;
    }

    private void getNews(int pageNo) {
        newsHandler.getLeaderNews(pageNo, candidate_id);
    }

    private void setUi() {
        //MainUi
        newsAdapter = new LocalNewsAdapter(getActivity(), this, this);
        //custom grid layout manager
        //  WrappingLinearLayoutManager gridLayoutManager = new WrappingLinearLayoutManager(getContext(), 2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        news_recycler_view.setLayoutManager(linearLayoutManager);
        news_recycler_view.setAdapter(newsAdapter);
        //recyclerView.setHasFixedSize(false);
        // recyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    public void onNewsRecyclerClick(int position, int type) {
        if (Constant.Condition.YoutubeVideo == type) {
            Intent intent = new Intent(getActivity(), YoutubeMoreActivity.class);
            intent.putExtra(Constant.IntentKey.NEWS_VIDEO, ExtraUtils.convertNewsModleToNewsVideoModel(newsAdapter.getLocalNewsList().get(position)));
            intent.putExtra(Constant.IntentKey.NEWS_POSITION, position);
            intent.putExtra(Constant.IntentKey.CANDIDATE_ID, candidate_id);
            intent.putExtra(Constant.IntentKey.FROM, Util.YoutubeType.leader.name());
            startActivity(intent);
        } else {
            ArrayList<News> send_news_list = new ArrayList<>();
            int click_position = 0;
            if (Constant.Condition.VERTICAL_NEWS == type) {
                click_position = position;
                send_news_list = newsAdapter.getLocalNewsList();
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.IntentKey.NEWS_LIST, send_news_list);
            Intent intent = new Intent(getActivity(), DetailNewsActivity.class);
            intent.putExtra(Constant.IntentKey.NEWS_POSITION, click_position);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void onVideoClick(int position, String type) {

        Intent intent = new Intent(getActivity(), YoutubePlayActivity.class);
        intent.putExtra(Constant.IntentKey.LINK, type);
        startActivity(intent);
    }

    @Override
    public void onVerticalRecycler(int position, int type) {
    }

    @Override
    public void onCloseClick() {
    }

    @Override
    public void onRefereshClick() {
    }

    @Override
    public void onNetworkAvailable() {

    }

    @Override
    public void onLoadMore(int totalItem) {
        if (loadMore && newsApiCall) {
            if (loading_grid.getVisibility() == View.GONE) {
                loading_grid.setVisibility(View.VISIBLE);
            }
            newsApiCall = false;
            getNews(totalItem);
        }
    }

    @Override
    public void onNewsResponse(APIResponse apiResponse) {
        ArrayList<News> newsArrayList = apiResponse.getData().getNews();
        newsApiCall = true;
        loading_grid.setVisibility(View.GONE);
        loadMore = newsArrayList.size() > 9;
        newsAdapter.addLocalNews(newsArrayList);
    }

    @Override
    public void onNewsApiException(ApiException apiException) {
        newsApiCall = true;
        if (getContext() != null)
            ErrorViewHandler.errorNoData(NO_RESULTS, main_container, getContext(), getString(R.string.no_data_available), null);
    }

    @Override
    public void onNewsSeekResponse(APIResponse apiResponse) {
    }

    @Override
    public void onNewsSeekException(ApiException apiExeption) {
        ErrorViewHandler.errorNoData(NO_RESULTS, main_container,
                getContext(), getString(R.string.seek_news_req_response), null);
    }

    public void onToolBarTouch() {
        //  recyclerView.setNestedScrollingEnabled(true);
    }

    public void onToolBarTouchDetach() {
        //recyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        newsAdapter.releaseLoaders();
        news_recycler_view.removeAllViews();
    }

    @Override
    public void onNoDataActionClick() {
        newsHandler.seekNews(candidate_id);
    }
}
