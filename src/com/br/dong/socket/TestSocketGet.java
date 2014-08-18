package com.br.dong.socket;

import java.io.BufferedReader; 
import java.io.BufferedWriter; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.io.OutputStreamWriter; 
import java.net.InetAddress; 
import java.net.Socket; 
import java.net.URLEncoder; 

public class TestSocketGet { 

    public static void main(String[] args) { 
        BufferedWriter httpGetWriter = null; 
        BufferedReader httpResponse = null; 
        try { 
            String hostname = "www.baidu.com";// 主机，可以是域名，也可以是ip地址 
            int port = 80;// 端口 
            InetAddress addr = InetAddress.getByName(hostname); 
            // 建立连接 
            Socket socket = new Socket(addr, port); 
            // 创建数据提交数据流 
            httpGetWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "GBK")); 
            // 相对主机的请求地址 
            StringBuffer httpSubmitPath = new StringBuffer("/icbcnet/testpostresult.jsp?"); 
            httpSubmitPath.append(URLEncoder.encode("name", "GBK")); 
            httpSubmitPath.append("="); 
            httpSubmitPath.append(URLEncoder.encode("fruitking", "GBK")); 
            httpSubmitPath.append("&"); 
            httpSubmitPath.append(URLEncoder.encode("company", "GBK")); 
            httpSubmitPath.append("="); 
            httpSubmitPath.append(URLEncoder.encode("pubone", "GBK")); 
            httpGetWriter.write("GET " + httpSubmitPath.toString() + " HTTP/1.1\r\n"); 
            httpGetWriter.write("Host: socket方式的get提交测试\r\n"); 
            httpGetWriter.write("\r\n"); 
            httpGetWriter.flush(); 
            // 创建web服务器响应的数据流 
            httpResponse = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK")); 
            // 读取每一行的数据.注意大部分端口操作都需要交互数据。 
            String lineStr = ""; 
            while ((lineStr = httpResponse.readLine()) != null) { 
                System.out.println(lineStr); 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally { 
            try { 
                if (httpGetWriter != null) { 
                    httpGetWriter.close(); 
                } 
                if (httpResponse != null) { 
                    httpResponse.close(); 
                } 
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
    } 
} 