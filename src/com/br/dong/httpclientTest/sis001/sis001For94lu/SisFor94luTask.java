package com.br.dong.httpclientTest.sis001.sis001For94lu;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.utils.PropertiesUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-08-25
 * Time: 20:43
 * 新的sis001的采集程序，专门给94lu采集种子与图片的程序
 */
public class SisFor94luTask extends SisBase{
    private static Logger logger = Logger.getLogger(SisFor94luTask.class);//日志

    private static String [] sits={
            "bt,"+baseUrl+"forum/forum-25-,bt亚洲无码转帖",
            "bt,"+baseUrl+"/forum/forum-58-,bt亚洲有码转帖",
            "bt,"+baseUrl+"/forum/forum-77-,bt欧美无码",
            "bt,"+baseUrl+"/forum/forum-27-,bt成人游戏卡通漫画转区",
            "bt,"+baseUrl+"/forum/forum-143-,bt亚洲无码原创区",
            "bt,"+baseUrl+"/forum/forum-426-,bt情色三级",
//            "url,"+baseUrl+"/forum/forum-187-,url外链成人网盘",
//            "url,"+baseUrl+"/forum/forum-270-,url外链电驴",
//            "url,"+baseUrl+"/forum/forum-212-,url外链迅雷",
            "pic_no_download,"+baseUrl+"/forum/forum-242-,pic熟女乱伦图片分享区",
            "pic_no_download,"+baseUrl+"/forum/forum-68-,pic西洋靓女骚妹",
            "pic_no_download,"+baseUrl+"/forum/forum-60-,pic动漫卡通游戏贴图区",
            "pic_no_download,"+baseUrl+"/forum/forum-64-,pic东方靓女集中营",
            "pic_no_download,"+baseUrl+"/forum/forum-184-,pic精品套图鉴赏区",
            "pic_no_download,"+baseUrl+"/forum/forum-219-,pic高跟美足丝袜区",
            "pic_no_download,"+baseUrl+"/forum/forum-62-,pic网友自拍贴图分享区"
//            "pic_no_download,"+baseUrl+"/forum/forum-61-,pic星梦奇缘合成天堂",//图片在附件
//            "txt_download,"+baseUrl+"/forum/forum-383-,txt原创人生区",
//            "txt_download,"+baseUrl+"/forum/forum-279-,txt人妻意淫区",
//            "txt_download,"+baseUrl+"/forum/forum-83-,txt乱伦迷情区",
//            "txt_download,"+baseUrl+"/forum/forum-96-,txt武侠玄幻区",
//            "txt_download,"+baseUrl+"/forum/forum-31-,txt另类其它区",
//            "txt_download,"+baseUrl+"/forum/forum-385-,txt电子书下载",
//            "txt_download,"+baseUrl+"/forum/forum-368-,txt杂志下载"
    };
    public static void main(String[] args) {
        //登录
        Boolean loginflag=false;
        loginflag= login(username,password);
        if(!loginflag){
           logger.info(username + "login error,try login again！");
            return ;
        }
        logger.info(username + "login success!");
    }

}
