package com.molitics.molitician.ui.dashboard.election.upcoming_election.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul on 1/12/2017.
 */

public class UpcomingPartyModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("party_name")
    @Expose
    private String partyName;

    @SerializedName("party_logo")
    @Expose
    private String partyLogo;
    @SerializedName("party_code")
    @Expose
    private String partyCode;
    @SerializedName("candidates")
    @Expose
    private String candidates;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyLogo() {
        return partyLogo;
    }

    public void setPartyLogo(String partyLogo) {
        this.partyLogo = partyLogo;
    }

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    public String getCandidates() {
        return candidates;
    }

    public void setCandidates(String candidates) {
        this.candidates = candidates;
    }
}
