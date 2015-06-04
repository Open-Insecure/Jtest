package com.br.dong.httpclientTest.proxy_2015_03_14;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.JdbcUtil;
import com.br.dong.httpclientTest.porn.ProxyBean;
import com.br.dong.utils.DateUtil;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
 * Date: 15-3-14
 * Time: 下午2:26
 * To change this template use File | Settings | File Templates.
 * 采集http://www.proxy.com.ru/list_1.html的代理信息
 * 采集的过程中就验证代理是否可用
 * 分别可以验证91porn与caoporn的
 */
public class ProxyGet2015_03_14 {
    //--xici代理网站
    private static String xiciUrl="http://www.xici.net.co/nn/";//需要加上页数
    private static String xiciGuowai="http://www.xici.net.co/wn/";
    //--kuai代理网站
    private static String kuaiinhaUrl="http://www.kuaidaili.com/free/inha/";
    private static String kuaiintrUrl="http://www.kuaidaili.com/free/intr/";
    private static String kuaiouthaUrl="http://www.kuaidaili.com/free/outha/";
    private static String kuaiouttrUrl="http://www.kuaidaili.com/free/outtr/";

    private static ExecutorService threadPool= Executors.newCachedThreadPool();
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, IOException, CloneNotSupportedException {
        JdbcUtil.deleteAll();
         ProxyThreadForproxyCom proxyThreadForproxyCom=new ProxyThreadForproxyCom("proxy.com.ru","http://www.proxy.com.ru/list_1.html","gb2312", new Clientlistener() {
             @Override
             public void onCreate(CrawlerUtil client) {
                 try {
                     client.clientCreate("http","www.proxy.com.ru","http://www.proxy.com.ru/list_1.html");
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
             @Override
             public void onError(Exception e) {
                  e.printStackTrace();
             }
         });
        proxyThreadForproxyCom.start();

//        ProxyXiciThread proxyXiciThread=new ProxyXiciThread("xici线程", "http://www.xici.net.co/nn/1", "utf-8", new Clientlistener() {
//            @Override
//            public void onCreate(CrawlerUtil client) {
//                try {
//                    client.clientCreate("http","www.xici.net.co","http://www.xici.net.co/");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Exception e) {
//                e.printStackTrace();
//            }
//        });
//        proxyXiciThread.start();


//        ProxyThreadForkuai proxyThreadForkuai=new ProxyThreadForkuai("kuai.com线程", "http://www.kuaidaili.com/free/inha/1", "utf-8", new Clientlistener() {
//            @Override
//            public void onCreate(CrawlerUtil client) {
//                try {
//                    client.clientCreate("http","www.kuaidaili.com","http://www.kuaidaili.com/");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Exception e) {
//                  e.printStackTrace();
//            }
//        });
//        proxyThreadForkuai.start();
    }

}

/**
 * client监听类
 */
 interface Clientlistener {
    void onCreate(CrawlerUtil client);
    void onError(Exception e);
}
/**
 * 线程基础类
 */
class BaseThread extends Thread{
     CrawlerUtil client=new CrawlerUtil();
     Clientlistener clientlistener;
     String wantUrl="";
     String encode="";
     List<ProxyBean> list=new ArrayList<ProxyBean>() ;
    //--caoporn检测url
    protected static String caopornCheckUrl="http://198.105.209.230/video/51cbc9a0f9caeabd6f05/岛崎-絵理子-eriko-shimazaki01";
    protected static String _91pornCheckUrl="http://91.v4p.co/view_video.php?viewkey=55f767c19a4c6f588681";


    public BaseThread(String name, String wantUrl,String encode,Clientlistener clientlistener) {
        super(name);
        //回调
        clientlistener.onCreate(this.client);
        this.wantUrl = wantUrl;
        this.encode=encode;
        this.clientlistener=clientlistener;
    }
    public void run(){
        try {
            parse(wantUrl);
        } catch (Exception e) {
            if(clientlistener!=null){
                clientlistener.onError(e);
            }
        }
    }
    public void checkResult(String result,String ip,String tport){
        if(result.contains("success")){
            System.out.println("当前代理可用:" + ip + ":" + tport + result);
            if(JdbcUtil.checkProxy(ip)==null||JdbcUtil.checkProxy(ip).size()==0){
                list.add(getAvaibleProxy(ip, tport, ""));
            } else{
                System.out.println("当前代理:" + ip + ":" + tport+"已经存在库中");
            }

        }else{
            System.out.println("当前代理不可用:"+ip+":"+tport+result);
        }
    }

    /**
     * 获得目标url的doc
     */
    public Document getDoc(String url) throws IOException, CloneNotSupportedException {
        HttpResponse response=client.noProxyGetUrl(url) ;
         if(response!=null){
           return   client.getDocument(response.getEntity(),encode);
         }
        return null;
    }

    /**
     * 获得一个可用代理的bean实例
     */
    public ProxyBean getAvaibleProxy(String ip,String tport,String type){
        int port=Integer.parseInt(tport);
        ProxyBean proxy=new ProxyBean(ip,port,type, DateUtil.getCurrentDay());
        return proxy;
    }

    /**
     * 需要被子类重写的方法
     */
    public  void parse(String url) throws Exception{
        System.out.println(this.getName() + "开始运行"+url);
    }
}


/**
 * proxy.com网站
 */
class ProxyThreadForproxyCom extends BaseThread{

    public ProxyThreadForproxyCom(String name, String wantUrl,String encode ,Clientlistener clientlistener) {
        super(name, wantUrl, encode, clientlistener);
    }
    /**
     * 只重写parse
     */
    public void parse(String url) throws Exception{
        super.parse(url);
        Document doc=super.getDoc(url);
        if(doc!=null){
            Elements elements=doc.select("font[size=2]:eq(0)").select("table>tbody>tr");
            for(int i=1;i<elements.size();i++){
                Element e=elements.get(i);
                String ip=e.select("td:eq(1)").text();
                String tport=e.select("td:eq(2)").text().trim();
                String type=e.select("td:eq(3)").text();
//                String result= PornCheck.testProxyForCaoporn(ip, Integer.parseInt(tport),  caopornCheckUrl);//检测当前代理对于caopron是否可用
//                String result= PornCheck.testProxyFor91porn(ip, Integer.parseInt(tport), _91pornCheckUrl);//检测当前代理对于caopron是否可用
//                super.checkResult(result,ip,tport);
                super.checkResult("success",ip,tport);
            }
            //插入数据库
            JdbcUtil.insertBatch(list);
        }

    }
}

/**
 * http://www.kuaidaili.com
 */
class ProxyThreadForkuai extends BaseThread{

    public ProxyThreadForkuai(String name, String wantUrl, String encode, Clientlistener clientlistener) {
        super(name, wantUrl, encode, clientlistener);
    }
    public void parse(String url) throws Exception{
        super.parse(url);
        Document doc=super.getDoc(url);
        if(doc!=null){
            Elements iplist=doc.select("div[id=list]>table>tbody>tr");
            for(Element e:iplist){
                String ip=e.select("td:eq(0)").text();
                String  tport=e.select("td:eq(1)").text().trim();
                String type=e.select("td:eq(3)").text();
                if(!tport.equals("")){
//                    过滤出http的代理 插入数据库
                    String result= PornCheck.testProxyForCaoporn(ip, Integer.parseInt(tport), caopornCheckUrl);//检测当前代理对于caopron是否可用
                    super.checkResult(result,ip,tport);
                }
            }
            //插入数据库
            JdbcUtil.insertBatch(list);
        }

    }
}

/**
 * http://www.xici.net.co
 * */
class ProxyXiciThread extends BaseThread{

    public ProxyXiciThread(String name, String wantUrl, String encode, Clientlistener clientlistener) {
        super(name, wantUrl, encode, clientlistener);
    }
    public void parse(String url) throws Exception{
        super.parse(url);
        Document doc=super.getDoc(url);
        if(doc!=null){
            Elements iplist = doc.select("table[id*=ip_list]>tbody>tr");
            for(Element e:iplist){
                String ip=e.select("td:eq(2)").text();
                String  tport=e.select("td:eq(3)").text().trim();
                String type=e.select("td:eq(5)").text();
                if(!tport.equals("")){
//                    过滤出http的代理 插入数据库
                    String result= PornCheck.testProxyForCaoporn(ip, Integer.parseInt(tport), caopornCheckUrl);//检测当前代理对于caopron是否可用
                    super.checkResult(result,ip,tport);
                }
            }
            //插入数据库
            JdbcUtil.insertBatch(list);
        }

    }

}
