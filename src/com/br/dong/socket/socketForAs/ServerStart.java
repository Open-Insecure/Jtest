package com.br.dong.socket.socketForAs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerStart {
	public static void main(String[] args) {
		ServerSocket server=null;
		try {
			server=new ServerSocket(C.SERVER_PORT);
			System.out.println("创建套接字成功");
			//循环
			while(true){
				Socket socket=server.accept();
				ThreadServerSocket ts=new ThreadServerSocket();
				ts.setSocket(socket);
				new Thread(ts).start();
				C.socketManager.add(ts);
				System.out.println("当前客户端连结数：" + C.socketManager.size());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
