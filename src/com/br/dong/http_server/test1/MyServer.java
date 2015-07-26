package com.br.dong.http_server.test1;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-07-23
 * Time: 12:39
 * 模拟server端接收浏览器的请求，然后把整个请求的报文打印出来。程序运行之后直接用浏览器测试。
 */
public class MyServer {
    public static void main(String[] args) throws IOException {
        ServerSocket svrSocket = new ServerSocket(8080);
        while(true){
            Socket socket = svrSocket.accept();
            //足够大的一个缓冲区
            byte[] buf = new byte[1024*1024];
            InputStream in = socket.getInputStream();
            int byteRead = in.read(buf, 0, 1024*1024);
            String dataString = new String(buf, 0, byteRead);
            System.out.println(dataString);
            in.close();
            socket.close();
        }
    }
}
