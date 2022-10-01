package com.indianjourno.indianjourno.model.static_page;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrivacyModels {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Static Page")
    @Expose
    private List<StaticPage> staticPage = null;

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
    public List<StaticPage> getStaticPage() {
        return staticPage;
    }
    public void setStaticPage(List<StaticPage> staticPage) {
        this.staticPage = staticPage;
    }
}
