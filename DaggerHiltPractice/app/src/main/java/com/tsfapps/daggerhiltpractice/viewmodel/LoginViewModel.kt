package com.tsfapps.daggerhiltpractice.viewmodel

import androidx.lifecycle.ViewModel
import com.tsfapps.daggerhiltpractice.repository.UserRepository

class LoginViewModel(userRepository: UserRepository) : ViewModel() {}