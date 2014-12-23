package com.br.dong.httpclientTest.porn.new_pron_2014_12_21;

import com.br.dong.file.FileOperate;
import com.br.dong.httpclientTest.CrawlerUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-12-23
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 * 针对http://www.caoporn.com/videos?page=的简单的采集程序
 */
public class CaoPron20141223 {
    //视频列表url page页数需要拼装
    private static String url="http://www.caoporn.com/videos?page=";
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
        getPaging(true);
    }
    public static void getPaging(Boolean wantMaxPage){

        try {
            client.clientCreate("http","91p.vido.ws",url+1);
            HttpResponse response = client.noProxyGetUrl(url+1);
            Document doc= client.getDocUTF8(response);
            System.out.println(doc.toString());
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
        }

    }

}
