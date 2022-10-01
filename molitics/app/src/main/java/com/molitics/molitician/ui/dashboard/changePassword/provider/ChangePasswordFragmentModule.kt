package com.molitics.molitician.ui.dashboard.changePassword.provider

import com.molitics.molitician.ui.dashboard.changePassword.ChangePasswordFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ChangePasswordFragmentModule {

    @ContributesAndroidInjector(modules = [ChangePasswordFragmentProvider::class])
    public abstract fun changePasswordFragment(): ChangePasswordFragment
}