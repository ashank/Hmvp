package com.funhotel.tvllibrary.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.Handler;
import android.text.TextUtils;

import com.funhotel.tvllibrary.utils.AppFileUtil;
import com.funhotel.tvllibrary.utils.DebugUtil;

/**
 * @ClassName：FileDownLoader
 * @Description： Http方式下载文件
 * @Author：Zhiyahan
 * @Date：2014-9-22 下午6:46:06
 * @version
 */
public class FileDownLoader {
	/**
	 * File
	 */
	private File file=null;
	/**
	 * 标记是否在下载中
	 */
	private static boolean isDownloading=false;
	
	/** 下载中 */
	public static final int DOWNLOAD = 1;
	/** 下载结束 */
	public static final int DOWNLOAD_FINISH = 2;
	/** 下载失败 */
	public static final int DOWNLOAD_ERROR = 3;
	/** 下载进度*/
	public static  int progress=0;
	
	private String fileUrl;
	private String fileName;
	private Handler mHandler;
	
	/**
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param fileUrl
	* @param fileName
	 */
	public FileDownLoader(String fileUrl, String fileName) {
		// TODO Auto-generated constructor stub
		this.fileUrl=fileUrl;
		this.fileName=fileName;
	}
	
	
	/**
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param fileUrl
	* @param fileName
	* @param mHandler
	 */
	public FileDownLoader(String fileUrl, String fileName,Handler mHandler) {
		// TODO Auto-generated constructor stub
		this.fileUrl=fileUrl;
		this.fileName=fileName;
		this.mHandler=mHandler;
	}
	
	
	/**
	 * @Title: download
	 * @Description: TODO 下载文件
	 */
	public File download() {
		// TODO Auto-generated method stub
		//开始下载
		isDownloading=true;
		//输入流
		InputStream in=null;
		//输出流
		FileOutputStream out=null;
		//下载文件的长度
		int lenght=0;
		try {

			//下载地址为空
			if (TextUtils.isEmpty(fileUrl)) {
				DebugUtil.v("FileDownLoader FileUrl is null");
				downLoadError();
				return null;
			}

			//判断SD卡是否可用
			if (AppFileUtil.isExternalStorageAvailable()) {
				//建立URL连接
				URL url=new URL(fileUrl);
				//使用url.openConnection()获得URLConnection对象；
				URLConnection con=(URLConnection)url.openConnection();
				System.setProperty("sun.net.client.defaultConnectTimeout", "120000"); //连接主机的超时时间（单位：毫秒）
				System.setProperty("sun.net.client.defaultReadTimeout", "120000"); //从主机读取数据的超时时间（单位：毫秒）
				con.setReadTimeout(2*60*1000);
				con.setConnectTimeout(2*60*1000);
				//连接
				con.connect();
				//获得输入流
				in=con.getInputStream();
				//得到输入流的长度
				lenght=con.getContentLength();
				//创建文件
				file=new File(AppFileUtil.createFile(AppFileUtil.DOWNLOAD_FOLDER),fileName);
				out=new FileOutputStream(file);
				//文件写出累计
				int len = 0;
				//退出标记
				int numread =0;
				byte[] buf = new byte[4*1024];
				while ((numread=in.read(buf))!=-1) {
					len+=numread;
					DebugUtil.v("FileDownLoader 已经下载  "+(int) (((float) len / lenght) * 100)+"%");
					if (null!=mHandler) {
						//获得进度
						progress=(int) (((float) len / lenght) * 100);
						if (null!=mHandler) {
							mHandler.sendEmptyMessage(DOWNLOAD);
						}
					}
					//写入数据
					out.write(buf,0,numread);
					//刷新缓冲
					out.flush();
					isDownloading=true;
					if (null!=mHandler) {
						if (len==lenght) {
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
						}
					}
				}
				//关闭流
				out.close();
				in.close();
				isDownloading=false;
			}else {
				DebugUtil.v("FileDownLoader SDCard is ont exist or unuserful");
				downLoadError();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DebugUtil.v("FileDownLoader MalformedURLError"+e.getMessage());
			downLoadError();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DebugUtil.v("FileDownLoader IOE Error=="+e.getMessage());
			downLoadError();
		}catch (Exception e) {
			// TODO: handle exception
			DebugUtil.v("FileDownLoader EX Error=="+e.getMessage());
			downLoadError();
		}
		return file;
	}
	
	
	/**
	 * @Title: deletFile
	 * @Description: TODO 如果下载中途失败 则删除文件,并清空数据
	 */
	private void deletFile() {
		// TODO Auto-generated method stub
		if (null!=file&&file.exists()) {
			AppFileUtil.deleteFile(file.getPath());
			file=null;
		}
	}

	/**
	 * @Title: downLoadError
	 * @Description: TODO  下载时失败的处理
	 */
	private void downLoadError() {
		// TODO Auto-generated method stub
		isDownloading=false;
		deletFile();
		if (null!=mHandler) {
			mHandler.sendEmptyMessage(DOWNLOAD_ERROR);
		}
	}
	
	
	/**
	 * @Title: isDownloading
	 * @Description: TODO  是否正在下载中
	 * @return
	 */
	public static boolean isDownloading() {
		return isDownloading;
	}
	
	

}
