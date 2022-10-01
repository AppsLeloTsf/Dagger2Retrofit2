package com.molitics.molitician.ui.dashboard.register

interface SignInNavigation {

    fun navigateToPassword()

    fun navigateToOtp(contactNumber: String?, authKey: String?)
}