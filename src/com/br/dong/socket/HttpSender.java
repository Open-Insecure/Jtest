package com.br.dong.socket;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class HttpSender {



		public static void main(String[] args) {
			try {
				Socket socket = new Socket("www.baidu.com", 80);
				OutputStream out = socket.getOutputStream();
				InputStream in = socket.getInputStream();
				StringBuffer sb = new StringBuffer();
				sb.append("GET /index.html HTTP/1.1\r\n");
//				sb.append("Host: www.baidu.com\r\n");
				sb.append("Connection: close\r\n");
//				sb.append("User-agent: Mozilla/5.0\r\n");
//				sb.append("Accept-language: zh-cn\r\n");
				sb.append("\r\n");
				
				out.write(sb.toString().getBytes());
				out.flush();
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String tmp = "";
				System.out.println("response:\r\n");
				while ((tmp = reader.readLine()) != null) {
					System.out.println(tmp);
				}
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


}

