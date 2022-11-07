package com.tsfapps.daggerhiltpractice.repository

import com.tsfapps.daggerhiltpractice.LoginService

class UserRepository (
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) {}


class UserLocalDataSource{

}
class UserRemoteDataSource(private val loginService: LoginService) {

}