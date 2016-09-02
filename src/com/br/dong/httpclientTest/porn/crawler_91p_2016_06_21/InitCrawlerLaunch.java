package com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.ProxyBean;
import com.br.dong.utils.PropertiesUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21
 * AUTHOR: hexOr
 * DATE :2016-08-01 15:15
 * DESCRIPTION:初始化
 */
public class InitCrawlerLaunch {
    private static Logger logger = Logger.getLogger(CrawlerFor91PornTask.class);//日志
    private static PropertiesUtil propertiesUtil=PropertiesUtil.getInstance("/com/br/dong/httpclientTest/porn/crawler_91p_2016_06_21/config/config.properties");//读取配置文件
    protected static CrawlerUtil crawlerUtil = new CrawlerUtil();

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, IOException, CloneNotSupportedException {
        String targetUrl="http://email.91dizhi.at.gmail.com.9h3.space/view_video.php?viewkey=618d8ce21ac2c99337f9";
        //domainUrl=http://email.91dizhi.at.gmail.com.t8d.space/&imgUrl=http://img2.t6k.co/&VID=171233&r=&file=http://91.9p91.com/banned.flv&st=AjD10GP_Y_HsO9pJJfJHZQ&e=1470051282
        /***
             119.188.94.145:80
             110.84.128.143:3128
             122.96.59.105:80
             121.12.149.18:2226
             122.96.59.102:843
             122.96.59.106:80
             106.38.160.134:80
             117.65.204.4:8998
         */
        crawlerUtil.clientCreate("http", "email.91dizhi.at.gmail.com.9h3.space", targetUrl);
        testProxy("119.188.94.145",80,targetUrl);
    }

    public static void testProxy(String ip, int port, String targetUrl) throws IOException, CloneNotSupportedException {
        HttpResponse response=crawlerUtil.post(targetUrl, crawlerUtil.produceEntity(getPostParmList()));
//        HttpResponse response=crawlerUtil.proxyPostUrl(targetUrl,ip,port,getPostParmList());
        Document doc=crawlerUtil.getDocUTF8(response);
        System.out.println(doc.toString());
    }
    /**
     * 拿去post参数，获得中文返回页面
     * */
    public static List<NameValuePair> getPostParmList(){
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("session_language", "cn_CN"));
        return list;
    }

}
