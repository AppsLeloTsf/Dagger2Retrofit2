package com.molitics.molitician.ui.dashboard.voice.video_module;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.hotTopics.hotTopicDetail.HotTopicDetailActivity;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.NewLeaderProfileActivity;
import com.molitics.molitician.ui.dashboard.login.LoginDialogFragment;
import com.molitics.molitician.ui.dashboard.voice.VoiceCreatorProfile;
import com.molitics.molitician.ui.dashboard.voice.VoiceFragment;
import com.molitics.molitician.ui.dashboard.voice.VoiceHandler;
import com.molitics.molitician.ui.dashboard.voice.VoicePresenter;
import com.molitics.molitician.ui.dashboard.voice.adapter.VoiceAllAdapter;
import com.molitics.molitician.ui.dashboard.voice.feedAction.LikeDislikeParticipantDialog;
import com.molitics.molitician.ui.dashboard.voice.model.GetVoiceVideoRequestModel;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.ui.dashboard.voice.reportFeed.ReportFeedDialog;
import com.molitics.molitician.ui.dashboard.voice.viewHolder.VoiceAllViewHolder;
import com.molitics.molitician.ui.dashboard.voice.voiceComment.CommentFragment;
import com.molitics.molitician.ui.dashboard.voice.voiceDetail.VoiceDetailActivity;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.MoliticsAppPermission;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.ShareScreenShot;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.ene.toro.CacheManager;
import im.ene.toro.ToroPlayer;
import im.ene.toro.widget.Container;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.molitics.molitician.util.Util.showProgressAnimation;

