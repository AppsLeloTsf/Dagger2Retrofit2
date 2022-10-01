package com.molitics.molitician.ui.dashboard.local;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 10/20/2016.
 */

public class Error {

    @SerializedName("auth_key")
    @Expose
    private List<String> authKey = new ArrayList<String>();

    /**
     *
     * @return
     * The authKey
     */
    public List<String> getAuthKey() {
        return authKey;
    }

    /**
     *
     * @param authKey
     * The auth_key
     */
    public void setAuthKey(List<String> authKey) {
        this.authKey = authKey;
    }

}