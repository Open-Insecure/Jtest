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

import com.br.dong.file.FileOperate;
import com.br.dong.httpclientTest.DownloadTask;
import com.br.dong.httpclientTest.DownloadTaskListener;
import com.br.dong.scanner.ScannerCreatFile;
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
	private static String url="http://ch.u6p.co/v.php?next=watch&page=";
	//视频文件请求url 后跟参数需要拼装
	private static String vedioFileUrl="http://91p.vido.ws/getfile.php?";
    //默认查找页数
    private static int defaultPage=100;
    //多少条进行一次批量插入
    private static int batchNum=100;
	private static CrawlerUtil client=new CrawlerUtil();
    private static FileOperate fo=new FileOperate();
    //--下载程序使用的参数
    private static String saveFile="f:/vedios/default";
    private static String type="http";
    private static String hosturl="91p.vido.ws";
    private static String refUrl="http://91p.vido.ws/index.php";
    //想要下载第几页
    private static int wantPage=1;
	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException, CloneNotSupportedException {
        //--视频列表采集
//        getPaging(true);
        //------
        //文件保存路径
        saveFile= ScannerCreatFile.scannerMain(saveFile);
        fo.newFolderMuti(saveFile);
        wantPage=ScannerCreatFile.wantPage(wantPage);
        //分别起3个线程查找视频列表数据,并且进行下载任务DownLdTask
        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        //暂时一个主线程
        for(int i=wantPage;i<wantPage+2;i++){
             threadPool.execute(new PronThread("视频主线程["+i+"]",url+i));
        }
        //----
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
     * 在线拿去实时视频列表页面 数据库中的页面会过期
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public static List<VedioBean> getPageVideosOnline(String url) throws ClientProtocolException, IOException, CloneNotSupportedException, KeyManagementException, NoSuchAlgorithmException {
            client.clientCreatNoUrl("http");
            List<VedioBean> list=new ArrayList<VedioBean>();
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
        }
        return list;
            //System.out.println(videobox.toString());
    }


	/**
	 * 获取分页信息,并且逐页采集视频信息 插入数据库
     * wantMaxPage true 则拿去最大页数    false 则拿去默认的页数
     *
	 */
	public static void getPaging(Boolean wantMaxPage){
        try {
            client.clientCreatNoUrl("http");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (KeyManagementException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
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
            }catch (Exception e){
                e.printStackTrace();
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
	 *
	 * @throws CloneNotSupportedException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void  getInfoDeatilProxy(DownLoadBean bean) throws ClientProtocolException, IOException, CloneNotSupportedException{
        try {
            client.clientCreate("http","91p.vido.ws" , "http://91p.vido.ws/index.php");
        } catch (KeyManagementException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        //使用代理方式获得
        System.out.println(bean.getThread().getName() + "正在连接代理" + bean.getProxy().toString());
        List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("session_language", "cn_CN"));
        //代理post访问视频页面
		HttpResponse response=client.proxyPostUrl(bean.getVedioUrl(),bean.getProxy().getIp(),bean.getProxy().getPort(),list);
		Document doc=null;
		if(response==null){
			//连接代理失败，换下一个代理
            System.out.println(bean.getThread().getName()+"连接代理:"+bean.getProxy().toString()+"失败..");
            //bean重新设置代理
            bean.resetProxy();
            getInfoDeatilProxy(bean);
            //代理失败以后 删除代理表中的代理
		}else{//连接代理成功 解析url
			 doc=client.getDocUTF8(response);
//			 System.out.println(doc.toString());
            if(!doc.toString().contains("游客")){
                //设置doc到bean中
                bean.setDoc(doc);
                //如果代理ip没有超过游客访问次数
                System.out.println(bean.getThread().getName()+"访问的代理"+bean.getProxy().toString()+"可用,准备下载视频..");
                //解析并下载视频
                downLoad(bean);
            }else{
                //超过访问次数
                bean.resetProxy();//更换代理
                System.out.println(bean.getThread().getName()+"访问的当前代理"+bean.getProxy().toString()+"超过访问次数,准备更换代理..");
                getInfoDeatilProxy(bean);
            }
		}
		
	}
	/**解析并下载视频 调用getDownLoadUrl()方法解析下载视频
     * 解析bean中的doc 获得参数 VID,mp4,seccode,max_vid
     *  seccode seccode随着时间变化 会失效
	 * @throws CloneNotSupportedException
	 * @throws IOException 
	 */
	public static void downLoad(DownLoadBean bean) throws IOException, CloneNotSupportedException{
        System.out.println(bean.getThread().getName()+"访问视频页面:"+bean.getVedioUrl());
        //解析bean
        String downLoadUrl=getDownLoadUrl(bean);
        if(!downLoadUrl.contains("error")){
            System.out.println(bean.getThread().getName()+"开始下载:"+downLoadUrl);
            DownloadTask downloadTask=new DownloadTask(downLoadUrl,saveFile+"/"+bean.getFileName(),10,type,hosturl,refUrl);
            //下载地址:http://50.7.73.90//dl//7cc1eb5db30e98a62097618350301783/53f2b02d//91porn/mp43/83954.mp4
            //调用开始下载
            try {
                downloadTask.addDownloadTaskListener(new DownloadTaskListener() {
                    //实现接口
                    @Override
                    public void downloadCompleted() {//下载完成
                        // TODO Auto-generated method stub
//                        System.out.print("download completed");
                    }
                });
                //开始下载
                downloadTask.startDown(client);
            } catch (Exception e) {
                System.out.println(bean.getThread().getName() + "startDown[下载视频报错]");
            }
        }else{
            //处理访问拿到下载地址页面失败的错误
            System.out.println(downLoadUrl);
            bean.resetProxy();
            getInfoDeatilProxy(bean);
        }

    }
	
	/**解析参数返回获得视频源地址的链接
	 * @return
	 * @throws CloneNotSupportedException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static String getDownLoadUrl(DownLoadBean bean) throws ClientProtocolException, IOException, CloneNotSupportedException{
		Map map=new HashMap();
		String file="";
		String max_vid="";
		String seccode="";
		String mp4="";
		String downUrl="";
        //视频页面的html信息
        String content=bean.getDoc().toString();
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

		} else{
            System.out.println("["+content+"]");
        }
        //拿到包含下载地址的页面
		String tempUrl=vedioFileUrl+"VID="+map.get("VID")+"&seccode="+map.get("seccode")+"&mp4="+mp4+"&max_vid="+map.get("max_vid");
		//--异常 跳转的下载地址
         HttpResponse response=client.noProxyGetUrl(tempUrl) ;
//        HttpResponse response=client.proxyGetUrl(tempUrl, bean.getProxy().getIp(), bean.getProxy().getPort()) ;
        if(response!=null){
            //解析下载地址页面
            Document tempdoc=client.getDocUTF8(response);
//            System.out.println(tempdoc.toString());
            //包含下载地址
            if (tempdoc.text() != null && tempdoc.text().contains("file=http://")) {
                //过滤出真正的下载地址
                downUrl=tempdoc.text().substring(
                        tempdoc.text().indexOf("﻿file=") + 6,
                        tempdoc.text().indexOf("&domainUrl"));
                //http://50.7.69.10//dl//8bb3e2c39d328430db7f9811a06fe8dd/53f1b5b5//91porn/mp43/83954.mp4
                //http://107.155.123.34//dl//81b0256e55efc72fd8d4c5d1889b1684/53f1bc61//91porn/mp43/83954.mp4
                return downUrl;
            } else{
                String msg=bean.getThread().getName()+"error[解析下载地址出错]"+tempdoc.toString()+"准备更换代理";
                return msg;
            }

        } else{
           //解析下载地址出错!!!!!!!这里还没处理
            String msg=bean.getThread().getName()+"error[访问下载地址获得页面出错]"+"准备更换代理";
            return msg;

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

    PronThread(String name,String url) {
        super(name);
        this.url=url;
    }
    List<VedioBean> list;
    String url;//视频列表链接
    @Override
    public void run(){
        try {
            list=PornTest.getPageVideosOnline(url);
        } catch (IOException e) {
        } catch (CloneNotSupportedException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (KeyManagementException e) {
        }
        //不从数据库拿了 链接会过期
        for(int i=0;i<list.size();i++){
            //设置主线程的
            this.setName(this.getName()+"-"+i);
            String fileName=list.get(i).getTitle()+".mp4";
            String vedioUrl=list.get(i).getVedioUrl();
            //装配下载参数bean
            DownLoadBean bean=new DownLoadBean();
            bean.setFileName(fileName);
            bean.setVedioUrl(vedioUrl);
            bean.setProxy(JdbcUtil.getProxy());
            bean.setThread(this);
            try {
                //线程开始分析视频页面获取下载地址
                PornTest.getInfoDeatilProxy(bean);
            }  catch (CloneNotSupportedException e) {
            }  catch (HttpHostConnectException e){
            } catch (ClientProtocolException e) {
            } catch (IOException e) {
            } catch (IllegalStateException e){
                System.out.println("IllegalStateException..");
            }
        }
    }
}
