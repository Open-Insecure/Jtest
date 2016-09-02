package com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21.repo;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21.repo
 * AUTHOR: hexOr
 * DATE :2016-06-24 16:45
 * DESCRIPTION:视频信息类
 */
public class VideoBean {
    private int id;
    private String title;
    private String preImgSrc;
    private String vedioUrl;
    private String infotime;
    private String updatetime;
    private String author;

    public VideoBean() {

    }

    public VideoBean(String title, String preImgSrc, String vedioUrl, String infotime, String updatetime, String author) {
        this.title = title;
        this.preImgSrc = preImgSrc;
        this.vedioUrl = vedioUrl;
        this.infotime = infotime;
        this.updatetime = updatetime;
        this.author = author;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
