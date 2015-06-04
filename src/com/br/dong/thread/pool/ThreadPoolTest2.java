package com.br.dong.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-8-21
 * Time: 下午1:44
 * To change this template use File | Settings | File Templates
 *
 * 注意thread与runable执行的顺序不一样
 */
public class ThreadPoolTest2 {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        for(int i = 1; i < 5; i++) {
            final int taskID = i;
            try {
                threadPool.execute(new MyThread("name"+i,i));
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        System.out.println("下载完成");
        threadPool.shutdown();// 任务执行完毕，关闭线程池
    }
}

class MyThread extends Thread{

    MyThread(String name, int id) {
        super(name);
        this.id = id;
    }

    int id;

    @Override
    public void run(){
        System.out.println("线程名字:"+getName()+"执行任务:"+id);
    }
}
