package com.br.dong.httpclientTest.proxy_2015_03_14;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.JdbcUtil;
import com.br.dong.httpclientTest.porn.PronVideo;
import com.br.dong.httpclientTest.porn.ProxyBean;
import com.br.dong.httpclientTest.porn.VedioBean;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-4-21
 * Time: 10:32
 * 检查代理ip是否可以用来检测91porn或者caoporn的某个视频播放地址
 *
 * http://91.v4p.co/v.php?next=watch  91新地址
 */
public class PornCheck {
    long contentLength;//下载的资源大小
    private static String downLoadUrl;//下载地址链接
    private static   String localPath;//存放的本地路径
    long receivedCount;//接收到文件的大小
    //--client 的参数
    public static CrawlerUtil client=new CrawlerUtil();
    long downloaded;//已经下载大小
//    private static String vedioFileUrl="http://91p.vido.ws/getfile.php?";    //91porn获得视频地址接口。视频文件请求url 后跟参数需要拼装
    private static String vedioFileUrl="http://91.v4p.co/getfile.php?";    //91porn获得视频地址接口。视频文件请求url 后跟参数需要拼装
    private static String caoPron="http://198.105.209.230/media/player/cpconfig.php?vkey=";    //caoporn获得视频源地址的接口
    public static void main(String[] args) {
        try {
            //202.119.25.227 超过了     111.161.126.98
            String proxyStr="171.36.131.222:8123 59.57.171.7:8090 61.174.13.223:8080 120.39.81.133:8090 119.253.252.27:3128 115.215.95.30:8090 219.140.136.218:8090 110.86.80.232:8090 61.38.252.13:3128 116.113.47.26:8080 182.147.202.27:8090 114.228.207.211:8090 110.73.40.22:8123 124.133.123.119:8090 182.40.61.238:8090 219.140.136.187:8090 110.73.10.118:8123 139.226.151.74:8090 27.184.173.162:8090 122.230.236.63:8090 182.113.114.3:8090 182.90.21.48:8090 61.153.100.30:8080 115.196.153.209:8118 210.28.164.1:3128 219.140.137.124:8090 182.54.115.99:8090 114.228.207.37:8090 139.226.112.107:8090 59.38.59.37:8090 42.184.29.53:8090 113.78.10.148:8090 114.228.192.33:8090 110.73.52.118:8123 150.255.85.34:8090 139.201.248.207:8090 27.40.102.44:8090 116.231.196.41:9999 110.73.156.139:8123 221.235.83.192:8090 110.73.196.29:8123 61.153.100.19:8080 119.96.233.214:8090 117.25.14.183:8090 183.145.33.141:8090 202.194.234.123:8080 101.69.181.164:8080 221.203.172.229:8090 27.151.252.89:8090 182.40.159.101:8090";
            String temp[]=proxyStr.split(" ");
            for(int i=0;i<temp.length;i++){
                String p[]=temp[i].split(":");
                testProxyFor91porn(p[0], Integer.parseInt(p[1]), "http://91.v4p.co/view_video.php?viewkey=55f767c19a4c6f588681");
            }

//            localVist91Porn("http://91.v4p.co/view_video.php?viewkey=55f767c19a4c6f588681");

        } catch (Exception e) {
            System.out.println("创建client失败");
        }
    }

    /**
     * 本地直接访问91解析获得视频源地址
     */
    public static void localVist91Porn(String targetUrl) throws NoSuchAlgorithmException, KeyManagementException, IOException, CloneNotSupportedException {
        client.clientCreate("http", "91.v4p.co", targetUrl);
        //代理post访问视频页面
//        HttpResponse response=client.noProxyPostUrl(targetUrl,getPostParmList());
        HttpResponse response=client.noProxyGetUrl(targetUrl);
        if(response!=null){
            Document doc=client.getDocUTF8(response);
            if(doc!=null){
                System.out.println(doc.toString());
            }
        }

    }


