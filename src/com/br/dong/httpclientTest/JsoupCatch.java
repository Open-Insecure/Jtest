package com.br.dong.httpclientTest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.IOException;
import java.util.Scanner;

 
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class JsoupCatch {
	 /**
     * heepclient 抓取页面
     * jroup 解析页面内容
     * @param args
     */
    public static void main(String[] args) {
    	Scanner reader=new Scanner(System.in);
        System.out.println("请输入手机号码：");
        String strphone=reader.nextLine();
    	HttpClient httpclient = new DefaultHttpClient();
    	HttpPost httppost = new HttpPost("http://haoma.imobile.com.cn");
    	//创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("mob", "13011036936"));
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
			//打印完整的返回数据
			//System.out.println("Response content: " + EntityUtils.toString(entity,"UTF-8"));
			//jsoup解析返回的页面元素获取想要的字段
			 //解析页面内容
            Document doc= Jsoup.parse(EntityUtils.toString(entity,"UTF-8"));   //从字符串中加载
            //直接从URL 中加载页面信息。timeout设置连接超时时间 post提交方式 或者get()
//          Document document = (Document) Jsoup.connect("http://haoma.imobile.com.cn/index.php?mob=18710115102").timeout(3000).post();

           //Elements  是 Element 的集合类
            Elements element=doc.select("table");  //从加载的信息中查找table 标签

          //从查找到table属性的Elements集合中获取标签 tr 或者tr[class$=alt] 表示 tr标签内class属性=alt
//          Elements titleName=element.select("tr[class$=alt]");   
            Elements titleName=element.select("tr");
            for(Element name : titleName){
               System.out.println(name.text());
               }
		System.out.println("--------------------------------------");
					}
				} catch(UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}catch (IOException e) {
					e.printStackTrace();
				}finally{
		             //关闭连接,释放资源
					httpclient.getConnectionManager().shutdown();
				}
	}

}
