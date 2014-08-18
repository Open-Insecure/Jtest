package com.br.dong.socket;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * ����һ��socket�����Ϊ����ͻ����ṩ����
 * */
public class ThreadedEchoServer {
	//�˿ں�
	private static int PORT=8189;
	public static void main(String[] args) {
		
		try{
			int i=1;
			//���Դ���Socket������
			ServerSocket s=new ServerSocket(PORT);
			System.out.println("�����˿�"+PORT+"�ɹ�");
			//ѭ�������׽���
			while(true){
				/**�����ͣ�ڴ˴� �ȴ��ͻ������ӡ����ͻ��������˴�Server�󣬳���Ż�������
				* ���ͻ������������ӳɹ��� �ᴴ��һ��socket���� �����ＴΪincoming
				* ͨ����һ��Thread ����һ���߳���ִ�д˿ͻ��������˵�ͨ��
				* ͬʱ����˵ȴ���һ���ͻ������ӣ�����һ���߳�ִ��ͨ��
				* 
				*/
				Socket incoming=s.accept();
				System.out.println("��ǰ�ͻ���������"+i);
				//����һ���ɱ��߳�ִ�е�Runableʵ��
				Runnable r=new ThreadedEchoHandler(incoming,i);
				//����һ���߳�
				Thread t=new Thread(r);
				//�߳̿�ʼִ��
				t.start();
				//������+1
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
