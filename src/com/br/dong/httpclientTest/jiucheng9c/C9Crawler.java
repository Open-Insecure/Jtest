package com.br.dong.httpclientTest.jiucheng9c;

import com.br.dong.file.FileOperate;
import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.utils.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-6
 * Time: 下午2:04
 * To change this template use File | Settings | File Templates.
 * 针对http://www.92cheng.com/的采集任务
 */
public class C9Crawler {
    //--9c的参数
    private static String index="http://www.92cheng.com/";
    private static String loginIndexUrl="http://www.92cheng.com/forum.php?mod=post&action=newthread&fid=106";
    private static String loginPostUrl="http://www.92cheng.com/member.php?mod=logging&action=login&loginsubmit=yes&handlekey=reply&loginhash=LOFbV&inajax=1";
    private static String GBK="GBK";
    private static String UTF8="UTF-8";
    private  String wantUrlPre="";
    private  String wantUrlAfter=".html";
    private  String fid="";
    private String tid="";
    private  String replayUrl="";//回复查看内容的url
    private static String mainPath="D:\\download\\"; //主文件路径
    private static String secondPath=DateUtil.getCurrentDay()+"\\";
    private static String mainTxt="main.txt";//储存采集的信息
    private static int wantPage=2;
    //--国王后宫的参数
    private static String loginPostUrl92emz="http://www.92emz.com/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes&inajax=1";

    private CrawlerUtil crawlerUtil=new CrawlerUtil();
    //http 4.5
    private CloseableHttpClient httpclient = HttpClients.createDefault();
    public C9Crawler(){
    }

    public C9Crawler(String wantUrlPre,String fid){
           this.wantUrlPre=wantUrlPre;
           this.fid=fid;
    }

    public static void main(String[] args) {
//        创建目录
        FileOperate.newFolderMuti(mainPath+secondPath);
        //创建文件
        FileOperate.appendMethodB(mainPath+mainTxt, "采集时间：["+DateUtil.getCurrentDay()+"]");
        //要采集的站点 ,因为回复有时间限制 又只有一个账号，所以单线程了
        String [] sits={
                "http://www.92cheng.com/forum-106-,106",
                "http://www.92cheng.com/forum-107-,107"
        };
        String [] sits92emz={
                "http://www.92emz.com/forum-42-,42",
                "http://www.92emz.com/forum-49-,49",
                "http://www.92emz.com/forum-52-,52"
        };
        C9Crawler c9Crawler=new C9Crawler();
//        执行9c的采集
        System.out.println("开始执行九城采集..");
        for(int i=0;i<sits.length;i++){
            System.out.println("正在采集..");
            String[] temp=sits[i].split(",");
            c9Crawler.wantUrlPre=temp[0];
            c9Crawler.fid=temp[1];
            c9Crawler.login("lz0033", "buhui5200");
        }
        System.out.println("开始执行国王后宫采集..");
        //执行完了9城的采集，执行国王后宫的采集
        for(int i=0;i<sits.length;i++){
            System.out.println("正在采集..");
            String[] temp=sits92emz[i].split(",");
            c9Crawler.wantUrlPre=temp[0];
            c9Crawler.fid=temp[1];
            c9Crawler.login92emz("he7253997","95b004");
        }




    }
    //--------------------国王后宫的方法
    public void login92emz(String username,String password){
        // 创建httppost
        HttpPost httppost = new HttpPost(loginPostUrl92emz);
        // 创建参数队列
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("fastloginfield", "username"));
        list.add(new BasicNameValuePair("username", username));
        list.add(new BasicNameValuePair("password", password));
        list.add(new BasicNameValuePair("quickforward", "yes"));

        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(list, UTF8);
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    Document doc= crawlerUtil.getDocument(entity,UTF8);
                    if(doc!=null){
                        //登陆成功后才进行采集
                        for(int i=1;i<=wantPage;i++){
                            //写入主文件
                            getInfos(wantUrlPre+i+wantUrlAfter);
                        }
                    }
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //--------------------

