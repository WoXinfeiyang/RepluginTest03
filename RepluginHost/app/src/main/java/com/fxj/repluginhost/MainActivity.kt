package com.fxj.repluginhost

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.qihoo360.loader2.DumpUtils
import com.qihoo360.replugin.RePlugin
import com.qihoo360.replugin.model.PluginInfo
import com.qihoo360.replugin.packages.PluginRunningList


class MainActivity : Activity(), View.OnClickListener {
    companion object{
        val TAG:String=MainActivity::class.java.simpleName+"_fxj"
        val DIR_PATH="RepluginHost"
        val PLUGIN_FILE_NAME="com.fxj.Plugin.apk"

        /**插件名称,"纯Apk插件"的插件名为插件的PackageName*/
        val PLUGIN_NAME="com.fxj.Plugin"
    }

    var pi: PluginInfo?=null

    var apkPluginFilePath:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn01).setOnClickListener(this)
        findViewById<Button>(R.id.btn02).setOnClickListener(this)
        findViewById<Button>(R.id.btn03).setOnClickListener(this)
        findViewById<Button>(R.id.btn04).setOnClickListener(this)
        findViewById<Button>(R.id.btn05).setOnClickListener(this)
        findViewById<Button>(R.id.btn06).setOnClickListener(this)
        findViewById<Button>(R.id.btn07).setOnClickListener(this)

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
                        apkPluginFilePath=FileUtils.coyAssetsFileToDestDir(this, PLUGIN_FILE_NAME, DIR_PATH, PLUGIN_FILE_NAME)
                        Log.d(TAG,"复制插件按钮被点击,currentThread name=${Thread.currentThread().name},apkPluginFilePath=${apkPluginFilePath}")
                    }
                }.start()
            }
            R.id.btn02 -> {
                if(!TextUtils.isEmpty(this.apkPluginFilePath)){
                    pi= RePlugin.install(apkPluginFilePath)
                    if(pi!=null){
                        RePlugin.preload(pi)
                    }
                }else{
                    var msg="请先将assets目录下的apk插件文件复制到SD卡${DIR_PATH}文件夹下,然后再安装apk插件!"
                    Toast.makeText(this@MainActivity,msg,Toast.LENGTH_LONG).show()
                }
                Log.d(TAG,"安装插件按钮已被点击,install result PluginInfo=${pi},apkPluginFilePath=${apkPluginFilePath}")
            }

            R.id.btn03->{
                if(pi!=null){
                    RePlugin.startActivity(this@MainActivity, RePlugin.createIntent(
                        PLUGIN_NAME,
                        "com.fxj.Plugin.PluginMainActivity"));
                }
            }

            R.id.btn04->{
                var uninstallResult= RePlugin.uninstall(PLUGIN_NAME)
                Log.d(TAG,"卸载插件按钮被点击了,卸载结果为:${uninstallResult}")
            }
            R.id.btn05->{
                DumpUtils.dump(null,null,null)
            }
            R.id.btn06->{
                var allPluginInfoList:MutableList<PluginInfo>?= RePlugin.getPluginInfoList()

                var allPluginInfoMsg="获取所有插件信息按钮被点击了,所有插件数量=${allPluginInfoList?.size},所有插件信息allPluginInfoList=${allPluginInfoList}"

                Log.d(TAG,allPluginInfoMsg)

                Toast.makeText(this@MainActivity,allPluginInfoMsg,Toast.LENGTH_LONG).show()
            }
            R.id.btn07->{
                var pluginRunningList: PluginRunningList? = RePlugin.getRunningPlugins()
                var runningPluginsMsg="获取正在运行插件信息按钮被点击了,pluginRunningList=${pluginRunningList}"

                Log.d(TAG,runningPluginsMsg)

                Toast.makeText(this@MainActivity,runningPluginsMsg,Toast.LENGTH_LONG).show()
            }
        }
    }
}