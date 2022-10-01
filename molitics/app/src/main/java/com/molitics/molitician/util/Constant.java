package com.molitics.molitician.util;

import com.molitics.molitician.model.CommonKeyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 9/30/16.
 */
public class Constant {

    public static final int ZERO = 0;
    public static final int STAGGERED_GRID_COUNT = 2;
    public static final String REMOVE_SPECIAL_CHA_FROM_STRING = "[^a-zA-Z0-9_-]";
    public static final String MOLITICS_PLUS_PLAY_STORE_LINK = "";
    public static final String PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=com.molitics.molitician&hl=en";
    public static final String SURVEY_FORMAT = "%s %02d:%02d:%02d";
    public static final String TWITTER_TOKEN_URL = "https://api.twitter.com/oauth2/token?grant_type=client_credentials";
    public static final String TWITTER_CANDIDATE_URL = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=";
    public static final String USER_VISIT_COUNT = "user_visit_count";
    public static final String DEVICE_ID = "device_id";
    public static final String VERSION_NAME = "version_name";
    public static final String CAMERA_DIR = "/molitics/";
    public static final String VIDEO_BROADCAST = "video_broadcast";
    public static final String OTP_BROADCAST = "otp_broadcast";
    public static final String COMPRESSED_VIDEO_EXTENSION = "static_rk.mp4";
    public static final String VIDEO_PLACEHOLDER = "-placeholder.jpg";
    public static final String MP4 = ".mp4";
    public static final String AWS_FILE_BASE_PATH = "images/user-feed/";
    public static String URL_REGEX = "((http:\\/\\/|https:\\/\\/)?(www.)?(([a-zA-Z0-9-]){2,}\\.){1,4}([a-zA-Z]){2,6}(\\/([a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)?)";
    public static final Long FILE_SIZE_BELOW = 150L;
    public static final int STORAGE_PERMISSION_CODE = 23;
    public static final long FILE_LIMIT_FOE_COMPRESS = 5L;////if limit 5mb dont compress video
    public static final int SUCCESS_RESULT = 200;
    public static final int SHARE_VOICE_TITLE_LENTH = 50;
    public static final String AWS_BUCKET = "molitics";
    public static final String IMAGE_AWS_BUCKET = "molitics";
    public static final String IMAGE = "image";
    public static final String VIDEO = "video";
    public static final Integer NEWS = 1;
    public static final Integer ARTICLE = 4;
    public static final String ID = "id";
    public static final String MOLITICS_BRANCHLINK = "https://molitics.app.link/5L5wlzQqxX";
    public static final String INTENT_FROM = "intent_from";
    public static final String MOLTICS_FOLDER_NAME = "molitics";
    public static final String SURVEY_PROGRESS = "progress";
    public static int createNormal = 0, createImage = 1, createVideo = 2, createLeader = 3;
    public static Long SEARCH_DELAY = 1000L;
    public static String MOLITICS = "Molitics";
    public static int MOLITICS_MEDIA = 1;
    public static final int GALLERY_IMAGE_REQUEST_CODE = 11;
    public static String USER_PROFILE_LINK = "https://www.molitics.in/userprofile?";

    public interface Action {
        int LIKE = 1;
        int DISLIKE = 2;
        int DELETE_LIKE = 3;
        int EDIT = 0;
        int DELETE = 1;
        int SHARE = 5;
        int FROM_USER_ARTICLE = 8;
        int FROM_ARTICLE = 9;
        int LOCAL = 3;
        int REPORT = 4;
    }
    public interface FeedAction {
        String LIKE = "1";
        String DISLIKE = "2";
    }

    public interface Language {
        int DEFAULT = 0;
        int HINDI = 1;
        int ENGLISH = 2;
        int OTHER = 100;
        int LANGUAGE_SELECTION = 110;
        String HINDI_STRING = "1";
        String ENGLISH_STRING = "2";
        String SYSTEM_HINDI = "hi";
        String SYSTEM_ENGLISH = "eng";
    }

/*
    public interface FiltersTitle {
        String STATE = "Select State";
        String DISTRICT = "Select District";
        String MLA = "Select M.L.A Constituency";
        String MP = "Select M.P Constituency";
        String PARTY = "Select Party";
        String POST = "Select Post";
    }
*/

