package com.br.dong.thread_socket_simply_test.mutileClientTest.schedule.listener;

import org.quartz.JobListener;

import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-10
 * Time: 14:29
 * 自定义job监听
 */
public interface MyJoblListener extends JobListener {
     Socket socket = null;

}
