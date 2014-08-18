package com.br.dong.socket.socketForAs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import com.br.dong.socket.socketForAs.IMsg;
import com.br.dong.socketForClient.C;


public class ThreadServerSocket implements Runnable, IMsg {
	private Socket socket; // 套接字引用变量
	private BufferedReader reader; // 套接字输入流
	private DataOutputStream writer; // 套接字输出流
	private String name;
	//线程运行的程序
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			System.out.println("thread启动run");
			String msg;
			//当读取到输入流不为空
			while((msg=reader.readLine())!=null){
				System.out.println("读取客户端输入的"+msg);
				if(msg.equals("hehe")){
					System.out.println(msg+"登录！");
					sendMsg("../images/star.png");
				}else{
					sendMsg("输入的不是hehe，重新输入");
				}
			}
		}catch(Exception e){
			
		}finally{
			try{
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
				System.out.println("客户端离开:");// 向屏幕输出相关信息
				System.out.println("当前客户端的连接数:" + C.socketManager.size());
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	/**
	 * 设置socket,并将socket的输入流，输出流解析出来
	 * */
	public void setSocket(Socket s){
		socket=s;
		try {
			//获得输入流
			reader = new BufferedReader(new InputStreamReader(this.socket
					.getInputStream(), "UTF-8"));
			// 获取套接字的输出流
			writer = new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException i) {
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
}
