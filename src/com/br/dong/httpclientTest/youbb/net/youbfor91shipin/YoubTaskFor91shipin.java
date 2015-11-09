package com.br.dong.httpclientTest.youbb.net.youbfor91shipin;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.downloadUtil.DownloadThread;
import com.br.dong.httpclientTest.downloadUtil.DownloadThreadEvent;
import com.br.dong.httpclientTest.downloadUtil.DownloadThreadListener;
import com.br.dong.utils.DateUtil;
import com.br.dong.utils.PropertiesUtil;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-10-12
 * Time: 13:53
 */
public class YoubTaskFor91shipin {
    private static Logger logger = Logger.getLogger(YoubTaskFor91shipin.class);//日志
    private static PropertiesUtil propertiesUtil=PropertiesUtil.getInstance("/com/br/dong/httpclientTest/youbb/net/properties/config.properties");//读取配置文件
    private static String IMG_ROOT_PATH=propertiesUtil.getPropValue("IMG_ROOT_PATH");//图片文件存放的本地路径
    private static String VIDEO_ROOT_PATH=propertiesUtil.getPropValue("VIDEO_ROOT_PATH");//视频文件存放的本地跟路径
    //    private static String VIDEO_DOWNLOAD_HOST="youb77.com";//视频资源host
//    private static String VIDEO_DOWNLOAD_URL_REF="http://vip.youb77.com:81";//视频资源ref url
    private static String HOST=propertiesUtil.getPropValue("HOST");//采集的视频信息的目标站点host
    private static String IMG_DOWNLOAD_URL_REF=propertiesUtil.getPropValue("IMG_DOWNLOAD_URL_REF");//采集的视频信息的目标站点host
    private static String absUrl="http://"+HOST;//采集的视频信息的请求使用的前缀
    private static String favUrlPre=absUrl+"/videos?c=1&o=tf&page=";//最爱前缀
    private static String latestUrlPre=absUrl+"/videos?c=1&o=mr&page=";//最新前缀
    private static String configUrl=absUrl+"/media/player/config.php?vkey=";//获得视频下载地址的请求目标url
    private static String HTTP="http";//http请求类型
    private static int DEFAULT_PAGE=10;//默认采集page最大为10
    private static CrawlerUtil crawlerUtil=new CrawlerUtil();//工具类
    private static ExecutorService executorForVideo = Executors.newFixedThreadPool(propertiesUtil.getPropValueInt("VIDEO_THREAD_POOL_SIZE")); // 视频下载线程池
    private static ExecutorService executorForImage = Executors.newFixedThreadPool(propertiesUtil.getPropValueInt("IMAGE_THREAD_POOL_SIZE")); // 图片下载线程池
    private static DownloadThreadListener threadListener;
    private static String video_url=propertiesUtil.getPropValue("91_video_url");
    private static String img_url=propertiesUtil.getPropValue("91_image_url");
    public static void main(String[] args) {
        threadListener = new DownloadThreadListener(){

            @Override
            public void afterPerDown(DownloadThreadEvent event) {

            }

            @Override
            public void downCompleted(DownloadThreadEvent event) {
                DownloadThread completedThread=(DownloadThread)event.getSource();//获得完成的线程名
                if(completedThread.getTname().contains("video")) {//如果这是一个视频线程
                    //如果 视频大小小于2000Kb 则不插库了
                    if(event.getCount()>=2000) {//大于2000kb(单位为kb)
                        YoubBeanFor91shipin bean=new YoubBeanFor91shipin(completedThread.getTitle(),img_url+completedThread.getVkey()+"_img.jpg",video_url+completedThread.getFile().getName(),completedThread.getTime(), DateUtil.getStrOfDateTime(),completedThread.getVkey());
                        YoubJdbcUtilFor91shipin.insertVideoInfo(bean);
                    }else{
                        logger.info("veky:"+completedThread.getVkey()+completedThread.getTitle()+"too much small--> size:"+completedThread.getSize());
                    }
                }
            }
        };

        try{
            crawlerUtil.clientCreate(HTTP, HOST, latestUrlPre + 1);//创建主线程用来采集视频列表信息
//            for(int i=propertiesUtil.getPropValueInt("WANT_PAGE_START");i<=propertiesUtil.getPropValueInt("WANT_PAGE_END");i++){ //不采用此种了采用arg[]参数的形式
            /**
             * arg[0] 起始采集页 arg[1] 结束采集页
             */
            for(int i=Integer.parseInt(args[0]);i<=Integer.parseInt(args[1]);i++){
                parsePage(latestUrlPre+i);//解析当前页面的所有视频信息
            }
            //当线程池调用该方法时,线程池的状态则立刻变成SHUTDOWN状态,以后不能再往线程池中添加任何任务，否则将会抛出RejectedExecutionException异常。但是，此时线程池不会立刻退出，直到添加到线程池中的任务都已经处理完成，才会退出。
            executorForImage.shutdown();
            executorForImage.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);//用于等待子线程结束，再继续执行下面的代码。
            executorForVideo.shutdown();
            executorForVideo.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);//用于等待子线程结束，再继续执行下面的代码。
            System.out.println("执行完毕");
        }catch (Exception e){
            logger.error(e.getMessage());
        }
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
     * 分析页面，解析出视频信息
     * @param targetUrl
     */
    public static void parsePage(String targetUrl) throws Exception{
        logger.info("target url:"+targetUrl);
        HttpResponse response=crawlerUtil.noProxyGetUrl(targetUrl);
        if (response==null) return;
        Document document=crawlerUtil.getDocUTF8(response);
        Elements elements=document.select("div[class=video_box]");
        for(Element element:elements){
            String title=element.select("img").attr("title");//获得标题
            String imgUlr=absUrl+element.select("img").attr("src");//获得视频预览图片url地址
            String videoTime=element.select("div[class=box_left]").text();//视频时长
            String vkey=element.select("a").attr("href").replace("/video/", "").substring(0, element.select("a").attr("href").replace("/video/", "").indexOf("/"));//获得vkey
//          logger.info(title+" "+videoTime+" "+vkey+" "+imgUlr);
            getConfigByVkey(vkey,title,videoTime,imgUlr);
        }
    }
    /**
     * 根据vkey查询该视频的flv路径信息
     * 如：http://youb444.com/media/player/config.php?vkey=9970
     * @param vkey 视频vkey用来获得视频源文件url地址
     * @param title 视频标题
     * @param videoTime 视频时长
     * @param imgUlr 视频预览图片url地址
     */
    public static void getConfigByVkey(String vkey,String title,String videoTime,String imgUlr)   {
        try{
            HttpResponse response= crawlerUtil.noProxyGetUrl(configUrl+vkey);//获得文件信息的响应
            if (response == null) {
                logger.error("vkey:"+vkey+"get video resources fall");//如果响应不存在
            }else{
                parseXml(vkey,title,videoTime,imgUlr,response);//解析config url返回的xml格式获得视频播放地址
            }
        }catch (Exception e){
            logger.error("vkey:" + vkey + e.getMessage());//获得响应出错
        }
    }

    /**
     * 解析服务端xml
     * @param title 视频标题
     * @param videoTime 视频时长
     * @param imgUlr 视频预览图片url地址
     * @param response
     * @throws DocumentException
     */
    public static void parseXml(String vkey,String title,String videoTime,String imgUlr,HttpResponse response) throws Exception {
        org.jsoup.nodes.Document document=crawlerUtil.getDocUTF8(response);
        if(document==null) return;//
        org.dom4j.io.SAXReader reader = new org.dom4j.io.SAXReader();
        org.dom4j.Document doc;
//        logger.info(document.body());
        doc =  org.dom4j.DocumentHelper.parseText(document.body().toString().replace("<body>","").replace("</body>","").trim());
        org.dom4j.Element root = doc.getRootElement();
        org.dom4j.Element fileUrl = root.element("file");
//        org.dom4j.Element image = root.element("image");
        logger.info(title+"-"+ "-"+videoTime+"-"+fileUrl.getTextTrim()+"-"+imgUlr);
        //开始下载 检查数据库中是否已经存在vkey 存在则不启动下载
        if(YoubJdbcUtilFor91shipin.checkVkey(vkey)){//检查数据库中是否有此条记录
            startDownload(vkey,fileUrl.getTextTrim(),title,videoTime,imgUlr);
        }else{
            logger.info("veky:" + vkey + "already exist in table video");
        }

    }
    /**
     * 开始下载
     * @param vkey 此处用来给作为唯一图片的命名
     * @param videoUrl 视频源文件url地址
     * @param title 视频标题
     * @param videoTime 视频时长
     * @param imgUlr 视频预览图片url地址
     */
    public static void startDownload(String vkey,String videoUrl,String title,String videoTime,String imgUlr){
        String fileName=parseFileName(videoUrl);//video名字
        DownloadThread imgDownloadThread=new DownloadThread(vkey+"_img_thread",vkey,title,videoTime,imgUlr, new File(IMG_ROOT_PATH+vkey+"_img.jpg"),HTTP,HOST,IMG_DOWNLOAD_URL_REF);//图片线程
        imgDownloadThread.addDownloadListener(threadListener);
        executorForImage.execute(imgDownloadThread);
        //待解决的 视频如果时间太长 就不采集了 节省空间！！！！！！！！！！！！
        DownloadThread videoDownloadThread=new DownloadThread(vkey+"_video_thread",vkey,title,videoTime,videoUrl, new File(VIDEO_ROOT_PATH+fileName),HTTP,"vip.youb77.com:81",videoUrl);//视频线程
        videoDownloadThread.addDownloadListener(threadListener);
        executorForVideo.execute(videoDownloadThread);
    }
}
