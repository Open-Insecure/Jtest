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
			s = new Socket("localhost", 8888);// ������������˵�����
			s.setSoTimeout(1000);//���ó�ʱʱ�� 1����
//			------------���������¼����ȴ��û������ַ��� start-----------------
//			System.out.println("Please enter Your Name and Press Enter key:");
//			Scanner sc = new Scanner(System.in);// �õ�Socket����������װ��һ��PrintWriter
//			------------���������¼����ȴ��û������ַ��� end-----------------
			out= new PrintWriter(s.getOutputStream(), true);
			out.println("���ǿͻ�1--hello world");//���������������

			in= new BufferedReader(new InputStreamReader(s.getInputStream()));// �õ��������˷��ص�����������װ��BufferedReader����
			System.out.println("���������͸��ҵ�����:" + in.readLine());

		}catch (SocketTimeoutException e) {
			System.out.println("���ӷ�������ʱ������1����û�з���");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			System.err.println("�Ҳ�����ָ����������" + e.getMessage());
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
