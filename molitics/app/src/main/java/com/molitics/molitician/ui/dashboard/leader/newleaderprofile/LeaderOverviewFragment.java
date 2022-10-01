package com.molitics.molitician.ui.dashboard.leader.newleaderprofile;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.molitics.molitician.BaseFragment;
import com.molitics.molitician.GoogleAnalytics.GAEventTracking;
import com.molitics.molitician.R;
import com.molitics.molitician.UiMediator.Loader;
import com.molitics.molitician.databinding.FragmentLeaderOverviewBinding;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.interfaces.TwitterDataListener;
import com.molitics.molitician.interfaces.TwitterTokenListener;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.hotTopics.hotTopicDetail.HotTopicDetailActivity;
import com.molitics.molitician.ui.dashboard.leader.leaderProfile.CandidateProfileModel;
import com.molitics.molitician.ui.dashboard.leader.leaderProfile.Event;
import com.molitics.molitician.ui.dashboard.leader.leaderProfile.TwitterDataResponse;
import com.molitics.molitician.ui.dashboard.leader.leaderProfile.TwitterTokenResponse;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.handler.NewCandidateProfileHandler;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.handler.NewCandidateProfilePresenter;
import com.molitics.molitician.ui.dashboard.login.LoginDialogFragment;
import com.molitics.molitician.ui.dashboard.voice.VoiceViewNavigation;
import com.molitics.molitician.ui.dashboard.voice.createVoice.CreateVoiceActivity;
import com.molitics.molitician.ui.dashboard.voice.ImageBigFragment;
import com.molitics.molitician.ui.dashboard.voice.VoiceCreatorProfile;
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
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.MoliticsAppPermission;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.ShareScreenShot;
import com.molitics.molitician.util.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import javax.inject.Inject;

import im.ene.toro.PlayerSelector;

import static com.molitics.molitician.MolticsApplication.getAndroid_id;
import static com.molitics.molitician.ui.dashboard.voice.VoiceFragment.VOICE_CREATE_MODEL;
import static com.molitics.molitician.ui.dashboard.voice.VoiceFragment.VOICE_MODEL_INTENT;
import static com.molitics.molitician.util.Constant.RequestTag.LOGINDIALOG;
import static com.molitics.molitician.util.MoliticsAppPermission.REQUEST_STORAGE;
import static com.molitics.molitician.util.MoliticsAppPermission.checkWritePermission;
import static com.molitics.molitician.util.MoliticsAppPermission.requestReadStoragePermission;
import static com.molitics.molitician.util.Util.showProgressAnimation;

/**
 * Created by rahul on 6/28/2017.
 */

