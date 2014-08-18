package com.br.dong.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
/**
 *  
 * */
public class ThreadedEchoHandler implements Runnable {
   //服务端与客户端的套接字
   private Socket incoming;
   //连接数
   private int count;

	
   public ThreadedEchoHandler(Socket incoming, int count) {
	super();
	this.incoming = incoming;
	this.count = count;
}
public Socket getIncoming() {
		return incoming;
	}
	public void setIncoming(Socket incoming) {
		this.incoming = incoming;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public void run() {
		try{
			try{
				/**
				 * 服务器发送给服务器输出流的所有信息会成为客户端程序的输入，
				 * 客户端的所有输出都被包括在服务器的输入流中
				 * */
				//获取输入流，从客户端输入给服务端的
				InputStream inStream=incoming.getInputStream();
				//输出流，从服务端输出给客户端
				OutputStream outStream=incoming.getOutputStream();
				//客户端输入给服务端
				Scanner in=new Scanner(inStream);
				//服务端输出给客户端
				PrintWriter out=new PrintWriter(outStream,true/**自动刷新*/);
				out.println("输入BYE退出");
				//断开标识
				boolean done=false;
				while(!done&&in.hasNextLine()){
					String line=in.nextLine();
					out.println("输入的.."+line);
					if(line.trim().equals("BYE")){
						out.println("断开");
						done=true;
					}
				}
				
				
			}finally{
				//关闭套接字
				
				incoming.close();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
