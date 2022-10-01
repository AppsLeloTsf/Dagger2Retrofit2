package com.molitics.molitician.ui.dashboard.voice.feedAction;

import android.app.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created rahul om on 03/04/18.
 */

public class LikeDislikeParticipantDialog extends DialogFragment implements FeedActionPresenter.FeedActionUI,
        FeedActionParticipantAdapter.UserProfileListener, ViewRefreshListener {


    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.local_news_fragment)
    FrameLayout local_news_fragment;
    @BindView(R.id.main_progress_bar)
    ProgressBar main_progress_bar;


    private FeedActionPeopleHandler feedActionPeopleHandler;
    private FeedActionParticipantAdapter participantAdapter;

    private int total_count = 0;
    private int action_position = 0;
    private int mFeedId = 0;
    private int mAction = 0;

    public static DialogFragment getInstance(Context mContext, int feed_id, int action) {


        LikeDislikeParticipantDialog dislikeParticipantDialog = new LikeDislikeParticipantDialog();
        Bundle mBundle = new Bundle();
        mBundle.putInt(Constant.IntentKey.ISSUE_ID, feed_id);
        mBundle.putInt(Constant.IntentKey.ACTION, action);

        dislikeParticipantDialog.setArguments(mBundle);
        return dislikeParticipantDialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            d.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.recyclerview_layout, container, false);

        ButterKnife.bind(this, mView);
        bindView();
        Bundle mBundle = getArguments();
        mFeedId = mBundle.getInt(Constant.IntentKey.ISSUE_ID);
        mAction = mBundle.getInt(Constant.IntentKey.ACTION);

        getDialog().setTitle(Util.getAction(mAction));

        total_count = 0;
        main_progress_bar.setVisibility(View.VISIBLE);
        feedActionPeopleHandler = new FeedActionPeopleHandler(this);
        feedActionPeopleHandler.getFeedActionList(mFeedId, mAction, total_count);
        return mView;
    }

    private void bindView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        participantAdapter = new FeedActionParticipantAdapter(getActivity(), this);

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

        Util.validateError(getActivity(), apiException, local_news_fragment, this, participantAdapter.getItemCount());

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

    @Override
    public void onRefereshClick() {
        onNetworkAvailable();
    }

    @Override
    public void onNetworkAvailable() {
        feedActionPeopleHandler.getFeedActionList(mFeedId, mAction, total_count);

    }
}