    /**
     * 通过代理检查caoporn的
     */
    public static String testProxyForCaoporn(String ip,int port,String targetUrl) throws NoSuchAlgorithmException, KeyManagementException, IOException, CloneNotSupportedException {
        client.clientCreatNoUrl("http");
        String temp[]=targetUrl.split("/");
        String vkey=temp[4]; //解析出viewkey
        HttpResponse response=client.proxyGetUrl(caoPron + vkey, ip, port) ;     //caopron站点get方式获得视频源文件地址
        if(response!=null){
            Document doc=client.getDocUTF8(response);
            //如果doc不为空，且doc中包含file字段，证明当前代理连接的目标页面成功解析出视频路径
            if(doc!=null&&doc.toString().contains("<file>")){
//                System.out.println(doc.toString());
                //解析xml形式的返回文件
                int start=doc.toString().indexOf("<file>");
                int end=doc.toString().indexOf("</file>");
                if(start!=-1&&end!=-1){
//                 System.out.println("parse result:"+doc.toString().substring(start+"<file>".length(),end).trim());
                    return "success:"+ doc.toString().substring(start+"<file>".length(),end).trim(); //解析出视频源地址
                }
                return "解析doc失败:"+doc.toString();
            }
            else{
                return "doc为空或者doc中不包含<file>";
            }

        }else{
            return "连接代理失败,response为空";
        }
    }

    /**
     * 检查91porn的
     */
    public  static String  testProxyFor91porn(String ip,int port,String url) throws ClientProtocolException, IOException, CloneNotSupportedException, NoSuchAlgorithmException, KeyManagementException {
        client.clientCreate("http", "91.v4p.co", url);
//        client.clientCreatNoUrl("http");
        System.out.println("start connet to proxy:["+ip+":"+port+"]");
        //代理post访问视频页面
        HttpResponse response=client.proxyPostUrl(url, ip, port,getPostParmList()) ;     //caopron站点get方式获得视频源文件地址
        Document doc=null;
        if(response==null){
            //连接代理失败，换下一个代理
            System.out.println("fail to connect:[" + ip + ":" + port + "]");
            return   "fail to connect:[" + ip + ":" + port + "]";
        }else{//连接代理成功 解析url
            doc=client.getDocUTF8(response);
//            System.out.println("dddd:"+doc.toString());
            if(doc!=null&&!doc.toString().contains("游客")){
//                System.out.println(doc.toString());
                //解析获得视频下载地址url
                System.out.println("开始解析doc");
                downLoadUrl=getDownLoadUrl(doc);
                if(!downLoadUrl.contains("error")){
                    System.out.println("start to download video:"+downLoadUrl);
                }else{
                    //处理访问拿到下载地址页面失败的错误
                    System.out.println(downLoadUrl);
                    //回调
                }
                return downLoadUrl;
            }else{
                //超过访问次数
                System.out.println("current proxy "+ip+":"+port+"more than number of visits..");
                return "current proxy "+ip+":"+port+"more than number of visits..";
            }
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
    /**解析参数返回获得视频源地址的链接
     * @return
     * @throws CloneNotSupportedException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static  String getDownLoadUrl(Document doc) throws ClientProtocolException, IOException, CloneNotSupportedException{
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
            localPath= PronVideo.saveFile+file+".mp4";
        } else{
            System.out.println("没有 seccode ["+content+"]");
        }
        //拿到包含下载地址的页面
        String tempUrl=vedioFileUrl+"VID="+map.get("VID")+"&seccode="+map.get("seccode")+"&mp4="+mp4+"&max_vid="+map.get("max_vid");
        System.out.println("包含下载地址的页面:"+tempUrl);
        //--异常 跳转的下载地址
        HttpResponse response=client.noProxyGetUrl(tempUrl);
        if(response!=null){
            //解析下载地址页面
            Document tempdoc=client.getDocUTF8(response);
            System.out.println("下载页面"+tempdoc.toString());
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
}
