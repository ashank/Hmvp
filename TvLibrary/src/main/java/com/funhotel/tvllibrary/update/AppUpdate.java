package com.funhotel.tvllibrary.update;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.funhotel.tvllibrary.R;
import com.funhotel.tvllibrary.utils.AppFileUtil;
import com.funhotel.tvllibrary.utils.DebugUtil;

import java.io.File;

/**
 * 
 * @ClassName: AppUpdate
 * @Description: TODO 软件更新
 * @author Zhiyahan
 * @date 2013-6-17 下午5:08:54
 */
public class AppUpdate {

	/**
	 * app信息实例
	 */
	private AppInfo version;
	/**
	 * 程序对象
	 */
	private Context context;

	private UpdateUrlBean updateBean;

	/**
	 * APP当前版本号
	 */
	private int currentVersion = 0;

	/**
	 * 服务器的版本号
	 */
	private int serverVersion = 0;
	/**
	 * 服务器版本名
	 */
	private String serverVersionName;
	/**
	 * 下载地址
	 */
	private String downLoadUrl;
	/**
	 * APK名称
	 */
	private String apkName="install.apk";
	/**
	 * 进度条对象
	 */
	private ProgressBar mProgress;
	/**
	 * 对话框实例
	 */
	private Dialog mDownloadDialog;

	/**
	 * 文件
	 */
	private File file;

	/** 已经是最新版本了 */
	private static final int NOT_UPDATE = 4;
	
	/**
	 * 执行动作的类型
	 */
	private int type=0;
	
	/**
	 * 什么也不做
	 */
	public static final int DO_NOTHING=101;
	/**
	 * 打开程序
	 */
	public static final int DO_OPEN=102;
	/**
	 * 重启系统
	 */
	public static final int DO_RESER_SYSTEM=103;

	/**
	 * ServiceConnection 实例
	 */
	@SuppressWarnings("unused")
	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName className) {
			Log.e("app", "没有连接");
		}

		@Override
		public void onServiceConnected(ComponentName className, IBinder myBind) {
			Log.e("funhotel", "连接中......");
		}
	};

	/**
	 * 更新UI
	 */
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case FileDownLoader.DOWNLOAD:
				// 更新进度条
				mProgress.setProgress(FileDownLoader.progress);
				break;
			case FileDownLoader.DOWNLOAD_FINISH:
				// 取消对黄框
				mDownloadDialog.cancel();
				break;
			case FileDownLoader.DOWNLOAD_ERROR:
				mDownloadDialog.cancel();
				break;
			case NOT_UPDATE:
				break;
			default:
				break;
			}
		};
	};


	public AppUpdate(Context context,UpdateUrlBean updateBean) {
		this.context = context;
		this.updateBean=updateBean;
		
	}
	/**
	 * @Title: ComparetVersion
	 * @Description: TODO  对比版本号是否决定更新
	 * @return void 返回true 说明有新版本要更新，返回false则说明已经是最新版本了
	 * @throws
	 */
	public Boolean comparetVersion() {
		Boolean ifupdateBoolean = false;
		//获取当前软件的版本号
		version = new AppInfo(context);
		currentVersion = version.getCurrentVersionCode();
		if (updateBean==null) {
			return false;
		}
		if (updateBean != null) {
			serverVersion = updateBean.getVersionCode();
			downLoadUrl = updateBean.getDownLoadUrl();
			serverVersionName = updateBean.getVersionName();
		}
		
		if (currentVersion==0||serverVersion==0) {
			return false;
		}
		
		if (serverVersion != currentVersion) {
			ifupdateBoolean = true;
		} else {
			ifupdateBoolean = false;
		}
		
		return ifupdateBoolean;
	}

	/**
	 * @Title: updateApp
	 * @Description: TODO  更新程序的主要过程
	 * @param  isshowToast  是否弹出提示
	 * @throws
	 */
	public void updateApp(boolean isshowToast) {
		// TODO Auto-generated method stub
		if (comparetVersion()) {
			// 如果碰上更新程序的时候，则先删除文件
			if (FileDownLoader.isDownloading()) {
				AppFileUtil.deleteFolderFile(AppFileUtil.DOWNLOAD_FOLDER);
			}
			//开始更新程序
			showDownloadDialog();
		} else {
			if (isshowToast) {
				mHandler.sendEmptyMessage(NOT_UPDATE);
			}
			DebugUtil.v( "已经是最新版本了");
		}
	}

	/**
	 * @Title: showDownloadDialog
	 * @Description: TODO  显示软件下载对话框
	 */
	private void showDownloadDialog() {
		mDownloadDialog = new Dialog(context, R.style.EvaDialog);
		// 给下载对话框增加进度条
		final LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.dialog_update, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);

		TextView currentView = (TextView) v.findViewById(R.id.current);
		TextView updateView = (TextView) v.findViewById(R.id.update);
		
		currentView.setText("当前版本：" + version.getCurrentVersionName());
		updateView.setText("更新版本：" + serverVersionName);

		mDownloadDialog.setContentView(v);
		
		// 禁止点击dialog的坐标 消失dialog
		mDownloadDialog.setCanceledOnTouchOutside(false);
		mDownloadDialog.show();
		mDownloadDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if(keyCode==KeyEvent.KEYCODE_BACK){
					//当正在更新时，禁止返回键
					return true;
				}
				return false;
			}
		});
		// 下载文件
		new DownLoadThread().start();
	}

	/**
	 * @ClassName: DownLoadThread
	 * @Description: TODO(下载客户端线程)
	 * @author Zhiyahan
	 * @date 2013-6-18 上午11:33:53
	 */
	public class DownLoadThread extends Thread {

		public DownLoadThread() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			FileDownLoader fileDownLoader=new FileDownLoader(downLoadUrl, apkName, mHandler);
			file=fileDownLoader.download();
			//标记已经更新完或者不再更新
			if (null!=file){
				DebugUtil.v( "文件路径>>>>>>>"+file.toString());
				//安装APK
				installApk();
			}else {
				DebugUtil.v( "文件路径>>>>>>>NULL");
			}
			super.run();
		}
	}
	
	
	/**
	 * @Title: installApk
	 * @Description: TODO  安装APK
	 */
	private void installApk() {
		// TODO Auto-generated method stub
		try {
			if (AppFileUtil.isFileExist(file.getPath())) {
//				ApkInstallUtils.installAndStartApk(context,file.getPath());
				/*Intent install_hide_intent = new Intent("android.intent.action.VIEW.BACK");
				install_hide_intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
				install_hide_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(install_hide_intent);*/
				try {
					Intent intent = new Intent("intent.action.install");
					intent.putExtra("filePath",file.getPath());
					intent.putExtra("type", type);
					context.startActivity(intent);
				} catch (ActivityNotFoundException e) {
					// TODO: handle exception
					/*Toast.makeText(context, "UpdateServiceNotFound", Toast.LENGTH_LONG).show();*/
					//正常模式
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(file),
							"application/vnd.android.package-archive");
					context.startActivity(intent);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//正常模式
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file),
					"application/vnd.android.package-archive");
			context.startActivity(intent);
		}
	}

}