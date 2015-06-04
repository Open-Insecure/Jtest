package com.br.dong.httpclientTest._1024gc_2015_04_10;

import com.br.dong.file.FileOperate;
import com.br.dong.utils.DateUtil;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-04-10
 * Time: 13:32
 * 采集
 * 可以打包发布的
 * http://cb.1024gc.info/bbs/ 1024核工厂的资源
 * 区域分为合集区域与普通区域，合集区域一个帖子包含了很多的单独的内容 需要细分
 * 针对linux上网盘的采集程序
 * linux的文件路径比如：/home/1
 */
public class GcForLinux {
//    public static String savePath="E:/uploads/1/";//采集的根目录 for local
//    public static String gc_logs="E:/uploads/gc_logs/";//采集日志记录for local
    public static String savePath="/home/1/";//采集的根目录 for linux
    public static String gc_logs="/mylogs/gc_logs/";//采集日志记录for linux
//    public static String todaySavePath=savePath+ DateUtil.getCurrentDay()+"/";//当天运行采集的子目录
    public static String logPath=gc_logs+"log.txt";//采集日志记录

    //线程池
    private static ExecutorService threadPool= Executors.newFixedThreadPool(20);
    //由于合集里一个帖子内容太多,所以合集单独采集
    private static String collectsUrl="http://cb.1024gc.info/bbs/forum-3-,heji" ; //合集
    private static  String [] targetUrls={
            "http://cb.1024gc.info/bbs/forum-5-,yazhouwuma",  // 亚洲无码
            "http://cb.1024gc.info/bbs/forum-7-,oumeiwuma",  //欧美无码
            "http://cb.1024gc.info/bbs/forum-22-,ribenyouma", //日本骑兵
            "http://cb.1024gc.info/bbs/forum-18-,sanjizipai"  //三级写真
    };
    private static String html=".html";

    /**
     * 当所有子线程结束的时候自动退出程序
     */
    public static void destoryAtFinsh(){
        threadPool.shutdown();
        while (true) {
            if (threadPool.isTerminated()) {
                break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        System.out.println("collect ending..");
        System.exit(0);
    }

    public static void main(String[] args) {
//        FileOperate.newFolderMuti(savePath); //创建根目录存盘路径
//        FileOperate.createFile(logPath);//创建日志文件
        //通过命令运行给arg赋值选择不同的启动方法进行采集
        try {
            String parm=args[0];
            int page=Integer.parseInt(args[1]);
            if(parm.equals("0")){
               otherStart(page);  //其他采集
            }else{
               collectsStart(page); //合集采集
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("param one: 0 collect other scope 1 collect heji scope");
            System.out.println("param two: page number");
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            destoryAtFinsh();
        }
    }

    /**
     * 启动其他采集
     */
    public static void otherStart(int page) throws KeyManagementException, NoSuchAlgorithmException {

        for(int i=0;i<targetUrls.length;i++){
            String []temp=targetUrls[i].split(",");
//            FileOperate.newFolderMuti(savePath + temp[1] + "/"); //创建对应文件夹
            //按页数采集
            for(int j=1;j<=page;j++){
                System.out.println("collecting :"+temp[1]+"page:"+j+"");
                threadPool.execute(new GcDownloadTaskForLinux(temp[1],temp[0]+j+html) );//创建采集线程
            }
        }
    }

    /**
     * 启动合集采集
     */
    public static void collectsStart(int page) throws KeyManagementException, NoSuchAlgorithmException {
        String []temp=collectsUrl.split(",");
//        FileOperate.newFolderMuti(savePath + temp[1] + "/"); //创建对应文件夹
        for(int j=1;j<=page;j++){
//            System.out.println("collecting:"+temp[1]+"page:"+j+"");
            threadPool.execute(new GcDownloadTaskForLinux(temp[1],temp[0]+j+html) );
        }

    }


}
