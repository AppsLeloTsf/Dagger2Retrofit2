
package com.molitics.molitician.httpapi

object RetrofitConfig {

    // time into milisec
    val DEFAULT_CONNECT_TIMEOUT = 10000L
    val DEFAULT_READ_TIMEOUT = 7000L
    val DEFAULT_WRITE_TIMEOUT = 5000L

    // timeout constants
    val CONNECT_TIMEOUT ="CONNECT_TIMEOUT"
    val READ_TIMEOUT ="READ_TIMEOUT"
    val WRITE_TIMEOUT ="WRITE_TIMEOUT"
}