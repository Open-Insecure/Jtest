package com.br.dong.httpclientTest._1024gc_2015_04_10;

import com.br.dong.file.FileOperate;
import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.utils.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-4-30
 * Time: 15:19
 * 部署在linux上的采集下载程序
 * 使用数据库中的mysql表记录采集信息
 */
public class GcDownloadTaskForLinux extends Thread{
    private static CrawlerUtil client=new CrawlerUtil();     //采集client
    private static CrawlerUtil downClient=new CrawlerUtil(); //下载client
    private static String baseUrl="http://cb.1024gc.info/bbs/";//网站的根url
    private static String downUrl="http://down.happytogether2015.com/freeone/down.php";  //下载种子文件的post根请求
    private static String www1Url="http://www1.happytogether2015.com/freeone/down.php";  //下载种子文件的post根请求
    private static String needReplace="(?:/|<|>|:|\\?|\\||\\*|\"|\\(|\\)|)";  //需要替换的字符,这些字符windows文件名不能出现
    private final static int BUFFER = 1024;
    private static String wpUrl="www.1024wp.com_";//重命名种子文件前缀
    private static String skydriveDownloadPre="http://www.1024wp.com/search/downloadPage?id=";//网盘下载地址的前缀
    private  String name=""; //线程的名字
    private  String url=""; //线程所对应采集的板块url


    /**
     * 构造方法
     * @param name 线程名 作为区分采集的种子的分类的类别
     * @param url  目标url
     */
    public GcDownloadTaskForLinux(String name,String url) throws NoSuchAlgorithmException, KeyManagementException {
        super(name);
        this.name=name;
        this.url=url;
        client.clientCreate("http", "cb.1024gc.info", "http://cb.1024gc.info/bbs/");
        downClient.clientCreate("http", "down.happytogether2015.com", "http://cb.1024gc.info/bbs/");
    }


    //重写run方法
    //特别注意 线程自己要单独用的变量不要写成static 否则变量被多个线程共享了
    public void run() {
        try {
            preParse();
//            throw new Exception(); //模拟异常抛    出
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("thread[" + name + "]fall");
            log( "thread[" + name + "]fall");
        }
    }
    public void log(String content){
        FileOperate.appendMethodB(GcForLinux.logPath,DateUtil.getStrOfDateTime()+":"+content);
    }

