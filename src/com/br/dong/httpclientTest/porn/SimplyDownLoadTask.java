package com.br.dong.httpclientTest.porn;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.DownloadTaskListener;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-24
 * Time: 下午12:36
 * To change this template use File | Settings | File Templates.
 * 简单的下载类
 *                       _ooOoo_
 *                      o8888888o
 *                      88" . "88
 *                      (| ^_^ |)
 *                      O\  =  /O
 *                   ____/`---'\____
 *                 .'  \\|     |//  `.
 *                /  \\|||  :  |||//  \
 *               /  _||||| -:- |||||-  \
 *               |   | \\\  -  /// |   |
 *               | \_|  ''\---/''  |   |
 *               \  .-\__  `-`  ___/-. /
 *             ___`. .'  /--.--\  `. . ___
 *           ."" '<  `.___\_<|>_/___.'  >'"".
 *         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *         \  \ `-.   \_ __\ /__ _/   .-` /  /
 *   ========`-.____`-.___\_____/___.-`____.-'========
 *                        `=---='
 *   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *         佛祖保佑       永无BUG        永不修改
 */
public class SimplyDownLoadTask extends Thread {

    long contentLength;//下载的资源大小
    VedioBean video;//视频页面链接
    String downLoadUrl;//下载地址链接
    String localPath;//存放的本地路径
    long receivedCount;//接收到文件的大小
    //--client 的参数
     CrawlerUtil client;
    long downloaded;//已经下载大小
    //视频文件请求url 后跟参数需要拼装
    private static String vedioFileUrl="http://91p.vido.ws/getfile.php?";
    //--下载程序使用的参数
     private static String saveFile="f:/vedios/new/";
    //  private static String saveFile="c:/vedios/new20140830/";

