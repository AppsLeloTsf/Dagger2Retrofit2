package com.molitics.molitician.httpapi

import com.molitics.molitician.model.APIResponse
import com.molitics.molitician.model.DeviceRegistration
import com.molitics.molitician.model.LoginRequest
import com.molitics.molitician.model.RegisterDeviceModel
import com.molitics.molitician.ui.dashboard.changePassword.model.ChangePasswordRequestModel
import com.molitics.molitician.ui.dashboard.leader.model.LeadersIdModel
import com.molitics.molitician.ui.dashboard.leader.model.ProblemPostModel
import com.molitics.molitician.ui.dashboard.login.OtpModel
import com.molitics.molitician.ui.dashboard.register.model.SignInRequestModel
import com.molitics.molitician.ui.dashboard.register.model.User
import com.molitics.molitician.ui.dashboard.userProfile.model.EditUserProfileModel
import com.molitics.molitician.ui.dashboard.voice.model.GetVoiceVideoRequestModel
import com.molitics.molitician.ui.dashboard.voice.model.ImageDeleteModel
import com.molitics.molitician.ui.dashboard.voice.model.VoiceCreateModel
import com.molitics.molitician.ui.dashboard.voice.reportFeed.ReportRequestModel
import com.molitics.molitician.ui.dashboard.youtubeMoreDetail.viewModel.SendDataBody
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ApiService {
    @GET("news/all")
    fun getNews(
        @Query("total_count") pageNo: Int, @Query("adv") adv: Int?
    ): Call<APIResponse?>?

    @GET("news/local/{value}")
    fun getLocalNews(
        @Path("value") value: Int, @Query("total_count") pageNo: Int
    ): Call<APIResponse?>?

    @GET("news/article")
    fun getArticle(
        @Query("page") page_no: Int
    ): Call<APIResponse?>?

    @GET("account/article")
    fun getUserArticle(
        @Query("page") page_no: Int
    ): Call<APIResponse?>?

    @POST("account/device")
    fun registerDevice(
        @Body registerModelwq: RegisterDeviceModel?
    ): Call<APIResponse?>?

    @POST("account/register")
    fun userLogin(
        @Body loginRequest: LoginRequest?
    ): Call<APIResponse?>?

    @POST("account/register")
    fun userLoginRx(
        @Body loginRequest: LoginRequest?
    ): Single<APIResponse?>?

    /*  @GET("search/candidate")
    public Call<APIResponse> leadersList(
            @Query("page") int pageNo
    );*/
    @GET("survey/all")
    fun surveyList(
        @Query("page") pageNo: Int, @Query("state") state_id: Int?
    ): Call<APIResponse?>?

    /*    @GET("survey/detail/{survey_id}")
    public Call<APIResponse> surveyDetail(
            @Path("survey_id") int survey_id
    );*/
    @GET("v2/survey/result/{survey_id}")
    fun surveyResult(
        @Path("survey_id") survey_id: Int
    ): Call<APIResponse?>?

    @GET("v2/survey/detail/{survey_id}")
    fun getSurveyDetail(
        @Path("survey_id") survey_id: Int
    ): Call<APIResponse?>?

    @POST("v2/survey/response/{survey_id}")
    fun submitSurvey(
        @Path("survey_id") survey_id: Int
    ): Call<APIResponse?>?

    @get:GET("v2/static/locations")
    val stateList: Call<APIResponse?>?

    @get:GET("v2/account/tabs")
    val activeFragment: Call<APIResponse?>?

    @GET("v2/static/locations/{state_id}")
    fun getLocationFromList(
        @Path("state_id") state_id: Int
    ): Call<APIResponse?>?

    @POST("v2/account/addlocation")
    fun submitLocation(
        @Body submitLocationModel: DeviceRegistration?
    ): Call<APIResponse?>?

    @POST("v2/account/addlocation")
    fun submitLocationRx(
        @Body submitLocationModel: DeviceRegistration?
    ): Single<APIResponse?>?

    @POST("v2/account/{otp}")
    fun otpSubmit(
        @Path("otp") Url: String?, @Body otpModel: OtpModel?
    ): Call<APIResponse?>?

    @GET("v2/news/news/{id}")
    fun getNewsDetail(
        @Path("id") id: Int, @Query("display_type") display_type: Int
    ): Call<APIResponse?>?

    @POST("v2/survey/response/{id}")
    fun submitSurvey(
        @Path("id") id: Int, @Query("response") response: Int
    ): Call<APIResponse?>?

    @GET("v2/survey/result/{id}")
    fun getSurveyResult(
        @Path("id") id: Int
    ): Call<APIResponse?>?

    @GET("v2/search/filters/{stateId}")
    fun getExtraLocationFilter(@Path("stateId") stateId: Int): Call<APIResponse?>?

    @POST("v2/search/candidate")
    fun getFilterCandidates(
        @Body leaders_id: LeadersIdModel?
    ): Call<APIResponse?>?

    // user-feed/candidate-by-name/{candidate name}
    @GET("v2/user-feed/candidate-by-name/{candidate_name}")
    fun getSearchCandidate(
        @Query("page") page: Int, @Path("candidate_name") name: String?
    ): Call<APIResponse?>?

    @GET("v2/candidate/profile/{id}")
    fun getProfileData(
        @Path("id") id: Int
    ): Call<APIResponse?>?

    @GET("v2/news/candidate-news/{id}/{page_no}")
    fun getLeaderNews(
        @Path("page_no") page_no: Int, @Path("id") id: Int
    ): Call<APIResponse?>?

    @get:POST("v2/oauth2/token?grant_type=client_credentials")
    val twitterToken: Call<APIResponse?>?

    @POST("v2/candidate/follow/{id}")
    fun followCandidate(
        @Path("id") id: Int
    ): Call<APIResponse?>?

    @POST("v2/candidate/like/{id}")
    fun likeCandidate(@Path("id") id: Int): Call<APIResponse?>?

    @POST("v2/candidate/dislike/{id}")
    fun dislikeCandidate(@Path("id") id: Int): Call<APIResponse?>?

    @DELETE("v2/candidate/like/{id}")
    fun deleteCandidateLike(@Path("id") id: Int): Call<APIResponse?>?

    @DELETE("v2/candidate/follow/{id}")
    fun unFollowCandidate(
        @Path("id") id: Int
    ): Call<APIResponse?>?

    @GET("v2/head-quarter/past-elections")
    fun getPastElection(
        @Query("count") count: Int, @Query("query") stateName: String?
    ): Call<APIResponse?>?

    @GET("v2/head-quarter/upcoming-elections")
    fun getUpComingElection(
        @Query("page") page_no: Int
    ): Call<APIResponse?>?

    @GET("v2/head-quarter/election-result/{state_id}")
    fun getPastState(
        @Path("state_id") state_id: Int
    ): Call<APIResponse?>?

    @GET("v2/head-quarter/constitutencies/{state_id}")
    fun getConstituencyList(
        @Path("state_id") state_id: Int
    ): Call<APIResponse?>?

    @GET("v2/head-quarter/party/{election_id}/{party_id}")
    fun getPartyList(
        @Path("election_id") election_id: Int,
        @Path("party_id") party_id: Int,
        @Query("page") page_no: Int
    ): Call<APIResponse?>?

    @GET("v2/constitutency/about")
    fun getMlaPastElection(
        @Query("state") state_id: Int, @Query("mp") mp_id: Int, @Query("mla") mla_id: Int
    ): Call<APIResponse?>?

    @GET("v2/account/user-profile/{User_ID}")
    fun getUserProfileData(
        @Path("User_ID") user_id: Int,
        @Query("total_count") count: Int,
        @Query("device_id") device: String?
    ): Call<APIResponse?>?

    @get:GET("v2/account/profile")
    val profile: Call<APIResponse?>?

    @GET("v2/head-quarter/elections/{election_id}")
    fun getUpcomingPartyList(
        @Path("election_id") election_id: Int
    ): Call<APIResponse?>?


    @Multipart
    @POST("v2/account/image")
    fun submitProfileImage(
        @Part image: MultipartBody.Part?
    ): Call<APIResponse?>?

    @DELETE("v2/account/image")
    fun deleteProfileImage(): Call<APIResponse?>?

    @GET("v2/head-quarter/upcoming/{election_id}")
    fun getUpComingConstituency(
        @Path("election_id") election_id: Int, @Query("mla") mla_id: Int
    ): Call<APIResponse?>?

    @POST("v2/account/contactchange")
    fun submitEditContact(
        @Query("contact") contact: String?
    ): Call<APIResponse?>?

    @POST("v2/account/contactchangeotp")
    fun submitEditOtp(
        @Query("otp") otp: String?
    ): Call<APIResponse?>?

    @POST("v2/candidate/issue/{candidate_id}")
    fun submitProblem(
        @Path("candidate_id") candidate_id: Int, @Body problemPostModel: ProblemPostModel?
    ): Call<APIResponse?>?

    @get:GET("v2/static/terms-conditions")
    val termCondition: Call<APIResponse?>?

    @GET("v2/account/following")
    fun getFollowingLeader(
        @Query("page") page_no: Int
    ): Call<APIResponse?>?

    @GET("v2/candidate/event-tab/{CandidateId}")
    fun submitEventClick(@Path("CandidateId") candidateId: Int): Call<APIResponse?>?

    @GET("v2/static/expiry-check/android/{version}")
    fun checkUpdate(@Path("version") version_code: Int): Call<APIResponse?>?

    @get:GET("v2/search/national-positions")
    val filterPost: Call<APIResponse?>?

    @POST("v2/candidate/{candidate_id}/{action}")
    fun onLeaderAction(
        @Path("candidate_id") candidate_id: Int,
        @Path("action") action: Int
    ): Call<APIResponse?>?

    @DELETE("v2/candidate/like/{candidate_id}")
    fun onDeleteLeaderAction(@Path("candidate_id") candidate_id: Int): Call<APIResponse?>?

    @DELETE("v2/news/{type_url}/{news_id}/{candidate_id}")
    fun onDeleteNewsLeaderAction(
        @Path("type_url") type_url: String?,
        @Path("news_id") news_id: Int,
        @Path("candidate_id") candidate_id: Int
    ): Call<APIResponse?>?

    @GET("v2/news/molitics-inhouse-video")
    fun getAllVideo(@Query("total_count") total_count: Int): Call<APIResponse?>?

    @GET("v2/user-feed/feed-new")
    fun getAllVoice(
        @Query("get_trending") getTrending: Int, @Query("state_count") state_count: Int,
        @Query("page") page: Int, @Query("device_id") device_id: String?
    ): Call<APIResponse?>?

    @GET("v2/user-feed/feed-new")
    fun getTypeAllVoice(
        @Query("get_trending") isGetTrending: Int,
        @Query("state") state: Int,
        @Query("page") page: Int, @Query("device_id") device_id: String?
    ): Call<APIResponse?>?

    @GET("p8/user-feed/list")
    fun getHomeFeedList(
        @Query("page") page: Int,
        @Query("state") state: Int,
    ): Call<APIResponse?>?

    @POST("v2/search/candidate")
    fun getSearchCandidatesByName(@Query("name") name: String?): Call<APIResponse?>?

    @POST("v2/user-feed/feed")
    fun createVoice(@Body voiceCreateModel: VoiceCreateModel?): Call<APIResponse?>?

    @POST("v2/user-feed/feed")
    fun createRxVoice(@Body voiceCreateModel: VoiceCreateModel?): Observable<APIResponse?>?

    @PUT("v2/user-feed/feed/{issue_id}")
    fun editRxVoice(
        @Path("issue_id") issue_id: Int,
        @Body voiceCreateModel: VoiceCreateModel?
    ): Observable<APIResponse?>?

    @PUT("v2/user-feed/delete-image/{issue_id}")
    fun deleteIssueImageRx(
        @Path("issue_id") issue_id: Int,
        @Body imageDeleteModel: ImageDeleteModel?
    ): Observable<APIResponse?>?

    @PUT("v2/user-feed/feed/{issue_id}")
    fun editVoice(
        @Path("issue_id") issue_id: Int,
        @Body voiceCreateModel: VoiceCreateModel?
    ): Call<APIResponse?>?

    @Multipart
    @POST("v2/user-feed/add-image/{voice_id}")
    fun createdVoiceImageUpload(
        @Path("voice_id") voice_id: Int,
        @Part image: Array<MultipartBody.Part?>?
    ): Call<APIResponse?>?

    @POST("v2/user-feed/action/{issue_id}")
    fun likeDislikeIssue(
        @Path("issue_id") issue_id: Int,
        @Query("action") action: Int
    ): Call<APIResponse?>?

    @POST("v2/user-feed/follow/{issue_id}")
    fun feedFollow(@Path("issue_id") issue_id: Int): Call<APIResponse?>?

    @DELETE("v2/user-feed/follow/{issue_id}")
    fun deleteFollow(@Path("issue_id") issue_id: Int): Call<APIResponse?>?

    @GET("v2/user-feed/comment/{issue_id}")
    fun getAllComment(
        @Path("issue_id") issue_id: Int,
        @Query("total_count") total_count: Int
    ): Call<APIResponse?>?

    @PUT("v2/user-feed/comment/{issue_id}/{comment_id}")
    fun editComment(
        @Path("issue_id") issue_id: Int, @Path("comment_id") comment_id: Int,
        @Query("comment") comment: String?
    ): Call<APIResponse?>?

    @POST("v2/user-feed/comment/{issue_id}")
    fun issueComment(
        @Path("issue_id") issue_id: Int,
        @Query("comment") comment: String?
    ): Call<APIResponse?>?

    @PUT("v2/user-feed/delete-image/{issue_id}")
    fun deleteIssueImage(
        @Path("issue_id") issue_id: Int,
        @Body imageDeleteModel: ImageDeleteModel?
    ): Call<APIResponse?>?

    @DELETE("v2/user-feed/delete-feed/{issue_id}")
    fun deleteIssue(@Path("issue_id") issue_id: Int): Call<APIResponse?>?

    @GET("v2/news/trending-topics")
    fun getHotTopic(@Query("count") count: Int): Call<APIResponse?>?

    @GET("v2/news/trending-news/{Topic_ID}")
    fun getHotTopicDetail(
        @Path("Topic_ID") topic_id: Int,
        @Query("count") count: Int
    ): Call<APIResponse?>?

    @GET("v2/account/profile-image/{userId}")
    fun getUserImage(@Path("userId") user_id: Int): Call<APIResponse?>?

    @GET("v2/user-feed/my-follow-feeds")
    fun getIssueFollowing(@Query("count") count: Int): Call<APIResponse?>?

    @POST("v2/account/follow/{userId}")
    fun getUserFollow(@Path("userId") user_id: Int): Call<APIResponse?>?

    @DELETE("v2/account/follow/{userId}")
    fun getUserUnFollow(@Path("userId") user_id: Int): Call<APIResponse?>?

    @GET("v2/candidate/candidate-feed/{candidate_id}")
    fun getLeaderFeeds(
        @Path("candidate_id") candidate_id: Int,
        @Query("total_count") total_count: Int, @Query("device_id") deviceId: String?
    ): Call<APIResponse?>?

    @GET("v2/user-feed/feed/{FeedID}")
    fun getVoiceDetails(@Path("FeedID") issue_id: Int): Call<APIResponse?>?

    @GET("v2/cartoon/all")
    fun getAllCartoon(@Query("total_count") total_count: Int): Call<APIResponse?>?

    @DELETE("/user-feed/delete-comment/{CommentId}")
    fun deleteComment(@Path("CommentId") comment_id: Int): Call<APIResponse?>?

    @GET("user-feed/like-list/{FeedId}")
    fun getFeedActionPeopleList(
        @Path("FeedId") feed_id: Int,
        @Query("action") action: Int,
        @Query("total_count") total_count: Int
    ): Call<APIResponse?>?

    @GET("account/users-following")
    fun getUserFollowingList(
        @Query("id") user_id: Int,
        @Query("action") action: Int,
        @Query("total_count") total_count: Int
    ): Call<APIResponse?>?

    @Multipart
    @POST("account/article")
    fun addArticle(
        @Part image: MultipartBody.Part?,
        @Query("heading") title: String?,
        @Query("content") description: String?
    ): Call<APIResponse?>?

    @Multipart
    @POST("account/article/{id}")
    fun editArticle(
        @Part image: MultipartBody.Part?,
        @Path("id") id: Int,
        @Query("heading") title: String?,
        @Query("content") description: String?
    ): Call<APIResponse?>?

    @POST("account/article/{id}")
    fun editArticleNonImage(
        @Path("id") id: Int,
        @Query("heading") title: String?,
        @Query("content") description: String?
    ): Call<APIResponse?>?

    @DELETE("account/article/{id}")
    fun deleteUserArticle(@Path("id") id: Int): Call<APIResponse?>?

    @Multipart
    @POST("account/upload-contacts")
    fun exportContact(@Part file: MultipartBody.Part?): Call<APIResponse?>?

    @Multipart
    @POST("account/upload-contacts")
    fun exportContactRx(@Part file: MultipartBody.Part?): Observable<APIResponse?>?

    @Streaming
    @GET
    fun downloadFileWithDynamicUrlAsync(@Url fileUrl: String?): Call<ResponseBody?>?

    @POST("v2/candidate/seek-news/{candidate_id}")
    fun seekNews(@Path("candidate_id") candidate_id: Int): Call<APIResponse?>?

    @GET("v2/invite/contact-list")
    fun getContacts(
        @Query("page") pageNo: Int,
        @Query("search") search: String?
    ): Single<APIResponse?>?

    @POST("v2/invite/send-invite")
    fun sendInvite(
        @Query("contact1") contact: String?,
        @Query("user_id") userId: Int,
        @Query("isInvite") isInvite: Int
    ): Single<APIResponse?>?

    @GET("v2/invite/near-by-user")
    fun getNearByUser(
        @Query("page") page: Int,
        @Query("search") search: String?,
        @Query("type") type: Int
    ): Single<APIResponse?>?

    @GET("v2/head-quarter/videos-by-language")
    fun getVideoByLanguage(
        @Query("language") language: Int,
        @Query("page") page: Int,
        @Query("display_type") displayType: Int,
        @Query("is_not_live") isNotAlive: Int
    ): Call<APIResponse?>?

    @POST("v2/candidate/follow/{id}")
    fun followUser(@Path("id") id: Int): Single<APIResponse?>?

    @POST("v2/videos")
    fun getYoutubeVideos(
        @Query("page") page: Int,
        @Body sendDataBody: SendDataBody?
    ): Single<APIResponse?>?

    @GET("v2/news/revised-videos")
    fun getLiveVideos(
        @Query("page") page: Int,
        @Query("state_id") stateId: Int
    ): Single<APIResponse?>?

    @GET("v2/static/news-source")
    fun getChannelList(
        @Query("page") pageNo: Int,
        @Query("query") searchName: String?
    ): Single<APIResponse?>?

    @GET("v2/static/news-source/{id}")
    fun getChannelNews(
        @Path("id") channelId: Int,
        @Query("page") page: Int,
        @Query("type") type: String?
    ): Single<APIResponse?>?

    @POST("v2/static/channel-follow/{id}")
    fun followChannel(
        @Path("id") channelId: Int,
        @Query("follow") action: Int
    ): Single<APIResponse?>?

    @get:GET("v2/static/home-tab-list")
    val browseList: Single<APIResponse?>?

    @POST("v2/user-feed/video-feed")
    fun getVoiceVideoFeed(
        @Query("page") page: Int,
        @Body requestBody: GetVoiceVideoRequestModel?
    ): Single<APIResponse?>?

    @POST("v2/account/follow/{userId}")
    fun getUserFollowRx(@Path("userId") user_id: Int): Single<APIResponse?>?

    @DELETE("v2/account/follow/{userId}")
    fun getUserUnFollowRx(@Path("userId") user_id: Int): Single<APIResponse?>?

    @GET("v2/static/expiry-check/android/{version}")
    suspend fun checkAppUpdate(@Path("version") currentVersion: Int): APIResponse

    @GET("v2/static/locations")
    suspend fun getStateList(): APIResponse

    @GET("v2/static/locations/{state_id}")
    suspend fun getDistrictList(@Path("state_id") state_id: Int?): APIResponse

    @POST("v3/account/signin")
    fun postSignIn(
        @Header("AuthKey") authKey: String?,
        @Body userDetails: SignInRequestModel
    ): Call<MainResponseModel>

    @POST("v3/account/forgot/password")
    fun forgotPassword(@Body userDetails: User): Call<MainResponseModel>

    @POST("v3/account/verifyotp")
    fun verifyOtp(
        @Header("AuthKey") authKey: String?,
        @Body userDetails: SignInRequestModel
    ): Call<MainResponseModel>

    @POST("v3/account/create")
    fun createAccount(
        @Header("AuthKey") authKey: String?,
        @Body userDetails: SignInRequestModel
    ): Call<MainResponseModel>

    @POST("v3/account/sendotp")
    fun sendOtp(@Body userDetails: SignInRequestModel): Call<MainResponseModel>

    @POST("v3/account/checkusername")
    fun checkUserName(
        @Header("AuthKey") authKey: String?,
        @Body userDetails: User
    ): Call<MainResponseModel>

    @POST("v2/account/profile")
    fun submitEditProfile(
        @Body editUserProfileModel: EditUserProfileModel?
    ): Call<APIResponse>

    @POST("v3/account/user-verification-request")
    @Multipart
    fun userVerificationForm(
        @Part("doc_number") doc_number: RequestBody,
        @Part("is_indian") is_indian: RequestBody,
        @Part("profession") profession: RequestBody,
        @Part("doc_type") doc_type: RequestBody,
        @Part image: MultipartBody.Part?
    ): Call<MainResponseModel>

    @POST("v3/account/change/password")
    fun changePassword(@Body password: ChangePasswordRequestModel): Call<MainResponseModel>

    @POST("/v3/account/reset/password")
    fun forgotRestPassword(@Header("AuthKey") authKey: String?,@Body user: SignInRequestModel): Call<MainResponseModel>

    @GET("report/reason")
    suspend fun reportList(@Query("reason_for") reason_for: String): APIResponse

    @POST("report/feed")
    suspend fun reportFeed(@Body reportFeed: ReportRequestModel): APIResponse

    @POST("report/user")
    suspend fun reportUser(@Body reportFeed: ReportRequestModel): APIResponse

}