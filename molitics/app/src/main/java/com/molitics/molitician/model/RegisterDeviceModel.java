package com.molitics.molitician.model;

/**
 * Created by rohitkumar on 10/12/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterDeviceModel {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("app_version")
    @Expose
    private String appVersion;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("os_version")
    @Expose
    private String os_version;

    @SerializedName("device_model")
    @Expose
    private String device_model;
    @SerializedName("brand")
    @Expose
    private String brand;

    @SerializedName("screen_height")
    @Expose
    private Integer screen_height;

    @SerializedName("screen_width")
    @Expose
    private Integer screen_width;

    @SerializedName("ram")
    @Expose
    private Long ram;

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getScreen_height() {
        return screen_height;
    }

    public void setScreen_height(Integer screen_height) {
        this.screen_height = screen_height;
    }

    public Integer getScreen_width() {
        return screen_width;
    }

    public void setScreen_width(Integer screen_width) {
        this.screen_width = screen_width;
    }

    public Long getRam() {
        return ram;
    }

    public void setRam(Long ram) {
        this.ram = ram;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The appVersion
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * @param appVersion The app_version
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    /**
     * @return The deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId The device_id
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return The token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token The token
     */
    public void setToken(String token) {
        this.token = token;
    }

}
