package com.br.dong.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SockClient {

	/**
	 * socket客户端
	 * */
	public static void main(String[] args) {
		//打开一个socket套接字
		try{
			Socket socket=new Socket("127.0.0.1",8189);
			try{
				//获取输入流，从服务端输入给客户端的
				InputStream inStream=socket.getInputStream();
				//使用Scanner读取服务端发送给客户端的每一行字符
				Scanner in=new Scanner(inStream);
				System.out.println("连上了");
				//获得服务端的输出流
				OutputStream outStream=socket.getOutputStream();
				PrintWriter pw =null;
				//输出流 作为服务端的输入流
				pw= new PrintWriter(socket.getOutputStream(), true);
				//读取控制台输入的
			    Scanner s=new Scanner(System.in);
			    String aaa = s.nextLine();
				/**
				 * 当完成了对一个流的读取或写入，应调用close方法将它关闭，可以释放所占用的系统资源
				 * 如果不关闭文件，最后一个字节可能永远不会被发送。也可以用flush方法手动刷新输出
				 * */

			    pw.println(aaa);
			    pw.flush();
			    while(in.hasNextLine())
				{
					String inp=in.nextLine();
				    System.out.println(inp);
				}
				
			  
				
			}finally{
			//关闭socket连接
			 socket.close();
			}
			
		}catch (Exception e) {
		  e.printStackTrace();
		}
	}
	
}
