package com.br.dong.thread;

/**
 * Created with IntelliJ IDEA.
 * User: rdpc0848
 * Date: 13-11-1
 * Time:  
 * To change this template use File | Settings | File Templates.
 * 在java中要想实现多线程，有两种手段，一种是继续Thread类，另外一种是实现Runable接口。
 */
public class ThreadTest1 {

    public static void main(String [] args){
        int i=0;
        while(i<10){
            i++;
            //使用实现runable接口的方法
            Runnable r= new MyRunable(i);
            new Thread(r).start();
            // Thread t=new Thread(r);
            //t.start();

        }

    }
}
