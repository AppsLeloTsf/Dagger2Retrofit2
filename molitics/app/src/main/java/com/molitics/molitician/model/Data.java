package com.molitics.molitician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.molitics.molitician.ui.dashboard.FragmentListModel;
import com.molitics.molitician.ui.dashboard.cartoon.cartoonModel.AllCartoonModel;
import com.molitics.molitician.ui.dashboard.channels.model.ChannelBankModel;
import com.molitics.molitician.ui.dashboard.channels.model.ChannelDataModel;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;
import com.molitics.molitician.ui.dashboard.election.past_election.model.AboutConstituencyModel;
import com.molitics.molitician.ui.dashboard.election.past_election.model.AllPastElectionList;
import com.molitics.molitician.ui.dashboard.election.past_election.model.PastStateModel;
import com.molitics.molitician.ui.dashboard.election.upcoming_election.model.UpcomingPartyModel;
import com.molitics.molitician.ui.dashboard.home.model.HomeBrowseModel;
import com.molitics.molitician.ui.dashboard.hotTopics.model.HotTopicModel;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.leader.leaderProfile.CandidateProfileModel;
import com.molitics.molitician.ui.dashboard.liveVideo.model.MoliticsVideoModel;
import com.molitics.molitician.ui.dashboard.more.model.MyContactListModel;
import com.molitics.molitician.ui.dashboard.nationalNews.model.HomeElectionModel;
import com.molitics.molitician.ui.dashboard.register.UserModel;
import com.molitics.molitician.ui.dashboard.splash.ExpiryData;
import com.molitics.molitician.ui.dashboard.survey.model.AllSurveyModel;
import com.molitics.molitician.ui.dashboard.survey.model.SubmitSurveyResponseModel;
import com.molitics.molitician.ui.dashboard.userProfile.model.UserProfileModel;
import com.molitics.molitician.ui.dashboard.voice.feedAction.FeedActionParticipantModel;
import com.molitics.molitician.ui.dashboard.voice.model.CommentModel;
import com.molitics.molitician.ui.dashboard.voice.model.FeedModel;
import com.molitics.molitician.ui.dashboard.voice.model.SuggestionModel;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.ui.dashboard.voice.reportFeed.ReportReasonResponseModel;

import java.util.ArrayList;
import java.util.List;

public class Data {

    @SerializedName("status")
    Integer status;

    @SerializedName("device")
    @Expose
    Device device;
    @SerializedName("news")
    @Expose
    private ArrayList<News> news = new ArrayList<News>();

    @SerializedName("heading_slider")
    @Expose
    private ArrayList<News> headingSlider = null;

    @SerializedName("article")
    @Expose
    private ArrayList<News> article = null;

    @SerializedName("video")
    @Expose
    private ArrayList<NewsVideoModel> news_video = null;


    @SerializedName("national_live_videos")
    @Expose
    private ArrayList<NewsVideoModel> nationalLiveVideos = null;


    @SerializedName("local_live_videos")
    @Expose
    private ArrayList<NewsVideoModel> localLiveVideos = null;


    @SerializedName("molitics_videos")
    @Expose
    private MoliticsVideoModel moliticsVideoModel = null;


    @SerializedName("article_slider")
    @Expose
    private ArrayList<ArticleModel> articleSlider = null;

    @SerializedName("trending_leaders")
    @Expose
    private ArrayList<AllLeaderModel> news_leader_model = null;

    @SerializedName("article_array_position")
    @Expose
    private Integer articlePosition;

    @SerializedName("video_position")
    @Expose
    private Integer videoPosition = 0;

    @SerializedName("advertise_positions")
    @Expose
    private List<Integer> advertise_positions;

    @SerializedName("home_survey")
    @Expose
    private List<AllSurveyModel> homeNewsSurvey;


    @SerializedName("candidates")
    @Expose
    private ArrayList<AllLeaderModel> leaderModels = null;

    @SerializedName("state")
    @Expose
    private List<ConstantModel> state_list = null;


    @SerializedName("profession")
    @Expose
    List<ConstantModel> profession_list = null;


    @SerializedName("mla")
    @Expose
    private List<ConstantModel> mla_list = null;

    @SerializedName("mp")
    @Expose
    private List<ConstantModel> mp_list = null;