public class VoiceVideoActivity extends BasicAcivity implements VoiceAllAdapter.VoiceInterFace,
        VoicePresenter.VoiceUI, VoicePresenter.VoiceActionUi, BranchShareClass.DeepLinkCallBack {

    private String TAG = "VoiceVideoActivity";

    @BindView(R.id.main_progress_bar)
    ProgressBar main_progress_bar;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.news_recycler_view)
    Container recyclerView;
    @BindView(R.id.progressbarLayout)
    LinearLayout progressBar;
    @BindView(R.id.main_container)
    RelativeLayout main_container;
    @BindView(R.id.RL_handler)
    RelativeLayout RL_handler;
    @BindView(R.id.ll_parent_lay_news)
    LinearLayout ll_parent_lay_news;
    @BindView(R.id.rl_main)
    RelativeLayout rl_main;

    List<VoiceAllModel> videoVoiceAllModels;
    VoiceAllVideoAdapter voiceAllVideoAdapter;
    FragmentManager fm;
    private int COMMENT_RESPONSE = 300;

    private LinearLayoutManager linearLayoutManager;

    private int image_position = 0, viewholderPosition = 0;
    private VoiceHandler voiceHandler;
    private int issue_action_position = 0;
    private String adapter_from = "";
    private int follow_position = 0;
    private int issue_action = Constant.ZERO;
    private int issueId;
    private int share_issue_id;
    private int pageNumber = 0;
    private int currentVisibleFeed = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_voice);
        ButterKnife.bind(this);
        fm = getSupportFragmentManager();
        setAdapter();
        videoVoiceAllModels = new ArrayList<>();
        getMyIntent();
        //// disable ref
        swipeContainer.setRefreshing(false);
        swipeContainer.setEnabled(false);
        voiceHandler = new VoiceHandler(this, this);
        handleDarkTheme();
        onLoadMore();

    }

    private void handleDarkTheme() {
        recyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.more_videoBg));
    }

    private void getMyIntent() {
        Intent mIntent = getIntent();
        List<VoiceAllModel> voiceAllModels = mIntent.getParcelableArrayListExtra(Constant.IntentKey.VOICE_MODEL);
        issueId = mIntent.getIntExtra(Constant.IntentKey.ISSUE_ID, 0);
        Log.d(TAG, voiceAllModels.toString());
        findOnlyVideoList(voiceAllModels);
    }


    private void setAdapter() {
        voiceAllVideoAdapter = new VoiceAllVideoAdapter(this, this);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setCacheManager(CacheManager.DEFAULT);
        recyclerView.setAdapter(voiceAllVideoAdapter);
        setOnVideoComplete();
        recyclerChangeListener();

    }

    private void setOnVideoComplete() {
        voiceAllVideoAdapter.setOnCompleteCallback(new VoiceAllVideoAdapter.OnCompleteCallback() {
            @Override
            void onCompleted(ToroPlayer player) {
                int position = voiceAllVideoAdapter.findNextPlayerPosition(player.getPlayerOrder());
                //noinspection Convert2MethodRef
                moveToPosition(position);
            }
        });
    }

    @SuppressLint("CheckResult")
    private void moveToPosition(int position) {
        Observable.just(recyclerView)
                .delay(250, TimeUnit.MILLISECONDS)
                .filter(c -> c != null)
                .subscribe(rv -> rv.smoothScrollToPosition(position));
    }

    private void findOnlyVideoList(List<VoiceAllModel> voiceAllModels) {
        outer:
        for (VoiceAllModel voiceAllModel : voiceAllModels) {
            for (String videoPath : voiceAllModel.getImages()) {
                if (videoPath.contains(Constant.MP4)) {
                    voiceAllModel.getImages().clear();
                    voiceAllModel.getImages().add(videoPath);

                    videoVoiceAllModels.add(voiceAllModel);
                    currentVisibleFeed = voiceAllModel.getId();
                    break outer;
                }
            }
        }
        voiceAllVideoAdapter.addVoiceVideoList(videoVoiceAllModels);
    }

    @Override
    public void onLoadMore() {
        pageNumber++;
        getVoiceVideoFromServer(currentVisibleFeed);
    }

    @Override
    public void onCreateVoiceClick(int itemSelection) {

    }

    @Override
    public void onFollowClick(String from, int actual_position, int position, int issue_id, int image_position) {
        follow_position = actual_position;
        voiceHandler.onFollow(issue_id);
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
        Intent mIntent = new Intent(this, VoiceDetailActivity.class);
        mIntent.putExtra(Constant.IntentKey.VOICE_MODEL, voiceModel);
        mIntent.putExtra(Constant.IntentKey.TEMP_POSITION, tempPosition);
        mIntent.putExtra(Constant.IntentKey.POSITION, position);
        startActivityForResult(mIntent, VoiceFragment.VOICE_MODEL_INTENT);
    }

    @Override
    public void onLikeDislikeClick(int issue_id, int action) {
        LikeDislikeParticipantDialog.getInstance(this, issue_id, action).show(fm, "LikeDialog");
    }


    @Override
    public void onVideoClick(VoiceAllViewHolder voiceAllViewHolder, String from, int clickPosition, int issueId) {
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

    @Override
    public void onLikeDislikeClick(String from, int actual_position, int position, int issue_id, int action, int image_position) {
        issue_action_position = actual_position;
        viewholderPosition = actual_position;
        issue_action = action;
        this.share_issue_id = issue_id;
        if (PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY).isEmpty()) {
            showLoginDialog();
        } else voiceHandler.likeDislike(share_issue_id, issue_action);
    }

    @Override
    public void onDotClick(int position, int issue_id, int action_type, VoiceAllModel voiceAllModel) {
    }

    @Override
    public void onCommentClick(String from, int position, int issue_id, int image_position) {


        this.image_position = image_position;
        this.share_issue_id = issue_id;
        issue_action_position = position;
        adapter_from = Constant.From.COMMNET_ACTION;
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


    private void showLoginDialog() {
        DialogFragment dialogFragment = LoginDialogFragment.getInstance();
        //  dialogFragment.setTargetFragment(this, LOGINDIALOG);
        dialogFragment.show(fm, getString(R.string.dialog_fragment));
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onShareClick(int issue_id, String userName, String voice_title) {
        if (MoliticsAppPermission.checkWritePermission()) {
            BranchShareClass.generateShareLink(this, this, userName,
                    voice_title, Constant.ShareCampaign.VOICE, Constant.ShareLinkTag.VOICE, String.valueOf(issue_id), Constant.ShareLinkTag.VOICE);

        } else {
            MoliticsAppPermission.requestReadStoragePermission(this);
        }
    }


    @Override
    public void onUnFollowClick(String from, int actual_position, int position, int issue_id, int image_position) {
        this.image_position = image_position;
        viewholderPosition = actual_position;

        follow_position = actual_position;
        voiceHandler.onUnFollow(issue_id);
    }

    @Override
    public void onCreatorImageClick(int position, int user_id) {
        Intent mIntent = new Intent(this, VoiceCreatorProfile.class);
        mIntent.putExtra(Constant.IntentKey.USER_ID, user_id);
        startActivity(mIntent);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onVoiceResponse(APIResponse apiResponse) {

    }

    @Override
    public void onVoiceApiError(ApiException apiexception) {

    }

    @Override
    public void onUserImageResponse(APIResponse apiResponse) {

    }

    @Override
    public void onUserImageError(ApiException apiException) {

    }

    @Override
    public void onLikeDislikeResponse(APIResponse apiResponse) {
        voiceAllVideoAdapter.updateLikeDislike(issue_action_position,
                apiResponse.getData().getLike_count(), apiResponse.getData().getDislike_count(),
                issue_action, image_position);

        getCurrentItemPosition(viewholderPosition, apiResponse.getData().getLike_count(), apiResponse.getData().getDislike_count());
    }

    private void getCurrentItemPosition(int position, int like_count, int dislike_count) {
        VoiceVideoViewHolder voiceAllViewHolder;
        if (recyclerView.findViewHolderForAdapterPosition(position) instanceof VoiceVideoViewHolder) {
            voiceAllViewHolder = (VoiceVideoViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
            if (voiceAllViewHolder != null ) {
                VoiceAllViewHolder.setUpvoteText(this, voiceAllViewHolder.like_count_view, like_count);
                VoiceAllViewHolder.setDownvoteText(this, voiceAllViewHolder.dislike_count_view, dislike_count);

                int totoalVoteCount = like_count + dislike_count;
                if (totoalVoteCount != 0) {
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
                }
            }
        }
    }

    @Override
    public void onLikeDislikeError(ApiException apiException) {
    }

    @Override
    public void onFollowResponse(APIResponse apiResponse) {
        voiceAllVideoAdapter.onFollowResponse(follow_position, apiResponse.getData().getCount(), image_position);

        onFollowResponseUpdateView(viewholderPosition, apiResponse.getData().getCount());
    }

    private void onFollowResponseUpdateView(int position, int follow_count) {
        VoiceVideoViewHolder voiceAllViewHolder;
        if (recyclerView.findViewHolderForAdapterPosition(position) instanceof VoiceVideoViewHolder) {
            voiceAllViewHolder = (VoiceVideoViewHolder)
                    recyclerView.findViewHolderForAdapterPosition(position);
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
        voiceAllVideoAdapter.onUnFollowResponse(follow_position, apiResponse.getData().getCount(), image_position);
        onUnFollowResponseUpdateView(viewholderPosition, apiResponse.getData().getCount());

    }

    private void onUnFollowResponseUpdateView(int position, int follow_count) {
        VoiceAllViewHolder voiceAllViewHolder;

        if (recyclerView.findViewHolderForAdapterPosition(position) instanceof VoiceAllViewHolder) {
            voiceAllViewHolder = (VoiceAllViewHolder)
                    recyclerView.findViewHolderForAdapterPosition(position);
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
            voiceAllVideoAdapter.deleteVoice(issue_action_position);
        } else {
            Util.validateError(this, apiException, null, null, null);
        }
    }

    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        ShareScreenShot.takeScreenshot(this, getString(R.string.media_of_politics) + full_txt, url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == COMMENT_RESPONSE) {
                onCommentAddListener(
                        data.getIntExtra(Constant.IntentKey.ISSUE_POSITION, 0),
                        data.getIntExtra(Constant.IntentKey.MESSAGE_COUNT, 0));
            }
        }
    }

    public void onCommentAddListener(int position, int count) {
        voiceAllVideoAdapter.addCommentCount(position, count);
    }

    @SuppressLint("CheckResult")
    private void getVoiceVideoFromServer(int selectedFeedId) {
        GetVoiceVideoRequestModel requestModel = new GetVoiceVideoRequestModel(Arrays.asList(selectedFeedId));
        RetrofitRestClient.getInstance().getVoiceVideoFeed(pageNumber, requestModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(result -> {
            isLoadingData = false;
            replaceImageFromFeed(result.getData().getVoiceAllModels());
            Util.showLog(TAG, result.getMessage());
        }, error -> {

        });
    }

    private int pastVisiblesItems = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;
    private boolean isLoadingData = false;

    private void recyclerChangeListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {

                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (!isLoadingData) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            isLoadingData = true;
                            onLoadMore();
                        }
                    }
                }
            }
        });
    }

    private void replaceImageFromFeed(List<VoiceAllModel> voiceAllModels) {
        outer:
        for (VoiceAllModel voiceAllModel : voiceAllModels) {
            for (String videoPath : voiceAllModel.getImages()) {
                if (videoPath.contains(Constant.MP4)) {
                    voiceAllModel.getImages().clear();
                    voiceAllModel.getImages().add(videoPath);
                    break outer;
                }
            }
        }
        voiceAllVideoAdapter.addVoiceVideoList(voiceAllModels);
    }
}
