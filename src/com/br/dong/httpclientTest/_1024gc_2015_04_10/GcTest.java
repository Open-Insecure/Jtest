package com.br.dong.httpclientTest._1024gc_2015_04_10;

import com.br.dong.httpclientTest.CrawlerUtil;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;



/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-04-10
 * Time: 13:32
 * 采集
 * http://cb.1024gc.info/bbs/ 1024核工厂的资源
 *
 */
public class GcTest {
    private static CrawlerUtil client=new CrawlerUtil();
    private static  String [] targetUrls={"http://cb.1024gc.info/bbs/forum-3-1.html"};//采集的目标网址
    private static String baseUrl="http://cb.1024gc.info/bbs/";//网站的根url

    public static void main(String[] args) {
        try {
            client.clientCreate("http", "cb.1024gc.info", "http://cb.1024gc.info/bbs/forum-3-1.html");
            HttpResponse httpResponse=client.noProxyGetUrl(targetUrls[0]);

            if(httpResponse!=null){
                Document document=client.getDocUTF8(httpResponse);
//                System.out.println(document.toString());//打印带采集页面
                parseInfos(document); //解析页面模块
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * 解析出板块的信息 -标题与超链接地址
     * @param document
     */
    public static void parseInfos(Document document){
        if(document==null) return;
         Elements elements=document.select("td[class=f_title]").select("a"); //获得标题与超链接地址
        for(Element element:elements){
            if(element.text().length()>=5){//为了过滤掉分页的
                String href=baseUrl+element.attr("href");//超链接
                String name=element.text();
                System.out.println("get info:"+name+"href:"+href);
                //存入数据库
            }
        }
    }

    /**
     * 解析帖子详细页
     * @param href
     */
    public static void parseDetail(String href){

    }

}
