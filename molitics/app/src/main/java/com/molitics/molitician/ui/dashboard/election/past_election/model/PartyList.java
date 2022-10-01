package com.molitics.molitician.ui.dashboard.election.past_election.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul on 1/10/2017.
 */

public class PartyList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("seats")
    @Expose
    private Integer seats;
    @SerializedName("votes_share")
    @Expose
    private String votesShare;

    @SerializedName("party_color")
    @Expose
    private String party_color;

    @SerializedName("location")
    @Expose
    private String location = "";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getParty_color() {
        return party_color;
    }

    public void setParty_color(String party_color) {
        this.party_color = party_color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getVotesShare() {
        return votesShare;
    }

    public void setVotesShare(String votesShare) {
        this.votesShare = votesShare;
    }
}
