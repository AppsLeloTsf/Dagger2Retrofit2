package com.molitics.molitician.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.molitics.molitician.constants.PrefConstant;



/**
 * Created by kailash on 24-12-2015.
 */
public class Pref {

    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean readBoolean(Context context, String key) {
        return getPreferences(context).getBoolean(key, false);
    }

    static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    static int readInteger(Context context, String key) {
        return getPreferences(context).getInt(key, 0);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public static float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    public static void writeLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }

    public static long readLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PrefConstant.FILE_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void clearPreferences(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PrefConstant.FILE_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().apply();
    }

    public static void deleteKey(Context con,String key){
        getPreferences(con).edit().remove(key).apply();
    }

    public static String fetchData(Context context, String key) {
       return readString(context,key,"0");
    }
}