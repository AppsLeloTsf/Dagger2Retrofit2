package com.molitics.molitician.ui.dashboard.nationalNews;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.molitics.molitician.GoogleAnalytics.GAEventTracking;
import com.molitics.molitician.R;
import com.molitics.molitician.database.Database;
import com.molitics.molitician.database.HeadingNewsModel;
import com.molitics.molitician.database.NewsLeaderModel;
import com.molitics.molitician.database.VerticalNewsModel;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.LeaderAdapterInterface;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.Data;
import com.molitics.molitician.model.News;
import com.molitics.molitician.model.NewsVideoModel;
import com.molitics.molitician.ui.dashboard.OnFragmentInteractionListener;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.NewLeaderProfileActivity;
import com.molitics.molitician.ui.dashboard.nationalNews.meditor.NewsPresenter;
import com.molitics.molitician.ui.dashboard.nationalNews.meditor.NationalNewsHandler;
import com.molitics.molitician.ui.dashboard.newsDetail.DetailNewsActivity;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.HomeSurveyInterface;
import com.molitics.molitician.ui.dashboard.nationalNews.adapter.NewsAdapter;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.molitics.molitician.util.Constant.GoogleAnalyticsKey.NATIONAL_NEWS;
import static com.molitics.molitician.util.ConvertUtils.convertRealmListToLeaderList;


