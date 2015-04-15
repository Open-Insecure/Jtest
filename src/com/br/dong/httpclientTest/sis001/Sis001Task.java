package com.br.dong.httpclientTest.sis001;

import com.br.dong.file.FileOperate;
import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.JdbcUtil;
import com.br.dong.utils.DateUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-10-9
 * Time: 下午4:20
 * To change this template use File | Settings | File Templates.
 * http://38.103.161.153/forum/forum-426-1.html   新地址 2015-03-16
 */
public class Sis001Task {
    //线程池
    private static ExecutorService threadPool= Executors.newCachedThreadPool();
    //本地硬盘
//    private static String floderpath="F:\\vedios\\sis\\"+DateUtil.getCurrentDay()+"\\";
    private  static String floderpath="C:\\sis\\download\\"+DateUtil.getCurrentDay()+"\\";
    public static CrawlerUtil client=new CrawlerUtil();
    //登录url
    private static String loginPostUrl="http://38.103.161.153/forum/logging.php?action=login&loginsubmit=true";
    //用户名
    private static String username="ckwison";
    //密码
    private static String password="1234qwer!@#$";
    public static void main(String[] args) {

        //登录
        Boolean loginflag=false;
        loginflag= login(username,password);
        if(!loginflag){
            System.out.println(username+"login error,try login again！");
             return ;
        }
        System.out.println(username+"login success!");
          //测试图片解析存数据库方法
//          Sis001DownLoadTask test=new Sis001DownLoadTask("pic_no_download","http://38.103.161.153/forum/forum-249-");
        //测试小说
//          Sis001DownLoadTask test=new Sis001DownLoadTask("txt_download",floderpath,"http://38.103.161.153/forum/forum-83-");
//          test.start();
//         start();      //开始



    }

    /**
     * 开始种子线程
     * 种子类的线程 Sis001DownLoadTask name以 bt_ 开头 对应数据库urls表中的floderName字段
     * url类的线程 Sis001DownLoadTask name以 url_ 开头 对应数据库urls表中的floderName字段
     */
    public static void start(){
        System.out.println("start main thread..");
        String [] sits={
                "bt,http://38.103.161.153/forum/forum-25-,bt亚洲无码转帖",
                "bt,http://38.103.161.153/forum/forum-58-,bt亚洲有码转帖",
                "bt,http://38.103.161.153/forum/forum-77-,bt欧美无码",
                "bt,http://38.103.161.153/forum/forum-27-,bt成人游戏卡通漫画转区",
                "bt,http://38.103.161.153/forum/forum-143-,bt亚洲无码原创区",
                "bt,http://38.103.161.153/forum/forum-426-,bt情色三级",
                "url,http://38.103.161.153/forum/forum-187-,url外链成人网盘",
                "url,http://38.103.161.153/forum/forum-270-,url外链电驴",
                "url,http://38.103.161.153/forum/forum-212-,url外链迅雷",
                "pic_no_download,http://38.103.161.153/forum/forum-242-,pic熟女乱伦图片分享区",
                "pic_no_download,http://38.103.161.153/forum/forum-68-,pic西洋靓女骚妹",
                "pic_no_download,http://38.103.161.153/forum/forum-60-,pic动漫卡通游戏贴图区",
                "pic_no_download,http://38.103.161.153/forum/forum-64-,pic东方靓女集中营",
                "pic_no_download,http://38.103.161.153/forum/forum-184-,pic精品套图鉴赏区",
                "pic_no_download,http://38.103.161.153/forum/forum-219-,pic高跟美足丝袜区",
                "pic_no_download,http://38.103.161.153/forum/forum-62-,pic网友自拍贴图分享区",
                "pic_no_download,http://38.103.161.153/forum/forum-61-,pic星梦奇缘合成天堂",//图片在附件
                "txt_download,http://38.103.161.153/forum/forum-383-,txt原创人生区",
                "txt_download,http://38.103.161.153/forum/forum-279-,txt人妻意淫区",
                "txt_download,http://38.103.161.153/forum/forum-83-,txt乱伦迷情区",
                "txt_download,http://38.103.161.153/forum/forum-96-,txt武侠玄幻区",
                "txt_download,http://38.103.161.153/forum/forum-31-,txt另类其它区",
                "txt_download,http://38.103.161.153/forum/forum-385-,txt电子书下载",
                "txt_download,http://38.103.161.153/forum/forum-368-,txt杂志下载"


        };
        //读取文本的urls配置文件
//        readTxtInfos("urls.txt");
        readUrls(sits);
    }

