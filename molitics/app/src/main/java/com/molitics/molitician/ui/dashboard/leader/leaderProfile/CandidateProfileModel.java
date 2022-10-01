package com.molitics.molitician.ui.dashboard.leader.leaderProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;

import java.util.List;

/**
 * Created by rahul on 1/4/2017.
 */

public class CandidateProfileModel {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("education")
    @Expose
    private List<String> education = null;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("party")
    @Expose
    private String party;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("mp_constituency")
    @Expose
    private String mpConstituency;
    @SerializedName("mla_constituency")
    @Expose
    private String mlaConstituency;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("posts_held")
    @Expose
    private List<String> postsHeld = null;
    @SerializedName("fb")
    @Expose
    private String fb;
    @SerializedName("twitter")
    @Expose
    private String twitter;
    @SerializedName("party_logo")
    @Expose
    private String partyLogo;
    @SerializedName("is_following")
    @Expose
    private Integer isFollowing;


    @SerializedName("k_followers")
    @Expose
    private String followers;

    @SerializedName("events")
    @Expose
    private List<Event> events = null;
    @SerializedName("newsCount")
    @Expose
    private Integer newsCount;

    @SerializedName("panel_activated")

    @Expose
    private Integer panel_activated;

    @SerializedName("status")
    @Expose
    private String candidate_status;

    @SerializedName("status_pic")
    @Expose
    private String status_url = "";

    @SerializedName("feeds")
    @Expose
    private List<VoiceAllModel> voiceAllModel = null;

    @SerializedName("is_verify")
    @Expose
    private int isVerify;

    @SerializedName("upvote_count")
    @Expose
    private Integer like_count;

    @SerializedName("downvote_count")
    @Expose
    private Integer dislike_count;

    @SerializedName("user_vote_action")
    @Expose
    private Integer like_action = 0;

    @SerializedName("location")
    @Expose
    private String leaderLocation;

    public String getLeaderLocation() {
        return leaderLocation;
    }

    public void setLeaderLocation(String leaderLocation) {
        this.leaderLocation = leaderLocation;
    }

    public Integer getLike_action() {
        return like_action;
    }

    public void setLike_action(Integer like_action) {
        this.like_action = like_action;
    }

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

    public List<VoiceAllModel> getVoiceAllModel() {
        return voiceAllModel;
    }

    public void setVoiceAllModel(List<VoiceAllModel> voiceAllModel) {
        this.voiceAllModel = voiceAllModel;
    }

    public String getStatus_url() {
        return status_url;
    }

    public void setStatus_url(String status_url) {
        this.status_url = status_url;
    }


    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
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

    public List<String> getEducation() {
        return education;
    }

    public void setEducation(List<String> education) {
        this.education = education;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getMpConstituency() {
        return mpConstituency;
    }

    public void setMpConstituency(String mpConstituency) {
        this.mpConstituency = mpConstituency;
    }

    public String getMlaConstituency() {
        return mlaConstituency;
    }

    public void setMlaConstituency(String mlaConstituency) {
        this.mlaConstituency = mlaConstituency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public List<String> getPostsHeld() {
        return postsHeld;
    }

    public void setPostsHeld(List<String> postsHeld) {
        this.postsHeld = postsHeld;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Integer getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(Integer newsCount) {
        this.newsCount = newsCount;
    }


    public Integer getPanel_activated() {
        return panel_activated;
    }

    public void setPanel_activated(Integer panel_activated) {
        this.panel_activated = panel_activated;
    }

    public String getCandidate_status() {
        return candidate_status;
    }

    public void setCandidate_status(String candidate_status) {
        this.candidate_status = candidate_status;
    }

    public int getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(int isVerify) {
        this.isVerify = isVerify;
    }

}
