package com.ca_dreamers.cadreamers.models.notification;

import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.ca_dreamers.cadreamers.utils.DateUtils.convertFormatDateTime;


public class Datum {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("notification")
    @Expose
    private String notification;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getCreatedAt() {
        return convertFormatDateTime(createdAt);
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
