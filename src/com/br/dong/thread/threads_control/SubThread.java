package com.br.dong.thread.threads_control;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015/7/15
 * Time: 14:18
 */
public class SubThread extends Thread{
    // 线程计数器
    static private int threadCounts;

    // 线程名称池
    static private String threadNames[];

    static {
        // 假设这里允许系统同时运行最大线程数为10个
        int maxThreadCounts = 10;
        threadNames = new String[maxThreadCounts];
        // 初始化线程名称池
        for (int i = 1; i <= maxThreadCounts; i++) {
            threadNames[i - 1] = "子线程_" + i;
        }
    }

    public SubThread() {
        // 临界资源锁定
        synchronized (SubThread.class) {
            // 线程总数加1
            threadCounts++;

            // 从线程名称池中取出一个未使用的线程名
            for (int i = 0; i < threadNames.length; i++) {
                if (threadNames[i] != null) {
                    String temp = threadNames[i];
                    // 名被占用后清空
                    threadNames[i] = null;
                    // 初始化线程名称
                    this.setName(temp);
                    break;
                }
            }
        }
    }

    public void run() {
        System.out.println("-->>" + this.getName() + "开始运行");
        try {
            //模拟程序耗时
            Thread.sleep(1000);
            // ...
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            synchronized (SubThread.class) {

                // 线程运行完毕后减1
                threadCounts--;

                // 释放线程名称
                String[] threadName = this.getName().split("_");

                // 线程名使用完后放入名称池
                threadNames[Integer.parseInt(threadName[1]) - 1] = this.getName();

                /*
                 * 通知其他被阻塞的线程，但如果其他线程要执行，则该同步块一定要运行结束（即直
                 * 到释放占的锁），其他线程才有机会执行，所以这里的只是唤醒在此对象监视器上等待
                 * 的所有线程，让他们从等待池中进入对象锁池队列中，而这些线程重新运行时它们一定
                 * 要先要得该锁后才可能执行，这里的notifyAll是不会释放锁的，试着把下面的睡眠语
                 * 句注释去掉，即使你已调用了notify方法，发现CreateThread中的同步块还是好
                 * 像一直处于对象等待状态，其实调用notify方法后，CreateThread线程进入了对象锁
                 * 池队列中了，只要它一获取到锁，CreateThread所在线程就会真真的被唤醒并运行。
                 */
                SubThread.class.notifyAll();
//              try {
//                  Thread.sleep(10000);
//              } catch (InterruptedException e) {
//                  e.printStackTrace();
//              }
                System.out.println("----" + this.getName() + " 所占用资源释放完毕，当前系统正在运行的子线程数："
                        + threadCounts);
            }
            System.out.println("<<--" + this.getName() + "运行结束");
        }
    }

    static public int getThreadCounts() {
        synchronized (SubThread.class) {
            return threadCounts;
        }
    }
}
