package com.molitics.molitician.config;

/**
 * Created by kailash on 7/2/17.
 */
public class ProjectConfig {

    private final static ProjectEnvironment env = ProjectEnvironment.DEV;

    public final static long VIDEO_DEFAULT_TIMER = 60000; // 60*1000

    public final static boolean SHOULD_SHOW_LOG = true;

    private static boolean IS_FORCE_UPDATE_ENABLE = true;       // true- enabling the force update check

    private static boolean IS_NOTIFICATION_ENABLE = true;      // enabling the notification

    public static ProjectEnvironment getEnv() {
        return env;
    }

    public static boolean isForceUpdateEnable() {
        return IS_FORCE_UPDATE_ENABLE;
    }

    public static boolean isNotificationEnable() {
        return IS_NOTIFICATION_ENABLE;
    }

}


