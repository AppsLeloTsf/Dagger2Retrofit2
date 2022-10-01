package com.molitics.molitician.ui.dashboard.register.provider

import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.ui.dashboard.register.location.repo.SignInRepo
import com.molitics.molitician.ui.dashboard.register.viewModel.SignInViewModel
import dagger.Module
import dagger.Provides

@Module
class SignInProvider {

    @Provides
    fun signInRepo() = SignInRepo()

    @Provides
    fun signInViewModel(application: MolticsApplication, repo: SignInRepo) = SignInViewModel(application, repo)

    @Provides
    fun signInFactory(viewModel: SignInViewModel) = MyViewModelFactory(viewModel)

}