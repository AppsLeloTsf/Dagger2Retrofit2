package com.molitics.molitician.ui.dashboard.voice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Rakesh Kumar on 17/09/2021.
 */

public class UserFeedModel implements Serializable {

    @SerializedName("context")
    @Expose
    private String context;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("users")
    @Expose
    private ArrayList<UsersFeedModel> usersFeedModel;


    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<UsersFeedModel> getUsersFeedModel() {
        return usersFeedModel;
    }

    public void setUsersFeedModel(ArrayList<UsersFeedModel> usersFeedModel) {
        this.usersFeedModel = usersFeedModel;
    }
}