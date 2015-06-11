package com.br.dong.httpclientTest._1024gc_2015_04_10;

import com.br.dong.utils.DateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-6-5
 * Time: 16:08
 * 发布日志表实例
 * 用来记录发布到网站的帖子的记录，防止重复发布
 */
public class PostLogBean {
    private int id;//id号
    private int collectLogsId;//发布的帖子对应的采集帖子的id
    private String postSite;//发布的网站
    private String postUrl;//发布的Url地址
    private String postTime= DateUtil.getStrOfDateTime();//发布的时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCollectLogsId() {
        return collectLogsId;
    }

    public void setCollectLogsId(int collectLogsId) {
        this.collectLogsId = collectLogsId;
    }

    public String getPostSite() {
        return postSite;
    }

    public void setPostSite(String postSite) {
        this.postSite = postSite;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }
}
