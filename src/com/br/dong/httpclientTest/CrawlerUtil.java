package com.br.dong.httpclientTest;

import java.io.IOException;
import java.net.SocketException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/** 
 * @author  hexd
 * 创建时间：2014-7-21 下午2:57:06 
 * 类说明 
 * 建立一个爬虫工具类(支持多线程)，方便编写使用
 * 使用HTTPclient4.1版本 为了兼容新浪sae
 */
public class CrawlerUtil {
	//解析response的时候所用的编码
	private static String DECODE_UTF_8="UTF-8";
	private static String DECODE_GBK="GBK";
    //超时时间
    private static int TIME_OUT_TIME=50000;
	//想要带入的参数，可以根据需要扩展
	private String parm1;
	//使用cookie
	private BasicCookieStore cookieStore = new BasicCookieStore();
	//返回给调用此类的一个client实例
	private DefaultHttpClient client;
	//post方式
	private HttpPost post=new HttpPost();
	//get方式
	private HttpGet get=new HttpGet();
	//带参构造方法
	public CrawlerUtil(String parm1){
		this.parm1=parm1;
	}
	//无参构造方法
	public CrawlerUtil(){
		
	}

    public void clientCreatNoUrl(String type) throws NoSuchAlgorithmException, KeyManagementException {

        this.clientCreate(type,"","");
    }

