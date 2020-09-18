package com.fxj.repluginhost;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by fuxianjin-hj on 2017/12/3.
 */

public class FileUtils {

    private static final String TAG=FileUtils.class.getSimpleName();
    /**检查SD卡是否存在*/
    public static boolean checkSDCard(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){/*如果手机插入了SD卡,且应用程序具有读写SD卡的权限*/
            return true;
        }
        return false;
    }

    /**创建文件*/
    public static void makeFie(String file){
        File f=new File(file);
        if(f!=null&&!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**创建文件夹*/
    public static void makeDir(String dir){
        File f=new File(dir);
        if(!f.exists()){
            f.mkdirs();
        }else if(!f.isDirectory()){
            deleteFile(f);
            f.mkdirs();
        }
    }

    /**
     * 根据路径获取一个文件夹的名称,如果传入的参数是一个文件的路径则获取该文件所在文件夹的名称
     * @param path 一个文件或文件夹的路径
     * @return String 文件夹的名称,如果传入的参数是一个文件的路径则获取该文件所在文件夹的名称
     * */

    public static String getDirName(String path){
        if(TextUtils.isEmpty(path)){
           return null;
        }
        int index=path.lastIndexOf("/");
        return path.substring(0,index);
    }

    /**根据文件路径获取一个文件的名称
     * @param path 一个文件的路径
     * @return String 文件名称
     * */
    public static String getFileName(String path){
        if(TextUtils.isEmpty(path)){
           return "";
        }
        int index=path.lastIndexOf("/");
        return path.substring(index+1,path.length());
    }

    /**
     * 删除文件或者目录
     *
     * @param filepath 要删除的文件路径
     */
    public static void deleteFile(String filepath) {
        File file = new File(filepath);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if(files != null) {
                    if (files.length > 0) {
                        File[] delFiles = file.listFiles();
                        if (delFiles != null && delFiles.length > 0) {//fix  delFiles 为空导致空指针
                            for (File delFile : delFiles) {
                                deleteFile(delFile.getAbsolutePath());
                            }
                        }
                    }
                }
            }
            file.delete();
        }
    }
    /**
     * 删除文件或者目录
     *
     * @param file 要删除的文件
     */
    public static void deleteFile(File file) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File[] filelist = file.listFiles();
                if (filelist != null && filelist.length > 0) {
//                    File[] delFiles = file.listFiles();
                    for (File delFile : filelist) {
                        if (delFile.exists())
                            deleteFile(delFile);
                    }
                }
            }
            file.delete();
        }
    }
    /**创建一个App根目录
     * @param context 上下文
     * @param AppDirName App根目录名称
     * @return String 返回App根目录路径
     * */
    public static String getAppDir(Context context,String AppDirName){
        String AppDir="";
        if(checkSDCard()){
            AppDir=Environment.getExternalStorageDirectory().getAbsolutePath();
        }else{

        }
        AppDir=AppDir+File.separator+AppDirName+File.separator;
        makeDir(AppDir);
        return AppDir;
    }

    /**
     * 给指定文件夹创建.nomedia文件
     * @param dirPath 指定文件夹路径
     * */
    public static void makeNoMediaFile(String dirPath){
        String NoMediaFilePath=dirPath+File.separator+".nomedia";
        makeFie(NoMediaFilePath);
    }

    /**
     * 删除nomedia
     *
     * @param path
     */
    public static void deleteNoMediaFile(String path) {
        String filePath = path + File.separator + ".nomedia";
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 将Assets目录下的文件拷贝到SD卡上的某个文件夹下<p>
     * @param assetsFilePath assets目录下源文件路径
     * @param destDir 目标文件夹路径
     * @param destFileName 目标文件夹下的目标文件名称
     * @return 目标文件的路径
     * */
    public static String coyAssetsFileToDestDir(Context context,String assetsFilePath,String destDir,String destFileName){
        AssetManager am= context.getAssets();
        try {
            InputStream is= am.open(assetsFilePath);
            String destDirPath=Environment.getExternalStorageDirectory().getAbsolutePath().endsWith(File.separator)?
                    Environment.getExternalStorageDirectory().getAbsolutePath()+destDir
                    :Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+destDir;

            File destFile=new File(destDirPath,destFileName);

            if(!destFile.exists()){
                destFile.createNewFile();
            }else if(destFile.isDirectory()){
                deleteFile(destFile);
                destFile.createNewFile();
            }
            Log.d(TAG,"##coyAssetsFileToDestDir##destFile Path="+destFile.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            is.close();
            fos.close();
            return destFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
