package com.molitics.molitician.ui.dashboard.userProfile;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.voice.feedAction.FeedActionParticipantAdapter;
import com.molitics.molitician.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 06/04/18.
 */

public class FollowUnfollowParticipantDialog extends Dialog implements
        FeedActionParticipantAdapter.UserProfileListener, UserProfilePresenter.FollowActionView {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.local_news_fragment)
    FrameLayout local_news_fragment;
    @BindView(R.id.main_progress_bar)
    ProgressBar main_progress_bar;

    FollowActionHandler feedActionPeopleHandler;
    FeedActionParticipantAdapter participantAdapter;
    static int mAction;
    static int mFeedId;
    static int current_user_id;

    int total_count = 0;
    int action_position = 0;


    public FollowUnfollowParticipantDialog(@NonNull Context context) {
        super(context);
    }

    public static Dialog getInstance(Context mContext, int user_id, int feed_id, int action) {


        FollowUnfollowParticipantDialog participantDialog = new FollowUnfollowParticipantDialog(mContext);

        mAction = action;
        mFeedId = feed_id;
        current_user_id = user_id;

        return participantDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.recyclerview_layout);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ButterKnife.bind(this);
        bindView();
        total_count = 0;
        main_progress_bar.setVisibility(View.VISIBLE);
        feedActionPeopleHandler = new FollowActionHandler(this);
        feedActionPeopleHandler.getFeedActionList(current_user_id, mFeedId, mAction, total_count);
    }

    private void bindView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        participantAdapter = new FeedActionParticipantAdapter(getContext(), this);

        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setAdapter(participantAdapter);
    }

    @Override
    public void onFeedActionResponse(APIResponse apiResponse) {
        main_progress_bar.setVisibility(View.GONE);
        participantAdapter.addFeesList(apiResponse.getData().getFeedActionParticipantModels());
    }

    @Override
    public void onFeedActionError(ApiException apiException) {
        main_progress_bar.setVisibility(View.GONE);
        Util.validateError(getContext(), apiException, local_news_fragment, null, participantAdapter.getItemCount());

    }

    @Override
    public void onUserFollowResponse(APIResponse apiResponse) {
        participantAdapter.followUser(true, apiResponse.getData().getCount(), action_position);

    }

    @Override
    public void onUserFollowError(ApiException apiException) {

    }

    @Override
    public void onUserUnFollowResponse(APIResponse apiResponse) {
        participantAdapter.followUser(false, apiResponse.getData().getCount(), action_position);

    }

    @Override
    public void onUserUnFollowError(ApiException apiException) {

    }

    @Override
    public void onUserFollow(int user_id, int position) {
        action_position = position;
        feedActionPeopleHandler.userFollow(user_id);
    }

    @Override
    public void onUserUnFollow(int user_id, int position) {
        action_position = position;
        feedActionPeopleHandler.userUnFollow(user_id);
    }

    @Override
    public void onProfileImageClick(String image) {

    }
}
