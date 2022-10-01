package com.molitics.molitician.ui.dashboard.voice.model;

import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.molitics.molitician.db.creator.feed.LeaderListCreator;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Rakesh Kumar on 17/09/2021.
 */
public class LeaderFeedModel implements Serializable {

    @SerializedName("context")
    @Expose
    private String context;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("users")
    @Expose
    private ArrayList<LeaderModel> leaderModels;

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

    public ArrayList<LeaderModel> getLeaderModels() {
        return leaderModels;
    }

    public void setLeaderModels(ArrayList<LeaderModel> leaderModels) {
        this.leaderModels = leaderModels;
    }
}