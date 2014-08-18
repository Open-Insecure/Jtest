package com.br.dong.socket.socket2;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Client3 {
	public void connServer() {
		Socket tSocket= null;
		OutputStream os=null;
		InputStream is=null;
		try {
			tSocket= new Socket("localhost", 8888);// 建立与服务器端的链接
			tSocket.setSoTimeout(1000);
			os= tSocket.getOutputStream();
			os.write("我是客户2--hello world".getBytes()); //向服务器发起请求
			tSocket.shutdownOutput();
//			ByteArrayOutputStream byteout= new ByteArrayOutputStream();
			is= tSocket.getInputStream();
			byte[] buf= new byte[1024];
			int len= is.read(buf);
			String ret="";//服务器返回
			if(len>0){
//				byteout.write(buf, 0, len);
				ret= new String(buf);
			}
			System.out.println("------服务器返回22------"+ret.trim());
			tSocket.shutdownInput();
		} catch (SocketTimeoutException e) {
			System.out.println("连接服务器超时，超过1秒钟没有返回");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			System.err.println("找不到你指定的主机！" + e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}finally{
			try {
			    os.close();
				is.close();
				tSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Client3().connServer();
	}
}
