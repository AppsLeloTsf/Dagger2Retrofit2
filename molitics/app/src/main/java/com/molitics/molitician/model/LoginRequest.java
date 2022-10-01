package com.molitics.molitician.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul on 10/13/16.
 */

public class LoginRequest {

    @SerializedName("source")
    @Expose
    private Integer source;
    @SerializedName("personName")
    @Expose
    private String personName;
    @SerializedName("personEmail")
    @Expose
    private String personEmail;
    @SerializedName("givenName")
    @Expose
    private String givenName;
    @SerializedName("personPhoto")
    @Expose
    private String personPhoto;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("age_range")
    @Expose
    private String ageRange;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("mobile")
    @Expose
    private String mobile;

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

    @SerializedName("image")
    @Expose
    private String image;

    //// set default type to api
    @SerializedName("type")
    @Expose
    private String type = "andorid";

    @SerializedName("app_version")
    @Expose
    private String appVersion;

    @SerializedName("token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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
     * @return The source
     */
    public Integer getSource() {
        return source;
    }

    /**
     * @param source The source
     */
    public void setSource(Integer source) {
        this.source = source;
    }

    /**
     * @return The personName
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * @param personName The personName
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    /**
     * @return The personEmail
     */
    public String getPersonEmail() {
        return personEmail;
    }

    /**
     * @param personEmail The personEmail
     */
    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    /**
     * @return The givenName
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * @param givenName The givenName
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * @return The personPhoto
     */
    public String getPersonPhoto() {
        return personPhoto;
    }

    /**
     * @param personPhoto The personPhoto
     */
    public void setPersonPhoto(String personPhoto) {
        this.personPhoto = personPhoto;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
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
     * @return The ageRange
     */
    public String getAgeRange() {
        return ageRange;
    }

    /**
     * @param ageRange The age_range
     */
    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    /**
     * @return The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile The mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
