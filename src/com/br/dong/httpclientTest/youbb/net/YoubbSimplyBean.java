package com.br.dong.httpclientTest.youbb.net;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-07-08
 * Time: 23:08
 * 测试遗留类，可以参考。
 */
@Deprecated
public class YoubbSimplyBean {
    private String vkey;//视频vkey
    private String title;//视频标题
    private String time;//视频时长

    public YoubbSimplyBean(String vkey, String title, String time) {
        this.vkey = vkey;
        this.title = title;
        this.time = time;
    }

    public String getVkey() {
        return vkey;
    }

    public void setVkey(String vkey) {
        this.vkey = vkey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
