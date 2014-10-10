package com.br.dong.httpclientTest.sis001;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-10-9
 * Time: 下午4:36
 * To change this template use File | Settings | File Templates.
 *
 */
public class Urls {
    private String type;//类型 0 种子 1图片 2小说
    private String title;//标题
    private String url; //对应url
    private String floderName;//所要相对路径存放的文件夹名字

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFloderName() {
        return floderName;
    }

    public void setFloderName(String floderName) {
        this.floderName = floderName;
    }
}
