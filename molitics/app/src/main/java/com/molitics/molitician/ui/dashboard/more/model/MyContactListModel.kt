package com.molitics.molitician.ui.dashboard.more.model

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.molitics.molitician.BR
import com.molitics.molitician.httpapi.RetrofitRestClient
import com.molitics.molitician.ui.dashboard.voice.VoiceCreatorProfile
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.Util
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MyContactListModel : BaseObservable() {
    val TAG = "MyContactListModel"

    @SerializedName("name")
    @Expose
    var name: String = "Anonymous"
    @SerializedName("contact1")
    @Expose
    var contact1: String = ""

    @SerializedName("location")
    @Expose
    var location: String = ""


    @SerializedName("user_id")
    @Expose
    var userId: Int = 0

    @SerializedName("image")
    @Expose
    var image: String = ""

    @SerializedName("isInvite")
    @Expose
    @get:Bindable
    var invite: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.invite)
        }

    @SerializedName("isFollow")
    @Expose
    @get:Bindable
    var follow: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.follow)
        }

    @SuppressLint("CheckResult")
    fun getContactFromServer() {
        RetrofitRestClient.instance?.sendInvite(contact1, userId, invite)!!
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    changeStatus(invite)
                }, { error ->
                    Util.showLog(TAG, error.message)
                })
    }

    @SuppressLint("CheckResult")
    fun userAction() {
        RetrofitRestClient.instance?.sendInvite(contact1, userId, follow)!!
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    changeStatus(follow)
                }, { error ->
                    Util.showLog(TAG, error.message)
                })
    }


    private fun changeStatus(previousValue: Int) {
        when (previousValue) {
            Constant.InviteFriend.INVITE -> invite = Constant.InviteFriend.INVITED
            Constant.InviteFriend.FOLLOW -> {
                invite = Constant.InviteFriend.FOLLOWING
                follow = Constant.InviteFriend.FOLLOWING
            }
            Constant.InviteFriend.FOLLOWING -> {
                invite = Constant.InviteFriend.FOLLOW
                follow = Constant.InviteFriend.FOLLOW
            }
        }
    }

    fun onUserClick(view: View) {
        if (userId != 0) {
            val mIntent = Intent(view.context, VoiceCreatorProfile::class.java)
            mIntent.putExtra(Constant.IntentKey.USER_ID, userId)
            view.context.startActivity(mIntent)
        }
    }

}
