package com.molitics.molitician.ui.dashboard.register.forgotPassword.provider

import com.molitics.molitician.ui.dashboard.register.forgotPassword.ForgotPasswordFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForgotFragmentModule {

    @ContributesAndroidInjector(modules = [ForgotFragmentProvider::class])
    public abstract fun forgotPasswordFragment(): ForgotPasswordFragment
}