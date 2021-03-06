package com.br.dong.http_server.test1;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-07-23
 * Time: 12:38
 * 模拟浏览器的行为， 向服务器发送get/post请求，然后打印出服务器返回的消息。这样就可以查看当一个请求到来之后， 服务器到底都给浏览器发送了哪些消息。
 */
public class MyHttpClient {
    public static void main(String[] args) throws Exception{
        InetAddress inet = InetAddress.getByName("www.baidu.com");
        System.out.println(inet.getHostAddress());
        Socket socket = new Socket(inet.getHostAddress(),80);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        PrintWriter writer = new PrintWriter(out);
        writer.println("GET /home.html HTTP/1.1");//home.html是关于百度的页面
        writer.println("Accept: image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint, */*");
        writer.println("Accept-Language: en-us,zh-cn;q=0.5");
        writer.println("Accept-Encoding: gzip, deflate");
        writer.println("Host: www.baidu.com");
        writer.println("User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
        writer.println("Connection: Keep-Alive");
        writer.println();
        writer.flush();
        String line = reader.readLine();
        while(line!=null){
            System.out.println(line);
            line = reader.readLine();
        }
        reader.close();
        writer.close();
    }
}
