package com.molitics.molitician.ui.dashboard.register.forgotPassword.provider

import com.molitics.molitician.ui.dashboard.register.forgotPassword.ForgotPasswordResetFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForgotPasswordResetModule {

    @ContributesAndroidInjector(modules = [ForgotPasswordResetProvider::class])
    public abstract fun forgotPasswordFragment(): ForgotPasswordResetFragment
}