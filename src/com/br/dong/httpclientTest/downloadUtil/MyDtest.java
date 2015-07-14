package com.br.dong.httpclientTest.downloadUtil;

import java.io.File;
import java.util.Calendar;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-07-11
 * Time: 13:53
 */
public class MyDtest {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        final DownloadThread downloadThread=new DownloadThread("test","http://vip.youb77.com:81/media/you22/flv/9970.flv", new File("E:\\video\\9970.flv"),"http","youb77.com","http://vip.youb77.com:81");
        //下载线程监听器 构造方法
        final DownloadThreadListener threadListener = new DownloadThreadListener() {

            public void afterPerDown(DownloadThreadEvent event) {
                //线程同步更改此下载监听器监听事件下载的文件大小
                synchronized (this) {
                    System.out.println("下载进度:"+downloadThread.getDownloadProgress()+"%");
                }
            }

            /*
             * 当该监听到线程下载完成后
             */
            public void downCompleted(DownloadThreadEvent event) {
                System.out.println("已经下载:"+event.getCount());
            }
        };
        downloadThread.addDownloadListener(threadListener);
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> result = executor.submit(downloadThread);
        System.out.println("result:"+result.get());
        executor.shutdown();
    }
}
