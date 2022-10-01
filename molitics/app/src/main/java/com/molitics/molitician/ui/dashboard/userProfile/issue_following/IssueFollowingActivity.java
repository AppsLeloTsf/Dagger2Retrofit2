package com.molitics.molitician.ui.dashboard.userProfile.issue_following;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.Data;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.hotTopics.hotTopicDetail.HotTopicDetailActivity;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.NewLeaderProfileActivity;
import com.molitics.molitician.ui.dashboard.login.LoginDialogFragment;
import com.molitics.molitician.ui.dashboard.voice.VoiceFragment;
import com.molitics.molitician.ui.dashboard.voice.ImageBigFragment;
import com.molitics.molitician.ui.dashboard.voice.VoiceCreatorProfile;
import com.molitics.molitician.ui.dashboard.voice.adapter.VoiceAllAdapter;
import com.molitics.molitician.ui.dashboard.voice.feedAction.LikeDislikeParticipantDialog;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.ui.dashboard.voice.reportFeed.ReportFeedDialog;
import com.molitics.molitician.ui.dashboard.voice.video_module.VoiceVideoActivity;
import com.molitics.molitician.ui.dashboard.voice.viewHolder.VoiceAllViewHolder;
import com.molitics.molitician.ui.dashboard.voice.voiceComment.CommentFragment;
import com.molitics.molitician.ui.dashboard.voice.voiceDetail.VoiceDetailActivity;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.MoliticsAppPermission;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.ShareScreenShot;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.ene.toro.widget.Container;

import static com.molitics.molitician.ui.dashboard.voice.VoiceFragment.COMMENT_RESPONSE;
import static com.molitics.molitician.ui.dashboard.voice.VoiceFragment.VOICE_MODEL_INTENT;
import static com.molitics.molitician.util.Constant.RequestTag.LOGINDIALOG;

/**
 * Created by rahul on 11/12/17.
 */

