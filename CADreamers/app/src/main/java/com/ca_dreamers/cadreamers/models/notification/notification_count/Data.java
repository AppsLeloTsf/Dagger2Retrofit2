package com.ca_dreamers.cadreamers.models.notification.notification_count;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("total_notification")
    @Expose
    private String totalNotification;

    public String getTotalNotification() {
        return totalNotification;
    }

    public void setTotalNotification(String totalNotification) {
        this.totalNotification = totalNotification;
    }
}

