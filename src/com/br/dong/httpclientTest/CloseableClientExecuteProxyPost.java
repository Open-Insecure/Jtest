//package com.br.dong.httpclientTest;
//
///** 
// * @author  hexd
// * 创建时间：2014-5-22 下午2:29:21 
// * 类说明 
// */
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.HttpHost;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.HttpParamConfig;
//import org.apache.http.util.EntityUtils;
//
////失败的测试类
//public class CloseableClientExecuteProxyPost {
//	public static void main(String[] args) throws Exception {
//        HttpHost proxy = new HttpHost("23.94.44.10", 7808, "http");
//		  DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
//		  CloseableHttpClient httpclient = HttpClients.custom()
//		          .setRoutePlanner(routePlanner)
//		          .build();
//		  HttpGet post = new HttpGet("http://www.baidu.com");
//		// HttpPost post = new HttpPost("www.baidu.com");
//			HttpResponse response = httpclient.execute(post);
//			String str = EntityUtils.toString(response.getEntity());
//			System.out.println(str);
//		  
//	}
//}
