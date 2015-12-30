package com.br.dong.httpclientTest.skonlt_auto_post_2015_12_30;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.youbb.net.YoubbBean;
import com.br.dong.json.JsonUtil;
import net.sf.json.JSONArray;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-30
 * Time: 13:20
 * 针对司空论坛的自动登录与发帖程序
 */
public class SkonltAutoPost {
    private static  CrawlerUtil crawlerUtil=new CrawlerUtil();
    /**登录接口*/
    private static String login_post_url="http://forum.skonlt.com/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes&inajax=1";
    /**验证是否登录成功的主页*/
    private static String home_url="http://forum.skonlt.com/forum.php";
    /**发帖页面 用来获得发帖的from的hidden参数 填充发帖参数*/
    private static String topical_post_page_url="http://forum.skonlt.com/forum.php?mod=post&action=newthread&fid=109";
    /**发帖接口*/
    private static String topical_post_url="http://forum.skonlt.com/forum.php?mod=post&action=newthread&fid=109&extra=&topicsubmit=yes";
    /***获得帖子接口*/
    private static String get_topicals_url="http://www.94luvideo.com/video/viewVideosInfo?count=1&type=random";
    /**登录账号与密码*/
    private static String username="he7253997";
    private static String password="95b004";
    /***/
    private static String UTF8="utf-8";
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, IOException, CloneNotSupportedException {
        crawlerUtil.clientCreate("http", "forum.skonlt.com", "http://forum.skonlt.com/forum.php");
        login();
        post();
    }
    public static void post() throws IOException, CloneNotSupportedException {
         if(checkLogin()){
             System.out.println("login success!!");
             /***登录成功，开始发帖*/
             getTopicalFrom94lu();
         }else {
             System.out.println("login fault!!");
         }
    }

    /***
     * 验证是否登录成功
     * @return
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public static Boolean checkLogin() throws IOException, CloneNotSupportedException {
        Document docc=crawlerUtil.getDocUTF8(crawlerUtil.noProxyGetUrl(home_url));
        return  docc.toString().contains(username)?true:false;
    }

    /**
     * 从94lu获得帖子
     */
    public static void getTopicalFrom94lu() throws IOException, CloneNotSupportedException {
        Document doc=crawlerUtil.getDocUTF8(crawlerUtil.noProxyGetUrl(get_topicals_url));
        String jsonStr=doc.body().text();
        List<Object> list=JsonUtil.jsonToList(JSONArray.fromObject(jsonStr));
        for(Object bean:list){
            System.out.println(((Map)bean).toString());
            /**根据帖子内容 开始调用发帖*/
            topicalPost((Map)bean);
        }
    }
    /***
     * 发帖方法
     */
    public static void topicalPost(Map map) throws IOException, CloneNotSupportedException {
           /**拿去发帖页面的隐藏参数*/
        Document document = crawlerUtil.getDocUTF8(crawlerUtil.noProxyGetUrl(topical_post_page_url));
        if (document == null) return;
        Elements elements = document.select("div[id=ct]").select("input[type=hidden]");
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        /**隐藏的登录参数填充*/
        for(Element element:elements){
            list.add(new BasicNameValuePair(element.attr("name"), element.attr("value")));
        }
           list.add(new BasicNameValuePair("typeid","26"));
           list.add(new BasicNameValuePair("subject",(String)map.get("title")));
           String message="[img]"+map.get("imgName")+"[/img]\n" +"[color=#000][font=Simsun][size=3]http://www.94luvideo.com/videoplay?vkey="+map.get("vkey")+"[/size][/font][/color]\n";
           list.add(new BasicNameValuePair("message",message));
           list.add(new BasicNameValuePair("replycredit_extcredits","0"));
           list.add(new BasicNameValuePair("replycredit_times","1"));
           list.add(new BasicNameValuePair("replycredit_membertimes","1"));
           list.add(new BasicNameValuePair("replycredit_random","100"));
           list.add(new BasicNameValuePair("tags",""));
           list.add(new BasicNameValuePair("rushreplyfrom",""));
           list.add(new BasicNameValuePair("rushreplyto",""));
           list.add(new BasicNameValuePair("rewardfloor",""));
           list.add(new BasicNameValuePair("replylimit",""));
           list.add(new BasicNameValuePair("stopfloor",""));
           list.add(new BasicNameValuePair("creditlimit",""));
           list.add(new BasicNameValuePair("allownoticeauthor","1"));
           list.add(new BasicNameValuePair("addfeed","1"));
           list.add(new BasicNameValuePair("rushreplyfrom",""));
           list.add(new BasicNameValuePair("uploadalbum","-2"));
           list.add(new BasicNameValuePair("newalbum", "请输入相册名称"));
           Document doc=crawlerUtil.getDocUTF8(crawlerUtil.post(topical_post_url, crawlerUtil.produceEntity(list)));
           System.out.println(doc.toString());
    }

    /***
     * 登录方法
     */
    public static  void login(){
        // 创建参数队列
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("fastloginfield", "username"));
        list.add(new BasicNameValuePair("cookietime", "2592000"));
        list.add(new BasicNameValuePair("quickforward", "yes"));
        list.add(new BasicNameValuePair("handlekey", "1s"));
        list.add(new BasicNameValuePair("username", username));
        list.add(new BasicNameValuePair("password", password));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(list, UTF8);
            Document doc=crawlerUtil.getDocUTF8(crawlerUtil.post(login_post_url,uefEntity));
            System.out.println(doc.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


}
