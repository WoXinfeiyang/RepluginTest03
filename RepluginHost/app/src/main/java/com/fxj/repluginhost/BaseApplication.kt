package com.fxj.repluginhost

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import com.qihoo360.replugin.RePlugin
import com.qihoo360.replugin.RePluginCallbacks
import com.qihoo360.replugin.RePluginConfig
import com.qihoo360.replugin.RePluginEventCallbacks
import com.qihoo360.replugin.model.PluginInfo

class BaseApplication: Application() {

    companion object{
        val TAG=BaseApplication::class.java.simpleName+"_fxj"
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        Log.d(TAG,"##attachBaseContext##ContextWrapper.getBaseContext=${baseContext}")

        var rePluginConfig=RePluginConfig()

        /*当插件没有指定类时，是否允许使用宿主的类？若为true，则当插件内没有指定类时，将默认使用宿主的。*/
        rePluginConfig.setUseHostClassIfNotFound(true)

        rePluginConfig.setPrintDetailLog(BuildConfig.DEBUG)

        /*在Art上对首次加载插件速度做优化*/
        rePluginConfig.setOptimizeArtLoadDex(true)

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

        rePluginConfig.setCallbacks(object : RePluginCallbacks(base) {
            val TAG:String="RePluginCallbacks_fxj"

            override fun onPluginNotExistsForActivity(context: Context?, plugin: String?, intent: Intent?, process: Int): Boolean {

                Log.d(TAG,"##onPluginNotExistsForActivity##" +
                        "当要打开的Activity所对应的插件不存在时触发该方法,Context=${context}," +
                        "plugin=${plugin},intent=${intent},process=${process}")

                return super.onPluginNotExistsForActivity(context, plugin, intent, process)
            }

            override fun onLoadLargePluginForActivity(context: Context?, plugin: String?, intent: Intent?, process: Int): Boolean {
                Log.d(TAG,"##onLoadLargePluginForActivity##context=${context},plugin=${plugin},intent=${intent},process=${process}")
                return super.onLoadLargePluginForActivity(context, plugin, intent, process)
            }
        })

        RePlugin.App.attachBaseContext(this,rePluginConfig)
    }

    override fun onCreate() {
        super.onCreate()
        RePlugin.App.onCreate()
        Log.d(TAG,"##onCreate##p-n型插件安装的路径=${RePlugin.getConfig().pnInstallDir}")
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