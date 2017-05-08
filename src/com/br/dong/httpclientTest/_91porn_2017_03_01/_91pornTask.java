package com.br.dong.httpclientTest._91porn_2017_03_01;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest._91porn_2017_03_01.bean._91pornVideoBean;
import com.br.dong.httpclientTest._91porn_2017_03_01.db._91pSqlHelper;
import com.br.dong.httpclientTest.downloadUtil.DownloadThread;
import com.br.dong.httpclientTest.downloadUtil.DownloadThreadEvent;
import com.br.dong.httpclientTest.downloadUtil.DownloadThreadListener;
import com.br.dong.httpclientTest.youbb.net.YoubbTask;
import com.br.dong.utils.DateUtil;
import com.br.dong.utils.PropertiesUtil;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.httpclientTest._91porn_2017_03_01
 * AUTHOR: hexOr
 * DATE :2017/3/1 20:57
 * DESCRIPTION:采集91porn的视频信息,下载预览图片与mp4视频
 */
public class _91pornTask {
    private static Logger logger = Logger.getLogger(YoubbTask.class);//日志
    private static PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("/com/br/dong/httpclientTest/_91porn_2017_03_01/config/config.properties");//读取配置文件
    private static String IMG_ROOT_PATH = propertiesUtil.getPropValue("IMG_ROOT_PATH");//图片文件存放的本地路径
    private static String VIDEO_ROOT_PATH = propertiesUtil.getPropValue("VIDEO_ROOT_PATH");//视频文件存放的本地跟路径
    private static String IMG_URL_ROOT_PATH = propertiesUtil.getPropValue("IMG_URL_ROOT_PATH");//外网访问获得图片的根路径
    private static String VIDEO_URL_ROOT_PATH = propertiesUtil.getPropValue("VIDEO_URL_ROOT_PATH");//外网访问获得视频的根路径
    private static String HOST = propertiesUtil.getPropValue("HOST");//采集的视频信息的目标站点host
    private static String HTTP = "http";//http请求类型
    public static final String PATH = "/";//斜杠符号
    public static   Boolean ifStop=false;
    /**
     * 获得mp4文件的后缀参数
     */
    public static final String GETFILE = "getfile.php?";
    /**
     * 当前最热的网址后缀 每天只采集当前最热的
     */
    public static final String VIDEO_HOT = "v.php?category=hot&viewtype=basic&page=";
    private static CrawlerUtil crawlerUtil = new CrawlerUtil();//工具类
    private static ExecutorService executorForVideo = Executors.newFixedThreadPool(propertiesUtil.getPropValueInt("VIDEO_THREAD_POOL_SIZE")); // 视频下载线程池
    private static ExecutorService executorForImage = Executors.newFixedThreadPool(propertiesUtil.getPropValueInt("IMAGE_THREAD_POOL_SIZE")); // 图片下载线程池
    private static DownloadThreadListener threadListener;
    /**
     * 设置采集的最大的视频大小小于100MB 大于的话就不采集了
     * 只采集的范围 10m到100m
     */
    private static final int MAX_VIDEO_SIZE = 100 * 1024 * 1024;// 104857600  24889567
    private static final int MIN_VIDEO_SIZE = 10 * 1024 * 1024;//10485760
    /**
     * 视频列表
     */
    public static List<_91pornVideoBean> videoBeans = new ArrayList<_91pornVideoBean>();

