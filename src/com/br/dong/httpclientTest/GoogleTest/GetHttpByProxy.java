package com.br.dong.httpclientTest.GoogleTest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.util.EntityUtils;
/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-4-27
 * Time: 17:13
 */
public class GetHttpByProxy {
    public static void main(String[] args) throws ClientProtocolException,
            IOException {
        //实例化一个HttpClient
        HttpClient httpClient = new DefaultHttpClient();
        //设定目标站点
        HttpHost httpHost = new HttpHost("http://91.v4p.co/view_video.php?viewkey=55f767c19a4c6f588681");
        //设置代理对象 ip/代理名称,端口
        HttpHost proxy = new HttpHost("125.39.66.67", 80);
        //对HttpClient对象设置代理
        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
                proxy);
        HttpGet httpGet = new HttpGet("/");
        //这里也可以直接使用httpGet的绝对地址，当然如果不是具体地址不要忘记/结尾
        //HttpGet httpGet = new HttpGet("http://www.shanhe114.com/");
        //HttpResponse response = httpClient.execute(httpGet);

        HttpResponse response = httpClient.execute(httpHost, httpGet);
        if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
            //请求成功
            //取得请求内容
            HttpEntity entity = response.getEntity();
            //显示内容
            if (entity != null) {
                // 显示结果
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity
                        .getContent(), "UTF-8"));
                String line = null;
                StringBuffer strBuf = new StringBuffer((int) entity.getContentLength());
                while ((line = reader.readLine()) != null) {
                    strBuf.append(line);
                }
                strBuf.trimToSize();
                System.out.println(strBuf.toString());
            }
            if (entity != null) {
                entity.consumeContent();
            }
        }else{
            System.out.println("aaaaa");
        }
    }
}
