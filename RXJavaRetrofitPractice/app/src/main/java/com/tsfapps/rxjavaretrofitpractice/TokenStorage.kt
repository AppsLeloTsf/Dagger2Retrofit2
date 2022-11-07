package com.tsfapps.rxjavaretrofitpractice

interface TokenStorage {
//    fun retrieveToken(): String?
      fun get(): Iterable<T>
}