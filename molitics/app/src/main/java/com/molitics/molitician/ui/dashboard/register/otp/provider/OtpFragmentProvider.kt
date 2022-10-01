package com.molitics.molitician.ui.dashboard.register.otp.provider

import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.ui.dashboard.register.forgotPassword.ForgotRepo
import com.molitics.molitician.ui.dashboard.register.forgotPassword.viewModel.ForgotPasswordViewModel
import com.molitics.molitician.ui.dashboard.register.location.repo.SignInRepo
import com.molitics.molitician.ui.dashboard.register.otp.OtpViewModel
import com.molitics.molitician.ui.dashboard.register.viewModel.SignInViewModel
import dagger.Module
import dagger.Provides

@Module
class OtpFragmentProvider {

    @Provides
    fun provideSigInInRepo() = SignInRepo()

    @Provides
    fun otpViewModel(application: MolticsApplication, signInRepo: SignInRepo) = SignInViewModel(application, signInRepo)

    @Provides
    fun otpFactory(viewModel: SignInViewModel) = MyViewModelFactory(viewModel)
}