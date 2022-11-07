package com.tsfapps.daggerhiltpractice

import android.app.Application
import com.tsfapps.daggerhiltpractice.container.AppContainer

class MyApplication : Application(){
    val appContainer = AppContainer()

}