    /**
     * 检查数据库中是否已经采集该帖子
     * @param resourceUrl
     * @return
     */
    public synchronized Boolean checkResourceUrl(String resourceUrl){
          List list=JDBCSkydriveUtil.checkResourceUrl(resourceUrl);
          if(list==null||list.size()==0) return true;//如果库中不存在该帖子记录
          return false;//如果库中存在该帖子记录
    }
    /**
     * 采集前的预处理
     */
    public  void preParse() throws IOException, CloneNotSupportedException {
        HttpResponse httpResponse=client.noProxyGetUrl(url);
        if(httpResponse!=null){
            Document document=client.getDocUTF8(httpResponse);
//          System.out.println(document.toString());//打印带采集页面
            parseInfos(document); //解析页面模块
        }
    }
    /**
     * 解析出板块的信息 -标题与超链接地址
     * @param document
     */
    public   void parseInfos(Document document) throws IOException, CloneNotSupportedException {
        if(document==null) return;
        System.out.println("thread[" + name + "]start:");
        Elements elements=document.select("td[class=f_title]").select("a"); //获得标题与超链接地址
        for(Element element:elements){
            if(element.text().length()>=5){//为了过滤掉分页的
                String href=baseUrl+element.attr("href");//超链接
                String title=element.text().replaceAll(needReplace, "");; //帖子title 采集的板块标题
                System.out.println("thread[" + name + "] collect[" + title + "]href:" + href);
                //解析href 并且存入存入数据库
                if(!(title.contains("最新地址")||title.contains("监管力度")||title.contains("有声小说"))){
                    if(checkResourceUrl(href)) {   //检查该链接是否已经被采集入库
                        parseDetail(title,href) ;
                    }else{
                        System.out.println("href exist:"+href);
                        log("href exist:" + href);
                    }
                }
            }
        }
    }
    /**
     * 记录采集内容与下载链接
     * @param contentTxtPath  txt文档目录
     * @param downloadUrlTxtPath  txt文档目录
     * @param content  帖子内容
     * @param downloadUrl 下载链接
     */
    public void recorded(String contentTxtPath,String downloadUrlTxtPath,String content,String downloadUrl) throws Exception{
        FileOperate.appendMethodB(contentTxtPath, content);
        FileOperate.appendMethodB(downloadUrlTxtPath, downloadUrl);
    }
    /**
     * 解析帖子详细页
     * @param title 帖子名字
     * @param href 帖子链接
     */
    public   void parseDetail(String title,String href) throws IOException, CloneNotSupportedException {
        HttpResponse httpResponse=client.noProxyGetUrl(href);
        if(httpResponse==null) return;
        Document document = client.getDocUTF8(httpResponse);
        Element element=document.select("div[id*=message]").first() ; //帖子详细信息的html代码
        Elements as=element.select("a"); //获得下载链接的a标签
        String aLinks=""; //存储下载链接地址
        String imgUrls="";//储存图片链接地址
        //循环获得图片地址
        for(Element imge:element.select("img")){
            String temp=imge.attr("src");
            if(!temp.contains("gif")){
                //如果不是gif图片，则进行采集
                imgUrls=imgUrls+temp+",";
            }
        }
        //循环获得下载链接地址
        for(int i=0;i<as.size();i++) {
            Element a = as.get(i);
            try {
//              donwload(a.text(),element.toString(),title,href,"" , i);  //依次下载种子文件
                donwload(a.text(),element.text().replace("【","\n【"),title,href,imgUrls , i);  //依次下载种子文件 为了适配发布程序
                aLinks = aLinks + a.text() + ",";
            } catch (Exception e) {
                log("href [" + a.text() + "] error!"+e.getMessage());
                e.printStackTrace();
                System.out.println("href [" + a.text() + "] error!");
            }
        }
    }
    /**
     * 下载
     * @param downloadUrl
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public   void donwload(String downloadUrl,String content,String title,String resourceUrl,String imgUrls,int i) throws Exception {
        if(!downloadUrl.startsWith("http://"))  return;
        System.out.println("start downloading:" + downloadUrl + "title:" + title);
        HttpResponse downResponse=downClient.noProxyGetUrl(downloadUrl);
        if(downResponse==null) return;
        Document document = downClient.getDocUTF8(downResponse);
        if (document == null) return; //获得下载页面信息
        Elements elements = document.select("input[type=hidden]");
//        String fileName=wpUrl+i;
        String fileName=title;
        String type=elements.select("input[id=type]").attr("value");
//        System.out.println(elements.toString());
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        //获取下载页面参数进行填充
        for(Element element:elements){
            list.add(new BasicNameValuePair(element.attr("name"), element.attr("value")));
        }
            String temp="";
            //判断下载链接对应的下载地址
            if(downloadUrl.contains("down")){
                temp=downUrl;
            } else{
                temp=www1Url;
            }
        //发送下载请求
        HttpResponse response= downClient.post(temp, downClient.produceEntity(list));
        String filesize="1";
        if(response!=null){
            Header[] headers = response.getHeaders("Content-Length");
            if (headers.length > 0)
            {	//获得要下载的文件的大小
               long contentLength = Long.valueOf(headers[0].getValue());
               filesize =contentLength/(1024*1024)+"";
               if(filesize.equals(0)) filesize="1";
            }
            //请求成功
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            //重新命名文件
            //重新命名bean的title
            //保存的种子文件路径
            File file=new File(GcForLinux.savePath+fileName+"."+type);
            FileOutputStream out = new FileOutputStream(file);
            byte[] b = new byte[BUFFER];
            int len = 0;
            while((len=in.read(b))!= -1){
                out.write(b,0,len);
            }
            in.close();
            out.close();
            System.out.println(file.getName()+"download, success!!");
            insertLog(baseUrl,"torrent",title+"."+type,content,resourceUrl,downloadUrl,skydriveDownloadPre,imgUrls,filesize);             //插入记录到数据库中
        }
    }

    /**
     * 插入记录到库中
     */
    public  void insertLog(String resourceSite, String type, String name, String content, String resourceUrl, String origDownloadUrl, String downloadUrl, String imgUrls,String filesize){
           FileBaseInfo fileBaseInfo=new FileBaseInfo(1,"admin", DateUtil.getStrOfDateTime(),name,filesize,"torrent");
          int fileId= JDBCSkydriveUtil.insertSkydriveFileInfo(fileBaseInfo);  //先插入到网盘的文件库中，获得该文件的返回值,再插入到采集日志中
            downloadUrl=downloadUrl+fileId;   //更改发帖内容中原始的下载地址为1024网盘的下载地址
           content=content.replace(origDownloadUrl,downloadUrl);//拼装1024网盘的下载地址downloadUrl
           GcBean  bean=new GcBean( resourceSite,type,name,content,resourceUrl,origDownloadUrl,downloadUrl,imgUrls, this.getName());
           JDBCSkydriveUtil.insertResource(bean) ;
    }
}
