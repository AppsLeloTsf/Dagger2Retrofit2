package com.molitics.molitician.ui.dashboard.register.password.provider

import com.molitics.molitician.ui.dashboard.register.password.PasswordFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PasswordFragmentModule {

    @ContributesAndroidInjector(modules = [PasswordFragmentProvider::class])
    abstract fun passwordFragment(): PasswordFragment
}