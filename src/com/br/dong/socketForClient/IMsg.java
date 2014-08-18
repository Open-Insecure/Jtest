package com.br.dong.socketForClient;

import java.net.Socket;

public interface IMsg {
	void sendUpdateListMsg(String msg);// ���������û���������msg��Ϣ

	void sendSingleMsg(String msg, String id);// ���͵�����Ϣ

	void sendBroadcastMsg(String msg);// Ⱥ����Ϣ���Լ�����

	void sendSystemMsg(String msg);// ϵͳ��Ϣ�����͸�������

	String makeMsg(String type, String msg, String from, String to);// ��װ������Ϣ

	void sendMsg(String msg);// ������Ϣ
	
	Socket getSocket();

	void setSocket(Socket value);
	
	String getName();

	void setName(String value);

	String getID();

	void setID(String value);
}
