package com.br.dong.httpclientTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.br.dong.file.FileOperate;
import com.br.dong.utils.DateUtil;


/**
 * @author  hexd
 * 创建时间：2014-7-24 下午12:55:45
 * 类说明
 * firstroom信息抓取
 */
public class JfirstroomTask {
	//资源购买一区
	private static String getResUrl="http://www.firstgongyu.com/forum.php?mod=forumdisplay&fid=46&page=";
	//资源一区页数计数器
	private static int i=1;
	//资源一区最大页数
	private static int max=99999;
	//主页页数计数器
	private static int maini=1;
	//主页最大页数
	private static int mainmax=99999;
	//首页资源查看
	private static String homeUrl="http://www.firstgongyu.com/forum.php?mod=forumdisplay&fid=2&mobile=no&page=";
	//登录post url
	private static String loginPostUrl="http://www.firstgongyu.com/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes&inajax=1";
	//一区某页购买了资源的
	private static String oneurl="http://www.firstgongyu.com/thread-435-1-6.html?mobile=no";
	private static String twourl="http://www.firstgongyu.com/thread-8649-1-1.html?mobile=no";
	private static String threeurl="http://www.firstgongyu.com/thread-1231-1-1.html?mobile=no";
	//某购买页面的详细信息
	private static String onedetailUrl="http://www.firstgongyu.com/thread-8649-1-1.html";
	//目录
	private static FileOperate fo=new FileOperate();
	//网站的前缀地址
	private static String abs="http://www.firstgongyu.com/";
	private static CrawlerUtil client=new CrawlerUtil();
	//网站加好友url
	private static String addFriend="http://www.firstgongyu.com/home.php?mod=spacecp&ac=friend&op=add&uid=14001&inajax=1";
	//登录并且进入购买一区
	public static void login(String refUrl) throws KeyManagementException, NoSuchAlgorithmException, CloneNotSupportedException, ClientProtocolException, IOException{
		//创建client
		client.clientCreate("http","www.firstgongyu.com" , refUrl);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("username", "he7253997"));
		list.add(new BasicNameValuePair("fastloginfield", "username"));
		list.add(new BasicNameValuePair("password", "95b004"));
		list.add(new BasicNameValuePair("cookietime", "2592000"));
		HttpResponse responsepost=client.post(loginPostUrl, client.produceEntity(list));
		Document doc=client.getDocUTF8(responsepost);
		//System.out.println(doc.toString());
		//保持登录状态get访问的数据
	}
	//获得信息
	public static Document getinfo(String geturl) throws ClientProtocolException, IOException, CloneNotSupportedException{
		Document doc2=client.getDocUTF8(client.noProxyGetUrl(geturl));
//		  System.out.println(doc2.toString());
		return doc2;
	}
	/**资源购买一区验证贴链接采集
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws ClientProtocolException
	 * @throws CloneNotSupportedException
	 * @throws IOException
	 */
	public static void getOneResource() throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, CloneNotSupportedException, IOException{
		 fo.newFolder("f://firstroom" );
		 String file="f://firstroom//resone.txt";
		 fo.newFile(file, "***开始创建资源购买一区购买帖链接"+DateUtil.getStrOfDateTime()+"***");
		 while(i<max){
			 Document doc=getinfo(getResUrl+i);
			 //System.out.println(getResUrl+i);
			 Element pgElement=doc.select("div[class$=pg]").select("a[class$=last]").first();
			 if(max==99999){
				 max=Integer.parseInt(pgElement.text().substring(3));//最大页数
			 }
			 //System.out.println(pgElement.text().substring(3));
			 Elements xjs=doc.select("div[class$=bm_c]");//购买链接的所有内容
				 //循环
				 for(Element e:xjs){
					 String tt=e.select("a").first().text()+abs+e.select("a").first().attr("href").replace("mobile=yes", "mobile=no")+"\t"+e.select("span").text();
//					 System.out.println(tt);
					 fo.appendMethodB(file, tt);
				 }
				 i++;
		 }
	}

	public static void getMain() throws ClientProtocolException, IOException, CloneNotSupportedException{
		String file="f://firstroom//main.txt";
		System.out.println("创建所有购买贴");
		//主页联系方式采集购买贴 -主页中的标题含有售价xx的字段 表示此帖子有联系方式出售
//	 System.out.println(doc.toString());
	 while(maini<mainmax){
		 Document doc= client.getDocUTF8(client.noProxyGetUrl(homeUrl+maini));
		// System.out.println(doc.toString());
		 Element pgElement=doc.select("div[class$=pg]").select("a[class$=last]").first();
		 //最大页数初始化
		 if(mainmax==99999){
			 mainmax=Integer.parseInt(pgElement.text().substring(4));//最大页数
		 }
	 //System.out.println(homeUrl+maini);
	 Elements xjs=doc.select("tbody[id*=normalthread]>tr");//帖子的div
	 //循环
	 for(Element e:xjs){
		// System.out.println(e.toString());
		 //只拿包含出售联系方式的帖子
		 if(e.text().contains("售价")){
			String tt="地区："
						+ e.select("th>em").select("a").text() + " 标题："
						+ e.select("a[class$=xst]").text() +" 链接地址:"
						+e.select("a[class$=xst]").attr("abs:href")+"**发布时间："
						+ e.select("td[class$=by]").select("span").text();
//			 System.out.println(tt);
			 fo.appendMethodB(file, tt);
		 }
	 }
//
	maini++;
	 }
	}
	/**详细信息查看
	 * @param url
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws CloneNotSupportedException
	 */
	public static void getInfoTest(String url) throws ClientProtocolException, IOException, CloneNotSupportedException{
		Document doc=client.getDocUTF8(client.noProxyGetUrl(url));
//		 System.out.println(doc.toString());
		 //盘符 f://firstroom//
		 String file="f://firstroom//";
		//拿去xj区域与标题 作为创建txt的目录位置与文件标题
		Elements scope=doc.select("h1[class$=ts]");
//		System.out.println("----");
		String place=scope.select(":eq(0)").text()==""?"其他区":scope.select(":eq(0)").text();
		String title=(scope.select(":eq(1)").text()==""||scope.select(":eq(1)").text()==null)?place:scope.select(":eq(1)").text();
//		System.out.println("--place"+place+"--"+title);
		//获得内容
		Element content=doc.select("div[class*=t_f]").first();
 		System.out.println(content.toString());

	}


	/**
	 * 获得某个帖子的详细信息
	 * @throws CloneNotSupportedException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static void  getInfoDeatil(String url) throws ClientProtocolException, IOException, CloneNotSupportedException{
		Document doc=client.getDocUTF8(client.noProxyGetUrl(url));
//		 System.out.println(doc.toString());
		 //盘符 f://firstroom//
		 String file="f://firstroom//";
		//拿去xj区域与标题 作为创建txt的目录位置与文件标题
		Elements scope=doc.select("h1[class$=ts]");
//		System.out.println("----");
		String place=scope.select(":eq(0)").text()==""?"其他区":scope.select(":eq(0)").text();
		String title=(scope.select(":eq(1)").text()==""||scope.select(":eq(1)").text()==null)?place:scope.select(":eq(1)").text();
//		System.out.println("--place"+place+"--"+title);
		//获得内容
		Element content=doc.select("div[class*=t_f]").first();
//		System.out.println(content.toString());
		fo.newFolder(file+place);
		String tips="<p>照片请在联网状态下，点击viewimg查看</p>";
	    fo.appendMehtodByGBK(file+place+"//"+title+".html", content.toString()+tips);

	}
	public static void readMainTxt(String filename) throws CloneNotSupportedException{
		System.out.println("read.."+filename);
		BufferedReader in=null;
		try{
			 in=new BufferedReader(new FileReader(filename));
			String line;
			try {
				while((line=in.readLine())!=null){
//					System.out.println(""+line);
					//url起始
					int urlstart=line.indexOf("http");
					int urlend=line.indexOf("**");
 				System.out.println(line.substring(urlstart, urlend)+"?mobile=no");
		 		getInfoDeatil(line.substring(urlstart, urlend)+"?mobile=no");
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch(FileNotFoundException e){
			System.out.println("没有找到文件，请确保文件目录");
//			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, CloneNotSupportedException, ClientProtocolException, IOException {
		 //登录
		   login(getResUrl);
		//资源购买一区联系方式购买贴链接采集 F:\firstroom\resone.txt
//		 getOneResource();
		//首页联系贴采集  F:\firstroom\main.txt
		  getMain();
		  //读取main.txt循环创建详细页面
//		  readMainTxt("f://firstroom//main.txt");
//		   getInfoTest("http://www.firstgongyu.com/thread-4689-1-1.html?mobile=no");
	}
}
