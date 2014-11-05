package com.br.dong.httpclientTest;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.br.dong.utils.DateUtil;

/**
* @author  hexd
* 创建时间：2014-7-23 下午5:42:25
* 类说明
* 自动在51lfw上发表新人贴
*/
public class J51lfwTask {
	//登录post url	http://www.51lfw.com/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes&inajax=1
	private static String  loginurl="http://www.59lfw.com/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes";
	//新发帖post url
	private static String  newtt="http://www.59lfw.com/forum.php?mod=post&action=newthread&fid=112&topicsubmit=yes&infloat=yes&handlekey=fastnewpost&inajax=1";
	//新发帖url php-dz 论坛的formhash 可以查看源文件获得formhash 这个是发帖的formhash地址
	 private static String  hashurl="http://www.59lfw.com/forum.php?mod=forumdisplay&fid=112";
	 //访问推广的url uid从1开始累加
	 private static String visurl="http://www.fenghuaxueyuelou.com/home.php?mod=space&uid=";
	 private static int i=1;
	 private static CrawlerUtil client=new CrawlerUtil();
	 /**执行登录
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws CloneNotSupportedException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static String login(String refurl) throws KeyManagementException, NoSuchAlgorithmException, CloneNotSupportedException, ParseException, IOException{
		    client.clientCreate("http", "http://www.59lfw.com/forum.php", refurl);
			//设置登陆
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("username", "he7253997"));
			list.add(new BasicNameValuePair("password", "95b004"));
			list.add(new BasicNameValuePair("cookietime", "2592000"));
			HttpResponse responsepost=client.post(loginurl, client.produceEntity(list));
			HttpEntity entityp = responsepost.getEntity();
			Document docp = Jsoup.parse(EntityUtils
					.toString(entityp, "UTF-8")); // 从字符串中加载
			//  System.out.println(docp.toString());
			if(docp.toString().contains("he7253997")){
				System.out.println("登录成功");
				 return "success";
			}
			return "fall";
	 }

	/**
	 * 执行发帖
	 * @throws CloneNotSupportedException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static void newtt() throws ClientProtocolException, IOException, CloneNotSupportedException{
		//设置天天发帖
		List<NameValuePair> listtt = new ArrayList<NameValuePair>();
		 listtt.add(new BasicNameValuePair("subject", "每日一帖"));
		listtt.add(new BasicNameValuePair("message", "每日一帖每日一帖每日一帖每日一帖每日一帖每日一帖每日一帖每日一帖每日一帖每日一帖每日一帖每日一帖每日一帖！"));
		listtt.add(new BasicNameValuePair("posttime", (new Date().getTime()+"").substring(0, 10)));//毫秒数
		//System.out.println((new Date().getTime()+"").substring(0, 10));
		HttpResponse gethash=client.noProxyGetUrl(hashurl);
		Document dohash=client.getDocUTF8(gethash);
//		 System.out.println(dohash.toString());
		 Elements formhash=dohash.select("div[class$=footer]").first().select("a[title$=退出]");
			//System.out.println(dohash.toString());
		// System.out.println(formhash.toString());
		 int start=formhash.attr("href").indexOf("formhash");
		 int end=formhash.attr("href").indexOf("&mobile");
		listtt.add(new BasicNameValuePair("formhash", formhash.attr("href").substring(start+9, end)));//97732f5e
//		 System.out.println(formhash.attr("href").substring(start+9, end));
		HttpResponse rett=client.post(newtt, client.produceEntity(listtt));
		HttpEntity entt=rett.getEntity();
		Document dott=Jsoup.parse(EntityUtils.toString(entt,"UTF-8"));
		//System.out.println(dott.toString());
	    if(dott.toString().contains("新主题需要审核")){
	    	System.out.println("发帖成功！"+DateUtil.getStrOfDateTime());
	    }else{
	   	System.out.println("失败！"+DateUtil.getStrOfDateTime());
	    }
	}

	/**
	 * 访问别人空间
	 * @throws CloneNotSupportedException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static void vist() throws ClientProtocolException, IOException, CloneNotSupportedException{
		HttpResponse response=client.noProxyGetUrl(visurl+""+i);
		Document doc=client.getDocUTF8(response);
		System.out.println(doc.toString());
	}
	 public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, ParseException, CloneNotSupportedException, IOException {

		  ScheduledExecutorService service=Executors.newScheduledThreadPool(2);
		  //任务1 自动发帖
		   Runnable task1=new Runnable()
           {
                public void run()
                {
                	try {
                		String flag=login(newtt);
                		if("success".equals(flag)){
                			newtt();
                		}

					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (KeyManagementException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
           };
           //任务2 访问别人空间
		   Runnable task2=new Runnable()
           {
                public void run()
                {
                	try {
                		String flag=login(visurl+""+i);
                		if("success".equals(flag)){
                			vist();
                			}
                		i++;
                		System.out.println(i);
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (KeyManagementException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
           };
        	   //执行线程 20秒执行一次
             final ScheduledFuture future1=service.scheduleAtFixedRate(task1,0,21,TimeUnit.SECONDS);
            // final ScheduledFuture future2=service.scheduleAtFixedRate(task2,0,1,TimeUnit.SECONDS);

	}
}
