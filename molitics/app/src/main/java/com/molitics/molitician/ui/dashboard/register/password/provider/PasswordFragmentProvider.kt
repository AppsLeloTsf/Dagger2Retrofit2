package com.molitics.molitician.ui.dashboard.register.password.provider

import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.ui.dashboard.register.location.repo.SignInRepo
import com.molitics.molitician.ui.dashboard.register.viewModel.SignInViewModel
import dagger.Module
import dagger.Provides

@Module
class PasswordFragmentProvider {

    @Provides
    fun signInRepo() = SignInRepo()

    @Provides
    fun passwordViewModel(application: MolticsApplication, signInRepo: SignInRepo) = SignInViewModel(application, signInRepo)

    @Provides
    fun passwordFactory(viewModel: SignInViewModel) = MyViewModelFactory(viewModel)
}