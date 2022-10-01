package com.molitics.molitician.config;

/**
 * Created by kailash on 29/1/18.
 */

public class ConfigDetails {

    String versionCode;
    String versionName;
    String environment;
    String baseUrl;

    public ConfigDetails(String versionCode, String versionName, String environment, String baseUrl) {
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.environment = environment;
        this.baseUrl = baseUrl;
    }

    @Override
    public String toString() {
        return "" +
                "\n versionCode= '" + versionCode + '\'' +
                ", \nversionName= '" + versionName + '\'' +
                ", \nenvironment= '" + environment + '\'' +
                ", \nbaseUrl= '" + baseUrl;
    }
}
