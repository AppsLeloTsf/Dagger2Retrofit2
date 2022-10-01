package com.indianjourno.indianjourno.model.videos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.indianjourno.indianjourno.utils.DateUtils;

public class Video {
    @SerializedName("v_id")
    @Expose
    private String vId;
    @SerializedName("v_category")
    @Expose
    private String vCategory;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("v_code")
    @Expose
    private String vCode;
    @SerializedName("v_url")
    @Expose
    private String vUrl;
    @SerializedName("video_image")
    @Expose
    private String videoImage;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("reporter_id")
    @Expose
    private String reporterId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("video_loc")
    @Expose
    private String videoLoc;
    @SerializedName("videos_state")
    @Expose
    private String videosState;
    @SerializedName("videos_country")
    @Expose
    private String videosCountry;
    @SerializedName("schedule")
    @Expose
    private String schedule;

    public String getVId() {
        return vId;
    }

    public void setVId(String vId) {
        this.vId = vId;
    }

    public String getVCategory() {
        return vCategory;
    }

    public void setVCategory(String vCategory) {
        this.vCategory = vCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVCode() {
        return vCode;
    }

    public void setVCode(String vCode) {
        this.vCode = vCode;
    }

    public String getVUrl() {
        return vUrl;
    }

    public void setVUrl(String vUrl) {
        this.vUrl = vUrl;
    }

    public String getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getReporterId() {
        return reporterId;
    }

    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return DateUtils.yyyy_MM_dd_HH_mm_ss(createdDate);
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getVideoLoc() {
        return videoLoc;
    }

    public void setVideoLoc(String videoLoc) {
        this.videoLoc = videoLoc;
    }

    public String getVideosState() {
        return videosState;
    }

    public void setVideosState(String videosState) {
        this.videosState = videosState;
    }

    public String getVideosCountry() {
        return videosCountry;
    }

    public void setVideosCountry(String videosCountry) {
        this.videosCountry = videosCountry;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
