package com.molitics.molitician.ui.dashboard.userProfile;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.molitics.molitician.BaseFragment;
import com.molitics.molitician.GoogleAnalytics.GAEventTracking;
import com.molitics.molitician.R;
import com.molitics.molitician.constants.ApiFieldConstant;
import com.molitics.molitician.databinding.HomeUserProfileBinding;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.NewLeaderProfileActivity;
import com.molitics.molitician.ui.dashboard.login.LoginDialogFragment;
import com.molitics.molitician.ui.dashboard.userProfile.model.UserProfileModel;
import com.molitics.molitician.ui.dashboard.voice.VoiceViewNavigation;
import com.molitics.molitician.ui.dashboard.voice.createVoice.CreateVoiceActivity;
import com.molitics.molitician.ui.dashboard.voice.ImageBigFragment;
import com.molitics.molitician.ui.dashboard.voice.VoiceHandler;
import com.molitics.molitician.ui.dashboard.voice.VoicePresenter;
import com.molitics.molitician.ui.dashboard.voice.adapter.VoiceAllAdapter;
import com.molitics.molitician.ui.dashboard.voice.feedAction.LikeDislikeParticipantDialog;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceViewModel;
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

import javax.inject.Inject;

import im.ene.toro.PlayerSelector;
import im.ene.toro.ToroPlayer;
import im.ene.toro.media.PlaybackInfo;

import static android.app.Activity.RESULT_OK;
import static com.molitics.molitician.MolticsApplication.getAndroid_id;
import static com.molitics.molitician.ui.dashboard.voice.VoiceFragment.COMMENT_RESPONSE;
import static com.molitics.molitician.ui.dashboard.voice.VoiceFragment.VOICE_CREATE_MODEL;
import static com.molitics.molitician.ui.dashboard.voice.VoiceFragment.VOICE_MODEL_INTENT;
import static com.molitics.molitician.util.Constant.RequestTag.LOGINDIALOG;
import static com.molitics.molitician.util.Constant.STORAGE_PERMISSION_CODE;

/**
 * Created by rahul on 30/11/17.
 */

