package com.br.dong.httpclientTest.youbb.net.youbfor91shipin;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-10-12
 * Time: 13:57
 */
public class YoubBeanFor91shipin {
    private String title; //视频标题
    private String preImgSrc;//图片链接
    private String vedioUrl; //视频播放地址
    private String infotime;  //时长
    private String updatetime;//更新日期
    private String videoId;//视频id
    private int flag=0;        //是否采集标志    0表示未采集 1表示采集
    private int rate=0;//视频被赞数
    private int views=0;//视频被观看数
    private int favourite=0;//视频被收藏次数
    private String type="";

    public YoubBeanFor91shipin(String title, String preImgSrc, String vedioUrl, String infotime, String updatetime, String videoId) {
        this.title = title;
        this.preImgSrc = preImgSrc;
        this.vedioUrl = vedioUrl;
        this.infotime = infotime;
        this.updatetime = updatetime;
        this.videoId = videoId;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreImgSrc() {
        return preImgSrc;
    }

    public void setPreImgSrc(String preImgSrc) {
        this.preImgSrc = preImgSrc;
    }

    public String getVedioUrl() {
        return vedioUrl;
    }

    public void setVedioUrl(String vedioUrl) {
        this.vedioUrl = vedioUrl;
    }

    public String getInfotime() {
        return infotime;
    }

    public void setInfotime(String infotime) {
        this.infotime = infotime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
