package com.molitics.molitician.ui.dashboard.leader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 3/21/2017.
 */

public class LeadersIdModel {

    @SerializedName("page")
    @Expose
    String page;
    @SerializedName("state")
    @Expose
    String state;
    @SerializedName("district")
    @Expose
    String district;
    @SerializedName("mp")
    @Expose
    String mp;
    @SerializedName("mla")
    @Expose
    String mla;
    @SerializedName("party")
    @Expose
    String party;
    @SerializedName("post")
    @Expose
    String post;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("tab_serial")
    @Expose
    Integer tab_serial;

    @SerializedName("leader_id")
    @Expose
    List<Integer> leader_id = new ArrayList<>();


    public Integer getTab_serial() {
        return tab_serial;
    }

    public void setTab_serial(Integer tab_serial) {
        this.tab_serial = tab_serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
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

    public String getMp() {
        return mp;
    }

    public void setMp(String mp) {
        this.mp = mp;
    }

    public String getMla() {
        return mla;
    }

    public void setMla(String mla) {
        this.mla = mla;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setLeader_id(List<Integer> leader_id) {
        this.leader_id = leader_id;
    }


    public List<Integer> getLeader_id() {
        return leader_id;
    }

    public void deleteLeader_is() {
        leader_id.clear();
    }

    public void setLeader_id(Integer leader_id) {
        this.leader_id.add(leader_id);
    }

}
