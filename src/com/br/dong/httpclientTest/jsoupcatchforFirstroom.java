package com.br.dong.httpclientTest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;

public class jsoupcatchforFirstroom {
	//不使用代理
	public  HttpResponse noProxy(String url) throws ClientProtocolException, IOException{
		HttpClient httpclient = new DefaultHttpClient();
		// 创建httpget.
		HttpGet httpget = new HttpGet(url);

		System.out.println("executing request " + httpget.getURI());

		// 执行get请求.
		HttpResponse response = httpclient.execute(httpget);
		return response;
	}
	//使用代理
	public HttpResponse proxy(String url) throws IOException{
		HttpClient httpclient = new DefaultHttpClient();
		   //设定目标站点     www.baidu.com
        HttpHost httpHost = new HttpHost(url);  
        //设置代理对象 ip/代理名称,端口     						//192.168.0.101, 3128
        httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost("svrproxya.chinastock.com.cn", 8080));  
        //实例化验证     
        CredentialsProvider credsProvider = new BasicCredentialsProvider();  
        //设定验证内容     
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials("fttj", "ft07");  
        //创建验证     
        credsProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);  
        ((DefaultHttpClient) httpclient).setCredentialsProvider(credsProvider);  
        // 目标地址       
        HttpGet httpget = new HttpGet("/");  
        // 执行       
        HttpResponse response = httpclient.execute(httpHost, httpget);  
        //请求成功     
		return response;
	}
	
	public static void main(String[] args) throws ParseException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		jsoupcatchforFirstroom jf=new jsoupcatchforFirstroom();

		try {
			//不使用代理
		//	HttpResponse response=jf.noProxy("http://www.firstgongyu.com/forum-2-2.html");
			HttpResponse response=jf.noProxy("http://www.firstgongyu.com/forum.php?mod=forumdisplay&fid=2&page=1&&mobile=no");
			//使用代理
			//HttpResponse response=jf.proxy("http://www.firstgongyu.com/forum-2-2.html");
			 //获取响应实体

			HttpEntity entity = response.getEntity();

			System.out.println("————————————–");

			// 打印响应状态

			System.out.println(response.getStatusLine());

			if (entity != null) {

				// 打印响应内容长度

				System.out.println("Response content length: "
						+ entity.getContentLength());

				// 打印响应内容

//				  System.out.println("Response content: " +
//				  EntityUtils.toString(entity));
				 //使用jsoup解析网页
				Document doc = Jsoup.parse(EntityUtils
						.toString(entity, "UTF-8")); // 从字符串中加载
				//获得所有信息列表 包括置顶信息和x列表信息
 				Elements divHeros = doc.select("table[summary$=forum_2]");
        		//获得x列表  在浏览器端信息点击前样式为new 点击后样式为common
     			// Elements xs=divHeros.select("th[class$=new]");
     			 //获得x列表 包含发布时间 取tbody的子元素tr
     			Elements xs=divHeros.select("tbody[id*=normalthread]>tr");
     			//获得地区列表
     			//String places=xs.select("em>a[href]").attr("href"); 查找em子元素a的链接地址
     			Elements places=xs.select("em");
     			//获得发布时间
     			//获得具体x消息的链接
     		//	Elements xdetail=divHeros.select("");
     			//获得x消息列表
				  for(Element name : xs){
					System.out.println("地区："
							+ name.select("th[class$=new]>em").text() + "标题："
							+ name.select("a[class$=xst]").text() +"链接地址：" 
							+name.select("a[class$=xst]").attr("abs:href")+"\t发布时间："
							+ name.select("span[class$=xi1]").text());
				  }
					System.out.println("————————————");
//					  for(Element name : places){
//						  //获得地区标签和链接  attr("abs:href"); 包含根路径的地址
//			               System.out.println(name.text()+"地区连接:"+name.select("a").attr("abs:href"));
//			               }
			}
			System.out.println("————————————");

		} catch (UnknownHostException e) {
			// TODO: handle exception 访问的主机错误
			System.out.println("UnknownHostException  错误！");
		}
		finally {
			// 关闭连接,释放资源
			httpclient.getConnectionManager().shutdown();

		}
		 
	}
}