    @SerializedName("district")
    @Expose
    private List<ConstantModel> district_list = null;

    @SerializedName("party")
    @Expose
    private List<ConstantModel> political_party = null;

    @SerializedName("filter_district")
    @Expose
    private List<ConstantModel> filter_district_list = null;

    @SerializedName("filter_mp")
    @Expose
    private List<ConstantModel> filter_dmp_list = null;

    @SerializedName("filter_mla")
    @Expose
    private List<ConstantModel> filter_mla_list = null;

    @SerializedName("filter_party")
    @Expose
    private List<ConstantModel> filter_party_list = null;

    @SerializedName("post")
    @Expose
    private List<ConstantModel> filter_post_list = null;

    @SerializedName("all_survey")
    @Expose
    private ArrayList<AllSurveyModel> survey_list = null;

    @SerializedName("survey")
    @Expose
    private AllSurveyModel newsSurveyModel;

    @SerializedName("trending_leaders_position")
    @Expose
    private Integer trendingLeaderPosition;

    @SerializedName("profile")
    @Expose
    private DeviceRegistration deviceRegistration;

    @SerializedName("survey_results")
    @Expose
    private SubmitSurveyResponseModel newsSurveyResponseModel;

    @SerializedName("loginResponse")
    @Expose
    private LoginResponse loginResponse;

    @SerializedName("detail")
    @Expose
    private News newsDetailModel;

    @SerializedName("candidate_profile")
    @Expose
    private CandidateProfileModel candidateProfileModel;

    @SerializedName("tabs")
    @Expose
    private List<FragmentListModel> fragmentListModel;

    @SerializedName("server_details")
    @Expose
    private ServerDetails serverDetails;

    @SerializedName("election_result")
    @Expose
    private PastStateModel pastStateModel;

    @SerializedName("mp_details")
    @Expose
    private AboutConstituencyModel mpConstituencyModel = null;

    @SerializedName("mla_details")
    @Expose
    private AboutConstituencyModel mlaConstituencyModel = null;

    /* @SerializedName("detail_survey")
    @Expose
    private SurveyDetailModel surveyDetailModel;*/


    @SerializedName("k_count")
    @Expose
    private String k_count = "";

    @SerializedName("election")
    @Expose
    private List<AllPastElectionList> allPastElectionLists = null;

    @SerializedName("election_detail")
    @Expose
    private List<UpcomingPartyModel> upcomingPartyModels = null;

    @SerializedName("user_details")
    @Expose
    private UserProfileModel userProfileModel;

    @SerializedName("user_profile")
    @Expose
    private UserProfileModel profileModel;

    @SerializedName("proposed_candidates")
    @Expose
    private ArrayList<AllLeaderModel> upComingLeaderModel = null;

    @SerializedName("leaders")
    @Expose
    private ArrayList<AllLeaderModel> followed_leader = null;

    @SerializedName("terms_conditions")
    @Expose
    private String terms_conditions;

    @SerializedName("expiry_data")
    @Expose
    private ExpiryData expiryData;

    @SerializedName("like_count")
    @Expose
    private Integer like_count = 0;

    @SerializedName("dislike_count")
    @Expose
    private Integer dislike_count;

    @SerializedName("upvote_count")
    @Expose
    private Integer upvoteCount;

    @SerializedName("downvote_count")
    @Expose
    private Integer downvoteCount;


    @SerializedName("allvideo")

    @Expose
    private List<NewsVideoModel> allVideo = null;

    @SerializedName("feeds")
    @Expose
    private List<FeedModel> feedModels = null;

    @SerializedName("suggestions")
    @Expose
    private SuggestionModel suggestionModel = null;

    @SerializedName("feeds-v3")
    @Expose
    private List<VoiceAllModel> voiceAllModels = null;

    @SerializedName("user_feeds")
    @Expose
    private List<VoiceAllModel> userFeeds = null;

    @SerializedName("feed")
    @Expose
    private VoiceAllModel singleVoiceModel = null;

    @SerializedName("feed-dddd")
    @Expose
    private VoiceAllModel voiceDetailModel = null;

    @SerializedName("comments")
    @Expose
    private List<CommentModel> allCommentModel = null;

