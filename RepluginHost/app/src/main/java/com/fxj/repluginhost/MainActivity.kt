package com.fxj.repluginhost

import android.app.Activity
import android.os.Bundle
import android.util.Log

class MainActivity : Activity() {

    val TAG:String=MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var appDir= FileUtils.getAppDir(this,"RepluginHost")
        Log.d(TAG,"##onCreate##appDir=${appDir}")
    }
}