package com.molitics.molitician.ui.dashboard.voice.provider

import com.molitics.molitician.ui.dashboard.login.LoginDialogFragment
import com.molitics.molitician.ui.dashboard.login.module.LoginDialogModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
public abstract class CreateVoiceFragmentModule {

    @ContributesAndroidInjector(modules = [LoginDialogModule::class])
    public abstract fun provideUserLoginDialogFragmentFactory(): LoginDialogFragment
}