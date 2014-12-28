package com.br.dong.httpclientTest.porn;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-8-20
 * Time: 下午4:56
 * To change this template use File | Settings | File Templates.
 *
 */

public class VedioBean {
     private String title; //视频标题
     private String preImgSrc;//图片链接
     private String vedioUrl; //视频播放地址 mp4文件地址
     private String infotime;  //时长
     private String updatetime;//更新日期
     private String videoId;//视频id
     private int flag=0;        // 暂时用来当做文件mb大小
     private String type=""; //用来当做外联的类型 如91 或者caopron



    @Override
    public String toString() {
        return "VedioBean{" +
                "title='" + title + '\'' +
                ", preImgSrc='" + preImgSrc + '\'' +
                ", vedioUrl='" + vedioUrl + '\'' +
                ", infotime='" + infotime + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", videoId='" + videoId + '\'' +
                ", flag=" + flag +
                ", type=" + type +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public VedioBean(String title, String preImgSrc, String vedioUrl, String infotime, String updatetime, String vkey, String type) {
        this.title = title;
        this.preImgSrc = preImgSrc;
        this.vedioUrl = vedioUrl;
        this.infotime = infotime;
        this.updatetime = updatetime;
        this.type = type;
        this.videoId=vkey;
    }
    public VedioBean(String title, String preImgSrc, String vedioUrl, String infotime, String updatetime, String type) {
        this.title = title;
        this.preImgSrc = preImgSrc;
        this.vedioUrl = vedioUrl;
        this.infotime = infotime;
        this.updatetime = updatetime;
        this.type = type;
    }

    public VedioBean(String title, String preImgSrc, String vedioUrl, String infotime, String updatetime, int flag) {
        this.title = title;
        this.preImgSrc = preImgSrc;
        this.vedioUrl = vedioUrl;
        this.infotime = infotime;
        this.updatetime = updatetime;
        this.flag = flag;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public VedioBean() {
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

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
