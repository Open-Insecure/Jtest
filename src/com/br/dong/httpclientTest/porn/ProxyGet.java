package com.br.dong.httpclientTest.porn;

import java.io.IOException;
import java.net.SocketException;
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
    //--
    private static String xiciUrl="http://www.xici.net.co/nn/";//需要加上页数
    private static String xiciGuowai="http://www.xici.net.co/wn/";
    //--
    private static String kuaiinhaUrl="http://www.kuaidaili.com/free/inha/";
    private static String kuaiintrUrl="http://www.kuaidaili.com/free/intr/";
    private static String kuaiouthaUrl="http://www.kuaidaili.com/free/outha/";
    private static String kuaiouttrUrl="http://www.kuaidaili.com/free/outtr/";

    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException, CloneNotSupportedException {
        //代理采集
        getXiciProxys();
        getKuaiProxys();
        //测试代理
    }

    public static void getXiciProxys(){
        System.out.println("get proxys from: http://www.xici.net.co/");
        //清空代理表
        JdbcUtil.deleteAll();
        //国内代理拿10页
        for(int i=1;i<=30;i++){
            ProxyThread thread=new ProxyThread(xiciUrl+""+i,"www.xici.net.co","http://www.xici.net.co/");
            thread.start();
        }
        //国外代理拿十页
        for(int i=1;i<=30;i++){
            ProxyThread thread=new ProxyThread(xiciGuowai+""+i,"www.kuaidaili.com","http://www.kuaidaili.com/");
            thread.start();
        }
    }
    public static void getKuaiProxys(){
        System.out.println("get proxys from: http://www.kuaidaili.com/");
        for(int i=1;i<=30;i++){
            ProxyThreadForkuai thread=new ProxyThreadForkuai(kuaiinhaUrl+""+i);
            thread.start();
        }
        //国外代理拿十页
        for(int i=1;i<=30;i++){
            ProxyThreadForkuai thread=new ProxyThreadForkuai(kuaiintrUrl+""+i);
            thread.start();
        }
        for(int i=1;i<=30;i++){
            ProxyThreadForkuai thread=new ProxyThreadForkuai(kuaiouthaUrl+""+i);
            thread.start();
        }
        for(int i=1;i<=30;i++){
            ProxyThreadForkuai thread=new ProxyThreadForkuai(kuaiouttrUrl+""+i);
            thread.start();
        }
    }

    }

class ProxyThreadForkuai extends Thread{
    private static CrawlerUtil client=new CrawlerUtil();
    String wanturl;
    ProxyThreadForkuai(String wanturl) {
        this.wanturl = wanturl;
    }
    public void run(){
        System.out.println("get proxy from"+wanturl);
        try {
            client.clientCreate("http","www.kuaidaili.com","http://www.kuaidaili.com/");
            Document doc=client.getDocUTF8(client.noProxyGetUrl(wanturl));
                    System.out.println(doc.toString());
            List<ProxyBean> list=new ArrayList<ProxyBean>() ;
            Elements iplist=doc.select("div[id=list]>table>tbody>tr");
            for(Element e:iplist){
                String ip=e.select("td:eq(0)").text();
                String  tport=e.select("td:eq(1)").text().trim();
                String type=e.select("td:eq(3)").text();
                System.out.println(ip+tport+type);
                //过滤出http的代理 插入数据库
                if("http".equals(type.toLowerCase())){
                    int port=Integer.parseInt(tport);
                    ProxyBean proxy=new ProxyBean(ip,port,type, DateUtil.getCurrentDay());
                    System.out.println(proxy.toString());
                    list.add(proxy);
                }

                //插入数据库
                JdbcUtil.insertBatch(list);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (KeyManagementException e) {
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

/**
 * 下载线程类
 * */
class ProxyThread extends Thread{

    private static CrawlerUtil client=new CrawlerUtil();
    String wanturl;
    String host;
    String refurl;

    ProxyThread(String wanturl,String host,String refurl) {
        this.wanturl = wanturl;
        this.host=host;
        this.refurl=refurl;
    }

    @Override
    public void run(){
        System.out.println("get proxy from"+wanturl);
        try {
            client.clientCreate("http",host,refurl);

            List<ProxyBean> list=new ArrayList<ProxyBean>() ;
            Document doc=client.getDocUTF8(client.noProxyGetUrl(wanturl));
                    System.out.println(doc.toString());
            Elements iplist=doc.select("table[id*=ip_list]>tbody>tr");
            //暂时先拿去10页的
            for(Element e:iplist){
                String ip=e.select("td:eq(1)").text();
                String  tport=e.select("td:eq(2)").text().trim();
                String type=e.select("td:eq(5)").text();
                System.out.println(ip+tport+type);
                //过滤出http的代理 插入数据库
                if("http".equals(type.toLowerCase())){
                    int port=Integer.parseInt(tport);
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


