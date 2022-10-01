package com.molitics.molitician.ui.dashboard.leader;


import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.molitics.molitician.db.creator.voice.AllLeaderListCreator;
import com.molitics.molitician.model.News;

import java.io.Serializable;

/**
 * Created by rahul on 12/20/2016.
 */
@TypeConverters(AllLeaderListCreator.class)
public class AllLeaderModel implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("party_code")
    @Expose
    private String partyCode;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("k_followers")
    @Expose
    private String followers = "";
    @SerializedName("news_count")
    @Expose
    private Integer newsCount = 0;
    @SerializedName("weightage")
    @Expose
    private String weightage;
    @SerializedName("is_following")
    @Expose
    private Integer isFollowing = 0;

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    @SerializedName("is_like")
    @Expose
    private Integer isLike = 0;

    public Integer getIsDislike() {
        return isDislike;
    }

    public void setIsDislike(Integer isDislike) {
        this.isDislike = isDislike;
    }

    @SerializedName("is_dislike")
    @Expose
    private Integer isDislike = 0;

    @SerializedName("upcoming_event")
    @Expose
    private Integer upcoming_event = 0;

    @SerializedName("can_vote")
    @Expose
    private Integer canVote = 0;
    @SerializedName("is_voted")
    @Expose
    private Integer isVoted = 0;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("user_vote_action")
    @Expose
    private Integer user_vote_action = 0;

    @SerializedName("like_action")
    @Expose
    private Integer like_action = 0;

    @SerializedName("total_like")
    @Expose
    private Integer totalLike = 0;

    @SerializedName("upvote_count")
    @Expose
    private int like_count;

    @SerializedName("downvote_count")
    @Expose
    private int dislike_count;

    @SerializedName("total_dislike")
    @Expose
    private Integer totalDislike = 0;
    @SerializedName("like")
    @Expose
    private String candidateLike;

    @SerializedName("dislike")
    @Expose
    private String candidateDislike;

    /*  @SerializedName("panel_activated")
          @Expose

          private Integer panelActivated = 0;*/
    @SerializedName("party_logo")
    @Expose
    private String partyLogo;

    @SerializedName("is_verify")
    @Expose
    private int isVerify;

    @SerializedName("panel_activated")
    @Expose
    private int panel_activated;
    @SerializedName("display_feeds_tab")
    @Expose
    private int displayFeedsTab;
    @SerializedName("display_news_tab")
    @Expose
    private int displayNewsTab;

    private News singleNews;


    public int getDisplayFeedsTab() {
        return displayFeedsTab;
    }

    public void setDisplayFeedsTab(int displayFeedsTab) {
        this.displayFeedsTab = displayFeedsTab;
    }

    public int getDisplayNewsTab() {
        return displayNewsTab;
    }

    public void setDisplayNewsTab(int displayNewsTab) {
        this.displayNewsTab = displayNewsTab;
    }


    public Integer getUser_vote_action() {
        return user_vote_action;
    }

    public void setUser_vote_action(Integer user_vote_action) {
        this.user_vote_action = user_vote_action;
    }


    public boolean isSelectedLeader() {
        return selectedLeader;
    }

    private boolean selectedLeader;

    public boolean getSelectedLeader() {
        return selectedLeader;
    }

    public void setSelectedLeader(boolean selectedLeader) {
        this.selectedLeader = selectedLeader;
    }

    public int getPanel_activated() {
        return panel_activated;
    }

    public void setPanel_activated(int panel_activated) {
        this.panel_activated = panel_activated;
    }

    public News getSingleNews() {
        return singleNews;
    }

    public void setSingleNews(News singleNews) {
        this.singleNews = singleNews;
    }

    public int getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(int isVerify) {
        this.isVerify = isVerify;
    }

    public String getPartyLogo() {
        return partyLogo;
    }

    public void setPartyLogo(String partyLogo) {
        this.partyLogo = partyLogo;
    }

    public Integer getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(Integer isFollowing) {
        this.isFollowing = isFollowing;
    }

    public String getCandidateLike() {
        return candidateLike;
    }

    public void setCandidateLike(String candidateLike) {
        this.candidateLike = candidateLike;
    }

    public String getCandidateDislike() {
        return candidateDislike;
    }

    public void setCandidateDislike(String candidateDislike) {
        this.candidateDislike = candidateDislike;
    }

    /*
        public Integer getPanelActivated() {
            return panelActivated;
        }

        public void setPanelActivated(Integer panelActivated) {
            this.panelActivated = panelActivated;
        }
    */
    public Integer getLike_action() {
        return like_action;
    }

    public void setLike_action(Integer like_action) {
        this.like_action = like_action;
    }

    public String getLocation() {
        return location;
    }

    public Integer getUpcoming_event() {
        return upcoming_event;
    }

    public void setUpcoming_event(Integer upcoming_event) {
        this.upcoming_event = upcoming_event;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

/*
    public String getMp() {
        return mp;
    }

    public void setMp(String mp) {
        this.mp = mp;
    }

    public String getMla() {
        return mla;
    }

    public void setMla(String mla) {
        this.mla = mla;
    }
*/

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

 /*   public String getFollowerScore() {
        return followerScore;
    }

    public void setFollowerScore(String followerScore) {
        this.followerScore = followerScore;
    }*/

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public void setNewsCount(Integer newsCount) {
        this.newsCount = newsCount;
    }

    public int getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(int newsCount) {
        this.newsCount = newsCount;
    }

    public String getWeightage() {
        return weightage;
    }

    public void setWeightage(String weightage) {
        this.weightage = weightage;
    }

    public Integer getCanVote() {
        return canVote;
    }

    public void setCanVote(Integer canVote) {
        this.canVote = canVote;
    }

    public Integer getIsVoted() {
        return isVoted;
    }

    public void setIsVoted(Integer isVoted) {
        this.isVoted = isVoted;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getDislike_count() {
        return dislike_count;
    }

    public void setDislike_count(int dislike_count) {
        this.dislike_count = dislike_count;
    }

}
