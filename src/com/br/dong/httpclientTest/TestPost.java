package com.br.dong.httpclientTest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
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
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

//http://10.1.12.52:8001/yhwz/fund/androidCMSJjh.do?methodCall=getDocList&catName=S0NTeGiEvFO+eQdgmEL3j5a4KITbfTegqp2M9Ef4/Yc=
//http://10.1.12.52:8001/yhwz/fund/androidMore.do?methodCall=report1&more=more2 
//http://www.yhzqjj.com/interface?func=013002&docId=1ANif5sz6Pg=
public class TestPost {
	public static void main(String[] args) {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000);
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000);
		HttpPost httppost = new HttpPost("http://www.10pan.cc/ajax.php");
		//创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("action", "load_down_addr1"));
		 formparams.add(new BasicNameValuePair("file_id", "142244"));
		//formparams.add(new BasicNameValuePair("more", "more1"));
		//VQXdvvQ/hng2iA97Y64BHssVPQA8Uejp466dEKuDuwY=   S0NTeGiEvFO+eQdgmEL3j5a4KITbfTegqp2M9Ef4/Yc=
		//formparams.add(new BasicNameValuePair("catName", "S0NTeGiEvFO+eQdgmEL3j5a4KITbfTegqp2M9Ef4/Yc="));
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			System.out.println("executing request " + httppost.getURI());
			HttpResponse response;
			response = httpclient.execute(httppost);
		
			HttpEntity entity = response.getEntity();
			if (entity != null) {	
			 System.out.println("--------------------------------------");
			System.out.println("Response content: " + EntityUtils.toString(entity,"UTF-8"));

			 System.out.println("--------------------------------------");
				}	
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				}catch(UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}catch (ConnectException e) {
					e.printStackTrace();
					System.out.println("链接超时");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
		             //关闭连接,释放资源
					httpclient.getConnectionManager().shutdown();
				}
	}
}
