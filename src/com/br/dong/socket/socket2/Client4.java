package com.br.dong.socket.socket2;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
public class Client4 {
	public void connServer() {
		Socket tSocket= null;
		OutputStream os=null;
		InputStream is=null;
		try {
			tSocket= new Socket("localhost", 8888);// ������������˵�����
			tSocket.setSoTimeout(1000);
			os= tSocket.getOutputStream();
			os.write("���ǿͻ�3--hello world".getBytes()); //���������������
			tSocket.shutdownOutput();
//			ByteArrayOutputStream byteout= new ByteArrayOutputStream();
			is= tSocket.getInputStream();
			String ret="";//����������
			int count = 0;
			while (count == 0) {
				count = is.available();
			}
			byte[] b = new byte[count];
			//is.read(b);
			
			int readCount = 0; // �Ѿ��ɹ���ȡ���ֽڵĸ���
			while (readCount < count) {
				readCount += is.read(b, readCount, count - readCount);
				ret = new String(b);
			}
			System.out.println("------����������33------"+ret+"----");
			tSocket.shutdownInput();
		} catch (SocketTimeoutException e) {
			System.out.println("���ӷ�������ʱ������1����û�з���");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			System.err.println("�Ҳ�����ָ����������" + e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}finally{
			try {
			    os.close();
				is.close();
				tSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Client4().connServer();
	}

}
