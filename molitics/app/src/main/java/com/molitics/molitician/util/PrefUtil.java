package com.molitics.molitician.util;

import android.content.SharedPreferences;

import com.molitics.molitician.MolticsApplication;

/**
 * Created by rohitkumar on 9/30/16.
 */
public class PrefUtil {


    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = MolticsApplication.molticsPreference.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(String key) {
        return MolticsApplication.molticsPreference.getString(key, "");
    }

    public static boolean getBoolean(String key) {
        return MolticsApplication.molticsPreference.getBoolean(key, false);
    }

    public static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = MolticsApplication.molticsPreference.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static int getInt(String key) {
        return MolticsApplication.molticsPreference.getInt(key, -1);
    }

    public static float getFloat(String key) {
        return MolticsApplication.molticsPreference.getFloat(key, -1);
    }

    public static void putFloat(String key, float value) {
        SharedPreferences.Editor editor = MolticsApplication.molticsPreference.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = MolticsApplication.molticsPreference.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    static void clearAll() {
        SharedPreferences.Editor editor = MolticsApplication.molticsPreference.edit();
        editor.clear();
        editor.apply();
    }

    public static void putConstantBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = MolticsApplication.molticsConstantPreference.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static Boolean getConstantBoolean(String key) {
        return MolticsApplication.molticsConstantPreference.getBoolean(key, false);
    }

    public static void putConstantString(String key, String value) {
        SharedPreferences.Editor editor = MolticsApplication.molticsConstantPreference.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getConstantString(String key) {
        return MolticsApplication.molticsConstantPreference.getString(key, "");
    }
}
