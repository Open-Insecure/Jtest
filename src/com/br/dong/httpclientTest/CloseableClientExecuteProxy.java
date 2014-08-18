//package com.br.dong.httpclientTest;
///** 
// * @author  hexd
// * 创建时间：2014-5-22 下午2:29:21 
// * 类说明 
// */
//import org.apache.http.HttpHost;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//public class CloseableClientExecuteProxy {
//	public static void main(String[] args)throws Exception {
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        try {
//            HttpHost target = new HttpHost("www.baidu.com");
//            HttpHost proxy = new HttpHost("23.94.44.10", 7808, "http");
//
//            RequestConfig config = RequestConfig.custom()
//                    .setProxy(proxy)
//                    .build();
//            HttpGet request = new HttpGet("/");
//            //request 配置好代理
//            request.setConfig(config);
//
//            System.out.println("Executing request " + request.getRequestLine() + " to " + target + " via " + proxy);
//
//            CloseableHttpResponse response = httpclient.execute(target, request);
//            try {
//                System.out.println("----------------------------------------");
//                System.out.println(response.getStatusLine());
//                System.out.println(EntityUtils.toString(response.getEntity()));
//                EntityUtils.consume(response.getEntity());
//            } finally {
//                response.close();
//            }
//        } finally {
//            httpclient.close();
//        }
//    }
//}
