package com.br.dong.socket;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 创建一个socket服务端为多个客户端提供服务
 * */
public class ThreadedEchoServer {
	//端口号
	private static int PORT=8189;
	public static void main(String[] args) {
		
		try{
			int i=1;
			//尝试创建Socket服务器
			ServerSocket s=new ServerSocket(PORT);
			System.out.println("创建端口"+PORT+"成功");
			//循环创建套接字
			while(true){
				/**程序会停在此处 等待客户端连接。当客户端连接了此Server后，程序才会往下走
				* 当客户端与服务端连接成功后 会创建一个socket连接 在这里即为incoming
				* 通过另一个Thread 另起一个线程来执行此客户端与服务端的通信
				* 同时服务端等待下一个客户端连接，另起一个线程执行通信
				* 
				*/
				Socket incoming=s.accept();
				System.out.println("当前客户端连接数"+i);
				//创建一个可被线程执行的Runable实例
				Runnable r=new ThreadedEchoHandler(incoming,i);
				//创建一个线程
				Thread t=new Thread(r);
				//线程开始执行
				t.start();
				//计数器+1
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