    //重写run方法
    public void run() {


        try {
            getInfoDeatilProxy();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    /**
     * 简单下载器类构造方法 在此初始化client
     * @param video      下载地址
     * @param client    client实例
     */
    public SimplyDownLoadTask(String name, VedioBean video, CrawlerUtil client) {
        super(name);
        this.contentLength = -1;
        this.video = video;
        this.receivedCount=0;
        this.downloaded=0;
        this.client=client;
        //初始化client
        try {
            this.client.clientCreate("http", "91p.vido.ws", "http://91p.vido.ws/index.php");
        } catch (Exception e){
            System.out.println("Constructor SimplyDownLoadTask fall");
        }
    }
    /**
     * 拿去post参数，获得中文返回页面
     * */
    public static List<NameValuePair> getPostParmList(){
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("session_language", "cn_CN"));
        return list;
    }
    /**
     *
     * @throws CloneNotSupportedException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public  void  getInfoDeatilProxy() throws ClientProtocolException, IOException, CloneNotSupportedException{
        ProxyBean proxy=JdbcUtil.getProxy();
        System.out.println("start connet to proxy:["+proxy.toString()+"]");
        //代理post访问视频页面
        HttpResponse response=client.proxyPostUrl(video.getVedioUrl(),proxy.getIp(),proxy.getPort(),getPostParmList());
        Document doc=null;
        if(response==null){
            //连接代理失败，换下一个代理
            System.out.println("fail to connect:["+proxy.toString()+"]");
            JdbcUtil.deleteProxyByIp(proxy);
            //回调 重新设置代理
            getInfoDeatilProxy();
            //代理失败以后 删除代理表中的代理
        }else{//连接代理成功 解析url
            doc=client.getDocUTF8(response);
//			 System.out.println(doc.toString());
            if(doc!=null&&!doc.toString().contains("游客")){
                //设置doc到bean中
                //如果代理ip没有超过游客访问次数
                System.out.println("current proxy available["+proxy.toString()+"],ready to download video");
                //解析获得视频下载地址url
                downLoadUrl=getDownLoadUrl(doc,proxy);
//                System.out.println(downLoadUrl);
                if(!downLoadUrl.contains("error")){
                    System.out.println("start to download video:"+downLoadUrl);
                    //开始下载
                    this.startDown();
                }else{
                    //处理访问拿到下载地址页面失败的错误
                    System.out.println(downLoadUrl);
                    //回调
                    getInfoDeatilProxy();
                }
            }else{
                //超过访问次数
                System.out.println("current proxy "+proxy.toString()+"more than number of visits..");
                //回调重新设置代理
                getInfoDeatilProxy();
            }
        }

    }

    /**解析参数返回获得视频源地址的链接
     * @return
     * @throws CloneNotSupportedException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public  String getDownLoadUrl(Document doc,ProxyBean proxy) throws ClientProtocolException, IOException, CloneNotSupportedException{
        Map map=new HashMap();
        String file="";
        String max_vid="";
        String seccode="";
        String mp4="";
        String downUrl="";
        //视频页面的html信息
        String content=doc.toString();
//        System.out.println("bbb"+doc.toString());
        if(content.contains("seccode")){
//            System.out.println("sss"+content);
            //截取包含参数的临时字符串
            String temp=content.substring(content.indexOf("so.addParam('allowscriptaccess'"),content.indexOf("so.write('mediaspace');")).replace("\n", "");
            String [] arr=temp.split(";");
            for(int i=0;i<arr.length;i++){
                if(arr[i].contains("file")){
                    file=arr[i].replaceAll("(?:so.addVariable|\\(|file|,|'|\"|\\))", "");// 替换掉不相关的
                }else if(arr[i].contains("max_vid")){
                    max_vid=arr[i].replaceAll("(?:so.addVariable|\\(|max_vid|,|'|\"|\\))", "");// 替换掉不相关的
                }else if(arr[i].contains("seccode")){
                    seccode=arr[i].replaceAll("(?:so.addVariable|\\(|seccode|,|'|\"|\\))", "");// 替换掉不相关的
                }else if(arr[i].contains("mp4")){
                    mp4=arr[i].replaceAll("(?:so.addVariable|\\(|mp4|,|'|\"|\\))", "");// 替换掉不相关的
                }
            }
            map.put("VID", file);
            map.put("max_vid", max_vid);
            map.put("seccode", seccode);
            map.put("mp4", mp4);
            //设置存放路径
            localPath=saveFile+file+".mp4";
            //设置video的属性
            video.setVideoId(localPath);

        } else{
            System.out.println("["+content+"]");
        }
        //拿到包含下载地址的页面
        String tempUrl=vedioFileUrl+"VID="+map.get("VID")+"&seccode="+map.get("seccode")+"&mp4="+mp4+"&max_vid="+map.get("max_vid");
        //--异常 跳转的下载地址
        HttpResponse response=client.noProxyGetUrl(tempUrl);
        if(response!=null){
            //解析下载地址页面
            Document tempdoc=client.getDocUTF8(response);
//            System.out.println("下载页面"+tempdoc.toString());
            //包含下载地址
            if (tempdoc.text() != null && tempdoc.text().contains("file=http://")) {
                //过滤出真正的下载地址
                downUrl=tempdoc.text().substring(
                        tempdoc.text().indexOf("﻿file=") + 6,
                        tempdoc.text().indexOf("&domainUrl"));
                //http://50.7.69.10//dl//8bb3e2c39d328430db7f9811a06fe8dd/53f1b5b5//91porn/mp43/83954.mp4
                //http://107.155.123.34//dl//81b0256e55efc72fd8d4c5d1889b1684/53f1bc61//91porn/mp43/83954.mp4
                return downUrl;
            } else{
                String msg="error[analyze download url fall]"+tempdoc.toString();
                return msg;
            }

        } else{
            //解析下载地址出错!!!!!!!这里还没处理
            String msg="error[visit download page error]";
            return msg;

        }

//
    }
    /**
     * 设置请求头访问获得资源是否可以正常下载
     */
    private Boolean getDownloadFileInfo() throws IOException,
            ClientProtocolException,HttpHostConnectException, Exception {
        //访问请求头
        HttpHead httpHead = new HttpHead(downLoadUrl);
        HttpResponse response = client.executeHead(httpHead);
        if(response==null){
            response = client.executeHead(httpHead);
        }
        // 获取HTTP状态码
//        int statusCode = response.getStatusLine().getStatusCode();

//        if (statusCode != 200){
//            System.out.println("resources unavailable：["+statusCode+"] for：["+downLoadUrl+"]");
//            //循环采集
//            getDownloadFileInfo();
//            return false;
//        }
        // Content-Length
        Header[] headers = response.getHeaders("Content-Length");
        if (headers.length > 0)
        {	//获得要下载的文件的大小
            contentLength = Long.valueOf(headers[0].getValue());
            System.out.println("file length:[ "+contentLength/(1024*1024)+"M ]");
        }
        httpHead.abort();
        return true;
    }
    /**开始下载 使用CrawlerUtil 创建HttpClient实例
     * @throws Exception
     */
    public void  startDown() {
//		CrawlerUtil client = new CrawlerUtil();
        //创建http请求的client
        try {
            //判断下载链接是否有效
            if(getDownloadFileInfo()){
                startDownload();
            }
        } catch (Exception e) {
            System.out.println("start download fall.." + downLoadUrl);
            e.printStackTrace();
            try {
                getInfoDeatilProxy();
            } catch (IOException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (CloneNotSupportedException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }


    public void startDownload() throws IOException {
        // 创建下载文件
        final File file = new File(localPath);
       try{
           file.createNewFile();
           RandomAccessFile raf = new RandomAccessFile(file, "rw");
           raf.setLength(contentLength);//分配文件空间
           raf.close();
       }catch (Exception e){

           System.out.println("creat file error");
           e.printStackTrace();
           return ;
       }
        //下载计数器+1
        synchronized(this){
            PronVideo.pageGetFlag++;
            System.out.println("start download.."+downLoadUrl+" already download:"+ PronVideo.pageGetFlag+"/20");
        }
        //插入video到数据库中
        JdbcUtil.insertVideo(video);
        // 定义下载线程事件实现类
        final Calendar time = Calendar.getInstance();
        final long startMili = System.currentTimeMillis();// 当前时间对应的毫秒数
        //开始下载
       try{
//           HttpGet httpGet = new HttpGet(downLoadUrl);
           HttpResponse response = client.noProxyGetUrl(downLoadUrl);
           //注意此处response为null的错误
           if(response!=null){
               java.io.InputStream inputStream = response.getEntity()
                       .getContent();
               RandomAccessFile outputStream = new RandomAccessFile(file,
                       "rw");
               byte[] buffer = new byte[10240];
               int count = 0;
               while ((count = inputStream.read(buffer, 0, buffer.length)) > 0) {
                   outputStream.write(buffer, 0, count);
                   downloaded += count;
               }
               //不能瞎比close
//           outputStream.close();
           }
       }catch (Exception e){
           System.out.println(downLoadUrl+"download error");
           e.printStackTrace();
       }finally {
           System.out.println(downLoadUrl+"download completed");
           //下载视频计数器
//           client.closeClient();
//           outputStream.close();
       }
    }

}