    @SerializedName("comment")
    @Expose
    private CommentModel singleComment = null;

    @SerializedName("trending_topics")
    @Expose
    private List<HotTopicModel> hotTopicModels;

    @SerializedName("user_image_url")
    @Expose
    private String userImageUrl;

    @SerializedName("count")
    @Expose
    private int count;


    @SerializedName("trending_feeds_positions")
    @Expose
    private List<Integer> trendingFeedsPosition;

    @SerializedName("trending_feeds")
    @Expose
    private List<VoiceAllModel> trendingFeedsModel;

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("state_count")
    @Expose
    private int state_count;
    @SerializedName("local_count")
    @Expose
    private int local_count;

    @SerializedName("follow_leader_count")
    @Expose
    private int follow_leader_count;

    @SerializedName("cartoons")
    @Expose
    private List<AllCartoonModel> allCartoonModel;

    @SerializedName("users_list")
    @Expose
    private List<FeedActionParticipantModel> feedActionParticipantModels;

    @SerializedName("article_detail")
    @Expose
    private News article_detail;

    @SerializedName("image_urls")
    @Expose
    private List<String> videoImageUrls;

    @SerializedName("home_video_news")
    @Expose
    private NewsVideoModel homeSingleVideo;

    @SerializedName("mp_election")
    @Expose
    private HomeElectionModel homeElectionModel;


    @SerializedName("current_page")
    @Expose
    private int current_page;

    @SerializedName("user_contact")
    @Expose
    List<MyContactListModel> myContactListModels;


    @SerializedName("data")
    @Expose
    List<MyContactListModel> nearByUser;


    @SerializedName("channels")
    @Expose
    ChannelDataModel channelDataModel;

    @SerializedName("news_channel")
    @Expose
    ChannelBankModel channelsNews;

    @SerializedName("channel_following")
    @Expose
    boolean channelFollowing;

    @SerializedName("source_detail")
    @Expose
    SourceDetailModel sourceDetailModel;

    @SerializedName("hometabs")
    @Expose
    List<HomeBrowseModel> HometabsModel;

    @SerializedName("api_key")
    @Expose
    String apiKey;

    @SerializedName("reasons")
    List<ReportReasonResponseModel> reasonsData;

    @SerializedName("user")
    @Expose
    private UserModel userModel;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public List<HomeBrowseModel> getHometabsModel() {
        return HometabsModel;
    }

    public void setHometabsModel(List<HomeBrowseModel> hometabsModel) {
        HometabsModel = hometabsModel;
    }

    public SourceDetailModel getSourceDetailModel() {
        return sourceDetailModel;
    }

    public void setSourceDetailModel(SourceDetailModel sourceDetailModel) {
        this.sourceDetailModel = sourceDetailModel;
    }

    public boolean isChannelFollowing() {
        return channelFollowing;
    }

    public void setChannelFollowing(boolean channelFollowing) {
        this.channelFollowing = channelFollowing;
    }

    public ChannelBankModel getChannelsNews() {
        return channelsNews;
    }

    public void setChannelsNews(ChannelBankModel channelsNews) {
        this.channelsNews = channelsNews;
    }

    public ChannelDataModel getChannelDataModel() {
        return channelDataModel;
    }

    public void setChannelDataModel(ChannelDataModel channelDataModel) {
        this.channelDataModel = channelDataModel;
    }

    public List<MyContactListModel> getMyContactListModels() {
        return myContactListModels;
    }

    public void setMyContactListModels(List<MyContactListModel> myContactListModels) {
        this.myContactListModels = myContactListModels;
    }

    public NewsVideoModel getHomeSingleVideo() {
        return homeSingleVideo;
    }

    public void setHomeSingleVideo(NewsVideoModel homeSingleVideo) {
        this.homeSingleVideo = homeSingleVideo;
    }

    public List<String> getVideoImageUrls() {
        return videoImageUrls;
    }

    public void setVideoImageUrls(List<String> videoImageUrls) {
        this.videoImageUrls = videoImageUrls;
    }


    public int getFollow_leader_count() {
        return follow_leader_count;
    }

    public void setFollow_leader_count(int follow_leader_count) {
        this.follow_leader_count = follow_leader_count;
    }

