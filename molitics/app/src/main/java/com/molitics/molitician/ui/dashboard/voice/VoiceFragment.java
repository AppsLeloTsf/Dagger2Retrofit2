package com.molitics.molitician.ui.dashboard.voice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.molitics.molitician.BR;
import com.molitics.molitician.BaseFragment;
import com.molitics.molitician.GoogleAnalytics.GAEventTracking;
import com.molitics.molitician.R;
import com.molitics.molitician.database.Database;
import com.molitics.molitician.databinding.FragmentVoiceBinding;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.interfaces.InitializeServerRequest;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.Data;
import com.molitics.molitician.ui.dashboard.DashBoardActivity;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;
import com.molitics.molitician.ui.dashboard.constantData.RecyclerTouchWithType;
import com.molitics.molitician.ui.dashboard.hotTopics.hotTopicDetail.HotTopicDetailActivity;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.NewLeaderProfileActivity;
import com.molitics.molitician.ui.dashboard.login.LoginDialogFragment;
import com.molitics.molitician.ui.dashboard.login.SelectLocationDialog;
import com.molitics.molitician.ui.dashboard.voice.adapter.VoiceAllAdapter;
import com.molitics.molitician.ui.dashboard.voice.createVoice.CreateVoiceActivity;
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
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.ShareScreenShot;
import com.molitics.molitician.util.Util;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import im.ene.toro.PlayerSelector;
import im.ene.toro.ToroPlayer;
import im.ene.toro.media.PlaybackInfo;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.molitics.molitician.MolticsApplication.getAndroid_id;
import static com.molitics.molitician.util.Constant.ActivityRequestKey.USER_PROFILE;
import static com.molitics.molitician.util.Constant.RequestTag.LOGINDIALOG;
import static com.molitics.molitician.util.MoliticsAppPermission.REQUEST_STORAGE;
import static com.molitics.molitician.util.MoliticsAppPermission.checkWritePermission;
import static com.molitics.molitician.util.MoliticsAppPermission.requestReadStoragePermission;
import static com.molitics.molitician.util.Util.showProgressAnimation;

/**
 * Created by rahul on 10/11/17.
 */

