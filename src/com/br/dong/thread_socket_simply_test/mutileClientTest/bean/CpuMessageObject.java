package com.br.dong.thread_socket_simply_test.mutileClientTest.bean;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-11
 * Time: 11:01
 * cpu 通信信息
 */
public class CpuMessageObject implements IMessageObject {
    @Override
    public Object doAction(Object rev) {
        System.out.println("cpu信息："+rev);
        return rev;
    }
}
