package com.br.dong.httpclientTest;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpStatus;

import org.apache.http.HttpEntity;

import org.apache.http.HttpHost;

import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.util.EntityUtils;

class HttpClientTest {

    public final static void main(String[] args) throws Exception {

        // 初始化，此处构造函数就与3.1中不同

        HttpClient httpclient = new DefaultHttpClient();

        HttpHost targetHost = new HttpHost("http://10.1.12.52:8001/interface?func=013001&catName=S0NTeGiEvFO+eQdgmEL3j5a4KITbfTegqp2M9Ef4/Yc=");

        //HttpGet httpget = new HttpGet("http://www.apache.org/");

        HttpGet httpget = new HttpGet("/");

        // 查看默认request头部信息

        System.out.println("Accept-Charset:" + httpget.getFirstHeader("Accept-Charset"));

        // 以下这条如果不加会发现无论你设置Accept-Charset为gbk还是utf-8，他都会默认返回gb2312（本例针对google.cn来说）

        httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");

        // 用逗号分隔显示可以同时接受多种编码

        httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");

        httpget.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");

        // 验证头部信息设置生效

        System.out.println("Accept-Charset:" + httpget.getFirstHeader("Accept-Charset").getValue());

        // execute HTTP request

        System.out.println("executing request " + httpget.getURI());

        HttpResponse response = httpclient.execute(targetHost, httpget);

        //HttpResponse response = httpclient.execute(httpget);

        System.out.println("----------------------------------------");

        System.out.println("Location: " + response.getLastHeader("Location"));

        System.out.println(response.getStatusLine().getStatusCode());

        System.out.println(response.getLastHeader("Content-Type"));

        System.out.println(response.getLastHeader("Content-Length"));

        

        System.out.println("----------------------------------------");

        // 判断页面返回状态判断是否进行转向抓取新链接

        int statusCode = response.getStatusLine().getStatusCode();

        if ((statusCode == HttpStatus.SC_MOVED_PERMANENTLY) ||

                (statusCode == HttpStatus.SC_MOVED_TEMPORARILY) ||

                (statusCode == HttpStatus.SC_SEE_OTHER) ||

                (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {

            // 此处重定向处理  此处还未验证

            String newUri = response.getLastHeader("Location").getValue();

            httpclient = new DefaultHttpClient();

            httpget = new HttpGet(newUri);

            response = httpclient.execute(httpget);

        }

        // Get hold of the response entity

        HttpEntity entity = response.getEntity();

        

        // 查看所有返回头部信息

        Header headers[] = response.getAllHeaders();

        int ii = 0;

        while (ii < headers.length) {

            System.out.println(headers[ii].getName() + ": " + headers[ii].getValue());

            ++ii;

        }

        

        // If the response does not enclose an entity, there is no need

        // to bother about connection release

        if (entity != null) {

            // 将源码流保存在一个byte数组当中，因为可能需要两次用到该流，

            byte[] bytes = EntityUtils.toByteArray(entity);

            String charSet = "";

            

            // 如果头部Content-Type中包含了编码信息，那么我们可以直接在此处获取

            charSet = EntityUtils.getContentCharSet(entity);

            System.out.println("In header: " + charSet);



            System.out.println("Last get: " + charSet);

            // 至此，我们可以将原byte数组按照正常编码专成字符串输出（如果找到了编码的话）

            System.out.println("Encoding string is: " + new String(bytes, charSet));

        }

        httpclient.getConnectionManager().shutdown();        

    }

}