package com.br.dong.httpclientTest.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class ThreadGET {
	// 线程池
	private ExecutorService exe = null;
	// 线程池的容量
	private static final int POOL_SIZE = 20;
	private HttpClient client = null;
	String[] urls = null;
	//构造方法
	public ThreadGET(String[] urls) {
		this.urls = urls;
	}
	
	public static void main(String[] args) {
		//并发请求的链接
		String [] urls={
						"http://www.baidu.com",
						"http://www.baidu.com",
						"http://www.baidu.com",
						"http://www.baidu.com",
						"http://www.baidu.com",
						"http://www.baidu.com",
						"http://www.baidu.com",
						"http://www.baidu.com",
						"http://www.baidu.com",
						"http://www.baidu.com",
						"http://www.baidu.com"
						};
		ThreadGET tps=new ThreadGET(urls);
		  // 设置组件参数, HTTP协议的版本,1.1/1.0/0.9   
	    HttpParams params = new BasicHttpParams();   
	    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);   
	    HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");   
	    HttpProtocolParams.setUseExpectContinue(params, true);     
		//设置连接超时时间   
	    int REQUEST_TIMEOUT = 10*1000;  //设置请求超时10秒钟   
	    int SO_TIMEOUT = 10*1000;       //设置等待数据超时时间10秒钟   
	    //HttpConnectionParams.setConnectionTimeout(params, REQUEST_TIMEOUT);  
	    //HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);  
	    params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, REQUEST_TIMEOUT);    
	    params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);   
	    //设置访问协议   
	    SchemeRegistry schreg = new SchemeRegistry();    
	    schreg.register(new Scheme("http",80,PlainSocketFactory.getSocketFactory()));   
	    schreg.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));         
	    //多连接的线程安全的管理器   
	   // PoolingClientConnectionManager pccm = new PoolingClientConnectionManager(schreg);  
	  //  pccm.setDefaultMaxPerRoute(20); //每个主机的最大并行链接数   
	 //   pccm.setMaxTotal(100);          //客户端总并行链接最大数      
	   // DefaultHttpClient httpClient = new DefaultHttpClient(pccm, params);  
		tps.exe=Executors.newFixedThreadPool(POOL_SIZE);
		/* 使用线程池 */
		for (int i = 0; i < urls.length; i++) {
			final int j = i;
			System.out.println(j);
			HttpGet httpget = new HttpGet(urls[i]);
		//	tps.exe.execute(new GetThread(httpClient, httpget, i));
		}
	}
    /** 
     * A thread that performs a GET. 
     */  
    static class GetThread extends Thread {  
  
        private final HttpClient httpClient;  
        private final HttpContext context;  
        private final HttpGet httpget;  
        private final int id;  
        //构造方法
        public GetThread(HttpClient httpClient, HttpGet httpget, int id) {  
            this.httpClient = httpClient;  
            this.context = new BasicHttpContext();  
            this.httpget = httpget;  
            this.id = id;  
        }  
  
        /** 
         * Executes the GetMethod and prints some status information. 
         */  
        @Override  
        public void run() {  
  
            System.out.println(id + " - about to get something from " + httpget.getURI());  
  
            try {  
  
                // execute the method  
                HttpResponse response = httpClient.execute(httpget, context);  
                System.out.println(id + " - get executed");  
                // get the response body as an array of bytes  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    byte[] bytes = EntityUtils.toByteArray(entity);  
                    System.out.println(id + " - " + bytes.length + " bytes read");  
                }  
  
            } catch (Exception e) {  
                httpget.abort();  
                System.out.println(id + " - error: " + e);  
            }  
        }  
  
    }  
}
