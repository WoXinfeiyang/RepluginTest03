package com.fxj.repluginhost

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import com.qihoo360.replugin.RePlugin
import com.qihoo360.replugin.RePluginConfig
import com.qihoo360.replugin.RePluginEventCallbacks
import com.qihoo360.replugin.model.PluginInfo

class BaseApplication: Application() {

    companion object{
        val TAG=BaseApplication::class.java.simpleName
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        var rePluginConfig=RePluginConfig()
        rePluginConfig.setEventCallbacks(object: RePluginEventCallbacks(base) {
            val TAG:String="RPEventCallbacks_fxj"

            override fun onInstallPluginFailed(path: String?, code: InstallResult?) {
                Log.d(TAG,"##onInstallPluginFailed##path=${path},InstallResult=${code}")
            }

            override fun onInstallPluginSucceed(info: PluginInfo?) {
                Log.d(TAG,"##onInstallPluginSucceed##PluginInfo=${info}")
            }

            override fun onPrepareAllocPitActivity(intent: Intent?) {
                Log.d(TAG,"##onPrepareAllocPitActivity##,当插件Activity准备分配坑位时执行该方法,Intent=${intent}")
            }

            override fun onPrepareStartPitActivity(
                context: Context?,
                intent: Intent?,
                pittedIntent: Intent?
            ) {
                Log.d(TAG,"##onPrepareStartPitActivity##intent=${intent},pittedIntent=${pittedIntent}")
            }
            override fun onStartActivityCompleted(
                plugin: String?,
                activity: String?,
                result: Boolean
            ) {
                Log.d(TAG,"##onStartActivityCompleted##plugin=${plugin},activity=${activity},result=${result}")
            }
        })
        RePlugin.App.attachBaseContext(this,rePluginConfig)
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