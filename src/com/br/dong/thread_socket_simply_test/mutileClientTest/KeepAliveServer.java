package com.br.dong.thread_socket_simply_test.mutileClientTest;

import com.br.dong.thread_socket_simply_test.mutileClientTest.bean.IMessageObject;
import com.br.dong.thread_socket_simply_test.mutileClientTest.bean.KeepAliveMessageObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-11
 * Time: 10:49
 */
public class KeepAliveServer {
    private int port;//端口号
    private volatile boolean running=false;//服务端运行标志
    private long receiveTimeDelay=3000;
    private ConcurrentHashMap<Class, IMessageObject> actionMapping = new ConcurrentHashMap<Class,IMessageObject>();
    private Thread connWatchDog;

    public KeepAliveServer(int port) {
        this.port = port;
    }

    public void start(){
        if(running)return;
        running=true;
        connWatchDog = new Thread(new ConnWatchDog());
        connWatchDog.start();
    }

    public void stop(){
        if(running)running=false;
        if(connWatchDog!=null)connWatchDog.stop();
    }

    public void addActionMap(Class<Object> cls,IMessageObject action){
        actionMapping.put(cls, action);
    }

    /***
     * 保持连接
     */
    class ConnWatchDog implements Runnable{
        public void run(){
            try {
                ServerSocket ss = new ServerSocket(port,5);
                while(running){
                    Socket s = ss.accept();
                    new Thread(new SocketAction(s)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
                KeepAliveServer.this.stop();
            }

        }
    }
    class CpuAction implements Runnable{

        @Override
        public void run() {

        }
    }

    class SocketAction implements Runnable{
        Socket s;
        boolean run=true;
        long lastReceiveTime = System.currentTimeMillis();
        public SocketAction(Socket s) {
            this.s = s;
        }
        public void run() {
            while(running && run){
                if(System.currentTimeMillis()-lastReceiveTime>receiveTimeDelay){//接收超时
                    overThis();
                }else{
                    try {
                        InputStream in = s.getInputStream();
                        if(in.available()>0){
                            ObjectInputStream ois = new ObjectInputStream(in);
                            Object obj = ois.readObject();
                            lastReceiveTime = System.currentTimeMillis();
                            System.out.println("接收：\t"+obj);
                            IMessageObject oa = actionMapping.get(obj.getClass());
                            oa = oa==null?new KeepAliveMessageObject():oa;
                            Object out = oa.doAction(obj);
                            if(out!=null){
                                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                                oos.writeObject(out);
                                oos.flush();
                            }
                        }else{
                            Thread.sleep(10);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        overThis();
                    }
                }
            }
        }

        /***
         * 断开此连接
         */
        private void overThis() {
            if(run)run=false;
            if(s!=null){
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("关闭："+s.getRemoteSocketAddress());
        }

    }
    public static void main(String[] args) {
        int port = 65432;
        KeepAliveServer server = new KeepAliveServer(port);
        server.start();
    }
}
