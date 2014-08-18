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
		// ����һ��timerģ�ⷢ��ϵͳ��Ϣ
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				//�漴����ϵͳ��Ϣ
				if ((int) (Math.random() * 100) > 50
						&& C.socketManager.size() > 0) {
					C.socketManager.get(0).sendSystemMsg("��ӭ��λ���������ң�ף����ĵÿ��ġ�");
				}
			}
		}, 0, 60 * 1000);
		// socket����
		ServerSocket server = null;
		try {
			server = new ServerSocket(C.SERVER_PORT);
			System.out.println("�������׽����Ѵ����ɹ���");
			while (true) {
				//��server���յ���һ���ͻ��˵����Ӻ󣬼�����������֮���һ��socketʵ��
				Socket socket = server.accept();
				Class c = Class.forName(C.mySocketClass);
				//����Mysocket��ʵ��
				IMsg s = (IMsg) c.newInstance();
				//set socket
				s.setSocket(socket);
				//����һ���߳�������һ��socket����
				new Thread((Runnable) s).start();
				//����Mysocket��ʵ������list�С�
				//ע��ǵ�������s ֻ��IMsg���ʵ��������  ����s���൱��ͨ�����ò���������ʵ��
				C.socketManager.add(s);
				System.out.println("��ǰ�ͻ�����������" + C.socketManager.size());
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
