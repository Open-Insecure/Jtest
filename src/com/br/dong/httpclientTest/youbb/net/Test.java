package com.br.dong.httpclientTest.youbb.net;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpHead;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-06-12
 * Time: 14:45
 * youbb站点的最爱视频采集测试
 * http://youbbb.net/videos?c=1&o=tf&page=1  最爱
 * http://youbbb.net/videos?c=1&o=mr &page=1最新
 */
public class Test {

    private static String absUrl="http://youbbb.net";
    private static String favUrlPre="http://youbbb.net/videos?c=1&o=tf&page=";//最爱前缀
    private static String latestUrlPre="http://youbbb.net/videos?c=1&o=mr&page=";//最新前缀
    private static String getDownLoadUrlTargetUrl="http://youbbb.net/media/player/config.php?vkey=9549";//获得视频下载地址的请求目标url
    private static int DEFAULT_PAGE=10;//默认采集page最大为10
    private static Logger logger = Logger.getLogger(Test.class);
    private static CrawlerUtil crawlerUtil=new CrawlerUtil();
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, IOException, CloneNotSupportedException {
        String sectionUrl=latestUrlPre + 1;
        crawlerUtil.clientCreate("http", "youbbb.net", sectionUrl);
       try{
           int count=getTotalPagerNumber(sectionUrl);
           for(int i=1;i<=count;i++){

           }
           parsePage(latestUrlPre+1);
       }catch (Exception e){
            e.printStackTrace();
       }

        System.out.println(readDownloadFileInfo("http://vip.youb77.com:81/media/you22/flv/9263.flv"));//返回文件大小
    }

    /**
     * 分析页面
     * @param targetUrl
     */
    public static void parsePage(String targetUrl) throws IOException, CloneNotSupportedException {
        logger.info("正在采集页面:"+targetUrl);
        HttpResponse response=crawlerUtil.noProxyGetUrl(targetUrl);
        if (response==null) return;
        Document document=crawlerUtil.getDocUTF8(response);
        Elements elements=document.select("div[class=video_box]");
        for(Element element:elements){
//            System.out.println(element.toString());
            String title=absUrl+element.select("a").attr("href");//获得标题
            String imgUlr=absUrl+element.select("img").attr("src");//获得视频预览图片url地址
            String videoTime=element.select("div[class=box_left]").text();//视频时长
            //获得视频的id
            System.out.println(title+imgUlr+videoTime);
        }


    }

    /**
     * 返回远程下载文件的文件信息
     * 创建header进行头访问
     * http://vip.youb77.com:81/media/you22/flv/9263.flv
     * @param targetUrl
     * @return 返回两位小数的String类型
     */
    private static String readDownloadFileInfo(String targetUrl) throws IOException, CloneNotSupportedException {
        HttpHead httpHead = new HttpHead(targetUrl);//创一个访问头
        HttpResponse response = crawlerUtil.executeHead(httpHead);//返回头访问信息
        int statusCode = response==null?-1:response.getStatusLine().getStatusCode(); // 获取HTTP状态码
        if (statusCode != 200) {//响应码不正常的时候
            logger.info("资源不存在：" + targetUrl);
            return "";
        }
        // Content-Length
        Header[] headers = response.getHeaders("Content-Length");
        double contentLength=0;
        if (headers.length > 0)
        {	//获得要下载的文件的大小
            contentLength = Long.valueOf(headers[0].getValue());
        }
        httpHead.abort();//释放
        return String.format("%.2f",contentLength/(1024 * 1024));
    }

    /**
     *传入url，获得当前板块的页数
     * @return
     */
    private static int getTotalPagerNumber(String targetUrl) throws Exception {
        HttpResponse response=crawlerUtil.noProxyGetUrl(targetUrl);
        int responseCode=response==null?-1:response.getStatusLine().getStatusCode();//获得响应码
        if(responseCode==200){//如果响应码正常的话
            Document doc=crawlerUtil.getDocUTF8(response);
            Elements lis=doc.select("div[class=pagination]>ul>li");
            int lisNum=lis.size()==0? -1:lis.size();
            if(lisNum==-1) return DEFAULT_PAGE;//如果当前没有页数的话。返回一个默认的页数 即采集最大到10页
            int maxPage=Integer.parseInt(lis.select(":eq("+(lisNum-2)+")").text());//获得页面上最大的显示页数
//            logger.info(lis.select(":eq(5)").toString());
            logger.info("当前版块:"+targetUrl);
            logger.info("总页数:"+maxPage);
            return maxPage;
        }
        return DEFAULT_PAGE;
    }

}
