package com.molitics.molitician.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.InitializeServerRequest;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.RegisterDeviceModel;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DeviceInfo;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.Util;

import java.io.IOException;
import java.util.Map;

import static com.molitics.molitician.util.VideoExpoPlayer.generateUUID;

/**
 * Created by Rahul on 10/12/2016.
 */

public class RegistrationIntentService extends IntentService implements InitializeServerRequest, DeviceRegisterPresenter.RegisterView {

    DeviceRegisterHandler deviceRegisterHandler;
    RegisterDeviceModel registerDeviceModel;
    String token = "";
    public String getAndroid_id = "";
    int app_version_code;
    String app_version_name = "";

    public RegistrationIntentService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            if (deviceRegisterHandler == null)
                deviceRegisterHandler = new DeviceRegisterHandler(this);

            token = PrefUtil.getString(Constant.PreferenceKey.FCM_TOKEN);
            getAndroid_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            app_version_code = pInfo.versionCode;
            app_version_name = pInfo.versionName;

            createServerRequest(Constant.RequestTag.REGISTER);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createServerRequest(int tag) {

        switch (tag) {
            case Constant.RequestTag.REGISTER:
                registerDeviceModel = new RegisterDeviceModel();
                registerDeviceModel.setType("android");
                registerDeviceModel.setAppVersion(app_version_name);
                registerDeviceModel.setDeviceId(getAndroid_id);
                registerDeviceModel.setToken(token);
                registerDeviceModel.setDeviceId(Util.getUnicDeviceId(this));
                DeviceInfo.registerDevice(getApplicationContext(), registerDeviceModel);

                deviceRegisterHandler.setRegisterDevice(registerDeviceModel);
                break;
        }
    }

    @Override
    public void onRegister(APIResponse loginResponse) {
        PrefUtil.putBoolean(Constant.PreferenceKey.SYNC_TOKEN, true);
    }

    @Override
    public void onRegisterApiException(ApiException apiException) {

    }

    @Override
    public void onRegisterServerError(ServerException serverException) {
    }

    @Override
    public void onRegisterFormValidation(Map<String, String> errors) {
    }
}
