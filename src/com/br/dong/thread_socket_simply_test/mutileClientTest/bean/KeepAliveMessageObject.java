package com.br.dong.thread_socket_simply_test.mutileClientTest.bean;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-11
 * Time: 11:07
 */
public class KeepAliveMessageObject  implements IMessageObject {
    @Override
    public Object doAction(Object rev) {
        System.out.println("保持连接："+rev);
        return rev;
    }
}
