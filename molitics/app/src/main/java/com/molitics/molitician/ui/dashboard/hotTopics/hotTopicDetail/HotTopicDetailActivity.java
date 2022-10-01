package com.molitics.molitician.ui.dashboard.hotTopics.hotTopicDetail;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.widget.Toast;

import com.molitics.molitician.BaseActivity;
import com.molitics.molitician.R;
import com.molitics.molitician.databinding.ActivityHotTopicDetailBinding;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.News;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.constantData.RecyclerTouchWithType;
import com.molitics.molitician.ui.dashboard.hotTopics.HotTopicDetailAdapter;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.NewLeaderProfileActivity;
import com.molitics.molitician.ui.dashboard.login.LoginDialogFragment;
import com.molitics.molitician.ui.dashboard.newsDetail.DetailNewsActivity;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.OnNewsItemClick;
import com.molitics.molitician.ui.dashboard.voice.VoiceFragment;
import com.molitics.molitician.ui.dashboard.voice.VoiceViewNavigation;
import com.molitics.molitician.ui.dashboard.voice.adapter.VoiceAllAdapter;
import com.molitics.molitician.ui.dashboard.voice.createVoice.CreateVoiceActivity;
import com.molitics.molitician.ui.dashboard.voice.ImageBigFragment;
import com.molitics.molitician.ui.dashboard.voice.VoiceCreatorProfile;
import com.molitics.molitician.ui.dashboard.voice.feedAction.LikeDislikeParticipantDialog;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceViewModel;
import com.molitics.molitician.ui.dashboard.voice.reportFeed.ReportFeedDialog;
import com.molitics.molitician.ui.dashboard.voice.video_module.VoiceVideoActivity;
import com.molitics.molitician.ui.dashboard.voice.viewHolder.VoiceAllViewHolder;
import com.molitics.molitician.ui.dashboard.voice.voiceComment.CommentFragment;
import com.molitics.molitician.ui.dashboard.youtubeMoreDetail.YoutubeMoreActivity;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.ShareScreenShot;
import com.molitics.molitician.util.UiFactory;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


import static com.molitics.molitician.ui.dashboard.voice.VoiceFragment.COMMENT_RESPONSE;
import static com.molitics.molitician.ui.dashboard.voice.VoiceFragment.VOICE_CREATE_MODEL;
import static com.molitics.molitician.util.Constant.RequestTag.LOGINDIALOG;
import static com.molitics.molitician.util.Util.showProgressAnimation;

/**
 * Created by rahul on 28/11/17.
 */

