package com.molitics.molitician.ui.dashboard.hotTopics;

import android.content.Intent;
import android.os.Bundle;


import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.molitics.molitician.BaseActivity;
import com.molitics.molitician.R;
import com.molitics.molitician.databinding.ActivityHotTopicDetailBinding;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.dashboard.dashboardView.DashBoardViewModel;
import com.molitics.molitician.ui.dashboard.hotTopics.hotTopicDetail.HotTopicDetailActivity;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.Util;

/**
 * Created by rahul on 27/11/17.
 */

public class HotTopicActivity extends BaseActivity<ActivityHotTopicDetailBinding, DashBoardViewModel> implements HotTopicPresenter.HotTopicUI,
        HotTopicAdapter.HotTopicListener, ViewRefreshListener {

    private ActivityHotTopicDetailBinding hotTopicDetailBinding;

    private int count = 0;
    private HotTopicHandler hotTopicHandler;
    private HotTopicAdapter topicAdapter;

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_hot_topic_detail;
    }

    @Override
    public DashBoardViewModel getViewModel() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hotTopicDetailBinding = getViewDataBinding();
        setSupportActionBar(hotTopicDetailBinding.includeTool.toolbar);

        showHeader(hotTopicDetailBinding.includeTool.toolbar, true, getString(R.string.trending));

        hotTopicDetailBinding.includeTool.toolbar.setNavigationOnClickListener(v -> finish());

        bindUI();
        hotTopicHandler = new HotTopicHandler(this);

        hotTopicHandler.getHotTopic(count);
        DialogClass.showDialog(this);

    }

    private void bindUI() {
        topicAdapter = new HotTopicAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        hotTopicDetailBinding.newsRecyclerView.setLayoutManager(layoutManager);
        hotTopicDetailBinding.newsRecyclerView.setAdapter(topicAdapter);
    }

    @Override
    public void onHotTopicResponse(APIResponse apiResponse) {
        DialogClass.dismissDialog();
        hotTopicDetailBinding.RLHandler.setVisibility(View.GONE);
        topicAdapter.addHotTopics(apiResponse.getData().getHotTopicModels());
    }

    @Override
    public void onHotTopicError(ApiException apiException) {
        DialogClass.dismissDialog();
        Util.validateError(this, apiException, hotTopicDetailBinding.RLHandler, this, topicAdapter.getHotTopicSize());
    }

    @Override
    public void onHotTopicClick(int position, int id, String tag,String logo) {

        Intent mIntent = new Intent(this, HotTopicDetailActivity.class);
        mIntent.putExtra(Constant.IntentKey.HOT_TOPIC_ID, id);
        mIntent.putExtra(Constant.IntentKey.HOT_TOPIC_STRING, tag);
        startActivity(mIntent);
    }

    @Override
    public void onRefereshClick() {
        DialogClass.showDialog(this);
        hotTopicHandler.getHotTopic(count);
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }
}
