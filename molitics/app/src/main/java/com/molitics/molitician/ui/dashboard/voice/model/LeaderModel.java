package com.molitics.molitician.ui.dashboard.voice.model;

import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.molitics.molitician.db.creator.feed.LeaderListCreator;

import java.io.Serializable;

/**
 * Created by Rakesh Kumar on 17/09/2021.
 */
@TypeConverters(LeaderListCreator.class)
public class LeaderModel implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("profile_image")
    @Expose
    private String profileImage;


    public boolean isSelectedLeader() {
        return selectedLeader;
    }

    private boolean selectedLeader;

    public boolean getSelectedLeader() {
        return selectedLeader;
    }

    public void setSelectedLeader(boolean selectedLeader) {
        this.selectedLeader = selectedLeader;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}