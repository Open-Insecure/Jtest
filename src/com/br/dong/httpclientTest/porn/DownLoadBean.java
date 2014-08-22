package com.br.dong.httpclientTest.porn;

import org.jsoup.nodes.Document;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-8-22
 * Time: 下午12:03
 * To change this template use File | Settings | File Templates.
 * 此类作为下载vedio所需要的一些参数
 */
public class DownLoadBean {
    private Thread thread;   //当前下载类对应的线程
    private String vedioUrl; //视频页面观看的地址
    private ProxyBean proxy; //所使用的代理
    private String fileName; //文件名字

    public ProxyBean resetProxy(){
        this.proxy=JdbcUtil.getProxy();
        System.out.println(thread.getName()+"重新设置代理服务器:"+this.proxy.toString());
        return this.proxy;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    private Document doc;    //响应文档


    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public String getVedioUrl() {
        return vedioUrl;
    }

    public void setVedioUrl(String vedioUrl) {
        this.vedioUrl = vedioUrl;
    }

    public ProxyBean getProxy() {
        return proxy;
    }

    public void setProxy(ProxyBean proxy) {
        this.proxy = proxy;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