public class HotTopicDetailActivity extends BaseActivity<ActivityHotTopicDetailBinding, VoiceViewModel> implements HotTopicDetailPresenter.HotTTopicDetailUI, OnNewsItemClick, RecyclerTouchWithType,
        ViewRefreshListener, HotTopicDetailAdapter.OnLoadMoreResult, VoiceAllAdapter.VoiceInterFace, VoiceViewNavigation,
        BranchShareClass.DeepLinkCallBack {

    @Inject
    VoiceViewModel voiceViewModel;

    private ActivityHotTopicDetailBinding hotTopicDetailBinding;
    private int count = 0;
    private HotTopicDetailAdapter newsAdapter;
    private boolean loadMore = false;
    private boolean newsApiCall = true;
    private HotTopicDetailHandler detailHandler;
    private String hot_topic_text;
    private int follow_position = 0, viewholderPosition = 0, hot_topic_id = 0, issue_action = 0, issue_action_position = 0;
    private int STORAGE_PERMISSION_CODE = 23;
    private int share_issue_id;

    LinearLayoutManager linearLayoutManager;

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_hot_topic_detail;
    }

    @Override
    public VoiceViewModel getViewModel() {
        return voiceViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hotTopicDetailBinding = getViewDataBinding();
        voiceViewModel.setNavigator(this);
        voiceViewModel.setContext(this);

        detailHandler = new HotTopicDetailHandler(this);
        Intent intent = getIntent();
        hot_topic_id = intent.getIntExtra(Constant.IntentKey.HOT_TOPIC_ID, 0);
        hot_topic_text = intent.getStringExtra(Constant.IntentKey.HOT_TOPIC_STRING);

        setSupportActionBar(hotTopicDetailBinding.includeTool.toolbar);

        showHeader(hotTopicDetailBinding.includeTool.toolbar, true, hot_topic_text);

        hotTopicDetailBinding.swipeContainer.setOnRefreshListener(() -> {
            count = 0;
            getNews(count);
        });

        hotTopicDetailBinding.includeFloating.hotTopicFloatBtn.setOnClickListener(v -> startCreateHotTopicActivity());

        setUi();
        refreshUI();
    }

    private void refreshUI() {
        hotTopicDetailBinding.includeProgess.mainProgressBar.setVisibility(View.VISIBLE);
        count = 0;
        getNews(count);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getNews(int pageNo) {
        detailHandler.getHotTopicDetail(hot_topic_id, pageNo);
    }

    private void setUi() {
        hotTopicDetailBinding.includeFloating.hotTopicFloatBtn.show();
        //MainUi
        newsAdapter = new HotTopicDetailAdapter(this, this, this, this);
        //custom grid layout manager
        //  WrappingLinearLayoutManager gridLayoutManager = new WrappingLinearLayoutManager(getContext(), 2);
        linearLayoutManager = new LinearLayoutManager(this);
        hotTopicDetailBinding.newsRecyclerView.setLayoutManager(linearLayoutManager);
        hotTopicDetailBinding.newsRecyclerView.setAdapter(newsAdapter);
        UiFactory.recyclerBottomSpace(HotTopicDetailActivity.this, hotTopicDetailBinding.newsRecyclerView, R.dimen.dim_100dp);

    }

    @Override
    public void onNewsRecyclerClick(int position, int type) {

        if (Constant.Condition.YoutubeVideo == type) {
            Intent intent = new Intent(this, YoutubeMoreActivity.class);
            intent.putExtra(Constant.IntentKey.NEWS_VIDEO, ExtraUtils.convertNewsModleToNewsVideoModel(newsAdapter.getLocalNewsList().get(position)));
            intent.putExtra(Constant.IntentKey.NEWS_POSITION, position);
            intent.putExtra(Constant.IntentKey.TRENDING_ID, hot_topic_id);
            intent.putExtra(Constant.IntentKey.FROM, Util.YoutubeType.trending.name());
            startActivity(intent);
        } else {
            ArrayList<News> send_news_list = new ArrayList<>();
            if (Constant.Condition.VERTICAL_NEWS == type) {
                send_news_list = newsAdapter.getLocalNewsList();
            }
            if (type != 5) {
                ArrayList<News> senfd_news_list = new ArrayList<>();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.IntentKey.NEWS_LIST, senfd_news_list);
                Intent intent = new Intent(this, DetailNewsActivity.class);
                intent.putExtra(Constant.IntentKey.NEWS_ID, send_news_list.get(position).getId());
                intent.putExtra(Constant.IntentKey.DISPLAY_TYPE, type);
                intent.putExtra(Constant.IntentKey.NEWS_POSITION, 0);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }

    public void startCreateHotTopicActivity() {
        Intent mIntent = new Intent(this, CreateVoiceActivity.class);
        mIntent.putExtra(Constant.IntentKey.Hot_TOPIC_TEXT, hot_topic_text);
        mIntent.putExtra(Constant.IntentKey.HOT_TOPIC_ID, hot_topic_id);
        startActivityForResult(mIntent, VOICE_CREATE_MODEL);
    }

    @Override
    public void onVideoClick(int position, String video_url) {

    }

    @Override
    public void onVerticalRecycler(int position, int type) {

    }

    @Override
    public void onCloseClick() {

    }

    @Override
    public void onRefereshClick() {
        getNews(count);
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }

    @Override
    public void onLoadMore(int totalItem) {
        if (loadMore && newsApiCall) {

            if (hotTopicDetailBinding.loadingGrid.getVisibility() == View.GONE) {
                hotTopicDetailBinding.loadingGrid.setVisibility(View.VISIBLE);
            }
            newsApiCall = false;
            count = totalItem;
            getNews(totalItem);
        }
    }

    @Override
    public void onHotTopicDetailResponse(APIResponse apiResponse) {
        hotTopicDetailBinding.includeProgess.mainProgressBar.setVisibility(View.GONE);
        hotTopicDetailBinding.swipeContainer.setRefreshing(false);
        ArrayList<News> newsArrayList = apiResponse.getData().getNews();
        newsApiCall = true;
        hotTopicDetailBinding.loadingGrid.setVisibility(View.GONE);
        loadMore = newsArrayList.size() > 60;
        if (count == 0) {
            newsAdapter.deleteLocalNewsList();
        }
        newsAdapter.addLocalNews(newsArrayList);
    }

    @Override
    public void onHotTopicDetailError(ApiException apiException) {
        hotTopicDetailBinding.includeProgess.mainProgressBar.setVisibility(View.GONE);

        hotTopicDetailBinding.swipeContainer.setRefreshing(false);
        newsApiCall = true;

        Util.validateError(this, apiException, hotTopicDetailBinding.mainContainer,
                this, newsAdapter.getNewsListSize());
    }

    @Override
    public void onLikeDislikeResponse(APIResponse apiResponse) {
        newsAdapter.updateLikeDislike(issue_action_position,
                apiResponse.getData().getLike_count(), apiResponse.getData().getDislike_count(), issue_action);

        getCurrentItemPosition(viewholderPosition, apiResponse.getData().getLike_count(), apiResponse.getData().getDislike_count());

    }

    private void getCurrentItemPosition(int position, int like_count, int dislike_count) {
        VoiceAllViewHolder voiceAllViewHolder;
        if (hotTopicDetailBinding.newsRecyclerView.findViewHolderForAdapterPosition(position) instanceof VoiceAllViewHolder) {
            voiceAllViewHolder = (VoiceAllViewHolder) hotTopicDetailBinding.newsRecyclerView.findViewHolderForAdapterPosition(position);
            VoiceAllViewHolder.setUpvoteText(this, voiceAllViewHolder.like_count_view, like_count);
            VoiceAllViewHolder.setDownvoteText(this, voiceAllViewHolder.dislike_count_view, dislike_count);

            int totoalVoteCount = like_count + dislike_count;
            if (totoalVoteCount != 0) {
                ////// left to right or right to left progress bar with diff color
                if (issue_action == Constant.Action.LIKE) {
                    int finalPercent = (like_count * 100) / (totoalVoteCount);
                    voiceAllViewHolder.vote_progress_bar.setProgress(finalPercent);
                    showProgressAnimation(voiceAllViewHolder.vote_progress_bar, finalPercent);
                    voiceAllViewHolder.vote_progress_bar.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.vote_progress_bar_horizontal, null));
                    voiceAllViewHolder.vote_progress_bar.setRotation(0);
                } else {
                    int finalPercent = (dislike_count * 100) / (totoalVoteCount);
                    voiceAllViewHolder.vote_progress_bar.setProgress(finalPercent);
                    showProgressAnimation(voiceAllViewHolder.vote_progress_bar, finalPercent);
                    voiceAllViewHolder.vote_progress_bar.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.downvote_progress_horizontal, null));
                    voiceAllViewHolder.vote_progress_bar.setRotation(180);
                }
            } else {
                voiceAllViewHolder.vote_progress_bar.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.gray_progress_horizontal, null));
            }
        }
    }


    @Override
    public void onLikeDislikeError(ApiException apiException) {
    }

    @Override
    public void onFollowResponse(APIResponse apiResponse) {
        newsAdapter.onFollowResponse(follow_position, apiResponse.getData().getCount());
        onFollowResponseUpdateView(viewholderPosition, apiResponse.getData().getCount());
    }

    private void onFollowResponseUpdateView(int position, int follow_count) {
        VoiceAllViewHolder voiceAllViewHolder;
        if (hotTopicDetailBinding.newsRecyclerView.findViewHolderForAdapterPosition(position) instanceof VoiceAllViewHolder) {
            voiceAllViewHolder = (VoiceAllViewHolder)
                    hotTopicDetailBinding.newsRecyclerView.findViewHolderForAdapterPosition(position);
            voiceAllViewHolder.follow_txt.setTextColor(getResources().getColor(R.color.theme));
            voiceAllViewHolder.follow_txt.setText(String.format(getString(R.string.following_tag), follow_count));
            voiceAllViewHolder.follow_txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.flag_blue, 0, 0, 0);
        }
    }

    @Override
    public void onFollowError(ApiException apiException) {
    }

    @Override
    public void onUnFollowResponse(APIResponse apiResponse) {
        newsAdapter.onUnFollowResponse(follow_position, apiResponse.getData().getCount());

        onUnFollowResponseUpdateView(viewholderPosition, apiResponse.getData().getCount());

    }

    private void onUnFollowResponseUpdateView(int position, int follow_count) {
        VoiceAllViewHolder voiceAllViewHolder;

        if (hotTopicDetailBinding.newsRecyclerView.findViewHolderForAdapterPosition(position) instanceof VoiceAllViewHolder) {
            voiceAllViewHolder = (VoiceAllViewHolder)
                    hotTopicDetailBinding.newsRecyclerView.findViewHolderForAdapterPosition(position);
            voiceAllViewHolder.follow_txt.setTextColor(getResources().getColor(R.color.follow_txte));
            voiceAllViewHolder.follow_txt.setText(String.format(getString(R.string.follow), follow_count));
            voiceAllViewHolder.follow_txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.flag, 0, 0, 0);
        }
    }

    @Override
    public void onUnFollowError(ApiException apiException) {
    }

    @Override
    public void onDeleteIssueResponse(APIResponse apiResponse) {
    }

    @Override
    public void onDeleteIssueError(ApiException apiException) {
        if (apiException.getCode() == 2005) {
            newsAdapter.deleteVoice(issue_action_position);
        } else {
            Util.validateError(this, apiException, null, null, null);
        }
    }

    @Override
    public void onLoadMore() {
    }

    @Override
    public void onCreateVoiceClick(int itemSelection) {

    }

    @Override
    public void onLikeDislikeClick(String from, int actual_position, int position, int issue_id, int action, int image_position) {

        // this.image_position = image_position;
        issue_action_position = position;
        viewholderPosition = actual_position;
        this.share_issue_id = issue_id;
        issue_action = action;

        if (PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY).isEmpty()) {
            showLoginDialog();
        } else detailHandler.likeDislike(share_issue_id, issue_action);
    }

    private void showLoginDialog() {
        DialogFragment dialogFragment = LoginDialogFragment.getInstance();
        ///dialogFragment.setTargetFragment(VoiceFragment.this, LOGINDIALOG);
        dialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_fragment));
    }

    @Override
    public void onDotClick(int position, int issue_id, int action_type, VoiceAllModel voiceAllModel) {
        if (action_type == Constant.Action.EDIT) {
            Intent mIntent = new Intent(this, CreateVoiceActivity.class);
            mIntent.putExtra(Constant.IntentKey.Hot_TOPIC_TEXT, hot_topic_text);
            mIntent.putExtra(Constant.IntentKey.HOT_TOPIC_ID, hot_topic_id);
            mIntent.putExtra(Constant.IntentKey.ISSUE_MODEL, voiceAllModel);
            mIntent.putExtra(Constant.IntentKey.POSITION, position);
            startActivityForResult(mIntent, VOICE_CREATE_MODEL);
        } else if (action_type == Constant.Action.DELETE) {
            issue_action_position = position;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.want_to_delete))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes), (dialog, id) -> detailHandler.deleteIssue(issue_id))
                    .setNegativeButton(getString(R.string.no), (dialog, id) -> {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    });
            AlertDialog alert = builder.create();
            alert.setTitle(getString(R.string.issue_tag));
            alert.show();
        } else if (action_type == Constant.Action.SHARE) {
            onShareClick(issue_id, voiceAllModel.getUserName(), voiceAllModel.getContent());
        }
    }

    @Override
    public void onCommentClick(String from, int position, int issue_id, int image_position) {
        this.share_issue_id = issue_id;
        issue_action_position = position;
        if (PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY).isEmpty()) {
            showLoginDialog();
        } else performCommentOnLogin();

    }

    private void performCommentOnLogin() {
        Intent mIntent = new Intent(this, CommentFragment.class);
        mIntent.putExtra(Constant.IntentKey.ISSUE_ID, share_issue_id);
        mIntent.putExtra(Constant.IntentKey.ISSUE_POSITION, issue_action_position);
        startActivityForResult(mIntent, COMMENT_RESPONSE);
    }


    @Override
    public void onShareClick(int issue_id, String userName, String voice_title) {

        if (isReadStorageAllowed()) {

            BranchShareClass.generateShareLink(this, this, userName, voice_title,
                    Constant.ShareCampaign.VOICE, Constant.ShareLinkTag.VOICE, String.valueOf(issue_id), Constant.ShareLinkTag.VOICE);

        } else {
            requestStoragePermission();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == VOICE_CREATE_MODEL) {
                VoiceAllModel voiceCreate = data.getParcelableExtra(Constant.IntentKey.VOICE_MODEL);
                int mVoicePostion = data.getIntExtra(Constant.IntentKey.POSITION, 0);
                if (voiceCreate.getId() != 0) {
                    newsAdapter.deleteVoice(mVoicePostion);
                }
                News mNews = new News();
                mNews.setType(5);
                mNews.setHotTopicIssue(voiceCreate);
                newsAdapter.addLocalNewsOnTop(mNews);
                if (!data.getBooleanExtra(Constant.IntentKey.IS_VOICE_POSTED, false))
                    voiceViewModel.setFeedData(voiceCreate);
                linearLayoutManager.scrollToPositionWithOffset(0, 0);
            } else if (requestCode == COMMENT_RESPONSE) {
                onCommentAddListener(
                        data.getIntExtra(Constant.IntentKey.COMMENT_POSITION, 0),
                        data.getIntExtra(Constant.IntentKey.MESSAGE_COUNT, 0));

            }
        }
    }

    public void onCommentAddListener(int position, int count) {
        newsAdapter.addCommentCount(position, count);

    }


    @Override
    public void onFollowClick(String from, int actual_position, int position, int issue_id, int image_position) {
        follow_position = position;
        viewholderPosition = actual_position;
        detailHandler.onFollow(issue_id);
    }

    @Override
    public void onUnFollowClick(String from, int actual_position, int position, int issue_id, int image_position) {
        follow_position = position;
        viewholderPosition = actual_position;
        detailHandler.onUnFollow(issue_id);
    }

    @Override
    public void onTagLeaderClick(int leader_id) {
        Intent mIntent = new Intent(this, NewLeaderProfileActivity.class);
        mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, leader_id);
        mIntent.putExtra(Constant.IntentKey.FROM, Constant.IntentKey.NOTIFICATION);
        startActivity(mIntent);
    }

    @Override
    public void onImageClick(VoiceAllViewHolder voiceAllViewHolder, int position, int issue_id, List<String> imageList) {
        ArrayList<String> strings = new ArrayList<>(imageList);
        FragmentManager fm = getSupportFragmentManager();
        ImageBigFragment.getInstance(position, issue_id, strings, null).show(fm, getString(R.string.dialog_fragment));
    }

    @Override
    public void onCreatorImageClick(int position, int user_id) {
        Intent mIntent = new Intent(this, VoiceCreatorProfile.class);
        mIntent.putExtra(Constant.IntentKey.USER_ID, user_id);
        startActivity(mIntent);
    }

    @Override
    public void onHashTagClick(int tag_id, String tag_name) {
        Intent mIntent = new Intent(this, HotTopicDetailActivity.class);
        mIntent.putExtra(Constant.IntentKey.HOT_TOPIC_ID, tag_id);
        mIntent.putExtra(Constant.IntentKey.HOT_TOPIC_STRING, tag_name);
        startActivity(mIntent);
    }

    @Override
    public void onDetailClick(int position, int tempPosition, VoiceAllModel voiceModel) {

    }

    @Override
    public void onLikeDislikeClick(int issue_id, int action) {
        LikeDislikeParticipantDialog.getInstance(this, issue_id, action).show(getSupportFragmentManager(), "LikeDialog");
    }


    @Override
    public void onVideoClick(VoiceAllViewHolder voiceAllViewHolder, String from, int clickPosition, int issueId) {
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

        ArrayList<VoiceAllModel> voiceVideoList = new ArrayList<>();
        for (int i = firstVisibleItemPosition; i < newsAdapter.getLocalNewsList().size(); i++) {
            if (newsAdapter.getLocalNewsList().get(i).getHotTopicIssue() != null) {
                voiceVideoList.add(newsAdapter.getLocalNewsList().get(i).getHotTopicIssue());
                break;
            }
        }
        Intent mIntent = new Intent(this, VoiceVideoActivity.class);
        mIntent.putParcelableArrayListExtra(Constant.IntentKey.VOICE_MODEL, voiceVideoList);
        mIntent.putExtra(Constant.IntentKey.ISSUE_ID, 0);
        startActivity(mIntent);
    }

    @Override
    public void onUserFollowActionClick(int userId, int action) {

    }

    @Override
    public void onReportDialogClick(int userId, int issueId) {
        ReportFeedDialog reportFeedDialog = new ReportFeedDialog();
        Bundle mBundle = new Bundle();
        mBundle.putInt(Constant.IntentKey.USER_ID, userId);
        mBundle.putInt(Constant.IntentKey.ISSUE_ID, issueId);
        mBundle.putString(Constant.IntentKey.FROM, getString(R.string.feed));
        reportFeedDialog.setArguments(mBundle);
        if (getFragmentManager() != null) {
            reportFeedDialog.show(getSupportFragmentManager(), getString(R.string.report_dialog));
        }
    }

    private boolean isReadStorageAllowed() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission() {

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE

        }, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, getString(R.string.denied_permission), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        ShareScreenShot.takeScreenshot(this, getString(R.string.media_of_politics) + full_txt, url);
    }

    @Override
    public void onVoiceCreateResponse(APIResponse apiResponse) {
        refreshUI();
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void onVideoProgressChanged(int id, int percentDone, int count) {

        if (hotTopicDetailBinding.newsRecyclerView.findViewHolderForAdapterPosition(0) instanceof VoiceAllViewHolder) {
            VoiceAllViewHolder voiceAllViewHolder = (VoiceAllViewHolder) hotTopicDetailBinding.newsRecyclerView.findViewHolderForAdapterPosition(0);
            if (voiceAllViewHolder != null) {
                voiceAllViewHolder.overLay.setVisibility(View.VISIBLE);
                voiceAllViewHolder.video_uploading_bar.setVisibility(View.VISIBLE);
                voiceAllViewHolder.video_uploading_bar.setProgress(percentDone);
            }
        }
    }

    @Override
    public void videoHandleError(Exception ex) {
        newsAdapter.removeVoiceOnTop(0);
        DialogClass.showAlert(this, getString(R.string.check_internet_connection));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
