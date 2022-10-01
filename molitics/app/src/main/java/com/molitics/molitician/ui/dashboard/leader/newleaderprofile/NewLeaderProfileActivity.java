package com.molitics.molitician.ui.dashboard.leader.newleaderprofile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.molitics.molitician.BaseActivity;
import com.molitics.molitician.GoogleAnalytics.GAEventTracking;
import com.molitics.molitician.R;
import com.molitics.molitician.databinding.ActivityNewLeaderProfileBinding;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.dashboard.dashboardView.DashBoardViewModel;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.leader.FollowHandler;
import com.molitics.molitician.ui.dashboard.leader.FollowPresenter;
import com.molitics.molitician.ui.dashboard.leader.leaderProfile.CandidateProfileModel;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.contactLeader.ContactLeaderActivity;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.handler.NewCandidateProfileHandler;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.handler.NewCandidateProfilePresenter;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.leaderNews.AboutLeaderFragment;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.leaderNews.NewLeaderNewsFragment;
import com.molitics.molitician.ui.dashboard.voice.createVoice.CreateVoiceActivity;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.ShareScreenShot;
import com.molitics.molitician.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


import static com.molitics.molitician.ui.dashboard.voice.VoiceFragment.VOICE_CREATE_MODEL;
import static com.molitics.molitician.util.Util.formatNumberToShort;
import static com.molitics.molitician.util.Util.showProgressAnimation;

/**
 * Created by rahul on 6/27/2017.
 */

