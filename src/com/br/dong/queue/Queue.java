package com.br.dong.queue;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-08
 * Time: 9:02
 * LinkedList实现的共享消息队列,可以多线程调
 */
public class Queue {
    private LinkedList<Object> msgList = new LinkedList<Object>();

    public Object getMsg() {
        synchronized (this) {
            if (msgList != null && msgList.size() > 0) {
                return msgList.removeFirst();
            }
            return null;
        }
    }

    public Object addMsg(Object obj) {
        synchronized(this) {
            msgList.addLast(obj);
        }
        return obj;
    }
}
