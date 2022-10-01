package com.molitics.molitician.ui.dashboard.splash

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.molitics.molitician.BR
import com.molitics.molitician.BaseActivity
import com.molitics.molitician.R
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.databinding.ActivitySplashScreenBinding
import com.molitics.molitician.ui.dashboard.DashBoardActivity
import com.molitics.molitician.ui.dashboard.language.LanguageActivity
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.ExtraUtils
import com.molitics.molitician.util.MoliticsActivity.Companion.startSignInActivity
import com.molitics.molitician.util.PrefUtil
import javax.inject.Inject

class SplashActivity : BaseActivity<ActivitySplashScreenBinding, SplashViewModel>() {

    @Inject
    lateinit var factory: MyViewModelFactory<SplashViewModel>

    private val splashViewModel: SplashViewModel by lazy {
        ViewModelProvider(this, factory)[SplashViewModel::class.java]
    }

    override fun getBindingVariable(): Int = BR.signInViewModel

    override fun getLayoutId(): Int = R.layout.activity_splash_screen

    override fun getViewModel(): SplashViewModel = splashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subScribeContactData()
        splashViewModel.shareTokenWithServer()
        splashViewModel.userVisibleCount()
        splashViewModel.checkAppVersion()
        splashViewModel.setAppThemeMode()
    }

    private fun subScribeContactData() {
        splashViewModel.checkUpdateVersion.observe(this,
            Observer {
                gotoNext()
            })
        splashViewModel.showUpdateVersion.observe(this,
            Observer {
                /* if (it)
                     showForceUpdateApp()
                 else */gotoNext()
            })
    }

    private fun showForceUpdateApp() {
        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle(R.string.update_app_title)
        mBuilder.setMessage(R.string.force_update)
        mBuilder.setPositiveButton(getString(R.string.update)) { dialog: DialogInterface?, which: Int ->
            ExtraUtils.openPlayStore(
                this
            )
        }
        mBuilder.setCancelable(false)
        mBuilder.show()
    }

    private fun gotoNext() {
        Handler().postDelayed({
            val authKey = PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY)
            val intent: Intent
            if (!PrefUtil.getBoolean(Constant.PreferenceKey.IS_FIRST_TIME)) {
                intent = Intent(this, LanguageActivity::class.java)
                startActivity(intent)
            } else if (authKey.isNullOrEmpty()) {
                startSignInActivity(this)
            } else {
                intent = Intent(this, DashBoardActivity::class.java)
                startActivity(intent)
            }
            overridePendingTransition(R.anim.fadein_activity, R.anim.fadeout_activity)
            finish()
        }, 500)
    }
}