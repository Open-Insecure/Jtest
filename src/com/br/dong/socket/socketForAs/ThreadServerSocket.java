package com.br.dong.socket.socketForAs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import com.br.dong.socket.socketForAs.IMsg;
import com.br.dong.socketForClient.C;


public class ThreadServerSocket implements Runnable, IMsg {
	private Socket socket; // �׽������ñ���
	private BufferedReader reader; // �׽���������
	private DataOutputStream writer; // �׽��������
	private String name;
	//�߳����еĳ���
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			System.out.println("thread����run");
			String msg;
			//����ȡ����������Ϊ��
			while((msg=reader.readLine())!=null){
				System.out.println("��ȡ�ͻ��������"+msg);
				if(msg.equals("hehe")){
					System.out.println(msg+"��¼��");
					sendMsg("../images/star.png");
				}else{
					sendMsg("����Ĳ���hehe����������");
				}
			}
		}catch(Exception e){
			
		}finally{
			try{
				//ɾ��list�еĴ�IMsg������
				C.socketManager.remove(this); // ɾ���׽���
				// �ر�������������׽���
				if (reader != null)
					reader.close();
				if (writer != null)
					writer.close();
				if (socket != null)
					socket.close();
				reader = null;
				writer = null;
				socket = null;
				System.out.println("�ͻ����뿪:");// ����Ļ��������Ϣ
				System.out.println("��ǰ�ͻ��˵�������:" + C.socketManager.size());
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	/**
	 * ����socket,����socket�����������������������
	 * */
	public void setSocket(Socket s){
		socket=s;
		try {
			//���������
			reader = new BufferedReader(new InputStreamReader(this.socket
					.getInputStream(), "UTF-8"));
			// ��ȡ�׽��ֵ������
			writer = new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException i) {
		}
	}

	//����˷�����Ϣ���ͻ���
	public void sendMsg(String msg) {
		try {
			// System.out.println(msg);
			writer.writeUTF(msg);
			writer.flush();
		} catch (Exception e) {
			System.err.println("������Ϣ����\n" + msg);
		}
	}
}