    public interface PreferenceKey {
        String IS_FIRST_TIME = "is_first_time";
        String AUTH_KEY = "auth_key";
        String NEXT_STRING = "next";
        //  public static final String STATE_ID = "state_id";
        String DEFAULT_STATE_KEY = "default_state_key";
        String DEFAULT_DISTRICT_KEY = "default_district_key";
        String DEFAULT_MP_KEY = "default_mp_key";
        String DEFAULT_MLA_KEY = "default_mla_key";
        String DEFAULT_POLITICAL_PARTY_KEY = "default_political_party_key";
        String DEFAULT_STATE_VALUE = "default_state_value";
        String DEFAULT_DISTRICT_VALUE = "default_district_value";
        String DEFAULT_MP_VALUE = "default_mp_value";
        String DEFAULT_MLA_VALUE = "default_mla_value";
        String DEFAULT_POLITICAL_PARTY_VALUE = "default_political_party";
        String NOTIFICATION_ACTIVE = "notification_active";
        String DARK_MODE_ACTIVE = "dark_mode_active";
        String NOTIFICATION_COUNT = "notification_cont";
        String NOTIFICATION_SURVEY_COUNT = "notification_survey_count";
        String NOTIFICATION_CARTOON = "notification_cartoon";
        String VIDEO_POSITION = "video_position";
        String USER_ID = "user_id";
        String USER_NAME = "user_name";
        String USER_CONTACT = "user_contact";
        String VIDEO_MUTE = "video_mute";
        String VIDEO_URL_LIST = "video_url_list";
        String ISSUE_ID = "issue_id";
        String VIDEO_RETRY = "video_retry";
        String FCM_TOKEN = "fcm_token";
        String SYNC_TOKEN = "sync_token";
        String LANGUAGE = "language";
        String USER_IMAGE = "user_image";
        String API_KEY = "api_key";
        String IS_USER_AGREE_TERM_CONDITION = "is_user_agree_term_condition";
        //  public sttic final String LocalStateName = "local_state_name";
    }

    public interface NotificationPayLoad {

        String NOTIFICATION_NEWS_ID = "news_id";
        String NOTIFICATION_CANDIDATE_ID = "candidate_id";
        String NOTIFICATION_SURVEY_ID = "survey_id";
        String LEADER_NAME = "leader_name";
        String LEADER_ID = "leader_id";
        String VOICE_ID = "feed_id";
        String CARTOON_ID = "cartoon_id";
        String CARTOON_IMAGE = "notification_image";
    }

    public interface FilterKey {
        String MLA_CONSTITUENCY = "MLA Constituency";
        String MP_CONSTITUENCY = "MP Constituency";
        String STATE = "State";
        String PARTY = "Party";
        String DISTRICT = "District";
        String POST = "Post";
    }

