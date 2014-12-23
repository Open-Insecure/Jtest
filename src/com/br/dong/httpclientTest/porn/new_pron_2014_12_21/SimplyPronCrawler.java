package com.br.dong.httpclientTest.porn.new_pron_2014_12_21;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.JdbcUtil;
import com.br.dong.httpclientTest.porn.PronVideo;
import com.br.dong.httpclientTest.porn.ProxyBean;
import com.br.dong.httpclientTest.porn.VedioBean;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-12-22
 * Time: 下午2:13
 * To change this template use File | Settings | File Templates.
 * 简单的针对91pron的观看地址的采集器
 */
public class SimplyPronCrawler extends Thread {

    long contentLength;//下载的资源大小
    VedioBean video;//视频页面链接
    String downLoadUrl;//下载地址链接
    String localPath;//存放的本地路径
    long receivedCount;//接收到文件的大小
    //--client 的参数
    CrawlerUtil client;
    long downloaded;//已经下载大小
    //视频文件请求url 后跟参数需要拼装
    private static String vedioFileUrl="http://104.20.5.82/getfile.php?";



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
    public SimplyPronCrawler(String name, VedioBean video, CrawlerUtil client) {
        super(name);
        this.contentLength = -1;
        this.video = video;
        this.receivedCount=0;
        this.downloaded=0;
        this.client=client;
        //初始化client
        try {
//            this.client.clientCreate("http", "91p.vido.ws", "http://91p.vido.ws/index.php");
            this.client.clientCreate("http", "91p.vido.ws", video.getVedioUrl());
        } catch (Exception e){
            System.out.println("Constructor SimplyPronCrawler fall");
        }
    }
    /**
     *
     * @throws CloneNotSupportedException
     * @throws IOException
     * @throws org.apache.http.client.ClientProtocolException
     */
    public  void  getInfoDeatilProxy() throws ClientProtocolException, IOException, CloneNotSupportedException{
        ProxyBean proxy= JdbcUtil.getProxy();
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
//            System.out.println(client.getContentCharSet(response.getEntity())+"eeeeeeeeeee");
              doc=client.getDocUTF8(response);
			 System.out.println("rrr"+video.getVedioUrl()+"dddddddd"+doc.toString());
            if(doc!=null/*&&!doc.toString().contains("游客")*/){
                //设置doc到bean中
                //如果代理ip没有超过游客访问次数
                System.out.println("current proxy available["+proxy.toString()+"],ready to download video");
                //解析获得视频下载地址url
                downLoadUrl=getDownLoadUrl(doc,proxy);
//                System.out.println(downLoadUrl);
                if(!downLoadUrl.contains("error")){
                    System.out.println("start to download video:"+downLoadUrl);
                   //获得访问信息
                    getDownloadFileInfo();
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
    /**
     * 设置请求头访问获得资源是否可以正常下载
     */
    private Boolean getDownloadFileInfo()    {
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
            System.out.println(video.getVideoId()+"length:["+contentLength+"]"+"file length:[ "+contentLength/(1024*1024)+"M ]");
            //设置文件大小
            video.setFlag((int)(contentLength/(1024*1024)));
            //设置该文件的视频访问地址
            video.setVedioUrl(downLoadUrl);
            //插入video到数据库中
            JdbcUtil.insertVideo(video);
        }
        httpHead.abort();

        PronVideo.pageGetFlag++;
        System.out.println(downLoadUrl+"|"+video.getVideoId()+"["+ PronVideo.pageGetFlag+"/20]");
        return true;
    }
    /**
     * 拿去post参数，获得中文返回页面
     * */
    public static List<NameValuePair> getPostParmList(){
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("session_language", "cn_CN"));
        return list;
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
        System.out.println("bbb"+doc.toString());
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
            localPath= PronVideo.saveFile+file+".mp4";
            //设置video的属性
            video.setVideoId(file+".mp4");
        } else{
            System.out.println("["+content+"]");
            return "error[page not contains params]" ;
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
    }
}
