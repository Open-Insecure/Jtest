package com.br.dong.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SockClient {

	/**
	 * socket�ͻ���
	 * */
	public static void main(String[] args) {
		//��һ��socket�׽���
		try{
			Socket socket=new Socket("127.0.0.1",8189);
			try{
				//��ȡ���������ӷ����������ͻ��˵�
				InputStream inStream=socket.getInputStream();
				//ʹ��Scanner��ȡ����˷��͸��ͻ��˵�ÿһ���ַ�
				Scanner in=new Scanner(inStream);
				System.out.println("������");
				//��÷���˵������
				OutputStream outStream=socket.getOutputStream();
				PrintWriter pw =null;
				//����� ��Ϊ����˵�������
				pw= new PrintWriter(socket.getOutputStream(), true);
				//��ȡ����̨�����
			    Scanner s=new Scanner(System.in);
			    String aaa = s.nextLine();
				/**
				 * ������˶�һ�����Ķ�ȡ��д�룬Ӧ����close���������رգ������ͷ���ռ�õ�ϵͳ��Դ
				 * ������ر��ļ������һ���ֽڿ�����Զ���ᱻ���͡�Ҳ������flush�����ֶ�ˢ�����
				 * */

			    pw.println(aaa);
			    pw.flush();
			    while(in.hasNextLine())
				{
					String inp=in.nextLine();
				    System.out.println(inp);
				}
				
			  
				
			}finally{
			//�ر�socket����
			 socket.close();
			}
			
		}catch (Exception e) {
		  e.printStackTrace();
		}
	}
	
}