    public interface IntentKey {
        String SURVEY_ID_INTENT = "survey_id";
        String NEWS_LIST = "news_list";
        String POSITION = "view_position";
        String TEMP_POSITION = "temp_position";
        String FROM = "from";
        String FILTERS = "filter_values";
        String CAN_REPLY = "can_reply";
        String PAST_STATE_ID = "state_id";
        String LEADER_PROFILE_ID = "leader_profile_id";
        String LEADER_PROFILE_NAME = "leader_profile_name";
        String PARTY_ID = "party_id";
        String ELECTION_ID = "election_id";
        String DECLARED_STATE_RESULT_FROM = "from";
        String PAST_MLA = "mla";
        String PAST_MP = "mp";
        String NEWS_ID = "news_id";
        String CHANNEL_ID = "channel_id";
        String SURVEY_POSITION = "survey_position";
        String LEADER_NAME = "name";
        String LEADER_POSITION = "position";
        String ITEM_POSITION = "position";
        String LEADER_LOCATION = "location";
        String LEADER_IMAGE = "image";
        String SOURCE_IMAGE = "source_image";
        String LEADER_PANEL = "panel";
        String LEADER_PARTY = "party";
        String LEADER_PART_LOGO = "party_logo";
        String LEADER_FOLLOW = "follow";
        String USER_FOLLOW = "user_follow";
        String LEADER_IS_FOLLOW = "is_follow";
        String NOTIFICATION = "notification";
        String LEADER_NEWS = "leader_news";
        String DISPLAY_TYPE = "display_type";
        String TYPE = "type";
        String NEWS_POSITION = "news_position";
        String ISSUE_ID = "issue_id";
        String ISSUE_MODEL = "issue_model";
        String IMAGE_LIST = "image_list";
        String IMAGE_POSITION = "image_position";
        String HOT_TOPIC_ID = "hot_topic_id";
        String HOT_TOPIC_STRING = "hot_topic_string";
        String ISSUE_POSITION = "issue_position";
        String USER_ID = "user_id";
        String USER_PROFILE_MODEL = "user_profile_model";
        String Hot_TOPIC_TEXT = "hot_topic_txt";
        String MESSAGE_COUNT = "message_count";
        String COMMENT_POSITION = "message_position";
        String VOICE_MODEL = "voice_model";
        String VOICE_ID = "voice_id";
        String IS_VERIFY = "is_verify";
        String SURVEY_DATA = "survey_data";
        String ARTICLE_ID = "article_id";
        String ARTICLE_TITLE = "article_title";
        String ARTICLE_CONTENT = "article_content";
        String ARTICLE_IMAGE = "article_image";
        String ACTION = "action";
        String UPVOTE = "upvote";
        String DOWNVOTE = "downvote";
        String LEADER_LIST = "leader_list";
        String CARTOON_LIST = "cartoon_list";
        String POST_IMAGE = "post_image";
        String VIDEO_URL = "video_url";
        String RESPONSE = "response";
        String OTP = "otp";
        String IS_VOICE_POSTED = "is_voice_posted";
        String NUMBER = "number";
        String URL = "url";
        String TOOL_BAR = "tool_bar";
        String NOTIFICATION_TITLE = "notification_title";
        String LINK = "link";
        String NEWS_DETAIL = "news_detail";
        String NOTIFICATION_TYPE = "notification_type";
        String EDIT = "edit";
        String NOTIFICATION_DATA = "notification_data";
        String NEWS_VIDEO = "news_video";
        String CANDIDATE_ID = "candidate_id";
        String TRENDING_ID = "trending_id";
        String SOURCE = "source";
        String REPORT_ID = "report_id";
        String SYNC_CONTACT = "sync_contact";

        String STATE_ID = "state_id";
        String AUTH_KEY = "auth_key";
        String HOME = "HOME";
    }

    public interface RequestTag {
        int STATE_LIST = 1;
        int REGISTER = 2;
        int LEADER_NEWS = 3;
        int ACCOUNT_REGISTER = 4;
        int LOCAL_NEWS = 5;
        int ALL_SURVEY = 6;
        int SUBMIT_LOCATION = 7;
        int RESEND_OTP = 8;
        int SUBMIT_OTP = 9;
        int FRAGMENT_LIST = 10;
        int DETAIL_NEWS = 11;
        int LIST_DETAIL_NEWS = 12;
        int SUBMIT_SURVEY = 13;
        int SURVEY_RESPONSE = 14;
        int SURVEY_DETAIL = 15;
        int TAKE_SURVEY = 16;
        int MAIN_SUBMIT_SURVEY = 17;
        int LOGINDIALOG = 18;

    }

    public interface Condition {
        int DEFAULT = 0;
        int VERTICAL_NEWS = 1;
        int YoutubeVideo = 2;

        int INTERNATIONAL_LEADER = 10;
        int SURVEY = 30;
        int ELECTION = 31;
        int HEASDER_NEWS = 3;
        int HEASDER_NEWS_YOUTUBE = 13;
        int LEADER_STATUS = 4;
        int LEADER_EVENT = 5;
        int ARTICLE = 6;
        int ADV = 7;
        int VIDEO = 8;
        int RELATED_NEWS = 11;
    }

