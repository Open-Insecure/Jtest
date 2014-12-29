package com.br.dong.httpclientTest.porn.new_pron_2014_12_21;

import com.br.dong.file.FileOperate;
import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.JdbcUtil;
import com.br.dong.httpclientTest.porn.VedioBean;
import com.br.dong.utils.DateUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-12-23
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 * 针对http://www.caoporn.com/videos?page=的简单的采集程序
 * http://198.105.209.230/videos?page=1
 */
public class CaoPron20141223 {
    //视频列表url page页数需要拼装
    private static String url="http://198.105.209.230/videos?page=";

    //caopron的index地址
    private static String indexUrl="http://198.105.209.230";
    //默认只采集20分钟长度以下的
    private static int min_20=2000;
    //视频文件请求url 后跟参数需要拼装
    //默认查找页数
    private static int defaultPage=1000;
    //多少条进行一次批量插入
    private static int batchNum=100;
    private static FileOperate fo=new FileOperate();
    //当前页面视频采集完成标志  pageGetFlag=20表示此页的20部视频全部下完
    public static int pageGetFlag=0;
    //当前要采集的第几页的页数
    public static int current=1;
    //第一次循环标志
    public static boolean first=true;
    //
    public static CrawlerUtil client=new CrawlerUtil();
    //--下载程序使用的参数
    //线程池
    static ExecutorService threadPool= Executors.newFixedThreadPool(20);

    public static void main(String[] args) {
        getPagingCaoPron(true);
//        int l=1340;
//        System.out.println(100*0+1+".."+100*1);
//        System.out.println(100*1+1+".."+100*2);

    }

    /**
     * 采集caopron
     * @param wantMaxPage
     */
    public static void getPagingCaoPron(Boolean wantMaxPage){
        int maxpage=defaultPage;
        try {
            client.clientCreate("http","198.105.209.230",url+1);
            HttpResponse response = client.noProxyGetUrl(url+1);
            Document doc= client.getDocUTF8(response);
//            System.out.println(doc.toString());
            if(wantMaxPage){
                //拿去最大视频页数
                Element maxpageElement=doc.select("div[class*=pagination]>ul>li").select("a").get(4);
//                System.out.println("...."+maxpageElement.toString());
                maxpage=Integer.parseInt(maxpageElement.text());
            }
        } catch (KeyManagementException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SocketException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClientProtocolException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch(NumberFormatException e){
            System.out.println("fail to get max page,auto set max page=" + defaultPage);
        } catch (NullPointerException e){
            System.out.println("null ponit exception..");
        }
        System.out.println("default max page:"+maxpage);
        //进行视频列表采集
        try{
            collectCaoPron(1,2);
        } catch (Exception e){

        }

    }

    /**
     * 采集视频列表
     * @param startPage
     * @param endPage
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public static void  collectCaoPron(int startPage,int endPage) throws NoSuchAlgorithmException, KeyManagementException, IOException, CloneNotSupportedException {
        List<VedioBean> list=new ArrayList<VedioBean>();
        client.clientCreate("http","198.105.209.230",url+1);
         //循环采集1到最大页数
         for(int i=startPage;i<=endPage;i++){
             String targetUrl=url+i;
            HttpResponse response = client.noProxyGetUrl(targetUrl);
             if(response!=null){
                 Document doc= client.getDocUTF8(response);
//                 System.out.println(doc.toString());
                 Elements videobox=doc.select("div[class=video_box]");
                 for(Element element:videobox){
                     String linkUrl=element.select("a").attr("href");
                     String infotime=element.select("div[class=box_left]").text().replace("<br />","").trim();
                     //由于20分钟以上的不给看，所以只采集20分钟以下的 20分钟格式20:00转换成int型比较
                     int int_infotime=Integer.parseInt(infotime.replace(":",""));
                     if(int_infotime<min_20){
                         String t[]=linkUrl.split("/");
                         String vkey=t[2];
                         String title=t[3];
                         String updatetime= DateUtil.getStrOfDateMinute();
                         String preImgUrl=element.select("img").attr("src");
                        //小于20分钟长度的视频 的才采集入库
//                         System.out.println(linkUrl+vkey+title+infotime+indexUrl+preImgUrl+updatetime+".."+int_infotime);
                         VedioBean bean=new VedioBean(title,indexUrl+preImgUrl,indexUrl+linkUrl,"时长:"+infotime,updatetime,vkey,"caopron");
                         System.out.println(bean.toString());
                         list.add(bean);
                     }
                 }
             }
         }
        //循环结束进行插入
        System.out.println("list长度"+list.size());
        JdbcUtil.insertVedioBatch(list);
    }

}
