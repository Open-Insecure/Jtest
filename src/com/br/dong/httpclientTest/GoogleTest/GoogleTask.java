package com.br.dong.httpclientTest.GoogleTest;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.JdbcUtil;
import com.br.dong.httpclientTest.porn.ProxyBean;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-3
 * Time: 下午3:28
 * To change this template use File | Settings | File Templates.
 */
public class GoogleTask extends Thread {
    private String ulr="http://173.194.72.199/";
    private CrawlerUtil client=new CrawlerUtil();
    public void run(){
        test();
    }
    public GoogleTask(){
        try {
            client.clientCreate("http","173.194.72.199","http://173.194.72.199/");
        } catch (KeyManagementException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    public void test(){
        ProxyBean proxy= JdbcUtil.getProxy();
        System.out.println("start connet to proxy:["+proxy.toString()+"]");
        //代理post访问视频页面
        HttpResponse response= null;
        try {
            response=client.proxyGetUrl(ulr, proxy.getIp(), proxy.getPort());
            if(response!=null){
                System.out.println("链接代理成功");
                Document doc=client.getDocGBK(response);
                if(doc!=null){
                    System.out.println(doc.toString());
                }  else{
                    test();
                }

            } else if(response==null){
                test();
            }
    } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void main(String[] args) {
        GoogleTask task=new GoogleTask();
        task.start();
    }
  }
