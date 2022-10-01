package com.molitics.molitician.ui.dashboard.register.provider

import com.molitics.molitician.ui.dashboard.register.SignUpFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SignUpFragmentModule {

    @ContributesAndroidInjector(modules = [SignUpFragmentProvider::class])
    public abstract fun signUpFragment(): SignUpFragment
}