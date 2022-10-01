package com.tsfapps.daggerhiltpractice

import com.tsfapps.daggerhiltpractice.repository.UserLocalDataSource
import com.tsfapps.daggerhiltpractice.repository.UserRemoteDataSource
import com.tsfapps.daggerhiltpractice.repository.UserRepository
import com.tsfapps.daggerhiltpractice.viewmodelfactory.LoginViewModelFactory
import retrofit2.Retrofit

class AppContainer {

    private val retrofit = Retrofit.Builder()
        .baseUrl("")
        .build()
        .create(LoginService::class.java)

    private val userRemoteDataSource = UserRemoteDataSource(retrofit)
    private val userLocalDataSource = UserLocalDataSource()

    //not private, as it'll be exposed
    val userRepository = UserRepository(userLocalDataSource, userRemoteDataSource)

    val  loginViewModelFactory = LoginViewModelFactory(userRepository)



}