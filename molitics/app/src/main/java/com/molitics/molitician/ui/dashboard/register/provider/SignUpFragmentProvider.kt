package com.molitics.molitician.ui.dashboard.register.provider

import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.ui.dashboard.register.register.RegisterRepo
import com.molitics.molitician.ui.dashboard.register.register.viewModel.RegisterViewModel
import dagger.Module
import dagger.Provides

@Module
class SignUpFragmentProvider {

    @Provides
    fun registerRepo() = RegisterRepo()

    @Provides
    fun signUpViewModel(application: MolticsApplication, registerRepo: RegisterRepo) = RegisterViewModel(application, registerRepo)

    @Provides
    fun signInFactory(viewModel: RegisterViewModel) = MyViewModelFactory(viewModel)
}