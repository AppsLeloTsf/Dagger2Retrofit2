package com.ca_dreamers.cadreamers.models.my_orders.course_details.chapters.topics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
    @SerializedName("topic_id")
    @Expose
    private String topicId;
    @SerializedName("topic_name")
    @Expose
    private String topicName;
    @SerializedName("topic_tag_name")
    @Expose
    private String topicTagName;
    @SerializedName("contents")
    @Expose
    private List<Content> contents = null;

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicTagName() {
        return topicTagName;
    }

    public void setTopicTagName(String topicTagName) {
        this.topicTagName = topicTagName;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

}
