package com.tsfapps.daggerhiltpractice.container

import com.tsfapps.daggerhiltpractice.repository.LoginUserData
import com.tsfapps.daggerhiltpractice.repository.UserRepository
import com.tsfapps.daggerhiltpractice.viewmodelfactory.LoginViewModelFactory

class LoginContainer (private val userRepository: UserRepository){
    val loginUserData = LoginUserData()

    val loginViewModelFactory = LoginViewModelFactory(userRepository)
}