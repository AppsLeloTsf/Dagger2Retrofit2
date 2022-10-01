package com.molitics.molitician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul on 10/13/2016.
 */


public class LoginResponse {

    @SerializedName("auth_key")
    @Expose
    private String authKey;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("mobile")
    @Expose
    private Object mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("state_name")
    @Expose
    private String stateName;
    @SerializedName("mla")
    @Expose
    private Integer mla;
    @SerializedName("mla_name")
    @Expose
    private String mlaName;
    @SerializedName("mp")
    @Expose
    private Integer mp;
    @SerializedName("mp_name")
    @Expose
    private String mpName;

    /**
     *
     * @return
     * The authKey
     */
    public String getAuthKey() {
        return authKey;
    }

    /**
     *
     * @param authKey
     * The auth_key
     */
    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    /**
     *
     * @return
     * The gender
     */
    public Object getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     * The gender
     */
    public void setGender(Object gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     * The mobile
     */
    public Object getMobile() {
        return mobile;
    }

    /**
     *
     * @param mobile
     * The mobile
     */
    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The state
     */
    public Integer getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     *
     * @param stateName
     * The state_name
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     *
     * @return
     * The mla
     */
    public Integer getMla() {
        return mla;
    }

    /**
     *
     * @param mla
     * The mla
     */
    public void setMla(Integer mla) {
        this.mla = mla;
    }

    /**
     *
     * @return
     * The mlaName
     */
    public String getMlaName() {
        return mlaName;
    }

    /**
     *
     * @param mlaName
     * The mla_name
     */
    public void setMlaName(String mlaName) {
        this.mlaName = mlaName;
    }

    /**
     *
     * @return
     * The mp
     */
    public Integer getMp() {
        return mp;
    }

    /**
     *
     * @param mp
     * The mp
     */
    public void setMp(Integer mp) {
        this.mp = mp;
    }

    /**
     *
     * @return
     * The mpName
     */
    public String getMpName() {
        return mpName;
    }

    /**
     *
     * @param mpName
     * The mp_name
     */
    public void setMpName(String mpName) {
        this.mpName = mpName;
    }

}
