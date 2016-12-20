package com.br.dong.httpclientTest._1024gc_2015_04_10;

import com.br.dong.file.FileOperate;
import com.br.dong.httpclientTest.CrawlerUtil;
import org.apache.commons.io.FileUtils;
import org.apache.http.*;
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
 * Date: 2015-4-19
 * Time: 12:48
 *  http://cb.1024gc.info/bbs/ 1024核工厂的下载线程
 */
public class GcDownloadTask extends Thread{
    private static CrawlerUtil client=new CrawlerUtil();     //采集client
    private static CrawlerUtil downClient=new CrawlerUtil(); //下载client
    private static String baseUrl="http://cb.1024gc.info/bbs/";//网站的根url
    private static String downUrl="http://down.happytogether2015.com/freeone/down.php";  //下载种子文件的post根请求
    private static String www1Url="http://www1.happytogether2015.com/freeone/down.php";  //下载种子文件的post根请求
    private static String needReplace="(?:/|<|>|:|\\?|\\||\\*|\"|\\(|\\)|)";  //需要替换的字符,这些字符windows文件名不能出现
    private final static int BUFFER = 1024;
    private static String wpUrl="www.1024wp.com_";//重命名种子文件前缀
    private String name=""; //线程的名字
    private  String url=""; //线程所对应采集的板块url
    /**
     * 构造方法
     * @param name 线程名
     * @param url  目标url
     */
    public GcDownloadTask(String name,String url) throws NoSuchAlgorithmException, KeyManagementException {
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
            System.out.println("线程["+name+"]失败");
        }
    }
    /**
     * 检查采集记录文件，查询当前标题的帖子是否已经采集过了
     * @param filePath
     * @param title
     * @return
     */
    public synchronized Boolean checkTitle(String filePath,String title){
        File file = new File(filePath);
        try {
            String content = FileUtils.readFileToString(file);
            if(content.contains(title)) return false;     //当已经采集过该帖子，则返回false
            return true;//返回true表示可以采集
        } catch (IOException e) {
            return false;
        }
    }
    /**
     * 采集前的预处理
     */
    public  void preParse() throws IOException, CloneNotSupportedException {
        System.out.println("创建线程["+name+"]...");
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
        System.out.println("线程["+name+"]开始采集:");
        Elements elements=document.select("td[class=f_title]").select("a"); //获得标题与超链接地址
        for(Element element:elements){
            if(element.text().length()>=5){//为了过滤掉分页的
                String href=baseUrl+element.attr("href");//超链接
                String title=element.text().replaceAll(needReplace, "");; //帖子title 采集的板块标题
                System.out.println("线程[" + name + "] 开始采集帖子[" + title + "]href:" + href);
                //解析href 并且存入存入数据库
                if(!(title.contains("最新地址")||title.contains("监管力度")||title.contains("有声小说"))){
                    if(checkLog(href)) {   //记录采集日志
                        parseDetail(title,href) ;
                    }

                }
            }
        }
    }

    /**
     * 合集类型的帖子解析
     * 由于合集类型帖子包含很多单独的帖子 需要单个解析
     * 先把合集的所有<a>标签的下载链接解析出来出来后存入库中，再单个下载
     * @param title
     * @param href
     */
    public   void parseCompilations(String title,String href) throws IOException, CloneNotSupportedException {
        HttpResponse httpResponse=client.noProxyGetUrl(href);
        Document document = client.getDocUTF8(httpResponse);
        Element element=document.select("div[id*=message]").first() ; //帖子详细信息的html代码
        Elements as=element.select("a"); //获得下载链接的a标签
        String aLinks=""; //存储下载链接地址
        //循环获得下载链接地址
        for(int i=0;i<as.size();i++){
            Element a=as.get(i);
            try{
                donwload(a.text(),title,i);  //依次下载种子文件
                aLinks=aLinks+a.text()+",";
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("下载链接["+a.text()+"] error!");
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
        FileOperate.appendMethodB(contentTxtPath,content);
        FileOperate.appendMethodB(downloadUrlTxtPath,downloadUrl);
    }
    /**
     * 记录日志
     */
    public  synchronized Boolean checkLog(String href){
        if(checkTitle(GcTest.logPath,href)){
            FileOperate.appendMethodB(GcTest.logPath,href);
            return true;
        }else{
            System.out.println("log中已经存在该记录");
            return false;
        }

    }
    /**
     * 解析帖子详细页
     * @param title 帖子名字
     * @param href 帖子链接
     */
    public   void parseDetail(String title,String href) throws IOException, CloneNotSupportedException {
        HttpResponse httpResponse=client.noProxyGetUrl(href);
        Document document = client.getDocUTF8(httpResponse);
        Element element=document.select("div[id*=message]").first() ; //帖子详细信息的html代码
        Elements as=element.select("a"); //获得下载链接的a标签
        String aLinks=""; //存储下载链接地址
        String detailPath=GcTest.todaySavePath+name+"/"+title+"/";
        //创建文件夹
        FileOperate.newFolderMuti(detailPath) ;
        //创建帖子txt记录文档
            //循环获得下载链接地址
            for(int i=0;i<as.size();i++) {
                Element a = as.get(i);
                try {
                    System.out.println("开始下载:" + a.text() + "第" + i + "个");
                    donwload(a.text(), title, i);  //依次下载种子文件
                    aLinks = aLinks + a.text() + ",";
                } catch (Exception e) {
                    System.out.println("下载链接[" + a.text() + "] error!");
                }
            }
       try{
           //记录帖子内容与采集地址分别到txt中
           recorded(detailPath+"conten.txt",detailPath+"links.txt",element.toString(),aLinks);
       }catch (Exception e){
           System.out.println("记录txt文档错误.");
       }

    }

    /**
     * 下载
     * @param downloadUrl
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public   void donwload(String downloadUrl,String title,int i) throws Exception {
        if(!downloadUrl.startsWith("http://"))  return;
        Document document = downClient.getDocUTF8(downClient.noProxyGetUrl(downloadUrl));
        if (document == null) return;
        Elements elements = document.select("input[type=hidden]");
        String fileName=wpUrl+i;
        String type=elements.select("input[id=type]").attr("value");
//        System.out.println(elements.toString());
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        //填充登录参数
        for(Element element:elements){
            list.add(new BasicNameValuePair(element.attr("name"), element.attr("value")));
        }
//        //检查数据库中是否已经有该帖子的记录
//            //没有相同记录，则进行下载种子与插入数据库
        try {
            String temp="";
            if(downloadUrl.contains("down")){
                temp=downUrl;
            } else{
                temp=www1Url;
            }
            HttpResponse response= downClient.post(temp, downClient.produceEntity(list));
//            int code=response.getStatusLine().getStatusCode();
//            String redirectURL = "";
//            if(code== 302){
//                redirectURL = response.getFirstHeader("Location").getValue();
//                System.out.println("redirectURL::"+redirectURL); //重定向地址
//            }
            //请求成功
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            //重新命名文件
            //重新命名bean的title
            //保存的种子文件路径
            File file=new File(GcTest.todaySavePath+name+"/"+title+"/"+fileName+"."+type);
            FileOutputStream out = new FileOutputStream(file);
            byte[] b = new byte[BUFFER];
            int len = 0;
            while((len=in.read(b))!= -1){
                out.write(b,0,len);
            }
            in.close();
            out.close();
            System.out.println(file.getName()+"download, success!!");
        } catch (SocketException e){

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
