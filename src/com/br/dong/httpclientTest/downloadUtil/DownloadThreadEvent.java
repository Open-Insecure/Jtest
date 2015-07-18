package com.br.dong.httpclientTest.downloadUtil;

import java.util.EventObject;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-07-10
 * Time: 21:22
 */
public class DownloadThreadEvent extends EventObject {
    private static final long serialVersionUID = 1L;
    Object sourObject;//被监听的事件源
    float count;

    public DownloadThreadEvent(Object sourceObject, float count) {
        super(sourceObject);
        this.sourObject = sourceObject;
        this.count = count;
    }

    public float getCount() {
        return count;
    }

    Object getTarget() {
        return sourObject;
    }
}
