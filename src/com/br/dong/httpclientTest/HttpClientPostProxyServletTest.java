package com.br.dong.httpclientTest;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpClientPostProxyServletTest {
 public static final String HttpPostProxyUrl="www.baidu.com";
 public static void main(String[] args) throws ParseException, IOException {
     HttpHost proxy = new HttpHost("183.33.22.62", 9797, "http");

     DefaultHttpClient httpclient = new DefaultHttpClient();
     try {
         httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

         HttpHost target = new HttpHost("www.baidu.com");
         HttpPost request = new HttpPost(HttpPostProxyUrl);
   StringEntity reqEntity = new StringEntity("userName=abin&passWord=varyall");   
   reqEntity.setContentType("application/x-www-form-urlencoded");   
   request.setEntity(reqEntity);   
   request.setHeader("lee", "lee");
         System.out.println("executing request to " + target + " via " + proxy);
         HttpResponse rsp = httpclient.execute(target, request);
         HttpEntity entity = rsp.getEntity();

         System.out.println("----------------------------------------");
         System.out.println(rsp.getStatusLine());
         Header[] headers = rsp.getAllHeaders();
         for (int i = 0; i<headers.length; i++) {
             System.out.println(headers[i]);
         }
         System.out.println("----------------------------------------");

         if (entity != null) {
             System.out.println(EntityUtils.toString(entity));
         }

     } finally {
         // When HttpClient instance is no longer needed,
         // shut down the connection manager to ensure
         // immediate deallocation of all system resources
         httpclient.getConnectionManager().shutdown();
     
     }
     }
}
