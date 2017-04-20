package com.funhotel.tvllibrary.update;
/**
 * 升级应用程序时，接收到的更新数据
 * @author zhiayan
 *
 */
public class UpdateBean {
	/**
	 * 包名
	 */
	private String packageName;
	/**
	 * 版本名
	 */
	private String versionName;
	/**
	 * 版本号
	 */
	private int versionCode;
	/**
	 * APK的名称
	 */
	private String appName;
	/**
	 * 下载的地址
	 */
	private String downLoadUrl;


	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}


	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getDownLoadUrl() {
		return downLoadUrl;
	}

	public void setDownLoadUrl(String downLoadUrl) {
		this.downLoadUrl = downLoadUrl;
	}
	
	}
