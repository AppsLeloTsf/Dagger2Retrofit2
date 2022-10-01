package com.molitics.molitician.ui.dashboard.splash

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageInfo
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.molitics.molitician.BaseViewModel
import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.gcm.RegistrationIntentService
import com.molitics.molitician.httpapi.RetrofitRestClient
import com.molitics.molitician.model.Data
import com.molitics.molitician.ui.dashboard.BaseNavigation
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.ExtraUtils
import com.molitics.molitician.util.ExtraUtils.setAppLanguage
import com.molitics.molitician.util.PrefUtil
import com.molitics.molitician.util.Util
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(val application: MolticsApplication) : BaseViewModel<BaseNavigation>() {

    val checkUpdateVersion: MutableLiveData<Data> = MutableLiveData()
    val showUpdateVersion: MutableLiveData<Boolean> = MutableLiveData()

    fun checkAppVersion() {
        viewModelScope.launch(coroutinException) {
            val pInfo: PackageInfo = application.packageManager.getPackageInfo(application.packageName, 0)
            val versionCode = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                pInfo.longVersionCode.toInt()
            } else {
                pInfo.versionCode
            }
            updateApi(versionCode)
        }
    }

    private suspend fun updateApi(appVersion: Int) = withContext(Dispatchers.Default) {
        val apiResponse = RetrofitRestClient.instance?.checkAppUpdate(appVersion)
        val expiryData: ExpiryData? = apiResponse?.data?.expiryData
        PrefUtil.putString(Constant.PreferenceKey.API_KEY, apiResponse?.data?.apiKey)
        val serverVersion = expiryData?.latestVersion
        if (serverVersion != null) {
            if (serverVersion > Util.getAppVersion(application).versionCode) {
                userVisibleCount()
            }
        }
        checkUpdateVersion.postValue(apiResponse?.data)
    }

    private val coroutinException = CoroutineExceptionHandler { _, exception ->
        // if exception == 1000 set true else false
        showUpdateVersion.postValue(true)
    }

    fun shareTokenWithServer() {
        if (!PrefUtil.getBoolean(Constant.PreferenceKey.SYNC_TOKEN) && !TextUtils.isEmpty(PrefUtil.getString(Constant.PreferenceKey.FCM_TOKEN))) {
            val intent = Intent(application, RegistrationIntentService::class.java)
            application.startService(intent)
        }
    }

    fun userVisibleCount() {
        val userCount = PrefUtil.getInt(Constant.USER_VISIT_COUNT)
        if (userCount < 5) {
            PrefUtil.putInt(Constant.USER_VISIT_COUNT, userCount + 1)
        }
    }

    fun setAppThemeMode() {
        if (PrefUtil.getInt(Constant.PreferenceKey.DARK_MODE_ACTIVE) == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun setLanguage() {
        val selectedLang = PrefUtil.getConstantString(Constant.PreferenceKey.LANGUAGE)

        if (selectedLang.equals(Constant.Language.HINDI_STRING)) {
            setAppLanguage(application, "hi");
        } else {
            setAppLanguage(application, "eng");
        }
    }
}