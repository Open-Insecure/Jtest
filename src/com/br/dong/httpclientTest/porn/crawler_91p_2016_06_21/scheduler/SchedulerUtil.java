package com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21.scheduler;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21.scheduler
 * AUTHOR: hexOr
 * DATE :2016-06-21 20:14
 * DESCRIPTION:定时器工具类
 */
public class SchedulerUtil {
    private static Logger logger = Logger.getLogger(SchedulerUtil.class);
    private static Scheduler sched=null;
    private static final String DEFAULT_GROUP="DEFAULT_GROUP";/**默认任务组分组*/
    private static final String DEFAULT_TRIGGER="DEFAULT_TRIGGER";/**默认触发器分组*/
    /**初始化*/
    static {
        if(null==sched){
            SchedulerFactory sf = new StdSchedulerFactory();
            try {
                  sched = sf.getScheduler();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
   }


    public static void main(String[] args) throws SchedulerException {
//        addJob("job1",-1,10,Scheduler91PJob.class);
        addJob("job1",3,3,Scheduler91PJob.class);
        start();
        try {
            System.out.println("------- 等待50 s  ... ------------");
            Thread.sleep(50*1000);
        } catch (Exception e) { }
        showMetaData();
    }

    /***
     * 新增定时任务-外部接口
     * @param jobName
     * @param repeatCount
     * @param interval
     * @param jobClass
     * @throws SchedulerException
     */
    public static void addJob(String jobName,int repeatCount,int  interval,Class   jobClass ) throws SchedulerException {
        Date startTime = DateBuilder.nextGivenSecondDate(null, 5);/**启动时间*/
        addJob(jobName, DEFAULT_GROUP, DEFAULT_TRIGGER, repeatCount, interval, startTime, jobClass);
    }

    /***
     * 新增一个定时任务job-内部接口
     * @param jobName 任务名
     * @param groupName 组名
     * @param triggerName 触发器名
     * @param repeatCount 重复次数 -1表示无限
     * @param interval 重复间隔
     * @param startTime
     * @param jobClass
     */
    private static void addJob(String jobName,String groupName,String triggerName,int repeatCount,int interval, Date startTime,Class  jobClass ) throws SchedulerException {
        JobDetail job  = newJob(jobClass).withIdentity(jobName, groupName).build();
        SimpleTrigger trigger =  newTrigger() .withIdentity(triggerName, groupName)
                                 .startAt(startTime)/**启动时间*/
                                 .withSchedule(
                                 simpleSchedule()
                                 .withIntervalInSeconds(interval)/**重复间隔*/
                                 .withRepeatCount(repeatCount))  /**重复次数*/
                                 .build();
        Date ft = sched.scheduleJob(job, trigger);
    }

    /***
     * 定时器开始
     * @throws SchedulerException
     */
    public static void start() throws SchedulerException {
        if(null!=sched){
            sched.start();
        }
    }

    /***
     * 定时器停止
     * @param immediately 是否立刻停止
     * @throws SchedulerException
     */
    public static void shutdown(Boolean immediately) throws SchedulerException {
        if(null!=sched){
            sched.shutdown(immediately);
        }
    }

    /***
     * 展示定时器的相关信息
     * @throws SchedulerException
     */
    public static void showMetaData() throws SchedulerException {
        if(null!=sched){
            SchedulerMetaData metaData = sched.getMetaData();
            logger.info(metaData);
        }
    }

}
