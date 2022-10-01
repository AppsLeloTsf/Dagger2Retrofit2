package com.molitics.molitician.ui.dashboard.userProfile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul on 1/13/2017.
 */

public class EditUserProfileModel {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("district")
    @Expose
    private Integer district;
    @SerializedName("mla")
    @Expose
    private Integer mla;
    @SerializedName("mp")
    @Expose
    private Integer mp;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("political_orientation")
    @Expose
    private Integer political_orientation;
    @SerializedName("profession")
    @Expose
    private Integer profession;
    @SerializedName("bio")
    String userBio;

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public Integer getProfession() {
        return profession;
    }

    public void setProfession(Integer profession) {
        this.profession = profession;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDistrict() {
        return district;
    }

    public void setDistrict(Integer district) {
        this.district = district;
    }

    public Integer getMla() {
        return mla;
    }

    public void setMla(Integer mla) {
        this.mla = mla;
    }

    public Integer getMp() {
        return mp;
    }

    public void setMp(Integer mp) {
        this.mp = mp;
    }

    public Integer getPolitical_orientation() {
        return political_orientation;
    }

    public void setPolitical_orientation(Integer political_orientation) {
        this.political_orientation = political_orientation;
    }

    public Integer getGender_id() {
        return gender;
    }

    public void setGender_id(Integer gender) {
        this.gender = gender;
    }


}
