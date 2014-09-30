package com.br.dong.httpclientTest.sis001;

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
public class Sis001Test {
    //登录url
    private static String loginPostUrl="http://sis001.com/forum/logging.php?action=login&loginsubmit=true";
    //亚洲无码转帖
    private static String ywzt="http://sis001.com/forum/forum-25-1.html";
    //亚洲无码原创
    private static String ywyc="http://sis001.com/forum/forumdisplay.php?fid=143";
    private static CrawlerUtil client=new CrawlerUtil();
    //相对路径资源前加绝对路径
    private static String absPre="http://sis001.com/forum/";
    //用户名
    private static String username="ckwison";
    //密码
    private static String password="1234qwer!@#$";
    //默认最大页数
    private static int maxPage=1000;
    public  static String saveFile="F:\\vedios\\new\\";
    private final static int BUFFER = 1024;
    public static void main(String[] args) {
        Boolean loginflag=false;
              loginflag= login(username,password);
        if(loginflag){
            System.out.println(username+"登录成功！");
//           登录成功后
//            getPages(ywzt);
//            test(ywzt);
            test2("http://sis001.com/forum/thread-9195173-1-1.html");
            downloadTest("http://sis001.com/forum/attachment.php?aid=2502527");
        }else{
            System.out.println(username+"登录失败！");
        }
    }
    public static void downloadTest(String url){
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response= client.execute(httpGet);
            //请求成功
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();

            FileOutputStream out = new FileOutputStream(new File("F:\\test_jar\\aa.torrent"));

            byte[] b = new byte[BUFFER];
            int len = 0;
            while((len=in.read(b))!= -1){
                out.write(b,0,len);
            }
            in.close();
            out.close();

        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            httpGet.abort();
        }
        System.out.println("download, success!!");
}
    /**
     * 设置请求头访问获得资源是否可以正常下载
     */
    private Boolean getDownloadFileInfo(String downLoadUrl) throws IOException,
            ClientProtocolException,HttpHostConnectException, Exception {
        long contentLength=0;
        //访问请求头
        HttpHead httpHead = new HttpHead(downLoadUrl);
        HttpResponse response = client.executeHead(httpHead);
        if(response==null){
            return false;
        }
        Header[] headers = response.getHeaders("Content-Length");
        if (headers.length > 0)
        {	//获得要下载的文件的大小
            contentLength = Long.valueOf(headers[0].getValue());
            //如果文件大于50MB就删了吧 或者等于0的 也不采集了
            if(contentLength/(1024*1024)>50||contentLength/(1024*1024)<=2){
                return false;
            }
            //设置文件大小
        }
        httpHead.abort();
        return true;
    }
    public static void startDownload(String downLoadUrl) throws IOException {
        String localPath=saveFile+"a.torrent";

    }
    /**
     * 测试种子页面采集信息
     * @param targetUrl
     */
   public static void test2(String targetUrl){
       Document doc=null;
       try {
           doc= client.getDocGBK(client.noProxyGetUrl(targetUrl));
       } catch (IOException e) {
           e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
       } catch (CloneNotSupportedException e) {
           e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
       }
       if(doc!=null){
          String  prcurl=doc.select("div[id^=postmessage_]").first().select("img").attr("src");//种子视频预览图片地址
          String torrenDownLoadUrl=absPre+doc.select("dl[class^=t_attachlist]>dt").select("a:eq(2)").attr("href");//种子下载地址
          System.out.println(prcurl+torrenDownLoadUrl);
       }
   }

    /**
     * 测试解析种子列表页面下面所有种子帖子信息
     * @param targetUrl
     */
    public static void test(String targetUrl){
        Document doc=null;
        try {
            doc= client.getDocGBK(client.noProxyGetUrl(targetUrl));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(doc!=null){
//            System.out.println(doc.toString());
            Elements elements= doc.select("tbody[id^=normalthread_]");
            System.out.println(elements.toString());
            for(Element element:elements){
               String type= element.select("em:eq(1)>a").text();  //类型
               String time= element.select("em:eq(0)>a").text();  //更新时间
               String title=element.select("span[id^=thread_]>a").text();  //标题
                String url=absPre+element.select("span[id^=thread_]>a").attr("href");//该帖子的绝对链接地址
               String size=element.select("td[class=nums]").text();   //视频大小和类型

                System.out.println(type+url+time+title+size);
            }
        }
    }
    /**
     * 获得目标页面的最大页数
     * @param targetUrl
     */
    public static int getPages(String targetUrl){
        Document doc=null;
        try {
            doc= client.getDocGBK(client.noProxyGetUrl(targetUrl));
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
    public static Boolean login(String username,String password) {
        try {
            client.clientCreatNoUrl("http");
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
