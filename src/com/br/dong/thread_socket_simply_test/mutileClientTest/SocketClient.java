package com.br.dong.thread_socket_simply_test.mutileClientTest;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-08
 * Time: 9:10
 */
import java.io.*;
import java.net.Socket;

/***
 * 多客户端连接服务端测试
 * 设计思路：服务器端主程序监听某一个端口，客户端发起连接请求，服务器端主程序接收请求，同时构造一个线程类，用于接管会话。
 * 当一个Socket会话产生后，这个会话就会交给线程进行处理，主程序继续进行监听。
 ：客户端和服务器建立连接，客户端发送消息，服务端根据消息进行处理并返回消息，若客户端申请关闭，则服务器关闭此连接，双方通讯结束。
 */
public class SocketClient {


    /***
     * socket链接并发送数据
     * @param message 发送给server端的数据
     * @return server端接收后返回给client的数据
     * @throws IOException
     */
    public static String socketConnect(Socket socket ,String message) throws IOException {
       ;//初始化socket链接数据
        OutputStream os=socket.getOutputStream();
        PrintWriter pw=new PrintWriter(os);
        InputStream   is=socket.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        //3.利用流按照一定的操作，对socket进行读写操作
        pw.write(message);
        pw.flush();
        socket.shutdownOutput();
        //接收服务器的相应
        String reply=null;
        while(!((reply=br.readLine())==null)){
            System.out.println("receive from server:--"+reply+"---");
        }
        //4.关闭资源
//        br.close();
//        is.close();
//        pw.close();
//        os.close();
//        socket.close();
        return reply;
    }

}
