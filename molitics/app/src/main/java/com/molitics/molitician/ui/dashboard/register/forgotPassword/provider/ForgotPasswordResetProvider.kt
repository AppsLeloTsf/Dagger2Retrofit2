package com.molitics.molitician.ui.dashboard.register.forgotPassword.provider

import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.ui.dashboard.register.forgotPassword.ForgotRepo
import com.molitics.molitician.ui.dashboard.register.forgotPassword.viewModel.ForgotPasswordViewModel
import dagger.Module
import dagger.Provides

@Module
class ForgotPasswordResetProvider {

    @Provides
    fun forgotPasswordRepo() = ForgotRepo()

    @Provides
    fun otpViewModel(application: MolticsApplication, forgot: ForgotRepo) = ForgotPasswordViewModel(application, forgot)

    @Provides
    fun otpFactory(viewModel: ForgotPasswordViewModel) = MyViewModelFactory(viewModel)
}