package com.br.dong.socketForClient;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;
import java.util.Iterator;
/**
 * 实现了Runnable的run方法 用来给Thread类初始化调用。
 * 即使用了多线程来创建socket的链接，为多个客户端服务
 * MySocket包含了Socket 即客户端与服务端的套接字连接
 * */
public class MySocket implements Runnable, IMsg {
	private Socket socket; // 套接字引用变量
	private BufferedReader reader; // 套接字输入流
	private DataOutputStream writer; // 套接字输出流
	private String name;
	private String id;
	/**
	 * 当完成了对一个流的读取或写入，应调用close方法将它关闭，可以释放所占用的系统资源
	 * 如果不关闭文件，最后一个字节可能永远不会被发送。也可以用flush方法手动刷新输出
	 * */

	public MySocket() {

	}
	public void run() {
		try {
			String msg;
			while ((msg = reader.readLine()) != null)// 如果收到客户端发来的数据
			{ // 向客户端发送信息
				String[] arr = msg.split(C.INTERVAL);
				if (arr.length == 4) {
					setName(arr[1]);
					setID(arr[2] + C.INTERVAL + arr[3]);
					System.out.println("注册客户端:" + arr[2] + C.INTERVAL + arr[3]);

					sendUpdateListMsg(getName() + "上线,共"
							+ C.socketManager.size() + "人在线。");
				} else if (arr.length == 3) {
					sendSingleMsg(arr[2], arr[0] + C.INTERVAL + arr[1]);
					System.out.println("客户端消息:" + name + "-" + arr[0] + " "
							+ C.dateFormat.format(new Date()) + ":" + arr[2]);
				} else {
					sendBroadcastMsg(msg);
					System.out.println("客户端消息:" + name + "-所有人 "
							+ C.dateFormat.format(new Date()) + ":" + msg);
				}
			}
		} catch (Exception e) {
			System.out.println("err");
		} finally {
			try {
				//删除list中的此IMsg的引用
				C.socketManager.remove(this); // 删除套接字
				// 关闭输入输出流及套接字
				if (reader != null)
					reader.close();
				if (writer != null)
					writer.close();
				if (socket != null)
					socket.close();
				reader = null;
				writer = null;
				socket = null;
				sendUpdateListMsg(name + "下线,共" + C.socketManager.size()
						+ "人在线。");
				// broadcastMsg(name + "下线,共" + sManager.size() + "人在线。",
				// "系统消息");

				System.out.println("客户端离开:" + this.getName());// 向屏幕输出相关信息
				System.out.println("当前客户端的连接数:" + C.socketManager.size());
			} catch (Exception e) {
			}
		}

	}
    //创建xml格式的string返回
	public String makeMsg(String type, String msg, String from, String to) {
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><root>";

		xml += addTag(msg, "msg") + addTag(from, "from") + addTag(to, "to")
				+ addTag(type, "type")
				+ addTag(C.dateFormat.format(new Date()), "time");

		xml += "</root>\r\n";
		return xml;
	}

	public void sendBroadcastMsg(String msg) {
		Iterator<IMsg> iter = (Iterator<IMsg>) C.socketManager.listIterator();
		IMsg s;
		while (iter.hasNext()) {
			s = iter.next();
			if (s.getName() != name)
				try {
					s.sendMsg(makeMsg(C.BROADCAST_MSG, msg, name, "所有人"));
				} catch (Exception e) {
					System.err.println(s.getName() + "-------BROADCAST_MSG");
				}
		}
	}

	public void sendSingleMsg(String msg, String id) {
		Iterator<IMsg> iter = (Iterator<IMsg>) C.socketManager.listIterator();
		IMsg s;
		while (iter.hasNext()) {
			s = iter.next();
			if (s.getID().equals(id)) {
				try {
					s.sendMsg(makeMsg(C.SINGLE_MSG, msg, name, id));
				} catch (Exception e) {
					System.err.println(s.getName() + "-------SINGLE_MSG");
				}
				return;
			}
		}
		System.err.println("出错没有此人:" + name + "-" + id + ":" + msg);
	}

	public void sendSystemMsg(String msg) {
		Iterator<IMsg> iter = (Iterator<IMsg>) C.socketManager.listIterator();
		IMsg s;
		while (iter.hasNext()) {
			s = iter.next();
			try {
				s.sendMsg(makeMsg(C.SYSTEM_MSG, msg, "系统消息", "所有人"));
			} catch (Exception e) {
				System.err.println(s.getName() + "-------SYSTEM_MSG");
			}
		}
	}

	public void sendUpdateListMsg(String msg) {
		String $msg = addTag(msg, "msg");
		Iterator<IMsg> iter = (Iterator<IMsg>) C.socketManager.listIterator();
		IMsg s;
		$msg += addTag("所有人" + C.INTERVAL + "所有人", "user");
		while (iter.hasNext()) {
			s = iter.next();
			$msg += addTag(s.getID(), "user");
		}
		iter = (Iterator<IMsg>) C.socketManager.listIterator();
		while (iter.hasNext()) {
			s = iter.next();
			try {
				s.sendMsg(makeMsg(C.UPDATELIST_MSG, $msg, "系统消息", "所有人"));
			} catch (Exception e) {
				System.err.println(s.getName() + "-------UPDATELIST_MSG");
			}
		}

	}
	//服务端发送信息给客户端
	public void sendMsg(String msg) {
		try {
			// System.out.println(msg);
			writer.writeUTF(msg);
			writer.flush();
		} catch (Exception e) {
			System.err.println("发送信息出错\n" + msg);
		}
	}

	public Socket getSocket() {
		return socket;
	}
    //设置当前socket的连接
	public void setSocket(Socket value) {
		socket = value;
		try {
			//获得输入流
			reader = new BufferedReader(new InputStreamReader(this.socket
					.getInputStream(), "UTF-8"));
			// 获取套接字的输出流
			writer = new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException i) {
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	public String getID() {
		return id;
	}

	public void setID(String value) {
		id = value;
	}

	private String addTag(String content, String tag) {
		return "<" + tag + ">" + content + "</" + tag + ">";
	}
}