public class UserProfileFragment extends BaseFragment<HomeUserProfileBinding, VoiceViewModel> implements UserProfilePresenter.UserProfileView, ViewRefreshListener,
        VoiceAllAdapter.VoiceInterFace, VoicePresenter.VoiceUI, UserProfileAdapter.UserProfileListener, BranchShareClass.DeepLinkCallBack,
        VoicePresenter.VoiceActionUi, ImageBigFragment.Callback, VoiceViewNavigation {

    @Inject
    VoiceViewModel voiceViewModel;


    private VoiceHandler voiceHandler;
    private UserProfileAdapter voiceAllAdapter;
    private int count = 0;
    private int issue_action_position = 0;
    private int issue_action = 0;
    private String adapter_from = "";

    private int follow_position = 0;
    private int user_id = 0;
    private MenuItem editItem;
    private int image_position;
    private UserProfileHandler profileHandler;
    private UserProfileModel userProfileModel;
    private int share_issue_id;
    private LinearLayoutManager linearLayoutManager;

    HomeUserProfileBinding fragmentUserBinding;
    final int EDIT_ACTIVITY_CODE = 100;

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_user_profile;
    }

    @Override
    public VoiceViewModel getViewModel() {
        return voiceViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileHandler = new UserProfileHandler(this);
        voiceHandler = new VoiceHandler(this, this);
        setHasOptionsMenu(true);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getToolBar();

        fragmentUserBinding = getViewDataBinding();
        voiceViewModel.setNavigator(this);
        voiceViewModel.setContext(getContext());
        checkUserLoginOrNot();

        bindUi();
        fragmentUserBinding.recyclerView.setVisibility(View.GONE);
        fragmentUserBinding.includeLayout.mainProgressBar.setVisibility(View.VISIBLE);
        setVideoRef();
    }

    private void checkUserLoginOrNot() {
        Bundle mBundle = getArguments();

        if (mBundle != null && mBundle.getInt(Constant.IntentKey.USER_ID) != 0) {
            user_id = mBundle.getInt(Constant.IntentKey.USER_ID);
            profileHandler.getProfileData(user_id, count, getAndroid_id);
        } else if (PrefUtil.getInt(Constant.PreferenceKey.USER_ID) != 0 && PrefUtil.getInt(Constant.PreferenceKey.USER_ID) != -1) {
            user_id = PrefUtil.getInt(Constant.PreferenceKey.USER_ID);
            profileHandler.getProfileData(user_id, count, getAndroid_id);
        } else {
            ///  showLoginDialog();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getToolBar() {
        @SuppressWarnings("ConstantConditions") Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        RelativeLayout center_toolbar_rl = toolbar.findViewById(R.id.center_toolbar_rl);
        if (center_toolbar_rl != null) {
            center_toolbar_rl.removeAllViews();

            View child = getLayoutInflater().inflate(R.layout.common_txt_view, null);
            center_toolbar_rl.addView(child);

            TextView header_text = child.findViewById(R.id.state_name);
            header_text.setText(R.string.my_profile);
        }
    }


    private void bindUi() {
        voiceAllAdapter = new UserProfileAdapter(getContext(), this, this, user_id);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        fragmentUserBinding.recyclerView.setLayoutManager(linearLayoutManager);
        fragmentUserBinding.recyclerView.setAdapter(voiceAllAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_blank, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                startActivityForResult(
                        new Intent(getContext(), EditUserProfileActivity.class), EDIT_ACTIVITY_CODE);

                setGAEvent(Constant.GoogleAnalyticsKey.EDIT_CLICK);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EDIT_ACTIVITY_CODE:
                    if (data != null) {
                        userProfileModel = data.getExtras().getParcelable(Constant.IntentKey.USER_PROFILE_MODEL);
                        voiceAllAdapter.addUserProfileData(userProfileModel);
                        voiceAllAdapter.notifyDataSetChanged();
                    }
                    voiceHandler.getUserImage(user_id);
                    break;
                case VOICE_MODEL_INTENT:
                    if (data != null) {
                        int position = data.getIntExtra(Constant.IntentKey.POSITION, 0);
                        VoiceAllModel voiceAllModel = data.getExtras().getParcelable(Constant.IntentKey.VOICE_MODEL);
                        voiceAllAdapter.updateVoiceModel(position, voiceAllModel);
                        count = 0;
                        profileHandler.getProfileData(user_id, count, getAndroid_id);
                    } else {
                        count = 0;
                        profileHandler.getProfileData(user_id, count, getAndroid_id);
                    }
                    break;
                case COMMENT_RESPONSE:
                    onCommentAddListener(data.getIntExtra(Constant.IntentKey.COMMENT_POSITION, 0),
                            data.getIntExtra(Constant.IntentKey.MESSAGE_COUNT, 0));
                    break;
                case VOICE_CREATE_MODEL:
                    VoiceAllModel voiceCreate = data.getParcelableExtra(Constant.IntentKey.VOICE_MODEL);

                    voiceAllAdapter.updateVoiceModel(Constant.ZERO, voiceCreate);
                    if (!data.getBooleanExtra(Constant.IntentKey.IS_VOICE_POSTED, false))
                        voiceViewModel.setFeedData(voiceCreate);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRefereshClick() {
        fragmentUserBinding.includeLayout.mainProgressBar.setVisibility(View.VISIBLE);
        profileHandler.getProfileData(user_id, count, getAndroid_id);
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }

    @Override
    public void onProfileDataResponse(APIResponse apiResponse) {
        fragmentUserBinding.includeLayout.mainProgressBar.setVisibility(View.GONE);

        fragmentUserBinding.recyclerView.setVisibility(View.VISIBLE);

        userProfileModel = apiResponse.getData().getUserProfileModel();
        voiceAllAdapter.addUserProfileData(userProfileModel);
        voiceAllAdapter.clearVoiceList();
        voiceAllAdapter.addVoiceList(apiResponse.getData().getUserFeeds());

    }

    @Override
    public void onProfileDataApiException(ApiException apiException) {
        fragmentUserBinding.includeLayout.mainProgressBar.setVisibility(View.GONE);
        Util.validateError(getContext(), apiException, fragmentUserBinding.childFragment, this, voiceAllAdapter.getVoiceListSize());
    }

    @Override
    public void onProfileDataServerError(ServerException serverException) {
        fragmentUserBinding.includeLayout.mainProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void onProfileResponse(APIResponse apiResponse) {
        fragmentUserBinding.includeLayout.mainProgressBar.setVisibility(View.GONE);


    }

    @Override
    public void onProfileApiException(ApiException apiException) {
        fragmentUserBinding.includeLayout.mainProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void onProfileServerError(ServerException serverException) {
        fragmentUserBinding.includeLayout.mainProgressBar.setVisibility(View.GONE);


    }

    @Override
    public void onUserFollowResponse(APIResponse apiResponse) {
        voiceAllAdapter.followUser(true, apiResponse.getData().getCount());
    }

    @Override
    public void onUserFollowError(ApiException apiException) {

    }

    @Override
    public void onUserUnFollowResponse(APIResponse apiResponse) {
        voiceAllAdapter.followUser(false, apiResponse.getData().getCount());
    }

    @Override
    public void onUserUnFollowError(ApiException apiException) {

    }

    @Override
    public void onUserFollowingListResponse(APIResponse apiResponse) {

    }

    @Override
    public void onUserFollowingListError(ApiException apiException) {

    }

    @Override
    public void onVoiceResponse(APIResponse apiResponse) {
        //blank
    }

    @Override
    public void onVoiceApiError(ApiException apiexception) {
        //blank
    }

    @Override
    public void onLikeDislikeResponse(APIResponse apiResponse) {
        voiceAllAdapter.updateLikeDislike(issue_action_position,
                apiResponse.getData().getLike_count(), apiResponse.getData().getDislike_count(), issue_action, image_position);

    }

    @Override
    public void onLikeDislikeError(ApiException apiException) {
    }

    @Override
    public void onFollowResponse(APIResponse apiResponse) {
        voiceAllAdapter.onFollowResponse(follow_position, apiResponse.getData().getCount(), image_position);
    }

    @Override
    public void onFollowError(ApiException apiException) {

    }

    @Override
    public void onUnFollowResponse(APIResponse apiResponse) {
        voiceAllAdapter.onUnFollowResponse(follow_position, apiResponse.getData().getCount(), image_position);
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
            Util.validateError(getContext(), apiException, null, null, null);
        }
    }

    @Override
    public void onUserImageResponse(APIResponse apiResponse) {
        if (userProfileModel != null) {
            userProfileModel.setImage(apiResponse.getData().getUserImageUrl());
            voiceAllAdapter.replaceUserImage(apiResponse.getData().getUserImageUrl());
        }
    }

    @Override
    public void onUserImageError(ApiException apiException) {
    }

    @Override
    public void onLoadMore() {
        count = voiceAllAdapter.getVoiceListSize();
        profileHandler.getProfileData(user_id, count, getAndroid_id);
    }

    @Override
    public void onCreateVoiceClick(int itemSelection) {

    }


    @Override
    public void onLikeDislikeClick(String from, int actual_position, int position, int issue_id, int action, int image_position) {

        this.image_position = image_position;
        issue_action_position = position;
        this.share_issue_id = issue_id;
        issue_action = action;
        adapter_from = from;

        if (PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY).isEmpty()) {
            showLoginDialog();
        } else voiceHandler.likeDislike(share_issue_id, issue_action);
    }

    private void showLoginDialog() {
        DialogFragment dialogFragment = LoginDialogFragment.getInstance();
        dialogFragment.setTargetFragment(this, LOGINDIALOG);
        dialogFragment.show(getFragmentManager(), getString(R.string.dialog_fragment));
    }

    @Override
    public void onDotClick(int position, int issue_id, int action_type, VoiceAllModel voiceAllModel) {
        if (action_type == Constant.Action.EDIT) {
            Intent mIntent = new Intent(getContext(), CreateVoiceActivity.class);

            mIntent.putExtra(Constant.IntentKey.ISSUE_MODEL, voiceAllModel);
            mIntent.putExtra(Constant.IntentKey.POSITION, position);
            startActivityForResult(mIntent, VOICE_CREATE_MODEL);
        } else if (action_type == Constant.Action.DELETE) {
            issue_action_position = position;
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(R.string.do_you_want_to_delete_issue)
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes, (dialog, id) -> {
                        voiceHandler.deleteIssue(issue_id);
                        ApiFieldConstant.shouldChange = true;
                    })
                    .setNegativeButton(R.string.no, (dialog, id) -> {
                        dialog.cancel();
                    });
            AlertDialog alert = builder.create();
            alert.setTitle(getString(R.string.issue));
            alert.show();
        } else if (action_type == Constant.Action.SHARE) {
            onShareClick(issue_id, voiceAllModel.getUserName(), voiceAllModel.getContent());
        }

        ///  System.out.("action_type--" + action_type);
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
        Intent mIntent = new Intent(getContext(), CommentFragment.class);
        mIntent.putExtra(Constant.IntentKey.ISSUE_ID, share_issue_id);
        mIntent.putExtra(Constant.IntentKey.ISSUE_POSITION, issue_action_position);
        startActivityForResult(mIntent, COMMENT_RESPONSE);
    }

    @Override
    public void onShareClick(int issue_id, String userName, String voice_title) {
        share_issue_id = issue_id;
        if (isReadStorageAllowed()) {
            BranchShareClass.generateShareLink(getActivity(), this, userName,
                    voice_title, Constant.ShareCampaign.VOICE, Constant.ShareLinkTag.VOICE, String.valueOf(issue_id), "user-feed/detail");
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                MoliticsAppPermission.requestReadStoragePermission(getContext());
            }
        }
    }

    //We are calling this method to check the permission status
    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                BranchShareClass.generateShareLink(getActivity(), this, "Molitics Voice", "",
                        Constant.ShareCampaign.VOICE, Constant.ShareLinkTag.VOICE, String.valueOf(share_issue_id), "user-feed/detail");

                //Displaying a toast
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onUnFollowClick(String from, int actual_position, int position, int issue_id, int image_position) {
        this.image_position = image_position;
        follow_position = position;
        voiceHandler.onUnFollow(issue_id);
    }

    @Override
    public void onTagLeaderClick(int leader_id) {
        Intent mIntent = new Intent(getContext(), NewLeaderProfileActivity.class);
        mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, leader_id);
        mIntent.putExtra(Constant.IntentKey.FROM, Constant.IntentKey.NOTIFICATION);
        startActivity(mIntent);
    }

    @Override
    public void onImageClick(VoiceAllViewHolder voiceAllViewHolder, int position, int issue_id, List<String> imageList) {
        if (voiceAllViewHolder instanceof ToroPlayer) {
            PlaybackInfo info = ((ToroPlayer) voiceAllViewHolder).getCurrentPlaybackInfo();
            ArrayList<String> strings = new ArrayList<>(imageList);
            ImageBigFragment.getInstance(position, issue_id, strings, info).show(getChildFragmentManager(), "Dialog Fragment");
        }
    }

    @Override
    public void onCreatorImageClick(int position, int user_id) {
    }

    @Override
    public void onHashTagClick(int tag_id, String tag_name) {
    }

    @Override
    public void onDetailClick(int position, int tempPosition, VoiceAllModel voiceModel) {
        Intent mIntent = new Intent(getContext(), VoiceDetailActivity.class);
        mIntent.putExtra(Constant.IntentKey.VOICE_MODEL, voiceModel);
        mIntent.putExtra(Constant.IntentKey.TEMP_POSITION, tempPosition);
        mIntent.putExtra(Constant.IntentKey.POSITION, position);
        startActivityForResult(mIntent, VOICE_MODEL_INTENT);
    }

    @Override
    public void onLikeDislikeClick(int issue_id, int action) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        LikeDislikeParticipantDialog.getInstance(getContext(), issue_id, action).show(fm, "LikeDialog");
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

        Intent mIntent = new Intent(getContext(), VoiceVideoActivity.class);
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
            reportFeedDialog.show(getFragmentManager(), getString(R.string.report_dialog));
        }
    }

    @Override
    public void onFollowClick(String from, int actual_position, int position, int issue_id, int image_position) {
        follow_position = position;
        voiceHandler.onFollow(issue_id);
    }


    public void onCommentAddListener(int position, int count) {
        voiceAllAdapter.editCommentCount(position, count);
    }

    @Override
    public void onUserFollow(int user_id) {
        setResultToVoiceFragment(1);
        profileHandler.userFollow(user_id);
    }

    @Override
    public void onUserUnFollow(int user_id) {
        setResultToVoiceFragment(0);
        profileHandler.userUnFollow(user_id);
    }


    @Override
    public void onProfileImageClick(String image) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(image);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ImageBigFragment.getInstance(Constant.ZERO, Constant.ZERO, strings, null).show(fm, "Dialog Fragment");
    }

    @Override
    public void getUserFollowingList(int user_id, int action) {

        Dialog participantDialog = FollowUnfollowParticipantDialog.getInstance(getContext(), user_id, 0, action);
        participantDialog.show();
    }


    @Override
    public void onReportDialogClick(int userId) {
        ReportFeedDialog reportFeedDialog = new ReportFeedDialog();
        Bundle mBundle = new Bundle();
        mBundle.putInt(Constant.IntentKey.REPORT_ID, userId);
        mBundle.putString(Constant.IntentKey.FROM, getString(R.string.user));
        reportFeedDialog.setArguments(mBundle);
        if (getFragmentManager() != null) {
            reportFeedDialog.show(getFragmentManager(), getString(R.string.report_dialog));
        }
    }

    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        ShareScreenShot.takeScreenshot(getActivity(), getString(R.string.media_of_politics) + full_txt, url);
    }

    final Handler handler = new Handler();
    PlayerSelector selector = PlayerSelector.DEFAULT;

    private void setVideoRef() {
        fragmentUserBinding.recyclerView.setPlayerSelector(null);
        // Using TabLayout has a downside: once we click to a tab to change page, there will be no animation,
        // which will cause our setup doesn't work well. We need a delay to make things work.
        handler.postDelayed(() -> {

            if (fragmentUserBinding.recyclerView != null)
                fragmentUserBinding.recyclerView.setPlayerSelector(selector);
        }, 500);
    }


    @Override
    public void onPlaylistCreated() {
        fragmentUserBinding.recyclerView.setPlayerSelector(PlayerSelector.NONE);
    }

    @Override
    public void onPlaylistDestroyed(int basePosition,/* VoiceAllModel baseItem,*/ PlaybackInfo latestInfo) {
        fragmentUserBinding.recyclerView.savePlaybackInfo(basePosition, latestInfo);
        fragmentUserBinding.recyclerView.setPlayerSelector(selector);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        selector = null;
    }


    private void setGAEvent(String action) {
        GAEventTracking.googleEventTracker(getActivity(), Constant.GoogleAnalyticsKey.USER_PROFILE, action);
    }

    @Override
    public void onVoiceCreateResponse(APIResponse apiResponse) {
        count = 0;
        profileHandler.getProfileData(user_id, count, getAndroid_id);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void onVideoProgressChanged(int id, int percentDone, int count) {
        voiceAllAdapter.updateProgressView(percentDone);
    }

    private void setResultToVoiceFragment(int userFollowAction) {
        Intent intent = new Intent();
        intent.putExtra(Constant.IntentKey.USER_ID, user_id);
        intent.putExtra(Constant.IntentKey.USER_FOLLOW, userFollowAction);
        if (getActivity() != null)
            getActivity().setResult(RESULT_OK, intent);
    }
}
