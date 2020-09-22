package com.fxj.repluginhost

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.qihoo360.replugin.RePlugin

class SecondActivity : Activity(), View.OnClickListener {
    companion object{
        val TAG:String=SecondActivity::class.java.simpleName+"_fxj"
        val DIR_PATH="RepluginHost"
        val PLUGIN_FILE_NAME="com.fxj.Plugin.apk"

        /**插件名称,"纯Apk插件"的插件名为插件的PackageName*/
        val PLUGIN_NAME="com.fxj.Plugin"
    }

    var pluginContext: Context?=null
    var pluginClassLoader:ClassLoader?=null

    var image:ImageView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        findViewById<Button>(R.id.seconde_act_btn01).setOnClickListener(this)

        image=findViewById(R.id.seconde_act_iv)

        pluginContext=RePlugin.fetchContext(PLUGIN_NAME)
        pluginClassLoader=RePlugin.fetchClassLoader(PLUGIN_NAME)

        Log.d(TAG, "##onCreate##pluginContext canonicalName=${pluginContext?.javaClass?.canonicalName},pluginClassLoader canonicalName=${pluginClassLoader?.javaClass?.canonicalName}")

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.seconde_act_btn01->{
                if(pluginContext!=null){
                    var pluginResource: Resources? =pluginContext?.getResources()

                    var drawable= pluginResource?.getDrawable(
                        pluginResource!!
                            .getIdentifier("mickey_mouse_in_plugin", "drawable","com.fxj.Plugin"))

                    if(drawable!=null&&image!=null){
                        image?.setImageDrawable(drawable)
                    }
                    Log.d(TAG,"显示插件中的图片按钮被点击了!pluginResource=${pluginResource}")
                }
            }
        }
    }
}