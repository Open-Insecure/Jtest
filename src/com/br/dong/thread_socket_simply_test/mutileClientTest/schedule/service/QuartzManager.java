//package com.br.dong.thread_socket_simply_test.mutileClientTest.schedule.service;
//
//import com.br.dong.thread_socket_simply_test.mutileClientTest.schedule.bean.MyJob;
//import com.br.dong.thread_socket_simply_test.mutileClientTest.schedule.bean.MyTask;
//import org.quartz.*;
//import org.quartz.impl.StdSchedulerFactory;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.quartz.JobBuilder.newJob;
//import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
//import static org.quartz.TriggerBuilder.newTrigger;
//
///**
// * Created with IntelliJ IDEA.
// * User: hexor
// * Date: 2015-12-09
// * Time: 13:41
// * 任务管理器管理类
// */
//public class QuartzManager {
//    private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
//    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");   // 日期格式化
//    private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";//任务类
//    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";//触发器类
//    private static Date startTime = DateBuilder.nextGivenSecondDate(null, 5);//获得5秒后的时间
//    private static Map<String,MyTask> taskMap=new HashMap<String,MyTask>();//任务池
//
//    /***
//     * 添加一个定时任务
//     * @param jobName 任务名
//     * @param groupName 任务组名
//     * @param time 执行间隔
//     */
//    public static void addJob(String jobName,String groupName, int time,JobListener listener) throws SchedulerException {
//        /***
//         * 创建定时任务
//         */
//        JobDetail job  = newJob(MyJob.class).withIdentity(jobName, groupName).build();
//        /***
//         * 创建触发器
//         */
//        SimpleTrigger trigger = newTrigger()
//                .withIdentity(jobName, groupName)//触发器的名字
//                .startAt(startTime)
//                .withSchedule(
//                        simpleSchedule().withIntervalInSeconds(time)//执行间隔
//                                .repeatForever()).build();
//        Date ft = gSchedulerFactory.getScheduler().scheduleJob(job, trigger);
//        System.out.println(job.getKey().getName() + " 将在 : " + dateFormat.format(ft) + "时运行.并且重复: "
//                + trigger.getRepeatCount() + " 次, 每次间隔 "
//                + trigger.getRepeatInterval() / 1000 + " 秒");
//        taskMap.put(jobName,new MyTask(job,trigger));
//        gSchedulerFactory.getScheduler().start();
//        gSchedulerFactory.getScheduler().getListenerManager().addJobListener(listener);//添加自定义的监听器
//    }
//    /***
//     * 移除任务
//     * @param mapKey 任务池中的任务key
//     */
//    public static void removeJob(String mapKey) {
//        try {
//            MyTask task=taskMap.get(mapKey);
//            gSchedulerFactory.getScheduler().pauseJob(task.getJob().getKey());//暂停任务
//            gSchedulerFactory.getScheduler().pauseTrigger(task.getTrigger().getKey());//暂停触发器
//            gSchedulerFactory.getScheduler().deleteJob(task.getJob().getKey());//删除任务
//            System.out.println(task.getJob().getKey().getName() + "任务移除 ");
//            taskMap.remove(mapKey);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public static void main(String[] args) throws SchedulerException, InterruptedException {
//
////        addJob("test任务0","组名",3);
////        addJob("test任务1","组名",4);
////        addJob("test任务2","组名",5);
////        addJob("test任务3", "组名", 10);
////        System.out.println(gSchedulerFactory.getScheduler().getJobGroupNames());
////        Thread.sleep(11*1000);
////        System.out.println("移除触发器");
////        removeJob("test任务0");
//    }
//}
