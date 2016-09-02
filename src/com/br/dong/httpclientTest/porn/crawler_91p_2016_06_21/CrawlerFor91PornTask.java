package com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21;

import com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21.scheduler.SchedulerUtil;
import com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21.scheduler.job.Scheduler91PJob;
import com.br.dong.utils.PropertiesUtil;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21
 * AUTHOR: hexOr
 * DATE :2016-06-17 9:56
 * DESCRIPTION:91视频采集任务主程序
 */
public class CrawlerFor91PornTask {
    private static Logger logger = Logger.getLogger(CrawlerFor91PornTask.class);//日志
    private static PropertiesUtil propertiesUtil=PropertiesUtil.getInstance("/com/br/dong/httpclientTest/porn/crawler_91p_2016_06_21/config/config.properties");//读取配置文件
    public static void main(String[] args) {
        try {
            /***
             * 首先根据mysql库中video表中是否已经有数据来判断这是不是新库的第一次采集
             * 如果是新库的第一次采集则创建
             * 如果是已经采集过的库需要持续更新则
             * 创建针对91porn视频的更新任务
             */
            SchedulerUtil.addJob("job1", -1, 1, Scheduler91PJob.class);
            SchedulerUtil.start();
            System.out.println("------- 等待50 s  ... ------------");
            Thread.sleep(50 * 1000);
            SchedulerUtil.showMetaData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
