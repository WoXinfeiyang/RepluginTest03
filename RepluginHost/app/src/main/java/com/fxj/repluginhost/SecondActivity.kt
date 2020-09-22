package com.fxj.repluginhost

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.qihoo360.replugin.RePlugin

class SecondActivity : Activity() {
    companion object{
        val TAG:String=SecondActivity::class.java.simpleName+"_fxj"
        val DIR_PATH="RepluginHost"
        val PLUGIN_FILE_NAME="com.fxj.Plugin.apk"

        /**插件名称,"纯Apk插件"的插件名为插件的PackageName*/
        val PLUGIN_NAME="com.fxj.Plugin"
    }

    var pluginContext: Context?=null
    var pluginClassLoader:ClassLoader?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        pluginContext=RePlugin.fetchContext(PLUGIN_NAME)
        pluginClassLoader=RePlugin.fetchClassLoader(PLUGIN_NAME)

        Log.d(TAG, "##onCreate##pluginContext canonicalName=${pluginContext?.javaClass?.canonicalName},pluginClassLoader canonicalName=${pluginClassLoader?.javaClass?.canonicalName}")

    }
}