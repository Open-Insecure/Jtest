package com.br.dong.socketForClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * �������ó���
 */
public class C {
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");//��ʽ������
	public static final List<IMsg> socketManager = new ArrayList<IMsg>();//��������
	public static final int SERVER_PORT = 719;// �˿�
	public static final String UPDATELIST_MSG = "0";// ������������
	public static final String SINGLE_MSG = "1";// �����û���Ϣ
	public static final String BROADCAST_MSG = "2";// �㲥Ⱥ����Ϣ
	public static final String SYSTEM_MSG = "3";// ����ϵͳ��Ϣ
	
	public static final String INTERVAL="__##__##__";//�ָ���
	public static final String mySocketClass="com.br.dong.socketForClient.MySocket";

}