    //-------------------九城的方法
    /**
     * 采集分区的列表，判断是否采集过，并写入主文件main.txt
     * @param url
     */
    public void getInfos(String url){
        HttpGet httpGet=new HttpGet(url);
        File file=new File(mainPath+mainTxt);//当前存储采集信息的txt
        try {
            CloseableHttpResponse response= httpclient.execute(httpGet);
            Document doc=crawlerUtil.getDocument(response.getEntity(),GBK);
//            System.out.println(doc.toString());
            //先将采集的信息全部存到txt文档里，方便每次采集的时候查看是否重复采集
            Elements xjs=doc.select("tbody[id*=normalthread]>tr");//帖子的div
            //循环
            for(Element e:xjs){
                // System.out.println(e.toString());
                //只拿包含出售联系方式的帖子
//                if(e.text().contains("售价")){
                    String tt="地区："
                            + e.select("th>em").select("a").text() + " 标题："
                            + e.select("a[class$=xst]").text() +" 链接地址:"
                            +e.select("a[class$=xst]").attr("abs:href")+"**发布时间："
                            + e.select("td[class$=by]").select("span").text();
			         System.out.println(tt);
                    //每一次循环到一条信息 先判断main.txt里有没有相同的记录 有的话 则 不进行写入了
                    String content=FileUtils.readFileToString(file);
                    if(!content.contains(e.select("a[class$=xst]").text())){
                        //txt里不存在此条信息 则往main.txt写入一条
                        FileOperate.appendMethodB(mainPath+mainTxt, tt);
                        //进行具体的采集
                        getDeatilOne(e.select("a[class$=xst]").attr("abs:href"));
                    }else{
                        System.out.println(tt+"已经存在");
                    }
//                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * 采集具体的某一条帖子的内容
     * @param targetUrl
     */
    public void getDeatilOne(String targetUrl){
        HttpGet httpGet=new HttpGet(targetUrl);

            //采集之前调用统一回复方法，进行回复后，再采集回复后可以看到的内容
            if(replay(targetUrl)){
                //发表回复成功后，进行采集
                CloseableHttpResponse response= null;
                try {
                    response = httpclient.execute(httpGet);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                Document doc=crawlerUtil.getDocument(response.getEntity(), GBK);
                //拿去xj区域与标题 作为创建txt的目录位置与文件标题
                Elements scope=doc.select("h1[class$=ts]");
                String place=scope.select(":eq(0)").text()==""?"其他区":scope.select(":eq(0)").text().replace(".",""); //哪个区的
                String title=(scope.select(":eq(1)").text()==""||scope.select(":eq(1)").text()==null)?place:scope.select(":eq(1)").text().replace(".",""); //标题
//            System.out.println("--place"+place+"--"+title);
                //获得内容
                String content=doc.select("div[class*=t_f]").first().text().replace("【","\r\n【");
                //获得隐藏照片url
                StringBuffer picsb=new StringBuffer();
                Elements pics=doc.select("div[class*=showhide]>ignore_js_op").select("img");
                for(Element e:pics){
                    picsb.append("\r\n[img]"+index+e.attr("file")+"[/img]");
                }
//            System.out.println(content.toString()+picsb.toString());
                //写入到具体的分区文件夹与文件中
//                FileOperate.newFolderMuti(mainPath+secondPath+place);//创建分区文件夹 ，不创建文件夹地区分区了。直接写入txt文件
                FileOperate.appendMethodB(mainPath+secondPath+title+".txt",content.toString()+picsb.toString());
            }else{
                //回复失败，进行回调
                getDeatilOne(targetUrl);
            }
    }


    /**
     * 针对某个帖子进行回复,注意回复间隔15秒。此处让线程暂停15秒
     * @param targetUrl
     */
    public Boolean replay(String targetUrl){
        //回复的post地址
        if(targetUrl.contains("92cheng")){
            replayUrl =getReplayPostUrl(targetUrl);
        }else{
            replayUrl=getReplayPostUrlFor92emz(targetUrl);
        }
        // 创建httppost
        HttpPost httppost = new HttpPost(replayUrl);
        // 创建参数队列
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("formhash", getFormhash(targetUrl,"formhash")));//从回复页面获得formhash
        list.add(new BasicNameValuePair("handlekey", "reply"));
        list.add(new BasicNameValuePair("noticeauthor", ""));
        list.add(new BasicNameValuePair("noticetrimstr", ""));
        list.add(new BasicNameValuePair("noticeauthormsg", ""));
        list.add(new BasicNameValuePair("usesig", "0"));
        list.add(new BasicNameValuePair("subject", ""));
        list.add(new BasicNameValuePair("message", "感谢楼主分享，好人一生平安！！！"));
        list.add(new BasicNameValuePair("replysubmit","true"));
        UrlEncodedFormEntity uefEntity;
        Document doc=null;
        try {
            uefEntity = new UrlEncodedFormEntity(list, GBK);
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            //回复间隔15秒所以 线程暂停15秒
            Thread.sleep(15 * 1000);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                     doc= crawlerUtil.getDocument(entity,GBK);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(doc!=null&&doc.toString().contains("回复发布成功")){
            System.out.println("帖子:["+targetUrl+"]发表回复成功,进行采集..");
            return true;
        } else{
            System.out.println(doc.toString());
            return false;
        }
    }

    /**
     * 登录方法
     */
    public  void login(String username,String password) {
        // 创建httppost
        HttpPost httppost = new HttpPost(loginPostUrl);
        // 创建参数队列
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("formhash", getFormhash(loginIndexUrl,"formhash")));
        list.add(new BasicNameValuePair("referer", loginIndexUrl));
        list.add(new BasicNameValuePair("loginfield", "username"));
        list.add(new BasicNameValuePair("cookietime", "2592000"));
        list.add(new BasicNameValuePair("questionid", "3"));
        list.add(new BasicNameValuePair("answer", "北京"));
        list.add(new BasicNameValuePair("username", username));
        list.add(new BasicNameValuePair("password", password));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(list, GBK);
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    Document doc= crawlerUtil.getDocument(entity,GBK);
                    if(doc!=null&&doc.toString().contains(username)){
                        System.out.println(username + "登录成功");
                        //登陆成功后才进行采集
                        for(int i=1;i<=wantPage;i++){
                            //写入主文件
                            getInfos(wantUrlPre+i+wantUrlAfter);
                        }
//                        System.out.println("主文件["+mainPath+mainTxt+"]写入成功，进行采集更新");
                    }  else{
                        System.out.println("登录失败:"+doc.toString());
                    }
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得当前发表新帖页面的formhash 放入参数中
     * @param url
     * @param keyname 要获得的隐藏参数
     * @throws IOException
     */
    public  String  getFormhash(String url,String keyname)  {
        HttpGet get=new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(get);
        } catch (IOException e) {
            return "";
        }
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            Document doc=crawlerUtil.getDocument(entity,GBK);
            Elements elements=doc.select("form[method=post]").select("input[name="+keyname+"]");
            String formhash=elements.toString();
            if(formhash.length()>10){
                formhash=formhash.substring(formhash.indexOf("value=\"")+7,formhash.indexOf("\" />"));
//                System.out.println("keyname.."+keyname+":"+formhash);
            }
            return formhash;
        }
        return "";
    }

    /**
     * 根据fid和targetUrl的theard获得tid用来替换掉replayUrl生成回复的post接口
     * @param targetUrl
     * @return
     */
    public String getReplayPostUrl(String targetUrl){
        tid=targetUrl.substring(targetUrl.indexOf("thread-")+"thread-".length(),targetUrl.indexOf("-1-"));
//        System.out.println("fid:"+fid+"tid:"+tid);
        String  tempUrl="http://www.92cheng.com/forum.php?mod=post&infloat=yes&action=reply&fid=&extra=&tid=&replysubmit=yes&inajax=1";
        tempUrl= tempUrl.replace("fid=","fid="+fid).replace("tid=","tid="+tid);
        return tempUrl;
    }
    public String getReplayPostUrlFor92emz(String targetUrl){
        //
        tid=targetUrl.substring(targetUrl.indexOf("thread-")+"thread-".length(),targetUrl.indexOf("-1-"));
        String  tempUrl="http://www.92emz.com/forum.php?mod=post&infloat=yes&action=reply&fid=&extra=&tid=&replysubmit=yes&inajax=1";
        tempUrl= tempUrl.replace("fid=","fid="+fid).replace("tid=","tid="+tid);
        return tempUrl;
    }
   //-----------------------------------------------------------
}
