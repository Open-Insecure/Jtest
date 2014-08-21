package com.br.dong.thread.pool;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-8-21
 * Time: 下午1:42
 * To change this template use File | Settings | File Templates.
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class ThreadPoolTest {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        for(int i = 1; i < 5; i++) {
            final int taskID = i;
            threadPool.execute(new Runnable() {
                public void run() {
                    for(int i = 1; i < 5; i++) {
                        try {
                            Thread.sleep(20);// 为了测试出效果，让每次任务执行都需要一定时间
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("第" + taskID + "次任务的第" + i + "次执行");
                    }
                }
            });
        }
        threadPool.shutdown();// 任务执行完毕，关闭线程池
    }
}
