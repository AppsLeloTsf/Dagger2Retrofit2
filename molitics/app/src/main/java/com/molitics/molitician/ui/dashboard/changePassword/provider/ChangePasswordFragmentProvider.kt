package com.molitics.molitician.ui.dashboard.changePassword.provider

import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.ui.dashboard.changePassword.viewmodel.ChangePasswordViewModel
import dagger.Module
import dagger.Provides

@Module
class ChangePasswordFragmentProvider {

    @Provides
    fun changeViewModel(application: MolticsApplication) =
        ChangePasswordViewModel(application)

    @Provides
    fun changeFactory(viewModel: ChangePasswordViewModel) = MyViewModelFactory(viewModel)
}