package com.funhotel.tvllibrary.update;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;

import com.funhotel.tvllibrary.utils.DebugUtil;

/**
 * 
* @ClassName: AppInfo 
* @Description: TODO 获取设备已安装的apk版本信息
* @author Zhiyahan
* @date 2013-6-17 下午4:48:42
 */
public class AppInfo {
	private  PackageManager packageManager;
	private  PackageInfo packageInfo;
	private Context mContext;
	
	/**
	* <p>Title: </p> 
	* <p>Description: 构造器</p> 
	* @param context
	 */
	public AppInfo(Context context) {
		this.mContext=context;
		try {
			packageManager=mContext.getPackageManager();
		    packageInfo=packageManager.getPackageInfo(mContext.getPackageName(), PackageManager.GET_CONFIGURATIONS);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			DebugUtil.v("AppInfo   "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param context
	* @param apkPath
	 */
	public AppInfo(Context context,String apkPath) {
		// TODO Auto-generated constructor stub
		this.mContext=context;
		packageManager=mContext.getPackageManager();
		packageInfo=packageManager.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
	}
	
	/**
	 * @Title: getAppPackageName
	 * @Description: TODO  获取包名
	 * @return  返回包名
	 */
	public  String getAppPackageName() {
		if (null==packageInfo) {
			return null;
		}
		return packageInfo.packageName;
	}
	
	/**
	 * @Title: getCurrentVersionName
	 * @Description: TODO 版本名
	 * @return 返回软件的版本名
	 */
	public  String getCurrentVersionName() {
		if (null==packageInfo) {
			return null;
		}
		return packageInfo.versionName;
	}

	/**
	 * @Title: getCurrentVersionCode
	 * @Description: TODO 版本号
	 * @return 返回软件的版本号
	 */
	public  int getCurrentVersionCode() {
		if (null==packageInfo) {
			return 0;
		}
		return packageInfo.versionCode;
	}
	
	
	/**
	 * @Title: findActivitiesForPackage
	 * @Description: TODO  获得未安装的apk的各种信息
	 * @param context
	 * @param packageName
	 * @return List<ResolveInfo>  返回apk的信息
	 */
    public  List<ResolveInfo> findActivitiesForPackage(Context context, String packageName) {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mainIntent.setPackage(packageName);

        final List<ResolveInfo> apps = packageManager.queryIntentActivities(mainIntent, 0);
        return apps != null ? apps : new ArrayList<ResolveInfo>();
    }
	
	
	/**
	 * @Title: shutdown
	 * @Description: TODO 释放资源
	 */
	public void shutdown() {
     packageManager=null;
     packageInfo=null;
	}

}
