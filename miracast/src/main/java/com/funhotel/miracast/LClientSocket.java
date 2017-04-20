package com.funhotel.miracast;

import android.util.Log;

import com.funhotel.mvp.utils.DebugUtil;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @ClassName：LClientSocket
 * @Description：TODO Socket Client端
 * @Author：Linker
 * @Date：2014-9-23 上午11:25:13
 * @version
 */
public class LClientSocket {
	
	public static String SUCCESS="SUCCESS";
	public static String FAILED="FAILED";
	
	/**
	 * Socket实例
	 */
	private Socket clientSocket=null;
	/**
	 * IP地址
	 */
	private String ip;
	/**
	 * 端口号
	 */
	private int port;
	/**
	 * 输出输入流实例
	 */
	private DataOutputStream out_messageStream;
	private DataInputStream in_messageStream;


	private FileInputStream reader = null;
	private byte[] buf = null;


	/**
	* <p>Title: LClientSocket </p> 
	* <p>Description: 构造器</p> 
	* @param ip  IP地址
	* @param port  端口号
	 */
	public LClientSocket(String ip,int port) {
		// TODO Auto-generated constructor stub
		this.ip=ip; 
		this.port=port;
	}
	
	/**
	 * @Title: connetionSocket
	 * @Description: TODO  Client端链接Server
	 * @return  boolean if true Connect Success or Connect faile;
	 */
	public boolean connetionSocket() {
		// TODO Auto-generated method stub
    	   try {
    		   //实例化对象
			   clientSocket=new Socket(ip, port);
			   //链接超时时间
			   clientSocket.setSoTimeout(70*1000);
			   if (clientSocket!=null) {
				   DebugUtil.v("连接服务器成功");
				   return true;
			   }else {
				   DebugUtil.v("连接服务器失败");
				   return false;
			   }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				DebugUtil.e("connetionSocket   EX ------>  "+e.getMessage());
				return false;
			}
		}

	public Socket getClientSocket() {
		return clientSocket;
	}

	/**
	 * @Title: sendMessage
	 * @Description: TODO  发送数据至服务器
	 * @param message  String 发送消息的文本
	 * @throws Exception
	 */
	public void sendMessage( String message) throws Exception {
		try {
			if (clientSocket.isConnected()) {
				out_messageStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
				out_messageStream.writeBytes(message);
				out_messageStream.flush();
				DebugUtil.v("客户端发送数据给服务器成功");
			}
		} catch (Exception e) {
			DebugUtil.v("sendMessage  EX   Error=="+e.getMessage());
			e.printStackTrace();
			if (out_messageStream != null) {
				try {
					out_messageStream.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return;
	}


	public void sendFile(File file) throws IOException{
		Log.e("TAG", "socket");
		reader = new FileInputStream(file);
		out_messageStream = new DataOutputStream(clientSocket.getOutputStream());
		/*out_messageStream.writeInt(100);
		out_messageStream.writeUTF("send");*/
		int bufferSize = 20480; // 20K
		buf = new byte[bufferSize];
		int read = 0;
		// 将文件输入流 循环 读入 Socket的输出流中
		while ((read = reader.read(buf, 0, buf.length)) != -1) {
			out_messageStream.write(buf, 0, read);
		}
		Log.e("TAG", "socket执行完成");
		out_messageStream.flush();
		reader.close();
		out_messageStream.close();
		// 一定要加上这句，否则收不到来自服务器端的消息返回
//			clientSocket.shutdownOutput();

		// //获取服务器端的相应
//			in_messageStream = new DataInputStream(clientSocket.getInputStream());
//			int status = in_messageStream.readInt();
//			String result = in_messageStream.readUTF();
//			Log.e("", "返回结果：" + status + "," + result);


	}





	
	
	/**
	* @Title: getMessage 
	* @Description: TODO 获取Server端发送过来的数据
	* @return String  
	* @throws
	 */
	public String getMessage() {
		// TODO Auto-generated method stub
		String result=null;
		try {
			if (clientSocket.isConnected()) {
				DebugUtil.v("socket连接成功，正在获取来自server数据....");
				//第一种读取数据方式
				/*in_messageStream=new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
				byte[] bs = new byte[4*1024];
				int len = 0;
				while((len=in_messageStream.read(bs))!=-1)
				{
					DebugUtil.v(LinkerApplication.getInstance().TAG, "clientSocket接收数据的长度==="+len);
					result = new String(bs, 0, len);
					break;
				}
				bs=null;
				in_messageStream=null;*/
				
				//第二种读取数据方式
				BufferedReader br =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				result = br.readLine();
				DebugUtil.v("接收数据完成.....");
				br=null;
				return  result;
			}else {
				DebugUtil.v("未连接至服务器...");
				return  result;
			}
		} catch (Exception e) {
			// TODO: handle exception
			DebugUtil.v("getMessage EX Error=="+e.getMessage());
			if (in_messageStream != null) {
				try {
					in_messageStream.close();
					in_messageStream=null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					DebugUtil.v("IOE Error=="+e.getMessage());
				}
			}
			return result;
		}
		
	}
	/**
	 * 
	 * @Title: ShutDownConnection
	 * @Description: TODO 关闭socket
	 * @return void
	 */
	public  void ShutDownLSocket() {
		try {
			if (null!=out_messageStream) {
				out_messageStream.close();
				out_messageStream=null;
			}
			if (null!=in_messageStream) {
				in_messageStream.close();
			}
			
			if (clientSocket != null) {
				clientSocket.close();
				clientSocket=null;
			}
			DebugUtil.v("LClientSocket-------释放socket资源");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	/**
	 * @Title: isConnect
	 * @Description: TODO  判断socket是否在链接状态
	 * @return
	 */
	public boolean isConnect() {
		// TODO Auto-generated method stub
		if (null==clientSocket) {
			return false;
		}
		try{  
			clientSocket.sendUrgentData(0xFF);  
			DebugUtil.d("socket正在接通中>>>>>>>>>>>>");
			return true;  
			}catch(IOException e){  
			DebugUtil.d(e+":socket链接失败，要关掉了阿 ！");  
//			ShutDownLSocket();
		    return false;
		    
			}  
	}
}
