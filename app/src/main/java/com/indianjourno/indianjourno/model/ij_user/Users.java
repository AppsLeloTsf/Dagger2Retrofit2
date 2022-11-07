package com.indianjourno.indianjourno.model.ij_user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {
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
    @SerializedName("viwers_password")
    @Expose
    private String viwersPassword;
    @SerializedName("viwers_status")
    @Expose
    private String viwersStatus;
    @SerializedName("viewer_created")
    @Expose
    private Object viewerCreated;

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

    public String getViwersPassword() {
        return viwersPassword;
    }

    public void setViwersPassword(String viwersPassword) {
        this.viwersPassword = viwersPassword;
    }

    public String getViwersStatus() {
        return viwersStatus;
    }

    public void setViwersStatus(String viwersStatus) {
        this.viwersStatus = viwersStatus;
    }

    public Object getViewerCreated() {
        return viewerCreated;
    }

    public void setViewerCreated(Object viewerCreated) {
        this.viewerCreated = viewerCreated;
    }
}
