package com.molitics.molitician.ui.dashboard.election.past_election.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rahul on 1/12/2017.
 */

public class AboutConstituencyModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("constitutency")
    @Expose
    private String constitutency;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("males")
    @Expose
    private String males;
    @SerializedName("females")
    @Expose
    private String females;
    @SerializedName("voters")
    @Expose
    private String voters;
    @SerializedName("election_year")
    @Expose
    private String electionYear;
    @SerializedName("winner")
    @Expose
    private String winner;
    @SerializedName("runner_up")
    @Expose
    private String runnerUp;
    @SerializedName("population")
    @Expose
    private String population;
    @SerializedName("winner_id")
    @Expose
    private Integer winnerId;
    @SerializedName("winner_name")
    @Expose
    private String winnerName;
    @SerializedName("winner_image")
    @Expose
    private String winnerImage;
    @SerializedName("winner_party")
    @Expose
    private String winnerParty;
    @SerializedName("winner_votes")
    @Expose
    private String winnerVotes;
    @SerializedName("other_candidates")
    @Expose
    private List<OtherCandidate> otherCandidates = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConstitutency() {
        return constitutency;
    }

    public void setConstitutency(String constitutency) {
        this.constitutency = constitutency;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMales() {
        return males;
    }

    public void setMales(String males) {
        this.males = males;
    }

    public String getFemales() {
        return females;
    }

    public void setFemales(String females) {
        this.females = females;
    }

    public String getVoters() {
        return voters;
    }

    public void setVoters(String voters) {
        this.voters = voters;
    }

    public String getElectionYear() {
        return electionYear;
    }

    public void setElectionYear(String electionYear) {
        this.electionYear = electionYear;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getRunnerUp() {
        return runnerUp;
    }

    public void setRunnerUp(String runnerUp) {
        this.runnerUp = runnerUp;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
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

    public String getWinnerImage() {
        return winnerImage;
    }

    public void setWinnerImage(String winnerImage) {
        this.winnerImage = winnerImage;
    }

    public String getWinnerParty() {
        return winnerParty;
    }

    public void setWinnerParty(String winnerParty) {
        this.winnerParty = winnerParty;
    }

    public String getWinnerVotes() {
        return winnerVotes;
    }

    public void setWinnerVotes(String winnerVotes) {
        this.winnerVotes = winnerVotes;
    }

    public List<OtherCandidate> getOtherCandidates() {
        return otherCandidates;
    }

    public void setOtherCandidates(List<OtherCandidate> otherCandidates) {
        this.otherCandidates = otherCandidates;
    }
}
