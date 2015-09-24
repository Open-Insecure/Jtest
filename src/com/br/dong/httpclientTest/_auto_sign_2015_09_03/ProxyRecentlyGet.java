package com.br.dong.httpclientTest._auto_sign_2015_09_03;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.JdbcUtil;
import com.br.dong.httpclientTest.porn.ProxyBean;
import com.br.dong.utils.DateUtil;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-09-14
 * Time: 15:37
 * 采集当前最新的代理
 */
public class ProxyRecentlyGet {
    private static String url_xici="http://www.xicidaili.com/nn/";//西刺代理最新

    public static void main(String[] args) {
            ProxyThreadforXici thread=new ProxyThreadforXici(url_xici,"www.xicidaili.com",url_xici);
            thread.start();
    }

}
/**
 * 下载线程类
 * */
class ProxyThreadforXici extends Thread{

    private static CrawlerUtil client=new CrawlerUtil();
    String wanturl;
    String host;
    String refurl;

    ProxyThreadforXici(String wanturl,String host,String refurl) {
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
//            System.out.println(doc.toString());
            Elements iplist=doc.select("table[id*=ip_list]>tbody>tr");
            int i=0;
            //暂时先拿去10页的
            for(Element e:iplist){
                if (i>=30) break;
                String ip=e.select("td:eq(2)").text();//端口
                String tport=e.select("td:eq(3)").text();//端口
                String type=e.select("td:eq(6)").text();//类型
                String time=e.select("td:eq(9)").text();//时间
                i++;
                //过滤出http的代理 插入数据库
                if("http".equals(type.toLowerCase())){
                    int port=Integer.parseInt(tport);
                    ProxyBean proxy=new ProxyBean(ip,port,type, time);
                    System.out.println(proxy.toString());
                    list.add(proxy);
                }
            }

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