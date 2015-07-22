package com.br.dong.queue;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-07-21
 * Time: 19:39
 *
 * ArrayList 采用的是数组形式来保存对象的，这种方式将对象放在连续的位置中，所以最大的缺点就是插入删除时非常麻烦
 * LinkedList 采用的将对象存放在独立的空间中，而且在每个空间中还保存下一个链接的索引  但是缺点就是查找非常麻烦 要丛第一个索引开始
 * Hashtable和HashMap类有三个重要的不同之处。第一个不同主要是历史原因。Hashtable是基于陈旧的Dictionary类的，HashMap是Java 1.2引进的Map接口的一个实现
 * 也许最重要的不同是Hashtable的方法是同步的，而HashMap的方法不是。
 * 第三点不同是，只有HashMap可以让你将空值作为一个表的条目的key或value。HashMap中只有一条记录可以是一个空的key，
 * 但任意数量的条目可以是空的value。这就是说，如果在表中没有发现搜索键，或者如果发现了搜索键，但它是一个空的值，那么get()将返回null。
 * 如果有必要，用containKey()方法来区别这两种情况
 * 当需要同步时，用Hashtable，反之用HashMap。但是，因为在需要时，HashMap可以被同步，HashMap的功能比Hashtable的功能更多，而且它不是基于一个陈旧的类的，
 * 所以有人认为，在各种情况下，HashMap都优先于Hashtable。
 */
public class Queuetest {
    /**
     * @param args
     */

    private static Thread thread;
    private static LinkedList<Runnable> list = new LinkedList<Runnable>();

    static int test = 0;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        final long time = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            tastEvent(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    System.out
                            .println("第"
                                    + (++test)
                                    + ("个任务  耗时:" + (System
                                    .currentTimeMillis() - time)));
                }

            });
        }
    }

    /**
     * 线程
     * @param r
     */
    public static void tastEvent(Runnable r) {
        //用来给对象和方法或者代码块加锁，当它锁定一个方法或者一个代码块的时候，同一时刻最多只有一个线程执行这段代码。当两个并发线程访问同一个对象object中的这个加锁同步代码块时，一个时间内只能有一个线程得到执行。另一个线程必须等待当前线程执行完这个代码块以后才能执行该代码块。
        synchronized (list) {//list加锁
            list.add(r);//添加runnable
        }
        //
        if (thread == null) {
            thread = new Thread(run);//线程启动
            thread.start();
        }


    }

    static Runnable run = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (list) {
                while (!list.isEmpty()) {
                    // new Thread(list.poll()).start();
                    list.poll().run();//检索返回list的第一个头元素 是一个Runnable开始运行run
                }
                thread = null;
            }
        }
    };
}
