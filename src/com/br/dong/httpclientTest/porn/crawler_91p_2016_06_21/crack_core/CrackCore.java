package com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21.crack_core;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21.repo.VideoBean;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21.crack_core
 * AUTHOR: hexOr
 * DATE :2016-08-01 18:00
 * DESCRIPTION:
 */
public class CrackCore {
    //视频文件请求url 后跟参数需要拼装
    private static String vedioFileUrl="http://email.91dizhi.at.gmail.com.9h3.space/getfile.php?";
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, IOException, CloneNotSupportedException {
        //---测试页面的
//        String url="http://email.91dizhi.at.gmail.com.9h3.space/v.php?next=watch&page=1";
//        CrawlerUtil crawlerUtil=new CrawlerUtil();
//        crawlerUtil.clientCreate("http", "email.91dizhi.at.gmail.com.9h3.space", url);
//        CrackCore.crackVideoList(crawlerUtil,url);
        //---测试破解详情
        String url="http://email.91dizhi.at.gmail.com.9h3.space/view_video.php?viewkey=edd51eb7488016ffe659&page=2&viewtype=basic&category=mr";
        CrawlerUtil crawlerUtil=new CrawlerUtil();
        crawlerUtil.clientCreate("http", "email.91dizhi.at.gmail.com.9h3.space", url);
//        crawlerUtil.clientByProxyCreate("http", "email.91dizhi.at.gmail.com.9h3.space", url);
//        System.out.println(CrackCore.crackVideoUrl(crawlerUtil, url));
        String proxys=
                "223.244.34.13:8998\n" +
                        "120.236.148.199:2226\n" +
                        "125.127.178.170:8998\n" +
                        "125.135.179.153:8080\n" +
                        "27.39.225.89:8080\n" +
                        "49.72.71.31:8998\n" +
                        "119.51.243.57:8118\n" +
                        "114.138.184.206:8998\n" +
                        "58.253.71.197:808\n" +
                        "120.32.115.133:80\n" +
                        "219.141.225.108:80\n" +
                        "121.14.36.38:8080\n" +
                        "182.90.252.10:2226\n" +
                        "221.199.203.106:3128\n" +
                        "49.73.170.162:808\n" +
                        "124.113.23.22:8118\n" +
                        "114.228.10.166:8118\n" +
                        "183.239.225.44:8888\n" +
                        "183.91.33.42:8086\n" +
                        "113.73.102.36:8118\n" +
                        "116.25.80.164:8118\n" +
                        "36.7.237.62:8998\n" +
                        "1.71.160.128:8118\n" +
                        "124.135.52.125:8118\n" +
                        "202.100.5.42:8118\n" +
                        "218.7.206.249:8998\n" +
                        "112.93.100.222:8998\n" +
                        "123.7.177.20:9999\n" +
                        "211.138.156.209:80\n" +
                        "112.16.21.18:2226\n" +
                        "110.244.8.188:8998\n" +
                        "60.190.99.173:443\n" +
                        "124.132.98.114:8998\n" +
                        "58.52.201.119:8080\n" +
                        "101.231.250.102:80\n" +
                        "123.124.168.149:80\n" +
                        "111.121.174.149:8998\n" +
                        "14.222.115.124:8888\n" +
                        "211.143.45.216:3128\n" +
                        "180.116.241.83:8888\n" +
                        "60.167.21.167:8118\n" +
                        "218.60.29.219:8080\n" +
                        "121.204.212.142:8888\n" +
                        "58.248.200.194:80\n" +
                        "218.26.237.18:3128\n" +
                        "123.165.175.137:8998\n" +
                        "122.193.14.106:82\n" +
                        "183.196.9.132:2226\n" +
                        "125.65.112.201:8008\n" +
                        "183.91.33.41:86\n" +
                        "125.111.171.158:8118\n" +
                        "122.13.214.134:8080\n" +
                        "110.85.115.146:8118\n" +
                        "1.71.128.85:80\n" +
                        "111.123.90.95:8998\n" +
                        "223.215.211.239:8998\n" +
                        "125.113.2.21:8118\n" +
                        "202.111.9.106:23\n" +
                        "182.105.29.71:8118\n" +
                        "183.131.76.27:8888\n" +
                        "113.135.178.15:8998\n" +
                        "123.165.160.47:8998\n" +
                        "114.238.85.76:8998\n" +
                        "113.248.165.23:8998\n" +
                        "117.40.35.233:8998\n" +
                        "183.91.33.43:90\n" +
                        "61.178.63.90:63000\n" +
                        "119.188.94.145:80\n" +
                        "111.1.3.36:8000\n" +
                        "61.178.139.8:8998\n" +
                        "114.244.215.46:8118\n" +
                        "111.35.143.237:8998\n" +
                        "60.13.226.254:2226\n" +
                        "223.95.113.199:80\n" +
                        "113.120.112.142:8118\n" +
                        "58.214.5.229:80\n" +
                        "183.128.168.161:8998\n" +
                        "36.47.178.125:8118\n" +
                        "36.47.211.147:8998\n" +
                        "222.220.34.74:1337\n" +
                        "61.178.238.122:63000\n" +
                        "121.224.89.44:8998\n" +
                        "111.13.7.42:81\n" +
                        "60.13.74.143:80\n" +
                        "180.104.204.229:8998\n" +
                        "218.164.170.248:8998\n" +
                        "60.161.137.168:63000\n" +
                        "121.9.128.198:8998\n" +
                        "117.36.197.152:3128\n" +
                        "180.213.69.64:8998\n" +
                        "58.250.177.14:8118\n" +
                        "180.162.214.218:8998\n" +
                        "60.186.23.49:8118\n" +
                        "115.53.157.114:8998\n" +
                        "117.135.250.88:83\n" +
                        "122.230.5.195:8998\n" +
                        "60.184.131.208:8998\n" +
                        "27.190.226.184:8998\n" +
                        "183.91.33.44:81\n" +
                        "180.125.36.60:8998";
        String [] temp=proxys.split("\n");
       for(int i=0;i<temp.length;i++){
           String []t=temp[i].split(":");
          try{
              System.out.println("proxy:"+t[0]+"port:"+t[1]);
              System.out.println("mp44444444:"+CrackCore.crackVideoUrl(crawlerUtil, url,t[0],Integer.parseInt(t[1])));
          }catch (Exception e){
              e.printStackTrace();
          }
       }
        //--
        //http://email.91dizhi.at.gmail.com.9h0.space/getfile.php?VID=171318&mp4=0&seccode=88d99241ce6ad174e2617e85bfe9bc14&max_vid=171389
        //http://email.91dizhi.at.gmail.com.9h3.space/getfile.php?VID=171316&mp4=0&seccode=6b18e3f0c9c2e112485bb5cd52f76ce4&max_vid=171392
//        System.out.println(urlDecode("http%3A%2F%2F198.255.46.162%2F%2Fmp43%2F171316.mp4%3Fst%3DzJMO_E26XDWYs0rDH4CUFg%26e%3D1470061617"));
        //http://192.240.120.2//mp43/171316.mp4?st=w3qHP7dMrIQjxarFbz8AIg&e=1470056244
    }

