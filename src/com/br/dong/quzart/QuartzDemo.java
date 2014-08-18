package com.br.dong.quzart;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created with IntelliJ IDEA.
 * User: rdpc0848
 * Date: 13-10-31
 * Time:  
 * To change this template use File | Settings | File Templates.
 */
public class QuartzDemo implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("aaa");
    }
}
