package com.br.dong.thread.threads_control;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-07-15
 * Time: 14:12
 * 系统在运行时，有时候我们要控制它同时允许多少个线程运行，如果太多可能引起内存溢出之类的异常，所以在线程比较多的情况下我们可以控制它的最大线程数，
 * 这样系统可以在一种比较稳定的状态下运行。下面是一个简单实现，可以少加修改用在系统中去。
 */
public class CreateThread {
    public static void main(String[] args) throws InterruptedException {
        //比如我们现要创建30个子线程，但允许最大线程数为10
        for (int i = 0; i < 30; i++) {
            // 获取锁资源
            synchronized (SubThread.class) {
                int tCount = SubThread.getThreadCounts();

                // 如果入库线程达到了最大允许的线程数
                while (tCount >= 10) {
                    System.out.println("系统当前线程数为：" + tCount
                            + "，已达到最大线程数 10，请等待其他线程执行完毕并释放系统资源");
                    // 释放锁，等待“线程数”资源，等待其他入库线程执行完毕
                    SubThread.class.wait();

                    /*
                     * 带条件的wait()（即放在条件语句执行的wait()方法），一定要放在while
                     * 条件里执行，因为被唤醒之后该线程有可能不会马上执行，只是把当前线程从等
                     * 待池中放入对象锁池队列中，直到获取到锁后才开始运行。所以在同步块里wait方
                     * 法前后的代码块不是原子性的，会分开执行，但sleep()不同，睡时不会释放锁。
                     * 所以等到执行时，可能条件已被其他的线程给改变了（像这里的最大线程数），此
                     * 时就需要再此判断该条件，否则条件可能不成立了
                     */
                    tCount = SubThread.getThreadCounts();
                }
                // 重新启动一个子线程
                Thread td = new SubThread();
                td.start();
                System.out.println("已创建新的子线程: " + td.getName());
            }
        }

    }
}
