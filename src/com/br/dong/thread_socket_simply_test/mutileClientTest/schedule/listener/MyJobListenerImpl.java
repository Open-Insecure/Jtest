package com.br.dong.thread_socket_simply_test.mutileClientTest.schedule.listener;

import com.br.dong.thread_socket_simply_test.mutileClientTest.SocketClient;
import com.br.dong.thread_socket_simply_test.mutileClientTest.execute.CpuUsage;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import java.io.IOException;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-11
 * Time: 9:20
 */
public class MyJobListenerImpl implements JobListener {

    public MyJobListenerImpl(Socket socket) {
        this.socket = socket;
    }

    private Socket socket=null;
    @Override
    public String getName() {
        return null;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {

    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {

    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        try {
            SocketClient.socketConnect(socket, CpuUsage.testGetIdle());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
