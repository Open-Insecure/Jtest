package com.br.dong.thread_socket_simply_test.mutileClientTest;

import com.br.dong.thread_socket_simply_test.mutileClientTest.execute.CpuUsage;
import com.br.dong.thread_socket_simply_test.mutileClientTest.schedule.listener.MyJobListenerImpl;
import com.br.dong.thread_socket_simply_test.mutileClientTest.schedule.listener.MyJoblListener;
import com.br.dong.thread_socket_simply_test.mutileClientTest.schedule.service.QuartzManager;
import com.br.dong.thread_socket_simply_test.mutileClientTest.util.PropertiesUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;
import java.io.IOException;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-10
 * Time: 14:39
 */
public class ClientStart {
    JobListener listener=null;
    public static void main(String[] args) throws IOException, SchedulerException {
        ClientStart clientStart=new ClientStart();
        clientStart.init();
    }
    /**
     * 初始化方法
     */
    private  void init() throws IOException, SchedulerException {
        System.out.println("system init..");
        PropertiesUtil propertiesUtil=PropertiesUtil.getInstance();
        Socket socket=new Socket(propertiesUtil.getPropertiesValue("config.ipaddress"),Integer.parseInt(propertiesUtil.getPropertiesValue("config.port")));
        while(true){
            SocketClient.socketConnect(socket,CpuUsage.testGetIdle());
        }
        //创建socket链接
//        listener=new MyJoblListener() {
//            private PropertiesUtil propertiesUtil=null;
//
//            @Override
//            public String getName() {
//                return "myJobListener";
//            }
//            @Override
//            public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
//            }
//            @Override
//            public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
//            }
//            /***
//             * 任务执行完了
//             * @param jobExecutionContext
//             * @param e
//             */
//            @Override
//            public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
//                try {
//                    SocketClient.socketConnect(socket,CpuUsage.testGetIdle());
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        };
//        listener= new MyJobListenerImpl(socket);
//        QuartzManager.addJob("cpu使用率", "系统类", 3, listener);//初始化定时器配置
    }

    /***
     * 操作系统判断
     * @return
     */
    public String osIdentify(){

        return "";
    }
}
