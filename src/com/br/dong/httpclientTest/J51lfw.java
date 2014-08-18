package com.br.dong.httpclientTest;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.br.dong.utils.DateUtil;


/** 
 * @author  hexd
 * 创建时间：2014-7-23 下午1:59:08 
 * 类说明 
 */
public class J51lfw {
	
	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, CloneNotSupportedException, ParseException, IOException {
		String loginurl="http://www.59lfw.com/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes";
		//新发帖地址
		String newtt="http://www.59lfw.com/forum.php?mod=post&action=newthread&fid=112&topicsubmit=yes&infloat=yes&handlekey=fastnewpost&inajax=1";
		//回帖拿分的地址
		String reUrl="http://www.51lfw.com/forum.php?mod=post&action=reply&fid=113&tid=216016&extra=page%3D1&replysubmit=yes&infloat=yes&handlekey=fastpost&inajax=1";
		//php-dz 论坛的formhash 可以查看源文件获得formhash 这个是发帖的formhash地址
		String hashurl="http://www.59lfw.com/forum.php?mod=forumdisplay&fid=112";
		//这个是发表回复的这边帖子的地址
		String reurl="http://www.51lfw.com/forum.php?mod=viewthread&tid=216016&extra=page%3D1";
		CrawlerUtil client=new CrawlerUtil();
		client.clientCreate("http", "www.59lfw.com", newtt);
		//设置登陆
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("username", "he7253997"));
		list.add(new BasicNameValuePair("password", "95b004"));
		HttpResponse responsepost=client.post(loginurl, client.produceEntity(list));
		HttpEntity entityp = responsepost.getEntity();
		Document docp = Jsoup.parse(EntityUtils
				.toString(entityp, "UTF-8")); // 从字符串中加载
		//  System.out.println(docp.toString());
		if(docp.toString().contains("he7253997")){
			while(true){
				//设置天天发帖  
				List<NameValuePair> listtt = new ArrayList<NameValuePair>();
				 listtt.add(new BasicNameValuePair("subject", "每日一帖"));
				listtt.add(new BasicNameValuePair("message", "每日一帖每日一帖每日一帖每日一帖每日一帖每日一帖每日一帖每日一帖每日一帖每日一帖每日一帖每日一帖每日一帖！"));
				listtt.add(new BasicNameValuePair("posttime", (new Date().getTime()+"").substring(0, 10)));//毫秒数
				//System.out.println((new Date().getTime()+"").substring(0, 10));
				HttpResponse gethash=client.noProxyGetUrl(hashurl);
				Document dohash=client.getDocUTF8(gethash);
//				 System.out.println(dohash.toString());
				 Elements formhash=dohash.select("div[class$=footer]").first().select("a[title$=退出]");
//				 System.out.println(formhash.toString());
				 int start=formhash.attr("href").indexOf("formhash");
				 int end=formhash.attr("href").indexOf("&mobile"); 
				listtt.add(new BasicNameValuePair("formhash", formhash.attr("href").substring(start+9, end)));//97732f5e
//				 System.out.println(formhash.attr("href").substring(start+9, end));
				HttpResponse rett=client.post(newtt, client.produceEntity(listtt));
				HttpEntity entt=rett.getEntity();
				Document dott=Jsoup.parse(EntityUtils.toString(entt,"UTF-8"));
				//System.out.println(dott.toString());
		 	    if(dott.toString().contains("新主题需要审核")){
		 	    	System.out.println("发帖成功！"+DateUtil.getStrOfDateTime());
		 	    } 
			}
		}
	
 	  
	}
}
