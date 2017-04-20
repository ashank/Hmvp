package com.funhotel.mvp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;


/**
 * Created by dell on 2016/1/14.
 */
public class NetWorkUtil {


    /**
     * 判断是否有网络
     * @param context
     * @return  return true is have network  else  not have network
     */
    public static Boolean haveNetWork(Context context) {
        // 网络判断
        if (null == context) {
            return false;
        }
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (null == info) {
            return false;
        }
        return info.isAvailable();
    }

    /**
     * 是否是wifi网络
     * @param context
     * @return true为wifi网络，否则就不是wifi网络
     */
    public static int getNetWorkType(Context context) {
        // 网络判断
        if (null == context) {
            return -1;
        }
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null)
            return info.getType();
        else
        return -1;
    }



    public static String getLocalIP(Context context){
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return intToIp(ipAddress);
    }


    private static String intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

}
