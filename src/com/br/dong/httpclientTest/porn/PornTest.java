package com.br.dong.httpclientTest.porn;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.br.dong.httpclientTest.CrawlerUtil;

/** 
 * @author  hexd
 * 创建时间：2014-8-12 上午9:05:37 
 * 类说明 
 */
public class PornTest {

	//视频列表url page页数需要拼装
	private static String url="http://91p.vido.ws/v.php?next=watch&page=";
	//视频文件请求url 后跟参数需要拼装
	private static String vedioFileUrl="http://91p.vido.ws/getfile.php?";
	private static CrawlerUtil client=new CrawlerUtil();
	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException, CloneNotSupportedException {
		//创建http请求的client
		client.clientCreate("http","91p.vido.ws" , "http://91p.vido.ws/index.php");
		getPaging();
     	//vedio http://91p.vido.ws/getfile.php?VID=8297&mp4=0&seccode=4455c308e748341a1f232bb67c557044&max_vid=83997
     	getPageVideos("http://91p.vido.ws/v.php?next=watch&page=2");
     	// getInfoDeatil("http://91p.vido.ws/view_video.php?viewkey=671600a14646d0a8f199&page=2&viewtype=basic&category=rf");
     	getInfoDeatilProxy("http://91p.vido.ws/view_video.php?viewkey=65a5320bf0dcd0243c54&page=2&viewtype=basic&category=mr");
	}
	/**
	 * 获取分页信息
	 */
	public static void getPaging(){
		int maxpage=2000;
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("session_language", "cn_CN"));
		//第一次采集第一页的
		HttpResponse response;
		try {
			response = client.post(url+"1", client.produceEntity(list));
			Document doc= client.getDocUTF8(response);
//	 		System.out.println(doc.toString());
			//拿到视频列表
			Elements content=doc.select("div[id=fullbox-content]");
			//拿到视频分页
//			Elements paging=doc.select("div[class*=pagingnav]>a");
			Elements maxpageElement=doc.select("div[class*=pagingnav]>a:eq(6)");
			maxpage=Integer.parseInt(maxpageElement.text());
		} catch (CloneNotSupportedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch(NumberFormatException e){
			System.out.println("拿去最大页数失败,自动填充为2000");
		}

     	System.out.println("获得最大页数:"+maxpage);
		//进行视频页面采集  最大页数+1
//     	for(int i=2;i<=maxpage+1;i++){
//     		System.out.println(url+""+i);
//     	}
	}
	
	/**
	 * 获得某个帖子的详细信息
	 * @throws CloneNotSupportedException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void  getInfoDeatilProxy(String url) throws ClientProtocolException, IOException, CloneNotSupportedException{
		//使用代理方式获得
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("session_language", "cn_CN"));
		HttpResponse response=client.proxyPostUrl(url,"122.232.229.90",80,list);
		Document doc=null;
		if(response==null){
			//连接代理失败，换下一个代理
			System.out.println("代理失败");
		}else{//连接代理成功 解析url
			 doc=client.getDocUTF8(response);
			 System.out.println(doc.toString());
			//在此处理具体的文件地址,解析出视频下载地址
			 getFileUrl(doc);
		}
		
		 //	 Document docHttps=clientforHttps.getDocUTF8(client.proxyPostUrl(url,"199.200.120.36",8089,list));
//		 Document doc=client.getDocUTF8(client.noProxyGetUrl(url));
	
	}
	/**
	 * @param doc
	 * @throws CloneNotSupportedException 
	 * @throws IOException 
	 */
	public static void getFileUrl(Document doc) throws IOException, CloneNotSupportedException{
		//vedio http://91p.vido.ws/getfile.php?VID=8297&mp4=0&seccode=4455c308e748341a1f232bb67c557044&max_vid=83997
		System.out.println(doc.toString());
		//解析doc 获得参数 VID,mp4,seccode,max_vid
		//获得下载文件临时url seccode seccode随着时间变化 会失效
		String downLoadUrl=getDownLoadUrl(doc.toString());
		System.out.println("下载地址:"+downLoadUrl);
		//下载地址:http://50.7.73.90//dl//7cc1eb5db30e98a62097618350301783/53f2b02d//91porn/mp43/83954.mp4
		}
	
