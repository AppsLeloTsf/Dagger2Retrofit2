package com.molitics.molitician.ui.dashboard.newsDetail;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.molitics.molitician.BR;
import com.molitics.molitician.BaseFragment;
import com.molitics.molitician.R;
import com.molitics.molitician.UiMediator.Loader;
import com.molitics.molitician.database.Database;
import com.molitics.molitician.databinding.FragmentNewsExpandedBinding;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.LeaderAdapterInterface;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.News;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.hotTopics.hotTopicDetail.HotTopicDetailActivity;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.NewLeaderProfileActivity;
import com.molitics.molitician.ui.dashboard.login.LoginDialogFragment;
import com.molitics.molitician.ui.dashboard.nationalNews.YoutubePlayActivity;
import com.molitics.molitician.ui.dashboard.nationalNews.newsRelatedLeadre.DetailNewsAdapter;
import com.molitics.molitician.ui.dashboard.nationalNews.newsRelatedLeadre.LeaderExtraHandler;
import com.molitics.molitician.ui.dashboard.nationalNews.newsRelatedLeadre.LeaderExtraPresenter;
import com.molitics.molitician.ui.dashboard.newsDetail.news_survey.NewsSurveyPresenter;
import com.molitics.molitician.ui.dashboard.newsDetail.viewModel.DetailNewsViewModel;
import com.molitics.molitician.ui.dashboard.survey.model.AllSurveyModel;
import com.molitics.molitician.ui.dashboard.survey.SurveyViewPagerFragment;
import com.molitics.molitician.ui.dashboard.termCondition.TermConditionActivity;
import com.molitics.molitician.ui.dashboard.voice.createVoice.CreateVoiceActivity;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.UiFactory;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.molitics.molitician.util.Constant.IntentKey.ITEM_POSITION;
import static com.molitics.molitician.util.Constant.IntentKey.LEADER_POSITION;
import static com.molitics.molitician.util.Constant.MOLITICS;
import static com.molitics.molitician.util.Constant.RequestTag.LOGINDIALOG;
import static com.molitics.molitician.util.MoliticsAppPermission.REQUEST_STORAGE;

/**
 * Created by rahul on 12/23/2016.
 */

