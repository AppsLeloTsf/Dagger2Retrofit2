package com.molitics.molitician.ui.dashboard.register.otp.provider

import com.molitics.molitician.ui.dashboard.register.otp.OtpFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class OtpFragmentModule {

    @ContributesAndroidInjector(modules = [OtpFragmentProvider::class])
    public abstract fun otpFragment(): OtpFragment
}