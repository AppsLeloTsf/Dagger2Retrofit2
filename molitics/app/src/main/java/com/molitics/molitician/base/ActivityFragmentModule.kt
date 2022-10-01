package com.molitics.molitician.base

import com.molitics.molitician.ui.dashboard.ActivityFragmentViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityFragmentModule {

    @Provides
    fun provideViewModule(): ActivityFragmentViewModel {
        return ActivityFragmentViewModel()
    }
}