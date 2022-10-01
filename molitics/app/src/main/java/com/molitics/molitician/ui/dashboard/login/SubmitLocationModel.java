package com.molitics.molitician.ui.dashboard.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul on 12/16/2016.
 */

public class SubmitLocationModel {

    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("district")
    @Expose
    private Integer district;
    @SerializedName("mp")
    @Expose
    private Integer mp;
    @SerializedName("mla")
    @Expose
    private Integer mla;

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

    public Integer getMp() {
        return mp;
    }

    public void setMp(Integer mp) {
        this.mp = mp;
    }

    public Integer getMla() {
        return mla;
    }

    public void setMla(Integer mla) {
        this.mla = mla;
    }
}
