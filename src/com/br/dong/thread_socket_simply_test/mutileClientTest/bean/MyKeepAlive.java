package com.br.dong.thread_socket_simply_test.mutileClientTest.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-11
 * Time: 10:49
 */
public class MyKeepAlive implements Serializable {
    private static final long serialVersionUID = -2813120366138988480L;

    /* 覆盖该方法，仅用于测试使用。
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\t 维持连接包";
    }
}
