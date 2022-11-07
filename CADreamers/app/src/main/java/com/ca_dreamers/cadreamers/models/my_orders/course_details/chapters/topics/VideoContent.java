package com.ca_dreamers.cadreamers.models.my_orders.course_details.chapters.topics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VideoContent implements Serializable {
    @SerializedName("vid")
    @Expose
    private String vid;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("embed")
    @Expose
    private String embed;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("full_url")
    @Expose
    private String fullUrl;
    @SerializedName("video_play_duraion")
    @Expose
    private String videoPlayDuraion;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEmbed() {
        return embed;
    }

    public void setEmbed(String embed) {
        this.embed = embed;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getVideoPlayDuraion() {
        return videoPlayDuraion;
    }

    public void setVideoPlayDuraion(String videoPlayDuraion) {
        this.videoPlayDuraion = videoPlayDuraion;
    }
}
