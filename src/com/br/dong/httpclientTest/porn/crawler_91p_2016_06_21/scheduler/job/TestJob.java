package com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21.scheduler.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21.scheduler.job
 * AUTHOR: hexOr
 * DATE :2016-09-05 13:38
 * DESCRIPTION:
 */
public class TestJob extends SchedulerBaseJob {
    @Override
    public void myExecute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("test job start ");
    }
}
