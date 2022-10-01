package com.molitics.molitician.ui.dashboard.verification.dagger

import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.ui.dashboard.verification.viewModel.VerificationActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class VerificationActivityModule {

    @Provides
    fun verificationViewModel(baseApplication: MolticsApplication) = VerificationActivityViewModel(baseApplication)

    @Provides
    fun verificationFactory(verificationActivityViewModel: VerificationActivityViewModel) =
            MyViewModelFactory(verificationActivityViewModel)
}