    public News getArticle_detail() {
        return article_detail;
    }

    public void setArticle_detail(News article_detail) {
        this.article_detail = article_detail;
    }

    public List<FeedActionParticipantModel> getFeedActionParticipantModels() {
        return feedActionParticipantModels;
    }

    public List<MyContactListModel> getNearByUser() {
        return nearByUser;
    }

    public void setNearByUser(List<MyContactListModel> nearByUser) {
        this.nearByUser = nearByUser;
    }


    public void setFeedActionParticipantModels(List<FeedActionParticipantModel> feedActionParticipantModels) {
        this.feedActionParticipantModels = feedActionParticipantModels;
    }

    public List<AllCartoonModel> getAllCartoonModel() {
        return allCartoonModel;
    }

    public void setAllCartoonModel(List<AllCartoonModel> allCartoonModel) {
        this.allCartoonModel = allCartoonModel;
    }

    public Integer getLike_count() {
        return like_count;
    }

    public void setLike_count(Integer like_count) {
        this.like_count = like_count;
    }

    public Integer getDislike_count() {
        return dislike_count;
    }

    public void setDislike_count(Integer dislike_count) {
        this.dislike_count = dislike_count;
    }

    public Integer getUpvoteCount() {
        return upvoteCount;
    }

    public void setUpvoteCount(Integer upvoteCount) {
        this.upvoteCount = upvoteCount;
    }

    public Integer getDownvoteCount() {
        return downvoteCount;
    }

