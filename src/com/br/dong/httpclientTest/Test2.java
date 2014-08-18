package com.br.dong.httpclientTest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * post提交表单数据
 * */
public class Test2 {
	
	public static void main(String[] args) {
		HttpClient httpclient = new DefaultHttpClient();
		//测试post提交数据给本地的应用ajaxTest
		HttpPost httppost = new HttpPost("http://localhost:8088/ajaxTest/jsonAjaxAction");
		//创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("userName", "house"));
		formparams.add(new BasicNameValuePair("content", "content"));
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			System.out.println("executing request " + httppost.getURI());
			HttpResponse response;
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {		System.out.println("--------------------------------------");
			System.out.println("Response content: " + EntityUtils.toString(entity,"UTF-8"));
		System.out.println("--------------------------------------");
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				}catch(UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}catch (IOException e) {
					e.printStackTrace();
				}finally{
		             //关闭连接,释放资源
					httpclient.getConnectionManager().shutdown();
				}

	}
}
