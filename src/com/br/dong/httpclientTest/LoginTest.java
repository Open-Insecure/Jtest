package com.br.dong.httpclientTest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
public class LoginTest {
private final static HttpClient client = new DefaultHttpClient();
	
	public static void main(String[] args) throws IOException {
		login("<用户名>","<密码>", false);
		get("http://www16.dict.cn/bdc/141");
	}
	
	/**
	 * 抓取网页
	 * @param url
	 * @throws IOException
	 */
	static void get(String url) throws IOException {
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		System.out.println(response.getStatusLine());
		HttpEntity entity = response.getEntity();
		dump(entity);
	}
	
	/**
	 * 执行登录过程
	 * @param user
	 * @param pwd
	 * @param debug
	 * @throws IOException
	 */
	static void login(String user, String pwd, boolean debug) throws IOException {
		HttpPost post = new HttpPost("http://dict.cn/login.php");
		post.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.63 Safari/534.3");
		
		//登录表单的信息
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("username", user));
		qparams.add(new BasicNameValuePair("password", pwd));
		qparams.add(new BasicNameValuePair("url", "http://www16.dict.cn/bdc/141"));
		qparams.add(new BasicNameValuePair("loginforever", "1"));
		
		UrlEncodedFormEntity params = new UrlEncodedFormEntity(qparams, "UTF-8");
		post.setEntity(params);

		// execute the request
		HttpResponse response = client.execute(post);
		
		if(debug){
			// Examine the response status
			System.out.println(response.getStatusLine());
	
			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			
			dump(entity);
		}
		post.abort();
	}

	/**
	 * 打印页面
	 * @param entity
	 * @throws IOException
	 */
	private static void dump(HttpEntity entity) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(entity.getContent(), "GBK"));
		
		System.out.println(IOUtils.toString(br));
	}
}
