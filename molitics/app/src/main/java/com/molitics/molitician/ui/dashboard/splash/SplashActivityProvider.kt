package com.molitics.molitician.ui.dashboard.splash

import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.base.MyViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SplashActivityProvider {

    @Provides
    fun splashViewModel(application: MolticsApplication): SplashViewModel = SplashViewModel(application)

    @Provides
    fun splashFactory(viewModel : SplashViewModel) = MyViewModelFactory(viewModel)


}