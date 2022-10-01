package com.molitics.molitician.ui.dashboard.register.location.provider

import com.molitics.molitician.ui.dashboard.register.location.DistrictLocationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DistrictLocationFragmentModule {

    @ContributesAndroidInjector(modules = [DistrictLocationFragmentProvider::class])
    public abstract fun districtLocationFragment(): DistrictLocationFragment
}