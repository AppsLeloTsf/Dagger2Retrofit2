package com.molitics.molitician.ui.dashboard.liveVideo.provider


import com.molitics.molitician.ui.dashboard.liveVideo.LiveVideoFragment
import com.molitics.molitician.ui.dashboard.liveVideo.module.LiveVideoModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class LiveVideoProvider {


    @ContributesAndroidInjector(modules = [LiveVideoModule::class])
    internal abstract fun provideLiveVideoFragmentFactory(): LiveVideoFragment


}