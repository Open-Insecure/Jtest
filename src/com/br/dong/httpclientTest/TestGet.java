package com.br.dong.httpclientTest;
import java.io.IOException;  
import java.io.UnsupportedEncodingException;  
import java.net.URI;  
import java.net.URISyntaxException;  
import java.net.URLEncoder;  
import java.text.SimpleDateFormat;  
import java.util.ArrayList;  
import java.util.Date;  
import java.util.HashMap;
import java.util.List;  

import org.apache.http.Header;
import org.apache.http.HttpEntity;  
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;  
import org.apache.http.NameValuePair;  
import org.apache.http.ParseException;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.protocol.HTTP;  
import org.apache.http.util.EntityUtils; 

public class TestGet {
	private static HttpClient httpClient = new DefaultHttpClient();  
	/**  
     * 发送Get请求  
     * @param url  
     * @param params  
     * @return  
     */  
	public static void main(String[] args) {
		String url="http://www.yhzqjj.com/interface";
		String testurl="http://10.1.12.52:8001/interface";
		String nnurl="http://10.1.50.197/interface";
		//银河局域网代理服务器svrproxya.chinastock.com.cn 8080
		
		//创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("func", "013001"));
		 formparams.add(new BasicNameValuePair("catName", "S0NTeGiEvFO+eQdgmEL3j5a4KITbfTegqp2M9Ef4/Yc="));
		  System.out.println("生产数据:"+get(nnurl));
		 System.out.println("测试数据:"+get(testurl));
	}
	
	//设置代理

	public static void getHttpClient() {

	 String proxyHost = "http://svrproxya.chinastock.com.cn";
	 int proxyPort = 8080;
	 HttpHost proxy = new HttpHost(proxyHost, proxyPort);
	 httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	}
    public static String get(String url) {  
        String body = null;  
        try {  
            // Get请求  
            HttpGet httpget = new HttpGet(url);  
            System.out.println("url:"+httpget.getURI().toString() + "?" + "func=013001&catName=S0NTeGiEvFO+eQdgmEL3j5a4KITbfTegqp2M9Ef4/Yc=");
            httpget.setURI(new URI(httpget.getURI().toString() + "?" + "func=013001&catName=S0NTeGiEvFO+eQdgmEL3j5a4KITbfTegqp2M9Ef4/Yc="));
            //设置头
            // 发送请求  
            HttpResponse httpresponse = httpClient.execute(httpget); 
      
            // 获取返回数据  
            HttpEntity entity = httpresponse.getEntity();  
            body = EntityUtils.toString(entity);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (URISyntaxException e) {  
            e.printStackTrace();  
        }finally{
            //关闭连接,释放资源
        	httpClient.getConnectionManager().shutdown();
		}  
        return body;  
    }  
}
