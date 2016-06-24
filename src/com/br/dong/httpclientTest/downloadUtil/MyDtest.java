package com.br.dong.httpclientTest.downloadUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-07-11
 * Time: 13:53
 */
public class MyDtest {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        start();
//        System.out.println(parseFileType("http://vip.youb77.com:81/media/you22/flv/9970.flv"));
    }
    public static void start() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);//创建固定大小的线程池
        //下载线程监听器 构造方法
        final DownloadThreadListener threadListener = new DownloadThreadListener() {
            public void afterPerDown(DownloadThreadEvent event) {
                //线程同步更改此下载监听器监听事件下载的文件大小
                synchronized (this) {
//                     System.out.println(((DownloadThread)event.getSource()).getTname()+"下载进度:"+event.getCount()+"%");
                }
            }
            /*
             * 当该监听到线程下载完成后
             */
            public void downCompleted(DownloadThreadEvent event) {
//                System.out.println(((DownloadThread)event.getSource()).getTname()+"已经下载:"+event.getCount());
            }
        };
        //模拟32个线程
        for(int i=0;i<5;i++){
            DownloadThread downloadThread=new DownloadThread("test"+i,"http://168.235.76.56/13140_img.jpg", new File("E:\\video\\test_img"+i+".jpg"),"http","youb77.com","http://vip.youb77.com:81");
            downloadThread.addDownloadListener(threadListener);
            executor.execute(downloadThread );
//            Future<String> result = executor.submit(downloadThread);
            int threadCount = ((ThreadPoolExecutor)executor).getActiveCount();//获得线程池中运行的线程数
            System.out.println("线程池中线程数:"+threadCount);
        }
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);//用于等待子线程结束，再继续执行下面的代码。
        System.out.println("执行完毕");
    }

    /**
     * 解析出网络资源中的文件名字
     * @return
     */
    public static  String parseFileType(String url){
        int index=url.lastIndexOf("/");
        return url.substring(index+1,url.length());
    }
}
