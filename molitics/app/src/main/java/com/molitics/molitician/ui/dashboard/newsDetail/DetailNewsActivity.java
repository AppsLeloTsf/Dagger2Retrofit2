package com.molitics.molitician.ui.dashboard.newsDetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.molitics.molitician.BR;
import com.molitics.molitician.BaseActivity;
import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.UiMediator.Loader;
import com.molitics.molitician.databinding.FragmentDetailNewsBinding;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.InitializeServerRequest;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.News;
import com.molitics.molitician.ui.dashboard.newsDetail.adapter.DetailNewsAdapter;
import com.molitics.molitician.ui.dashboard.newsDetail.meditor.DetailNewsHandler;
import com.molitics.molitician.ui.dashboard.newsDetail.meditor.DetailNewsPresenter;
import com.molitics.molitician.ui.dashboard.newsDetail.viewModel.DetailNewsViewModel;
import com.molitics.molitician.ui.dashboard.survey.model.AllSurveyModel;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 12/19/2016.
 */

public class DetailNewsActivity extends BaseActivity<FragmentDetailNewsBinding, DetailNewsViewModel> implements InitializeServerRequest, DetailNewsPresenter.NewsDetailView, ViewRefreshListener {


    @Inject
    DetailNewsViewModel detailNewsViewModel;

    @BindView(R.id.news_pager_view)
    ViewPager news_pager_view;

    private DetailNewsAdapter detailNewsAdapter;
    private DetailNewsHandler newsHandler;
    private int news_id = 0;
    private int mPosition = 0;
    private List<News> detailModels;
    private Handler handler = new Handler(Looper.getMainLooper() /*UI thread*/);
    private Runnable workRunnable;
    private int news_type = 0;


    @Override
    public int getBindingVariable() {
        return BR.newsDetail;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_detail_news;
    }

    @Override
    public DetailNewsViewModel getViewModel() {
        return detailNewsViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        detailModels = new ArrayList<>();

        newsHandler = new DetailNewsHandler(this);

        initializeUI();
    }

    private void initializeUI() {
        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        int news_position = intent.getIntExtra(Constant.IntentKey.NEWS_POSITION, 0);
        //set adapter
        detailNewsAdapter = new DetailNewsAdapter(getSupportFragmentManager());
        news_pager_view.setAdapter(detailNewsAdapter);

        assert bundle != null;
        detailModels = (List<News>) bundle.getSerializable(Constant.IntentKey.NEWS_LIST);
        if (detailModels != null && detailModels.size() > 0) {
            detailNewsAdapter.addNewsList(detailModels);
            news_id = detailModels.get(news_position).getId();
            if (detailModels.get(news_position).getDisplayType() != null)
                news_type = detailModels.get(news_position).getDisplayType();
            mPosition = news_position;
            createServerRequest(Constant.RequestTag.LIST_DETAIL_NEWS);
        } else {
            news_id = bundle.getInt(Constant.IntentKey.NEWS_ID);
            news_type = bundle.getInt(Constant.IntentKey.DISPLAY_TYPE);
            createServerRequest(Constant.RequestTag.DETAIL_NEWS);
        }

        news_pager_view.setCurrentItem(news_position);
        news_pager_view.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {

                handler.removeCallbacks(workRunnable);
                workRunnable = () -> {
                    if (detailModels.size() > position) {
                        mPosition = position;
                        News mNews = detailModels.get(mPosition);
                        news_id = mNews.getId();
                        createServerRequest(Constant.RequestTag.LIST_DETAIL_NEWS);
                    }
                };
                handler.postDelayed(workRunnable, 2000 /*delay*/);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void createServerRequest(int tag) {
        switch (tag) {
            case Constant.RequestTag.DETAIL_NEWS:
                Loader.showMyDialog(this);
                newsHandler.getNewsDetail(news_id, news_type);
                break;
            case Constant.RequestTag.LIST_DETAIL_NEWS:
                newsHandler.getListDetail(news_id, news_type);
                break;
        }
    }

    @Override
    public void onNewsDetailDone(APIResponse apiResponse) {

        Loader.dismissMyDialog(this);
        News newsDetailModel = apiResponse.getData().getNewsDetailModel();
        if (news_id == newsDetailModel.getId())
            onParticularNews(newsDetailModel);

    }

    @Override
    public void onListDetailDone(APIResponse apiResponse) {
        if (apiResponse.getStatus() == 2000) {
            News newsDetailModel = apiResponse.getData().getNewsDetailModel();
            AllSurveyModel survey = newsDetailModel.getSurvey();
            if (survey != null) {
                detailNewsAdapter.updateList(mPosition, newsDetailModel);
                Fragment current_fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.news_pager_view + ":" + news_pager_view.getCurrentItem());

                if (current_fragment != null) {
                    if (newsDetailModel.getId() == news_id) {
                        ((NewsDetailFragment) current_fragment).updateData(newsDetailModel);
                    }
                }
            }
        }
    }


    @Override
    public void onNewsDetailServerFailed(ServerException serverException) {
        Loader.dismissMyDialog(DetailNewsActivity.this);
        DialogClass.showAlert(this, getString(R.string.something_went_wrong));
    }

    @Override
    public void onAPIError(ApiException apiException) {
        Loader.dismissMyDialog(this);
        Util.validateError(this, apiException, null, null, null);
    }

    @Override
    public void onFormValidationError(Map<String, String> errors) {
        Loader.dismissMyDialog(this);
    }

    private void onParticularNews(News newsDetailModel) {
        detailNewsAdapter.addNews(newsDetailModel);

        Fragment current_fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.news_pager_view + ":" + news_pager_view.getCurrentItem());
        if (current_fragment != null) {
            ((NewsDetailFragment) current_fragment).updateData(newsDetailModel);
        }
    }

    @Override
    public void onRefereshClick() {
        createServerRequest(Constant.RequestTag.LIST_DETAIL_NEWS);
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }
}