	/**解析参数返回获得视频源地址的链接
	 * @return
	 * @throws CloneNotSupportedException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static String getDownLoadUrl(String content) throws ClientProtocolException, IOException, CloneNotSupportedException{
		Map map=new HashMap();
		String file="";
		String max_vid="";
		String seccode="";
		String mp4="";
		String downUrl="";
		if(content.contains("seccode")){
			//截取包含参数的临时字符串
			String temp=content.substring(content.indexOf("so.addParam('allowscriptaccess'"),content.indexOf("so.write('mediaspace');")).replace("\n", "");
			String [] arr=temp.split(";");
			for(int i=0;i<arr.length;i++){
				if(arr[i].contains("file")){
					 file=arr[i].replaceAll("(?:so.addVariable|\\(|file|,|'|\"|\\))", "");// 替换掉不相关的
				}else if(arr[i].contains("max_vid")){
					 max_vid=arr[i].replaceAll("(?:so.addVariable|\\(|max_vid|,|'|\"|\\))", "");// 替换掉不相关的
				}else if(arr[i].contains("seccode")){
					 seccode=arr[i].replaceAll("(?:so.addVariable|\\(|seccode|,|'|\"|\\))", "");// 替换掉不相关的
				}else if(arr[i].contains("mp4")){
					 mp4=arr[i].replaceAll("(?:so.addVariable|\\(|mp4|,|'|\"|\\))", "");// 替换掉不相关的
				}
			}
			map.put("VID", file);
			map.put("max_vid", max_vid);
			map.put("seccode", seccode);
			map.put("mp4", mp4);
			
		}
		String tempUrl=vedioFileUrl+"VID="+map.get("VID")+"&seccode="+map.get("seccode")+"&mp4="+mp4+"&max_vid="+map.get("max_vid");
		Document tempdoc=client.getDocUTF8(client.noProxyGetUrl(tempUrl));
 		System.out.println(tempdoc.toString());
		if (tempdoc.text() != null && tempdoc.text().contains("file=http://")) {
			//过滤出真正的下载地址
			 downUrl=tempdoc.text().substring(
					tempdoc.text().indexOf("﻿file=") + 6,
					tempdoc.text().indexOf("&domainUrl"));
		//http://50.7.69.10//dl//8bb3e2c39d328430db7f9811a06fe8dd/53f1b5b5//91porn/mp43/83954.mp4
		//http://107.155.123.34//dl//81b0256e55efc72fd8d4c5d1889b1684/53f1bc61//91porn/mp43/83954.mp4
		}
		return downUrl;
	}
	
	/**获得某一页下的视频的标题,图片预览,视频链接地址,视频时长
	 * @param url
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws CloneNotSupportedException
	 */
	public static void getPageVideos(String url) throws ClientProtocolException, IOException, CloneNotSupportedException{
		Document doc=client.getDocUTF8(client.noProxyGetUrl(url));
		Elements videobox=doc.select("div[class*=listchannel]");
		System.out.println(url+"视频总数:"+videobox.size()+"个");
		//拿去视频预览图片
		for(Element e:videobox){
			String title=e.select("div[class*=imagechannel]>a>img").attr("title");//标题
			String preImgSrc=e.select("div[class*=imagechannel]>a>img").attr("src");//获得预览图片链接
			String vedioUrl=e.select("div[class*=imagechannel]>a").attr("abs:href");//视频链接地址
			String infotime=e.text().substring(e.text().indexOf("时长:")-3,e.text().indexOf(" 添加时间"));		//获得时长
	
			System.out.println(title+preImgSrc+vedioUrl+infotime);
			
			//获得视频链接
		}
		System.out.println(videobox.toString());
		
	}
}