    /**
     * 读取要采集的text文档，创建子线程
     * @param textName
     * @return
     */
    public static void readTxtInfos(String textName){
        String textPath= Sis001Task.class.getResource(textName).toString().substring(6); //获得当前txt的路径
        //读取txt
        readLine(textPath);
    }
   public static void readUrls(String [] sits){
       System.out.println("sits length:"+sits.length);
       for(int i=0;i<sits.length;i++){
           try {
                   String line=sits[i];
                   System.out.println(""+line);
                   String []temp=line.split(",");//每一行进行分割出对应的需要的
                   String name=temp[0];//在线程类中判断此线程要使用哪种采集方法
                   String finalUrl=temp[1];   //当前版块的对应的url
                   String folderName=temp[2];
                   String finalFloderPath=floderpath+folderName+"\\";//文件夹路径
                   System.out.println(temp.length+finalFloderPath+finalUrl);
                   newFolderMuti(finalFloderPath); //创建对应文件夹
                   threadPool.execute(new Sis001DownLoadTask(name, finalFloderPath, finalUrl,folderName));//线程池启动线程
               //判断线程池里的线程是否全部执行完

           } catch (ArrayIndexOutOfBoundsException e){
           }
       }
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
       System.out.println("采集结束");
       System.exit(0);
   }

    /**
     * 依次读取每一行，创建线程
     * @param filePath
     */
    public static void readLine(String filePath){
        BufferedReader in=null;
        try{
            in=new BufferedReader(new FileReader(filePath));
            String line;
            try {
                while((line=in.readLine())!=null){
                    System.out.println(""+line);
                    String []urls=line.split(",");//每一行进行分割出对应的需要的
                    String name=urls[0];//在线程类中判断此线程要使用哪种采集方法
                    String finalUrl=urls[1];   //当前版块的对应的url
                    String folderName=urls[2];
                    String finalFloderPath=floderpath+folderName+"\\";//文件夹路径
                    System.out.println(urls.length+finalFloderPath+finalUrl);
                    newFolderMuti(finalFloderPath); //创建对应文件夹
                    threadPool.execute(new Sis001DownLoadTask(name, finalFloderPath, finalUrl,folderName));//线程池启动线程
                }
                //判断线程池里的线程是否全部执行完
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
                System.out.println("采集结束");
                System.exit(0);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (ArrayIndexOutOfBoundsException e){
        }
    }

    /**
     * 创建存盘路径
     * @param folderpath
     */
    public static void newFolderMuti(String folderpath){
        System.out.println("creat floder path:"+folderpath);
        FileOperate.newFolderMuti(folderpath);
    }

    /**
     * 登录sis001
     */
    public  static Boolean login(String username,String password) {
        try {
            client.clientCreatNoUrl("http");
            //先get执行一下
            HttpResponse response=client.noProxyGetUrl("http://38.103.161.153/forum/index.php");
            Document doc=client.getDocGBK(response);
            System.out.println(doc.toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClientProtocolException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //填充登录参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("formhash", "c27b368e"));
        list.add(new BasicNameValuePair("cookietime", "2592000"));
        list.add(new BasicNameValuePair("loginfield", "username"));
        list.add(new BasicNameValuePair("cookietime", "2592000"));
        list.add(new BasicNameValuePair("62838ebfea47071969cead9d87a2f1f7", username));
        list.add(new BasicNameValuePair("c95b1308bda0a3589f68f75d23b15938", password));
        HttpResponse responsepost= null;
        try {
            //发送登录请求
            responsepost = client.post(loginPostUrl, client.produceEntity(list));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } catch (SocketException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        if(responsepost!=null){
            Document doc=client.getDocGBK(responsepost);
//            System.out.println(doc.toString());
            if(doc.toString().toLowerCase().contains(username)){
                //登录成功
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

}
