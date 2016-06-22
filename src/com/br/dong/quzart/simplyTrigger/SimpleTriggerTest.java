package com.br.dong.quzart.simplyTrigger;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-1-14
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTriggerTest {
    public static void main(String[] args) throws Exception {
        SimpleTriggerTest test=new SimpleTriggerTest();
        test.run();

    }

    public void run() throws Exception{
        // 日期格式化
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy 年 MM 月 dd 日  HH 时 mm 分 ss 秒");

        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        System.out.println("--------------- 初始化 -------------------");


        Date startTime = DateBuilder.nextGivenSecondDate(null, 3);


        JobDetail job = newJob(SimpleJob.class).withIdentity("job3", "group1").build();
        SimpleTrigger  trigger = newTrigger()
                .withIdentity("trigger3", "group1")
                .startAt(startTime)
                .withSchedule(
                        simpleSchedule()
                                .withIntervalInSeconds(1)// 重复间隔
                                .withRepeatCount(-1))     // 重复次数
                .build();
        Date  ft = sched.scheduleJob(job, trigger);
        System.out.println(job.getKey().getName()+ " 将在 : " + dateFormat.format(ft) + " 时运行.并且重复: "
                + trigger.getRepeatCount() + " 次, 每次间隔 "
                + trigger.getRepeatInterval() / 1000 + " 秒");
        sched.start();
        try {
            System.out.println("------- 等待5分钟  ... ------------");
            Thread.sleep(300L * 1000L);
        } catch (Exception e) { }


        sched.shutdown(true);
        System.out.println("------- 调度已关闭 ---------------------");

        // 显示一下  已经执行的任务信息
        SchedulerMetaData metaData = sched.getMetaData();
        System.out.println("~~~~~~~~~~  执行了 " + metaData.getNumberOfJobsExecuted() + " 个 jobs.");

    }
}
