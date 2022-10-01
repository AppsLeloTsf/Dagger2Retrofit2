package com.molitics.molitician.ui.dashboard.election.past_election.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rahul on 1/10/2017.
 */

public class PastStateModel {


    @SerializedName("total_seats")
    @Expose
    private Integer total_seats;

    @SerializedName("election_year")
    @Expose
    private String election_year;
    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("winner_id")
    @Expose
    private Integer winnerId;
    @SerializedName("winner_name")
    @Expose
    private String winnerName;
    @SerializedName("winner_party")
    @Expose
    private String winnerParty;
    @SerializedName("winner_image")
    @Expose
    private String winnerImage;
    @SerializedName("winner_position")
    @Expose
    private String winner_position;

    @SerializedName("runner_up_id")
    @Expose
    private Integer runnerUpId;
    @SerializedName("runner_up_name")
    @Expose
    private String runnerUpName;
    @SerializedName("runner_up_party")
    @Expose
    private String runnerUpParty;
    @SerializedName("runner_up_image")
    @Expose
    private String runnerUpImage;
    @SerializedName("party_list")
    @Expose
    private List<PartyList> partyList = null;

    @SerializedName("party_count")
    @Expose
    private Integer party_count = null;

    @SerializedName("governor_detail")
    @Expose
    private GovernorDetailModel governor_detail;

    public GovernorDetailModel getGovernor_detail() {
        return governor_detail;
    }

    public void setGovernor_detail(GovernorDetailModel governor_detail) {
        this.governor_detail = governor_detail;
    }

    public Integer getTotal_seats() {
        return total_seats;
    }

    public void setTotal_seats(Integer total_seats) {
        this.total_seats = total_seats;
    }

    public String getElection_year() {
        return election_year;
    }

    public void setElection_year(String election_year) {
        this.election_year = election_year;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Integer winnerId) {
        this.winnerId = winnerId;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public String getWinnerParty() {
        return winnerParty;
    }

    public void setWinnerParty(String winnerParty) {
        this.winnerParty = winnerParty;
    }

    public String getWinnerImage() {
        return winnerImage;
    }

    public void setWinnerImage(String winnerImage) {
        this.winnerImage = winnerImage;
    }

    public Integer getRunnerUpId() {
        return runnerUpId;
    }

    public void setRunnerUpId(Integer runnerUpId) {
        this.runnerUpId = runnerUpId;
    }

    public String getRunnerUpName() {
        return runnerUpName;
    }

    public void setRunnerUpName(String runnerUpName) {
        this.runnerUpName = runnerUpName;
    }

    public String getRunnerUpParty() {
        return runnerUpParty;
    }

    public void setRunnerUpParty(String runnerUpParty) {
        this.runnerUpParty = runnerUpParty;
    }

    public String getRunnerUpImage() {
        return runnerUpImage;
    }

    public void setRunnerUpImage(String runnerUpImage) {
        this.runnerUpImage = runnerUpImage;
    }

    public List<PartyList> getPartyList() {
        return partyList;
    }

    public String getWinner_position() {
        return winner_position;
    }

    public void setWinner_position(String winner_position) {
        this.winner_position = winner_position;
    }

    public void setPartyList(List<PartyList> partyList) {
        this.partyList = partyList;
    }

    public Integer getParty_count() {
        return party_count;
    }

    public void setParty_count(Integer party_count) {
        this.party_count = party_count;
    }

}
