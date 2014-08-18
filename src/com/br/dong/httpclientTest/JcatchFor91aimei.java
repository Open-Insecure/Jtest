package com.br.dong.httpclientTest;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/** 
 * @author  hexd
 * 创建时间：2014-7-22 下午2:22:38 
 * 类说明 
 */
public class JcatchFor91aimei {
	public static void main(String[] args) throws ClientProtocolException, IOException, CloneNotSupportedException, KeyManagementException, NoSuchAlgorithmException {
		CrawlerUtil client=new CrawlerUtil();
//		client.clientCreate("http", "www.91aimei.info.com", "www.91aimei.info.com");
////		HttpResponse response=client.noProxyGetUrl("http://www.91aimei.info/forum.php?mod=forumdisplay&fid=106&filter=author&orderby=dateline");
//		HttpResponse response=client.noProxyGetUrl("http://www.91aimei.info/forum.php?mod=viewthread&tid=6816&extra=page%3D1%26filter%3Dauthor%26orderby%3Ddateline%26orderby%3Ddateline");
//		HttpEntity entity = response.getEntity();
//		Document doc = Jsoup.parse(EntityUtils
//				.toString(entity, "UTF-8")); // 从字符串中加载
//		//System.out.println(doc.toString());
//		
//		 client.closeClient();
		
		client.clientCreate("http", "myhhg.me", "http://myhhg.me/login.php");
		String loginurl="http://www.91aimei.info/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes";
		//设置登陆
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("username", "he7253997"));
		list.add(new BasicNameValuePair("password", "95b004"));
		HttpResponse responsepost=client.post(loginurl, client.produceEntity(list));
		HttpEntity entityp = responsepost.getEntity();
		Document docp = Jsoup.parse(EntityUtils
				.toString(entityp, "UTF-8")); // 从字符串中加载
		 System.out.println(docp.toString());
		//获得签到页面
		HttpResponse responseg=client.noProxyGetUrl("http://www.91aimei.info/plugin.php?id=dsu_paulsign:sign");
		HttpEntity entityg = responseg.getEntity();
		Document docg= Jsoup.parse(EntityUtils
				.toString(entityg, "UTF-8")); // 从字符串中加载
		 // System.out.println(docg.toString());
//		String qiandao="http://www.91aimei.info/plugin.php?id=dsu_paulsign:sign&operation=qiandao&infloat=1";
//		List<NameValuePair> qdlist = new ArrayList<NameValuePair>();
//		qdlist.add(new BasicNameValuePair("qdxq", "kx"));
//		qdlist.add(new BasicNameValuePair("eply", "哎...今天够累的，签到来了1..."));
//		HttpResponse qiandaor=client.post(qiandao, client.produceEntity(qdlist));
//		
//		HttpEntity entitqiandao = qiandaor.getEntity();
//		Document docqiandao= Jsoup.parse(EntityUtils
//				.toString(entitqiandao, "UTF-8")); // 从字符串中加载
//		System.out.println(docqiandao.toString());
	}
}
