package com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21.scheduler;

import com.br.dong.httpclientTest.CrawlerUtil;
import org.apache.http.HttpResponse;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21.scheduler
 * AUTHOR: hexOr
 * DATE :2016-06-21 20:25
 * DESCRIPTION:针对91porn的采集任务
 */
public class Scheduler91PJob extends SchedulerBaseJob {

    private String url_for_91p="";
    public Scheduler91PJob() {
       try{
           url_for_91p=HTTP+HOST+NEW_SUFFIX+1;
            crawlerUtil.clientCreate("http", HOST, url_for_91p);
       }catch (Exception e){
           e.printStackTrace();
           logger.info("init Scheduler91PJob.class error"+e.getMessage());
       }
    }



    @Override
    public void myExecute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        logger.info(propertiesUtil.getPropValue("TEST"));
         logger.info(jobName+"初始化");
        HttpResponse response=crawlerUtil.post(url_for_91p, crawlerUtil.produceEntity(getPostParmList()));
        Document doc=crawlerUtil.getDocUTF8(response);
//        System.out.println(doc.toString());
        Elements videobox=doc.select("div[class*=listchannel]");
        System.out.println(url_for_91p+"视频总数:"+videobox.size()+"个");
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
            /**获得添加时间*/
            String updatetime=e.text().substring(e.text().indexOf("添加时间"),e.text().indexOf("作者:"));
            System.out.println(title + " " + preImgSrc + " " + vedioUrl + " " + infotime + " " +updatetime);

        }

        System.out.println(crawlerUtil.getCookieStore().toString());

    }
}
