package com.br.dong.thread;

/**
 * Created with IntelliJ IDEA.
 * User: rdpc0848
 * Date: 13-11-1
 * Time: 
 * To change this template use File | Settings | File Templates.
 */
public class MyRunable implements Runnable {
   private int count;

    public MyRunable(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        System.out.println("线程"+count);
    }
    
}
