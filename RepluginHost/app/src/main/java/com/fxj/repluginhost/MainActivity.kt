package com.fxj.repluginhost

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View
import android.widget.Button
import com.qihoo360.replugin.RePlugin
import com.qihoo360.replugin.model.PluginInfo


class MainActivity : Activity(), View.OnClickListener {
    companion object{
        val TAG:String=MainActivity::class.java.simpleName
        val DIR_PATH="RepluginHost"
        val PLUGIN_FILE_NAME="com.fxj.Plugin.apk"
    }

    var pi: PluginInfo?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn01).setOnClickListener(this)
        findViewById<Button>(R.id.btn02).setOnClickListener(this)
        findViewById<Button>(R.id.btn03).setOnClickListener(this)

        checkPermission()
    }


    fun checkPermission(){

        var permissionWriteExternalStorage:Boolean= PackageManager.PERMISSION_GRANTED ===packageManager.checkPermission(
            "android.permission.WRITE_EXTERNAL_STORAGE",
            packageName
        )
        var permissionReadExteranlStorage:Boolean= PackageManager.PERMISSION_GRANTED ===packageManager.checkPermission(
            "android.permission.READ_EXTERNAL_STORAGE",
            packageName
        )
        while(!permissionWriteExternalStorage||!permissionReadExteranlStorage){
            ActivityCompat.requestPermissions(
                this@MainActivity, arrayOf<String>(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 0x01
            )
            permissionWriteExternalStorage= PackageManager.PERMISSION_GRANTED ===packageManager.checkPermission(
                "android.permission.WRITE_EXTERNAL_STORAGE",
                packageName
            )

            permissionReadExteranlStorage= PackageManager.PERMISSION_GRANTED ===packageManager.checkPermission(
                "android.permission.READ_EXTERNAL_STORAGE",
                packageName
            )
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn01 -> {
                Thread{
                    run{
                        Log.d(TAG,"currentThread name=${Thread.currentThread().name}")
                        FileUtils.coyAssetsFileToDestDir(this, PLUGIN_FILE_NAME, DIR_PATH, PLUGIN_FILE_NAME)
                    }
                }.start()
            }
            R.id.btn02 -> {
                var pluginApkPath:String="/sdcard/RepluginHost/com.fxj.Plugin.apk"
                pi= RePlugin.install(pluginApkPath)
                if(pi!=null){
                    RePlugin.preload(pi)
                }
            }

            R.id.btn03->{
                if(pi!=null){
                    RePlugin.startActivity(this@MainActivity, RePlugin.createIntent("com.fxj.Plugin",
                        "com.fxj.Plugin.PluginMainActivity"));
                }
            }
        }
    }
}