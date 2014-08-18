package com.br.dong.socket.socket2;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Client3 {
	public void connServer() {
		Socket tSocket= null;
		OutputStream os=null;
		InputStream is=null;
		try {
			tSocket= new Socket("localhost", 8888);// ������������˵�����
			tSocket.setSoTimeout(1000);
			os= tSocket.getOutputStream();
			os.write("���ǿͻ�2--hello world".getBytes()); //���������������
			tSocket.shutdownOutput();
//			ByteArrayOutputStream byteout= new ByteArrayOutputStream();
			is= tSocket.getInputStream();
			byte[] buf= new byte[1024];
			int len= is.read(buf);
			String ret="";//����������
			if(len>0){
//				byteout.write(buf, 0, len);
				ret= new String(buf);
			}
			System.out.println("------����������22------"+ret.trim());
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
		new Client3().connServer();
	}
}
