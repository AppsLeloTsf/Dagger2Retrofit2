package com.molitics.molitician.ui.dashboard.register

import com.google.gson.annotations.SerializedName
import io.realm.internal.Keep

@Keep
data class UserModel(
    val auth_key: String,
    val next: String,
    var mobile: String,
    var username: String? = null,
    @SerializedName("id")
    var userId: Int,
    @SerializedName("locaation")
    val userLocation: UserLocationDetails,
)

@Keep
data class UserLocationDetails(val state: UserState)

@Keep
data class UserState(val name: String)