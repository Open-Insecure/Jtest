package com.br.dong.socket.socket2;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
public class Client4 {
	public void connServer() {
		Socket tSocket= null;
		OutputStream os=null;
		InputStream is=null;
		try {
			tSocket= new Socket("localhost", 8888);// 建立与服务器端的链接
			tSocket.setSoTimeout(1000);
			os= tSocket.getOutputStream();
			os.write("我是客户3--hello world".getBytes()); //向服务器发起请求
			tSocket.shutdownOutput();
//			ByteArrayOutputStream byteout= new ByteArrayOutputStream();
			is= tSocket.getInputStream();
			String ret="";//服务器返回
			int count = 0;
			while (count == 0) {
				count = is.available();
			}
			byte[] b = new byte[count];
			//is.read(b);
			
			int readCount = 0; // 已经成功读取的字节的个数
			while (readCount < count) {
				readCount += is.read(b, readCount, count - readCount);
				ret = new String(b);
			}
			System.out.println("------服务器返回33------"+ret+"----");
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
		new Client4().connServer();
	}

}
