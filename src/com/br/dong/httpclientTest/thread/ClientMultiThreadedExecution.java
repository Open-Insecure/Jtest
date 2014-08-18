package com.br.dong.httpclientTest.thread;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class ClientMultiThreadedExecution {  
	  
    public static void main(String[] args) throws Exception {  
        // Create and initialize HTTP parameters  
        HttpParams params = new BasicHttpParams();  
        ConnManagerParams.setMaxTotalConnections(params, 100);  
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);  
  
        // Create and initialize scheme registry  
        SchemeRegistry schemeRegistry = new SchemeRegistry();  
        schemeRegistry.register(  
                new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));  
  
        // Create an HttpClient with the ThreadSafeClientConnManager.  
        // This connection manager must be used if more than one thread will  
        // be using the HttpClient.  
        ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);  
        HttpClient httpClient = new DefaultHttpClient(cm, params);  
  
        // create an array of URIs to perform GETs on  
        String[] urisToGet = {  
        		"http://localhost:8088/weixinforx/test/fuck3",  
        		"http://localhost:8088/weixinforx/test/fuck3",  
        		"http://localhost:8088/weixinforx/test/fuck3",  
        		"http://localhost:8088/weixinforx/test/fuck3"  
        };  
  
        // create a thread for each URI  
        GetThread[] threads = new GetThread[urisToGet.length];  
        for (int i = 0; i < threads.length; i++) {  
            HttpGet httpget = new HttpGet(urisToGet[i]);  
            threads[i] = new GetThread(httpClient, httpget, i + 1);  
        }  
  
        // start the threads  
        for (int j = 0; j < threads.length; j++) {  
            threads[j].start();  
        }  
  
        // join the threads  
        for (int j = 0; j < threads.length; j++) {  
            threads[j].join();  
        }  
  
        // When HttpClient instance is no longer needed,  
        // shut down the connection manager to ensure  
        // immediate deallocation of all system resources  
        httpClient.getConnectionManager().shutdown();  
    }  
  
    /** 
     * A thread that performs a GET. 
     */  
    static class GetThread extends Thread {  
  
        private final HttpClient httpClient;  
        private final HttpContext context;  
        private final HttpGet httpget;  
        private final int id;  
  
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