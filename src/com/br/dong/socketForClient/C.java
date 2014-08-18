package com.br.dong.socketForClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 用来放置常量
 */
public class C {
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");//格式化日期
	public static final List<IMsg> socketManager = new ArrayList<IMsg>();//在线人数
	public static final int SERVER_PORT = 719;// 端口
	public static final String UPDATELIST_MSG = "0";// 更新在线人数
	public static final String SINGLE_MSG = "1";// 发单用户消息
	public static final String BROADCAST_MSG = "2";// 广播群发消息
	public static final String SYSTEM_MSG = "3";// 发送系统消息
	
	public static final String INTERVAL="__##__##__";//分隔符
	public static final String mySocketClass="com.br.dong.socketForClient.MySocket";

}
