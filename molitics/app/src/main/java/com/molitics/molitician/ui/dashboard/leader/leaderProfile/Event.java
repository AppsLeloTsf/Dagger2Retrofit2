package com.molitics.molitician.ui.dashboard.leader.leaderProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;

/**
 * Created by rahul on 1/4/2017.
 */

public class Event {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("district")
    @Expose
    private String district;

    private VoiceAllModel voiceAllModel = null;

    private CandidateProfileModel candidateProfileModel = null;


    public VoiceAllModel getVoiceAllModel() {
        return voiceAllModel;
    }

    public void setVoiceAllModel(VoiceAllModel voiceAllModel) {
        this.voiceAllModel = voiceAllModel;
    }

    public CandidateProfileModel getCandidateProfileModel() {
        return candidateProfileModel;
    }

    public void setCandidateProfileModel(CandidateProfileModel candidateProfileModel) {
        this.candidateProfileModel = candidateProfileModel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }


}
