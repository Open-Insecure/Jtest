package com.br.dong.httpclientTest.sis001;

import com.br.dong.file.FileOperate;
import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.JdbcUtil;
import com.br.dong.httpclientTest.porn.PronUI;
import com.br.dong.httpclientTest.porn.PronVideo;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-9-29
 * Time: 下午2:18
 * To change this template use File | Settings | File Templates.
 * 针对 http://sis001.com/forum/index.php 采集程序
 */
public class Sis001DownLoadTask extends Thread{
    //登录url
    private static String loginPostUrl="http://sis001.com/forum/logging.php?action=login&loginsubmit=true";
    //html后缀
    private static String html=".html";
    //需要替换的字符,这些字符windows文件名不能出现
    private static String needReplace="(?:/|<|>|:|\\?|\\||\\*|\"|\\(|\\)|)";
//    private  CrawlerUtil client=new CrawlerUtil();
    //相对路径资源前加绝对路径
    private static String absPre="http://sis001.com/forum/";
    //用户名
    private static String username="ckwison";
    //密码
    private static String password="1234qwer!@#$";
    //要采集的版块的url
    private  String zoneurl="";
    //默认最大页数
    private static int maxPage=30;
    //存盘路径目录
    public   String folderpath="F:\\vedios\\torrent\\";
//   public   String folderpath="C:\\sis\\torrent\\";
    private final static int BUFFER = 1024;
    //重写run方法
    //特别注意 线程自己要单独用的变量不要写成static 否则变量被多个线程共享了
    public void run() {
        init(zoneurl);
    }

    /**
     * 线程构造方法
     * @param name 线程名字,本地所存的文件夹名字
     * @param folderpath   线程采集的种子所存的文件夹路径
     * @param zoneurl     线程所采集的版块
     */
    public Sis001DownLoadTask(String name,String folderpath,String zoneurl) {
        super(name);
//        System.out.println(name + "当前传入的文件路径：" + folderpath + "要采集的url:" + zoneurl);
        this.folderpath=folderpath;
        this.zoneurl=zoneurl;
    }

    /**
     * 采集程序初始启动
     * @param zoneurl 对应的要采集的分区的url
     */
    public  void init(String zoneurl){
//        Boolean loginflag=false;
//        loginflag= login(username,password);
//        if(loginflag){
            System.out.println(this.getName()+"登录成功！");
            //获取最大页数 暂时先采集30页数据 将近3个月的数据
//            getPages(zoneUrl+"1"+html);
            for(int i=1;i<=maxPage;i++){
                String p=zoneurl+i+html;
                System.out.println(this.getName()+"正在采集:"+p);
                listPageAnalyse(p);
            }

//        }else{
//            System.out.println(username+"登录失败，尝试重新登录！");
//            init(zoneurl);
//        }
    }

    /**
     * 下载程序
     * @param bean
     */
    public  void downloadTest(SisTorrentBean bean){
        //检查数据库中是否已经有该种子的记录
        List rows=JdbcUtil.checkSameTorrent(bean.getTorrentUrl());
        if(rows==null||rows.size()==0){
            //没有相同记录，则进行下载种子与插入数据库
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(bean.getTorrentUrl());
            try {
                HttpResponse response= client.execute(httpGet);
                //请求成功
                HttpEntity entity = response.getEntity();
                InputStream in = entity.getContent();
                //重新命名文件
                String title=bean.getTitle().replaceAll(needReplace,"")+".torrent";
                //重新命名bean的title
                bean.setTitle(title);
                //保存的种子文件路径
                File file=new File(folderpath+title);
//            FileOutputStream out = new FileOutputStream(new File("F:\\test_jar\\bb.torrent"));
                FileOutputStream out = new FileOutputStream(file);
                byte[] b = new byte[BUFFER];
                int len = 0;
                while((len=in.read(b))!= -1){
                    out.write(b,0,len);
                }
                in.close();
                out.close();
                //在此插入到数据库中
                JdbcUtil.insertTorrent(bean);
//            System.out.println(bean.toString());
                System.out.println(file.getName()+"download, success!!");
            } catch (SocketException e){

            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                httpGet.abort();
            }

        }else{
            System.out.println(bean.getTitle()+"已经存在");
        }
}
    /**
     * 种子详细页面分析
     * @param bean
     */
   public  void detailPageAnalyse(SisTorrentBean bean){
       Document doc=null;
       try {
           HttpResponse response=Sis001Task.client.noProxyGetUrl(bean.getUrl());
           if(response==null){
               return ;
           }
           doc= Sis001Task.client.getDocGBK(response);
       } catch (IOException e) {
           e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
       } catch (CloneNotSupportedException e) {
           e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
       }
       if(doc!=null){
          try{
              String  picurl=doc.select("div[id^=postmessage_]").first().select("img").attr("src");//种子视频预览图片地址
              String torrenDownLoadUrl=absPre+doc.select("dl[class^=t_attachlist]>dt").select("a:eq(2)").attr("href");//种子下载地址
              bean.setPicUrl(picurl);
              bean.setTorrentUrl(torrenDownLoadUrl);
              downloadTest(bean);
          }catch (NullPointerException e){
              System.out.println("NullPointerException...");
          }

       }
   }

    /**
     *种子列表页面分析
     * @param targetUrl
     */
    public  void listPageAnalyse(String targetUrl){
        Document doc=null;
        try {
            HttpResponse response=Sis001Task.client.noProxyGetUrl(targetUrl);
            if(response!=null){
                doc= Sis001Task.client.getDocGBK(response);
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(doc!=null){
//            System.out.println(doc.toString());
            Elements elements= doc.select("tbody[id^=normalthread_]");
//            System.out.println(elements.toString()); //打印采集内容
            for(Element element:elements){
               String type= element.select("em:eq(1)>a").text();  //类型
               String time= element.select("em:eq(0)>a").text();  //更新时间
               String title=element.select("span[id^=thread_]>a").text();  //标题
               String url=absPre+element.select("span[id^=thread_]>a").attr("href");//该帖子的绝对链接地址
               String size=element.select("td[class=nums]").select(":eq(5)").text();   //视频大小和类型
//               System.out.println(type+"|"+url+"|"+time+"|"+title+"|"+size);
               //每一行的内容进入详细url进行下载操作
               SisTorrentBean bean=new SisTorrentBean(this.getName(), type,title,url,size,time);
               //进入详细
               detailPageAnalyse(bean);
            }
        }
    }
    /**
     * 获得目标页面的最大页数
     * @param targetUrl
     */
    public  int getPages(String targetUrl){
        Document doc=null;
        try {
            doc= Sis001Task.client.getDocGBK(Sis001Task.client.noProxyGetUrl(targetUrl));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(doc!=null){
            //查找最大页数
            Element maxPageE= doc.select("div[class=pages_btns]>div>a[class=last]").first();
            System.out.println("最大页数:"+maxPageE.text().replace("... ","").trim());
            try{
                maxPage=Integer.parseInt(maxPageE.text().replace("... ","").trim());
            } catch (NumberFormatException e){
                return maxPage;
            }

        }
        return maxPage;
    }

    /**
     * 登录sis001
     */
    public  Boolean login(String username,String password) {
        try {
            Sis001Task.client.clientCreatNoUrl("http");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
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
            responsepost = Sis001Task.client.post(loginPostUrl, Sis001Task.client.produceEntity(list));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } catch (SocketException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        if(responsepost!=null){
            Document doc=Sis001Task.client.getDocGBK(responsepost);
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
