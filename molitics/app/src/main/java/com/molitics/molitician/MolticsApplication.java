package com.molitics.molitician;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import android.os.StrictMode;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.PrefUtil;
import com.squareup.picasso.Picasso;
import com.yandex.metrica.YandexMetrica;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import io.branch.referral.Branch;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * on 9/30/16.
 */
public class MolticsApplication extends Application implements HasAndroidInjector {

    private static final String TAG = "MolticsApplication";
    public static SharedPreferences molticsPreference;
    public static SharedPreferences molticsConstantPreference;

/*    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;*/
    private static Context mContext;
    public static String YOUTUBE_KEY = "";
    public static String getAndroid_id = "";


    @Inject
    DispatchingAndroidInjector<Object> activityDispatchingAndroidInjector;

    @Override
    public AndroidInjector<Object> androidInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override
    public void onCreate() {
        //setStrictMode();
        super.onCreate();
        mContext = this;

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        FirebaseApp.initializeApp(this);
        Branch.enableLogging();
        Branch.getAutoInstance(this);
        initPicasso();

        YandexMetrica.activate(this, getString(R.string.app_matrica_key));
        YandexMetrica.enableActivityAutoTracking(this);
        Realm.setDefaultConfiguration(config);

        init();
        YOUTUBE_KEY = PrefUtil.getString(Constant.PreferenceKey.API_KEY);
        getAndroid_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        ////printKeyHash();
    }

    public void init() {
        molticsPreference = MoliticsPreference.getInstance(this);
        molticsConstantPreference = MoliticsPreference.getConstantInstance(this);
    }


    private void initPicasso() {
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this, Integer.MAX_VALUE));
        Picasso build = builder.build();
        build.setIndicatorsEnabled(false);
        build.setLoggingEnabled(true);
        Picasso.setSingletonInstance(build);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    public void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.molitics.molitician", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("VIVZ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    private void setStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectAll()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
    }

    public static Context getAppContaxt() {
        return mContext;
    }

}
