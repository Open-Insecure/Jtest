package com.br.dong.thread_socket_simply_test.mutileClientTest.schedule.bean;

import com.br.dong.thread_socket_simply_test.mutileClientTest.SocketClient;
import com.br.dong.thread_socket_simply_test.mutileClientTest.execute.CpuUsage;
import com.br.dong.thread_socket_simply_test.mutileClientTest.util.PropertiesUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-09
 * Time: 13:35
 * 定时器任务类
 */
public class MyJob implements Job {
    private PropertiesUtil propertiesUtil=null;
    private SocketClient socketClient=new SocketClient();
    /***
     * 任务执行计划
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // job 的名字
        String jobName = context.getJobDetail().getKey().getName();
        // 任务执行的时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒 ");
        String jobRunTime = dateFormat.format(Calendar.getInstance().getTime());
        // 输出任务执行情况
        System.out.println("任务:" + jobName + "在" + jobRunTime + "执行了 ");
        try {
            propertiesUtil=PropertiesUtil.getInstance();
            Socket socket=new Socket(propertiesUtil.getPropertiesValue("config.ipaddress"),Integer.parseInt(propertiesUtil.getPropertiesValue("config.port")));
            SocketClient.socketConnect(socket,CpuUsage.testGetIdle());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
