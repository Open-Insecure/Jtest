package com.br.dong.httpclientTest.sis001;

import com.br.dong.file.FileOperate;
import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.JdbcUtil;
import com.br.dong.httpclientTest.porn.PronUI;
import com.br.dong.httpclientTest.porn.PronVideo;
import com.br.dong.utils.DateUtil;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-9-29
 * Time: 下午2:18
 * To change this template use File | Settings | File Templates.
 * 针对 http://38.103.161.188/forum/index.php 采集程序
 */
public class Sis001DownLoadTask extends Thread{
    //html后缀
    private static String html=".html";
    //需要替换的字符,这些字符windows文件名不能出现
    private static String needReplace="(?:/|<|>|:|\\?|\\||\\*|\"|\\(|\\)|)";
    //相对路径资源前加绝对路径
    private static String absPre="http://38.103.161.188/forum/";
    //要采集的版块的url
    private  String zoneurl="";
    //默认最大页数
    private static int maxPage=2;
    //存盘路径目录
    private   String folderpath="";
    //
    private String folderName="";
    private final static int BUFFER = 1024;
    //重写run方法
    //特别注意 线程自己要单独用的变量不要写成static 否则变量被多个线程共享了
    public void run() {
        init(zoneurl);
    }


    /**
     * 采集线程构造方法
     * @param name        线程名字,本地所存的文件夹名字
     *                    种子类的线程 Sis001DownLoadTask name以 bt_ 开头 对应数据库urls表中的floderName字段
     *                    url类的线程 Sis001DownLoadTask name以 url_ 开头 对应数据库urls表中的floderName字段
     *                    pic_no_download 在具体采集的时候针对为pic图片类型的采集
     * @param folderpath  线程采集的种子所存的文件夹路径
     * @param zoneurl     线程所采集的版块
     * @param folderName 对应torrents表中的temp字段 在上传的时候使用此字段来查找要上传的信息
     */
    public Sis001DownLoadTask(String name,String folderpath,String zoneurl,String folderName) {
        super(name);
//        System.out.println(name + "当前传入的文件路径：" + folderpath + "要采集的url:" + zoneurl);
        this.folderpath=folderpath;
        this.zoneurl=zoneurl;
        this.folderName=folderName;
    }

    /**
     *  采集线程构造方法
     * @param name
     * @param zoneurl    线程所采集的版块
     */
    public Sis001DownLoadTask(String name,String zoneurl){
        super(name);
        this.zoneurl=zoneurl;
    }


    /**
     * 采集程序初始启动
     * @param zoneurl 对应的要采集的分区的url
     *  根据线程name区分是种子线程 还是url采集网盘迅雷下载的线程
     */
    public  void init(String zoneurl){
            System.out.println(this.getName()+"初始化");
            //获取最大页数 暂时先采集30页数据 将近3个月的数据
//            getPages(zoneUrl+"1"+html);
            for(int i=1;i<=maxPage;i++){
                String p=zoneurl+i+html;
                System.out.println(this.getName()+"正在采集:"+p);
                //两种类型可以共用此方法进行列表数据分析
                listPageAnalyse(p);
            }
    }



    /**
     * 下载种子程序
     * @param bean
     */
    public  void downloadTest(SisTorrentBean bean){
        //检查数据库中是否已经有该帖子的记录
        List rows=JdbcUtil.checkSameUrl(bean.getUrl());
        if(rows==null||rows.size()==0){
            //没有相同记录，则进行下载种子与插入数据库
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(bean.getTorrentUrl());
            try {
                HttpResponse response= client.execute(httpGet);
                //请求成功
                HttpEntity entity = response.getEntity();
                InputStream in = entity.getContent();
                //重新命名文件
                String title=bean.getTitle().replaceAll(needReplace,"");
                //重新命名bean的title
                bean.setTitle(title+".torrent");
                //保存的种子文件路径
                File file=new File(folderpath+title+".torrent");
//            FileOutputStream out = new FileOutputStream(new File("F:\\test_jar\\bb.torrent"));
                FileOutputStream out = new FileOutputStream(file);
                byte[] b = new byte[BUFFER];
                int len = 0;
                while((len=in.read(b))!= -1){
                    out.write(b,0,len);
                }
                in.close();
                out.close();
                //在此插入到数据库中
                JdbcUtil.insertTorrent(bean);
//            System.out.println(bean.toString());
                System.out.println(file.getName()+"download, success!!");
            } catch (SocketException e){

            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                httpGet.abort();
            }

        }else{
            System.out.println(bean.getTitle()+"已经存在");
        }
}

