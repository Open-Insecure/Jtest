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
 */
public class Sis001Task {
    //线程池
    private static ExecutorService threadPool= Executors.newCachedThreadPool();
    //本地硬盘
    private static String floderpath="F:\\vedios\\sis\\";
//    private  static String floderpath="C:\\sis\\download\\";
    public static CrawlerUtil client=new CrawlerUtil();
    //登录url
    private static String loginPostUrl="http://38.103.161.188/forum/logging.php?action=login&loginsubmit=true";
    //用户名
    private static String username="ckwison";
    //密码
    private static String password="1234qwer!@#$";
    public static void main(String[] args) {

        //登录
        Boolean loginflag=false;
        loginflag= login(username,password);
        if(!loginflag){
            System.out.println(username+"登录失败，尝试重新登录！");
              return ;
        }
        System.out.println(username+"登录成功");

//        Sis001DownLoadTask test=new Sis001DownLoadTask("url","F:\\vedios\\torrent\\","http://38.103.161.188/forum/forum-270-");
//        test.start();
         start();
    }

    /**
     * 开始种子线程
     * 种子类的线程 Sis001DownLoadTask name以 bt_ 开头 对应数据库urls表中的floderName字段
     * url类的线程 Sis001DownLoadTask name以 url_ 开头 对应数据库urls表中的floderName字段
     */
    public static void start(){
        //获取urls表中flag=torrent的数据
        //获取urls表中flag=url    还有一个问题注意要改了！ 用getAllUrls方法
        List rows= JdbcUtil.getAllUrls();
        threadPoolStart(rows);
    }

    /**
     * 线程池开始
     * @param rows
     */
    public static void threadPoolStart(List rows){
        for(int i=0;i<rows.size();i++){
            Map map= (Map) rows.get(i);
//            System.out.println(floderpath+(String)map.get("floderName")+date+"\\");
            String finalFloderPath=floderpath+map.get("type")+"\\"+(String)map.get("floderName")+DateUtil.getCurrentDay()+"\\"; //拼装最终的存储文件夹
            newFolderMuti(finalFloderPath); //创建对应文件夹
            String finalUrl=(String)map.get("url");
            threadPool.execute(new Sis001DownLoadTask((String)map.get("floderName")+DateUtil.getCurrentDay(), finalFloderPath, finalUrl));
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
            HttpResponse response=client.noProxyGetUrl("http://38.103.161.188/forum/index.php");
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
