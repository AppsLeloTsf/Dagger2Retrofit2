package com.ca_dreamers.cadreamers.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.ca_dreamers.cadreamers.utils.Constant;


public class SharedPrefManager {
    private static com.ca_dreamers.cadreamers.storage.SharedPrefManager mInstance;
    private Context tContext;

    private SharedPreferences.Editor tEditor;


    public SharedPrefManager(Context tContext) {
        this.tContext = tContext;
    }

    public static synchronized com.ca_dreamers.cadreamers.storage.SharedPrefManager getInstance(Context tCtx){
        if (mInstance == null){
            mInstance = new com.ca_dreamers.cadreamers.storage.SharedPrefManager(tCtx);

        }
        return mInstance;
    }

    public void setUserData( String userId, String userName , String phoneNo , String email, String userPic, String userToken) {
        SharedPreferences tSharedPreferences = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        tEditor = tSharedPreferences.edit();
        tEditor.putString(Constant.USER_ID, userId);
        tEditor.putString(Constant.USER_NAME, userName);
        tEditor.putString(Constant.USER_MOBILE, phoneNo);
        tEditor.putString(Constant.USER_EMAIL, email);
        tEditor.putString(Constant.USER_PRO_PIC, userPic);
        tEditor.putString(Constant.USER_TOKEN, userToken);
        tEditor.apply();

    }


    public void clearUserData(){
        SharedPreferences tPref = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        tEditor = tPref.edit();

        tEditor.remove(Constant.USER_ID);
        tEditor.remove(Constant.USER_NAME);
        tEditor.remove(Constant.USER_MOBILE);
        tEditor.remove(Constant.USER_EMAIL);
        tEditor.remove(Constant.USER_PRO_PIC);
        tEditor.remove(Constant.USER_TOKEN);

        tEditor.apply();
        tEditor.clear();
    }









    public String getUserId(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        return sp.getString(Constant.USER_ID, Constant.EMPTY);
    }
    public String getUserToken(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        return sp.getString(Constant.USER_TOKEN, Constant.EMPTY);
    }
    public String getUserName(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        return sp.getString(Constant.USER_NAME, Constant.EMPTY);
    }
    public String getUserDob(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        return sp.getString(Constant.USER_DOB, Constant.EMPTY);
    }
    public String getUserMobile(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        return sp.getString(Constant.USER_MOBILE, Constant.EMPTY);
    }
    public String getUserEmail(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        return sp.getString(Constant.USER_EMAIL, Constant.EMPTY);
    }

    public String getUserDoj(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        return sp.getString(Constant.USER_DOJ, Constant.EMPTY);
    }
    public String getUserProPic(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        return sp.getString(Constant.USER_PRO_PIC, Constant.EMPTY);
    }

    public void setFilePath( String strFilePath) {
        SharedPreferences tSharedPreferences = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        tEditor = tSharedPreferences.edit();
        tEditor.putString(Constant.FILE_PATH, strFilePath);
        tEditor.apply();
    }

    public String getFilePath(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        return sp.getString(Constant.FILE_PATH, Constant.EMPTY);
    }


}