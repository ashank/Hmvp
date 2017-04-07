package com.funhotel.mvp.utils;

import android.util.Log;


/**
 * @Title: DebugUtil
 * @Description: 打印Log信息的类
 * @author: LinWeiDong
 * @data: 2016/8/8 17:47
 */
public class DebugUtil {
    
    public static boolean debugOpen=true;
    private static String TAG="TAG";
    
    /**
     * @param msg
     * @return void
     * @throws
     * @Title: v
     * @Description: TODO  Verbose
     */
    public static void v(String msg) {
        if (debugOpen) {
            if (msg != null) {
                Log.v(TAG, msg);
            } else {
                Log.v(TAG, "vmsg的值为null");
            }
        }
    }

    /**
     * @param msg
     * @return void
     * @throws
     * @Title: d
     * @Description: TODO  Debug
     */
    public static void d(String msg) {
        if (debugOpen) {
            if (msg != null) {
                Log.d(TAG, msg);
            } else {
                Log.d(TAG, "dmsg的值为null");
            }
        }
    }

    /**
     * @param msg
     * @return void
     * @throws
     * @Title: i
     * @Description: TODO  Info
     */
    public static void i(String msg) {
        if (debugOpen) {
            if (msg != null) {
                Log.i(TAG, msg);
            } else {
                Log.i(TAG, "imsg的值为null");
            }
        }
    }

    /**
     * @param msg
     * @return void
     * @throws
     * @Title: w
     * @Description: TODO  Warn
     */
    public static void w(String msg) {
        if (debugOpen) {
            if (msg != null) {
                Log.w(TAG, msg);
            } else {
                Log.w(TAG, "wmsg的值为null");
            }
        }
    }

    /**
     * @param msg
     * @return void
     * @throws
     * @Title: es
     * @Description: TODO  Error
     */
    public static void e(String msg) {
        if (debugOpen) {
            if (msg != null) {
                Log.e(TAG, msg);
            } else {
                Log.e(TAG, "emsg的值为null");
            }
        }
    }
}