package com.br.dong.httpclientTest.porn;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.br.dong.httpclientTest.DownloadTask;
import com.br.dong.httpclientTest.DownloadTaskListener;
import com.br.dong.utils.DateUtil;
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
    //默认查找页数
    private static int defaultPage=100;
    //多少条进行一次批量插入
    private static int batchNum=100;
	private static CrawlerUtil client=new CrawlerUtil();
    //
    private static int wantData=2;
    //--下载程序使用的参数
    private static String saveFile="F:\\test_jar\\";
    private static String type="http";
    private static String hosturl="91p.vido.ws";
    private static String refUrl="http://91p.vido.ws/index.php";

	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException, CloneNotSupportedException {
		//创建http请求的client
//		client.clientCreate("http","91p.vido.ws" , "http://91p.vido.ws/index.php");
//    	getPaging(false);
//     	//vedio http://91p.vido.ws/getfile.php?VID=8297&mp4=0&seccode=4455c308e748341a1f232bb67c557044&max_vid=83997
//    	getInfoDeatilProxy("http://91p.vido.ws/view_video.php?viewkey=65a5320bf0dcd0243c54&page=2&viewtype=basic&category=mr",getProxy());
//         VedioBean vedio=JdbcUtil.getVedioInfo();
//         System.out.println(vedio.toString());

        //分别起3个线程查找视频列表数据,并且进行下载任务DownLoadTask
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        for(int i=2;i<5;i++){
            try {
                threadPool.execute(new PronThread(""+i,i*wantData,wantData));
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
//        System.out.println("**[全部下载完成]**");
//        threadPool.shutdown();// 任务执行完毕，关闭线程池
     }
    /**
     * 获得一个代理
     * */
    public static ProxyBean getProxy(){
         return JdbcUtil.getProxy();
    }
    /**
     * 拿去post参数，获得中文返回页面
     * */
    public static List<NameValuePair> getPostParmList(){
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("session_language", "cn_CN"));
        return list;
    }

	/**
	 * 获取分页信息,并且逐页采集视频信息 插入数据库
     * wantMaxPage true 则拿去最大页数    false 则拿去默认的页数
     *
	 */
	public static void getPaging(Boolean wantMaxPage){

		int maxpage=defaultPage;

		//第一次采集第一页的
		HttpResponse response;
        //查找最大页数
        if(wantMaxPage){
            try {
                response = client.post(url+"1", client.produceEntity(getPostParmList()));
                Document doc= client.getDocUTF8(response);
//	 		System.out.println(doc.toString());
                //拿到视频分页
                Elements maxpageElement=doc.select("div[class*=pagingnav]>a:eq(6)");
                maxpage=Integer.parseInt(maxpageElement.text());
            } catch (CloneNotSupportedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }catch(NumberFormatException e){
                System.out.println("拿去最大页数失败,自动填充为50");
            }
        }

     	System.out.println("获得最大页数:"+maxpage);
        List<VedioBean> list=new ArrayList<VedioBean>();
		//进行视频页面采集  实际最大页数要+1
     	for(int i=1;i<=maxpage+1;i++){
     		//在此进行视频页面的采集
             try {
                 getPageVideos(url+i,list);
             } catch (IOException e) {
                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             } catch (CloneNotSupportedException e) {
                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             }
         }
	}
	
	/**
	 * 获得某个帖子的详细信息
	 * @throws CloneNotSupportedException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void  getInfoDeatilProxy(String threadName,String url,ProxyBean proxy,String fileName) throws ClientProtocolException, IOException, CloneNotSupportedException{
        try {
            client.clientCreate("http","91p.vido.ws" , "http://91p.vido.ws/index.php");
        } catch (KeyManagementException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        //使用代理方式获得
        System.out.println(threadName+"正在连接代理"+proxy.getIp()+":"+proxy.getPort());
        List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("session_language", "cn_CN"));
		HttpResponse response=client.proxyPostUrl(url,proxy.getIp(),proxy.getPort(),list);
		Document doc=null;
		if(response==null){
			//连接代理失败，换下一个代理
            getInfoDeatilProxy(threadName,url,getProxy(),fileName);
            //代理失败以后 删除代理表中的代理
		}else{//连接代理成功 解析url
			 doc=client.getDocUTF8(response);
//			 System.out.println(doc.toString());
            if(!doc.toString().contains("游客")){
                //如果代理ip没有超过游客访问次数
                System.out.println(threadName+"访问的代理可用,准备下载视频..");
                //下载视频
                downLoad(doc, fileName);
            }else{
                //超过访问次数
                System.out.println(threadName+"访问的当前代理超过访问次数,准备更换代理..");
                getInfoDeatilProxy(threadName,url,getProxy(),fileName);
            }
			//在此处理具体的文件地址,解析出视频下载地址
		}
		
		 //	 Document docHttps=clientforHttps.getDocUTF8(client.proxyPostUrl(url,"199.200.120.36",8089,list));
//		 Document doc=client.getDocUTF8(client.noProxyGetUrl(url));
	
	}
	/**
	 * @param doc
	 * @throws CloneNotSupportedException 
	 * @throws IOException 
	 */
	public static void downLoad(Document doc,String fileName) throws IOException, CloneNotSupportedException{
		//vedio http://91p.vido.ws/getfile.php?VID=8297&mp4=0&seccode=4455c308e748341a1f232bb67c557044&max_vid=83997
//		System.out.println(doc.toString());
		//解析doc 获得参数 VID,mp4,seccode,max_vid
		//获得下载文件临时url seccode seccode随着时间变化 会失效
		String downLoadUrl=getDownLoadUrl(doc.toString());
        if(!"".equals(downLoadUrl)){
            System.out.println("下载地址:"+downLoadUrl);
            //解析下载地址的名字
            DownloadTask downloadTask=new DownloadTask(downLoadUrl,saveFile+fileName,10,type,hosturl,refUrl);
            //下载地址:http://50.7.73.90//dl//7cc1eb5db30e98a62097618350301783/53f2b02d//91porn/mp43/83954.mp4
            //调用开始下载
            try {

                downloadTask.addDownloadTaskListener(new DownloadTaskListener() {
                    //实现接口
                    @Override
                    public void downloadCompleted() {//下载完成
                        // TODO Auto-generated method stub
                        System.out.print("download completed");
                    }
                });
                //开始下载
                downloadTask.startDown(client);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //处理访问拿到下载地址页面失败的错误
            System.out.println("拿到file=..失败");
        }

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
		//--异常 跳转的下载地址
        HttpResponse response=client.noProxyGetUrl(tempUrl) ;
        if(response!=null){
            //解析下载地址正常
            Document tempdoc=client.getDocUTF8(response);
//            System.out.println(tempdoc.toString());
            if (tempdoc.text() != null && tempdoc.text().contains("file=http://")) {
                //过滤出真正的下载地址
                downUrl=tempdoc.text().substring(
                        tempdoc.text().indexOf("﻿file=") + 6,
                        tempdoc.text().indexOf("&domainUrl"));
                //http://50.7.69.10//dl//8bb3e2c39d328430db7f9811a06fe8dd/53f1b5b5//91porn/mp43/83954.mp4
                //http://107.155.123.34//dl//81b0256e55efc72fd8d4c5d1889b1684/53f1bc61//91porn/mp43/83954.mp4
            }
            return downUrl;
        } else{
           //解析下载地址出错!!!!!!!这里还没处理
              return "";
        }

//
	}
	
	/**获得某一页下的视频的标题,图片预览,视频链接地址,视频时长
	 * @param url
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws CloneNotSupportedException
	 */

	public static void getPageVideos(String url,List<VedioBean> list) throws ClientProtocolException, IOException, CloneNotSupportedException{
		Document doc=client.getDocUTF8(client.post(url, client.produceEntity(getPostParmList())));
		Elements videobox=doc.select("div[class*=listchannel]");
		System.out.println(url+"视频总数:"+videobox.size()+"个");
		//拿去视频预览图片
		for(Element e:videobox){
			String title=e.select("div[class*=imagechannel]>a>img").attr("title");//标题
			String preImgSrc=e.select("div[class*=imagechannel]>a>img").attr("src");//获得预览图片链接
			String vedioUrl=e.select("div[class*=imagechannel]>a").attr("abs:href");//视频链接地址
			String infotime=e.text().substring(e.text().indexOf("时长:"),e.text().indexOf(" 添加时间"));//获得时长
            String updatetime= DateUtil.getStrOfDateMinute();
            System.out.println(title+preImgSrc+vedioUrl+infotime+updatetime);
            list.add(new VedioBean(title,preImgSrc,vedioUrl,infotime,updatetime,0));

			if(list.size()>batchNum){
                //清空list
                JdbcUtil.insertVedioBatch(list);
                list.clear();
            }
		}
		 //System.out.println(videobox.toString());
	}
}
/**
 * pron线程
 * 0-20
 * 20-20
 * 40-20
 *
 */

class PronThread extends Thread{

    PronThread(String name,int start,int count) {
        super(name);
        this.start=start;
        this.count=count;
    }
    int start;//视频起始下载标志
    int count; //下载视频的个数
    @Override
    public void run(){
        System.out.println("视频线程"+getName()+"正在运行，目标下载"+count+"个视频");
        List list= JdbcUtil.getVedios(start,count);//从0开始查找20条数据
        for(int i=0;i<list.size();i++){
            Map map= (Map) list.get(i);
            String fileName= (String) map.get("title")+".mp4";
            String vedioUrl= (String) map.get("vedioUrl");
            //
            try {
                PornTest.getInfoDeatilProxy("视频线程"+getName(),vedioUrl,PornTest.getProxy(),fileName);

            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
