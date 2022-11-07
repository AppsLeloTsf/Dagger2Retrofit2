package com.tsfapps.rxjavaretrofitpractice

interface Repository<T> {
    fun get(): Iterable<T>
}