package com.funhotel.tvllibrary.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName: AppFileUtil
 * @Description: TODO 文件管理、创建文件，删除文件、保存到文件等操作
 * @author Zhiyahan
 * @date 2015年9月25日 下午2:56:25
 */
public class AppFileUtil {
	/**
	 * 单位
	 */
	private static int oneMB=1*1024*1024;
	/**
	 * APP总目录
	 */
	public static String  ROOT_FOLDER="IPTV";
	/**
	 * 错误文件的存放目录
	 */
	public static String  ERROR_FILE_FOLDER="ErrorLog";
	/**
	 * 下载文件的文件夹目录
	 */
	public static String  DOWNLOAD_FOLDER="Download";
	
	/**
	 * 临时存放目录
	 */
	public static String  TEMP_FOLDER="Temp";
	
	/**
	 * 临时存放目录
	 */
	public static String  DCIM="DCIM";

	/**
	 * 语音泸州存放目录
	 */
	public static String  RECORD="Record";
	
	
	
	
	/**
	 * @Title: createRootFiles
	 * @Description: TODO 创建根目录文件
	 * @return {@link File}
	 */
	private static File createRootFiles() {
		File file=null;
		try {
			 //在System权限中，这个方法并不可行
			 file = new File(Environment.getExternalStorageDirectory(),ROOT_FOLDER);
			 if (!file.exists()) {
				 file.mkdirs();
			 }
			DebugUtil.i("根路径----->"+file);	
		} catch (Exception e) {
			// TODO: handle exception
			DebugUtil.i( "Error====="+e.getMessage());	
		}
		return file;
	}
	
	/**
	 * @Title: createFile
	 * @Description: TODO 在根目录下 创建文件夹
	 * @param fileName 文件名
	 * @return  {@link File}
	 */
	public static File createFile(String fileName) {
		// 创建一个文件
		File dir = new File(createRootFiles(),fileName);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		DebugUtil.i("FileManager---new File---->"+dir.toString());
		return dir;
	}
	
	/**
	 * @Title: saveFile
	 * @Description: TODO  String类型保存到SD卡
	 * @param source  {@link String} 资源
	 * @param filePath  {@link String} 文件路径
	 * @param stringname  {@link String}文件名
	 */
	public static void saveFile(String source,String filePath,String stringname) {
		// TODO Auto-generated method stub
		// 创建一个文件
		File stringFile = new File(filePath, stringname);
		if (!stringFile.exists()) {
			try {
				stringFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream out = new FileOutputStream(stringFile);
			out.write(source.getBytes());
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @Title: readFile
	 * @Description: TODO 读取SD卡的文件 转化成String
	 * @param filePath {@link String}文件路径
	 * @param stringname {@link String}文件名
	 * @return {@link String}
	 */
	public static String readFile(String filePath,String stringname) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		int a;
		// 创建一个文件
		File stringFile = new File(filePath, stringname);
		if (stringFile.exists()) {
			try {
				FileInputStream in = new FileInputStream(stringFile);
				try {
					while ((a = in.read()) != -1) {
						sb.append((char) a);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * @Title: deleteFolderFile
	 * @Description: TODO  删除sd卡的文件夹以及文件夹中的文件
	 * @param foldername 文件夹名
	 * @return {@link Boolean}
	 */
	public static Boolean deleteFolderFile(String foldername) {
		File file = new File(createRootFiles(),foldername);
		if (file == null || !file.exists() || file.isFile()) {
			return false;
		}
		//删除文件夹的文件
	    for (File files : file.listFiles()) {  
            if (files.isFile()) {  
                files.delete();  
            } else if (files.isDirectory()) {  
            	deleteFolderFile(files.toString());// 递归   
            }  
        }  
	    //删除文件夹
		file.delete();
		return true;
	}
	
	/**
	 * @Title: deleteFile
	 * @Description: TODO  删除指定文件夹里的单个文件
	 * @param filePath 文件的路径
	 * @return  boolean
	 */
	public static boolean deleteFile(String filePath){
		File file=new  File(filePath);
		if (file==null||!file.exists()||file.isDirectory()) {
			return false;
		}
		//删除文件
		file.delete();
		return true;
	}
	
	/**
	 * @Title: isExternalStorageAvailable
	 * @Description: TODO 判断存储空间是否可用
	 * @return  boolean
	 */
	public static boolean isExternalStorageAvailable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}
	
	/**
	 * @Title: isFileExist
	 * @Description: TODO 判断某个文件夹下的某个文件是否存在
	 * @param folderName  文件夹名
	 * @param fileName  文件名
	 * @return boolean
	 */
	public static File isFileExist(String folderName,String fileName) {
		File file=new File(createFile(folderName), fileName);
		if (file.exists()) {
			return file;
		}else {
			return null;
		}
	}
	
	/**
	 * @Title: isFileExist
	 * @Description: TODO  判断某个文件夹下的某个文件是否存在
	 * @param filePath 文件的路径
	 * @return  boolean
	 */
	public static boolean isFileExist(String filePath) {
		File file=new File(filePath);
		if (file.exists()) {
			return true;
		}else {
			return false;
		}
	}
	
	
	/**
	 * @Title: getTotalExternalStorageSize
	 * @Description: TODO  获取SD卡总空间
	 * @return  long
	 */
	public static  long getTotalExternalStorageSize() {
		if (AppFileUtil.isExternalStorageAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize/oneMB;
		} else {
			return -1;
		}
	}

	
	/**
	 * @Title: getAvailableExternalStorageSize
	 * @Description: TODO  获得剩余存储空间
	 * @return long
	 */
	public static long getAvailableExternalStorageSize() {
		if (AppFileUtil.isExternalStorageAvailable()) {
			File file = Environment.getExternalStorageDirectory();
			StatFs statFs = new StatFs(file.getPath());
			long blockSize = statFs.getBlockSize();
			long availableBlocks = statFs.getAvailableBlocks();
			return blockSize * availableBlocks/oneMB;
		}
		return -1;
	}
	
	
	/**
	 * @Title: getAvailableInternalStorageSize
	 * @Description: TODO  获取空闲的内存空间
	 * @return  long
	 */
    public static long getAvailableInternalStorageSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize/oneMB;
    }
    
    
    /**
     * @Title: getTotalInternalStorageSize
     * @Description: TODO 获取总内存空间
     * @return
     */
    public static  long getTotalInternalStorageSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();      
        long totalBlocks = stat.getBlockCount();  
        return totalBlocks * blockSize/oneMB;
    }
	
	
	
	

}
