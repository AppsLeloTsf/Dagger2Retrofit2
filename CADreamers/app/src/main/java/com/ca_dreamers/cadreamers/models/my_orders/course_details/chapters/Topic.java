package com.ca_dreamers.cadreamers.models.my_orders.course_details.chapters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Topic {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("topic_name")
    @Expose
    private String topicName;
    @SerializedName("topic_tag_name")
    @Expose
    private String topicTagName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

}
