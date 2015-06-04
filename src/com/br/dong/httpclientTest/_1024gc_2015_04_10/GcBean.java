package com.br.dong.httpclientTest._1024gc_2015_04_10;

import com.br.dong.utils.DateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-4-18
 * Time: 16:35
 * 针对
 * http://cb.1024gc.info/bbs/ 1024核工厂的资源采集下来的bean
 */
public class GcBean {
    private int id;//id号
    private String resourceSite;//资源的来源网站
    private String type;//资源的类型如:txt ,img,torrent
    private String name;//资源的名称
    private String content;//资源的描述
    private String resourceUrl;//包含资源的帖子的url地址
    private String origDownloadUrl;//资源的原始下载地址
    private String downloadUrl="http://www.1024wp.com/";//资源的网盘的下载地址
    private String imgUrls;//资源的图片链接地址
    private String updatetime= DateUtil.getStrOfDateTime();
    private String section="";
    public GcBean() {
    }

    public GcBean(String name, String content, String origDownloadUrl, String imgUrls) {
        this.name = name;
        this.content = content;
        this.origDownloadUrl = origDownloadUrl;
        this.imgUrls = imgUrls;
    }

    public GcBean(String resourceSite, String type, String name, String content, String resourceUrl, String origDownloadUrl, String downloadUrl, String imgUrls,String section) {
        this.resourceSite = resourceSite;
        this.type = type;
        this.name = name;
        this.content = content;
        this.resourceUrl = resourceUrl;
        this.origDownloadUrl = origDownloadUrl;
        this.downloadUrl = downloadUrl;
        this.imgUrls = imgUrls;
        this.section=section;
    }

    @Override
    public String toString() {
        return "GcBean{" +
                "id=" + id +
                ", resourceSite='" + resourceSite + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", resourceUrl='" + resourceUrl + '\'' +
                ", origDownloadUrl='" + origDownloadUrl + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", imgUrls='" + imgUrls + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrigDownloadUrl() {
        return origDownloadUrl;
    }

    public void setOrigDownloadUrl(String origDownloadUrl) {
        this.origDownloadUrl = origDownloadUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getResourceSite() {
        return resourceSite;
    }

    public void setResourceSite(String resourceSite) {
        this.resourceSite = resourceSite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
