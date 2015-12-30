package com.br.dong.thread_socket_simply_test.mutileClientTest.schedule.bean;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-09
 * Time: 14:45
 */
public class MyTask {
    private JobDetail job;
    private SimpleTrigger trigger;

    public MyTask(JobDetail job, SimpleTrigger trigger) {
        this.job = job;
        this.trigger = trigger;
    }

    public JobDetail getJob() {
        return job;
    }

    public void setJob(JobDetail job) {
        this.job = job;
    }

    public SimpleTrigger getTrigger() {
        return trigger;
    }

    public void setTrigger(SimpleTrigger trigger) {
        this.trigger = trigger;
    }
}
