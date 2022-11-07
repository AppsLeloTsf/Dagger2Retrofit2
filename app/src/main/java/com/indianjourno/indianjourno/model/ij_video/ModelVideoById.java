package com.indianjourno.indianjourno.model.ij_video;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelVideoById {
    @SerializedName("video_id")
    @Expose
    private String videoId;
    @SerializedName("video_title")
    @Expose
    private String videoTitle;
    @SerializedName("video_iframe")
    @Expose
    private String videoIframe;
    @SerializedName("video_link")
    @Expose
    private String videoLink;
    @SerializedName("video_thumbnail")
    @Expose
    private String videoThumbnail;
    @SerializedName("v_category_id")
    @Expose
    private String vCategoryId;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoIframe() {
        return videoIframe;
    }

    public void setVideoIframe(String videoIframe) {
        this.videoIframe = videoIframe;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

    public String getvCategoryId() {
        return vCategoryId;
    }

    public void setvCategoryId(String vCategoryId) {
        this.vCategoryId = vCategoryId;
    }

}
