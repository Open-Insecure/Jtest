package com.br.dong.httpclientTest.porn;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.utils.DateUtil;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/** 
 * @author  hexd
 * 创建时间：2014-8-19 上午11:10:35 
 * 类说明 
 * 从http://www.xici.net.co/ 获得代理列表插入到数据库中
 * 暂时只拿去http类型的代理
 */
public class ProxyGet {


    private static CrawlerUtil client=new CrawlerUtil();
    private static String xiciUrl="http://www.xici.net.co/nn/";//需要加上页数
    private static String xiciGuowai="http://www.xici.net.co/wn/";

    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException, CloneNotSupportedException {
//         getProxys();
       ProxyBean proxy=  JdbcUtil.getProxy();
        System.out.println(proxy.toString());

    }

    public static void getProxys(){
        //清空代理表
        JdbcUtil.deleteAll();
        //国内代理拿10页
        for(int i=1;i<=3;i++){
            ProxyThread thread=new ProxyThread(xiciUrl+""+i);
            thread.start();
        }
        //国外代理拿十页
        for(int i=1;i<=3;i++){
            ProxyThread thread=new ProxyThread(xiciUrl+""+i);
            thread.start();
        }
    }

    }

/**
 * 下载线程类
 * */
class ProxyThread extends Thread{

    private static CrawlerUtil client=new CrawlerUtil();
    String wanturl;

    ProxyThread(String wanturl) {
        this.wanturl = wanturl;
    }

    @Override
    public void run(){
        System.out.println("一个线程启动"+wanturl);
        try {
            client.clientCreatNoUrl("http");
            List<ProxyBean> list=new ArrayList<ProxyBean>() ;
            Document doc=client.getDocUTF8(client.noProxyGetUrl(wanturl));
            //        System.out.println(doc.toString());
            Elements iplist=doc.select("table[id*=ip_list]>tbody>tr");
            //暂时先拿去10页的
            for(Element e:iplist){
                String ip=e.select("td:eq(1)").text();
                String port=e.select("td:eq(2)").text();
                String type=e.select("td:eq(5)").text();
                //过滤出http的代理 插入数据库
                if("http".equals(type.toLowerCase())){
                    ProxyBean proxy=new ProxyBean(ip,port,type, DateUtil.getCurrentDay());
                    System.out.println(proxy.toString());
                    list.add(proxy);
                }
            }
            //插入数据库
            JdbcUtil.insertBatch(list);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (KeyManagementException e) {
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


