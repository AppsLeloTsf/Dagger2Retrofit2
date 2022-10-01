package com.molitics.molitician.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.READ_CALENDAR;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static androidx.core.app.ActivityCompat.requestPermissions;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by rahul on 14/06/18.
 */

public class MoliticsAppPermission {

    public static final Integer MY_PERMISSIONS_REQUEST_CONTACT = 1223;
    public static final Integer REQUEST_STORAGE = 1233;
    public final static int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 900;
    public final static int CONTACT_PERMISSION_CODE = 900;
    public final static int STORAGE_PERMISSION = 100;


    public static void requestStoragePermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,

                READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO

        }, 23);
    }


    public static boolean checkCameraAndWritePermission() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int ThirdPermission = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermission == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestCameraPermission(Activity mContext) {
        requestPermissions(mContext, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                ASK_MULTIPLE_PERMISSION_REQUEST_CODE);

    }

    public static void calenderPermission(Activity mContext) {
        if (ContextCompat.checkSelfPermission(mContext, READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(mContext, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE,
                            READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
                    ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
        }
    }

    public static boolean contactPermission(Activity mContext) {
        if (ContextCompat.checkSelfPermission(mContext, READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else return true;
    }

    public static boolean checkWritePermission() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED;
    }


    public static void requestReadStoragePermission(Context mContext) {
        ActivityCompat.requestPermissions((Activity) mContext, new String[]{
                        READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                REQUEST_STORAGE);
    }

    public static boolean checkVideoPermission() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int TPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                TPermission == PackageManager.PERMISSION_GRANTED;
    }


}