    /**
     * 写入文件程序，并且插入记录到数据库中
     * 相对于不是种子文件来说的下载文件，数据表torrents中的torrenturl存放的是该条详细页面的url地址，用来判定是否重复
     * @param bean  其中title属于写入的txt文件的文件名，message属于写入的文件内容
     */
    public void writeFile(SisTorrentBean bean){
        //检查数据库中是否已经有该条下载记录
        List rows=JdbcUtil.checkSameUrl(bean.getUrl());
        //替换title文件名为windows下符合命名规则的文件名
        String title=bean.getTitle().replaceAll(needReplace,"");
        if(rows==null||rows.size()==0){
            //重新命名文件
            //重新命名bean的title
            bean.setTitle(title+".txt");
            //保存txt文件
            FileOperate.newFile(folderpath+title+".txt",bean.getMessage());
            //在此插入到数据库中
            JdbcUtil.insertTorrent(bean);
            System.out.println(title+"write success!");
        } else{
            System.out.println(title+"已经存在！");
        }
    }

    /**
     * 此方法用来直接写入采集的信息到数据库中
     * 比如采集外链美图信息，将外链地址全部写入到torrents表中的message字段中。在上传的时候直接使用message中的字段进行上传
     * 相对于不是种子文件来说的下载文件，数据表torrents中的torrenturl存放的是该条详细页面的url地址，用来判定是否重复
     * @param bean
     */
    public void saveData(SisTorrentBean bean){
        //检查数据库中是否已经有该条下载记录
        List rows=JdbcUtil.checkSameUrl(bean.getUrl());
        //替换title文件名为windows下符合命名规则的文件名
        String title=bean.getTitle().replaceAll(needReplace,"");
        if(rows==null||rows.size()==0){
            //重新命名文件
            //重新命名bean的title
            bean.setTitle(title);
            //在此插入到数据库中
            JdbcUtil.insertTorrent(bean);
        } else{
            System.out.println(title+"已经存在！");
        }
    }