    public void setDownvoteCount(Integer downvoteCount) {
        this.downvoteCount = downvoteCount;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getState_count() {
        return state_count;
    }

    public void setState_count(int state_count) {
        this.state_count = state_count;
    }

    public int getLocal_count() {
        return local_count;
    }

    public void setLocal_count(int local_count) {
        this.local_count = local_count;
    }

    public List<Integer> getTrendingFeedsPosition() {
        return trendingFeedsPosition;
    }

    public void setTrendingFeedsPosition(List<Integer> trendingFeedsPosition) {
        this.trendingFeedsPosition = trendingFeedsPosition;
    }

    public List<VoiceAllModel> getTrendingFeedsModel() {
        return trendingFeedsModel;
    }

    public void setTrendingFeedsModel(List<VoiceAllModel> trendingFeedsModel) {
        this.trendingFeedsModel = trendingFeedsModel;
    }

    public HomeElectionModel getHomeElectionModel() {
        return homeElectionModel;
    }

    public void setHomeElectionModel(HomeElectionModel homeElectionModel) {
        this.homeElectionModel = homeElectionModel;
    }


    public VoiceAllModel getVoiceDetailModel() {
        return voiceDetailModel;
    }

    public void setVoiceDetailModel(VoiceAllModel voiceDetailModel) {
        this.voiceDetailModel = voiceDetailModel;
    }


    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public String getK_count() {
        return k_count;
    }

    public void setK_count(String k_count) {
        this.k_count = k_count;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public List<CommentModel> getAllCommentModel() {
        return allCommentModel;
    }

    public void setAllCommentModel(List<CommentModel> allCommentModel) {
        this.allCommentModel = allCommentModel;
    }

    public CommentModel getSingleComment() {
        return singleComment;
    }

    public void setSingleComment(CommentModel singleComment) {
        this.singleComment = singleComment;
    }

    public VoiceAllModel getSingleVoiceModel() {
        return singleVoiceModel;
    }

    public void setSingleVoiceModel(VoiceAllModel singleVoiceModel) {
        this.singleVoiceModel = singleVoiceModel;
    }

    public List<VoiceAllModel> getVoiceAllModels() {
        return voiceAllModels;
    }

    public void setVoiceAllModels(List<VoiceAllModel> voiceAllModels) {
        this.voiceAllModels = voiceAllModels;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public ArrayList<ArticleModel> getArticleSlider() {
        return articleSlider;
    }

    public void setArticleSlider(ArrayList<ArticleModel> articleSlider) {
        this.articleSlider = articleSlider;
    }

    public List<NewsVideoModel> getAllVideo() {
        return allVideo;
    }

    public void setAllVideo(List<NewsVideoModel> allVideo) {
        this.allVideo = allVideo;
    }

/*
    public Integer getDislike_count() {
        return dislike_count;
    }

    public void setDislike_count(Integer dislike_count) {
        this.dislike_count = dislike_count;
    }

    public Integer getLike_count() {

        return like_count;
    }

    public void setLike_count(Integer like_count) {
        this.like_count = like_count;
    }
*/

    public ExpiryData getExpiryData() {
        return expiryData;
    }

    public void setExpiryData(ExpiryData expiryData) {
        this.expiryData = expiryData;
    }

    public String getKCount() {
        return k_count;
    }

    public List<ConstantModel> getMla_list() {
        return mla_list;
    }

    public void setMla_list(List<ConstantModel> mla_list) {
        this.mla_list = mla_list;
    }

    public List<ConstantModel> getMp_list() {
        return mp_list;
    }

    public void setMp_list(List<ConstantModel> mp_list) {
        this.mp_list = mp_list;
    }

    public List<ConstantModel> getDistrict_list() {
        return district_list;
    }

    public void setDistrict_list(List<ConstantModel> district_list) {
        this.district_list = district_list;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }


    /**
     * @return The deviceRegistration
     */
    public DeviceRegistration getDeviceRegistration() {
        return deviceRegistration;
    }

    /**
     * @param deviceRegistration The deviceRegistration
     */
    public void setDeviceRegistration(DeviceRegistration deviceRegistration) {
        this.deviceRegistration = deviceRegistration;
    }

    /**
     * @return The news
     */
    public ArrayList<News> getNews() {
        return news;
    }

    /**
     * @param news The news
     */
    public void setNews(ArrayList<News> news) {
        this.news = news;
    }

    /**
     * @return The headingSlider
     */
    public ArrayList<News> getHeadingSlider() {
        return headingSlider;
    }

    /**
     * @param headingSlider The heading_slider
     */
    public void setHeadingSlider(ArrayList<News> headingSlider) {
        this.headingSlider = headingSlider;
    }

    /**
     * @return The trendingNews
     */
 /*   public ArrayList<News> getTrendingNews() {
        return trendingNews;
    }*/

    /**
     * @param trendingNews The trending_news
     */
/*    public void setTrendingNews(ArrayList<News> trendingNews) {
        this.trendingNews = trendingNews;
    }*/

    /**
     * @return The trendingNewsPosition
     */
    public Integer getTrendingLeaderPosition() {
        return trendingLeaderPosition;
    }

    /**
     * @param trendingLeaderPosition The trending_Leader_position
     */
    public void setTrendingLeaderPosition(Integer trendingLeaderPosition) {
        this.trendingLeaderPosition = trendingLeaderPosition;
    }

    public List<ConstantModel> getState_list() {
        return state_list;
    }

    public void setState_list(List<ConstantModel> state_list) {
        this.state_list = state_list;
    }

    public List<ConstantModel> getProfession_list() {
        return profession_list;
    }

    public void setProfession_list(List<ConstantModel> profession_list) {
        this.profession_list = profession_list;
    }


    public List<FragmentListModel> getFragmentListModel() {
        return fragmentListModel;
    }

    public void setFragmentListModel(List<FragmentListModel> fragmentListModel) {
        this.fragmentListModel = fragmentListModel;
    }

    public Integer getArticlePosition() {
        return articlePosition;
    }

    public void setArticlePosition(Integer articlePosition) {
        this.articlePosition = articlePosition;
    }

    public ArrayList<AllLeaderModel> getLeaderModels() {
        return leaderModels;
    }

    public void setLeaderModels(ArrayList<AllLeaderModel> leaderModels) {
        this.leaderModels = leaderModels;
    }

    public ArrayList<NewsVideoModel> getLocalLiveVideos() {
        return localLiveVideos;
    }

    public void setLocalLiveVideos(ArrayList<NewsVideoModel> localLiveVideos) {
        this.localLiveVideos = localLiveVideos;
    }

/*    public List<AllSurveyModel> getSurvey_list() {
        return survey_list;
    }

    public void setSurvey_list(List<AllSurveyModel> survey_list) {
        this.survey_list = survey_list;
    }*/

    public News getNewsDetailModel() {
        return newsDetailModel;
    }

    public void setNewsDetailModel(News newsDetailModel) {
        this.newsDetailModel = newsDetailModel;
    }

    public AllSurveyModel getNewsSurveyModel() {
        return newsSurveyModel;
    }

    public void setNewsSurveyModel(AllSurveyModel newsSurveyModel) {
        this.newsSurveyModel = newsSurveyModel;
    }

    public SubmitSurveyResponseModel getNewsSurveyResponseModel() {
        return newsSurveyResponseModel;
    }

    public void setNewsSurveyResponseModel(SubmitSurveyResponseModel newsSurveyResponseModel) {
        this.newsSurveyResponseModel = newsSurveyResponseModel;
    }

    public List<ConstantModel> getFilter_party_list() {
        return filter_party_list;
    }

    public void setFilter_party_list(List<ConstantModel> filter_party_list) {
        this.filter_party_list = filter_party_list;
    }

    public List<ConstantModel> getFilter_mla_list() {
        return filter_mla_list;
    }

    public void setFilter_mla_list(List<ConstantModel> filter_mla_list) {
        this.filter_mla_list = filter_mla_list;
    }

    public List<ConstantModel> getFilter_dmp_list() {
        return filter_dmp_list;
    }

    public void setFilter_dmp_list(List<ConstantModel> filter_dmp_list) {
        this.filter_dmp_list = filter_dmp_list;
    }

    public List<ConstantModel> getFilter_district_list() {
        return filter_district_list;
    }

    public void setFilter_district_list(List<ConstantModel> filter_district_list) {
        this.filter_district_list = filter_district_list;
    }

    public CandidateProfileModel getCandidateProfileModel() {
        return candidateProfileModel;
    }

    public void setCandidateProfileModel(CandidateProfileModel candidateProfileModel) {
        this.candidateProfileModel = candidateProfileModel;
    }

    public ArrayList<AllSurveyModel> getSurvey_list() {
        return survey_list;
    }

    public void setSurvey_list(ArrayList<AllSurveyModel> survey_list) {
        this.survey_list = survey_list;
    }

    public List<AllPastElectionList> getAllPastElectionLists() {
        return allPastElectionLists;
    }

    public void setAllPastElectionLists(List<AllPastElectionList> allPastElectionLists) {
        this.allPastElectionLists = allPastElectionLists;
    }

    public PastStateModel getPastStateModel() {
        return pastStateModel;
    }

    public void setPastStateModel(PastStateModel pastStateModel) {
        this.pastStateModel = pastStateModel;
    }

    public AboutConstituencyModel getMpConstituencyModel() {
        return mpConstituencyModel;
    }

    public void setMpConstituencyModel(AboutConstituencyModel mpConstituencyModel) {
        this.mpConstituencyModel = mpConstituencyModel;
    }

    public AboutConstituencyModel getMlaConstituencyModel() {
        return mlaConstituencyModel;
    }

    public void setMlaConstituencyModel(AboutConstituencyModel mlaConstituencyModel) {
        this.mlaConstituencyModel = mlaConstituencyModel;
    }

    public List<UpcomingPartyModel> getUpcomingPartyModels() {
        return upcomingPartyModels;
    }

    public void setUpcomingPartyModels(List<UpcomingPartyModel> upcomingPartyModels) {
        this.upcomingPartyModels = upcomingPartyModels;
    }

    public ArrayList<NewsVideoModel> getNationalLiveVideos() {
        return nationalLiveVideos;
    }

    public void setNationalLiveVideos(ArrayList<NewsVideoModel> nationalLiveVideos) {
        this.nationalLiveVideos = nationalLiveVideos;
    }

    public UserProfileModel getUserProfileModel() {
        return userProfileModel;
    }

    public void setUserProfileModel(UserProfileModel userProfileModel) {
        this.userProfileModel = userProfileModel;
    }

    public ArrayList<AllLeaderModel> getUpComingLeaderModel() {
        return upComingLeaderModel;
    }

    public void setUpComingLeaderModel(ArrayList<AllLeaderModel> upComingLeaderModel) {
        this.upComingLeaderModel = upComingLeaderModel;
    }

    public String getTerms_conditions() {
        return terms_conditions;
    }

    public void setTerms_conditions(String terms_conditions) {
        this.terms_conditions = terms_conditions;
    }

    public ArrayList<AllLeaderModel> getFollowed_leader() {
        return followed_leader;
    }

    public void setFollowed_leader(ArrayList<AllLeaderModel> followed_leader) {
        this.followed_leader = followed_leader;
    }

    public List<ConstantModel> getPolitical_party() {
        return political_party;
    }

    public void setPolitical_party(List<ConstantModel> political_party) {
        this.political_party = political_party;
    }

    public List<ConstantModel> getFilter_post_list() {
        return filter_post_list;
    }

    public void setFilter_post_list(List<ConstantModel> filter_post_list) {
        this.filter_post_list = filter_post_list;
    }

    public ServerDetails getServerDetails() {
        return serverDetails;
    }

    public void setServerDetails(ServerDetails serverDetails) {
        this.serverDetails = serverDetails;
    }

/*    public ArrayList<News> getInternational_news() {
        return international_news;
    }

    public void setInternational_news(ArrayList<News> international_news) {
        this.international_news = international_news;
    }
  public ArrayList<AllLeaderModel> getPartyListModels() {
        return partyListModels;
    }

    public void setPartyListModels(ArrayList<AllLeaderModel> partyListModels) {
        this.partyListModels = partyListModels;
    }*/


    public ArrayList<News> getArticle() {
        return article;
    }

    public void setArticle(ArrayList<News> article) {
        this.article = article;
    }

    public List<Integer> getAdvertise_positions() {
        return advertise_positions;
    }

    public void setAdvertise_positions(List<Integer> advertise_positions) {
        this.advertise_positions = advertise_positions;
    }

    public ArrayList<NewsVideoModel> getNews_video() {
        return news_video;
    }

    public void setNews_video(ArrayList<NewsVideoModel> news_video) {
        this.news_video = news_video;
    }

    public Integer getVideoPosition() {
        return videoPosition;
    }

    public void setVideoPosition(Integer videoPosition) {
        this.videoPosition = videoPosition;
    }

    public ArrayList<ArticleModel> getArticleModels() {
        return articleSlider;
    }

    public void setArticleModels(ArrayList<ArticleModel> articleSlider) {
        this.articleSlider = articleSlider;
    }

    public ArrayList<AllLeaderModel> getNews_leader_model() {
        return news_leader_model;
    }

    public void setNews_leader_model(ArrayList<AllLeaderModel> news_leader_model) {
        this.news_leader_model = news_leader_model;
    }


    public List<HotTopicModel> getHotTopicModels() {
        return hotTopicModels;
    }

    public void setHotTopicModels(List<HotTopicModel> hotTopicModels) {
        this.hotTopicModels = hotTopicModels;
    }

    public List<VoiceAllModel> getUserFeeds() {
        return userFeeds;
    }

    public void setUserFeeds(List<VoiceAllModel> userFeeds) {
        this.userFeeds = userFeeds;
    }

    public UserProfileModel getProfileModel() {
        return profileModel;
    }

    public void setProfileModel(UserProfileModel profileModel) {
        this.profileModel = profileModel;
    }

    public void setKCount(String k_count) {
        this.k_count = k_count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<AllSurveyModel> getHomeNewsSurvey() {
        return homeNewsSurvey;
    }

    public void setHomeNewsSurvey(List<AllSurveyModel> homeNewsSurvey) {
        this.homeNewsSurvey = homeNewsSurvey;
    }

    public MoliticsVideoModel getMoliticsVideoModel() {
        return moliticsVideoModel;
    }

    public void setMoliticsVideoModel(MoliticsVideoModel moliticsVideoModel) {
        this.moliticsVideoModel = moliticsVideoModel;
    }

    public List<ReportReasonResponseModel> getReasonsData() {
        return reasonsData;
    }

    public void setReasonsData(List<ReportReasonResponseModel> reasonsData) {
        this.reasonsData = reasonsData;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<FeedModel> getFeedModels() {
        return feedModels;
    }

    public void setFeedModels(List<FeedModel> feedModels) {
        this.feedModels = feedModels;
    }

    public SuggestionModel getSuggestionModels() {
        return suggestionModel;
    }

    public void setSuggestionModels(SuggestionModel suggestionModel) {
        this.suggestionModel = suggestionModel;
    }
}