package com.br.dong.thread_socket_simply_test.mutileClientTest.bean;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-11
 * Time: 11:00
 * 信息类接口
 * 所有的通信信息必须实现
 */
public interface IMessageObject {
    Object doAction(Object rev);
}
