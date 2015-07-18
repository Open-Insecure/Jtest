package com.br.dong.httpclientTest.youbb.net;

import com.br.dong.utils.DateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-07-08
 * Time: 21:49
 * 针对myvidesite[94lu]项目的采集程序的bean
 * 适配 [94lu]数据表video的字段
 */
public class YoubbBean {
    private int id;//视频id
    private String title; //视频标题
    private String imgName;//图片名字
    private String videoName; //视频播放地址
    private String infotime;  //时长
    private String updatetime= DateUtil.getStrOfDateTime();//更新日期
    private int rate=0;//视频被赞数
    private int views=0;//视频被观看数
    private int favourite=0;//视频被收藏次数
    private int viewAuthority=0;//观看该视频的权限 -1标示不能看

    public YoubbBean(String title, String imgName, String videoName, String infotime) {
        this.title = title;
        this.imgName = imgName;
        this.videoName = videoName;
        this.infotime = infotime;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
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

    public int getViewAuthority() {
        return viewAuthority;
    }

    public void setViewAuthority(int viewAuthority) {
        this.viewAuthority = viewAuthority;
    }
}
