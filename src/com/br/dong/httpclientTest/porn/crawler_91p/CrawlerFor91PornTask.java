package com.br.dong.httpclientTest.porn.crawler_91p;

import com.br.dong.utils.PropertiesUtil;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.httpclientTest.porn.crawler_91p
 * AUTHOR: hexOr
 * DATE :2016-06-17 9:56
 * DESCRIPTION:91视频采集任务主程序
 */
public class CrawlerFor91PornTask {
    private static Logger logger = Logger.getLogger(CrawlerFor91PornTask.class);//日志
    private static PropertiesUtil propertiesUtil=PropertiesUtil.getInstance("/com/br/dong/httpclientTest/porn/crawler_91p/config/config.properties");//读取配置文件
    private static String TEST=propertiesUtil.getPropValue("TEST");
    public static void main(String[] args) {
        System.out.println(TEST);
    }
}
