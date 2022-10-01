package com.molitics.molitician.ui.dashboard.changePassword.model

data class ChangePasswordRequestModel(val user: UserChangePassword)

data class UserChangePassword(
    val current_password: String?,
    val new_password: String?,
    val new_password_confirmation: String?
)