    /**
     * 进行小说类型详细页面分析
     * @param bean
     */
    public void detailPageAnalyseTxt(SisTorrentBean bean){
        Document doc=null;
        try {
            HttpResponse response=Sis001Task.client.noProxyGetUrl(bean.getUrl());
            if(response==null){
                return ;
            }
            doc= Sis001Task.client.getDocGBK(response);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(doc!=null){
//            System.out.println("ddd"+doc.toString());
            try{
                //此处try一下 防止有些帖子解析读取报错
                StringBuffer sb=new StringBuffer();
                doc.select("div[id^=postmessage_]").first().select("table").remove();//清除掉不需要采集的
                doc.select("div[id^=postmessage_]").first().select("fieldset").remove();//清除掉不需要采集的
                String content=doc.select("div[id^=postmessage_]>div").first().html().replace("<br />","\r\n");//替换br标签为换行标记
                sb.append(content+"\r\n");
//                System.out.println(sb.toString());//打印测试
                //设置在写入到本地文件的message内容到bean中方便在writeFile方法中写入到本地文件
                bean.setMessage(sb.toString());
               //写入文件
                writeFile(bean);
            }catch (Exception e){
            }
        }
    }

    /**
     * 分析图片类详细页面
     * @param bean
     */
    public void detailPageAnalysePic(SisTorrentBean bean){
        Document doc=null;
        try {
            HttpResponse response=Sis001Task.client.noProxyGetUrl(bean.getUrl());
            if(response==null){
                return ;
            }
            doc= Sis001Task.client.getDocGBK(response);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(doc!=null){
//            System.out.println("ddd"+doc.toString());
           try{
               //此处try一下 防止有些帖子解析读取报错
               StringBuffer sb=new StringBuffer();
               String content=doc.select("div[id^=postmessage_]").first().text();
               sb.append(content+"\r\n");
               Elements pics=doc.select("div[id^=postmessage_]").first().select("img");//
               for(Element element:pics){
                   sb.append("[img]"+element.attr("src")+"[/img]\r\n");
               }
               //在此针对星梦奇缘合成区的图片在附件div下面专门进行处理
               if(folderName.contains("合成")){
                   Elements attachs=doc.select("dl[class=t_attachlist]").select("img");
//                   System.out.println("bb1"+attachs.toString());
                   for(Element e:attachs){
//                       System.out.println("bbbbbb"+absPre+e.attr("src"));
                         sb.append("[img]" + absPre + e.attr("href") + "[/img]\r\n");
                   }
               }
//               System.out.println("----"+sb.toString());//打印测试
               //设置在写入到本地文件的message内容到bean中方便在writeFile方法中写入到本地文件
               bean.setMessage(sb.toString());

               //保存信息到数据库中
//               saveData(bean);
               writeFile(bean);//改成也写入到本地中吧，为了统一
           }catch (Exception e){
           }
        }
    }
    /**
     * 分析网盘和迅雷详细页面
     * @param bean
     */
    public void detailPageAnalyseUrl(SisTorrentBean bean){
        Document doc=null;
        try {
            HttpResponse response=Sis001Task.client.noProxyGetUrl(bean.getUrl());
            if(response==null){
                return ;
            }
            doc= Sis001Task.client.getDocGBK(response);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(doc!=null){
            try{
                String message=doc.select("div[id^=postmessage_]").first().text().replace("【","\r\n【");
                String  picurl=doc.select("div[id^=postmessage_]").first().select("img").attr("src");//种子视频预览图片地址
                //设置bean的相关属性 替换下预览图片
                bean.setPicUrl(picurl);
                bean.setMessage(message);
                writeFile(bean);
            }catch (NullPointerException e){
                System.out.println("NullPointerException...");
            }

        }
    }

    /**
     * 种子详细页面分析
     * @param bean
     */
   public  void detailPageAnalyse(SisTorrentBean bean){
       Document doc=null;
       try {
           HttpResponse response=Sis001Task.client.noProxyGetUrl(bean.getUrl());
           if(response==null){
               return ;
           }
           doc= Sis001Task.client.getDocGBK(response);
       } catch (IOException e) {
           e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
       } catch (CloneNotSupportedException e) {
           e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
       }
       if(doc!=null){
          try{
              String message=doc.select("div[id^=postmessage_]").first().text().replace("【","\r\n【");
//              System.out.println("message.."+message);
              String  picurl=doc.select("div[id^=postmessage_]").first().select("img").attr("src");//种子视频预览图片地址
              String torrenDownLoadUrl=absPre+doc.select("dl[class^=t_attachlist]>dt").select("a:eq(2)").attr("href");//种子下载地址
              //设置bean的相关属性
              bean.setPicUrl(picurl);
              bean.setTorrentUrl(torrenDownLoadUrl);
              bean.setMessage(message);
              downloadTest(bean);
          }catch (NullPointerException e){
              System.out.println("NullPointerException...");
          }

       }
   }

    /**
     * 种子列表页面分析方法
     * @param targetUrl
     */
    public  void listPageAnalyse(String targetUrl){
        Document doc=null;
        try {
            HttpResponse response=Sis001Task.client.noProxyGetUrl(targetUrl);
            if(response!=null){
                doc= Sis001Task.client.getDocGBK(response);
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(doc!=null){
//            System.out.println(doc.toString());
            Elements elements= doc.select("tbody[id^=normalthread_]");
//            System.out.println(elements.toString()); //打印采集内容
            for(Element element:elements){
               String type= element.select("em:eq(1)>a").text();  //类型中文描述
               String time= element.select("em:eq(0)>a").text();  //更新时间
               String title=element.select("span[id^=thread_]>a").text();  //标题
               String url=absPre+element.select("span[id^=thread_]>a").attr("href");//该帖子的绝对链接地址
               String size=element.select("td[class=nums]").select(":eq(5)").text();   //视频大小和类型
               String updatetime= DateUtil.getCurrentDay();
             System.out.println(type+"|"+url+"|"+time+"|"+title+"|"+size);//打印采集信息
               //每一行的内容进入详细url进行下载操作 ,修改temp标志为文件夹名字的标志
               SisTorrentBean bean=new SisTorrentBean(this.getName(), type,title,url,size,time,updatetime,folderName);
               //进入详细,根据线程类型调用不同的处理方法
                typeAnalyse(bean);
            }
        }
    }

    /**
     * 根据不同的线程的name-即数据库中urls表中flodername字段打头来调用不同的具体页面采集方法
     */
    public void typeAnalyse(SisTorrentBean bean){
        if(this.getName().contains("bt")){
            // bt类型进行采集
            detailPageAnalyse(bean);
        }else if(this.getName().contains("url")){
            //链接(迅雷，电驴，网盘链接)类型进行采集
            detailPageAnalyseUrl(bean);
        }else if(this.getName().contains("pic_no_download")){
            //进行图片类型的采集，只采集图片外链地址，不采集到本地
            detailPageAnalysePic(bean);
        }else if(this.getName().contains("pic_down")){
            //进行图片类型采集，采集下载到本地
        }else if(this.getName().contains("txt_download")){
            //进行小说txt类型采集
            detailPageAnalyseTxt(bean);
        }
    }
    /**
     * 获得目标页面的最大页数
     * @param targetUrl
     */
    public  int getPages(String targetUrl){
        Document doc=null;
        try {
            doc= Sis001Task.client.getDocGBK(Sis001Task.client.noProxyGetUrl(targetUrl));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(doc!=null){
            //查找最大页数
            Element maxPageE= doc.select("div[class=pages_btns]>div>a[class=last]").first();
            System.out.println("最大页数:"+maxPageE.text().replace("... ","").trim());
            try{
                maxPage=Integer.parseInt(maxPageE.text().replace("... ","").trim());
            } catch (NumberFormatException e){
                return maxPage;
            }

        }
        return maxPage;
    }

}
