package com.molitics.molitician.ui.dashboard.election.past_election.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul on 7/4/2017.
 */

public class GovernorDetailModel {

    @SerializedName("id")
    @Expose
    private Integer gov_id = 0;
    @SerializedName("party_name")
    @Expose
    private String party_name;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("governor_image")
    @Expose
    private String governorImage = "";

    @SerializedName("position")
    @Expose
    private String position;

    public String getGovernorImage() {
        return governorImage;
    }

    public void setGovernorImage(String governorImage) {
        this.governorImage = governorImage;
    }

    public String getParty_name() {
        return party_name;
    }

    public void setParty_name(String party_name) {
        this.party_name = party_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGov_id() {
        return gov_id;
    }

    public void setGov_id(Integer gov_id) {
        this.gov_id = gov_id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
