package com.br.dong.httpclientTest.porn.new_91porn_2016.thread;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.VedioBean;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2016-01-13
 * Time: 16:21
 * 91p的视频预览图片加载线程
 */
public class Crawler91pThread extends Thread{
    /**跳转地址列表*/
    private static String []urls={
            "http://www.allanalpass.com/Cazn2" ,
            "http://www.galleries.bz/Cazn2" ,
            "http://www.hornywood.tv/Cazn2" ,
            "http://www.linkbabes.com/Cazn2" ,
            "http://www.theseblogs.com/Cazn2" ,
            "http://www.poontown.net/Cazn2" ,
            "http://www.rqq.co/Cazn2" ,
            "http://www.tnabucks.com/Cazn2" ,
            "http://www.youfap.me/Cazn2" ,
            "http://www.whackyvidz.com/Cazn2" ,
            "http://www.rqq.co/Cazn2" ,
            "http://www.zff.co/Cazn2" ,
            "http://www.any.gs/Cazn2" ,
            "http://www.cash4files.com/Cazn2" ,
            "http://www.dyo.gs/Cazn2" ,
            "http://www.filesonthe.net/Cazn2" ,
            "http://www.goneviral.com/Cazn2" ,
            "http://www.megaline.co/Cazn2" ,
            "http://www.miniurls.co/Cazn2" ,
            "http://www.seriousdeals.net/Cazn2" ,
            "http://www.tubeviral.com/Cazn2" ,
            "http://www.ultrafiles.net/Cazn2" ,
            "http://www.urlbeat.net/Cazn2" ,
            "http://www.qqc.co/Cazn2" ,
            "http://www.yyv.co/Cazn2" ,
            "http://www.theseforums.com/Cazn2"};
    /**自拍视频首页后缀*/
    private static String url_suffix="/v.php?next=watch&page=1";
    /**监听器*/
    Thread91pListener listener;
    /**CrawlerUtil实例*/
    private static CrawlerUtil crawlerUtil = new CrawlerUtil();
    /**当前页视频信息集合*/
    List<VedioBean> list=new ArrayList<VedioBean>();
    public static void main(String[] args) {
        Thread91pListener listener= new Thread91pListener() {
            @Override
            public void complete(Thread91pEvent event) {
                 List<VedioBean> list=((Crawler91pThread)event.getTarget()).getList();
                 System.out.println("llllllllll:"+list.size());
            }

            @Override
            public void onError(Thread91pEvent event) {
                System.out.println(event.getMessage());
            }
        };
        Crawler91pThread thread=new Crawler91pThread("test",listener);
        thread.start();
    }
    /***
     * 构造方法
     * @param name 线程名
     * @param listener 监听
     */
    public Crawler91pThread(String name, Thread91pListener listener)  {
        super(name);
        this.listener = listener;
    }

    public void run(){
        try{
            getVideos();
            listener.complete(new Thread91pEvent(this,"complete"));
        }catch (Exception e){
            listener.onError(new Thread91pEvent(this,e.getMessage()));
        }
    }


    public   void getVideos() throws Exception {
        String reslut=getAddress(urls);
        /**如果解析所有的跳转地址都没有结果，则提示*/
        if("".equals(reslut)){
            throw new Exception("error:解析跳转地址错误");
        }
//        System.out.println(reslut);
        String host=reslut.substring(reslut.indexOf("//") + "//".length(), reslut.indexOf(url_suffix));
        /**创建视频头链接信息*/
        crawlerUtil.clientCreate("http", host, reslut);
        HttpResponse response=crawlerUtil.post(reslut, crawlerUtil.produceEntity(getPostParmList()));
        Document doc=crawlerUtil.getDocUTF8(response);
//        System.out.println(doc.toString());
        Elements videobox=doc.select("div[class*=listchannel]");
        System.out.println(reslut+"视频总数:"+videobox.size()+"个");
        /**拿去视频预览图片*/
        for(Element e:videobox){
            /**标题*/
            String title=e.select("div[class*=imagechannel]>a>img").attr("title");
            /**获得预览图片链接*/
            String preImgSrc=e.select("div[class*=imagechannel]>a>img").attr("src");
            /**视频链接地址*/
            String vedioUrl=e.select("div[class*=imagechannel]>a").attr("abs:href");
            /**获得时长*/
            String infotime=e.text().substring(e.text().indexOf("时长:"),e.text().indexOf(" 添加时间"));
            System.out.println(title+" "+preImgSrc+" "+vedioUrl+" "+infotime);
            VedioBean bean=new VedioBean(title,preImgSrc,vedioUrl,infotime,"",0);
            list.add(bean);
        }
    }

    /***
     * 根据地址跳转页url获得访问地址
     * @param urls
     * @return
     */
    public   String getAddress(String []urls) throws Exception {
        /**循环跳转列表 获得访问地址*/
        for(int i=0;i<urls.length;i++){
            String url=urls[i];
            /**根据跳转地址获得host信息*/
            String host=url.substring(url.indexOf("//") + "//".length(), url.indexOf("/Cazn2"));
            /**创建跳转地址的头链接*/
            crawlerUtil.clientCreate("http", host, url);
            /**注意此处用post访问才能获得跳转信息页面，如果访问失败返回的response是null*/
            HttpResponse response=crawlerUtil.post(url, crawlerUtil.produceEntity(getPostParmList()));
            /**如果获得响应成功且响应码是跳转302*/
            if(response!=null&&response.getStatusLine().getStatusCode()== HttpStatus.SC_MOVED_TEMPORARILY){
                Document doc=crawlerUtil.getDocUTF8(response);
                /**如果网页内容包含跳转关键字*/
                if(doc!=null&&doc.body().toString().contains("Object moved to")){
                    /**获得跳转字段*/
                    String reslut=doc.body().select("a").attr("href");
                    /**替换链接跳转到自拍视频第一页*/
                    reslut=reslut.replace("/index.php",url_suffix);
                    /**如果替换成功，表示这个网址可以访问使用*/
                    if(reslut.contains(url_suffix)){
                        return reslut;
                    }
                }
            }
        }
        /**如果都不符合条件，返回空串*/
        return "";
    }

    /**
     * 拿去post参数，获得中文返回页面
     * */
    public static List<NameValuePair> getPostParmList(){
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("session_language", "cn_CN"));
        return list;
    }

    public List<VedioBean> getList() {
        return list;
    }

    public void setList(List<VedioBean> list) {
        this.list = list;
    }

}
