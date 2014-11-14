package com.br.dong.httpclientTest.desiretaiwan;

import com.br.dong.httpclientTest.CrawlerUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-12
 * Time: 下午1:20
 * To change this template use File | Settings | File Templates.
 */
public class DesireTaiWan {
    private static String index="http://www.desire-taiwan.com/forum.php";
    private static CrawlerUtil crawlerUtil=new CrawlerUtil();
    //http 4.5
    private static  CloseableHttpClient httpclient = HttpClients.createDefault();
    public static void main(String[] args) {
        HttpGet httpGet=new HttpGet(index);
        try {
            CloseableHttpResponse response=httpclient.execute(httpGet);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    Document doc= crawlerUtil.getDocument(entity,"UTF-8");
                    System.out.println(doc.toString());
                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
