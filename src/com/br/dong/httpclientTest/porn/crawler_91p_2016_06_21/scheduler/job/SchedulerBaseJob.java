package com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21.scheduler.job;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.utils.PropertiesUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21.scheduler
 * AUTHOR: hexOr
 * DATE :2016-06-21 20:15
 * DESCRIPTION:抽象类 定时器基本job
 */
public abstract class SchedulerBaseJob implements Job{
    protected static Logger logger = Logger.getLogger(SchedulerBaseJob.class);
    protected CrawlerUtil crawlerUtil = new CrawlerUtil();
    protected static PropertiesUtil propertiesUtil=PropertiesUtil.getInstance("/com/br/dong/httpclientTest/porn/crawler_91p_2016_06_21/config/config.properties");/**读取配置文件*/
    protected static final String HTTP= propertiesUtil.getPropValue("HTTP") ;/**Http地址前缀*/
    protected static final String HOST= propertiesUtil.getPropValue("HOST") ;/**主机HOST*/
    protected static final String NEW_SUFFIX=propertiesUtil.getPropValue("NEW_SUFFIX");/**最新前缀*/
    protected  String jobName="";/**任务名*/
    /***
     * 如果你在类中声明了带参数的构造函数，会自动覆盖无参数的构造函数，这样系统就无法调用无参数的构造函数实例化类，所以会出现这种错误。
     * 这个问题在使用类反射实例化某个对象时,如果这个对象不包含无参数的构造函数,也会出现这个错误,解决办法是在这个要通过类反射实例化的这个类中添加一个空的无参数构造函数就可以了.
     */
    public SchedulerBaseJob() {
    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        jobName=jobExecutionContext.getJobDetail().getKey().getName();/**job的名字*/
        myExecute(jobExecutionContext);
    }

    /***
     * 此方法才是其他定时器job要重写的
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    public abstract void myExecute(JobExecutionContext jobExecutionContext)  throws JobExecutionException;

    /**
     * 拿去post参数，获得中文返回页面
     * */
    public static List<NameValuePair> getPostParmList(){
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("session_language", "cn_CN"));
        return list;
    }

}
