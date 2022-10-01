package com.molitics.molitician.ui.dashboard.login.module

import com.molitics.molitician.ui.dashboard.login.viewModel.LoginDialogViewModel
import dagger.Module
import dagger.Provides

@Module
class LoginDialogModule {

    @Provides
    fun loginDialogViewModel(): LoginDialogViewModel = LoginDialogViewModel()
}