package com.br.dong.thread.newFixedThreadPool_test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015/7/15
 * Time: 14:32
 * 测试得到线程池中的当前活动线程
 */
public class TestPool {
    public static void main(String[] args) {

        ExecutorService exec = null;
        try{

            exec = Executors.newFixedThreadPool(10);

//想线程池中放入三个任务
            exec.execute(new Task());
            exec.execute(new Task());
            exec.execute(new Task());
//延迟一下,因为三个任务放入需要时间
            Thread.sleep(200);

//重点到了!!

//将exec转换为ThreadPoolExecutor,ThreadPoolExecutor有方法 getActiveCount()可以得到当前活动线程数
            int threadCount = ((ThreadPoolExecutor)exec).getActiveCount();
            System.out.println(threadCount);


            Thread.sleep(4000);


            threadCount = ((ThreadPoolExecutor)exec).getActiveCount();
            System.out.println(threadCount);

            exec.execute(new Task());
            exec.execute(new Task());

            Thread.sleep(200);

            threadCount = ((ThreadPoolExecutor)exec).getActiveCount();
            System.out.println(threadCount);



        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(exec!=null){
                exec.shutdown();
            }
        }

    }

}
class Task implements Runnable{
    @Override
    public void run() {
        try{
            Thread.sleep(3000);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
