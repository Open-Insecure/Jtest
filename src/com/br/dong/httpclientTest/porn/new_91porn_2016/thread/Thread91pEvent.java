package com.br.dong.httpclientTest.porn.new_91porn_2016.thread;

import java.util.EventObject;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2016-01-13
 * Time: 16:31
 *
 */
public class Thread91pEvent extends EventObject {
    private static final long serialVersionUID = 1L;
    Object sourObject;//被监听的事件源
    String message;//提示信息
    public Thread91pEvent(Object sourceObject,String message) {
        super(sourceObject);
        this.sourObject = sourceObject;
        this.message=message;
    }

    public Object getTarget() {
        return sourObject;
    }

    public String getMessage(){return message;}
}
