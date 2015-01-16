//package com.br.dong.quzart.dongtai;
//import org.quartz.CronTrigger;
//import org.quartz.JobDetail;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import java.text.ParseException;
//
///**
//* Created with IntelliJ IDEA.
//* User: Administrator
//* Date: 14-12-31
//* Time: 下午4:41
//* To change this template use File | Settings | File Templates.
//*/
//public class Test {
//    public static void main(String[] args) throws SchedulerException, ParseException {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("/com/br/dong/quzart/dongtai/pa.xml");
//        Scheduler scheduler = (Scheduler)ctx.getBean("scheduler");
//
//        System.out.println("Scheduling to run tasks.");
//        for (int i = 0; i < 5; i++) {
//            try {
//                JobDetail jobDetail = new JobDetail();
//                jobDetail.setName("job_" + i);
//                MyTask myTask = new MyTask();
//                myTask.setName("task_" + i);
//                jobDetail.getJobDataMap().put("myTask", myTask);
//                jobDetail.setJobClass(MyJob.class);
//                scheduler.addJob(jobDetail, true);
//
//                CronTrigger cronTrigger =new CronTrigger("cron_" + i, Scheduler.DEFAULT_GROUP, jobDetail.getName(), Scheduler.DEFAULT_GROUP);
//                cronTrigger.setCronExpression("0/20 * * * * ?");
//                scheduler.scheduleJob(cronTrigger);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            } catch (SchedulerException e) {
//                e.printStackTrace();
//            }
//        }
//
//        try {
//            Thread.sleep(60 * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Un-scheduling to run tasks.");
//        //卸载一个
//        for (int i = 0; i < 5; i++) {
//            try {
//                scheduler.unscheduleJob("cron_" + i, Scheduler.DEFAULT_GROUP);
//            } catch (SchedulerException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("开始加载一个");
//
//        JobDetail jobDetail = new JobDetail();
//        jobDetail.setName("job_apdo");
//        MyTask myTask = new MyTask();
//        myTask.setName("task_apdo");
//        jobDetail.getJobDataMap().put("myTask", myTask);
//        jobDetail.setJobClass(MyJob.class);
//        scheduler.addJob(jobDetail, true);
//
//        CronTrigger cronTrigger =new CronTrigger("cron_apdo" , Scheduler.DEFAULT_GROUP, jobDetail.getName(), Scheduler.DEFAULT_GROUP);
//        cronTrigger.setCronExpression("0/5 * * * * ?");
//        scheduler.scheduleJob(cronTrigger);
//    }
//}
