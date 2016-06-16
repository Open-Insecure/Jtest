package com.br.dong.httpclientTest.porn.reg_91_porn_2016_04_15;

import com.br.dong.httpclientTest.CrawlerUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2016-04-15
 * Time: 15:31
 * 测试注册91porn
 */
public class RegPornTest {
    private static CrawlerUtil client=new CrawlerUtil();
    private static String signUpUrl="http://91porn.com/signup.php?next=";
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, IOException, CloneNotSupportedException {
        client.clientCreate("http", "91porn.com", "http://91porn.com/signup.php");
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("username","791675429@qq.com"));
        list.add(new BasicNameValuePair("vip",""));
        list.add(new BasicNameValuePair("password1","he95b004"));
        list.add(new BasicNameValuePair("password2","he95b004"));
        list.add(new BasicNameValuePair("fingerprint","1564171464"));
        list.add(new BasicNameValuePair("captcha_input","pczivq"));
        list.add(new BasicNameValuePair("action_signup","Sign Up"));
        list.add(new BasicNameValuePair("submit.x","44"));
        list.add(new BasicNameValuePair("submit.y","16"));
        HttpResponse response=client.noProxyPostUrl(signUpUrl,list);
        Document document=client.getDocUTF8(response);
        System.out.println(document.toString());
    }

}
