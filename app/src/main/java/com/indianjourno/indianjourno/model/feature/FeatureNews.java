package com.indianjourno.indianjourno.model.feature;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeatureNews {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("NewsFeature")
    @Expose
    private List<NewsFeature> newsFeatures = null;

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

    public List<NewsFeature> getNewsFeatures() {
        return newsFeatures;
    }

    public void setNewsFeatures(List<NewsFeature> newsFeatures) {
        this.newsFeatures = newsFeatures;
    }

}
