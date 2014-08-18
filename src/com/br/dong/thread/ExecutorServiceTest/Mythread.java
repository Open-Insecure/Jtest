package com.br.dong.thread.ExecutorServiceTest;
/** 
 * @author  hexd
 * 创建时间：2014-7-22 上午11:09:12 
 * 类说明 
 */
public class Mythread extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "正在执行。。。");
    }
}
