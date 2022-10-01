package com.molitics.molitician.ui.dashboard.register.location.provider

import com.molitics.molitician.ui.dashboard.register.location.LocationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LocationFragmentModule {

    @ContributesAndroidInjector(modules = [LocationFragmentProvider::class])
    public abstract fun locationFragment(): LocationFragment
}