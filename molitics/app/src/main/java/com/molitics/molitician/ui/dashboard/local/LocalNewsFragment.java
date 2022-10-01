package com.molitics.molitician.ui.dashboard.local;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.molitics.molitician.GoogleAnalytics.GAEventTracking;
import com.molitics.molitician.R;
import com.molitics.molitician.database.Database;
import com.molitics.molitician.database.LocalNewsLeaderModel;
import com.molitics.molitician.database.LocalNewsModel;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.InitializeServerRequest;
import com.molitics.molitician.interfaces.LeaderAdapterInterface;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.Data;
import com.molitics.molitician.model.News;
import com.molitics.molitician.model.NewsVideoModel;
import com.molitics.molitician.ui.dashboard.DashBoardActivity;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;
import com.molitics.molitician.ui.dashboard.constantData.RecyclerTouchWithType;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.NewLeaderProfileActivity;
import com.molitics.molitician.ui.dashboard.localNews.LocalNewsAdapter;
import com.molitics.molitician.ui.dashboard.login.SelectLocationDialog;
import com.molitics.molitician.ui.dashboard.nationalNews.YoutubePlayActivity;
import com.molitics.molitician.ui.dashboard.nationalNews.meditor.NewsPresenter;
import com.molitics.molitician.ui.dashboard.nationalNews.meditor.NationalNewsHandler;
import com.molitics.molitician.ui.dashboard.newsDetail.DetailNewsActivity;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.HomeSurveyInterface;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.OnNewsItemClick;
import com.molitics.molitician.ui.dashboard.survey.model.AllSurveyModel;
import com.molitics.molitician.ui.dashboard.survey.SurveyViewPagerFragment;
import com.molitics.molitician.ui.dashboard.youtubeMoreDetail.YoutubeMoreActivity;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.MoliticsAppPermission;
import com.molitics.molitician.util.NetworkUtil;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.ShareScreenShot;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.molitics.molitician.util.ConvertUtils.convertRealmListToLeaderList;