public class LeaderOverviewFragment extends BaseFragment<FragmentLeaderOverviewBinding, VoiceViewModel> implements NewCandidateProfilePresenter.LeaderProfileView,
        TwitterTokenListener, TwitterDataListener, EventAdapter.LeaderEventListener, VoiceAllAdapter.VoiceInterFace, ViewRefreshListener,
        NewCandidateProfilePresenter.VoiceUI, NewCandidateProfilePresenter.VoiceActionUi, BranchShareClass.DeepLinkCallBack, VoiceViewNavigation {

    String TAG = LeaderOverviewFragment.class.getSimpleName();

    @Inject
    VoiceViewModel voiceViewModel;

    private List<VoiceAllModel> voiceAllModels;
    private EventAdapter eventAdapter;
    private NewCandidateProfileHandler profileHandler;
    private int issue_action_position = 0;
    private int issue_action = 0;
    private int follow_position = 0;
    private int candidate_id = 0;
    private int image_position = 0;

    private int share_issue_id;
    private String adapter_from = "";


    private int COMMENT_RESPONSE = 300;
    private String leader_name;
    private LinearLayoutManager linearLayoutManager;
    private FragmentLeaderOverviewBinding fragmentLeaderOverBinding;

    private final Handler handler = new Handler();
    private PlayerSelector selector = PlayerSelector.DEFAULT;


    public static Fragment getInstance(Integer candidate_id, String name) {
        Fragment mFragment = new LeaderOverviewFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt(Constant.IntentKey.LEADER_PROFILE_ID, candidate_id);
        mBundle.putString(Constant.IntentKey.LEADER_PROFILE_NAME, name);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_leader_overview;
    }

    @Override
    public VoiceViewModel getViewModel() {
        return voiceViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileHandler = new NewCandidateProfileHandler(this, this, this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        voiceViewModel.setNavigator(this);
        voiceViewModel.setContext(getContext());
        fragmentLeaderOverBinding = getViewDataBinding();

        Bundle mBundle = getArguments();
        assert mBundle != null;
        candidate_id = mBundle.getInt(Constant.IntentKey.LEADER_PROFILE_ID);
        leader_name = mBundle.getString(Constant.IntentKey.LEADER_PROFILE_NAME);

        bindUI();

        Loader.showMyDialog(getActivity());
        profileHandler.getProfile(candidate_id);
        setVideoRef();
    }

    private void bindUI() {
        eventAdapter = new EventAdapter(getContext(), this, this);
        linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentLeaderOverBinding.eventRecyclerView.setLayoutManager(linearLayoutManager);
        fragmentLeaderOverBinding.eventRecyclerView.setAdapter(eventAdapter);
        ///linearLayoutManager.setAutoMeasureEnabled(true);
    }

    @Override
    public void onProfileResponse(APIResponse apiResponse) {
        Loader.dismissMyDialog(getActivity());
        CandidateProfileModel candidateProfileModel = apiResponse.getData().getCandidateProfileModel();
        handleUi(candidateProfileModel);
    }

    @Override
    public void onProfileApiException(ApiException apiException) {
        Loader.dismissMyDialog(getActivity());
        Util.validateError(getContext(), apiException, fragmentLeaderOverBinding.rlNoData, this, eventAdapter.getItemCount());
    }

    private void handleUi(CandidateProfileModel candidateProfileModel) {
        // String status_url = candidateProfileModel.getStatus_url();
        // String status_txt = candidateProfileModel.getCandidate_status();
        fragmentLeaderOverBinding.llMain.setVisibility(View.VISIBLE);


        voiceAllModels = candidateProfileModel.getVoiceAllModel();
        if (voiceAllModels.size() != 0 || !candidateProfileModel.getCandidate_status().isEmpty()) {
            eventAdapter.clearAdapter();
            eventAdapter.setVoice(voiceAllModels);
        } else {
            fragmentLeaderOverBinding.eventRecyclerView.setVisibility(View.GONE);
        }
        ///// twitterHandler();

        if (eventAdapter.getItemCount() == 0)
            fragmentLeaderOverBinding.rlNoData.setVisibility(View.VISIBLE);
    }

    @Override
    public void onToken(TwitterTokenResponse tokenResponse) {
    }


    @Override
    public void onTwitterData(TwitterDataResponse dataResponse) {
    }


    @Override
    public void onMoreLeaderFeedsResponse(APIResponse apiResponse) {
        voiceAllModels = apiResponse.getData().getVoiceAllModels();
        eventAdapter.setVoice(apiResponse.getData().getVoiceAllModels());
        fragmentLeaderOverBinding.includeLoading.loadingGrid.setVisibility(View.GONE);
    }


    @Override
    public void onMoreLeaderFeedsError(ApiException apiException) {
        eventAdapter.isReadMoreVisible = false;
        fragmentLeaderOverBinding.includeLoading.loadingGrid.setVisibility(View.GONE);
    }


    @Override
    public void onTwitterNullResponse() {
        if (eventAdapter.getItemCount() == 0)
            fragmentLeaderOverBinding.rlNoData.setVisibility(View.VISIBLE);
    }

    public void onToolBarTouch() {
    }

    public void onToolBarTouchDetach() {
    }

    @Override
    public void onRemindMeClick(String time, String name, String address) {

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), getString(R.string.following_event_has_been), Toast.LENGTH_SHORT).show();

            // event insert
            ContentResolver cr = Objects.requireNonNull(getActivity()).getContentResolver();
            ContentValues values = new ContentValues();
            long calID = 3;
            String give_time = "1";
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm a");
            try {
                Date mDate = sdf.parse(give_time);
                long timeInMilliseconds = mDate.getTime();
                values.put(CalendarContract.Events.DTSTART, timeInMilliseconds);  // event starts at  from
                values.put(CalendarContract.Events.DTEND, timeInMilliseconds + 20 * 60 * 1000);  // ends 20 minutes from now
            } catch (ParseException e) {
                e.printStackTrace();
            }
            values.put(CalendarContract.Events.TITLE, name);
            values.put(CalendarContract.Events.DESCRIPTION, address);
            values.put(CalendarContract.Events.CALENDAR_ID, calID);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

            Uri event = cr.insert(CalendarContract.Events.CONTENT_URI, values);

            values = new ContentValues();
            if (event != null) {
                values.put("event_id", Long.parseLong(event.getLastPathSegment()));
            }
            values.put("method", 1);
            values.put("minutes", 10);
            cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
        } else {
            MoliticsAppPermission.calenderPermission(getActivity());
        }
    }

    @Override
    public void shareEvent(String title, String address, String date) {
        setGAEvent(Constant.GoogleAnalyticsKey.LEADER_EVENT_SHARE + "  " + leader_name);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        shareIntent.putExtra(Intent.EXTRA_TITLE, address);
        shareIntent.putExtra(Intent.EXTRA_TEXT, title + "\n" + address + "\n" + date + "\n" + leader_name);
        startActivity(Intent.createChooser(shareIntent, "Share link using"));
    }

    @Override
    public void onMoreFeedClick(int page_count) {
        fragmentLeaderOverBinding.includeLoading.loadingGrid.setVisibility(View.VISIBLE);
        profileHandler.getMoreLeaderFeeds(candidate_id, eventAdapter.getItemCount(), getAndroid_id);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            int VOICE_MODEL = 400;
            if (requestCode == COMMENT_RESPONSE) {
                onCommentAddListener(
                        data.getIntExtra(Constant.IntentKey.COMMENT_POSITION, 0),
                        data.getIntExtra(Constant.IntentKey.MESSAGE_COUNT, 0));
            } else if (requestCode == VOICE_MODEL_INTENT) {
                VoiceAllModel voiceAllModel = data.getParcelableExtra(Constant.IntentKey.VOICE_MODEL);
                int mPostion = data.getIntExtra(Constant.IntentKey.POSITION, 0);
                eventAdapter.updateVoiceModel(mPostion, voiceAllModel);
            } else if (requestCode == VOICE_MODEL) {
                int position = data.getIntExtra(Constant.IntentKey.POSITION, 0);
                VoiceAllModel voiceAllModel = data.getParcelableExtra(Constant.IntentKey.VOICE_MODEL);
                eventAdapter.updateVoiceModel(position, voiceAllModel);
            } else if (requestCode == VOICE_CREATE_MODEL) {
                VoiceAllModel voiceCreate = data.getParcelableExtra(Constant.IntentKey.VOICE_MODEL);
                int position = data.getIntExtra(Constant.IntentKey.POSITION, 0);
                if (data.hasExtra(Constant.IntentKey.EDIT)) {
                    eventAdapter.updateVoiceModel(position, voiceCreate);
                } else
                    eventAdapter.addVoiceModel(Constant.ZERO, voiceCreate);
                if (!data.getBooleanExtra(Constant.IntentKey.IS_VOICE_POSTED, false))
                    voiceViewModel.setFeedData(voiceCreate);
                linearLayoutManager.scrollToPositionWithOffset(0, 0);

            }
        }
    }

    @Override
    public void onLikeDislikeResponse(APIResponse apiResponse) {

        eventAdapter.updateLikeDislike(issue_action_position,
                apiResponse.getData().getLike_count(), apiResponse.getData().getDislike_count(),
                issue_action, image_position);
        getCurrentItemPosition(issue_action_position, apiResponse.getData().getLike_count(), apiResponse.getData().getDislike_count());
    }

    private void getCurrentItemPosition(int position, int like_count, int dislike_count) {
        VoiceAllViewHolder voiceAllViewHolder;

        if (fragmentLeaderOverBinding.eventRecyclerView.findViewHolderForAdapterPosition(position) instanceof VoiceAllViewHolder) {
            voiceAllViewHolder = (VoiceAllViewHolder) fragmentLeaderOverBinding.eventRecyclerView.findViewHolderForAdapterPosition(position);

            VoiceAllViewHolder.setUpvoteText(getContext(), voiceAllViewHolder.like_count_view, like_count);
            VoiceAllViewHolder.setDownvoteText(getContext(), voiceAllViewHolder.dislike_count_view, dislike_count);

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
        eventAdapter.onFollowResponse(follow_position, apiResponse.getData().getCount(), image_position);
        onFollowResponseUpdateView(follow_position, apiResponse.getData().getCount());
    }

    private void onFollowResponseUpdateView(int position, int follow_count) {
        VoiceAllViewHolder voiceAllViewHolder;
        if (fragmentLeaderOverBinding.eventRecyclerView.findViewHolderForAdapterPosition(position) instanceof VoiceAllViewHolder) {
            voiceAllViewHolder = (VoiceAllViewHolder)
                    fragmentLeaderOverBinding.eventRecyclerView.findViewHolderForAdapterPosition(position);
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
        eventAdapter.onUnFollowResponse(follow_position, apiResponse.getData().getCount(), image_position);
        onUnFollowResponseUpdateView(follow_position, apiResponse.getData().getCount());
    }

    private void onUnFollowResponseUpdateView(int position, int follow_count) {
        VoiceAllViewHolder voiceAllViewHolder;

        if (fragmentLeaderOverBinding.eventRecyclerView.findViewHolderForAdapterPosition(position) instanceof VoiceAllViewHolder) {
            voiceAllViewHolder = (VoiceAllViewHolder)
                    fragmentLeaderOverBinding.eventRecyclerView.findViewHolderForAdapterPosition(position);
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
            eventAdapter.deleteVoice(issue_action_position);
        } else {
            Util.validateError(getContext(), apiException, null, null, null);
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
        } else profileHandler.likeDislike(share_issue_id, issue_action);
    }


    @Override
    public void onDotClick(int position, int issue_id, int action_type, VoiceAllModel voiceAllModel) {
        if (action_type == Constant.Action.EDIT) {
            Intent mIntent = new Intent(getContext(), CreateVoiceActivity.class);

            if (voiceAllModel.getTag() != null) {
                mIntent.putExtra(Constant.IntentKey.Hot_TOPIC_TEXT, voiceAllModel.getTagName());

                mIntent.putExtra(Constant.IntentKey.HOT_TOPIC_ID, voiceAllModel.getTag());
            }
            mIntent.putExtra(Constant.IntentKey.POSITION, position);
            mIntent.putExtra(Constant.IntentKey.ISSUE_MODEL, voiceAllModel);
            startActivityForResult(mIntent, VOICE_CREATE_MODEL);
        } else if (action_type == Constant.Action.DELETE) {
            issue_action_position = position;
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
            builder.setMessage(getString(R.string.want_to_delete_issue))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes), (dialog, id) -> profileHandler.deleteIssue(issue_id))
                    .setNegativeButton(getString(R.string.no), (dialog, id) -> {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    });
            android.app.AlertDialog alert = builder.create();
            alert.setTitle(getString(R.string.issue_tag));
            alert.show();
        } else if (action_type == Constant.Action.SHARE) {
            onShareClick(issue_id, voiceAllModel.getUserName(), voiceAllModel.getContent());
        }
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


    private void showLoginDialog() {
        DialogFragment dialogFragment = LoginDialogFragment.getInstance();
        dialogFragment.setTargetFragment(this, LOGINDIALOG);
        dialogFragment.show(getFragmentManager(), getString(R.string.dialog_fragment));
    }

    @Override
    public void onShareClick(int issue_id, String userName, String voice_title) {

        if (checkWritePermission()) {
            BranchShareClass.generateShareLink(getActivity(), this, userName,
                    "", Constant.ShareCampaign.VOICE, Constant.ShareLinkTag.VOICE, String.valueOf(issue_id), Constant.ShareLinkTag.VOICE);
        } else {
            requestReadStoragePermission(getContext());
        }
    }


    @Override
    public void onUnFollowClick(String from, int actual_position, int position, int issue_id, int image_position) {
        this.image_position = image_position;
        follow_position = position;
        profileHandler.onUnFollow(issue_id);
    }

    @Override
    public void onTagLeaderClick(int leader_id) {
        if (candidate_id != leader_id) {
            Intent mIntent = new Intent(getContext(), NewLeaderProfileActivity.class);
            mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, leader_id);
            mIntent.putExtra(Constant.IntentKey.FROM, Constant.IntentKey.NOTIFICATION);
            startActivity(mIntent);
        }
    }

    @Override
    public void onImageClick(VoiceAllViewHolder voiceAllViewHolder, int position, int issue_id, List<String> imageList) {
        ArrayList<String> strings = new ArrayList<>(imageList);
        //noinspection ConstantConditions
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ImageBigFragment.getInstance(position, issue_id, strings, null).show(fm, getString(R.string.dialog_fragment));
    }

    @Override
    public void onCreatorImageClick(int position, int user_id) {
        Intent mIntent = new Intent(getContext(), VoiceCreatorProfile.class);
        mIntent.putExtra(Constant.IntentKey.USER_ID, user_id);
        startActivity(mIntent);
    }


    @Override
    public void onHashTagClick(int tag_id, String tag_name) {
        Intent mIntent = new Intent(getContext(), HotTopicDetailActivity.class);
        mIntent.putExtra(Constant.IntentKey.HOT_TOPIC_ID, tag_id);
        mIntent.putExtra(Constant.IntentKey.HOT_TOPIC_STRING, tag_name);
        startActivity(mIntent);
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

        ArrayList<VoiceAllModel> voiceVideoList = new ArrayList<>();

        for (Event mEvent : eventAdapter.getEventList()) {
            if (mEvent.getVoiceAllModel() != null)
                voiceVideoList.add(mEvent.getVoiceAllModel());
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
        profileHandler.onFollow(issue_id);
    }

    @Override
    public void onVoiceResponse(APIResponse apiResponse) {
    }

    @Override
    public void onVoiceApiError(ApiException apiexception) {

    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == REQUEST_STORAGE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //BranchShareClass.generateShareLink(getActivity(), this, "Voice", "voice_id", String.valueOf(issue_id));
                //Displaying a toast
                Toast.makeText(getContext(), getString(R.string.permission_granted_now), Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getContext(), getString(R.string.denied_permission), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onCommentAddListener(int position, int count) {
        eventAdapter.addCommentCount(position, count, image_position);
        //System.out.println("onCommentAddListener");
    }

    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        ShareScreenShot.takeScreenshot(getActivity(), getString(R.string.media_of_politics) + full_txt, url);
    }


    private void setVideoRef() {

        fragmentLeaderOverBinding.eventRecyclerView.setPlayerSelector(null);
        // Using TabLayout has a downside: once we click to a tab to change page, there will be no animation,
        // which will cause our setup doesn't work well. We need a delay to make things work.
        handler.postDelayed(() -> {
            if (fragmentLeaderOverBinding.eventRecyclerView != null)
                fragmentLeaderOverBinding.eventRecyclerView.setPlayerSelector(selector);
        }, 500);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            selector = PlayerSelector.DEFAULT;
        } else {
            selector = PlayerSelector.NONE;
        }

        // Using TabLayout has a downside: once we click to a tab to change page, there will be no animation,
        // which will cause our setup doesn't work well. We need a delay to make things work.
        handler.postDelayed(() -> {
            if (fragmentLeaderOverBinding.eventRecyclerView != null)
                fragmentLeaderOverBinding.eventRecyclerView.setPlayerSelector(selector);
        }, 200);
    }

    private void setGAEvent(String action) {
        GAEventTracking.googleEventTracker(getActivity(), Constant.GoogleAnalyticsKey.LEADER_PROFILE, action);
    }

    @Override
    public void onStop() {
        super.onStop();
        eventAdapter.releaseVideo();
    }

    @Override
    public void onVoiceCreateResponse(APIResponse apiResponse) {
        setDataVisibility();
        eventAdapter.updateVoiceModel(0, apiResponse.getData().getSingleVoiceModel());
    }

    @Override
    public void handleError(Throwable throwable) {
    }

    @Override
    public void onVideoProgressChanged(int id, int percentDone, int count) {
        setDataVisibility();
        if (fragmentLeaderOverBinding.eventRecyclerView.findViewHolderForAdapterPosition(0) instanceof VoiceAllViewHolder) {
            VoiceAllViewHolder voiceAllViewHolder = (VoiceAllViewHolder) fragmentLeaderOverBinding.eventRecyclerView.findViewHolderForAdapterPosition(0);
            if (voiceAllViewHolder != null && getContext() != null) {
                voiceAllViewHolder.overLay.setVisibility(View.VISIBLE);
                voiceAllViewHolder.video_uploading_bar.setVisibility(View.VISIBLE);
                voiceAllViewHolder.video_uploading_bar.setProgress(percentDone);
            }
        }
    }

    @Override
    public void videoHandleError(Exception ex) {
        if (isVisible()) {
            eventAdapter.removeVoiceOnTop(0);
            DialogClass.showAlert(getContext(), getString(R.string.check_internet_connection));
        }
    }

    @Override
    public void onRefereshClick() {
        profileHandler.getProfile(candidate_id);
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }

    private void setDataVisibility() {
        fragmentLeaderOverBinding.rlNoData.setVisibility(View.GONE);
        fragmentLeaderOverBinding.eventRecyclerView.setVisibility(View.VISIBLE);
    }
}
