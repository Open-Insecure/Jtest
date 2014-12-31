package com.br.dong.quzart.dongtai;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-12-31
 * Time: 下午4:39
 * To change this template use File | Settings | File Templates.
 */
public class MyJob extends QuartzJobBean {
    private MyTask myTask;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //To change body of implemented methods use File | Settings | File Templates.
        myTask.run();
    }

    public MyTask getMyTask() {
        return myTask;
    }

    public void setMyTask(MyTask myTask) {
        this.myTask = myTask;
    }
}
