package com.br.dong.httpclientTest._91porn_2017_03_01.bean;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.httpclientTest._91porn_2017_03_01.bean
 * AUTHOR: hexOr
 * DATE :2017/3/2 10:41
 * DESCRIPTION:
 */
public class _91pornVideoBean {
    private String title;//标题
    private String fileName;//文件名
    private String preImgSrc;//获得预览图片链接
    private String videoPageUrl;//视频链接地址
    private String infotime;//获得时长
    private String updatetime;//获得添加时间
    private String author;//作者信息
    private String description;//视频描述
    private String videoPath;//下载后的视频的存储路径
    private String imgPath;//下载后的视频的预览图片的存储路径

    /***
     * 构造方法
     * @param title
     * @param preImgSrc
     * @param videoPageUrl
     * @param infotime
     * @param updatetime
     * @param author
     * @param description
     */
    public _91pornVideoBean(String title, String preImgSrc, String videoPageUrl, String infotime, String updatetime, String author, String description) {
        this.title = title;
        this.preImgSrc = preImgSrc;
        this.videoPageUrl = videoPageUrl;
        this.infotime = infotime;
        this.updatetime = updatetime;
        this.author = author;
        this.description = description;
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

    public String getVideoPageUrl() {
        return videoPageUrl;
    }

    public void setVideoPageUrl(String videoPageUrl) {
        this.videoPageUrl = videoPageUrl;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
