package com.molitics.molitician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmModel;

/**
 * Created by rahul on 3/2/2017.
 */

public class ServerDetails implements RealmModel {

    @SerializedName("server_time")
    @Expose
    private Long server_time;

    public Long getServer_time() {
        return server_time;
    }

    public void setServer_time(Long server_time) {
        this.server_time = server_time;
    }
}
