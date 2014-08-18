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
   //�������ͻ��˵��׽���
   private Socket incoming;
   //������
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
				 * ���������͸��������������������Ϣ���Ϊ�ͻ��˳�������룬
				 * �ͻ��˵�����������������ڷ���������������
				 * */
				//��ȡ���������ӿͻ������������˵�
				InputStream inStream=incoming.getInputStream();
				//��������ӷ����������ͻ���
				OutputStream outStream=incoming.getOutputStream();
				//�ͻ�������������
				Scanner in=new Scanner(inStream);
				//�����������ͻ���
				PrintWriter out=new PrintWriter(outStream,true/**�Զ�ˢ��*/);
				out.println("����BYE�˳�");
				//�Ͽ���ʶ
				boolean done=false;
				while(!done&&in.hasNextLine()){
					String line=in.nextLine();
					out.println("�����.."+line);
					if(line.trim().equals("BYE")){
						out.println("�Ͽ�");
						done=true;
					}
				}
				
				
			}finally{
				//�ر��׽���
				
				incoming.close();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
