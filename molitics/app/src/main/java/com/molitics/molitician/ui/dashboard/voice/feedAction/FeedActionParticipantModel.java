package com.molitics.molitician.ui.dashboard.voice.feedAction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul on 03/04/18.
 */

public class FeedActionParticipantModel {


    @SerializedName("user_id")
    @Expose
    int user_id;

    @SerializedName("name")
    @Expose
    String userName;

    @SerializedName("location")
    @Expose
    String userLocation;

    @SerializedName("is_following")
    @Expose
    int isFollowing;

    @SerializedName("image")
    @Expose
    String userImage;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public int getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(int isFollowing) {
        this.isFollowing = isFollowing;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}