	/**实例化client并且设置请求头和get post实例
	 * @param type 返回client的类型 type="http"的时候使用http的实例 type="https"的时候使用https实例
	 * @param host 目标主机url
	 * @param refURL 引用的refURL 具体可以使用谷歌浏览器network查看
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public void clientCreate(String type,String host,String refURL) throws KeyManagementException, NoSuchAlgorithmException
	{
		
		//初始化client
		if("http".equals(type)){
			client=getDefaultClient();
		}else if("https".equals(type)){
			client=getHttpsClient();
		}
		//设置cookie
		 client.setCookieStore(cookieStore);
//		System.out.println("cookie:"+cookieStore.toString());
		//设置浏览器参数
		String HEADER_HOST = host;
		String HEADER_CONNECTION = "keep-alive";
		String HEADER_ACCEPT = "*/*";
		String HEADER_USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36";
		String HEADER_REFERER = refURL;
		String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
 		String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
		//设置post请求头
		post.setHeader("Host",host);
		post.setHeader("Connection", HEADER_CONNECTION);
		post.setHeader("Accept", HEADER_ACCEPT);
		post.setHeader("User-Agent", HEADER_USER_AGENT);
		post.setHeader("Referer", HEADER_REFERER);
		post.setHeader("Accept-Encoding", HEADER_ACCEPT_ENCODING);
		post.setHeader("Accept-Language", HEADER_ACCEPT_LANGUAGE);
		//设置get请求头
		get.setHeader("Host",host);
		get.setHeader("Connection", HEADER_CONNECTION);
		get.setHeader("Accept", HEADER_ACCEPT);
		get.setHeader("User-Agent", HEADER_USER_AGENT);
		get.setHeader("Referer", HEADER_REFERER);
		get.setHeader("Accept-Encoding", HEADER_ACCEPT_ENCODING);
		get.setHeader("Accept-Language", HEADER_ACCEPT_LANGUAGE);
		
	}
	/**
	 * 初始化访问https目标url的类
	 */
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
	
	/**实现返回一个访问https的client实例
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public DefaultHttpClient getHttpsClient() throws KeyManagementException,NoSuchAlgorithmException {
			client=new DefaultHttpClient(new ThreadSafeClientConnManager());
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = client.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", ssf, 443));
			client = new DefaultHttpClient(ccm, client.getParams());
            //设置超时时间
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT,TIME_OUT_TIME );
			return client;

	}
	
	/**返回一个默认的client
	 * @return
	 */
	public DefaultHttpClient getDefaultClient(){
		//ThreadSafeClientConnManager 解决多线程公用一个client问题
		DefaultHttpClient client = new DefaultHttpClient(new ThreadSafeClientConnManager());
        //设置超时时间
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT_TIME);
		return client;
	}
	
	//不使用代理get方式访问url
	public  HttpResponse noProxyGetUrl(String url) throws ClientProtocolException, IOException, CloneNotSupportedException{
		HttpGet get =getGetInstance(url);
		HttpResponse response=null;
		try {
			//System.out.println("executing request " + get.getURI());
			response =client.execute(get);
		} catch (HttpHostConnectException e) {
			// TODO: handle exception
			} 
		return response;
	}
	//使用代理post方式访问url
	public HttpResponse proxyPostUrl(String url,String proxyUrl,int port,List<NameValuePair> list) throws IOException, CloneNotSupportedException{
		HttpPost post =getPostInstance(url);
		HttpResponse response=null;
		try {
	        //设置代理对象 ip/代理名称,端口   // "125.39.66.66", 80 "66.85.131.18", 8089 "210.51.56.198",808  "96.56.105.66",7004	 "122.232.229.90",80
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(proxyUrl, port));
	        //实例化验证     
	        CredentialsProvider credsProvider = new BasicCredentialsProvider();  
	        //设定验证内容     
	        UsernamePasswordCredentials creds = new UsernamePasswordCredentials("fttj", "ft07");  
	        //创建验证     
	        credsProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);  
	        ((DefaultHttpClient) client).setCredentialsProvider(credsProvider);  
			//System.out.println("executing request " + get.getURI());
	    		//从新设置post的内容
	    		post.setEntity(produceEntity(list));
	    		 response = client.execute(post);

		}catch(HttpHostConnectException e){
//            System.out.println("连接代理服务器"+proxyUrl+"失败..");
        }catch(NoHttpResponseException e){
//            System.out.println("连接代理服务器"+proxyUrl+"没有响应..");
        }catch (SocketException e){
//            System.out.println("连接服务器"+proxyUrl+"连接重置错误..");
        }
		return response;
	}
	//使用代理get方式访问url
	public HttpResponse proxyGetUrl(String url,String proxyUrl,int port) throws IOException, CloneNotSupportedException{
		HttpGet get =getGetInstance(url);
	     HttpHost httpHost = new HttpHost(url); 
		HttpResponse response=null;
		try {
	        //设置代理对象 ip/代理名称,端口     						// "125.39.66.66", 80 "66.85.131.18", 8089
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(proxyUrl, port));
	        //实例化验证     
	        CredentialsProvider credsProvider = new BasicCredentialsProvider();  
	        //设定验证内容     
	        UsernamePasswordCredentials creds = new UsernamePasswordCredentials("fttj", "ft07");  
	        //创建验证     
	        credsProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);  
	        ((DefaultHttpClient) client).setCredentialsProvider(credsProvider);  
			//System.out.println("executing request " + get.getURI());
	        try{
	    		response =client.execute(httpHost, get); 
	        }catch(HttpHostConnectException e){
//				System.out.println("连接代理"+proxyUrl+"失败..");
			}catch(NoHttpResponseException e){
//				System.out.println("服务器"+proxyUrl+"没有响应..");
			}
	
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			} 
		return response;
	}
	/**克隆获得post的实例
	 * @param URL
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public HttpPost getPostInstance(String URL) throws CloneNotSupportedException{
		HttpPost p=new HttpPost();
		p=(HttpPost) this.post.clone();
		p.setURI(URI.create(URL));
		return p;
	}
	/**克隆获得get的实例
	 * @param URL
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public HttpGet getGetInstance(String URL) throws CloneNotSupportedException{
		HttpGet g=new HttpGet();
		//克隆get的header给新的HttpGet
		g=(HttpGet) this.get.clone();
		g.setURI(URI.create(URL));
		return g;
	}
	/**
	 * 设置post表单的内容 需要根据具体的网站设置具体的post内容
	 * @return UrlEncodedFormEntity
	 * */
	public UrlEncodedFormEntity produceFormEntity(){
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("username", "he7253997"));
		list.add(new BasicNameValuePair("password", "95b004"));
		UrlEncodedFormEntity encodedFormEntity = null;
		try {
			encodedFormEntity = new UrlEncodedFormEntity(list,"utf-8");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return encodedFormEntity;
	}
	
	/**
	 * 设置post表单的内容 需要根据具体的网站设置具体的post内容
	 * @return UrlEncodedFormEntity
	 * */
	public UrlEncodedFormEntity produceEntity(List param){
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list=param;
		UrlEncodedFormEntity encodedFormEntity = null;
		try {
			encodedFormEntity = new UrlEncodedFormEntity(list,"utf-8");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return encodedFormEntity;
	} 
	/**
	 * 用于post的函数
	 * @param URL
	 * post的URL
	 * @param entity 将UrlEncodedFormEntity方法当参数传入
	 * post的内容
	 * @throws CloneNotSupportedException 
	 * 
	 * */
	public  HttpResponse post(String URL,UrlEncodedFormEntity entity) throws CloneNotSupportedException
	{
		HttpPost post = getPostInstance(URL);
		//从新设置post的内容
		post.setEntity(entity);
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (Exception e) {
		// TODO: handle exception
			e.printStackTrace();
		}  
		return response;
	}
	public Document getDocUTF8(HttpResponse response){
		HttpEntity entity=response.getEntity();
		Document document=null;
		try {
			 document=Jsoup.parse(EntityUtils.toString(entity, DECODE_UTF_8));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}
	
	public Document getDocGBK(HttpResponse response){
		HttpEntity entity=response.getEntity();
		Document document=null;
		try {
			 document=Jsoup.parse(EntityUtils.toString(entity, DECODE_GBK));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}
	/**
	 * 关闭Httpclient
	 * */
	public void closeClient()
	{
		client.getConnectionManager().shutdown();
	}
	
	/**执行头方法
	 * @param httpHead
	 * @return
	 */
	public HttpResponse executeHead(HttpHead httpHead){
		HttpResponse response=null;
		try {
			response=client.execute(httpHead);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	/**直接执行get方法
	 * @param get
	 * @return
	 */
	public HttpResponse executeGetMethod(HttpGet get){
		HttpResponse response=null;
		try {
			response=client.execute(get);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	//测试
	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException, CloneNotSupportedException {
    }

}
