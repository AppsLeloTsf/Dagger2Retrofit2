package com.molitics.molitician.util

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.molitics.molitician.R
import com.molitics.molitician.httpapi.ApiResult
import com.molitics.molitician.httpapi.MainResponseModel
import com.molitics.molitician.model.Data
import com.molitics.molitician.ui.dashboard.voice.reportFeed.ReportFeedDialog
import org.json.JSONObject


fun <T> LiveData<T>.nullify() {
    (this as MutableLiveData<T>).value = null
}

fun ApiResult<MainResponseModel>.resultFactory(context: Context): Data? {
    when (this) {
        is ApiResult.Ok -> {
            return this.data ?: Data().apply { this.status = 200 }
        }
        is ApiResult.Error -> {
            val errorJson = JSONObject(this.response.string())
            Util.showToast(context, errorJson.getString("message"))
        }
        is ApiResult.Exception -> {
            Util.showToast(context, this.exception.message)
        }
    }
    return null
}

fun checkContactNumber(context: Context, contactNumber: String): Boolean {
    return when {
        contactNumber.length > 9 -> {
            true
        }
        contactNumber.isEmpty() -> {
            // show toast
            Util.showToast(context, context.getString(R.string.enter_your_mobile_number))
            false
        }
        else -> {
            Util.showToast(context, context.getString(R.string.number_should_digits))
            false
        }
    }
}

fun isEmailValid(email: CharSequence?): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun generateProfileLink(userId: Int, userName: String): String {
    val finalUserName = userName.replace(" ", "_")
    return "https://molitics.in/userprofile?$finalUserName&id=$userId"
}

fun reportUser(context: Context, userId: Int) {
    val activity: AppCompatActivity = context as AppCompatActivity

    val reportFeedDialog = ReportFeedDialog()
    val mBundle = Bundle()
    mBundle.putInt(Constant.IntentKey.REPORT_ID, userId)
    mBundle.putString(Constant.IntentKey.FROM, context.getString(R.string.user))
    reportFeedDialog.arguments = mBundle
    reportFeedDialog.show(
        activity.supportFragmentManager,
        context.getString(R.string.report_dialog)
    )
}