package com.fxj.repluginhost

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.qihoo360.replugin.RePlugin

class BaseApplication: Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        RePlugin.App.attachBaseContext(this)
    }

    override fun onCreate() {
        super.onCreate()
        RePlugin.App.onCreate()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        RePlugin.App.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        RePlugin.App.onTrimMemory(level)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        RePlugin.App.onConfigurationChanged(newConfig)
    }
}