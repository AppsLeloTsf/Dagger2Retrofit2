package com.molitics.molitician.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;

import com.molitics.molitician.model.LoginRequest;
import com.molitics.molitician.model.RegisterDeviceModel;

import java.util.HashMap;

/**
 * Created by rahul on 4/24/2017.
 */

public class DeviceInfo {


    public static void registerDevice(Context mContext, RegisterDeviceModel registerDeviceModel) {

        String os_version = android.os.Build.VERSION.RELEASE;
        String brand = Build.MANUFACTURER;
        String device_model = Build.MODEL;
        registerDeviceModel.setOs_version(os_version);
        registerDeviceModel.setBrand(brand);
        registerDeviceModel.setDevice_model(device_model);
        registerDeviceModel.setRam(getDeviceMemory(mContext));
    }

    public static void getDeviceInfo(Activity mContext, LoginRequest loginRequest) {

        String os_version = android.os.Build.VERSION.RELEASE;
        String brand = Build.MANUFACTURER;
        String device_model = Build.MODEL;

        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int screen_height = metrics.heightPixels;
        int screen_width = metrics.widthPixels;

        loginRequest.setOs_version(os_version);
        loginRequest.setBrand(brand);
        loginRequest.setDevice_model(device_model);
        loginRequest.setScreen_height(screen_height);
        loginRequest.setScreen_width(screen_width);
        loginRequest.setRam(getDeviceMemory(mContext.getApplicationContext()));
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private static long getDeviceMemory(Context mContext) {
        ActivityManager actManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        if (actManager != null) {
            actManager.getMemoryInfo(memInfo);
        }
        return memInfo.totalMem;
    }

    public static HashMap getVersionInfo(Context mContext) throws PackageManager.NameNotFoundException {
        HashMap<String, String> hashMap = new HashMap<>();
        PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        String app_version_name = pInfo.versionName;
        hashMap.put(Constant.VERSION_NAME, app_version_name);
        return hashMap;
    }

}
