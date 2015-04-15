package com.br.dong.httpclientTest.porn.new_pron_2014_12_21;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.JdbcUtil;
import com.br.dong.httpclientTest.porn.ProxyBean;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-12-29
 * Time: 下午4:14
 * To change this template use File | Settings | File Templates.
 * 探测列表中可用的代理。如何判断可用，当返回结果是HTTP/1.1 200 OK
 */
public class ProxyCheckTask extends Thread{
    private static CrawlerUtil crawlerUtil=new CrawlerUtil();
    private static String testUrl="http://198.105.209.230/media/player/cpconfig.php?vkey=51cbc9a0f9caeabd6f05";
    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            ProxyCheckTask task=new ProxyCheckTask("name["+i+"]");
            task.start();
        }
    }

    public ProxyCheckTask(String name) {
        super(name);
    }

    public void run(){
        while(true){
            ProxyBean proxy= JdbcUtil.getProxy();
            if(proxy!=null){
                System.out.println(this.getName()+"start connet to proxy:["+proxy.toString()+"]");
//                crawlerUtil.clientByProxyCreate("http",proxy.getIp(),"http://" + proxy.getIp());
                try {
                    crawlerUtil.clientCreate("http","198.105.209.230",testUrl);
                } catch (KeyManagementException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                try {
                    HttpResponse response=crawlerUtil.proxyGetUrl(testUrl,proxy.getIp(),proxy.getPort()) ;
                    if(response!=null){
                        int statusCode=response.getStatusLine().getStatusCode();
                        System.out.println(this.getName()+"status code:["+statusCode+"]");
                        if(statusCode==200){
                            System.out.println(this.getName()+"save proxy:["+proxy.toString()+"]");
                        } else{
                            System.out.println(this.getName()+"delete proxy:["+proxy.toString()+"]");
                        }
                    } else{
                        System.out.println(this.getName()+"delete proxy:["+proxy.toString()+"]");
                    }

                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }


    }

}