public class NewsDetailFragment extends BaseFragment<FragmentNewsExpandedBinding, DetailNewsViewModel>
        implements NewsSurveyPresenter.SurveyView,
        DetailNewsAdapter.OnLeaderActionClick, LeaderAdapterInterface,
        LeaderExtraPresenter.LeaderExtraView, BranchShareClass.DeepLinkCallBack {

    private String TAG = NewsDetailFragment.class.getSimpleName();

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.news_share)
    ImageView news_share;
    @BindView(R.id.news_bookmark)
    ImageView news_bookmark;
    @BindView(R.id.news_unBookmark)
    ImageView news_unBookmark;
    @BindView(R.id.leader_recycler)
    RecyclerView leader_recycler;
    @BindView(R.id.take_survey_txt)
    FloatingActionButton take_survey_txt;
    @BindView(R.id.hot_topic_txt)
    TextView hot_topic_txt;
    @BindView(R.id.rl_related_topic)
    RelativeLayout rl_related_topic;
    @BindView(R.id.hot_topic_float_btn)
    FloatingActionButton hot_topic_float_btn;
    @BindView(R.id.main_progress_bar)
    ProgressBar main_progress_bar;
    @BindView(R.id.source_name)
    TextView source_name;

    private Map<YouTubeThumbnailView, YouTubeThumbnailLoader> thumbnailViewToLoaderMap;
    private ThumbnailListener thumbnailListener;
    private News newsDetailModel;
    private AllSurveyModel newsSurvey = null;
    private DetailNewsAdapter leaderAdapter;
    private LeaderExtraHandler leaderExtraHandler;
    private int news_id = 0;
    private int temp_follow_position = 0;
    private int temp_unfollow_position = 0;
    private int adapter_position = 0;
    private int likeAction = 0, candidateId = 0;

    private String source_name_txt, source_url_txt, url_type;

    private static final String ARTICLE_ACTION_URL = "articlelike";
    private static final String NEWS_ACTION_URL = "like";

    public static Fragment getInstance(int position, News newsDetailModel) {
        Fragment detailFragment = new NewsDetailFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt(ITEM_POSITION, position);
        mBundle.putSerializable(Constant.IntentKey.NEWS_DETAIL, newsDetailModel);
        detailFragment.setArguments(mBundle);
        return detailFragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.newsDetailViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_expanded;
    }

    @Override
    public DetailNewsViewModel getViewModel() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thumbnailViewToLoaderMap = new HashMap<>();
        thumbnailListener = new ThumbnailListener();
        leaderExtraHandler = new LeaderExtraHandler(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        getMyArguments();
        bindDataWithUI();
    }

    private void getMyArguments() {
        Bundle mBundle = getArguments();
        assert mBundle != null;
        newsDetailModel = (News) mBundle.getSerializable(Constant.IntentKey.NEWS_DETAIL);
        news_id = newsDetailModel != null ? newsDetailModel.getId() : 0;
        source_url_txt = newsDetailModel != null ? newsDetailModel.getSource_url() : null;
        if (TextUtils.isEmpty(source_url_txt) || newsDetailModel.getSource().trim().equalsIgnoreCase(MOLITICS))
            source_name.setVisibility(View.GONE);
        else
            source_name.setVisibility(View.VISIBLE);
    }

    private void bindDataWithUI() {
        //set adapter
        leaderAdapter = new DetailNewsAdapter(getContext(), this, this);
        LinearLayoutManager leaderLinearLayoutManager = new LinearLayoutManager(getContext());
        leader_recycler.setLayoutManager(leaderLinearLayoutManager);
        leader_recycler.setNestedScrollingEnabled(false);
        leader_recycler.setAdapter(leaderAdapter);
        leaderAdapter.addNewsOnLeaderModel(newsDetailModel);
        back.setOnClickListener(v -> getActivity().finish());
        news_share.setOnClickListener(v -> shareNews(String.valueOf(newsDetailModel.getId()), String.valueOf(newsDetailModel.getDisplayType())));
        source_name.setOnClickListener(v -> {
            source_url_txt = newsDetailModel.getSource_url();
            onNewsSourceClick();
        });

        if (Database.findOutNewsBookmark(newsDetailModel.getId()) != 0) {
            news_unBookmark.setVisibility(View.VISIBLE);
            news_bookmark.setVisibility(View.GONE);
        } else {
            news_unBookmark.setVisibility(View.GONE);
            news_bookmark.setVisibility(View.VISIBLE);
        }
        UiFactory.recyclerBottomSpace(getContext(), leader_recycler, R.dimen.toro_media_button_height);

    }

    @OnClick(R.id.news_bookmark)
    public void onNewsBookMarkClick() {
        Database.saveNewsAsBookMark(newsDetailModel);
        news_bookmark.setVisibility(View.GONE);
        news_unBookmark.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), "Bookmarked !", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.news_unBookmark)
    public void onNewsUnBookmarkClick() {
        Database.deleteBookMark(newsDetailModel.getId());
        news_bookmark.setVisibility(View.VISIBLE);
        news_unBookmark.setVisibility(View.GONE);
        // Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();
    }

    /// @OnClick(R.id.source_name)
    public void onNewsSourceClick() {
        if (!TextUtils.isEmpty(source_url_txt)) {
            Intent mIntent = new Intent(getContext(), TermConditionActivity.class);
            mIntent.putExtra(Constant.IntentKey.TOOL_BAR, source_name_txt);
            mIntent.putExtra(Constant.IntentKey.URL, source_url_txt);
            startActivity(mIntent);
        }
    }


    public void updateData(News new_news) {

        main_progress_bar.setVisibility(View.GONE);
        newsDetailModel = new_news;

        if (newsDetailModel.getIssueTag() != 0) {
            rl_related_topic.setVisibility(View.VISIBLE);
            hot_topic_txt.setText(newsDetailModel.getIssueTagName());
            hot_topic_float_btn.hide();
        }
        if (newsDetailModel.getSurvey().getResponse() != null) {
            newsSurvey = newsDetailModel.getSurvey();
            if (newsSurvey != null) {
                take_survey_txt.show();
                if (hot_topic_float_btn.getVisibility() == View.GONE) {

                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) take_survey_txt.getLayoutParams();
                    params.bottomMargin = ExtraUtils.convertDpToPx(getContext(), 20);
                    take_survey_txt.setLayoutParams(params);
                } else {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) take_survey_txt.getLayoutParams();
                    params.bottomMargin = ExtraUtils.convertDpToPx(getContext(), 120);
                    take_survey_txt.setLayoutParams(params);

                }
                take_survey_txt.setOnClickListener(v -> {
                    ArrayList<AllSurveyModel> survey_array_list = new ArrayList<>();
                    survey_array_list.add(newsSurvey);
                    openSurveyDialog(0, survey_array_list);
                });
            } else {
                take_survey_txt.hide();
            }
        }

        if (new_news.getLeaderModels() != null && new_news.getLeaderModels().size() != 0) {
            Util.showLog(TAG, String.valueOf(new_news.getLeaderModels().size()));
            leaderAdapter.addLeaderList(new_news.getLeaderModels());
        }

        if (new_news.getRelatedNews() != null && new_news.getRelatedNews().size() != 0) {
            leaderAdapter.addRelatedNews(new_news.getRelatedNews());
        }

        rl_related_topic.setOnClickListener(v -> {
            Intent mIntent = new Intent(getContext(), HotTopicDetailActivity.class);
            mIntent.putExtra(Constant.IntentKey.HOT_TOPIC_ID, newsDetailModel.getIssueTag());
            mIntent.putExtra(Constant.IntentKey.HOT_TOPIC_STRING, newsDetailModel.getIssueTagName());
            startActivity(mIntent);
        });
        hot_topic_float_btn.setOnClickListener(v -> {
            Intent mIntent = new Intent(getContext(), CreateVoiceActivity.class);
            mIntent.putExtra(Constant.IntentKey.Hot_TOPIC_TEXT, newsDetailModel.getIssueTagName());
            mIntent.putExtra(Constant.IntentKey.HOT_TOPIC_ID, newsDetailModel.getIssueTag());
            startActivity(mIntent);
        });
    }


    @Override
    public void onStop() {
        super.onStop();
        leaderAdapter.releaseLoaders();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseLoaders();
    }

    public void releaseLoaders() {
        if (thumbnailViewToLoaderMap != null) {
            for (YouTubeThumbnailLoader loader : thumbnailViewToLoaderMap.values()) {
                loader.release();
            }
        }
    }

    private void shareNews(String news_id, String display_type) {
        BranchShareClass.generateNewsShareLink(this, newsDetailModel.getHeading(), newsDetailModel.getShareLink());
    }

    private void playVideo(String link) {
        Intent intent = new Intent(getActivity(), YoutubePlayActivity.class);
        intent.putExtra(Constant.IntentKey.LINK, link);
        startActivity(intent);
    }

    @Override
    public void onSubmitSurvey(APIResponse apiResponse) {
        Loader.dismissMyDialog(getActivity());
    }

    @Override
    public void onSubmitSurveyServerError(ServerException serverException) {
        Loader.dismissMyDialog(getActivity());
        DialogClass.showAlert(getContext(), getString(R.string.something_went_wrong));
    }

    @Override
    public void onSubmitSurveyAPIError(ApiException apiException) {
        Loader.dismissMyDialog(getActivity());
        Util.validateError(getContext(), apiException, null, null, null);
    }

    @Override
    public void onNewsLeaderActionResponse(APIResponse apiResponse) {
        if (likeAction == 1) {
            leaderAdapter.likeDone(adapter_position, apiResponse.getData().getUpvoteCount(), apiResponse.getData().getDownvoteCount());
        } else if (likeAction == 2) {
            leaderAdapter.disLikeDone(adapter_position, apiResponse.getData().getUpvoteCount(), apiResponse.getData().getDownvoteCount());
        }
    }

    @Override
    public void onNewsLeaderActionApiError(ApiException apiException) {
    }

    @Override
    public void onDeleteNewsLeaderActionApiError(ApiException apiException) {

    }

    @Override
    public void onDeleteNewsLeaderAction(APIResponse apiResponse) {
        leaderAdapter.deleteDone(adapter_position, apiResponse.getData().getUpvoteCount(), apiResponse.getData().getDownvoteCount());
    }

    @Override
    public void onLikeDisClick(int position, int candidate_id, int action) {

        adapter_position = position;
        likeAction = action;
        candidateId = candidate_id;
        if (newsDetailModel.getDisplayType() == 4) {
            url_type = ARTICLE_ACTION_URL;
        } else {
            url_type = NEWS_ACTION_URL;
        }
        if (PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY).isEmpty()) {
            showLoginDialog();
        } else leaderExtraHandler.onNewsLeaderAction(url_type, news_id, candidate_id, action);
    }

    private void showLoginDialog() {
        DialogFragment dialogFragment = LoginDialogFragment.getInstance();
        dialogFragment.setTargetFragment(this, LOGINDIALOG);
        dialogFragment.show(getFragmentManager(), getString(R.string.dialog_fragment));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == LOGINDIALOG) {
                perfromActionOnLogin();
            }
        }
    }

    private void perfromActionOnLogin() {
        leaderExtraHandler.onNewsLeaderAction(url_type, news_id, candidateId, likeAction);
    }

    @Override
    public void onDeleteClick(int position, int candidate_id) {
        adapter_position = position;
        String url_type;
        if (newsDetailModel.getDisplayType() == 4) {
            url_type = ARTICLE_ACTION_URL;
        } else {
            url_type = NEWS_ACTION_URL;
        }
        leaderExtraHandler.onDeleteNewsLeaderAction(url_type, news_id, candidate_id);
    }

    @Override
    public void onFollowClick(int id, int position) {
        temp_follow_position = position;
        leaderExtraHandler.followCandidate(id);
    }

    @Override
    public void onUnFollowClick(int id, int position) {
        temp_unfollow_position = position;
        leaderExtraHandler.unFollowCandidate(id);
    }

    @Override
    public void onLeaderNewsClick(int position, AllLeaderModel allLeaderModel) {

        Intent mIntent = new Intent(getActivity(), NewLeaderProfileActivity.class);

        mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, Integer.valueOf(allLeaderModel.getId()));
        mIntent.putExtra(Constant.IntentKey.LEADER_NAME, allLeaderModel.getName());
        mIntent.putExtra(Constant.IntentKey.LEADER_IMAGE, allLeaderModel.getProfileImage());
        mIntent.putExtra(LEADER_POSITION, allLeaderModel.getPosition());
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

        startActivity(mIntent);
    }

    @Override
    public void onLeaderClick(int position, AllLeaderModel allLeaderModel) {

        Intent mIntent = new Intent(getActivity(), NewLeaderProfileActivity.class);

        mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, Integer.valueOf(allLeaderModel.getId()));
        mIntent.putExtra(Constant.IntentKey.LEADER_NAME, allLeaderModel.getName());
        mIntent.putExtra(Constant.IntentKey.LEADER_IMAGE, allLeaderModel.getProfileImage());
        mIntent.putExtra(LEADER_POSITION, allLeaderModel.getPosition());
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

        startActivity(mIntent);
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
    public void onUnFollowresponse(APIResponse apiResponse) {
        if (apiResponse.getStatus() == 2000) {
            leaderAdapter.unFollowDone(temp_unfollow_position, apiResponse.getData().getKCount());
        } else {
            leaderAdapter.onUnSuccessUnFollow(temp_unfollow_position);
        }
    }


    @Override
    public void onFollowedApiException(ApiException apiException) {

    }

    private void openSurveyDialog(int position, ArrayList<AllSurveyModel> allSurveyModels) {
        FragmentManager fm = getChildFragmentManager();
        if (fm != null) {
            SurveyViewPagerFragment.getInstance(position, allSurveyModels).show(fm, "Dialog Fragment");
        }
        // Show DialogFragment
    }


    private final class ThumbnailListener implements
            YouTubeThumbnailView.OnInitializedListener,
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onInitializationSuccess(
                YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
            loader.setOnThumbnailLoadedListener(this);
            thumbnailViewToLoaderMap.put(view, loader);
            view.setImageResource(R.drawable.video_placeholder);
            String videoId = (String) view.getTag();
            if (!TextUtils.isEmpty(videoId))
                loader.setVideo(videoId);
        }

        @Override
        public void onInitializationFailure(
                YouTubeThumbnailView view, YouTubeInitializationResult loader) {
        }

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView view, String videoId) {
        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView view, YouTubeThumbnailLoader.ErrorReason errorReason) {
            view.setImageResource(R.drawable.video_placeholder);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                shareNews(String.valueOf(newsDetailModel.getId()), String.valueOf(newsDetailModel.getDisplayType()));
            } else {
                Toast.makeText(getContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String headLine;
        headLine = newsDetailModel.getHeading() + "-" + "Molitics";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, headLine);
        shareIntent.putExtra(Intent.EXTRA_TITLE, "Molitics");
        String shareText = headLine + "\n" + url + "\n" + "via @MoliticsIndia";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(shareIntent, "Share link using"));
    }

}
