package com.tsfapps.daggerhiltpractice.viewmodelfactory

import com.tsfapps.daggerhiltpractice.repository.UserRepository
import com.tsfapps.daggerhiltpractice.viewmodel.LoginViewModel

interface Factory<T> {
    fun create(): T
}


class LoginViewModelFactory(private val userRepository: UserRepository) : Factory<LoginViewModel> {
    override fun create(): LoginViewModel{
        return LoginViewModel(userRepository)
    }
}

class StringFactory(private val userRepository: UserRepository) : Factory<String>{
    override fun create(): String {
        TODO("Not yet implemented")
    }

}
class IntFactory(private val userRepository: UserRepository) : Factory<Int>{
    override fun create(): Int {
        TODO("Not yet implemented")
    }

}