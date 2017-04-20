package com.funhotel.tvllibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Title: DeviceUtil
 * @Description: 设备的一些信息
 * @author: LinWeiDong
 * @data: 2016/4/19 14:39
 */
public class DeviceUtil {

    WindowManager wm;
    DisplayMetrics displayMetrics;

    public DeviceUtil(Context context) {
        // TODO Auto-generated constructor stub
        wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        displayMetrics = new DisplayMetrics();
    }

    public int getScreenWidth() {
        return wm.getDefaultDisplay().getWidth();
    }

    public int getScreenHeight() {
        return wm.getDefaultDisplay().getHeight();
    }


    /**
     * @param mContext
     * @Title: resetProgram
     * @Description: TODO 重启软件
     */
    public static void resetProgram(Context mContext) {
        Intent i = ((ContextWrapper) mContext)
                .getBaseContext()
                .getPackageManager()
                .getLaunchIntentForPackage(((ContextWrapper) mContext).getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(i);
        ((Activity) mContext).finish();
        System.exit(0);
    }


    /**
     * @return 当前时间
     * @Title: getCurrentTime
     * @Description: TODO 获取当前时间 yyyy_MM_dd_HH_mm_ss
     */
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
                .format(new Date(System.currentTimeMillis()));
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param res
     * @param dp
     * @return
     */
    public static int dpToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }

    /**
     * 获取屏幕宽度
     */
    public static int getWidthPixels(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getHeightPixels(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * @param @param  context
     * @param @return 设定文件
     * @return int    返回类型
     * @Description: TODO获取版本号
     */
    public static int getVersionCode(Context context) {
        int verCode = 0;
        try {
            verCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * @param @param  context
     * @param @return 设定文件
     * @return String    返回类型
     * @Description: TODO获取版本名称
     */
    public static String getVersionName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * @param @param  context
     * @param @return 设定文件
     * @return String    返回类型
     * @Description: TODO获取手机IMEI
     */
    public static String getDeviceToken(Context context) {
        String deviceToken = "";
        String androidId;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (null == telephonyManager){
            deviceToken = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
        }else{
            deviceToken = telephonyManager.getDeviceId();
        }
        return deviceToken;
    }

    /**
     * @param @param  context
     * @param @return 设定文件
     * @return String    返回类型
     * @Description: TODO获取手机系统类型Android
     */
    public static String getDeviceType() {
        String deviceType = "Android";
        return deviceType;
    }

    /**
     * 强制软键盘的关闭,如果显示就门关闭,如果关闭,则不处理
     * @param context
     */
    public  static  void dealWithSoftKeyBoard(View view,Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
        if (isOpen){
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        }
    }

}
