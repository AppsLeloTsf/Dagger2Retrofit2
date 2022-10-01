package com.molitics.molitician.ui.dashboard.voice.model;

import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.molitics.molitician.db.creator.feed.FeedImageCreator;

import java.io.Serializable;

/**
 * Created by Rakesh Kumar on 17/09/2021.
 */
@TypeConverters(FeedImageCreator.class)
public class FeedImageModel implements Serializable {
    @SerializedName("media_key")
    @Expose
    private String mediaKey;

    @SerializedName("media_type")
    @Expose
    private String mediaType;

    @SerializedName("current_dimention")
    @Expose
    private String currentDimension;

    @SerializedName("real_dimention")
    @Expose
    private String realDimension;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("preload_url")
    @Expose
    private String preloadUrl;

    public FeedImageModel() {
    }

    public String getMediaKey() {
        return mediaKey;
    }

    public void setMediaKey(String mediaKey) {
        this.mediaKey = mediaKey;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getCurrentDimension() {
        return currentDimension;
    }

    public void setCurrentDimension(String currentDimension) {
        this.currentDimension = currentDimension;
    }
    public String getRealDimension() {
        return realDimension;
    }

    public void setRealDimension(String realDimension) {
        this.realDimension = realDimension;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPreloadUrl() {
        return preloadUrl;
    }

    public void setPreloadUrl(String preloadUrl) {
        this.preloadUrl = preloadUrl;
    }
}