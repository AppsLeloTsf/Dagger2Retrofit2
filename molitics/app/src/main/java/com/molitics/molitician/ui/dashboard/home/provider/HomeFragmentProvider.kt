package com.molitics.molitician.ui.dashboard.home.provider

import com.molitics.molitician.ui.dashboard.home.HomeFragment
import com.molitics.molitician.ui.dashboard.home.module.HomeFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentProvider {


    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    internal abstract fun provideHomeFragmentFactory(): HomeFragment



}