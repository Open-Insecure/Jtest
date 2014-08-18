package com.br.dong.socketForClient;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
public class Test {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 设置一个timer模拟发送系统消息
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				//随即发送系统消息
				if ((int) (Math.random() * 100) > 50
						&& C.socketManager.size() > 0) {
					C.socketManager.get(0).sendSystemMsg("欢迎各位来到聊天室，祝大家聊得开心。");
				}
			}
		}, 0, 60 * 1000);
		// socket启动
		ServerSocket server = null;
		try {
			server = new ServerSocket(C.SERVER_PORT);
			System.out.println("服务器套接字已创建成功！");
			while (true) {
				//当server接收到了一个客户端的连接后，即创建了两者之间的一个socket实例
				Socket socket = server.accept();
				Class c = Class.forName(C.mySocketClass);
				//创建Mysocket的实例
				IMsg s = (IMsg) c.newInstance();
				//set socket
				s.setSocket(socket);
				//新起一个线程来处理一个socket链接
				new Thread((Runnable) s).start();
				//将此Mysocket的实例放入list中。
				//注意记得这个概念，s 只是IMsg这个实例的引用  操作s即相当于通过引用操作真正的实例
				C.socketManager.add(s);
				System.out.println("当前客户端连结数：" + C.socketManager.size());
			}
		} catch (Exception e) {
		} finally {
			try {
				server.close();
			} catch (Exception e) {
			}
		}

	}
}
