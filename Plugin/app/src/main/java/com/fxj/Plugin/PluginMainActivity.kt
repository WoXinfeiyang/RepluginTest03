package com.fxj.Plugin

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.qihoo360.replugin.RePlugin


class PluginMainActivity : AppCompatActivity(), View.OnClickListener {
    companion object{
        val TAG:String= PluginMainActivity::class.java.simpleName+"_fxj"
    }

    var hostContext: Context?=null

    var hostClassLoader:ClassLoader?=null
    var imageView:ImageView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn01).setOnClickListener(this)
        findViewById<Button>(R.id.btn02).setOnClickListener(this)
        imageView=findViewById(R.id.iv)
        findViewById<Button>(R.id.btn03).setOnClickListener(this)

        hostContext= RePlugin.getHostContext()
        hostClassLoader=RePlugin.getHostClassLoader()
        Log.d(TAG, "##onCreate##hostContext canonicalName=${hostContext?.javaClass?.canonicalName},hostClassLoader canonicalName=${hostClassLoader?.javaClass?.canonicalName}")
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn01 -> {
                val intent = Intent()
                intent.setComponent(
                    ComponentName(
                        "com.fxj.repluginhost",
                        "com.fxj.repluginhost.SecondActivity"
                    )
                )
                startActivity(intent)
            }
            R.id.btn02->{
                if(hostContext!=null){
                    var drawable= hostContext?.getResources()?.getDrawable(
                        hostContext?.getResources()!!
                            .getIdentifier("mickey_mouse_in_host", "drawable","com.fxj.repluginhost"))
                    if(drawable!=null&&imageView!=null){
                        imageView?.setImageDrawable(drawable)
                    }
                }
            }

            R.id.btn03->{
                jsonStrToBean()
            }
        }
    }

    private fun jsonStrToBean(){
        var jsonStr:String="{\"name\":\"ZhangSan\",\"age\":\"26\"}"
        val gson = Gson()
        val person: Person = gson.fromJson(jsonStr, Person::class.java)
        Log.d(TAG, "##jsonStrToBean##jsonStr=${jsonStr},person=${person}")
    }
}