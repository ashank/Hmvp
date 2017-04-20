package com.funhotel.tvllibrary.update;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.funhotel.tvllibrary.utils.DebugUtil;


/**
 * @ClassName：ApkInstallUtils   
 * @Description：    TODO auto install apk in phone or tablet
 * but the phone or tablet must be rooted!
 * @Author：Zhiyahan
 * @Date：2014-9-23 下午1:45:48   
 * @version
 */
public class ApkInstallUtils {

	/**
	 * @Title: installAndStartApk
	 * @Description: TODO  安装
	 * @param context
	 * @param apkPath
	 */
	public static void installAndStartApk(final Context context, final String apkPath) {
		if ((apkPath==null) || (context==null)) {
			return;
		}
		File file = new File(apkPath);
		if (file.exists() == false) {
			return;
		}
		new Thread() {
			public void run() {
				if (silentInstall(apkPath)) {
					//获取包名
					AppInfo mAppInfo=new AppInfo(context,apkPath);
					String packageName = mAppInfo.getAppPackageName();
					DebugUtil.e("InstallApkUtils---"+packageName);
					
					List<ResolveInfo> matches = mAppInfo.findActivitiesForPackage(context, packageName);
					if ((matches!=null) && (matches.size()>0)) {
						
						ResolveInfo resolveInfo = matches.get(0);
						ActivityInfo activityInfo = resolveInfo.activityInfo;
						//安装程序
						startApk(activityInfo.packageName, activityInfo.name);
						DebugUtil.e("InstallApkUtils----安装成功");
					}
				}else {
					//正常模式

				}
			};
		}.start();
	}
	
	/**
	 * @Title: silentInstall
	 * @Description: TODO  能否静默安装
	 * @param apkPath
	 * @return
	 */
	private static boolean silentInstall(String apkPath) {
		String cmd1 = "chmod 777 " + apkPath + " \n";
		String cmd2 = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r " + apkPath + " \n";
		return execWithSID(cmd1, cmd2);
	}

	/**
	 * @Title: execWithSID
	 * @Description: TODO  检测是否ROOT权限
	 * @param args
	 * @return
	 */
	private static boolean execWithSID(String... args) {
		boolean isSuccess = false;
		Process process = null;
		OutputStream out = null;
		try {
			process = Runtime.getRuntime().exec("su");
			out = process.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(out);

			for (String tmp : args) {
				dataOutputStream.writeBytes(tmp);
			}

			dataOutputStream.flush(); // 提交命令
			dataOutputStream.close(); // 关闭流操作
			out.close();
			
			isSuccess = waitForProcess(process);
			DebugUtil.e("PackInstaller-----"+ isSuccess);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}
    
	/**
	 * @Title: startApk
	 * @Description: TODO  安装
	 * @param packageName
	 * @param activityName
	 * @return  boolean
	 */
	private static boolean startApk(String packageName, String activityName) {
		boolean isSuccess = false;
		String cmd = "am start -n " + packageName + "/" + activityName + " \n";
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			isSuccess = waitForProcess(process);
		} catch (IOException e) {
			DebugUtil.e("PackInstaller-----"+ e.getMessage());
			e.printStackTrace();
		} 
		return isSuccess;
	}
	
	/**
	 * @Title: waitForProcess
	 * @Description: TODO  等待
	 * @param p
	 * @return
	 */
	private static boolean waitForProcess(Process p) {
		boolean isSuccess = false;
		int returnCode;
		try {
			returnCode = p.waitFor();
			switch (returnCode) {
			case 0:
				isSuccess = true;
				break;
				
			case 1:
				break;
				
			default:
				break;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}
}