    /***
     * @param args
     * arg[0]-起始采集页
     * arg[1]-结束采集页
     */
    public static void main(String[] args) {
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
                DownloadThread completedThread = (DownloadThread) event.getSource();//获得完成的线程名
                logger.info("thread name[" + completedThread.getTname() + "] complete!");
                if (completedThread.getTname().contains("video")) {//如果这是一个视频线程
                    if (event.getCount() >= 2000) {//大于2000kb(单位为kb)
                        logger.info("veky:" + completedThread.getVkey() + completedThread.getTitle() + "download completed--> size:" + completedThread.getSize());
                        String []names=completedThread.getVkey().split(",");
                        _91pSqlHelper.addVideo(completedThread.getTitle(), (String) completedThread.getMap().get("preimgsrc"),(String) completedThread.getMap().get("videopageurl"),completedThread.getTime(), DateUtil.getStrOfDateTime(),(String) completedThread.getMap().get("author"),"",VIDEO_URL_ROOT_PATH+names[0],IMG_URL_ROOT_PATH+names[1]);
                    } else {//视频大小小于2000kb 不入库了
                        logger.info("veky:" + completedThread.getVkey() + completedThread.getTitle() + "too much small--> size:" + completedThread.getSize());
                    }
                }
            }
        };
        /***
         * 开始采集程序
         */
        try {
            for (int i = Integer.parseInt(args[0]); i <= Integer.parseInt(args[1]); i++) {
                parseVideosListPage("http://" + HOST + PATH + VIDEO_HOT + i);//解析当前页面的所有视频信息
            }
            /**循环视频列表开始解析下载*/
            logger.info("############start parse video#################");
            //下面的是测试 只为采集一条
//            beforeCheckExistVideo(videoBeans.get(0));
            /**开始采集*/
            for (_91pornVideoBean videoBean:videoBeans){
                if (ifStop) {
                    logger.info("current ip is limit!!! will exit!!!");
                    return;
                }
                beforeCheckExistVideo(videoBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            //当线程池调用该方法时,线程池的状态则立刻变成SHUTDOWN状态,以后不能再往线程池中添加任何任务，否则将会抛出RejectedExecutionException异常。但是，此时线程池不会立刻退出，直到添加到线程池中的任务都已经处理完成，才会退出。
            try{
                executorForImage.shutdown();
                executorForImage.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);//用于等待子线程结束，再继续执行下面的代码。
                executorForVideo.shutdown();
                executorForVideo.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);//用于等待子线程结束，再继续执行下面的代码。
                logger.info("91porn task complete!!");
            }catch (Exception e){
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
    }

    /***
     * 解析当前页面获取页面的视频列表
     * @param targetUrl
     * @throws Exception
     */
    public static void parseVideosListPage(String targetUrl) throws Exception {
        logger.info("current parse target url:" + targetUrl);
        crawlerUtil.clientCreate(HTTP, HOST, targetUrl);
        HttpResponse response = crawlerUtil.noProxyPostUrl(targetUrl, getPostParmList());
        if (null != response && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            Document document = crawlerUtil.getDocUTF8(response);
            if (null == document) {
                logger.error("url：" + targetUrl + " document is null!");
                return;
            }
            Elements videobox = document.select("div[class*=listchannel]");
            for (Element e : videobox) {
                /**标题*/
                String title = e.select("div[class*=imagechannel]>a>img").attr("title");
                /**获得预览图片链接*/
                String preImgSrc = e.select("div[class*=imagechannel]>a>img").attr("src");
                /**视频链接地址*/
                String videoPageUrl = e.select("div[class*=imagechannel]>a").attr("abs:href");
                /**获得时长*/
                String infotime = e.text().substring(e.text().indexOf("时长:"), e.text().indexOf(" 添加时间"));
                /**获得添加时间*/
                String updatetime = e.text().substring(e.text().indexOf("添加时间"), e.text().indexOf("作者:"));
                /**作者信息*/
                String author = e.text().substring(e.text().indexOf("作者:"), e.text().indexOf("查看:"));
                /**视频描述*/
//                logger.info("视频:"+title);
                String description = "";//todo
                videoBeans.add(new _91pornVideoBean(title, preImgSrc, videoPageUrl, infotime, updatetime, author, description));
            }
            logger.info("parse target url:" + targetUrl + " complete, now videoBeans size:" + videoBeans.size());
        } else {
            logger.error("parse url:" + targetUrl + " fault,response is null!");
        }
    }

    /***
     * 检测当前是否已经采集过该标题的视频
     * @param videoBean
     */
    public static void beforeCheckExistVideo(_91pornVideoBean videoBean) throws Exception {
        if (null == videoBean || "".equals(videoBean.getTitle())) return;
        /**如果库里没有采集过这个视频 则入库*/
        if(_91pSqlHelper.checkExistVideo(videoBean.getTitle())) return;
        parseVideoInfoPage(videoBean);


    }

    /***
     * 解析包含视频详细页面的视频获得视频的下载地址
     * @param videoBean
     */
    public static void parseVideoInfoPage(_91pornVideoBean videoBean) throws Exception {
        crawlerUtil.clientCreate("http", HOST, videoBean.getVideoPageUrl());
        HttpResponse response = crawlerUtil.noProxyPostUrl(videoBean.getVideoPageUrl(), getPostParmList());
        if (null != response && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            Document document = crawlerUtil.getDocUTF8(response);
            /**解析视频下载地址*/
//            String videoUrl = parseVideoUrl(document);
            String videoUrl = parseVideoUrlNew(document);
            /***
             * 判断当前videoUrl 如果包含[limit] 则是观看到了上限
             *                 如果包含[error] 则是破解报错了
             */
            if ("".equals(videoUrl)||videoUrl.contains("[error]")) {
                logger.info("parseVideoInfoPage fault:"+videoUrl);
                return;
            } else if(videoUrl.contains("[limit]")){
                logger.info("parseVideoInfoPage limit:"+videoUrl);
                ifStop=true;
            }else {
                //todo 开始下载
                startDownload(videoBean,videoUrl);
            }
        } else {
            logger.info("parseVideoInfoPage fault video page url:"+videoBean.getVideoPageUrl()+" response is null!");
        }
    }

    /***
     * 最新的破解真正的播放地址的
     * @param doc
     * @return
     * @throws Exception
     */
    public static String parseVideoUrlNew(Document doc)throws Exception {
        /**视频页面的html信息*/
        String content = doc.toString();
        String downUrl = "";
//        System.out.println("cccccccccccccccccc:"+content);
        if (content.contains("游客")) {
            downUrl = "[limit]  current ip is limit !";
        }else{
             try{
                downUrl=doc.select("source[type=video/mp4]").attr("src");
             } catch (Exception e){

             }
        }
        return downUrl;
    }
    /***
     * 解析出视频真正的播放地址
     * @param doc
     * @return 如果当前没有观看权限了 返回
     * 新的91已经修改了，此方法过时
     */
    @Deprecated
    public static String parseVideoUrl(Document doc) throws Exception {
        Map map = new HashMap();
        String file = "";
        String max_vid = "";
        String seccode = "";
        String mp4 = "";
        String downUrl = "";
        /**视频页面的html信息*/
        String content = doc.toString();
        System.out.println("cccccccccccccccccc:"+content);
        /***
         * 当前还能观看
         */
        if (content.contains("seccode")) {
            /**截取包含参数的临时字符串*/
            String temp = content.substring(content.indexOf("so.addParam('allowscriptaccess'"), content.indexOf("so.write('mediaspace');")).replace("\n", "");
            String[] arr = temp.split(";");
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].contains("file")) {
                    file = arr[i].replaceAll("(?:so.addVariable|\\(|file|,|'|\"|\\))", "");
                } else if (arr[i].contains("max_vid")) {
                    max_vid = arr[i].replaceAll("(?:so.addVariable|\\(|max_vid|,|'|\"|\\))", "");
                } else if (arr[i].contains("seccode")) {
                    seccode = arr[i].replaceAll("(?:so.addVariable|\\(|seccode|,|'|\"|\\))", "");
                } else if (arr[i].contains("mp4")) {
                    mp4 = arr[i].replaceAll("(?:so.addVariable|\\(|mp4|,|'|\"|\\))", "");
                }
            }
            /**
             * update by hexor 2017-02-24 去除seccode中的可能包含的空格防止破解地址失败
             * 如：
             * http://email.91dizhi.at.gmail.com.7h5.space/getfile.php?VID=199867&seccode=   5f65f9d8cf9b72ba3e05b23abf8eaa7f&mp4=0&max_vid=199881
             * 访问会失败
             * */
            if (null != seccode) seccode = seccode.trim();
            map.put("VID", file);
            map.put("max_vid", max_vid);
            map.put("seccode", seccode);
            map.put("mp4", mp4);
            /**获得下载地址的跳转地址*/
            String getFileUrl = HTTP+"://" + HOST + PATH + GETFILE + "VID=" + map.get("VID") + "&seccode=" + map.get("seccode") + "&mp4=" + mp4 + "&max_vid=" + map.get("max_vid");
            System.out.println( "uuuuuuuuuuuu" + getFileUrl);
            crawlerUtil.clientCreate("http", HOST, getFileUrl);
            HttpResponse response = crawlerUtil.noProxyGetUrl(getFileUrl);
            if (null != response && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                Document docMp4 = crawlerUtil.getDocUTF8(response);
                /**URL解码mp4源下载地址*/
                downUrl = URLDecoder.decode(docMp4.body().text(), "UTF-8");
                /**解决file=http://68.235.35.99/cheat.mp4&domainUrl=http://91porn.ro.lt&imgUrl=http://img.file.am/91porn/ 这种被识别的返回串*/
                if (downUrl.contains("cheat")||!downUrl.contains("domainUrl")) return "[error] parse getFileUrl :+" + getFileUrl + " fault, cheat.mp4!!!";
                return downUrl.substring("file=".length(), downUrl.indexOf("&domainUrl"));
            } else {
                downUrl = "[error] parse getFileUrl :+" + getFileUrl + " fault, response is null";
            }
        }
        /***
         * 当前观看到了次数了，不能观看
         */
        else if (content.contains("游客")) {
            downUrl = "[limit]  current ip is limit !";
        }
        return downUrl;
    }

    /***
     * 开始下载
     * @param videoBean 视频信息
     * @param mp4Url 视频下载的地址
     */
    public static void startDownload(_91pornVideoBean videoBean,String mp4Url){
        logger.info("start download video:"+mp4Url+"\n imgUrl:"+videoBean.getPreImgSrc());
        /**视频大小*/
        int fileSize=readDownloadFileInfo(mp4Url);
        /**只拿去在次范围大小的视频，节省空间*/
        if(fileSize<MIN_VIDEO_SIZE||fileSize>=MAX_VIDEO_SIZE){
            logger.info("this video :["+mp4Url+"] size:["+fileSize+"] too large or too small,so give up");
            return;
        }else{
            logger.info("start downloading videoUrl:["+mp4Url+"] fileSize:["+fileSize+"]  mp4Url:["+mp4Url+"]");
        }
        String mp4FileName=new File(mp4Url.trim()).getName().split("\\.mp4")[0]+".mp4";//获得mp4文件名
        String imgFileName=new File(videoBean.getPreImgSrc().trim()).getName();//获得预览图片名
        /**图片下载线程*/
        DownloadThread imgDownloadThread=new DownloadThread("img_thread_"+imgFileName,imgFileName,videoBean.getTitle(), videoBean.getInfotime(),videoBean.getPreImgSrc(), new File(IMG_ROOT_PATH+imgFileName),HTTP,getHost(videoBean.getPreImgSrc()),videoBean.getPreImgSrc());
        imgDownloadThread.addDownloadListener(threadListener);
        executorForImage.execute(imgDownloadThread);
        /**视频下载线程 DownloadThread 构造的第二个参数 mp4FileName+","+imgFileName 为了在入库的时候好确定img的名字和video的名字*/
        DownloadThread videoDownloadThread=new DownloadThread("video_thread_"+mp4FileName,mp4FileName+","+imgFileName,videoBean.getTitle(), videoBean.getInfotime(),mp4Url, new File(VIDEO_ROOT_PATH+mp4FileName),HTTP,getHost(mp4Url),videoBean.getVideoPageUrl());
        Map map=new HashMap();
        map.put("preimgsrc",videoBean.getPreImgSrc());
        map.put("videopageurl",videoBean.getVideoPageUrl());
        map.put("author",videoBean.getAuthor());
        videoDownloadThread.setMap(map);
        videoDownloadThread.addDownloadListener(threadListener);
        executorForVideo.execute(videoDownloadThread);
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
    /**
     * 拿去post参数，获得中文返回页面
     */
    public static List<NameValuePair> getPostParmList() {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("session_language", "cn_CN"));
        return list;
    }

}