    public static String parseVideoUrl(CrawlerUtil crawlerUtil,HttpResponse response_detail,String proxyIp,int proxyPort) throws IOException, CloneNotSupportedException {
        Document doc=crawlerUtil.getDocUTF8(response_detail);
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
        } else{
            System.out.println("["+content+"]");
        }
        //拿到包含下载地址的页面
        String tempUrl=vedioFileUrl+"VID="+map.get("VID")+"&seccode="+map.get("seccode")+"&mp4="+mp4+"&max_vid="+map.get("max_vid");
        System.out.println(tempUrl);
        //--异常 跳转的下载地址
        HttpResponse response=crawlerUtil.proxyGetUrl(tempUrl,proxyIp,proxyPort);
        if(response!=null){
            //解析下载地址页面
            Document tempdoc=crawlerUtil.getDocUTF8(response);
//            System.out.println("下载页面"+tempdoc.toString());
            //包含下载地址
            if (tempdoc.text() != null && tempdoc.text().contains("file=http")) {
                //过滤出真正的下载地址
                downUrl=tempdoc.text().substring(
                        tempdoc.text().indexOf("file=") + 5,
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
    /***
     * 视频详情页面破解获取视频地址
     * @param crawlerUtil 实例化的破解工具
     * @param targetUrl 目标url
     * @return
     */
    public static String crackVideoUrl(CrawlerUtil crawlerUtil,String targetUrl) throws IOException, CloneNotSupportedException {
//        HttpResponse response_detail=crawlerUtil.post(targetUrl, crawlerUtil.produceEntity(getPostParmList()));
//         return parseVideoUrl(crawlerUtil,response_detail,"");
        return "";
    }
    public static String crackVideoUrl(CrawlerUtil crawlerUtil,String targetUrl,String proxyIp, int proxyPort) throws IOException, CloneNotSupportedException {
        HttpResponse response_detail=crawlerUtil.proxyPostUrl(targetUrl, proxyIp, proxyPort, getPostParmList());
//        HttpResponse response_detail=crawlerUtil.proxyGetUrl(targetUrl, proxyIp, proxyPort);
        return parseVideoUrl(crawlerUtil,response_detail,proxyIp,proxyPort);
    }

    /***
     * 视频列表页破解获取视频列表信息
     * @param crawlerUtil 实例化的破解工具
     * @param targetUrl 目标url
     * @return
     */
    public static  List<VideoBean> crackVideoList(CrawlerUtil crawlerUtil,String targetUrl){
        List<VideoBean> list=new ArrayList<VideoBean>();
        HttpResponse response=crawlerUtil.post(targetUrl, crawlerUtil.produceEntity(getPostParmList()));
        Document doc=crawlerUtil.getDocUTF8(response);
        Elements videobox=doc.select("div[class*=listchannel]");
        System.out.println(targetUrl+"视频总数:"+videobox.size()+"个");
        /**拿去视频预览图片*/
        for(Element e:videobox){
            /**标题*/
            String title=e.select("div[class*=imagechannel]>a>img").attr("title");
            /**获得预览图片链接*/
            String preImgSrc=e.select("div[class*=imagechannel]>a>img").attr("src");
            /**视频链接地址*/
            String videoUrl=e.select("div[class*=imagechannel]>a").attr("abs:href");
            /**获得时长*/
            String infotime=e.text().substring(e.text().indexOf("时长:"),e.text().indexOf(" 添加时间"));
            /**获得添加时间*/
            String updatetime=e.text().substring(e.text().indexOf("添加时间"),e.text().indexOf("作者:"));
            /**作者信息*/
            String author=e.text().substring(e.text().indexOf("作者:"),e.text().indexOf("查看:"));
            System.out.println(title + " " + preImgSrc + " " + videoUrl + " " + infotime + " " +updatetime+" "+author);
            VideoBean videoBean=new VideoBean(title,preImgSrc,videoUrl,infotime,updatetime,author);
            list.add(videoBean);
        }
        return list;
    }



    /**
     * 拿去post参数，获得中文返回页面
     * */
    public static List<NameValuePair> getPostParmList(){
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("session_language", "cn_CN"));
        return list;
    }
    public static String urlDecode(String url) throws UnsupportedEncodingException {
        return URLDecoder.decode(url,"utf-8");
    }
}
