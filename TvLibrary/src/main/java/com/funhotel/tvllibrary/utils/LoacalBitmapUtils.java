package com.funhotel.tvllibrary.utils;

import android.graphics.Bitmap;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Title: LoacalBitmapUtils
 * @Description:
 * @author: Zhang Yetao
 * @data: 2016/7/26 15:42
 */
public class LoacalBitmapUtils {

    /**
     *  文件名的截取
     */
    public String getImgName(String url){
        String [] arr = url.split("/");
        return arr[arr.length-1];
    }

    /**
     * 获取本地图片
     */
    public Bitmap getLoacalBitmap(String name) {
        if(TextUtils.isEmpty(name)){
            return null;
        }
        File file = new File(AppFileUtil.createFile("mainResource"), name);
        return BitmapUtil.getBitmapFromFile(file.toString());

    }

    /**
     * 保存图片到本地
     */
    public void saveDrawable(String url, String name) {
        try {
            //创建一个文件
            File file = new File(AppFileUtil.createFile("mainResource"),name);
            if (!file.exists()){
                file.createNewFile();
            }
            //创建一个url对象
            URL imgUrl = new URL(url);
            // 使用HttpURLConnection打开连接
            HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
//                    len = is.read(buffer);
            }
            //关闭流
            is.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
