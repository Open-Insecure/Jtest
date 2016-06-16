package com.br.dong.httpclientTest.asiaxxxporn_2015_06_08;

import com.br.dong.httpclientTest.CrawlerUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015/6/11
 * Time: 16:21
 * http://www.asiaxxxporn.com
 */
public class MyTest {

    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException, CloneNotSupportedException {
//        CrawlerUtil crawlerUtil=new CrawlerUtil();
//        crawlerUtil.clientCreate("http", "www.asiaxxxporn.com","http://www.asiaxxxporn.com");
//        HttpResponse response=crawlerUtil.noProxyGetUrl("http://www.asiaxxxporn.com");
//        System.out.println(crawlerUtil.getDocUTF8(response).toString());

        CrawlerUtil crawlerUtil=new CrawlerUtil();
        crawlerUtil.clientCreatNoUrl("http");
        HttpResponse response=crawlerUtil.noProxyGetUrl("http://168.235.76.56/video?page=1");
        System.out.println(crawlerUtil.getDocUTF8(response).body().toString().replace("&quot;","\"").replace("<body>","").replace("</body>",""));

    }
}
