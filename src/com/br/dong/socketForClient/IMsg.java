package com.br.dong.socketForClient;

import java.net.Socket;

public interface IMsg {
	void sendUpdateListMsg(String msg);// 更新在线用户表，并附加msg信息

	void sendSingleMsg(String msg, String id);// 发送单个消息

	void sendBroadcastMsg(String msg);// 群发消息，自己除外

	void sendSystemMsg(String msg);// 系统消息，发送给所有人

	String makeMsg(String type, String msg, String from, String to);// 组装发送信息

	void sendMsg(String msg);// 发送信息
	
	Socket getSocket();

	void setSocket(Socket value);
	
	String getName();

	void setName(String value);

	String getID();

	void setID(String value);
}
