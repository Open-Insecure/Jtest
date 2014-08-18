package com.br.dong.httpclientTest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.IOException;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.http.client.methods.HttpPost;

public class JsoupCatchForLol {
	public static void main(String[] args) throws ParseException, IOException {
		HttpClient httpclient = new DefaultHttpClient();

		try {

			// 创建httpget.

			HttpGet httpget = new HttpGet("http://lol.duowan.com/s/heroes.html");

			System.out.println("executing request " + httpget.getURI());

			// 执行get请求.

			HttpResponse response = httpclient.execute(httpget);

			// 获取响应实体

			HttpEntity entity = response.getEntity();

			System.out.println("————————————–");

			// 打印响应状态

			System.out.println(response.getStatusLine());

			if (entity != null) {

				// 打印响应内容长度

				System.out.println("Response content length: "
						+ entity.getContentLength());

				// 打印响应内容

				// System.out.println("Response content: " +
				// EntityUtils.toString(entity));
				// 使用jsoup解析网页
				Document doc = Jsoup.parse(EntityUtils
						.toString(entity, "UTF-8")); // 从字符串中加载
				// 获得英雄框体 $= 参考jquery通配符 也可以=
				/**
				 * 
				 * (1)通配符：
					  $("input[id^='code']");//id属性以code开始的所有input标签
					  $("input[id$='code']");//id属性以code结束的所有input标签
					  $("input[id*='code']");//id属性包含code的所有input标签
				 * */
				Elements divHeros = doc.select("ul[id$=champion_list]");
				//获得每个英雄
				Elements liHeros=divHeros.select("li");
				  for(Element name : liHeros){
		               System.out.println(name.text());
		               }
				
			}

			System.out.println("————————————");

		} finally {

			// 关闭连接,释放资源

			httpclient.getConnectionManager().shutdown();

		}
	}
}
