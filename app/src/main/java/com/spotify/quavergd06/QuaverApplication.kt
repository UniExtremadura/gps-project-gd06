package com.spotify.quavergd06

import android.app.Application
import com.spotify.quavergd06.util.AppContainer

class QuaverApplication : Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}