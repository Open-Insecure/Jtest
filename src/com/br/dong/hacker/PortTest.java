package com.br.dong.hacker;

import java.io.IOException;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-3
 * Time: 下午2:10
 * To change this template use File | Settings | File Templates.
 */
public class PortTest extends Thread{
    private int[] p;
    Socket ss = null;

    public PortTest(int[] p) {
        this.p = p;
    }

    public static void main(String[] args) {
//        for(int i=0;i<5000;i=i+100){
//            new PortTest(new int[]{
//                    i+1,i+100
//            }).start();
//        }
        try {
            Socket ss = new Socket("10.62.124.28",135);
            System.err.println("扫描到端口： " + 135);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }
    @Override
    public void run() {
        System.err.println("启动线程");
        for(int i=p[0]; i<p[1];i++){
            try {
//                System.out.println(i);
                ss = new Socket("127.0.0.1",i);
                System.err.println("扫描到端口： " + i);

            } catch (IOException e) {

            }
        }
    }
}
