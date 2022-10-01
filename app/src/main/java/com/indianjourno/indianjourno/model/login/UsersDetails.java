package com.indianjourno.indianjourno.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersDetails {
    @SerializedName("viwers_id")
    @Expose
    private String viwersId;
    @SerializedName("viwers_name")
    @Expose
    private String viwersName;
    @SerializedName("viwers_mobile")
    @Expose
    private String viwersMobile;
    @SerializedName("viwers_email")
    @Expose
    private String viwersEmail;

    public String getViwersId() {
        return viwersId;
    }

    public void setViwersId(String viwersId) {
        this.viwersId = viwersId;
    }

    public String getViwersName() {
        return viwersName;
    }

    public void setViwersName(String viwersName) {
        this.viwersName = viwersName;
    }

    public String getViwersMobile() {
        return viwersMobile;
    }

    public void setViwersMobile(String viwersMobile) {
        this.viwersMobile = viwersMobile;
    }

    public String getViwersEmail() {
        return viwersEmail;
    }

    public void setViwersEmail(String viwersEmail) {
        this.viwersEmail = viwersEmail;
    }

}