public class VoiceFragment extends BaseFragment<FragmentVoiceBinding, VoiceViewModel> implements VoicePresenter.VoiceUI, VoiceAllAdapter.VoiceInterFace,
        ViewRefreshListener, VoicePresenter.VoiceActionUi, BranchShareClass.DeepLinkCallBack, ImageBigFragment.Callback, VoiceViewNavigation {
    String TAG = VoiceFragment.class.getSimpleName();


    @Inject
    VoiceViewModel voiceViewModel;

    private VoiceHandler voiceHandler;
    private VoiceAllAdapter voiceAllAdapter;

    private int issue_action_position = Constant.ZERO;
    private int issue_action = Constant.ZERO;
    private int follow_position = Constant.ZERO;
    private int isGetTrending = Constant.ZERO;
    public final static int COMMENT_RESPONSE = 300;
    public final static int VOICE_CREATE_MODEL = 401;
    public final static int VOICE_MODEL_INTENT = 400;
    private int image_position = 0, viewholderPosition = 0, page = 0;
    private boolean isStateChange = false;
    private LinearLayoutManager linearLayoutManager;
    private InitializeServerRequest mListener;
    private ImageView remove_feed_state;
    private String adapter_from = "";
    //for state wise voice
    private TextView state_text;
    private List<ConstantModel> state_list;
    private int selectedState = Constant.ZERO;
    private int currentState = Constant.ZERO;
    private int share_issue_id;
    private FragmentManager fm;
    private boolean isPannelRefreshed = false;
    private static VoiceFragment mNewsRef;
    private static int dataLoadingLimit = 10;
    private int item_count = Constant.ZERO;
    private boolean canFeedDelete = false;
    private boolean isLoadingData = false;

    private FragmentVoiceBinding fragmentVoiceBinding;

    public static Fragment getInstance(int display_type) {

        Fragment mFragment = new VoiceFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt(Constant.IntentKey.DISPLAY_TYPE, display_type);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_voice;
    }

    @Override
    public VoiceViewModel getViewModel() {
        return voiceViewModel;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InitializeServerRequest) {
            mListener = (InitializeServerRequest) context;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentVoiceBinding = getViewDataBinding();
        voiceViewModel.setNavigator(this);
        voiceViewModel.setContext(getContext());
        voiceHandler = new VoiceHandler(this, this);
        fm = getActivity().getSupportFragmentManager();
        voiceHandler.getUserImage(PrefUtil.getInt(Constant.PreferenceKey.USER_ID));
        getToolBar();
        bindUI();
        setSwipeContainer();
        item_count = Constant.ZERO;
        setVideoRef();
        refreshVoice();
        loadFeedFirstTime();
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onCreateVoiceClick(int openType) {
        Intent mIntent = new Intent(getContext(), CreateVoiceActivity.class);
        mIntent.putExtra(Constant.IntentKey.TYPE, openType);
        startActivityForResult(mIntent, VOICE_CREATE_MODEL);
    }

    private void loadFeedFirstTime() {
        final Handler handler = new Handler();
        int LOAD_TIME = 200;
        handler.postDelayed(() -> {
            //Do something after 100ms
            getRaiseYourVoiceFromDataBase(item_count, dataLoadingLimit);
            getTrendingLeaderFromDataBase(dataLoadingLimit);
        }, LOAD_TIME);
    }

    private void refreshVoice() {
        fragmentVoiceBinding.mainContainer.setVisibility(View.GONE);
        isPannelRefreshed = true;
        page = Constant.ZERO;
        isGetTrending = 1;
        fragmentVoiceBinding.progressBar.setVisibility(View.VISIBLE);
        //// get data from server
        getAllVoiceData(selectedState);
    }

    @Override
    public void onResume() {
        super.onResume();

        ///   voiceInterFace = this;
        //  clearFlagOnFirstTimeLoading();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        IntentFilter intentFilter = new IntentFilter(Constant.VIDEO_BROADCAST);
        localBroadcastManager.registerReceiver(import_contact_broadcast, intentFilter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_check, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void getToolBar() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        RelativeLayout center_toolbar_rl = toolbar.findViewById(R.id.center_toolbar_rl);
        if (center_toolbar_rl != null) {
            // if (center_toolbar_rl != null)
            center_toolbar_rl.removeAllViews();
            View child = getLayoutInflater().inflate(R.layout.voice_tool_bar_layout, null);
            center_toolbar_rl.addView(child);

            state_text = child.findViewById(R.id.state_name);
            state_text.setText(R.string.select_state);

            state_text.setOnClickListener(v -> {
                state_list = DashBoardActivity.state_list;
                if (state_list != null) {
                    SelectLocationDialog selectLocationDialog = SelectLocationDialog.getInstance();
                    selectLocationDialog.showDialog(getContext(), getString(R.string.select_state), state_list, new RecyclerTouchWithType() {
                        @Override
                        public void onVerticalRecycler(int position, int type) {
                            onVerticalRecyclerMy(position, type);
                        }

                        @Override
                        public void onCloseClick() {

                        }
                    });
                } else {
                    onStateNull();
                }
            });
            remove_feed_state = child.findViewById(R.id.remove_feed_state);

            remove_feed_state.setOnClickListener(v -> {
                ///// refreshing data
                isStateChange = false;
                voiceAllAdapter.clearVoiceList();
                voiceAllAdapter.clearTrendingList();
                selectedState = 0;
                state_text.setText(R.string.select_state);
                remove_feed_state.setVisibility(View.GONE);
                refreshVoice();
            });
        }
    }

    private void onVerticalRecyclerMy(int position, int type) {
        isStateChange = true;
        isGetTrending = 0;
        item_count = 0;
        page = Constant.ZERO;
        ///// refreshing data
        voiceAllAdapter.clearVoiceList();
        voiceAllAdapter.clearTrendingList();

        //  interactionListener.onViewPagerAdapterUpdate(state_list.get(position).getKey());
        SelectLocationDialog.dismissDialog();
        selectedState = state_list.get(position).getValue();
        String select_state_name = state_list.get(position).getKey();
        if (state_text != null) {
            state_text.setText(select_state_name);
        }

        remove_feed_state.setVisibility(View.VISIBLE);
        currentState = selectedState;
        refreshVoice();
        fragmentVoiceBinding.progressBar.setVisibility(View.VISIBLE);
        fragmentVoiceBinding.newsRecyclerView.setVisibility(View.GONE);

        setGAEvent(Constant.GoogleAnalyticsKey.VOICE_STATE_CHANGE + "  " + select_state_name);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onStateNull() {
        if (mListener != null) {
            Util.toastLong(getContext(), getString(R.string.state_list_fetching));
            mListener.createServerRequest(Constant.RequestTag.STATE_LIST);
        }
    }

    private void bindUI() {

        voiceAllAdapter = new VoiceAllAdapter(getContext(), this);

        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        fragmentVoiceBinding.newsRecyclerView.setLayoutManager(linearLayoutManager);
        fragmentVoiceBinding.newsRecyclerView.setAdapter(voiceAllAdapter);
        recyclerChangeListener();

    }

    private void setSwipeContainer() {
        fragmentVoiceBinding.swipeContainer.setOnRefreshListener(() -> {
            /// if state selected and user pull to refresh clear data and render new(remove repetition problem)
            if (currentState != 0) {
                isStateChange = true;
            }
            refreshVoice();
            fragmentVoiceBinding.progressBar.setVisibility(View.GONE);
        });
    }

    private void getAllVoiceData(int state_id) {
        voiceHandler.getAllVoice(isGetTrending, state_id, page, getAndroid_id);
    }

    private void updateUiWithData(List<VoiceAllModel> mData) {


        if (fragmentVoiceBinding.progressBar.getVisibility() == View.VISIBLE)
            fragmentVoiceBinding.progressBar.setVisibility(View.GONE);
        if (fragmentVoiceBinding.newsRecyclerView.getVisibility() == View.GONE) {
            fragmentVoiceBinding.newsRecyclerView.setVisibility(View.VISIBLE);
        }
        if (fragmentVoiceBinding.swipeContainer.isRefreshing())
            fragmentVoiceBinding.swipeContainer.setRefreshing(false);

        voiceAllAdapter.addVoiceList(mData);
    }

    public static Fragment getInstance() {

        if (mNewsRef == null) {
            mNewsRef = new VoiceFragment();
        }
        return mNewsRef;
    }

    @SuppressWarnings("unchecked")
    private void getRaiseYourVoiceFromDataBase(int dbFrom, int limit) {
        Observable.fromCallable(() -> {
            List<VoiceAllModel> mNewsList = new ArrayList<>();
            try {
                mNewsList = Database.readRaiseYourVoice(getContext(), dbFrom, limit);
            } catch (Exception e) {
            }
            return mNewsList;
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VoiceAllModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<VoiceAllModel> voiceAllModels) {
                        if (dbFrom == 0) {
                            voiceAllAdapter.clearVoiceList();
                        }
                        if (voiceAllModels != null && voiceAllModels.size() > 0) {
                            isLoadingData = false;
                            fragmentVoiceBinding.progressBar.setVisibility(View.GONE);
                            fragmentVoiceBinding.included.loadingGrid.setVisibility(View.GONE);
                            updateUiWithData(voiceAllModels);
                        } else {
                            onLoadMoreFromServer(); // it means data finished in db
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressWarnings("unchecked")
    private void getTrendingLeaderFromDataBase(int limit) {

        Observable.fromCallable(() -> {
            List<VoiceAllModel> mNewsList = Database.readLeaderTrendingVoice(getContext(), limit);
            return mNewsList;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VoiceAllModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<VoiceAllModel> voiceAllModels) {
                        manageTrendingFeeds(voiceAllModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void manageTrendingFeeds(List<VoiceAllModel> mData) {
        if (mData != null && mData.size() > 0) {
            voiceAllAdapter.clearTrendingList();
            voiceAllAdapter.addTrendingPositions(Collections.singletonList(new Integer(1)));
            voiceAllAdapter.addTrendingVoiceList(mData);
        }
    }


    @Override
    public void onVoiceResponse(APIResponse apiResponse) {

        fragmentVoiceBinding.RLHandler.removeAllViews();
        fragmentVoiceBinding.RLHandler.setVisibility(View.GONE);
        Data mData = apiResponse.getData();
        fragmentVoiceBinding.swipeContainer.setEnabled(true);
        fragmentVoiceBinding.swipeContainer.setRefreshing(false);

        fragmentVoiceBinding.included.loadingGrid.setVisibility(View.GONE);
        page++;

        if (mData.getTrendingFeedsPosition().get(0) != 0 || isStateChange) {
            ///refresh list delete data from database and refresh
            isStateChange = false;
            canFeedDelete = true;
            item_count = 0;
        }
        writeDataInDataBase(mData);
    }

    private void writeDataInDataBase(Data mData) {

        Database.writeRaiseYourVoice(mData.getVoiceAllModels(), getContext(), canFeedDelete, object -> {

            ((Activity) getContext()).runOnUiThread(() -> {
                canFeedDelete = false;
                ///  isLoadingData = true;
                getRaiseYourVoiceFromDataBase(item_count, dataLoadingLimit);// fetching data first time when app is install and no item is there
            });

            Database.writeTrendingData(mData.getTrendingFeedsModel(), getContext(), trending_object -> getTrendingLeaderFromDataBase(12));
        });
    }

    @Override
    public void onVoiceApiError(ApiException apiexception) {
        fragmentVoiceBinding.included.loadingGrid.setVisibility(View.GONE);

        fragmentVoiceBinding.swipeContainer.setRefreshing(false);
        fragmentVoiceBinding.progressBar.setVisibility(View.GONE);
        if (isVisible()) {

            if (apiexception.getCode() == 100003) {
                if (voiceAllAdapter.getVoiceListSize() == Constant.ZERO) {
                    Util.validateError(getContext(), apiexception, fragmentVoiceBinding.RLHandler, this, voiceAllAdapter.getVoiceListSize());
                } else {
                    Util.addMiniNetworkFailView(getContext(), fragmentVoiceBinding.RLHandler);
                }
            } else {
                if (page == 0)
                    Util.validateError(getContext(), apiexception, fragmentVoiceBinding.mainContainer, this, 0);
                else
                    Util.validateError(getContext(), apiexception, fragmentVoiceBinding.mainContainer, this, voiceAllAdapter.getVoiceListSize());
            }
        }
    }

    @Override
    public void onHashTagClick(int tag_id, String tag_name) {
        Intent mIntent = new Intent(getContext(), HotTopicDetailActivity.class);
        mIntent.putExtra(Constant.IntentKey.HOT_TOPIC_ID, tag_id);
        mIntent.putExtra(Constant.IntentKey.HOT_TOPIC_STRING, tag_name);
        startActivity(mIntent);
    }

    @Override
    public void onDetailClick(int position, int tempPosition, VoiceAllModel voiceAllModel) {

        Intent mIntent = new Intent(getContext(), VoiceDetailActivity.class);
        mIntent.putExtra(Constant.IntentKey.VOICE_MODEL, voiceAllModel);
        mIntent.putExtra(Constant.IntentKey.TEMP_POSITION, tempPosition);
        mIntent.putExtra(Constant.IntentKey.POSITION, position);
        /// startActivity(mIntent);
        startActivityForResult(mIntent, VoiceFragment.VOICE_MODEL_INTENT);
    }

    @Override
    public void onLikeDislikeClick(int issue_id, int action) {
        LikeDislikeParticipantDialog.getInstance(getActivity(), issue_id, action).show(fm, "LikeDialog");
    }


    @Override
    public void onVideoClick(VoiceAllViewHolder voiceAllViewHolder, String from, int clickPosition, int issueId) {
        //// ToDO : video click
        ArrayList<VoiceAllModel> voiceVideoList = new ArrayList<>();
        List<VoiceAllModel> selectedVideoList;
        int assumedClickPosition;
        if (from.equalsIgnoreCase(VoiceAllAdapter.TAG)) {
            assumedClickPosition = clickPosition;
            selectedVideoList = voiceAllAdapter.getVoiceList();
        } else {
            assumedClickPosition = 0;
            selectedVideoList = voiceAllAdapter.getTrendingList();
        }
        for (int data = assumedClickPosition; data < selectedVideoList.size(); data++) {
            VoiceAllModel voiceAllModel = selectedVideoList.get(data);
            if (voiceAllModel.getId() == issueId) {
                voiceVideoList.add(voiceAllModel);
                break;
            }
        }

        Intent mIntent = new Intent(getContext(), VoiceVideoActivity.class);
        mIntent.putParcelableArrayListExtra(Constant.IntentKey.VOICE_MODEL, voiceVideoList);
        mIntent.putExtra(Constant.IntentKey.ISSUE_ID, issueId);
        startActivity(mIntent);
    }

    @Override
    public void onUserFollowActionClick(int userId, int action) {
        voiceAllAdapter.updateUserFollow(userId, action);
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
        this.image_position = image_position;
        viewholderPosition = actual_position;
        adapter_from = from;
        follow_position = position;
        voiceHandler.onFollow(issue_id);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case COMMENT_RESPONSE:
                    onCommentAddListener(
                            data.getIntExtra(Constant.IntentKey.COMMENT_POSITION, 0),
                            data.getIntExtra(Constant.IntentKey.MESSAGE_COUNT, 0));
                    break;
                case VOICE_MODEL_INTENT:
                    if (data != null) {
                        VoiceAllModel voiceAllModel = data.getParcelableExtra(Constant.IntentKey.VOICE_MODEL);
                        int mVoicePosition = data.getIntExtra(Constant.IntentKey.POSITION, 0);
                        voiceAllAdapter.addVoiceOnPosition(mVoicePosition, voiceAllModel);
                    } else {
                        refreshVoice();
                    }
                    break;
                case VOICE_CREATE_MODEL:
                    isPannelRefreshed = false;
                    int mVoicePostion = data.getIntExtra(Constant.IntentKey.POSITION, 0);
                    VoiceAllModel voiceCreate = data.getParcelableExtra(Constant.IntentKey.VOICE_MODEL);
                    if (voiceCreate.getId() != 0) {
                        voiceAllAdapter.deleteVoice(mVoicePostion);
                    }
                    voiceAllAdapter.addVoiceOnTop(Constant.ZERO, voiceCreate);
                    if (!data.getBooleanExtra(Constant.IntentKey.IS_VOICE_POSTED, false))
                        voiceViewModel.setFeedData(voiceCreate);
                    break;
                case LOGINDIALOG:
                    perfromActionOnLogin();
                    break;
                case USER_PROFILE:
                    int userId = data.getIntExtra(Constant.IntentKey.USER_ID, 0);
                    int isUserFollowing = data.getIntExtra(Constant.IntentKey.USER_FOLLOW, 0);

                    voiceAllAdapter.updateUserFollow(userId, isUserFollowing);
                    break;
            }
        }
    }

    @Override
    public void onRefereshClick() {
        refreshVoice();
    }

    @Override
    public void onLikeDislikeResponse(APIResponse apiResponse) {

        if (adapter_from.equals("TrendingVoiceAdapter")) {
            voiceAllAdapter.updateTrendingLikeDislike(issue_action_position,
                    apiResponse.getData().getLike_count(), apiResponse.getData().getDislike_count(),
                    issue_action, image_position);
        } else {
            voiceAllAdapter.updateLikeDislike(issue_action_position,
                    apiResponse.getData().getLike_count(), apiResponse.getData().getDislike_count(),
                    issue_action, image_position);

            getCurrentItemPosition(viewholderPosition, apiResponse.getData().getLike_count(), apiResponse.getData().getDislike_count());
        }
    }

    ///// update adapter without notify -- like,dislike
    private void getCurrentItemPosition(int position, int like_count, int dislike_count) {
        VoiceAllViewHolder voiceAllViewHolder;
        if (fragmentVoiceBinding.newsRecyclerView.findViewHolderForAdapterPosition(position) instanceof VoiceAllViewHolder) {
            voiceAllViewHolder = (VoiceAllViewHolder) fragmentVoiceBinding.newsRecyclerView.findViewHolderForAdapterPosition(position);
            /// voiceAllViewHolder.like_count_view.setText(String.format(getString(R.string.prefix_upvote_string), like_count));
            if (voiceAllViewHolder != null && getContext() != null) {
                VoiceAllViewHolder.setUpvoteText(getContext(), voiceAllViewHolder.like_count_view, like_count);
                VoiceAllViewHolder.setDownvoteText(getContext(), voiceAllViewHolder.dislike_count_view, dislike_count);

                setVoteProgressBar(voiceAllViewHolder.vote_progress_bar, like_count, dislike_count);
            }
        }
    }

    public void setVoteProgressBar(ProgressBar vote_progress_bar, int like_count, int dislike_count) {

        int totoalVoteCount = like_count + dislike_count;
        if (totoalVoteCount != 0) {
            ////// left to right or right to left progress bar with diff color
            if (issue_action == Constant.Action.LIKE) {
                int finalPercent = (like_count * 100) / (totoalVoteCount);
                vote_progress_bar.setProgress(finalPercent);
                showProgressAnimation(vote_progress_bar, finalPercent);
                vote_progress_bar.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.vote_progress_bar_horizontal, null));
                vote_progress_bar.setRotation(0);
            } else {
                int finalPercent = (dislike_count * 100) / (totoalVoteCount);
                vote_progress_bar.setProgress(finalPercent);
                showProgressAnimation(vote_progress_bar, finalPercent);
                vote_progress_bar.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.downvote_progress_horizontal, null));
                vote_progress_bar.setRotation(180);
            }
        } else {
            vote_progress_bar.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.gray_progress_horizontal, null));
        }
    }


    @Override
    public void onLikeDislikeError(ApiException apiException) {
        Toast.makeText(getContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFollowResponse(APIResponse apiResponse) {
        if (adapter_from.equals("TrendingVoiceAdapter"))
            voiceAllAdapter.onTrendingFollowResponse(follow_position, apiResponse.getData().getCount(), image_position);
        else {
            voiceAllAdapter.onFollowResponse(follow_position, apiResponse.getData().getCount(), image_position);
            onFollowResponseUpdateView(viewholderPosition, apiResponse.getData().getCount());
        }
    }

    private void onFollowResponseUpdateView(int position, int follow_count) {
        VoiceAllViewHolder voiceAllViewHolder;
        if (fragmentVoiceBinding.newsRecyclerView.findViewHolderForAdapterPosition(position) instanceof VoiceAllViewHolder) {
            voiceAllViewHolder = (VoiceAllViewHolder)
                    fragmentVoiceBinding.newsRecyclerView.findViewHolderForAdapterPosition(position);
            voiceAllViewHolder.follow_txt.setTextColor(getResources().getColor(R.color.theme));
            voiceAllViewHolder.follow_txt.setText(String.format(getString(R.string.following_tag), follow_count));
            voiceAllViewHolder.follow_txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.flag_blue, 0, 0, 0);
        }
    }

    @Override
    public void onFollowError(ApiException apiException) {
        Toast.makeText(getContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUnFollowResponse(APIResponse apiResponse) {
        if (adapter_from.equals("TrendingVoiceAdapter"))
            voiceAllAdapter.onTrendingUnFollowResponse(follow_position, apiResponse.getData().getCount(), image_position);
        else
            voiceAllAdapter.onUnFollowResponse(follow_position, apiResponse.getData().getCount(), image_position);

        onUnFollowResponseUpdateView(viewholderPosition, apiResponse.getData().getCount());
    }

    private void onUnFollowResponseUpdateView(int position, int follow_count) {
        VoiceAllViewHolder voiceAllViewHolder;

        if (fragmentVoiceBinding.newsRecyclerView.findViewHolderForAdapterPosition(position) instanceof VoiceAllViewHolder) {
            voiceAllViewHolder = (VoiceAllViewHolder)
                    fragmentVoiceBinding.newsRecyclerView.findViewHolderForAdapterPosition(position);
            voiceAllViewHolder.follow_txt.setTextColor(getResources().getColor(R.color.follow_txte));
            voiceAllViewHolder.follow_txt.setText(String.format(getString(R.string.follow), follow_count));
            voiceAllViewHolder.follow_txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.flag, 0, 0, 0);
        }
    }

    @Override
    public void onUnFollowError(ApiException apiException) {
        Toast.makeText(getContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDeleteIssueResponse(APIResponse apiResponse) {
    }

    @Override
    public void onDeleteIssueError(ApiException apiException) {
        if (apiException.getCode() == 2005) {
            Util.toastLong(getContext(), getString(R.string.issue_deleted));
        }
    }

    @Override
    public void onUserImageResponse(APIResponse apiResponse) {
        String image_url = apiResponse.getData().getUserImageUrl();
        if (!TextUtils.isEmpty(image_url))
            saveImage(image_url);
    }

    private void saveImage(String image_url) {
        Picasso.with(getContext())
                .load(image_url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                       /* File file = Util.saveImageAsAuth(getActivity(), bitmap,
                                PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY));*/
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
    }

    @Override
    public void onUserImageError(ApiException apiException) {
        if (apiException.getCode() == 2004)
            Util.deleteImageAsAuth(PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY));
    }

    @Override
    public void onLoadMore() {
        fragmentVoiceBinding.included.loadingGrid.setVisibility(View.VISIBLE);
        int itemCount = voiceAllAdapter.getVoiceListSize() - 1;
        item_count = voiceAllAdapter.getVoiceList().get(itemCount).getPrimaryKey();
        getRaiseYourVoiceFromDataBase(item_count, dataLoadingLimit);
    }

    private void onLoadMoreFromServer() {
        ///  page = voiceAllAdapter.getItemCount() / 20;
        isGetTrending = 0;
        if (selectedState != 0) {
            voiceHandler.getAllVoice(isGetTrending, selectedState, page, getAndroid_id);
        } else {
            voiceHandler.getAllVoice(isGetTrending, 0, page, getAndroid_id);
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

        ////// setGAEvent(Constant.GoogleAnalyticsKey.COMMENT + "  " + issue_id);
    }

    private void performCommentOnLogin() {
        Intent mIntent = new Intent(getContext(), CommentFragment.class);
        mIntent.putExtra(Constant.IntentKey.ISSUE_ID, share_issue_id);
        mIntent.putExtra(Constant.IntentKey.ISSUE_POSITION, issue_action_position);
        startActivityForResult(mIntent, COMMENT_RESPONSE);
    }


    @Override
    public void onLikeDislikeClick(String from, int actual_position, int position, int issue_id, int action, int image_position) {


        this.image_position = image_position;
        issue_action_position = position;
        viewholderPosition = actual_position;
        this.share_issue_id = issue_id;
        issue_action = action;
        adapter_from = from;

        if (PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY).isEmpty()) {
            showLoginDialog();
        } else voiceHandler.likeDislike(share_issue_id, issue_action);
    }

    private void showLoginDialog() {
        DialogFragment dialogFragment = LoginDialogFragment.getInstance();
        dialogFragment.setTargetFragment(VoiceFragment.this, LOGINDIALOG);
        dialogFragment.show(fm, getString(R.string.dialog_fragment));
    }

    private void perfromActionOnLogin() {
        Util.showLog(TAG, adapter_from);
        if (adapter_from.equalsIgnoreCase(Constant.From.COMMNET_ACTION)) {
            performCommentOnLogin();
        } else
            voiceHandler.likeDislike(share_issue_id, issue_action);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(getString(R.string.want_to_delete_issue))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                        voiceAllAdapter.deleteVoice(position);
                        voiceHandler.deleteIssue(issue_id);
                    })
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onShareClick(int issue_id, String userName, String voice_title) {
        share_issue_id = issue_id;
        if (checkWritePermission()) {
            BranchShareClass.generateShareLink(getActivity(), this, userName,
                    "", Constant.ShareCampaign.VOICE, Constant.ShareLinkTag.VOICE, String.valueOf(issue_id), Constant.ShareLinkTag.VOICE);

        } else {
            requestReadStoragePermission(getContext());
        }
        //shareNews(issue_id, voice_title);
    }


    @Override
    public void onUnFollowClick(String from, int actual_position, int position, int issue_id, int image_position) {
        this.image_position = image_position;
        viewholderPosition = actual_position;
        adapter_from = from;
        follow_position = position;
        voiceHandler.onUnFollow(issue_id);
    }

    @Override
    public void onTagLeaderClick(int leader_id) {
        Intent mIntent = new Intent(getContext(), NewLeaderProfileActivity.class);
        mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, leader_id);
        mIntent.putExtra(Constant.IntentKey.FROM, Constant.IntentKey.NOTIFICATION);
        startActivity(mIntent);
        setGAEvent(Constant.GoogleAnalyticsKey.TAG_LEADER + "  " + leader_id);
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
        Intent mIntent = new Intent(getContext(), VoiceCreatorProfile.class);
        mIntent.putExtra(Constant.IntentKey.USER_ID, user_id);
        mIntent.putExtra(Constant.IntentKey.POSITION, position);
        startActivityForResult(mIntent, USER_PROFILE);
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }

    public void onCommentAddListener(int position, int count) {

        if (adapter_from.equals("TrendingVoiceAdapter"))
            voiceAllAdapter.addTrendingCommentCount(position, count);
        else
            voiceAllAdapter.addCommentCount(position, count);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == REQUEST_STORAGE) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                BranchShareClass.generateShareLink(getActivity(), this, "",
                        "", Constant.ShareCampaign.VOICE, Constant.ShareLinkTag.VOICE, String.valueOf(share_issue_id), "user-feed/detail");
            } else {
                Toast.makeText(getContext(), getString(R.string.denied_permission), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        ShareScreenShot.takeScreenshot(getActivity(), getString(R.string.media_of_politics) + full_txt, url);
    }

    private BroadcastReceiver import_contact_broadcast = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //put here whaterver you want your activity to do with the intent received
            List<String> video_urls = intent.getStringArrayListExtra(Constant.IntentKey.IMAGE_LIST);
            boolean isSuccess = intent.getBooleanExtra(Constant.IntentKey.RESPONSE, false);

            if (isSuccess && video_urls != null && !isPannelRefreshed) {
                voiceAllAdapter.editVideoUrl(0, video_urls);
            } else if (!isSuccess) {
                ////  retry
                voiceAllAdapter.notifyDataSetChanged();
            }
        }
    };


    final Handler handler = new Handler();
    PlayerSelector selector = PlayerSelector.DEFAULT;

    private void setVideoRef() {

        fragmentVoiceBinding.newsRecyclerView.setPlayerSelector(null);
        // Using TabLayout has a downside: once we click to a tab to change page, there will be no animation,
        // which will cause our setup doesn't work well. We need a delay to make things work.
        handler.postDelayed(() -> {
            if (fragmentVoiceBinding.newsRecyclerView != null)
                fragmentVoiceBinding.newsRecyclerView.setPlayerSelector(selector);
        }, 500);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        /// important : remove auto play
        if (isVisibleToUser) {
            selector = PlayerSelector.DEFAULT;
        } else {
            selector = PlayerSelector.NONE;
        }

        // Using TabLayout has a downside: once we click to a tab to change page, there will be no animation,
        // which will cause our setup doesn't work well. We need a delay to make things work.
        handler.postDelayed(() -> {
            if (fragmentVoiceBinding.newsRecyclerView != null)
                fragmentVoiceBinding.newsRecyclerView.setPlayerSelector(selector);
        }, 200);
    }

    @Override
    public void onPlaylistCreated() {
        fragmentVoiceBinding.newsRecyclerView.setPlayerSelector(PlayerSelector.NONE);
        voiceAllAdapter.detachTrendingVideo();

    }

    @Override
    public void onPlaylistDestroyed(int basePosition, /*VoiceAllModel baseItem,*/ PlaybackInfo latestInfo) {

        fragmentVoiceBinding.newsRecyclerView.savePlaybackInfo(basePosition, latestInfo);
        fragmentVoiceBinding.newsRecyclerView.setPlayerSelector(selector);
        voiceAllAdapter.attachTrendingVideo();
    }

    private void setGAEvent(String action) {
        GAEventTracking.googleEventTracker(getActivity(), Constant.GoogleAnalyticsKey.RYV, action);
    }

    //
    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(import_contact_broadcast);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
        selector = null;
    }

    @Override
    public void onVoiceCreateResponse(APIResponse apiResponse) {
        refreshVoice();
    }

    @Override
    public void handleError(Throwable throwable) {
        //// retry option

    }

    @Override
    public void onVideoProgressChanged(int id, int percentDone, int count) {
        fragmentVoiceBinding.swipeContainer.setEnabled(false);
        int showProgressOnPosition = 2;
        if (fragmentVoiceBinding.newsRecyclerView.findViewHolderForAdapterPosition(showProgressOnPosition) instanceof VoiceAllViewHolder) {
            VoiceAllViewHolder voiceAllViewHolder = (VoiceAllViewHolder) fragmentVoiceBinding.newsRecyclerView.findViewHolderForAdapterPosition(showProgressOnPosition);
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
            fragmentVoiceBinding.swipeContainer.setEnabled(true);
            voiceAllAdapter.removeVoiceOnTop(0);
            DialogClass.showAlert(getContext(), getString(R.string.check_internet_connection));
        }
    }

    private int pastVisiblesItems = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;

    private void recyclerChangeListener() {
        fragmentVoiceBinding.newsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

}
