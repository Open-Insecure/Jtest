package com.br.dong.httpclientTest.btgc_2015_08_30;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.downloadUtil.DownloadThread;
import com.br.dong.httpclientTest.downloadUtil.DownloadThreadEvent;
import com.br.dong.httpclientTest.downloadUtil.DownloadThreadListener;
import com.br.dong.httpclientTest.youbb.net.YoubbJdbcUtil;
import com.br.dong.utils.PropertiesUtil;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-08-30
 * Time: 14:57
 * http://99btgc01.com/forumdisplay.php?fid=9 针对爱唯侦察-自拍图片区的
 */
public class BtgcImageTask {
    private static Logger logger = Logger.getLogger(BtgcImageTask.class);//日志
    private static PropertiesUtil propertiesUtil=PropertiesUtil.getInstance("/com/br/dong/httpclientTest/btgc_2015_08_30/properties/config.properties");//读取配置文件
    private static CrawlerUtil crawlerUtil=new CrawlerUtil();//工具类
    private static  DownloadThreadListener threadListener;//下载线程的监听
    private static String HOST=propertiesUtil.getPropValue("HOST");//采集的视频信息的目标站点host
    private static String baseUrl=propertiesUtil.getPropValue("BASE_URL");//爱唯侦察网页版块分页url
    private static String HTTP="http";//http请求类型
    private static ExecutorService executorForImage = Executors.newFixedThreadPool(propertiesUtil.getPropValueInt("IMAGE_THREAD_POOL_SIZE")); // 图片下载线程池

    /**
     * 主方法
     * @param args  第一个参数是采集起始页数 第二个参数是采集结束页数
     */
    public static void main(String[] args) {
        //下载线程监听器 构造方法
        threadListener = new DownloadThreadListener() {
            public void afterPerDown(DownloadThreadEvent event) {
                //线程同步更改此下载监听器监听事件下载的文件大小
                synchronized (this) {
//                     System.out.println(((DownloadThread)event.getSource()).getTname()+"下载进度:"+event.getCount()+"%");
                }
            }
            /*
             * 当该监听到线程下载完成后
             * 如果是图片线程下载成功，不进行插入操作。如果是视频下载线程下载完成 判断视频大小，符合的则进行数据库的插入
             */
            public void downCompleted(DownloadThreadEvent event) {
                DownloadThread completedThread=(DownloadThread)event.getSource();//获得完成的线程名

            }
        };
        try{
            crawlerUtil.clientCreate(HTTP, HOST, baseUrl + 1);//创建主线程用来采集视频列表信息
//            for(int i=propertiesUtil.getPropValueInt("WANT_PAGE_START");i<=propertiesUtil.getPropValueInt("WANT_PAGE_END");i++){ //不采用此种了采用arg[]参数的形式
            for(int i=Integer.parseInt(args[0]);i<=Integer.parseInt(args[1]);i++){
                parsePage(baseUrl+i);//解析当前页面的所有视频信息
            }
            //当线程池调用该方法时,线程池的状态则立刻变成SHUTDOWN状态,以后不能再往线程池中添加任何任务，否则将会抛出RejectedExecutionException异常。但是，此时线程池不会立刻退出，直到添加到线程池中的任务都已经处理完成，才会退出。
            executorForImage.shutdown();
            executorForImage.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);//用于等待子线程结束，再继续执行下面的代码。
            System.out.println("执行完毕");
        }catch (Exception e){
            logger.error(e);
        }
    }

    /**
     * 分析页面，解析出视频信息
     * @param targetUrl
     */
    public static void parsePage(String targetUrl) throws Exception{
        logger.info("target url:"+targetUrl);
        HttpResponse response=crawlerUtil.noProxyGetUrl(targetUrl);
        if (response==null) return;
        Document document=crawlerUtil.getDocGBK(response);
        Elements elements=document.select("#forum_9>tbody>tr");//每一行的帖子信息
        System.out.println(elements);
        for(Element element:elements){
            Elements topical=element.select("th[class=new]").select("span");//获得帖子信息
            String id=topical.attr("id").replace("thread_","");
            String title=topical.select("a").text();//帖子标题
            String href=topical.select("a").attr("href");

        }
    }
}
