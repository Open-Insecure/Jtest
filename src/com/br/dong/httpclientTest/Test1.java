package com.br.dong.httpclientTest;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
/**
 * 
 * */
public class Test1 {
	public static void main(String[] args) throws ParseException, IOException {
		//创建默认的httpClient实例.
		HttpClient httpclient = new DefaultHttpClient();
		try {
		//创建httpget.
		   HttpGet httpget = new HttpGet("http://localhost:8088/ajaxTest/");
		System.out.println("executing request " + httpget.getURI());
		//执行get请求.
		   HttpResponse response = httpclient.execute(httpget);
		//获取响应实体
		   HttpEntity entity = response.getEntity();
		System.out.println("--------------------------------------");
		//打印响应状态
		   System.out.println(response.getStatusLine());
		      if (entity != null) {
		//打印响应内容长度
		          System.out.println("Response content length: " + entity.getContentLength());
		//打印响应内容
		         System.out.println("Response content: " + EntityUtils.toString(entity));
		            }
		        System.out.println("------------------------------------");
		        } finally {
		            //关闭连接,释放资源
		            httpclient.getConnectionManager().shutdown();
		        }
	}
}
