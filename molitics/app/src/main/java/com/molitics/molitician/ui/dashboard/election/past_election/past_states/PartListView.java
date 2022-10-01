package com.molitics.molitician.ui.dashboard.election.past_election.past_states;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.UiMediator.Loader;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.LeaderAdapterInterface;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.leader.FollowHandler;
import com.molitics.molitician.ui.dashboard.leader.FollowPresenter;
import com.molitics.molitician.ui.dashboard.leader.LeaderAdapter;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.NewLeaderProfileActivity;
import com.molitics.molitician.ui.dashboard.login.LoginDialogFragment;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.molitics.molitician.ui.dashboard.leader.LeaderView.DETAIL_PROFILE;
import static com.molitics.molitician.util.Constant.RequestTag.LOGINDIALOG;

/**
 * Created by rahul on 1/10/2017.
 */

public class PartListView extends BasicAcivity implements PartyListPresenter.PartListView,
        LeaderAdapterInterface, ViewRefreshListener, LeaderAdapter.OnLoadMoreResult, FollowPresenter.FollowView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.leaders_recycler_main)
    RecyclerView leaders_recycler_main;
    @BindView(R.id.main_progress_bar)
    ProgressBar main_progress_bar;
    @BindView(R.id.loading_grid)
    LinearLayout loading_grid;
    @BindView(R.id.RL_handler)
    RelativeLayout main_frame_layout;

    private ArrayList<AllLeaderModel> allLeaderModels = new ArrayList<>();
    private boolean loadMore = false;
    private boolean newsApiCall = true;
    private LeaderAdapter leaderAdapter;
    private PartyListHandler listHandler;
    private int page_no = 1;
    private int party_id = 0;
    private int election_id = 0;
    private int mla_id = 0;
    private String from = "";
    private int likeAction = 0;
    private int adapter_position = 0;
    private FollowHandler followHandler;
    private int temp_follow_position = 0;
    private int temp_unfollow_position = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listHandler = new PartyListHandler(this);
        setContentView(R.layout.fragment_leader_view);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.VISIBLE);
        followHandler = new FollowHandler(this);

        toolbar.setNavigationOnClickListener(v -> goBack());

        bindUI();
        handleView();

    }

    private void handleView() {
        Intent mIntent = getIntent();
        election_id = mIntent.getIntExtra(Constant.IntentKey.ELECTION_ID, 0);
        from = mIntent.getStringExtra(Constant.IntentKey.FROM);
        Loader.showMyDialog(this);
        switch (from) {
            case Constant.From.FROM_PROFILE:
                showHeader(true, getResources().getString(R.string.Leaders_Followed));
                listHandler.getLeaderFollowed(page_no);
                break;
            case Constant.From.FROM_UPCOMING_PARTY:
                showHeader(true, getResources().getString(R.string.candidates_header));
                mla_id = mIntent.getIntExtra(Constant.IntentKey.PAST_MLA, 0);
                listHandler.getUpcomingConstituency(election_id, mla_id, 0);
                break;
            case Constant.From.FROM_DECLARED_STATE:
                showHeader(true, getResources().getString(R.string.candidates_header));
                party_id = mIntent.getIntExtra(Constant.IntentKey.PARTY_ID, 0);
                listHandler.getPartyList(election_id, party_id, page_no);
                break;
        }
    }

    private void bindUI() {
        leaderAdapter = new LeaderAdapter(this, this, this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        leaders_recycler_main.setLayoutManager(mLayoutManager);
        leaders_recycler_main.setNestedScrollingEnabled(true);
        leaders_recycler_main.setAdapter(leaderAdapter);
    }


    @Override
    public void onFollowClick(int id, int position) {
        temp_follow_position = position;
        listHandler.followCandidate(id);

    }

    @Override
    public void onUnFollowClick(int id, int position) {
        temp_unfollow_position = position;
        listHandler.unFollowCandidate(id);
    }

    @Override
    public void onLeaderNewsClick(int position, AllLeaderModel allLeaderModel) {
        Intent mIntent = new Intent(this, NewLeaderProfileActivity.class);

        mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, Integer.valueOf(allLeaderModel.getId()));
        mIntent.putExtra(Constant.IntentKey.LEADER_NAME, allLeaderModel.getName());
        mIntent.putExtra(Constant.IntentKey.LEADER_IMAGE, allLeaderModel.getProfileImage());
        mIntent.putExtra(Constant.IntentKey.LEADER_POSITION, allLeaderModel.getPosition());
        mIntent.putExtra(Constant.IntentKey.LEADER_LOCATION, allLeaderModel.getLocation());
        mIntent.putExtra(Constant.IntentKey.LEADER_PARTY, allLeaderModel.getPartyCode());
        mIntent.putExtra(Constant.IntentKey.LEADER_PART_LOGO, allLeaderModel.getPartyLogo());
        mIntent.putExtra(Constant.IntentKey.LEADER_FOLLOW, allLeaderModel.getFollowers());
        mIntent.putExtra(Constant.IntentKey.LEADER_IS_FOLLOW, allLeaderModel.getIsFollowing());
        mIntent.putExtra(Constant.IntentKey.IS_VERIFY, allLeaderModel.getIsVerify());
        mIntent.putExtra(Constant.IntentKey.LEADER_PANEL, allLeaderModel.getPanel_activated());
        mIntent.putExtra(Constant.IntentKey.ACTION, allLeaderModel.getUser_vote_action());
        mIntent.putExtra(Constant.IntentKey.UPVOTE, allLeaderModel.getLike_count());
        mIntent.putExtra(Constant.IntentKey.DOWNVOTE, allLeaderModel.getDislike_count());
        mIntent.putExtra(Constant.IntentKey.POSITION, position);

        mIntent.putExtra(Constant.IntentKey.FROM, Constant.IntentKey.LEADER_NEWS);

        startActivityForResult(mIntent, DETAIL_PROFILE);
    }

    @Override
    public void onLeaderClick(int position, AllLeaderModel allLeaderModel) {
        Intent mIntent = new Intent(this, NewLeaderProfileActivity.class);
        mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, Integer.valueOf(allLeaderModel.getId()));
        mIntent.putExtra(Constant.IntentKey.LEADER_NAME, allLeaderModel.getName());
        mIntent.putExtra(Constant.IntentKey.LEADER_IMAGE, allLeaderModel.getProfileImage());
        mIntent.putExtra(Constant.IntentKey.LEADER_POSITION, allLeaderModel.getPosition());
        mIntent.putExtra(Constant.IntentKey.LEADER_LOCATION, allLeaderModel.getLocation());
        mIntent.putExtra(Constant.IntentKey.LEADER_PARTY, allLeaderModel.getPartyCode());
        mIntent.putExtra(Constant.IntentKey.LEADER_PART_LOGO, allLeaderModel.getPartyLogo());
        mIntent.putExtra(Constant.IntentKey.LEADER_FOLLOW, allLeaderModel.getFollowers());
        mIntent.putExtra(Constant.IntentKey.LEADER_IS_FOLLOW, allLeaderModel.getIsFollowing());
        mIntent.putExtra(Constant.IntentKey.IS_VERIFY, allLeaderModel.getIsVerify());
        mIntent.putExtra(Constant.IntentKey.LEADER_PANEL, allLeaderModel.getPanel_activated());
        mIntent.putExtra(Constant.IntentKey.ACTION, allLeaderModel.getUser_vote_action());
        mIntent.putExtra(Constant.IntentKey.UPVOTE, allLeaderModel.getLike_count());
        mIntent.putExtra(Constant.IntentKey.DOWNVOTE, allLeaderModel.getDislike_count());
        mIntent.putExtra(Constant.IntentKey.POSITION, position);
        startActivityForResult(mIntent, DETAIL_PROFILE);
    }

    @Override
    public void onLikeDisClick(int position, int candidate_id, int action) {
        adapter_position = position;
        likeAction = action;

        if (PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY).isEmpty()) {
            showLoginDialog();
        } else followHandler.onLeaderAction(candidate_id, action);
    }

    private void showLoginDialog() {
        DialogFragment dialogFragment = LoginDialogFragment.getInstance();
        /// dialogFragment.setTargetFragment(this, LOGINDIALOG);
        dialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_fragment));
    }


    @Override
    public void onDeleteClick(int position, int candidate_id) {
        adapter_position = position;

        followHandler.onDeleteLeaderAction(candidate_id);
    }

    @Override
    public void onFollowResponse(APIResponse apiResponse) {
        if (apiResponse.getStatus() == 2000) {
            leaderAdapter.followDone(temp_follow_position, apiResponse.getData().getKCount());
        } else {
            leaderAdapter.onUnSuccessFollow(temp_follow_position);
        }
    }

    @Override
    public void onUnFollowRespnse(APIResponse apiResponse) {

    }

    @Override
    public void onFollowApiException(ApiException apiException) {

    }

    @Override
    public void onFollowServerError(ServerException serverException) {

    }

    @Override
    public void onLeaderActionResponse(APIResponse apiResponse) {
        if (likeAction == 1) {
            leaderAdapter.likeDone(adapter_position, apiResponse.getData().getUpvoteCount(), apiResponse.getData().getDownvoteCount());
        } else if (likeAction == 2) {
            leaderAdapter.disLikeDone(adapter_position, apiResponse.getData().getUpvoteCount(), apiResponse.getData().getDownvoteCount());
        }

    }

    @Override
    public void onLeaderActionApiError(ApiException apiException) {

    }

    @Override
    public void onDeleteLeaderActionApiError(ApiException apiException) {

    }

    @Override
    public void onDeleteLeaderAction(APIResponse apiResponse) {
        leaderAdapter.deleteDone(adapter_position, apiResponse.getData().getUpvoteCount(), apiResponse.getData().getDownvoteCount());
    }

    @Override
    public void onUnFollowresponse(APIResponse apiResponse) {
        if (apiResponse.getStatus() == 2000) {
            leaderAdapter.unFollowDone(temp_unfollow_position, apiResponse.getData().getKCount());
        } else {
            leaderAdapter.onUnSuccessUnFollow(temp_unfollow_position);
        }
    }

    @Override
    public void onPartyListResponse(APIResponse apiResponse) {
        Loader.dismissMyDialog(this);
        if (main_progress_bar != null && main_progress_bar.getVisibility() == View.VISIBLE)
            main_progress_bar.setVisibility(View.GONE);
        loading_grid.setVisibility(View.GONE);
        if (page_no == 1) {
            leaderAdapter.clearLeaderList();
        }
        allLeaderModels = apiResponse.getData().getLeaderModels();
        leaderAdapter.addLeaderList(allLeaderModels);
        pageNoHandler(apiResponse.getData().getLeaderModels());
    }

    @Override
    public void onPartyListApiException(ApiException apiException) {
        Loader.dismissMyDialog(this);
        loading_grid.setVisibility(View.GONE);

        Util.validateError(this, apiException, main_frame_layout,
                this, leaderAdapter.getLeaderListSize());
    }

    @Override
    public void onUpComingConstituencyResponse(APIResponse apiResponse) {
        Loader.dismissMyDialog(this);
        if (main_progress_bar != null && main_progress_bar.getVisibility() == View.VISIBLE)
            main_progress_bar.setVisibility(View.GONE);
        loading_grid.setVisibility(View.GONE);
        if (page_no == 1) {
            leaderAdapter.clearLeaderList();
        }
        allLeaderModels = apiResponse.getData().getUpComingLeaderModel();
        leaderAdapter.addLeaderList(allLeaderModels);
        pageNoHandler(apiResponse.getData().getUpComingLeaderModel());
    }

    @Override
    public void onUpComingConstituencyApiException(ApiException apiException) {
        Loader.dismissMyDialog(this);

        Util.validateError(this, apiException, main_frame_layout,
                this, leaderAdapter.getLeaderListSize());
    }

    @Override
    public void onFollowedResponse(APIResponse apiResponse) {
        Loader.dismissMyDialog(this);
        if (main_progress_bar != null && main_progress_bar.getVisibility() == View.VISIBLE)
            main_progress_bar.setVisibility(View.GONE);
        loading_grid.setVisibility(View.GONE);
        if (page_no == 1) {
            leaderAdapter.clearLeaderList();
        }
        allLeaderModels = apiResponse.getData().getFollowed_leader();
        int total_count = apiResponse.getData().getCount();
        if (total_count == 1) {
            showHeader(true, total_count + getString(R.string.leader_followed));
        } else {
            showHeader(true, total_count + " " + getResources().getString(R.string.Leaders_Followed));

        }
        leaderAdapter.addLeaderList(allLeaderModels);
        pageNoHandler(apiResponse.getData().getFollowed_leader());
    }

    @Override
    public void onFollowedApiException(ApiException apiException) {
        Loader.dismissMyDialog(this);

        Util.validateError(this, apiException, main_frame_layout,
                this, leaderAdapter.getLeaderListSize());
    }

    @Override
    public void onPartyListServerException(ServerException serverException) {
        Loader.dismissMyDialog(this);
        DialogClass.showAlert(this, getString(R.string.something_went_wrong));
    }

    public void pageNoHandler(ArrayList<AllLeaderModel> data) {
        newsApiCall = true;
        if (data.size() > 9) {
            loadMore = true;
            page_no++;
        } else {
            loadMore = false;
        }
    }

    @Override
    public void onRefereshClick() {
        switch (from) {
            case Constant.From.FROM_PROFILE:
                listHandler.getLeaderFollowed(page_no);
                break;
            case Constant.From.FROM_UPCOMING_PARTY:

                listHandler.getUpcomingConstituency(election_id, mla_id, 0);
                break;
            case Constant.From.FROM_DECLARED_STATE:

                listHandler.getPartyList(election_id, party_id, page_no);
                break;
        }
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }

    @Override
    public void onLoadMore(int totalItem) {
        if (loadMore && newsApiCall) {
            if (loading_grid.getVisibility() == View.GONE) {
                loading_grid.setVisibility(View.VISIBLE);
            }
            newsApiCall = false;
            switch (from) {
                case Constant.From.FROM_PROFILE:
                    listHandler.getLeaderFollowed(page_no);
                    break;
                case Constant.From.FROM_UPCOMING_PARTY:
                    listHandler.getUpcomingConstituency(election_id, mla_id, 0);
                    break;
                case Constant.From.FROM_DECLARED_STATE:
                    listHandler.getPartyList(election_id, party_id, page_no);
                    break;
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == DETAIL_PROFILE && data != null) {
                int position = data.getIntExtra(Constant.IntentKey.POSITION, 0);
                int action = data.getIntExtra(Constant.IntentKey.ACTION, 0);
                int upVote = data.getIntExtra(Constant.IntentKey.UPVOTE, 0);
                int downVote = data.getIntExtra(Constant.IntentKey.DOWNVOTE, 0);
                int is_following = data.getIntExtra(Constant.IntentKey.LEADER_IS_FOLLOW, 0);
                int followers = data.getIntExtra(Constant.IntentKey.LEADER_FOLLOW, 0);
                if (leaderAdapter != null && leaderAdapter.getLeaderListSize() > 0) {
                    if (leaderAdapter.getLeaderModel(position) != null) {
                        AllLeaderModel leaderModel = leaderAdapter.getLeaderModel(position);
                        leaderModel.setUser_vote_action(action);
                        leaderModel.setFollowers(String.valueOf(followers));
                        leaderModel.setIsFollowing(is_following);
                        leaderModel.setLike_count(upVote);
                        leaderModel.setDislike_count(downVote);
                        leaderAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }
}
