package com.molitics.molitician.ui.dashboard.hotTopics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul
 * on 27/11/17.
 */

public class HotTopicModel {

    @SerializedName("tag")
    @Expose
    String tag = null;

    @SerializedName("id")
    @Expose
    Integer id;

    @SerializedName("source")
    String source;

    @SerializedName("source_logo")
    String sourceLogo;

    @SerializedName("follow")
    boolean follow;

    @SerializedName("news_count")
    String newsCount;

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public String getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(String newsCount) {
        this.newsCount = newsCount;
    }


    public String getSourceLogo() {
        return sourceLogo;
    }

    public void setSourceLogo(String sourceLogo) {
        this.sourceLogo = sourceLogo;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