    public interface NotificationType {
        int NEWS_NOTI = 2;
        int LEADER_DETAIL = 3;
        int SURVEY_NOTI = 5;
        int LEADER_NOTI = 7;
        int ARTICLE_NOTI = 13;
        int VOICE_NOTI = 12;
        int CARTOON_NOTI = 15;
    }

/*
    public interface tabs {
        int NATIONAL_TAB = 1;
        int SELECTED_STATE_TAB = 2;
        int LEADERS_TAB = 3;
        int SURVEY_TAB = 4;
        int ALL_STATES_TAB = 5;
        int UPCOMING_TAB = 6;
        int PROFILE_TAB = 7;
    }
*/

    public interface ActivityRequestKey {
        int PROFILE_KEY = 101;
        int NOTIFICATION_KEY = 102;
        int CARTOON_KEY = 103;
        int SURVEY_KEY = 104;
        int USER_PROFILE = 105;
    }

    public interface From {
        String FROM_PROFILE = "profile";
        String FROM_UPCOMING_PARTY = "upcoming_party";
        String FROM_DECLARED_STATE = "declared_state";
        String FROM_ARTICLE = "declared_article";
        String FROM_IMAGE_SHARE = "share_image_video";
        String COMMNET_ACTION = "comment_action";
        int FROM_CARICATURE = 10;
        int FROM_ELECTION_RESULT = 11;
        int FROM_NEARBY_USER = 12;
        int SEARCH_LEADER = 13;
        int VISIT_LEADER = 14;
        int ACTIVITY_SIGN_IN_FRAGMENT = 15;
        int ACTIVITY_SIGN_UP_FRAGMENT = 16;
        int ACTIVITY_CHANGE_PASSWORD_FRAGMENT = 17;
        String SIGN_IN_FRAGMENT = "ACCOUNT";
        String SIGN_IN = "SIGN_IN";
        String SIGN_UP_FRAGMENT = "REGISTER";
        String FORGET_PASSWORD = "FORGET_PASSWORD";
        String STATE_LOCATION = "state_location";
        String DISTRICT_LOCATION = "district_location";
    }

    public static List<CommonKeyModel> getStatusValue() {
        List<CommonKeyModel> commonKeyModels = new ArrayList<>();
        CommonKeyModel commonKeyModel1 = new CommonKeyModel("Edit", Action.EDIT);
        CommonKeyModel commonKeyModel2 = new CommonKeyModel("Delete", Action.DELETE);
        CommonKeyModel commonKeyModel3 = new CommonKeyModel("Share", Action.SHARE);
        commonKeyModels.add(commonKeyModel1);
        commonKeyModels.add(commonKeyModel2);
        commonKeyModels.add(commonKeyModel3);
        return commonKeyModels;
    }

    public static List<CommonKeyModel> getReportUser() {
        List<CommonKeyModel> commonKeyModels = new ArrayList<>();
        CommonKeyModel commonKeyModel1 = new CommonKeyModel("Report", Action.REPORT);
        commonKeyModels.add(commonKeyModel1);
        return commonKeyModels;
    }

    public interface ShareLinkTag {
        String LEADER = "leader";
        String VOICE = "user-feed/detail";
        String ARTICLE = "article";
        String SURVEY = "survey/detail";
        String NEWS = "news";
    }

    public interface ShareCampaign {
        String SURVEY = "survey";
        String VOICE = "voice";
    }

