package com.molitics.molitician.ui.dashboard.register.register.provider

import com.molitics.molitician.ui.dashboard.register.register.RegisterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RegisterFragmentModule {

    @ContributesAndroidInjector(modules = [RegisterFragmentProvider::class])
    public abstract fun registerFragment(): RegisterFragment
}