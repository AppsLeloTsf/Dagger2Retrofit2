package com.indianjourno.indianjourno.model.sliders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SliderMessage {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("VideoCategory")
    @Expose
    private List<VideoCategory> videoCategory = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<VideoCategory> getVideoCategory() {
        return videoCategory;
    }

    public void setVideoCategory(List<VideoCategory> videoCategory) {
        this.videoCategory = videoCategory;
    }
}