    public interface GoogleAnalyticsKey {
        String TUTORIAL_SCREEN = "Tutorial";// category
        String TUTORIAL_SKIP = "Skip";//action
        String LOGIN = "Login";// cat
        String LOGIN_CONTACT = "Login_Contact";//act
        String LOGIN_FACEBOOK = "Login_Facebok";//act
        String LOGIN_GMAIL = "Login_Gmail";//act
        String LOCATION_SELECTION = "Location Selection";/// cat
        String STATE = "State";//act
        String DISTRICT = "District";//act
        String MLA = "MLA";//act
        String MP = "MP";//action
        String HOME_SURVEY = "Home Survey"; // cat
        String HOME_SURVEY_CLICK = "Home Survey Click";//act
        String SURVEY_SUBMIT = "Survey Submit";//act
        String SURVEY_CHANGE_OPINION = "Survey Opinion";//act

        String STATE_NEWS = "State News";// cat
        String STATE_CHANGE = "State Change";//act
        String NATIONAL_NEWS = "National Tab";//cat
        String SINGLE_VIDEO = "Single Video";//act
        String HOME_YOUTUBE_VIDEO = "Home Youtube Video";//act
        String NATIONAL_NES_YOUTUBE_VIDEO = "National News Youtube Video";//act
        String HOME_LEADER = "Home Leader";//act
        String HOME_ARTICLE = "Home Article";//act
        String RYV = "RYV";//cat
        String COMMENT = "Comment";//act
        String TAG_LEADER = "Tag Leader";//act
        String VOICE_STATE_CHANGE = "Voice State Change";//act
        String VIDEO_PLAY = "Video Play";//act
        String LEADER_PROFILE = "Leader Profile";//cat
        String PROFILE_OPEN = "profile Open";//act
        String PROFILE_OPEN_ID = "profile Open From ID";//act
        String LEADER_KNOW_MORE = "Leader Know More";//act
        String FOLLOW_LEADER = "Follow Leader";//act
        String UNFOLLOW_LEADER = "UnFollow Leader";//act
        String LEADER_SHARE = "Leader Share";//act
        String LEADER_EVENT_SHARE = "Share Event";//act
        String NEWS_FEED_TAB = "News Feed Tab";//cat
        String LOCAL_NEWS_TAB = "Local News Tab";//cat
        String VOICE_TAB = "Voice Tab";//cat
        String LEADER_TAB = "Leader Tab";//cat
        String USER_PROFILE = "User Profile";//cat
        String CLICK = "Click";//cat
        String EDIT_CLICK = "Edit Click";//act
        String EDIT_SAVE = "Edit Save";//act
        String TRENDING_NEWS_CLICK = "Trending News Click";//act
        String HOME_CARICATURE_CLICK = "Home Caricature Click";//act
        String SIDE_DRAWER = "Side Drawer";//cat
        String SURVEY_CLICK = "Survey Click";//act
        String LEADER_FOLLOWING_CLICK = "Leader Following Click";//act
        String FOLLOWING_ISSUE_CLICK = "Following Issue Click";//act
        String ELECTION_RESULT_CLICK = "Election Result Click";//act
        String USER_LOCATION_SCREEN = "User Location Screen";//Cat
        String VISIT = "Visit";//act
    }

    public interface GoogleAnalyticsCustomDimension {
        String CUSTOM_USER_ID = "Custom User Id";
    }

    public interface VoiceFrom {
        String VOICE_FRAGMENT = "VoiceFragment";
        String VOICE_DETAIL = "VoiceDetail";
        String VOICE_ALL_VIDEO = "VoiceAllVideo";
    }

    public interface AppTheme {
        int DEFAULT_TEXT = 1;
        int WHITE_TEXT = 2;
    }

    public interface InviteFriend {
        int INVITE = 1;
        int INVITED = 2;
        int FOLLOW = 3;
        int FOLLOWING = 4;
    }

    public interface HomeBrowseItem {

        int NATIONAL_type = 1;

        int LOCAL_NEWS_type = 2;

        int LIVE_TV_type = 3;

        int CARICATURE_TYPE = 4;

        int ELECTION_RESULT_TYPE = 5;

        int NEARBY_USER_TYPE = 6;

        /*      String SURVEY = "Survey";
        int SURVEY_TYPE = 5;*/

 /*       String TRENDING_ISSUE = "Trending Issue";
        int TRENDING_ISSUE_TYPE = 6;*/
    }
}
