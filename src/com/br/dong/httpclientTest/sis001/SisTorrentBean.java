package com.br.dong.httpclientTest.sis001;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-9-29
 * Time: 下午4:08
 * To change this template use File | Settings | File Templates.
 * sis001 视频bt种子
 */
public class SisTorrentBean {
    private String flag;//bt种子所属的本地文件夹名字分类
    private String type;//bt种子所属分类，如国产，韩国等
    private String title;//标题
    private String url;//当前种子帖子所在的url
    private String size;//bt种子所对应的下载文件的大小
    private String torrentUrl;//下载bt种子的url
    private String time;//采集日期
    private String picUrl;//bt种子视频预览图片地址
    private int downloads;//bt种子被下载的次数
    //System.out.println(type+"|"+url+"|"+time+"|"+title+"|"+size);


    public SisTorrentBean(String flag,String type, String title, String url, String size, String time) {
        this.flag = flag;
        this.type = type;
        this.title = title;
        this.url = url;
        this.size = size;
        this.time = time;
    }

    @Override
    public String toString() {
        return "SisTorrentBean{" +
                "type='" + type + '\'' +
                ", flag='" + flag + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", size='" + size + '\'' +
                ", torrentUrl='" + torrentUrl + '\'' +
                ", time='" + time + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", downloads=" + downloads +
                '}';
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTorrentUrl() {
        return torrentUrl;
    }

    public void setTorrentUrl(String torrentUrl) {
        this.torrentUrl = torrentUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }
}
