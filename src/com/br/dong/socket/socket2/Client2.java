package com.br.dong.socket.socket2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;
public class Client2 {
	public void connServer() {
		Socket s =null;
		PrintWriter out =null;
		BufferedReader in =null;
		try {
			s = new Socket("localhost", 8888);// 建立与服务器端的链接
			s.setSoTimeout(1000);//设置超时时间 1秒钟
//			------------监听键盘事件，等待用户输入字符串 start-----------------
//			System.out.println("Please enter Your Name and Press Enter key:");
//			Scanner sc = new Scanner(System.in);// 得到Socket的输入流封装成一个PrintWriter
//			------------监听键盘事件，等待用户输入字符串 end-----------------
			out= new PrintWriter(s.getOutputStream(), true);
			out.println("我是客户1--hello world");//向服务器发起请求

			in= new BufferedReader(new InputStreamReader(s.getInputStream()));// 得到服务器端返回的输入流并封装成BufferedReader对象
			System.out.println("服务器发送给我的数据:" + in.readLine());

		}catch (SocketTimeoutException e) {
			System.out.println("连接服务器超时，超过1秒钟没有返回");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			System.err.println("找不到你指定的主机！" + e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}finally{
			try {
				out.close();
				in.close();
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Client2().connServer();
	}
}