public class IssueFollowingActivity extends BasicAcivity implements IssueFollowingPresenter.IssueFollowingUi,
        VoiceAllAdapter.VoiceInterFace, ViewRefreshListener, BranchShareClass.DeepLinkCallBack {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_progress_bar)
    ProgressBar main_progress_bar;
    @BindView(R.id.loading_grid)
    LinearLayout loading_grid;
    @BindView(R.id.state_recycler)
    Container recyclerView;
    @BindView(R.id.progressbarLayout)
    LinearLayout progressBar;
    @BindView(R.id.RL_handler)
    RelativeLayout RL_handler;

    int count = 0;
    IssueFollowingAdapter voiceAllAdapter;

    IssueFollowingHandler issueFollowingHandler;

    int issue_action_position = 0;
    int issue_action = 0;
    int follow_position = 0, viewholderPosition = 0;
    LinearLayoutManager linearLayoutManager;
    private int share_issue_id;
    private String adapter_from = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_past_election);
        ButterKnife.bind(this);
        setToolbar();
        main_progress_bar.setVisibility(View.VISIBLE);
        issueFollowingHandler = new IssueFollowingHandler(this);
        issueFollowingHandler.getFollowingIssue(count);
        bindUI();
    }

    private void bindUI() {
        recyclerView.setVisibility(View.VISIBLE);
        voiceAllAdapter = new IssueFollowingAdapter(this, this, false);

        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(voiceAllAdapter);
    }


    private void setToolbar() {
        setSupportActionBar(toolbar);
        showHeader(true, getResources().getString(R.string.issue_following));
        toolbar.setNavigationOnClickListener(v -> goBack());
    }

    @Override
    public void onIssueFollowingResponse(APIResponse apiResponse) {
        RL_handler.removeAllViews();
        main_progress_bar.setVisibility(View.GONE);
        loading_grid.setVisibility(View.GONE);
        Data mData = apiResponse.getData();

        if (mData.getCount() == 1) {
            showHeader(true, mData.getCount() + getResources().getString(R.string.issue_following));
        } else {

            showHeader(true, mData.getCount() + " " + getResources().getString(R.string.issues_following));
        }


        if (count == Constant.ZERO) {
            voiceAllAdapter.clearVoiceList();
        }
        voiceAllAdapter.addVoiceList(mData.getVoiceAllModels());
    }

    @Override
    public void onIssueFollowingError(ApiException apiException) {
        loading_grid.setVisibility(View.GONE);
        main_progress_bar.setVisibility(View.GONE);

        if (apiException.getCode() == 100003 && voiceAllAdapter.getVoiceListSize() != 0) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        } else {
            Util.validateError(this, apiException, RL_handler,
                    this, voiceAllAdapter.getVoiceListSize());
        }
    }

    @Override
    public void onLikeDislikeResponse(APIResponse apiResponse) {
        voiceAllAdapter.updateLikeDislike(issue_action_position,
                apiResponse.getData().getLike_count(), apiResponse.getData().getDislike_count(), issue_action);

        getCurrentItemPosition(viewholderPosition, apiResponse.getData().getLike_count(), apiResponse.getData().getDislike_count());
    }

    private void getCurrentItemPosition(int position, int like_count, int dislike_count) {
        VoiceAllViewHolder voiceAllViewHolder;
        if (recyclerView.findViewHolderForAdapterPosition(position) instanceof VoiceAllViewHolder) {
            voiceAllViewHolder = (VoiceAllViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
            voiceAllViewHolder.like_count_view.setText("" + like_count);
            voiceAllViewHolder.dislike_count_view.setText("" + dislike_count);
        }
    }

    @Override
    public void onLikeDislikeError(ApiException apiException) {
    }

    @Override
    public void onFollowResponse(APIResponse apiResponse) {
        voiceAllAdapter.onFollowResponse(follow_position, apiResponse.getData().getCount());
        onFollowResponseUpdateView(viewholderPosition, apiResponse.getData().getCount());
    }

    private void onFollowResponseUpdateView(int position, int follow_count) {
        VoiceAllViewHolder voiceAllViewHolder;
        if (recyclerView.findViewHolderForAdapterPosition(position) instanceof VoiceAllViewHolder) {
            voiceAllViewHolder = (VoiceAllViewHolder)
                    recyclerView.findViewHolderForAdapterPosition(position);
            voiceAllViewHolder.follow_txt.setTextColor(getResources().getColor(R.color.theme));
            voiceAllViewHolder.follow_txt.setText("following " + follow_count);
            voiceAllViewHolder.follow_txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.flag_blue, 0, 0, 0);
        }
    }

    @Override
    public void onFollowError(ApiException apiException) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case VOICE_MODEL_INTENT:
                    if (data != null) {
                        int position = data.getIntExtra(Constant.IntentKey.POSITION, 0);
                        VoiceAllModel voiceAllModel = data.getExtras().getParcelable(Constant.IntentKey.VOICE_MODEL);
                        voiceAllAdapter.updateVoiceModel(voiceAllModel, position);
                    } else {
                        count = 0;
                        issueFollowingHandler.getFollowingIssue(count);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onUnFollowResponse(APIResponse apiResponse) {
        voiceAllAdapter.onUnFollowResponse(follow_position, apiResponse.getData().getCount());
        onUnFollowResponseUpdateView(viewholderPosition, apiResponse.getData().getCount());
    }

    private void onUnFollowResponseUpdateView(int position, int follow_count) {
        VoiceAllViewHolder voiceAllViewHolder;

        if (recyclerView.findViewHolderForAdapterPosition(position) instanceof VoiceAllViewHolder) {
            voiceAllViewHolder = (VoiceAllViewHolder)
                    recyclerView.findViewHolderForAdapterPosition(position);
            voiceAllViewHolder.follow_txt.setTextColor(getResources().getColor(R.color.follow_txte));
            voiceAllViewHolder.follow_txt.setText("follow " + follow_count);
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
            voiceAllAdapter.deleteVoice(issue_action_position);
        } else {
            Util.validateError(this, apiException, null, null, null);
        }
    }

    @Override
    public void onUserImageResponse(APIResponse apiResponse) {
    }

    @Override
    public void onUserImageError(ApiException apiException) {
    }

    @Override
    public void onLoadMore() {
        loading_grid.setVisibility(View.VISIBLE);
        count = voiceAllAdapter.getVoiceListSize();
        issueFollowingHandler.getFollowingIssue(count);
    }

    @Override
    public void onCreateVoiceClick(int itemSelection) {

    }


    @Override
    public void onLikeDislikeClick(String from, int actual_position, int position, int issue_id, int action, int image_position) {

        issue_action_position = position;
        viewholderPosition = actual_position;
        this.share_issue_id = issue_id;
        issue_action = action;
        adapter_from = from;

        if (PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY).isEmpty()) {
            showLoginDialog();
        } else issueFollowingHandler.likeDislike(share_issue_id, issue_action);
    }

    private void showLoginDialog() {
        DialogFragment dialogFragment = LoginDialogFragment.getInstance();
        // dialogFragment.setTargetFragment(VoiceFragment.this, LOGINDIALOG);
        dialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_fragment));
    }

    @Override
    public void onDotClick(int position, int issue_id, int action_type, VoiceAllModel voiceModel) {
    }

    @Override
    public void onCommentClick(String from, int position, int issue_id, int image_position) {
        this.share_issue_id = issue_id;
        issue_action_position = position;
        adapter_from = Constant.From.COMMNET_ACTION;
        if (PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY).isEmpty()) {
            showLoginDialog();
        } else performCommentOnLogin();

        ////// setGAEvent(Constant.GoogleAnalyticsKey.COMMENT + "  " + issue_id);
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
            BranchShareClass.generateShareLink(this, this, userName,
                    voice_title, Constant.ShareCampaign.VOICE, Constant.ShareLinkTag.VOICE, String.valueOf(issue_id), Constant.ShareLinkTag.VOICE);

        } else {
            MoliticsAppPermission.requestStoragePermission(this);
        }
    }

    //We are calling this method to check the permission status
    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //If permission is granted returning true
        return result == PackageManager.PERMISSION_GRANTED;
        //If permission is not granted returning false
    }


    @Override
    public void onUnFollowClick(String from, int actual_position, int position, int issue_id, int image_position) {
        follow_position = position;
        issueFollowingHandler.onUnFollow(issue_id);
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
        ImageBigFragment.getInstance(position, issue_id, strings, null).show(fm, "Dialog Fragment");
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
        Intent mIntent = new Intent(this, VoiceDetailActivity.class);
        mIntent.putExtra(Constant.IntentKey.VOICE_MODEL, voiceModel);
        mIntent.putExtra(Constant.IntentKey.TEMP_POSITION, tempPosition);
        mIntent.putExtra(Constant.IntentKey.POSITION, position);
        startActivityForResult(mIntent, VoiceFragment.VOICE_MODEL_INTENT);
    }

    @Override
    public void onLikeDislikeClick(int issue_id, int action) {
        FragmentManager fm = getSupportFragmentManager();
        LikeDislikeParticipantDialog.getInstance(this, issue_id, action).show(fm, "LikeDialog");
    }

    @Override
    public void onVideoClick(VoiceAllViewHolder voiceAllViewHolder, String from, int clickPosition, int issueId) {
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        ArrayList<VoiceAllModel> voiceVideoList;
        if (firstVisibleItemPosition > 2) {
            voiceVideoList = new ArrayList<VoiceAllModel>(voiceAllAdapter.getVoiceList().subList(firstVisibleItemPosition - 2, voiceAllAdapter.getVoiceList().size()));
        } else {
            voiceVideoList = new ArrayList<>(voiceAllAdapter.getVoiceList());
        }

        Intent mIntent = new Intent(this, VoiceVideoActivity.class);
        mIntent.putParcelableArrayListExtra(Constant.IntentKey.VOICE_MODEL, voiceVideoList);
        mIntent.putExtra(Constant.IntentKey.ISSUE_ID, issueId);
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


    @Override
    public void onFollowClick(String from, int actual_position, int position, int issue_id, int image_position) {
        follow_position = position;
        viewholderPosition = actual_position;

        issueFollowingHandler.onFollow(issue_id);
    }

    @Override
    public void onRefereshClick() {
        issueFollowingHandler.getFollowingIssue(count);
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }

    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        ShareScreenShot.takeScreenshot(this, getString(R.string.media_of_politics) + full_txt, url);
    }
}
