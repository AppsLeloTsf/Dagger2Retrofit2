package com.ca_dreamers.cadreamers.models.version_detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("version_details")
    @Expose
    private String versionDetails;
    @SerializedName("created_dated")
    @Expose
    private String createdDated;
    @SerializedName("updated_dated")
    @Expose
    private String updatedDated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersionDetails() {
        return versionDetails;
    }

    public void setVersionDetails(String versionDetails) {
        this.versionDetails = versionDetails;
    }

    public String getCreatedDated() {
        return createdDated;
    }

    public void setCreatedDated(String createdDated) {
        this.createdDated = createdDated;
    }

    public String getUpdatedDated() {
        return updatedDated;
    }

    public void setUpdatedDated(String updatedDated) {
        this.updatedDated = updatedDated;
    }
}
