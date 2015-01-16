package com.br.dong.quzart.simplyTrigger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-1-14
 * Time: 下午3:36
 * To change this template use File | Settings | File Templates.
 */
public class SimpleJob implements Job {

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        // job 的名字
        String jobName = context.getJobDetail().getKey().getName();

        // 任务执行的时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy 年 MM 月 dd 日  HH 时 mm 分 ss 秒");
        String jobRunTime = dateFormat.format(Calendar.getInstance().getTime());

        // 输出任务执行情况
        System.out.println("任务 : " + jobName + " 在  " +jobRunTime + " 执行了 ");
    }
}
