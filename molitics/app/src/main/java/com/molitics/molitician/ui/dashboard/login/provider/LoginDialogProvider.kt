package com.molitics.molitician.ui.dashboard.login.provider

import com.molitics.molitician.ui.dashboard.login.LoginDialogFragment
import com.molitics.molitician.ui.dashboard.login.module.LoginDialogModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginDialogProvider {

    @ContributesAndroidInjector(modules = [LoginDialogModule::class])
    internal abstract fun provideLoginDiaogFactory(): LoginDialogFragment


}