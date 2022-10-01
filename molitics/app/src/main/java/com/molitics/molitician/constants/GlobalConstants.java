package com.molitics.molitician.constants;


import com.molitics.molitician.config.ProjectConfig;
import com.molitics.molitician.config.ProjectEnvironment;

public class GlobalConstants {


    public static final int NORMAL_REQUEST = 0;


    /**
     *   Do not change the server url from here. To change goto ProjectConfig.java and change the project environment to
     *   ProjectEnvironment.STAG, ProjectEnvironment.PROD, ProjectEnvironment.DEV
     */
    static {
        ProjectEnvironment env = ProjectConfig.getEnv();

        if (env.environment.equals(ProjectEnvironment.DEV.environment)) {
            //BASE_URL = "http://192.168.0.57:8084/MeData/";
          //  BASE_URL = "https://dev.godr.com/godoctor_dev3/";
        } else if (env.environment.equals(ProjectEnvironment.STAG.environment)) {
          //  BASE_URL = "http://13.228.231.252:8080/MeData/";
        } else if (env.environment.equals(ProjectEnvironment.PROD.environment)) {
            //BASE_URL = "http://192.168.0.84:8080/MeData/";
           // BASE_URL = "http://13.229.193.112:8080/MeData/";
        }
    }
}
