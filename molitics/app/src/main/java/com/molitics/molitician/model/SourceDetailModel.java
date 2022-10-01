package com.molitics.molitician.model;

import com.google.gson.annotations.SerializedName;

public class SourceDetailModel {

    @SerializedName("status")
    int status;
    @SerializedName("follow")
    boolean follow;

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
