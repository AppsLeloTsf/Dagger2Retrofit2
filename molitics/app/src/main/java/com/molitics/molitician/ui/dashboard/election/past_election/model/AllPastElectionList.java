package com.molitics.molitician.ui.dashboard.election.past_election.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul on 1/10/2017.
 */

public class AllPastElectionList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("state_id")
    @Expose
    private Integer state_id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("parties")
    @Expose
    private String parties;
    @SerializedName("candidates")
    @Expose
    private String candidates;
    @SerializedName("total_seats")
    @Expose
    private String totalSeats;
    @SerializedName("total_candidates")
    @Expose
    private String totalCandidates;
    @SerializedName("display_date")
    @Expose
    private String displayDate;

    @SerializedName("ruling_party")
    @Expose
    private String ruling_party;


    @SerializedName("party_logo")
    @Expose
    private String partyLogo = "";

    @SerializedName("icon")
    @Expose
    private String icon;


    public void setPartyLogo(String partyLogo) {
        this.partyLogo = partyLogo;
    }

    public Integer getState_id() {
        return state_id;
    }

    public void setState_id(Integer state_id) {
        this.state_id = state_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getParties() {
        return parties;
    }

    public void setParties(String parties) {
        this.parties = parties;
    }

    public String getCandidates() {
        return candidates;
    }

    public void setCandidates(String candidates) {
        this.candidates = candidates;
    }

    public String getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(String totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getTotalCandidates() {
        return totalCandidates;
    }

    public void setTotalCandidates(String totalCandidates) {
        this.totalCandidates = totalCandidates;
    }

    public String getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(String displayDate) {
        this.displayDate = displayDate;
    }

    public String getRuling_party() {
        return ruling_party;
    }

    public void setRuling_party(String ruling_party) {
        this.ruling_party = ruling_party;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPartyLogo() {
        return partyLogo;
    }
}
