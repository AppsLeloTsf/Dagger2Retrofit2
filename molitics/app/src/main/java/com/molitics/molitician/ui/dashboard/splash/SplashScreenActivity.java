package com.molitics.molitician.ui.dashboard.splash;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.gcm.RegistrationIntentService;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.ActivityFragment;
import com.molitics.molitician.ui.dashboard.DashBoardActivity;
import com.molitics.molitician.ui.dashboard.language.LanguageActivity;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.Util;

/**
 * Created by Rahul on 10/17/2016.
 */

public class SplashScreenActivity extends BasicAcivity implements SplashPresenter.SplashView {
    private SplashHandler splashHandler;
    private int app_version_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        checkAppUpdate();
        shareTokenWithServer();
        setLanguage();
        setAppThemeMode();
        Util.showLog("gotoNext", "SplashScreenActivity");
    }

    private void checkAppUpdate() {
        splashHandler = new SplashHandler(this);
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            app_version_code = pInfo.versionCode;
            splashHandler.getVersion(app_version_code);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setAppThemeMode() {
        if (PrefUtil.getInt(Constant.PreferenceKey.DARK_MODE_ACTIVE) == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    void gotoNext() {
        int SPLASH_TIME_OUT = 500;
        new Handler().postDelayed(() -> {
            String next = PrefUtil.getString(Constant.PreferenceKey.NEXT_STRING);
            Intent intent;
            if (!PrefUtil.getBoolean(Constant.PreferenceKey.IS_FIRST_TIME)) {
                intent = new Intent(SplashScreenActivity.this, LanguageActivity.class);
            } else if (next.equals("HOME")) {
                intent = new Intent(SplashScreenActivity.this, DashBoardActivity.class);
            } else {
                intent = new Intent(SplashScreenActivity.this, ActivityFragment.class);
                intent.putExtra(Constant.INTENT_FROM, Constant.From.SIGN_IN_FRAGMENT);
            }
//            else {
//                intent = new Intent(SplashScreenActivity.this, ActivityFragment.class);
//                intent.putExtra(Constant.INTENT_FROM, Constant.From.ACTIVITY_SIGN_IN_FRAGMENT);
//            }
            startActivity(intent);
            overridePendingTransition(R.anim.fadein_activity, R.anim.fadeout_activity);
            finish();
        }, SPLASH_TIME_OUT);
    }

    private void setLanguage() {
        String selectedLang = PrefUtil.getConstantString(Constant.PreferenceKey.LANGUAGE);

     /*   if (selectedLang.equals(Constant.Language.HINDI_STRING)) {
            setAppLanguage(this, "hi");
        } else {
            setAppLanguage(this, "eng");
        }*/
    }

    private void checkPreviousLangSelected() {
        String selectedLanguage = PrefUtil.getString(Constant.PreferenceKey.LANGUAGE);

        if (!TextUtils.isEmpty(selectedLanguage)) {
            PrefUtil.putConstantString(Constant.PreferenceKey.LANGUAGE, selectedLanguage);
            PrefUtil.putString(Constant.PreferenceKey.LANGUAGE, "");

        }
    }

    @Override
    public void onVersionResponse(APIResponse apiResponse) {
        ExpiryData expiryData = apiResponse.getData().getExpiryData();
        PrefUtil.putString(Constant.PreferenceKey.API_KEY, apiResponse.getData().getApiKey());

        int server_version = expiryData.getLatestVersion();

        if (server_version > app_version_code) {
            userVisibleCount();
        }
        gotoNext();
    }

    @Override
    public void onVersionException(ApiException apiException) {
        if (apiException.getCode() == 1000) {

            if (!isFinishing()) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                mBuilder.setTitle(R.string.update_app_title);
                mBuilder.setMessage(R.string.force_update);
                mBuilder.setPositiveButton(getString(R.string.update), (dialog, which) -> ExtraUtils.openPlayStore(SplashScreenActivity.this));
                mBuilder.setCancelable(false);
                mBuilder.show();
            }
        } else {
            gotoNext();
        }
    }

    private void userVisibleCount() {
        int user_count = PrefUtil.getInt(Constant.USER_VISIT_COUNT);
        if (user_count < 5) {
            PrefUtil.putInt(Constant.USER_VISIT_COUNT, user_count + 1);
        }
    }

    private void shareTokenWithServer() {

        if (!PrefUtil.getBoolean(Constant.PreferenceKey.SYNC_TOKEN) && !TextUtils.isEmpty(PrefUtil.getString(Constant.PreferenceKey.FCM_TOKEN))) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }
}
