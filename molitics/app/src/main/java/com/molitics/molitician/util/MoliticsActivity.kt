package com.molitics.molitician.util

import android.content.Context
import android.content.Intent
import com.molitics.molitician.ui.dashboard.ActivityFragment
import com.molitics.molitician.ui.dashboard.userProfile.EditUserProfileActivity
import com.molitics.molitician.ui.dashboard.verification.VerificationActivity

class MoliticsActivity {

    companion object {
        fun startSignInActivity(context: Context) {
            val intent = Intent(context, ActivityFragment::class.java)
            intent.putExtra(Constant.INTENT_FROM, Constant.From.ACTIVITY_SIGN_IN_FRAGMENT)
            context.startActivity(intent)
        }

        @JvmStatic
        fun startVerificationActivity(context: Context) {
            val intent = Intent(context, VerificationActivity::class.java)
            context.startActivity(intent)
        }

        @JvmStatic
        fun startChangePasswordActivity(context: Context) {
            val intent = Intent(context, ActivityFragment::class.java)
            intent.putExtra(Constant.INTENT_FROM, Constant.From.ACTIVITY_CHANGE_PASSWORD_FRAGMENT)
            context.startActivity(intent)
        }

        @JvmStatic
        fun startEditProfileActivity(context: Context) {
            val intent = Intent(context, EditUserProfileActivity::class.java)
            context.startActivity(intent)
        }
    }
}