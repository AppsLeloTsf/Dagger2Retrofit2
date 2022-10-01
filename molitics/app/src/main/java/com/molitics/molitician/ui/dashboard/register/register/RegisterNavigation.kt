package com.molitics.molitician.ui.dashboard.register.register

interface RegisterNavigation {

    fun navigateToRegister() {}

    fun navigateToStateLocation() {}

    fun navigateToOtp(contactNumber: String?, auth: String?) {}
    fun navigateToOtp() {}
}