public class LocalNewsFragment extends Fragment implements NewsPresenter.NewsView, InitializeServerRequest, OnNewsItemClick,
        ViewRefreshListener, LocalNewsAdapter.OnLoadMoreResult,
        LeaderAdapterInterface.LeaderClickListener, HomeSurveyInterface, BranchShareClass.DeepLinkCallBack {

    private static final int DIALOG_FRAGMENT = 1;

    @BindView(R.id.main_progress_bar)
    ProgressBar main_progress_bar;
    @BindView(R.id.loading_grid)
    LinearLayout loading_grid;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.news_recycler_view)
    RecyclerView news_recycler_view;
    @BindView(R.id.RL_handler)
    RelativeLayout RL_handler;
    @BindView(R.id.ll_parent_lay_news)
    LinearLayout ll_parent_lay_news;
    @BindView(R.id.stateChangeButton)
    TextView stateChangeButton;

    private InitializeServerRequest mListener;
    private NewsPresenter.UserActionsListener mActionsListener;
    private LocalNewsAdapter newsAdapter;
    private int pageNo = 0;
    private boolean loadMore = false;
    private boolean newsApiCall = true;
    private boolean isStateChange = false;
    private boolean loadLocallyNews = true;
    private List<ConstantModel> state_list;
    private int currentState = 0;
    private int selectedState = 0;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InitializeServerRequest) {
            mListener = (InitializeServerRequest) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionsListener = new NationalNewsHandler(this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country_view, container, false);
        ButterKnife.bind(this, view);
        setRetainInstance(true);
        pageNo = Constant.ZERO;
        bindUI();
        getNews(currentState, pageNo);
        getLocalNewsFromDataBase();
        getLocalNewsLeader();
        getToolBar();
        getNewsVideo();
        return view;
    }

    private void getToolBar() {
        stateChangeButton.setVisibility(View.VISIBLE);
        stateChangeButton.setOnClickListener(v -> {
            state_list = DashBoardActivity.state_list;
            if (state_list != null) {
                SelectLocationDialog selectLocationDialog = SelectLocationDialog.getInstance();
                selectLocationDialog.showDialog(getActivity(), getString(R.string.select_state), state_list, new RecyclerTouchWithType() {
                    @Override
                    public void onVerticalRecycler(int position, int type) {
                        onStateChangeListener(position, type);
                    }

                    @Override
                    public void onCloseClick() {
                    }
                });
            } else {
                onStateNull();
            }
        });
    }


    private void bindUI() {
        if (isStateChange) {
            currentState = selectedState;
        } else {
            currentState = PrefUtil.getInt(Constant.PreferenceKey.DEFAULT_STATE_VALUE);
        }
        newsAdapter = new LocalNewsAdapter(getActivity(), this, this, this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        news_recycler_view.setLayoutManager(linearLayoutManager);
        news_recycler_view.setAdapter(newsAdapter);

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(() -> {
            pageNo = Constant.ZERO;
            if (NetworkUtil.isNetworkConnected(Objects.requireNonNull(getContext())))
                getNews(currentState, pageNo);
            else {
                swipeContainer.setRefreshing(false);
                Util.addMiniNetworkFailView(getContext(), RL_handler);
                Toast.makeText(getContext(), getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_check, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void getNews(int value, int pageNo) {
        if (mActionsListener != null) {
            mActionsListener.getLocalNews(value, pageNo);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onStateNull() {
        if (mListener != null) {
            Util.toastLong(getContext(), getString(R.string.state_list_fetching));
            mListener.createServerRequest(Constant.RequestTag.STATE_LIST);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void clearAdapter() {
        newsAdapter.deleteNewsLeadersList();
        newsAdapter.deleteLocalNewsList();
        newsAdapter.deleteSurvey();

    }

    public void updateAdapter(Data data) {
        if (pageNo == Constant.ZERO) {
            clearAdapter();
            if (data.getHomeNewsSurvey() != null)
                newsAdapter.addSurvey(data.getHomeNewsSurvey());
            newsAdapter.addNewsLeader(data.getNews_leader_model());
            newsAdapter.addNewsVideo(data.getNews_video());
            if (data.getNews().size() > 9)
                newsAdapter.addLocalNews(data.getNews().subList(Constant.ZERO, 10));
            else
                newsAdapter.addLocalNews(data.getNews());
            newsAdapter.setTrendingNewsPosition(data.getTrendingLeaderPosition());
        }
        dealWithDataBase(data);
    }


    private void getNewsVideo() {
        List<NewsVideoModel> newsVideoModels = new ArrayList<>(Database.readLocalNewsVideo());
        newsAdapter.addNewsVideo(newsVideoModels);
    }

    private void dealWithDataBase(Data data) {
        ArrayList<News> vertical_news_list = data.getNews();
        if (pageNo == 0) {
            Database.deleteLocalAllNews();
            Database.deleteLocalNewsLeader();
            Database.writeLocalNews(data.getNews());
            Database.writeLocalNewsLeaders(data.getNews_leader_model());
            Database.writeGlobalNews(data.getNews());
        } else {
            existingNewsHandler(vertical_news_list);
        }
    }

    private void existingNewsHandler(ArrayList<News> news) {
        Database.writeLocalNews(news);

        if (!news.isEmpty()) {
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                getLocalNewsFromDataBase();
            }, 1000);
        }
    }

    @SuppressWarnings("unchecked")
    private void getLocalNewsFromDataBase() {
        List<LocalNewsModel> localNewsModels = Database.readLocalNews(newsAdapter.getNewsListSize());
        if (localNewsModels != null) {
            ArrayList<News> mNewsList = new ArrayList<>();
            for (int i = 0; i < localNewsModels.size(); i++) {
                News mNews = new News();
                mNews.setType(localNewsModels.get(i).getType());
                mNews.setContent(localNewsModels.get(i).getContent());
                mNews.setId(localNewsModels.get(i).getId());
                mNews.setDisplayType(localNewsModels.get(i).getDisplayType());
                mNews.setHeading(localNewsModels.get(i).getHeading());
                mNews.setTime(localNewsModels.get(i).getTime());
                mNews.setImage(localNewsModels.get(i).getImage());
                mNews.setSource(localNewsModels.get(i).getSource());
                mNews.setSourceLogo(localNewsModels.get(i).getSourceLogo());
                mNews.setSurveyId(localNewsModels.get(i).getSurveyId());
                mNews.setAuthor_name(localNewsModels.get(i).getAuthor_name());
                mNews.setAuthorId(localNewsModels.get(i).getAuthor_id());
                mNews.setShareLink(localNewsModels.get(i).getShare_link());
                mNews.setLink(localNewsModels.get(i).getLink());
                mNews.setSourceId(localNewsModels.get(i).getSourceId());
                mNews.setTaggedLeader(convertRealmListToLeaderList(localNewsModels.get(i).getLeaderModels()));

                mNewsList.add(mNews);
            }
            newsAdapter.addLocalNews(mNewsList);
            loading_grid.setVisibility(View.GONE);
            loadLocallyNews = true;
        }
    }

    private void getLocalNewsLeader() {
        List<LocalNewsLeaderModel> leaderNewsModels = Database.readLocalNewsLeader();
        if (leaderNewsModels != null) {
            ArrayList<AllLeaderModel> allLeaderModels = new ArrayList<>();
            for (int i = 0; i < leaderNewsModels.size(); i++) {
                AllLeaderModel leaderModel = new AllLeaderModel();

                leaderModel.setId(leaderNewsModels.get(i).getId());
                leaderModel.setCanVote(leaderNewsModels.get(i).getCanVote());
                leaderModel.setFollowers(leaderNewsModels.get(i).getFollowers());
                leaderModel.setIsFollowing(leaderNewsModels.get(i).getIsFollowing());
                leaderModel.setIsVoted(leaderNewsModels.get(i).getIsVoted());
                leaderModel.setName(leaderNewsModels.get(i).getName());
                leaderModel.setProfileImage(leaderNewsModels.get(i).getProfileImage());
                leaderModel.setPosition(leaderNewsModels.get(i).getPosition());
                leaderModel.setUser_vote_action(leaderNewsModels.get(i).getUser_vote_action());
                leaderModel.setLike_count(leaderNewsModels.get(i).getUpvoteCount());
                leaderModel.setDislike_count(leaderNewsModels.get(i).getDownVoteCount());
                leaderModel.setIsVerify(leaderNewsModels.get(i).getIsVerified());

                allLeaderModels.add(leaderModel);
            }

            newsAdapter.addNewsLeader(allLeaderModels);

        }
    }

    public void pageNoHandler(Data data) {
        newsApiCall = true;
        loadMore = data.getNews().size() > 9;
    }

    @Override
    public void onNewsDone(APIResponse response) {
        swipeContainer.setRefreshing(false);
        RL_handler.setVisibility(View.GONE);
        main_progress_bar.setVisibility(View.GONE);
        handleLoaderUI();
        if (response != null && response.getStatus() == 2000 && response.getData() != null) {
            updateAdapter(response.getData());
            pageNoHandler(response.getData());
        }
    }

    @Override
    public void onNewsServerFailed(ServerException serverException) {
        newsApiCall = true;
        swipeContainer.setRefreshing(false);
        main_progress_bar.setVisibility(View.GONE);
        if (isVisible())
            DialogClass.showAlert(getContext(), getString(R.string.something_went_wrong));
    }

    @Override
    public void onNewsAPIError(ApiException apiException) {
        loadMore = true;
        newsApiCall = true;
        swipeContainer.setRefreshing(false);
        main_progress_bar.setVisibility(View.GONE);
        loading_grid.setVisibility(View.GONE);
        if (isVisible()) {
            Util.validateError(getContext(), apiException, RL_handler, this, newsAdapter.getNewsListSize());
        }
    }

    @Override
    public void onFormValidationError(Map<String, String> errors) {
        swipeContainer.setRefreshing(false);
    }


    @Override
    public void createServerRequest(int tag) {
    }

    @Override
    public void onNewsRecyclerClick(int position, int type) {
        if (Constant.Condition.INTERNATIONAL_LEADER == type) {
            AllLeaderModel allLeaderModels = newsAdapter.getNewsLeadersList().get(position);
            Intent mIntent = new Intent(getActivity(), NewLeaderProfileActivity.class);
            mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, allLeaderModels.getId());
            mIntent.putExtra(Constant.IntentKey.LEADER_NAME, allLeaderModels.getName());
            mIntent.putExtra(Constant.IntentKey.LEADER_IMAGE, allLeaderModels.getProfileImage());
            mIntent.putExtra(Constant.IntentKey.LEADER_POSITION, allLeaderModels.getPosition());
            mIntent.putExtra(Constant.IntentKey.LEADER_LOCATION, allLeaderModels.getLocation());
            mIntent.putExtra(Constant.IntentKey.LEADER_PARTY, allLeaderModels.getPartyCode());
            mIntent.putExtra(Constant.IntentKey.LEADER_PART_LOGO, allLeaderModels.getPartyLogo());
            mIntent.putExtra(Constant.IntentKey.LEADER_FOLLOW, allLeaderModels.getFollowers());
            mIntent.putExtra(Constant.IntentKey.LEADER_IS_FOLLOW, allLeaderModels.getIsFollowing());
            mIntent.putExtra(Constant.IntentKey.ACTION, allLeaderModels.getUser_vote_action());
            mIntent.putExtra(Constant.IntentKey.UPVOTE, allLeaderModels.getLike_count());
            mIntent.putExtra(Constant.IntentKey.DOWNVOTE, allLeaderModels.getDislike_count());
            startActivity(mIntent);
        } else if (Constant.Condition.VIDEO == type) {
            Intent intent = new Intent(getActivity(), DetailNewsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.IntentKey.NEWS_LIST, convertNewsMobile(newsAdapter.getVideoList()));
            intent.putExtra(Constant.IntentKey.NEWS_POSITION, position);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (Constant.Condition.YoutubeVideo == type) {
            Intent intent = new Intent(getActivity(), YoutubeMoreActivity.class);
            intent.putExtra(Constant.IntentKey.NEWS_VIDEO, ExtraUtils.convertNewsModleToNewsVideoModel(newsAdapter.getLocalNewsList().get(position)));
            intent.putExtra(Constant.IntentKey.NEWS_POSITION, position);
            intent.putExtra(Constant.IntentKey.FROM, Util.YoutubeType.local.name());

            startActivity(intent);
        } else {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.IntentKey.NEWS_LIST, newsAdapter.getLocalNewsList());
            Intent intent = new Intent(getActivity(), DetailNewsActivity.class);
            intent.putExtra(Constant.IntentKey.NEWS_POSITION, position);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void onVideoClick(int position, String video_link) {
        Intent intent = new Intent(getActivity(), YoutubePlayActivity.class);
        intent.putExtra(Constant.IntentKey.LINK, video_link);
        startActivity(intent);
    }

    private void onStateChangeListener(int position, int type) {
        pageNo = 0;
        isStateChange = true;
        SelectLocationDialog.dismissDialog();
        selectedState = state_list.get(position).getValue();
        String select_state_name = state_list.get(position).getKey();
        stateChangeButton.setText(select_state_name);
        currentState = selectedState;
        newsAdapter.deleteLocalNewsList();
        Database.deleteLocalAllNews();
        getNews(currentState, pageNo);
        main_progress_bar.setVisibility(View.VISIBLE);
        news_recycler_view.setVisibility(View.GONE);
        setGAEvent(Constant.GoogleAnalyticsKey.STATE_CHANGE + "  " + currentState);
    }

    private void handleLoaderUI() {
        swipeContainer.setRefreshing(false);
        loading_grid.setVisibility(View.GONE);
        if (main_progress_bar.getVisibility() == View.VISIBLE)
            main_progress_bar.setVisibility(View.GONE);
        if (news_recycler_view.getVisibility() == View.GONE)
            news_recycler_view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefereshClick() {
        getNews(currentState, pageNo);
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }

    @Override
    public void onLoadMore(int totalItem) {

        if (loadMore && newsApiCall && newsAdapter.getNewsListSize() == Database.countOfLocalNews()) {
            newsApiCall = false;
            if (Database.countOfLocalNews() != 0) {
                pageNo = newsAdapter.getNewsListSize();
            }
            getNews(currentState, pageNo);
        } else if (loadLocallyNews) {
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                loadLocallyNews = false;
                getLocalNewsFromDataBase();
            }, 2000);
        }
        if (loading_grid.getVisibility() == View.GONE)
            loading_grid.setVisibility(View.VISIBLE);
    }

  /*  @Override
    public void onFollowClick(int id, int position) {
        int temp_follow_position = position;
    }

    @Override
    public void onUnFollowClick(int id, int position) {
        int temp_unfollow_position = position;
    }

    */

    @Override
    public void onLeaderClick(int position, AllLeaderModel allLeaderModel) {

    }


    @Override
    public void onSurveyClick(int position, ArrayList<AllSurveyModel> allSurveyModels) {
        FragmentManager fm = getFragmentManager();
        DialogFragment surveyViewPagerFragment = SurveyViewPagerFragment.getInstance(position, allSurveyModels);
        surveyViewPagerFragment.setTargetFragment(this, DIALOG_FRAGMENT);
        surveyViewPagerFragment.show(fm, "Dialog Fragment");
    }

    @Override
    public void shareSurvey(int survey_id, String survey_question) {

        if (MoliticsAppPermission.checkWritePermission()) {
            BranchShareClass.generateShareLink(getActivity(), this, survey_question, "", Constant.ShareCampaign.SURVEY,
                    Constant.ShareLinkTag.SURVEY, String.valueOf(survey_id), Constant.ShareLinkTag.NEWS);

        } else {
            MoliticsAppPermission.requestStoragePermission(getActivity());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == DIALOG_FRAGMENT) {
                if (newsAdapter.getSurveyVisible()) {
                    newsAdapter.addSurvey(null);
                }
            }
        }
    }

    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        ShareScreenShot.takeScreenshot(getActivity(), getString(R.string.media_of_politics), url);
    }

    private void setGAEvent(String action) {
        GAEventTracking.googleEventTracker(getActivity(), Constant.GoogleAnalyticsKey.STATE_NEWS, action);
    }

    @Override
    public void onStop() {
        super.onStop();
        newsAdapter.releaseLoaders();
    }

    private ArrayList<News> convertNewsMobile(List<NewsVideoModel> news_video) {
        ArrayList<News> send_news_list = new ArrayList<>();

        for (int i = 0; i < news_video.size(); i++) {
            News mNews = new News();

            mNews.setDisplayType(news_video.get(i).getDisplayType());
            mNews.setContent(news_video.get(i).getContent());
            mNews.setTime(news_video.get(i).getTime());
            mNews.setType(news_video.get(i).getType());
            mNews.setSource(news_video.get(i).getSource());
            mNews.setHeading(news_video.get(i).getHeading());
            mNews.setId(news_video.get(i).getId());
            mNews.setImage(news_video.get(i).getImage());
            mNews.setLink(news_video.get(i).getLink());
            mNews.setSourceLogo(news_video.get(i).getSourceLogo());
            mNews.setSurveyId(news_video.get(i).getSurveyId());
            mNews.setVideoLink(news_video.get(i).getVideoLink());
            send_news_list.add(mNews);
        }
        return send_news_list;
    }

}
