package com.br.dong.httpclientTest.weibonvshen_2015_09_03;

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
 * Date: 2015-09-08
 * Time: 10:24
 * 采集http://lunvshen.com/?sort=new&p=1 的女神照片
 */
public class WeiBoNvShenTask {
    private static CrawlerUtil crawlerUtil=new CrawlerUtil();
    private static String testUrl="http://lunvshen.com/?sort=new&p=";
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, IOException, CloneNotSupportedException {
        crawlerUtil.clientCreate("http","lunvshen.com","testUrl"+1);
        HttpResponse response= crawlerUtil.noProxyGetUrl(testUrl + 1);

    }
}
