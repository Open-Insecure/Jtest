package com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21
 * AUTHOR: hexOr
 * DATE :2016-08-01 20:07
 * DESCRIPTION:
 */
public class ProxyTest_08 {
    public static void main(String args[]) throws Exception {
        String proxys= "223.244.34.13:8998\n" +
                "120.236.148.199:2226\n" +
                "125.127.178.170:8998\n" +
                "125.135.179.153:8080\n" +
                "27.39.225.89:8080\n" +
                "49.72.71.31:8998\n" +
                "119.51.243.57:8118\n" +
                "114.138.184.206:8998\n" +
                "58.253.71.197:808\n" +
                "120.32.115.133:80\n" +
                "219.141.225.108:80\n" +
                "121.14.36.38:8080\n" +
                "182.90.252.10:2226\n" +
                "221.199.203.106:3128\n" +
                "49.73.170.162:808\n" +
                "124.113.23.22:8118\n" +
                "114.228.10.166:8118\n" +
                "183.239.225.44:8888\n" +
                "183.91.33.42:8086\n" +
                "113.73.102.36:8118\n" +
                "116.25.80.164:8118\n" +
                "36.7.237.62:8998\n" +
                "1.71.160.128:8118\n" +
                "124.135.52.125:8118\n" +
                "202.100.5.42:8118\n" +
                "218.7.206.249:8998\n" +
                "112.93.100.222:8998\n" +
                "123.7.177.20:9999\n" +
                "211.138.156.209:80\n" +
                "112.16.21.18:2226\n" +
                "110.244.8.188:8998\n" +
                "60.190.99.173:443\n" +
                "124.132.98.114:8998\n" +
                "58.52.201.119:8080\n" +
                "101.231.250.102:80\n" +
                "123.124.168.149:80\n" +
                "111.121.174.149:8998\n" +
                "14.222.115.124:8888\n" +
                "211.143.45.216:3128\n" +
                "180.116.241.83:8888\n" +
                "60.167.21.167:8118\n" +
                "218.60.29.219:8080\n" +
                "121.204.212.142:8888\n" +
                "58.248.200.194:80\n" +
                "218.26.237.18:3128\n" +
                "123.165.175.137:8998\n" +
                "122.193.14.106:82\n" +
                "183.196.9.132:2226\n" +
                "125.65.112.201:8008\n" +
                "183.91.33.41:86\n" +
                "125.111.171.158:8118\n" +
                "122.13.214.134:8080\n" +
                "110.85.115.146:8118\n" +
                "1.71.128.85:80\n" +
                "111.123.90.95:8998\n" +
                "223.215.211.239:8998\n" +
                "125.113.2.21:8118\n" +
                "202.111.9.106:23\n" +
                "182.105.29.71:8118\n" +
                "183.131.76.27:8888\n" +
                "113.135.178.15:8998\n" +
                "123.165.160.47:8998\n" +
                "114.238.85.76:8998\n" +
                "113.248.165.23:8998\n" +
                "117.40.35.233:8998\n" +
                "183.91.33.43:90\n" +
                "61.178.63.90:63000\n" +
                "119.188.94.145:80\n" +
                "111.1.3.36:8000\n" +
                "61.178.139.8:8998\n" +
                "114.244.215.46:8118\n" +
                "111.35.143.237:8998\n" +
                "60.13.226.254:2226\n" +
                "223.95.113.199:80\n" +
                "113.120.112.142:8118\n" +
                "58.214.5.229:80\n" +
                "183.128.168.161:8998\n" +
                "36.47.178.125:8118\n" +
                "36.47.211.147:8998\n" +
                "222.220.34.74:1337\n" +
                "61.178.238.122:63000\n" +
                "121.224.89.44:8998\n" +
                "111.13.7.42:81\n" +
                "60.13.74.143:80\n" +
                "180.104.204.229:8998\n" +
                "218.164.170.248:8998\n" +
                "60.161.137.168:63000\n" +
                "121.9.128.198:8998\n" +
                "117.36.197.152:3128\n" +
                "180.213.69.64:8998\n" +
                "58.250.177.14:8118\n" +
                "180.162.214.218:8998\n" +
                "60.186.23.49:8118\n" +
                "115.53.157.114:8998\n" +
                "117.135.250.88:83\n" +
                "122.230.5.195:8998\n" +
                "60.184.131.208:8998\n" +
                "27.190.226.184:8998\n" +
                "183.91.33.44:81\n" +
                "180.125.36.60:8998";
        String [] temp=proxys.split("\n");
        for(int i=0;i<temp.length;i++){
            String [] t=temp[i].split(":");
            // 创建HttpClientBuilder
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            // HttpClient
            CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
            // 依次是目标请求地址，端口号,协议类型
            HttpHost target = new HttpHost("email.91dizhi.at.gmail.com.9h3.space",
                    80,
                    "http");
            // 依次是代理地址，代理端口号，协议类型
            HttpHost proxy = new HttpHost(t[0], Integer.parseInt(t[1]), "http");
            RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

            // 请求地址
            HttpPost httpPost = new HttpPost("http://email.91dizhi.at.gmail.com.9h3.space/view_video.php?viewkey=edd51eb7488016ffe659&page=2&viewtype=basic&category=mr");
            httpPost.setConfig(config);
            // 创建参数队列
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            // 参数名为pid，值是2
            formparams.add(new BasicNameValuePair("session_language", "cn_CN"));

            UrlEncodedFormEntity entity;
            try {
                entity = new UrlEncodedFormEntity(formparams, "UTF-8");
                httpPost.setEntity(entity);
                CloseableHttpResponse response = closeableHttpClient.execute(
                        target, httpPost);
                // getEntity()
                HttpEntity httpEntity = response.getEntity();
                if (httpEntity != null) {
                    // 打印响应内容
                    System.out.println("response:"
                            + EntityUtils.toString(httpEntity, "UTF-8"));
                }
                // 释放资源
                closeableHttpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
