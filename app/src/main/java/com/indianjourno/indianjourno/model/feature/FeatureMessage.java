package com.indianjourno.indianjourno.model.feature;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeatureMessage {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Features ID")
    @Expose
    private List<FeaturesID> featuresID = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FeaturesID> getFeaturesID() {
        return featuresID;
    }

    public void setFeaturesID(List<FeaturesID> featuresID) {
        this.featuresID = featuresID;
    }
}