public class NationalNewsFragment extends Fragment implements NewsPresenter.NewsView, OnNewsItemClick, ViewRefreshListener,
        NewsAdapter.OnLoadMoreResult, HomeSurveyInterface, LeaderAdapterInterface.LeaderClickListener,
        BranchShareClass.DeepLinkCallBack {

    String TAG = NationalNewsFragment.class.getSimpleName();

    public static final int DIALOG_FRAGMENT = 1;

    @BindView(R.id.news_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.loading_grid)
    LinearLayout loading_grid;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.RL_handler)
    RelativeLayout RL_handler;
    @BindView(R.id.ll_parent_lay_news)
    LinearLayout ll_parent_lay_news;

    private OnFragmentInteractionListener mListener;
    private NewsPresenter.UserActionsListener mActionsListener;
    private NewsAdapter newsAdapter;
    private int pageNo = Constant.ZERO;
    private boolean loadMore = false;
    private boolean newsApiCall = true;
    private boolean loadLocallyNews = true;
    private int language = 0;
    // LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager;

    private static NationalNewsFragment mNewsRef;

    public static Fragment getInstance() {
        if (mNewsRef == null) {
            mNewsRef = new NationalNewsFragment();
        }
        return mNewsRef;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_country_view, container, false);
        ButterKnife.bind(this, view);

        setRetainInstance(true);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeContainer.setRefreshing(true);
        pageNo = Constant.ZERO;

        bindUI();
        getNews(pageNo);
        getHeadingNews();
        getAdvertisePosition();
        getNewsFromDataBase();
        getNewsVideo();
        getTrendingLeader();

        checkAppExpiry();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_check, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void bindUI() {
        newsAdapter = new NewsAdapter(getActivity(), this, this,
                this, this);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(newsAdapter);

        swipeContainer.setOnRefreshListener(() -> {
            pageNo = Constant.ZERO;
            if (NetworkUtil.isNetworkConnected(getContext()))
                getNews(pageNo);
            else {
                swipeContainer.setRefreshing(false);
                Util.addMiniNetworkFailView(getContext(), RL_handler);
                Toast.makeText(getContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getNews(int pageNo) {
        if (mActionsListener != null) {
            mActionsListener.getNews(pageNo);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onNewsRecyclerClick(int position, int type) {

        ArrayList<News> send_news_list = new ArrayList<>();
        Intent intent = null;
        Bundle bundle = new Bundle();

        switch (type) {
            case Constant.Condition.HEASDER_NEWS:
                send_news_list = newsAdapter.getHeadingNewsList();
                intent = new Intent(getActivity(), DetailNewsActivity.class);
                break;
            case Constant.Condition.HEASDER_NEWS_YOUTUBE:
                intent = new Intent(getActivity(), YoutubeMoreActivity.class);
                intent.putExtra(Constant.IntentKey.NEWS_VIDEO, ExtraUtils.convertNewsModleToNewsVideoModel(newsAdapter.getHeadingNewsList().get(position)));
                intent.putExtra(Constant.IntentKey.FROM, Util.YoutubeType.national.name());
                break;
            case Constant.Condition.VIDEO:
                setGAEvent(Constant.GoogleAnalyticsKey.HOME_YOUTUBE_VIDEO);

                intent = new Intent(getActivity(), YoutubeMoreActivity.class);
                intent.putExtra(Constant.IntentKey.NEWS_VIDEO, newsAdapter.getVideoList().get(position));
                intent.putExtra(Constant.IntentKey.FROM, Util.YoutubeType.national.name());

                break;

            case Constant.Condition.YoutubeVideo:
                intent = new Intent(getActivity(), YoutubeMoreActivity.class);
                intent.putExtra(Constant.IntentKey.NEWS_VIDEO, ExtraUtils.convertNewsModleToNewsVideoModel(newsAdapter.getVerticalNewsList().get(position)));
                intent.putExtra(Constant.IntentKey.FROM, Util.YoutubeType.national.name());
                setGAEvent(Constant.GoogleAnalyticsKey.NATIONAL_NES_YOUTUBE_VIDEO);
                break;

            case Constant.Condition.INTERNATIONAL_LEADER:
                AllLeaderModel allLeaderModels = newsAdapter.getNewsLeadersList().get(position);
                intent = new Intent(getActivity(), NewLeaderProfileActivity.class);
                intent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, allLeaderModels.getId());
                intent.putExtra(Constant.IntentKey.LEADER_NAME, allLeaderModels.getName());
                intent.putExtra(Constant.IntentKey.LEADER_IMAGE, allLeaderModels.getProfileImage());
                intent.putExtra(Constant.IntentKey.LEADER_POSITION, allLeaderModels.getPosition());
                intent.putExtra(Constant.IntentKey.LEADER_LOCATION, allLeaderModels.getLocation());
                intent.putExtra(Constant.IntentKey.LEADER_PARTY, allLeaderModels.getPartyCode());
                intent.putExtra(Constant.IntentKey.LEADER_PART_LOGO, allLeaderModels.getPartyLogo());
                intent.putExtra(Constant.IntentKey.LEADER_FOLLOW, allLeaderModels.getFollowers());
                intent.putExtra(Constant.IntentKey.LEADER_IS_FOLLOW, allLeaderModels.getIsFollowing());
                intent.putExtra(Constant.IntentKey.ACTION, allLeaderModels.getUser_vote_action());
                intent.putExtra(Constant.IntentKey.UPVOTE, allLeaderModels.getLike_count());
                intent.putExtra(Constant.IntentKey.DOWNVOTE, allLeaderModels.getDislike_count());
                intent.putExtra(Constant.IntentKey.IS_VERIFY, allLeaderModels.getIsVerify());
                intent.putExtra(Constant.IntentKey.POSITION, position);

                break;
            default:
                send_news_list = newsAdapter.getVerticalNewsList();
                intent = new Intent(getActivity(), DetailNewsActivity.class);

        }
        bundle.putSerializable(Constant.IntentKey.NEWS_LIST, send_news_list);
        intent.putExtra(Constant.IntentKey.NEWS_POSITION, position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onVideoClick(int position, String video_link) {

        Intent intent = new Intent(getActivity(), YoutubePlayActivity.class);
        intent.putExtra(Constant.IntentKey.LINK, video_link);
        startActivity(intent);
    }

    @Override
    public void onSurveyClick(int position, ArrayList<AllSurveyModel> allSurveyModels) {

        FragmentManager fragmentManager = getFragmentManager();
        DialogFragment surveyViewPagerFragment = SurveyViewPagerFragment.getInstance(position, allSurveyModels);
        surveyViewPagerFragment.setTargetFragment(this, DIALOG_FRAGMENT);
        if (fragmentManager != null) {
            surveyViewPagerFragment.show(fragmentManager, getString(R.string.dialog_fragment));
        }
        GAEventTracking.googleEventTracker(getActivity(), Constant.GoogleAnalyticsKey.HOME_SURVEY,
                Constant.GoogleAnalyticsKey.HOME_SURVEY_CLICK);
    }

    @Override
    public void shareSurvey(int survey_id, String survey_question) {

        if (!MoliticsAppPermission.checkWritePermission()) {
            MoliticsAppPermission.requestStoragePermission(getActivity());
        }
        BranchShareClass.generateShareLink(getActivity(), this, survey_question, "", Constant.ShareCampaign.SURVEY,
                Constant.ShareLinkTag.SURVEY, String.valueOf(survey_id), "survey");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 23) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), getString(R.string.permission_granted_now), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), getString(R.string.denied_permission), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void clearAdapter() {
        newsAdapter.deleteNewsVideoList();
        newsAdapter.deleteVerticalList();
        newsAdapter.deleteHeadingList();
        newsAdapter.deleteSurvey();
    }

    private void updateAdapter(Data data) {

        if (pageNo == Constant.ZERO) {
            clearAdapter();
            if (data.getHomeNewsSurvey() != null)
                newsAdapter.addSurvey(data.getHomeNewsSurvey());
            newsAdapter.addNewsLeader(data.getNews_leader_model());
            newsAdapter.addHeadingNews(data.getHeadingSlider());
            newsAdapter.setNewsVideo(Constant.Language.DEFAULT, data.getNews_video());
            newsAdapter.setElection(data.getHomeElectionModel());
            newsAdapter.addVerticalNews(data.getNews().subList(Constant.ZERO, 10));
        }
        if (data.getNews().size() != 0)
            dealWithDataBase(data);

    }

    private void pageNoHandler(Data data) {
        newsApiCall = true;
        loadMore = data.getNews().size() > 9;
    }

    private void dealWithDataBase(Data data) {
        if (pageNo == Constant.ZERO) {
            //Delete previous data
            Database.deleteNewsLeader();
            Database.deleteNewsVideo();
            Database.deleteAllNews();
            Database.deleteArticle();
            // if page is 1 handle international news and Header news
            Database.writeHeadingNews(data.getHeadingSlider());
            Database.writeNewsLeaders(data.getNews_leader_model());
            Database.writeNewsVideo(data.getNews_video());
            Database.writeGlobalNews(data.getNews());
            Database.writeNationalTrendingLeaders(data.getNews_leader_model());
            PrefUtil.putInt(Constant.PreferenceKey.VIDEO_POSITION, data.getVideoPosition());
        } else {
            existingNewsHandler(data.getNews());
        }

    }

    private void existingNewsHandler(ArrayList<News> newsss) {
        Database.writeGlobalNews(newsss);

        final Handler handler = new Handler();
        int LOAD_TIME = 1000;
        handler.postDelayed(this::getNewsFromDataBase, LOAD_TIME);
    }

    private void getHeadingNews() {
        List<HeadingNewsModel> headingNewsModels = Database.readHeadingNews();
        if (headingNewsModels != null && !headingNewsModels.isEmpty()) {
            ArrayList<News> mNewsList = new ArrayList<>();
            for (int i = 0; i < headingNewsModels.size(); i++) {
                News mNews = new News();
                mNews.setType(headingNewsModels.get(i).getType());
                mNews.setContent(headingNewsModels.get(i).getContent());
                mNews.setId(headingNewsModels.get(i).getId());
                mNews.setDisplayType(headingNewsModels.get(i).getDisplayType());
                mNews.setHeading(headingNewsModels.get(i).getHeading());
                mNews.setTime(ExtraUtils.getTimeDiff(headingNewsModels.get(i).getApprovedAt()));
                mNews.setImage(headingNewsModels.get(i).getImage());
                mNews.setSource(headingNewsModels.get(i).getSource());
                mNews.setSourceLogo(headingNewsModels.get(i).getSourceLogo());
                mNews.setSurveyId(headingNewsModels.get(i).getSurveyId());
                mNews.setLocation(headingNewsModels.get(i).getLocation());
                mNews.setAuthor_name(headingNewsModels.get(i).getAuthor_name());
                mNews.setAuthorId(headingNewsModels.get(i).getAuthor_id());
                mNews.setShareLink(headingNewsModels.get(i).getShare_link());
                mNews.setSource_url(headingNewsModels.get(i).getSource_url());
                mNews.setLink(headingNewsModels.get(i).getLink());
                mNews.setSourceId(headingNewsModels.get(i).getSourceId());
                mNewsList.add(mNews);
            }
            newsAdapter.addHeadingNews(mNewsList);
            swipeContainer.setRefreshing(false);
        }
    }

    private void getNewsVideo() {
        List<NewsVideoModel> newsVideoModels = new ArrayList<>(Database.readNewsVideo());
        newsAdapter.setNewsVideo(Constant.Language.DEFAULT, newsVideoModels);
        linearLayoutManager.smoothScrollToPosition(recyclerView, null, 0);
    }

    private void getTrendingLeader() {
        List<NewsLeaderModel> newsLeaderModels = Database.readLeaderNews();
        if (newsLeaderModels != null && newsLeaderModels.size() > 0) {
            ArrayList<AllLeaderModel> mNewsList = new ArrayList<>();
            for (int i = 0; i < newsLeaderModels.size(); i++) {
                AllLeaderModel mNews = new AllLeaderModel();
                mNews.setName(newsLeaderModels.get(i).getName());
                mNews.setWeightage(newsLeaderModels.get(i).getWeightage());
                mNews.setProfileImage(newsLeaderModels.get(i).getProfileImage());
                mNews.setPosition(newsLeaderModels.get(i).getPosition());
                mNews.setPartyCode(newsLeaderModels.get(i).getPartyCode());
                mNews.setCanVote(newsLeaderModels.get(i).getCanVote());
                mNews.setFollowers(newsLeaderModels.get(i).getFollowers());
                //   mNews.setFollowerScore(newsLeaderModels.get(i).getFollowerScore());
                mNews.setId(newsLeaderModels.get(i).getId());
                mNews.setMessage(newsLeaderModels.get(i).getMessage());
                mNews.setIsVoted(newsLeaderModels.get(i).getIsVoted());
                mNews.setUser_vote_action(newsLeaderModels.get(i).getUser_vote_action());
                mNews.setLike_count(newsLeaderModels.get(i).getUpvoteCount());
                mNews.setDislike_count(newsLeaderModels.get(i).getDownVoteCount());
                mNews.setIsVerify(newsLeaderModels.get(i).getIsVerified());

                mNewsList.add(mNews);
            }
            newsAdapter.addNewsLeader(mNewsList);
        }
    }


    private void getNewsFromDataBase() {
        List<VerticalNewsModel> verticalNewsModels = Database.readGlobalNews(newsAdapter.getNewsListSize());
        if (verticalNewsModels != null) {
            ArrayList<News> mNewsList = new ArrayList<>();
            for (int i = 0; i < verticalNewsModels.size(); i++) {
                News mNews = new News();
                mNews.setType(verticalNewsModels.get(i).getType());
                mNews.setContent(verticalNewsModels.get(i).getContent());
                mNews.setId(verticalNewsModels.get(i).getId());
                mNews.setDisplayType(verticalNewsModels.get(i).getDisplayType());
                mNews.setHeading(verticalNewsModels.get(i).getHeading());
                mNews.setTime(verticalNewsModels.get(i).getTime());
                mNews.setImage(verticalNewsModels.get(i).getImage());
                mNews.setSource(verticalNewsModels.get(i).getSource());
                mNews.setSourceLogo(verticalNewsModels.get(i).getSourceLogo());
                mNews.setSurveyId(verticalNewsModels.get(i).getSurveyId());
                mNews.setLocation(verticalNewsModels.get(i).getLocation());
                mNews.setAuthor_name(verticalNewsModels.get(i).getAuthor_name());
                mNews.setAuthorId(verticalNewsModels.get(i).getAuthor_id());
                mNews.setShareLink(verticalNewsModels.get(i).getShare_link());
                mNews.setSource_url(verticalNewsModels.get(i).getSource_url());
                mNews.setLink(verticalNewsModels.get(i).getLink());
                mNews.setSourceId(verticalNewsModels.get(i).getSourceId());
                mNews.setTaggedLeader(convertRealmListToLeaderList(verticalNewsModels.get(i).getLeaderModels()));
                mNewsList.add(mNews);
            }
            newsAdapter.addVerticalNews(mNewsList);
            loading_grid.setVisibility(View.GONE);
            loadLocallyNews = true;
        }
    }


    private void getAdvertisePosition() {
        //   newsAdapter.setAdvPosition(Database.getAdvertisePosition());
    }

    @Override
    public void onLanguageSelectionClick(int language, int pageNo) {
        this.language = language;
        mActionsListener.getVideoByLanguage(language, pageNo, 10, 0);
    }

    @Override
    public void onNewsDone(APIResponse response) {
        ll_parent_lay_news.setVisibility(View.VISIBLE);
        RL_handler.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
        loading_grid.setVisibility(View.GONE);
        pageNoHandler(response.getData());
        if (response.getStatus() == 2000 && response.getData() != null && isVisible()) {
            updateAdapter(response.getData());
        }
    }

    @Override
    public void onNewsServerFailed(ServerException serverException) {
        swipeContainer.setRefreshing(false);
        newsApiCall = true;
        DialogClass.showAlert(getContext(), getString(R.string.something_went_wrong));
    }

    @Override
    public void onNewsAPIError(ApiException apiException) {
        loadMore = true;
        swipeContainer.setRefreshing(false);
        newsApiCall = true;
        loading_grid.setVisibility(View.GONE);
        if (isVisible()) {
            if (apiException.getCode() == 100003) {
                if (newsAdapter.getNewsListSize() == Constant.ZERO) {
                    Util.validateError(getContext(), apiException, RL_handler, this, newsAdapter.getNewsListSize());
                } else {
                    Util.addMiniNetworkFailView(getContext(), RL_handler);
                }
            } else {
                Util.validateError(getContext(), apiException, null, this, newsAdapter.getNewsListSize());
            }
        }
    }

    @Override
    public void onFormValidationError(Map<String, String> errors) {
    }

    @Override
    public void onVideoLanguageSelection(APIResponse apiResponse) {
        // if (apiResponse.getData().getNews_video() != null)
        newsAdapter.setNewsVideo(language, apiResponse.getData().getNews_video());
    }

    @Override
    public void onVideoLanguageError(ApiException apiException) {
        if (apiException.getCode() == 2004)
            newsAdapter.setNewsVideo(language, null);
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
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefereshClick() {
        getNews(pageNo);
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }

    @Override
    public void onLoadMore(int totalItem) {
        if (loadMore && newsApiCall && newsAdapter.getVerticalNewsList().size() == Database.countOfNews()) {
            if (loading_grid.getVisibility() == View.GONE) {
                loading_grid.setVisibility(View.VISIBLE);
                newsApiCall = false;
                if (Database.countOfNews() != 0) {
                    pageNo = newsAdapter.getNewsListSize();
                }
                getNews(pageNo);
            }
        } else {
            loading_grid.setVisibility(View.VISIBLE);
            if (loadLocallyNews) {
                final Handler handler = new Handler();
                handler.postDelayed(() -> {
                    //Do something after 100ms
                    loadLocallyNews = false;
                    getNewsFromDataBase();
                }, 1000);
            }
        }
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        newsAdapter.releaseLoader();
    }

    private void checkAppExpiry() {
        if (PrefUtil.getInt(Constant.USER_VISIT_COUNT) > 4) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
            mBuilder.setTitle(R.string.update_app_title);
            mBuilder.setMessage(R.string.update_message_txt);
            mBuilder.setPositiveButton(getString(R.string.update), (dialog, which) -> {
                ExtraUtils.openPlayStore(requireContext());
                dialog.dismiss();

            });
            mBuilder.setNegativeButton(getString(R.string.ignore), (dialog, which) -> dialog.dismiss());
            mBuilder.setCancelable(true);
            mBuilder.show();
            PrefUtil.putInt(Constant.USER_VISIT_COUNT, 0);
        }
    }

    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        ShareScreenShot.takeScreenshot(requireActivity(), getString(R.string.media_of_politics), url);
    }

    private void setGAEvent(String action) {
        GAEventTracking.googleEventTracker(getActivity(), NATIONAL_NEWS, action);
    }

    @Override
    public void onLeaderClick(int position, AllLeaderModel leaderModel) {

    }
}
