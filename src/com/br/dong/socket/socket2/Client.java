package com.br.dong.socket.socket2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
public class Client {
	public void connServer() {
		Socket s =null;
		PrintWriter pw =null;
		BufferedReader br =null;
		try {
			s = new Socket("localhost", 8888);// 建立与服务器端的链接
//			------------监听键盘事件，等待用户输入字符串 start-----------------
//			System.out.println("Please enter Your Name and Press Enter key:");
//			Scanner sc = new Scanner(System.in);// 得到Socket的输入流封装成一个PrintWriter
//			------------监听键盘事件，等待用户输入字符串 end-----------------
			pw= new PrintWriter(s.getOutputStream(), true);
			pw.println("hello world");//向服务器发起请求

			br= new BufferedReader(new InputStreamReader(s.getInputStream()));// 得到服务器端返回的输入流并封装成BufferedReader对象
			System.out.println("服务器发送给我的数据:" + br.readLine());

		} catch (UnknownHostException e) {
			System.err.println("找不到你指定的主机！" + e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}finally{
			try {
				pw.close();
				br.close();
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Client().connServer();
	}
}
