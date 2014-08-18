package com.br.dong.httpclientTest;
import java.io.IOException;  
import org.apache.http.HttpEntity;  
import org.apache.http.HttpHost;  
import org.apache.http.HttpResponse;  
import org.apache.http.HttpStatus;  
import org.apache.http.auth.AuthScope;  
import org.apache.http.auth.UsernamePasswordCredentials;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.CredentialsProvider;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.conn.params.ConnRoutePNames;  
import org.apache.http.impl.client.BasicCredentialsProvider;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.util.EntityUtils;  
public class GetProxy2 {
	public static void main(String[] args) throws ClientProtocolException, IOException {  
        //实例化一个HttpClient  
		
		DefaultHttpClient httpClient = new DefaultHttpClient();  
		
        //设定目标站点     www.baidu.com "http://www.firstgongyu.com/forum-2-1.html"
        HttpHost httpHost = new HttpHost("www.baidu.com");  
        //设置代理对象 ip/代理名称,端口     						//192.168.0.101, 3128
        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost("23.94.44.10", 7808, "http"));  
        //实例化验证     
        CredentialsProvider credsProvider = new BasicCredentialsProvider();  
        //设定验证内容     
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials("test", "test");  
        //创建验证     
        credsProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);  
        ((DefaultHttpClient) httpClient).setCredentialsProvider(credsProvider);  
        // 目标地址       
        HttpGet httpget = new HttpGet("/");  
        // 执行       
        HttpResponse response = httpClient.execute(httpHost, httpget);  
        //请求成功     
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {  
        	System.out.println("..."+response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();  
            if (entity != null) {  
                System.out.println(EntityUtils.toString(entity));  
            }  
            if (entity != null) {  
                entity.consumeContent();  
            }  
        }  
        //释放资源  
        httpClient.getConnectionManager().shutdown();  
    }  
  
}
