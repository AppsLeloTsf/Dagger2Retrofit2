package com.molitics.molitician.model;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.survey.model.AllSurveyModel;
import com.molitics.molitician.ui.dashboard.voice.adapter.LeaderListAdapter;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class News implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("article_type")
    @Expose
    private Integer type = 0;

    @SerializedName("type")
    @Expose
    private Integer entityType = 0;

    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("video_link")
    @Expose
    private String videoLink;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("source_id")
    @Expose
    private Integer sourceId;

    @SerializedName("source_logo")
    @Expose
    private String sourceLogo;
    @SerializedName("survey_id")
    @Expose
    private Integer surveyId;
    @SerializedName("display_type")
    @Expose
    private Integer displayType;


    @SerializedName("img_source")
    @Expose
    private String imageSourceName;

    /*   @SerializedName("updated_at")
       @Expose
       private String updatedAt;*/
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("source_url")
    @Expose
    private String source_url;


    @SerializedName("survey")
    @Expose
    private AllSurveyModel survey = null;

    @SerializedName("candidate")
    @Expose
    private ArrayList<AllLeaderModel> leaderModels = null;

    @SerializedName("location")
    @Expose
    private String location = null;

    @SerializedName("tag_name")
    @Expose
    private String issueTagName = null;

    @SerializedName("tag")
    @Expose
    private int issueTag = 0;

    @SerializedName("author_name")
    @Expose
    private String author_name;
    @SerializedName("author_id")
    @Expose
    private Integer authorId;

    @SerializedName("feed_assets")
    @Expose
    private VoiceAllModel hotTopicIssue = null;

    @SerializedName("app_active")
    @Expose
    private int isActive = 1;
    @SerializedName("share_link")
    @Expose
    private String shareLink;

    @SerializedName("tagged_leaders")
    @Expose
    private List<AllLeaderModel> taggedLeader;

    @SerializedName("related_news")
    @Expose
    private ArrayList<News> relatedNews;

    public List<AllLeaderModel>
    getTaggedLeader() {
        return taggedLeader;
    }


    public void setTaggedLeader(List<AllLeaderModel> taggedLeader) {
        this.taggedLeader = taggedLeader;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getIssueTagName() {
        return issueTagName;
    }

    public void setIssueTagName(String issueTagName) {
        this.issueTagName = issueTagName;
    }

    public int getIssueTag() {
        return issueTag;
    }

    public void setIssueTag(int issueTag) {
        this.issueTag = issueTag;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The heading
     */
    public String getHeading() {
        return heading;
    }

    /**
     * @param heading The heading
     */
    public void setHeading(String heading) {
        this.heading = heading;
    }

    /**
     * @return The content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return The image_small_icon
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image The image_small_icon
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return The createdAt
     */
   /* public String getCreatedAt() {
        return createdAt;
    }

    *//**
     * @param createdAt The created_at
     *//*
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }*/

    /**
     * @return The source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return The sourceLogo
     */
    public String getSourceLogo() {
        return sourceLogo;
    }

    /**
     * @param sourceLogo The source_logo
     */
    public void setSourceLogo(String sourceLogo) {
        this.sourceLogo = sourceLogo;
    }

    /**
     * @return The surveyId
     */
    public Integer getSurveyId() {
        return surveyId;
    }

    /**
     * @param surveyId The survey_id
     */
    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    /**
     * @return The displayType
     */
    public Integer getDisplayType() {
        return displayType;
    }

    /**
     * @param displayType The display_type
     */
    public void setDisplayType(Integer displayType) {
        this.displayType = displayType;
    }

    /**
     * @return The updatedAt
     */
/*    public String getUpdatedAt() {
        return updatedAt;
    }

    *//**
     * @param updatedAt The updated_at
     *//*
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }*/

    /**
     * @return The time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time The time
     */
    public void setTime(String time) {
        this.time = time;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public AllSurveyModel getSurvey() {
        return survey;
    }

    public void setSurvey(AllSurveyModel survey) {
        this.survey = survey;
    }

    public ArrayList<AllLeaderModel> getLeaderModels() {
        return leaderModels;
    }

    public void setLeaderModels(ArrayList<AllLeaderModel> leaderModels) {
        this.leaderModels = leaderModels;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public VoiceAllModel getHotTopicIssue() {
        return hotTopicIssue;
    }

    public void setHotTopicIssue(VoiceAllModel hotTopicIssue) {
        this.hotTopicIssue = hotTopicIssue;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getImageSourceName() {
        return imageSourceName;
    }

    public void setImageSourceName(String imageSourceName) {
        this.imageSourceName = imageSourceName;
    }
    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public ArrayList<News> getRelatedNews() {
        return relatedNews;
    }

    public void setRelatedNews(ArrayList<News> relatedNews) {
        this.relatedNews = relatedNews;
    }
}