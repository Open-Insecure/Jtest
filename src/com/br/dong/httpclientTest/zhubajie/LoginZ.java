package com.br.dong.httpclientTest.zhubajie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.br.dong.httpclientTest.HttpsClient;

/** 
 * @author  hexd
 * 创建时间：2014-7-7 下午3:43:59 
 * 类说明 
 */
public class LoginZ {
	
    private static X509TrustManager tm = new X509TrustManager() {  
        public void checkClientTrusted(X509Certificate[] xcs, String string)  
                throws CertificateException {  
        }  
        public void checkServerTrusted(X509Certificate[] xcs, String string)  
                throws CertificateException {  
        }  
        public X509Certificate[] getAcceptedIssuers() {  
            return null;  
        }  
    };  
    //返回https的client
	public static HttpClient getInstance() throws KeyManagementException,
			NoSuchAlgorithmException {
		HttpClient client = new DefaultHttpClient();
		SSLContext ctx = SSLContext.getInstance("TLS");
		ctx.init(null, new TrustManager[] { tm }, null);
		SSLSocketFactory ssf = new SSLSocketFactory(ctx);
		ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		ClientConnectionManager ccm = client.getConnectionManager();
		SchemeRegistry sr = ccm.getSchemeRegistry();
		sr.register(new Scheme("https", ssf, 443));
		client = new DefaultHttpClient(ccm, client.getParams());
		return client;
	}

	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
		  String url="https://login.zhubajie.com/login/dologin";
	       HttpClient httpsClient = HttpsClient.getInstance();  
	       httpsClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,20000);  
	       HttpPost post = new HttpPost("https://login.zhubajie.com/login/dologin"); 
	       //
	   	   post.setHeader("Host","login.zhubajie.com");
	   	   post.setHeader("Referer", "https://login.zhubajie.com/login");
	   	   //创建参数队列
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("username", "837484691@qq.com"));
			 formparams.add(new BasicNameValuePair("password", "95b004"));
			 formparams.add(new BasicNameValuePair("catcha", "6js3"));
			 formparams.add(new BasicNameValuePair("seed", "53ba4ecc0ef1e"));
			 formparams.add(new BasicNameValuePair("fromurl", "http://www.zhubajie.com/"));
			 
			 UrlEncodedFormEntity uefEntity=new UrlEncodedFormEntity(formparams, "UTF-8");
			 post.setEntity(uefEntity);
	       HttpResponse response = httpsClient.execute(post);  
	       HttpEntity entity = response.getEntity();   
	       BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));  
	        StringBuffer content = new StringBuffer();  
	        for (String line; (line = br.readLine()) != null;) {  
	            content.append(line + "\r\n");  
	        }  
	        System.err.println(content.toString());  
	        System.out.println("code:"+response.getStatusLine().getStatusCode());
	        if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
	        	//登录成功
	        	HttpGet get=new HttpGet("http://www.zhubajie.com");
				HttpResponse r=httpsClient.execute(get);
				HttpEntity entitymain = r.getEntity();
				 BufferedReader br2 = new BufferedReader(new InputStreamReader(entitymain.getContent()));  
			        StringBuffer content2 = new StringBuffer();  
			        for (String line; (line = br2.readLine()) != null;) {  
			            content2.append(line + "\r\n");  
			        }  
			        System.err.println(content2.toString());  
	        }
	}
	
}
