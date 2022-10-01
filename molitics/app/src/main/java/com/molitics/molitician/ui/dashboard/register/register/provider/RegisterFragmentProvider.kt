package com.molitics.molitician.ui.dashboard.register.register.provider

import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.ui.dashboard.register.register.RegisterRepo
import com.molitics.molitician.ui.dashboard.register.register.viewModel.RegisterViewModel
import dagger.Module
import dagger.Provides

@Module
class RegisterFragmentProvider {

    @Provides
    fun registerRepo() = RegisterRepo()

    @Provides
    fun registerViewModel(application: MolticsApplication, registerRepo: RegisterRepo) = RegisterViewModel(application, registerRepo)

    @Provides
    fun otpFactory(viewModel: RegisterViewModel) = MyViewModelFactory(viewModel)
}