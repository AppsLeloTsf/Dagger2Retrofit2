package com.molitics.molitician.ui.dashboard.voice.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul on 14/11/17.
 */

public class CandidateLeaderModel implements Parcelable {


    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;

    private boolean selectedLeader;

    public boolean getSelectedLeader() {
        return selectedLeader;
    }

    public void setSelectedLeader(boolean selectedLeader) {
        this.selectedLeader = selectedLeader;
    }


    public final static Parcelable.Creator<CandidateLeaderModel> CREATOR = new Creator<CandidateLeaderModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CandidateLeaderModel createFromParcel(Parcel in) {
            return new CandidateLeaderModel(in);
        }

        public CandidateLeaderModel[] newArray(int size) {
            return (new CandidateLeaderModel[size]);
        }

    };

    protected CandidateLeaderModel(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.profileImage = ((String) in.readValue((String.class.getClassLoader())));
    }

    public CandidateLeaderModel() {
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(profileImage);
    }

    public int describeContents() {
        return 0;
    }
}
