package com.ca_dreamers.cadreamers.models.course_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Chapter {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("chapter_name")
    @Expose
    private String chapterName;
    @SerializedName("topics")
    @Expose
    private List<Topic> topics = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
}
