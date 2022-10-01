package com.molitics.molitician.ui.dashboard.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rahul on 12/16/2016.
 */

public class OtpModel {

    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("device_id")
    @Expose
    private String deviceId;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
