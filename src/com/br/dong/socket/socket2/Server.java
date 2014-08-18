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
			ss = new ServerSocket(8888);// �����󶨵��ض��˿ڵķ������׽��֡�
			while (true) {// ʱ�̼����ͻ��˵�����
				System.out.println("�ȴ��ͻ��˷������ӡ�����");
				s = ss.accept();// ���ܿͻ��˵�����
//				String RemoteIP = s.getInetAddress().getHostAddress();//��ȡ�ͻ��˵�IP�Ͷ˿ڣ�����ӡ����
//				String RemotePort = ":" + s.getLocalPort();//��ȡ�ͻ��˵�IP�Ͷ˿ڣ�����ӡ����
//				System.out.println("A client come in!IP:" + RemoteIP + RemotePort);

				br = new BufferedReader(new InputStreamReader(s.getInputStream()));// �õ����յ����Socket��������������װ��һ��BufferedReader()
				String clientMsgString = br.readLine();
				System.out.println("The Client Says:" + clientMsgString);//�������˿���̨��ӡ�����ܵ�����Ϣ

				pw = new PrintWriter(s.getOutputStream(), true);// ���ӿͻ��˽��յ��������ٷ��ͻؿͻ���
				pw.println("Hello client,you just said,'" + clientMsgString+"'");//��ͻ����ʺ�
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {// �ر���Ӧ����Դ
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
