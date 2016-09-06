package com.br.dong.httpclientTest.youbb.net;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.downloadUtil.DownloadThread;
import com.br.dong.httpclientTest.downloadUtil.DownloadThreadEvent;
import com.br.dong.httpclientTest.downloadUtil.DownloadThreadListener;
import com.br.dong.utils.PropertiesUtil;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpHead;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-07-08
 * Time: 22:13
 * 主线程方法
 * 调用通用的DownloadThread进行下载
 * /?m=vod-type-id-26-pg-2.html  分类的参数后缀url
 * id-25 日本视频
 * id-26 自拍
 * id-27 日韩
 * id-28 欧美
 * id-29 动画
 */
public class YoubbTask {
    private static Logger logger = Logger.getLogger(YoubbTask.class);//日志
    private static PropertiesUtil propertiesUtil=PropertiesUtil.getInstance("/com/br/dong/httpclientTest/youbb/net/properties/config.properties");//读取配置文件
    private static String IMG_ROOT_PATH=propertiesUtil.getPropValue("IMG_ROOT_PATH");//图片文件存放的本地路径
    private static String VIDEO_ROOT_PATH=propertiesUtil.getPropValue("VIDEO_ROOT_PATH");//视频文件存放的本地跟路径
    private static String HOST=propertiesUtil.getPropValue("HOST");//采集的视频信息的目标站点host
    private static String HTTP="http";//http请求类型
    private static int DEFAULT_PAGE=10;//默认采集page最大为10
    private static CrawlerUtil crawlerUtil=new CrawlerUtil();//工具类
    private static ExecutorService executorForVideo = Executors.newFixedThreadPool(propertiesUtil.getPropValueInt("VIDEO_THREAD_POOL_SIZE")); // 视频下载线程池
    private static ExecutorService executorForImage = Executors.newFixedThreadPool(propertiesUtil.getPropValueInt("IMAGE_THREAD_POOL_SIZE")); // 图片下载线程池
    private static  DownloadThreadListener threadListener;
    /**设置采集的最大的视频大小小于100MB 大于的话就不采集了*/
    private static final int MAX_VIDEO_SIZE=100*1024*1024;// 104857600  24889567
    private static final int MIN_VIDEO_SIZE=10*1024*1024;//10485760
    /***
     * @param args
     * arg[0]-要采集的板块
     * arg[1]-起始采集页
     * arg[2]-结束采集页
     */
    public static void main(final String[] args) {
        /***
         * 创建下载监听
         */
           threadListener = new DownloadThreadListener() {
            public void afterPerDown(DownloadThreadEvent event) {
                //线程同步更改此下载监听器监听事件下载的文件大小
                synchronized (this) {
//                 System.out.println(((DownloadThread)event.getSource()).getTname()+"下载进度:"+event.getCount()+"%");
                }
            }
            /**
             * 当该监听到线程下载完成后
             * 如果是图片线程下载成功，不进行插入操作。如果是视频下载线程下载完成 判断视频大小，符合的则进行数据库的插入
             */
            public void downCompleted(DownloadThreadEvent event) {
                DownloadThread completedThread=(DownloadThread)event.getSource();//获得完成的线程名
                logger.info("thread name["+completedThread.getTname()+"] complete!");
                if(completedThread.getTname().contains("video")){//如果这是一个视频线程
               //如果 视频大小小于2000Kb 则不插库了
                    if(event.getCount()>=2000){//大于2000kb(单位为kb)
                        YoubbBean bean=new YoubbBean(completedThread.getTitle(),completedThread.getVkey(),completedThread.getVkey()+"_img.jpg",completedThread.getFile().getName(),completedThread.getTime());
                        int type=Integer.parseInt(args[0]);
                        switch (type){
                            case 25:bean.setTags("");break;
                            case 26:bean.setTags("自拍");break;
                            case 27:bean.setTags("日韩");break;
                            case 28:bean.setTags("欧美");break;
                            case 29:bean.setTags("动画");break;
                            default:break;
                        }
                        YoubbJdbcUtil.insertVideoInfo(bean);//插入到视频数据库中
                    }else {//视频大小小于2000kb 不入库了
                        logger.info("veky:"+completedThread.getVkey()+completedThread.getTitle()+"too much small--> size:"+completedThread.getSize());
                    }
                }
            }
        };
        /***
         * 创建主线程采集程序
         */
        try{
            for(int i=Integer.parseInt(args[1]);i<=Integer.parseInt(args[2]);i++){
                parsePage("http://"+HOST+"/?m=vod-type-id-"+args[0]+"-pg-"+i+".html");//解析当前页面的所有视频信息
            }
            //当线程池调用该方法时,线程池的状态则立刻变成SHUTDOWN状态,以后不能再往线程池中添加任何任务，否则将会抛出RejectedExecutionException异常。但是，此时线程池不会立刻退出，直到添加到线程池中的任务都已经处理完成，才会退出。
            executorForImage.shutdown();
            executorForImage.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);//用于等待子线程结束，再继续执行下面的代码。
            executorForVideo.shutdown();
            executorForVideo.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);//用于等待子线程结束，再继续执行下面的代码。
            logger.info("youbb task complete!!");
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }

    }

    /**
     *传入url，获得当前板块的页数
     * @return
     */
    @Deprecated
    private static int getTotalPagerNumber(String targetUrl) throws Exception {
        HttpResponse response=crawlerUtil.noProxyGetUrl(targetUrl);
        int responseCode=response==null?-1:response.getStatusLine().getStatusCode();//获得响应码
        if(responseCode==200){//如果响应码正常的话
            Document doc=crawlerUtil.getDocUTF8(response);
            Elements lis=doc.select("div[class=pagination]>ul>li");
            int lisNum=lis.size()==0? -1:lis.size();
            if(lisNum==-1) return DEFAULT_PAGE;//如果当前没有页数的话。返回一个默认的页数 即采集最大到10页
            int maxPage=Integer.parseInt(lis.select(":eq("+(lisNum-2)+")").text());//获得页面上最大的显示页数
//            logger.info(lis.select(":eq(5)").toString());
            logger.info("current section:"+targetUrl+" total page:"+maxPage);
            return maxPage;
        }
        return -1;//如果响应码不正常 则返回-1
    }

    /**
     * 解析出网络资源中的文件名字
     * 如：http://vip.youb77.com:81/media/you22/flv/9970.flv 解析出 9970.flv 包含文件名字的后缀
     * @return
     */
    public static  String parseFileName(String url){
        int index=url.lastIndexOf("/");
        return url.substring(index+1,url.length());
    }


    /**
     * 返回资源文件的后缀名
     * @param url
     * @return
     */
    public static String parseFileType(String url){
        int index=url.lastIndexOf("/");
        String temp= url.substring(index + 1, url.length());
        return temp.split(".")[1];
    }

    /**
     * 分析页面，解析出视频信息
     * @param targetUrl
     */
    public static void parsePage(String targetUrl) throws Exception{
        crawlerUtil.clientCreate(HTTP, HOST, targetUrl );
        logger.info("target url:"+targetUrl);
        HttpResponse response=crawlerUtil.noProxyGetUrl(targetUrl);
        if (response==null) return;
        Document document=crawlerUtil.getDocUTF8(response);
        Elements elements=document.select("div[class=col-sm-6 col-md-4 col-lg-4]");
        int i=0;
        for(Element element:elements){
            if(i==1) return;
            String href="http://"+HOST+element.select("a").attr("href");
            String title=element.select("img").attr("title");//获得标题
            String imgUlr=element.select("img").attr("src");//获得视频预览图片url地址
            String addTime=element.select("div[class=video-added]").text();//更新时间
//            logger.info(href+title + imgUlr+addTime);
            /**解析视频播放页面*/
            parseVideoPage(href,title,imgUlr,addTime);
            i++;

        }
    }

    /***
     * 解析视频页面
     * @param targetUrl 视频页面url
     * @param title 标题
     * @param imgUrl 预览图片
     * @param addTime 添加时间
     */
    public static void parseVideoPage(String targetUrl,String title,String imgUrl,String addTime)  {
        try{
            /**重新制定下新的client 防止出现403禁止访问错误*/
            crawlerUtil.clientCreate("http",HOST,targetUrl);
            HttpResponse response=crawlerUtil.noProxyGetUrl(targetUrl);
            Document document=crawlerUtil.getDocUTF8(response);
            /**解析失败，则直接返回*/
            if(null==document) return;
            String video_document=document.toString();
            String mp4Url=video_document.substring(video_document.indexOf("mac_url=unescape(base64decode('") + "mac_url=unescape(base64decode('".length(), video_document.indexOf("'));"));
            /**解析视频MP4地址*/
            mp4Url=decodeMp4Url(mp4Url);
            /**开始下载到本地*/
            startDownload(title,mp4Url,imgUrl);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("parseVideoPage error:["+targetUrl+"]");
        }
    }

    /***
     * 解析mp4的真实地址
     * 被base64与url加密
     * @param mp4Url
     * @return
     */
    public static String decodeMp4Url(String mp4Url) throws IOException {
        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
        String value = new String(dec.decodeBuffer(mp4Url));
        return URLDecoder.decode(value);
    }
    /**
     * 返回远程下载文件的文件大小
     * @return  返回文件大小
     */
    public static  int readDownloadFileInfo(String mp4Url){
        try{
            HttpHead httpHead = new HttpHead(mp4Url);//创一个访问头
            int size=0;
            crawlerUtil.clientCreatNoUrl("http");
            HttpResponse response = crawlerUtil.executeHead(httpHead);//返回头访问信息
            int statusCode = response==null?-1:response.getStatusLine().getStatusCode(); // 获取HTTP状态码
            if (statusCode != HttpStatus.SC_OK) {//响应码不正常的时候
                return size;
            }
            // Content-Length
            Header[] headers = response.getHeaders("Content-Length");
            if (headers.length > 0)
            {	//获得要下载的文件的大小
                size = Integer.parseInt(headers[0].getValue());
                httpHead.abort();//释放
            }else{
                size=0;
            }
            return size;
        }catch (Exception e){
            return 0;
        }
    }
    /**
     * 换算文件的大小
     */
    public String FormetFileSize(long fileSize) {// 转换文件大小
        if (fileSize <= 0) {
            return "0M";
        }
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "K";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) + "G";
        }
        return fileSizeString;
    }




    /***
     * 开始下载
     * @param title 标题
     * @param videoUrl 视频MP4地址
     * @param imgUlr 图片地址
     */
    public static void startDownload(String title,String videoUrl,String imgUlr){

        /**图片url host*/
        String img_host=HOST;
        /**视频url host*/
        String video_host=getVideoHost(videoUrl);
        /**视频大小*/
        int fileSize=readDownloadFileInfo(videoUrl);
        /**只拿去在次范围大小的视频，节省空间*/
        if(fileSize<MIN_VIDEO_SIZE||fileSize>=MAX_VIDEO_SIZE){
            logger.info("this video :["+videoUrl+"] size:["+fileSize+"] too large or too small,so give up");
            return;
        }else{
            logger.info("start downloading videoUrl:["+videoUrl+"] fileSize:["+fileSize+"]  imgUrl:["+imgUlr+"]");
        }
        String fileName=parseFileName(videoUrl);//video名字
        String vkey_arr[]=fileName.split("\\.");
        String vkey=vkey_arr[0];//video的vkey
        /***
         * 判断下如果视频预览图片是http://打头的 该图片并没有放在当前视频站上
         */
        if(!imgUlr.startsWith("http://")){
            imgUlr="http://"+HOST+imgUlr;
        }else{
            img_host=getHost(imgUlr);
        }
        /**启动图片下载线程*/
        DownloadThread imgDownloadThread=new DownloadThread(vkey+"_img_thread",vkey,title,"",imgUlr, new File(IMG_ROOT_PATH+vkey+"_img.jpg"),HTTP,img_host,"");//图片线程
        imgDownloadThread.addDownloadListener(threadListener);
        executorForImage.execute(imgDownloadThread);
        /**启动视频下载线程*/
        DownloadThread videoDownloadThread=new DownloadThread(vkey+"_video_thread",vkey,title,"",videoUrl, new File(VIDEO_ROOT_PATH+fileName),HTTP,video_host,videoUrl);//视频线程
        videoDownloadThread.addDownloadListener(threadListener);
        executorForVideo.execute(videoDownloadThread);
    }

    /***
     * 正则表达式获取host
     * @param url
     * @return
     */
    public static String getHost(String url){
        if(url==null||url.trim().equals("")){
            return "";
        }
        String host = "";
        Pattern p =  Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
        Matcher matcher = p.matcher(url);
        if(matcher.find()){
            host = matcher.group();
        }
        return host;
    }
    public static String getVideoHost(String url){
        if(url==null||url.trim().equals("")){
            return "";
        }
        String host = url.substring(url.indexOf("http://")+"http://".length(),url.indexOf("/iphone"));

        return host;
    }

}
