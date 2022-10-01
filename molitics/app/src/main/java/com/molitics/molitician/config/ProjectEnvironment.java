package com.molitics.molitician.config;

/**
 * Created by kailash on 7/2/17.
 */
public enum ProjectEnvironment {
    DEV("dev"), STAG("staging"), PROD("production");
    public String environment;

    ProjectEnvironment(String s) {
        environment = s;
    }
}