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
			s = new Socket("localhost", 8888);// ������������˵�����
//			------------���������¼����ȴ��û������ַ��� start-----------------
//			System.out.println("Please enter Your Name and Press Enter key:");
//			Scanner sc = new Scanner(System.in);// �õ�Socket����������װ��һ��PrintWriter
//			------------���������¼����ȴ��û������ַ��� end-----------------
			pw= new PrintWriter(s.getOutputStream(), true);
			pw.println("hello world");//���������������

			br= new BufferedReader(new InputStreamReader(s.getInputStream()));// �õ��������˷��ص�����������װ��BufferedReader����
			System.out.println("���������͸��ҵ�����:" + br.readLine());

		} catch (UnknownHostException e) {
			System.err.println("�Ҳ�����ָ����������" + e.getMessage());
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
