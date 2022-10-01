package com.molitics.molitician.ui.dashboard.voice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Created by Rakesh Kumar on 18/09/2021.
 */

public class SuggestionModel implements Serializable {

    @SerializedName("users")
    @Expose
    private UserFeedModel userFeedModel;

    @SerializedName("leaders")
    @Expose
    private LeaderFeedModel leaderFeedModel;

    public UserFeedModel getUserModel() {
        return userFeedModel;
    }

    public void setUserModel(UserFeedModel userFeedModel) {
        this.userFeedModel = userFeedModel;
    }

    public LeaderFeedModel getLeaderModelArrayList() {
        return leaderFeedModel;
    }

    public void setLeaderModelArrayList(LeaderFeedModel leaderFeedModel) {
        this.leaderFeedModel = leaderFeedModel;
    }

}
