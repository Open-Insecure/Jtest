package com.br.dong.httpclientTest.downloadUtil;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-07-10
 * Time: 21:21
 */

import java.util.EventListener;

/**
 *具体的对监听的事件类 继承EventListener
 */
interface DownloadThreadListener extends EventListener {

    /**
     * 下载过程的监听方法
     * @param event
     */
    public void afterPerDown(DownloadThreadEvent event);

    /**
     * 子线程下载完毕的监听方法
     * @param event
     */
    public void downCompleted(DownloadThreadEvent event);
}
