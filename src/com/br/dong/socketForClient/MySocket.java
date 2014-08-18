package com.br.dong.socketForClient;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;
import java.util.Iterator;
/**
 * ʵ����Runnable��run���� ������Thread���ʼ�����á�
 * ��ʹ���˶��߳�������socket�����ӣ�Ϊ����ͻ��˷���
 * MySocket������Socket ���ͻ��������˵��׽�������
 * */
public class MySocket implements Runnable, IMsg {
	private Socket socket; // �׽������ñ���
	private BufferedReader reader; // �׽���������
	private DataOutputStream writer; // �׽��������
	private String name;
	private String id;
	/**
	 * ������˶�һ�����Ķ�ȡ��д�룬Ӧ����close���������رգ������ͷ���ռ�õ�ϵͳ��Դ
	 * ������ر��ļ������һ���ֽڿ�����Զ���ᱻ���͡�Ҳ������flush�����ֶ�ˢ�����
	 * */

	public MySocket() {

	}
	public void run() {
		try {
			String msg;
			while ((msg = reader.readLine()) != null)// ����յ��ͻ��˷���������
			{ // ��ͻ��˷�����Ϣ
				String[] arr = msg.split(C.INTERVAL);
				if (arr.length == 4) {
					setName(arr[1]);
					setID(arr[2] + C.INTERVAL + arr[3]);
					System.out.println("ע��ͻ���:" + arr[2] + C.INTERVAL + arr[3]);

					sendUpdateListMsg(getName() + "����,��"
							+ C.socketManager.size() + "�����ߡ�");
				} else if (arr.length == 3) {
					sendSingleMsg(arr[2], arr[0] + C.INTERVAL + arr[1]);
					System.out.println("�ͻ�����Ϣ:" + name + "-" + arr[0] + " "
							+ C.dateFormat.format(new Date()) + ":" + arr[2]);
				} else {
					sendBroadcastMsg(msg);
					System.out.println("�ͻ�����Ϣ:" + name + "-������ "
							+ C.dateFormat.format(new Date()) + ":" + msg);
				}
			}
		} catch (Exception e) {
			System.out.println("err");
		} finally {
			try {
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
				sendUpdateListMsg(name + "����,��" + C.socketManager.size()
						+ "�����ߡ�");
				// broadcastMsg(name + "����,��" + sManager.size() + "�����ߡ�",
				// "ϵͳ��Ϣ");

				System.out.println("�ͻ����뿪:" + this.getName());// ����Ļ��������Ϣ
				System.out.println("��ǰ�ͻ��˵�������:" + C.socketManager.size());
			} catch (Exception e) {
			}
		}

	}
    //����xml��ʽ��string����
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
					s.sendMsg(makeMsg(C.BROADCAST_MSG, msg, name, "������"));
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
		System.err.println("����û�д���:" + name + "-" + id + ":" + msg);
	}

	public void sendSystemMsg(String msg) {
		Iterator<IMsg> iter = (Iterator<IMsg>) C.socketManager.listIterator();
		IMsg s;
		while (iter.hasNext()) {
			s = iter.next();
			try {
				s.sendMsg(makeMsg(C.SYSTEM_MSG, msg, "ϵͳ��Ϣ", "������"));
			} catch (Exception e) {
				System.err.println(s.getName() + "-------SYSTEM_MSG");
			}
		}
	}

	public void sendUpdateListMsg(String msg) {
		String $msg = addTag(msg, "msg");
		Iterator<IMsg> iter = (Iterator<IMsg>) C.socketManager.listIterator();
		IMsg s;
		$msg += addTag("������" + C.INTERVAL + "������", "user");
		while (iter.hasNext()) {
			s = iter.next();
			$msg += addTag(s.getID(), "user");
		}
		iter = (Iterator<IMsg>) C.socketManager.listIterator();
		while (iter.hasNext()) {
			s = iter.next();
			try {
				s.sendMsg(makeMsg(C.UPDATELIST_MSG, $msg, "ϵͳ��Ϣ", "������"));
			} catch (Exception e) {
				System.err.println(s.getName() + "-------UPDATELIST_MSG");
			}
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

	public Socket getSocket() {
		return socket;
	}
    //���õ�ǰsocket������
	public void setSocket(Socket value) {
		socket = value;
		try {
			//���������
			reader = new BufferedReader(new InputStreamReader(this.socket
					.getInputStream(), "UTF-8"));
			// ��ȡ�׽��ֵ������
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
