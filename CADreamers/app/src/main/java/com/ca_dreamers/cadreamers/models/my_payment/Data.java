package com.ca_dreamers.cadreamers.models.my_payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("redirect_long_url")
    @Expose
    private String redirectLongUrl;

    public String getRedirectLongUrl() {
        return redirectLongUrl;
    }

    public void setRedirectLongUrl(String redirectLongUrl) {
        this.redirectLongUrl = redirectLongUrl;
    }

}
