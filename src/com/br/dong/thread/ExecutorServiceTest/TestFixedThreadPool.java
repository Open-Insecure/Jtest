package com.br.dong.thread.ExecutorServiceTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 
 * @author  hexd
 * 创建时间：2014-7-22 上午11:09:36 
 * 类说明 
 */
public class TestFixedThreadPool {
    public static void main(String[] args) {
        // 创建一个可重用固定线程数的线程池 线程池有10个线程
       // ExecutorService pool = Executors.newFixedThreadPool(10);
        ExecutorService pool = Executors.newCachedThreadPool();
        // 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
        Thread t1 = new Mythread();
        Thread t2 = new Mythread();
        Thread t3 = new Mythread();
        Thread t4 = new Mythread();
        Thread t5 = new Mythread();
        Thread t6 = new Mythread();
        Thread t7 = new Mythread();
        Thread t8 = new Mythread();
        Thread t9 = new Mythread();
        Thread t10 = new Mythread();
        Thread t11 = new Mythread();
        // 将线程放入池中进行执行
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);
        pool.execute(t6);
        pool.execute(t7);
        pool.execute(t8);
        pool.execute(t9);
        // 关闭线程池
        pool.shutdown();
    }
}