public class NewLeaderProfileActivity extends BaseActivity<ActivityNewLeaderProfileBinding, DashBoardViewModel> implements NewCandidateProfilePresenter.LeaderProfileView,
        FollowPresenter.FollowView, AppBarLayout.OnOffsetChangedListener, BranchShareClass.DeepLinkCallBack {

    private FollowHandler followHandler;
    private NewCandidateProfileHandler profileHandler;
    private NewLeaderProfileAdapter profileAdapter;
    private ProgressBar vote_progress_bar;
    private int leader_id = 0;
    private int l_is_follow = 0;
    private int latRange = 0;
    private int position = 0;
    private int STORAGE_PERMISSION_CODE = 23;
    private boolean mIsTheTitleVisible = false;
    private String l_follow = "";
    private String l_name = "";
    private String l_image = "";
    private String l_position = "";
    private int l_panel = 0;
    private String l_location = "";
    private String l_party = "";
    private String l_party_logo = "";
    private int l_is_verify;
    private int l_upvote, l_downvote, l_isLike;
    private ActivityNewLeaderProfileBinding activityNewLeaderProfileBinding;
    private MenuItem itemMessages;

    public static ArrayList<Fragment> setLeaderProfileFragment(Integer candidate_id, String name) {
        ArrayList<Fragment> mFragment = new ArrayList<>();
        mFragment.add(LeaderOverviewFragment.getInstance(candidate_id, name));
        mFragment.add(NewLeaderNewsFragment.getInstance(candidate_id, name));
        mFragment.add(AboutLeaderFragment.getInstance(candidate_id, name));

        return mFragment;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_leader_profile;
    }

    @Override
    public DashBoardViewModel getViewModel() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNewLeaderProfileBinding = getViewDataBinding();
        profileHandler = new NewCandidateProfileHandler(this);

        setToolbar();

        Intent mIntent = getIntent();
        if (mIntent.hasExtra(Constant.IntentKey.FROM) && mIntent.getStringExtra(Constant.IntentKey.FROM).equals(Constant.IntentKey.NOTIFICATION)) {
            leader_id = mIntent.getIntExtra(Constant.IntentKey.LEADER_PROFILE_ID, 0);
            handleNotificationUi(leader_id);
            setGAEvent(Constant.GoogleAnalyticsKey.PROFILE_OPEN_ID + "  " + leader_id);
        } else {
            setIntentKey(mIntent);
        }
        handleNotificationUi(leader_id);
        followHandler = new FollowHandler(this);

        activityNewLeaderProfileBinding.smoothAppBarLayout.addOnOffsetChangedListener(this);

        activityNewLeaderProfileBinding.includeFloating.hotTopicFloatBtn.show();
        activityNewLeaderProfileBinding.includeFloating.hotTopicFloatBtn.setOnClickListener(v -> onIssueCreateClick());
        vote_progress_bar = activityNewLeaderProfileBinding.voteProgressBar;
        viewPagerListener();
        setAdapter();
    }


    private void setToolbar() {
        activityNewLeaderProfileBinding.toolbar.setNavigationOnClickListener(v -> goBack());
        setSupportActionBar(activityNewLeaderProfileBinding.toolbar);
        showHeader(activityNewLeaderProfileBinding.toolbar, true, "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        itemMessages = menu.findItem(R.id.message_menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share_menu) {
            BranchShareClass.generateShareLink(this, this, l_name,
                    "", "Leader", Constant.ShareLinkTag.LEADER, String.valueOf(leader_id), "leader");

            setGAEvent(Constant.GoogleAnalyticsKey.LEADER_SHARE + "  " + leader_id);
            return true;
        } else if (item.getItemId() == R.id.message_menu) {
            Intent mIntent = new Intent(this, ContactLeaderActivity.class);
            mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, leader_id);
            mIntent.putExtra(Constant.IntentKey.LEADER_NAME, l_name);
            mIntent.putExtra(Constant.IntentKey.LEADER_POSITION, l_position);
            mIntent.putExtra(Constant.IntentKey.LEADER_IMAGE, l_image);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goBack();
    }

    private void setResponseBack() {
        Intent mIntent = new Intent();
        mIntent.putExtra(Constant.IntentKey.ACTION, l_isLike);
        mIntent.putExtra(Constant.IntentKey.POSITION, position);
        mIntent.putExtra(Constant.IntentKey.UPVOTE, l_upvote);
        mIntent.putExtra(Constant.IntentKey.DOWNVOTE, l_downvote);
        mIntent.putExtra(Constant.IntentKey.LEADER_IS_FOLLOW, l_is_follow);
        mIntent.putExtra(Constant.IntentKey.LEADER_FOLLOW, l_follow);

        setResult(RESULT_OK, mIntent);
    }


    public void onIssueCreateClick() {

        if (!TextUtils.isEmpty(l_name)) {
            Intent mIntent = new Intent(this, CreateVoiceActivity.class);
            VoiceAllModel voiceAllModel = new VoiceAllModel();
            List<AllLeaderModel> candidateLeaderModels = new ArrayList<>();
            AllLeaderModel candidateLeaderModel = new AllLeaderModel();

            candidateLeaderModel.setId(leader_id);
            candidateLeaderModel.setName(l_name);
            candidateLeaderModel.setProfileImage(l_image);

            candidateLeaderModels.add(candidateLeaderModel);
            voiceAllModel.setCandidateLeader((ArrayList<AllLeaderModel>) candidateLeaderModels);
            mIntent.putExtra(Constant.IntentKey.ISSUE_MODEL, voiceAllModel);
            startActivityForResult(mIntent, VOICE_CREATE_MODEL);
        }
    }

    @Override
    public void onMoreLeaderFeedsResponse(APIResponse apiResponse) {
        //extra
    }

    @Override
    public void onMoreLeaderFeedsError(ApiException apiException) {
        //extra
    }

    public void onImageClick(View view) {
        onKnowMoreClick();
    }

    public void onKnowMoreClick() {
        Intent mIntent = new Intent(this, KnowMoreLeaderActivity.class);
        mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, leader_id);
        startActivity(mIntent);
        setGAEvent(Constant.GoogleAnalyticsKey.LEADER_KNOW_MORE + "  " + leader_id);
    }

    public void onFollow(View view) {
        MediaPlayer follow_media_player = MediaPlayer.create(this, R.raw.notification);
        if (l_is_follow == 0) {
            l_is_follow = 1;
            follow_media_player.start();

            followHandler.followCandidate(leader_id);
            setGAEvent(Constant.GoogleAnalyticsKey.FOLLOW_LEADER + "  " + leader_id);

        } else {
            l_is_follow = 0;
            followHandler.unFollowCandidate(leader_id);
            setGAEvent(Constant.GoogleAnalyticsKey.UNFOLLOW_LEADER + "  " + leader_id);
        }
        setFollowCount();
    }

    private void handleNotificationUi(int leader_id) {
        activityNewLeaderProfileBinding.progressBar.setVisibility(View.VISIBLE);
        profileHandler.getProfile(leader_id);
    }


    public void onUpvoteClick(View view) {
        if (l_isLike == 1) {
            l_isLike = 0;
            followHandler.onDeleteLeaderAction(leader_id);
        } else {
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.fb_like);
            mediaPlayer.start();
            l_isLike = 1;
            followHandler.onLeaderAction(leader_id, 1);
        }
    }

    public void onDownVoteClick(View view) {
        if (l_isLike == Constant.Action.DISLIKE) {
            l_isLike = 0;
            followHandler.onDeleteLeaderAction(leader_id);
        } else {
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.fb_like);
            mediaPlayer.start();
            l_isLike = Constant.Action.DISLIKE;
            followHandler.onLeaderAction(leader_id, Constant.Action.DISLIKE);
        }
    }


    private void setAdapter() {

        /////// set adapter
        profileAdapter = new NewLeaderProfileAdapter(getSupportFragmentManager(), setLeaderProfileFragment(leader_id, l_name), Util.setLeaderProfileTitle(this));
        activityNewLeaderProfileBinding.includeViewpager.viewPager.setAdapter(profileAdapter);
        activityNewLeaderProfileBinding.includeViewpager.tabLayout.setupWithViewPager(activityNewLeaderProfileBinding.includeViewpager.viewPager);
        activityNewLeaderProfileBinding.includeViewpager.viewPager.setOffscreenPageLimit(2);
        if (getIntent().hasExtra(Constant.IntentKey.FROM) && getIntent().getStringExtra(Constant.IntentKey.FROM).equals(Constant.IntentKey.LEADER_NEWS))
            activityNewLeaderProfileBinding.includeViewpager.viewPager.setCurrentItem(1);
        else if (getIntent().hasExtra(Constant.IntentKey.FROM) && getIntent().getStringExtra(Constant.IntentKey.FROM).equals(Constant.IntentKey.NOTIFICATION)) {
            activityNewLeaderProfileBinding.includeViewpager.viewPager.setCurrentItem(2);
        }
    }

    private void handleUi() {
        activityNewLeaderProfileBinding.parentView.setVisibility(View.VISIBLE);
        activityNewLeaderProfileBinding.collapsingToolbarLayout.setVisibility(View.VISIBLE);

        setFollowCount();
        setLeaderAction();
        if (!l_image.isEmpty())
            Picasso.with(this).load(l_image).placeholder(R.drawable.sample_image).into(activityNewLeaderProfileBinding.circleImage);
        else
            activityNewLeaderProfileBinding.circleImage.setImageResource(R.drawable.sample_image);

        activityNewLeaderProfileBinding.nameView.setText(l_name);
        if (!TextUtils.isEmpty(l_location))

            activityNewLeaderProfileBinding.leaderConstituencyView.setText(l_location);
        activityNewLeaderProfileBinding.posView.setText(l_position);
        activityNewLeaderProfileBinding.leaderPartyView.setText(l_party);
        if (!TextUtils.isEmpty(l_party_logo))
            Picasso.with(this).load(l_party_logo).into(activityNewLeaderProfileBinding.partyLogo);


        if (l_is_verify == 1) {
            activityNewLeaderProfileBinding.leaderVerifiedView.setVisibility(View.VISIBLE);
        } else
            activityNewLeaderProfileBinding.leaderVerifiedView.setVisibility(View.GONE);

        setVoteView(l_isLike);
        if (itemMessages != null) {
            if (l_panel == 0)
                itemMessages.setVisible(false);
            else
                itemMessages.setVisible(true);
        }
    }

    private void setIntentKey(Intent mIntent) {

        leader_id = mIntent.getIntExtra(Constant.IntentKey.LEADER_PROFILE_ID, 0);
        l_panel = mIntent.getIntExtra(Constant.IntentKey.LEADER_PANEL, 0);
        l_name = mIntent.getStringExtra(Constant.IntentKey.LEADER_NAME);
        l_position = mIntent.getStringExtra(Constant.IntentKey.LEADER_POSITION);
        l_location = mIntent.getStringExtra(Constant.IntentKey.LEADER_LOCATION);
        l_image = mIntent.getStringExtra(Constant.IntentKey.LEADER_IMAGE);
        l_party = mIntent.getStringExtra(Constant.IntentKey.LEADER_PARTY);
        l_party_logo = mIntent.getStringExtra(Constant.IntentKey.LEADER_PART_LOGO);
        l_follow = mIntent.getStringExtra(Constant.IntentKey.LEADER_FOLLOW);
        l_is_follow = mIntent.getIntExtra(Constant.IntentKey.LEADER_IS_FOLLOW, 0);
        l_isLike = mIntent.getIntExtra(Constant.IntentKey.ACTION, 0);
        l_is_verify = mIntent.getIntExtra(Constant.IntentKey.IS_VERIFY, 0);
        l_upvote = mIntent.getIntExtra(Constant.IntentKey.UPVOTE, 0);
        l_downvote = mIntent.getIntExtra(Constant.IntentKey.DOWNVOTE, 0);
        position = mIntent.getIntExtra(Constant.IntentKey.POSITION, 0);

        handleUi();
        setGAEvent(Constant.GoogleAnalyticsKey.PROFILE_OPEN + "  " + leader_id);
    }

    private void setHitKey(CandidateProfileModel candidateProfileModel) {
        l_panel = candidateProfileModel.getPanel_activated();
        l_name = candidateProfileModel.getName();
        l_position = candidateProfileModel.getPosition();
        l_location = candidateProfileModel.getLeaderLocation();
        l_image = candidateProfileModel.getProfileImage();
        l_party = candidateProfileModel.getParty();
        l_party_logo = candidateProfileModel.getPartyLogo();
        l_follow = candidateProfileModel.getFollowers();
        l_is_follow = candidateProfileModel.getIsFollowing();
        l_isLike = candidateProfileModel.getLike_action();
        l_upvote = candidateProfileModel.getLike_count();
        l_downvote = candidateProfileModel.getDislike_count();
        l_is_verify = candidateProfileModel.getIsVerify();
        handleUi();
        setVoteProgressBar(l_isLike, l_upvote, l_downvote);

    }


    @Override
    public void onFollowResponse(APIResponse apiResponse) {
        l_is_follow = 1;
        l_follow = apiResponse.getData().getKCount();
        setFollowCount();
        setResponseBack();
    }


    @Override
    public void onUnFollowRespnse(APIResponse apiResponse) {
        l_is_follow = 0;
        l_follow = apiResponse.getData().getKCount();
        setFollowCount();
        setResponseBack();

    }

    @Override
    public void onFollowApiException(ApiException apiException) {

    }

    @Override
    public void onFollowServerError(ServerException serverException) {

    }

    @Override
    public void onLeaderActionResponse(APIResponse apiResponse) {

        l_upvote = apiResponse.getData().getUpvoteCount();
        l_downvote = apiResponse.getData().getDownvoteCount();
        setLeaderAction();
        setVoteProgressBar(l_isLike, l_upvote, l_downvote);
    }

    @Override
    public void onLeaderActionApiError(ApiException apiException) {
    }

    @Override
    public void onDeleteLeaderActionApiError(ApiException apiException) {
    }

    @Override
    public void onDeleteLeaderAction(APIResponse apiResponse) {
        l_upvote = apiResponse.getData().getUpvoteCount();
        l_downvote = apiResponse.getData().getDownvoteCount();
        setLeaderAction();
        setVoteProgressBar(l_isLike, l_upvote, l_downvote);
        setVoteView(l_isLike);

    }


    private void setFollowCount() {
        if (l_is_follow == 0) {
            activityNewLeaderProfileBinding.followButton.setText(getString(R.string.follow_tag));
            activityNewLeaderProfileBinding.followButton.setBackground(getResources().getDrawable(R.drawable.leader_downvote_bg));
        } else {
            activityNewLeaderProfileBinding.followButton.setText(getString(R.string.following_tag));

            activityNewLeaderProfileBinding.followButton.setBackground(getResources().getDrawable(R.drawable.leader_unfollow_btn));
        }
    }

    private void setLeaderAction() {
        setResponseBack();
        setVoteView(l_isLike);

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        handleToolBar(verticalOffset, maxScroll);
    }


    private void handleToolBar(int percentage, int rang) {
        if (percentage == -rang) {
            if (!mIsTheTitleVisible) {
                mIsTheTitleVisible = true;
                callFragmentMethod(1);
            }
        } else if (percentage == 0) {
            if (mIsTheTitleVisible) {
                mIsTheTitleVisible = false;
                callFragmentMethod(2);
            }
        }
        latRange = percentage;
    }

    public void updateAboutDataFragment(CandidateProfileModel candidateProfileModel) {
        Fragment current_fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + 2);
        if (current_fragment != null) {
            if (current_fragment instanceof AboutLeaderFragment) {
                ((AboutLeaderFragment) current_fragment).setLeaderData(candidateProfileModel);
            }
        }
    }

    public void callFragmentMethod(int type) {
        Fragment current_fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + activityNewLeaderProfileBinding.includeViewpager.viewPager.getCurrentItem());
        // based on the current position you can then cast the page to the correct
        // class and call the method:
        if (current_fragment != null) {
            if (current_fragment instanceof LeaderOverviewFragment) {
                ////   ns_view.setNestedScrollingEnabled(false);

                if (type == 1) {
                    ((LeaderOverviewFragment) current_fragment).onToolBarTouch();
                } else if (type == 2) {

                    ((LeaderOverviewFragment) current_fragment).onToolBarTouchDetach();
                }
            } else if (current_fragment instanceof NewLeaderNewsFragment) {
                //  ns_view.setNestedScrollingEnabled(true);
                if (type == 1) {
                    //ns_view.setNestedScrollingEnabled(false);
                    ((NewLeaderNewsFragment) current_fragment).onToolBarTouch();
                } else if (type == 2) {
                    // ns_view.setNestedScrollingEnabled(true);
                    ((NewLeaderNewsFragment) current_fragment).onToolBarTouchDetach();
                }
            }
        }
    }

    CandidateProfileModel candidateProfileModel;

    @Override
    public void onProfileResponse(APIResponse apiResponse) {
        activityNewLeaderProfileBinding.progressBar.setVisibility(View.GONE);
        candidateProfileModel = apiResponse.getData().getCandidateProfileModel();
        updateAboutDataFragment(candidateProfileModel);
        setHitKey(candidateProfileModel);
    }

    @Override
    public void onProfileApiException(ApiException apiException) {
        activityNewLeaderProfileBinding.progressBar.setVisibility(View.GONE);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment current_fragment = profileAdapter.getRegisteredFragment(activityNewLeaderProfileBinding.includeViewpager.viewPager.getCurrentItem());
        //current_fragment.onActivityResult(requestCode,resultCode,data);
        if (current_fragment != null) {
            (current_fragment).onActivityResult(requestCode, resultCode, data);
        }
    }

    //Requesting permission
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
                BranchShareClass.generateShareLink(this, this, l_name, "",
                        "Leader", Constant.ShareLinkTag.LEADER, String.valueOf(leader_id), "leader");
                ///Displaying a toast
                ///Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
                setGAEvent(Constant.GoogleAnalyticsKey.LEADER_SHARE + "  " + leader_id);
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, getString(R.string.denied_permission), Toast.LENGTH_LONG).show();
            }
        }
    }

    //replace fragment for main pages
    public void replaceFragment(Fragment fragment, Bundle bundle, boolean addToBackStack, boolean anim) {
        if (bundle != null) fragment.setArguments(bundle);
        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.home_container, fragment, tag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragment.setRetainInstance(true);
        if (addToBackStack)
            ft.addToBackStack(tag);
        try {
            ft.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            ft.commitAllowingStateLoss();
        }
    }

    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        ShareScreenShot.takeScreenshot(this, getString(R.string.media_of_politics) + full_txt, url);
    }

    private void setGAEvent(String action) {
        GAEventTracking.googleEventTracker(this, Constant.GoogleAnalyticsKey.LEADER_PROFILE, action);
    }

    public void setVoteProgressBar(int action, int upvote_count, int downvote_count) {

        int totoalVoteCount = upvote_count + downvote_count;
        if (totoalVoteCount != 0) {
            ////// left to right or right to left progress bar with diff color
            if (action == Constant.Action.LIKE) {
                int finalPercent = (upvote_count * 100) / (totoalVoteCount);
                vote_progress_bar.setProgress(finalPercent);
                showProgressAnimation(vote_progress_bar, finalPercent);
                vote_progress_bar.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.vote_progress_bar_horizontal, null));
                vote_progress_bar.setRotation(0);
            } else {
                int finalPercent = (downvote_count * 100) / (totoalVoteCount);
                vote_progress_bar.setProgress(finalPercent);
                showProgressAnimation(vote_progress_bar, finalPercent);
                vote_progress_bar.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.downvote_progress_horizontal, null));
                vote_progress_bar.setRotation(180);
            }
        } else {
            vote_progress_bar.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.gray_progress_horizontal, null));
        }
    }

    public void setVoteView(int action) {

        activityNewLeaderProfileBinding.candidateLikeView.setText(String.format(getString(R.string.upvote_string), formatNumberToShort(l_upvote)));
        activityNewLeaderProfileBinding.candidateDislikeView.setText(String.format(getString(R.string.downvote_string), formatNumberToShort(l_downvote)));
        if (action == Constant.Action.LIKE) {
            activityNewLeaderProfileBinding.candidateLikeView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.upvote_active, 0, 0, 0);
            activityNewLeaderProfileBinding.candidateDislikeView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.downvote_deactive, 0, 0, 0);
            activityNewLeaderProfileBinding.candidateLikeView.setTypeface(ExtraUtils.getRobotoMedium(this));
            activityNewLeaderProfileBinding.candidateDislikeView.setTypeface(ExtraUtils.getRobotoRegular(this));
        } else if (action == Constant.Action.DISLIKE) {
            activityNewLeaderProfileBinding.candidateLikeView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.upvote_deactive, 0, 0, 0);
            activityNewLeaderProfileBinding.candidateDislikeView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.downvote_active, 0, 0, 0);
            activityNewLeaderProfileBinding.candidateLikeView.setTypeface(ExtraUtils.getRobotoRegular(this));
            activityNewLeaderProfileBinding.candidateDislikeView.setTypeface(ExtraUtils.getRobotoMedium(this));
        } else {
            activityNewLeaderProfileBinding.candidateLikeView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.upvote_deactive, 0, 0, 0);
            activityNewLeaderProfileBinding.candidateDislikeView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.downvote_deactive, 0, 0, 0);
            activityNewLeaderProfileBinding.candidateLikeView.setTypeface(ExtraUtils.getRobotoRegular(this));
            activityNewLeaderProfileBinding.candidateDislikeView.setTypeface(ExtraUtils.getRobotoRegular(this));
        }
    }

    private void viewPagerListener() {
        /// activityNewLeaderProfileBinding.includeFloating.hotTopicFloatBtn
        activityNewLeaderProfileBinding.includeViewpager.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                activityNewLeaderProfileBinding.includeFloating.hotTopicFloatBtn.show();
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }
}
