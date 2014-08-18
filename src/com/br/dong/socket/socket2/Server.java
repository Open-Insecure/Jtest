package com.br.dong.socket.socket2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {
	public void startServer() {
		ServerSocket ss = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		Socket s = null;
		try {
			ss = new ServerSocket(8888);// 创建绑定到特定端口的服务器套接字。
			while (true) {// 时刻监听客户端的连接
				System.out.println("等待客户端发起连接。。。");
				s = ss.accept();// 接受客户端的连接
//				String RemoteIP = s.getInetAddress().getHostAddress();//获取客户端的IP和端口，并打印出来
//				String RemotePort = ":" + s.getLocalPort();//获取客户端的IP和端口，并打印出来
//				System.out.println("A client come in!IP:" + RemoteIP + RemotePort);

				br = new BufferedReader(new InputStreamReader(s.getInputStream()));// 得到接收的这个Socket的输入流，并封装成一个BufferedReader()
				String clientMsgString = br.readLine();
				System.out.println("The Client Says:" + clientMsgString);//服务器端控制台打印出接受到的消息

				pw = new PrintWriter(s.getOutputStream(), true);// 将从客户端接收到的数据再发送回客户端
				pw.println("Hello client,you just said,'" + clientMsgString+"'");//向客户端问好
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {// 关闭相应的资源
			try {
				br.close();
				pw.close();
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Server().startServer();
	}
}
