package com.br.dong.quzart;


import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: rdpc0848
 * Date: 13-10-31
 * Time:  
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    private static final SimpleTrigger cronTrigger=null;
    /**
     *  
     * */

     public static void main(String[] agrs){
         System.out.print("--启动--- ");
        long ctime=System.currentTimeMillis();
        System.out.print("--time:"+new Date(ctime));
        Test test=new Test();
        test.test1();

    }
     /**
      *  
      * */
    public void test1(){
        SchedulerFactory schedulerFactory=new StdSchedulerFactory();
        Scheduler scheduler;
        try{
            long ctime=System.currentTimeMillis();
            
            scheduler=schedulerFactory.getScheduler();
            JobDetailImpl jobDetailImpl = new JobDetailImpl("jobDetail-s1","jobDeatilGroup",QuartzDemo.class);
			JobDetailImpl jobDetail= jobDetailImpl;
            SimpleTriggerImpl simpleTrigger=new SimpleTriggerImpl("simpleTrigger","triggerGroup");
            simpleTrigger.setStartTime(new Date((ctime)));
            simpleTrigger.setRepeatInterval(1000);
            simpleTrigger.setRepeatCount(10);
            scheduler.scheduleJob(jobDetail,simpleTrigger);
            scheduler.start();
         } catch (SchedulerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
