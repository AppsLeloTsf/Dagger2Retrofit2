package com.molitics.molitician.ui.dashboard.register.provider

import com.molitics.molitician.ui.dashboard.register.SignInFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
public abstract class SignInFragmentModule {

    @ContributesAndroidInjector(modules = [SignInProvider::class])
    public abstract fun signInFragment(): SignInFragment
}