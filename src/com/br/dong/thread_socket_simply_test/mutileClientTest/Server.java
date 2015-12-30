package com.br.dong.thread_socket_simply_test.mutileClientTest;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-08
 * Time: 9:11
 */
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends ServerSocket {
    private static final int SERVER_PORT =2015;
    private Map<String,Socket> socketMap=new HashMap<String, Socket>();
    public Server()throws IOException {
        super(SERVER_PORT);
        try {
            while (true) {
                Socket socket = accept();
                System.out.println(socket.getInetAddress().getHostName()+"---"+socket.getInetAddress().getHostAddress()+"----"+socket.getInetAddress().getCanonicalHostName());;
                new CreateServerThread(socket);//当有请求时，启一个线程处理
            }
        }catch (IOException e) {
        }finally {
//            close();
        }
    }

    //线程类
    class CreateServerThread extends Thread {
        private Socket client;
        private BufferedReader bufferedReader;
        private PrintWriter printWriter;


        public CreateServerThread(Socket s)throws IOException {
            client = s;
            bufferedReader =new BufferedReader(new InputStreamReader(client.getInputStream()));
            printWriter =new PrintWriter(client.getOutputStream(),true);
            System.out.println("线程 (" + getName() +")创建");
            start();
        }

        public void run() {
            try {
                String line = bufferedReader.readLine();
                System.out.println("客户端发来的数据："+line);
                printWriter.println("线程" + getName() +"执行完毕");
//                printWriter.close();
//                bufferedReader.close();
//                client.close();
            }catch (IOException e) {
            }
        }
    }

    public static void main(String[] args)throws IOException {
        new Server();
    }
}
