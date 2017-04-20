package com.funhotel.tvllibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by dell on 2016/1/14.
 */
public class NetWorkUtil {

    private static ConnectivityManager manager;

    public static Boolean haveNetWork(Context context) {
        // 网络判断
        if (null == context) {
            return false;
        }
        manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (null == info) {
            return false;
        }
        return info.isAvailable();
    }

}
