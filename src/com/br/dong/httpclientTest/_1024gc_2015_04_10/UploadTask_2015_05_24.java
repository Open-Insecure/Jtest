package com.br.dong.httpclientTest._1024gc_2015_04_10;

import com.br.dong.file.FileOperate;
import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.utils.DateUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-5-24
 * Time: 15:32
 * 发帖程序针对1024gc采集下来的种子进行发帖
 *
 * mm的地址是 21mybbs.me
 * 御花王朝  184.164.72.160
 * 新巴黎 http://107.150.17.66
 */
public class UploadTask_2015_05_24 extends Thread{
    //编码方式
    private static String GBK="GBK";
    private static String UTF8="UTF-8";
    private static String path="E:\\logs\\";
//    private static String path="C:\\logs\\";
    //日志目录
    private static String logPath=path+ DateUtil.getCurrentDay()+"log.txt";
    private CloseableHttpClient httpclient = HttpClients.createDefault();
    //文件操作工具类
    private static FileOperate fileOperate=new FileOperate();
    //
    private static CrawlerUtil crawlerUtil=new CrawlerUtil();
    //发布帖子是否包含附件的标志
    private String withFile;
    //用户名
    private String username;
    //密码
    private String password;
    //登录首页url
    private  String index="";
    //登录posturl获取formhash
    private  String loginPostUrl="";
    //当前发表新帖的地址获取formhash
    private  String postPage="";
    //上传posturl
    private  String uploadUrl="";
    //发布类型
    private String type="";
    //fid
    private String fid="";
    //当前种子所在根目录
    private  String folderpath="";

    public static void main(String[] args) throws UnsupportedEncodingException {
        //新巴黎 http://107.150.17.66/
//        UploadTask_2015_05_24 task=new UploadTask_2015_05_24("name","E:\\uploads\\1\\","yes","liang93370894","asd123123","http://107.150.17.66/logging.php?action=login","http://107.150.17.66/logging.php?action=login&","http://107.150.17.66/post.php?action=newthread&fid=25&extra=page%3D1","http://107.150.17.66/post.php?action=newthread&fid=25&extra=page%3D1&topicsubmit=yes","");
//        task.start();

        //mm公寓
//        UploadTask_2015_05_24 task=new UploadTask_2015_05_24("name","E:\\uploads\\1\\","yes","he7253997","95b004","http://21mybbs.me/member.php?mod=logging&action=login&infloat=yes&handlekey=login&inajax=1&ajaxtarget=fwin_content_login","http://21mybbs.me/member.php?mod=logging&action=login&loginsubmit=yes&handlekey=login&loginhash=LtnzP&inajax=1","http://21mybbs.me/post.php?action=newthread&fid=25&extra=page%3D1","http://21mybbs.me/post.php?action=newthread&fid=25&extra=page%3D1&topicsubmit=yes","mmhouse");
//        task.start();



    }

    /**
     * 线程构造方法
     * @param name 该线程对应数据库中torrents表update+temp标志如：2014-10-27,bt亚洲无码转帖 对应本地存储的文件夹名字
     * @param withFile 是否包含附件上传的标志
     * @param index 首页登录页面用来获得登录表单formhash
     * @param loginPostUrl 登录post接口地址
     * @param postPage  发帖地址用来获得发帖表单的formhash
     * @param uploadUrl 发帖接口地址
     * @param type 发布类型
     */
    public UploadTask_2015_05_24(String name,String folderpath,String withFile,String username,String password,String index,String loginPostUrl,String postPage,String uploadUrl,String type) {
        super(name);
        this.folderpath=folderpath;
        this.withFile=withFile;
        this.username=username;
        this.password=password;
        this.index=index;
        this.loginPostUrl=loginPostUrl;
        this.postPage=postPage;
        this.uploadUrl=uploadUrl;
        this.type=type;
        System.out.println(toString());
    }
    //重写run方法
    public void run(){
//        init();
        try {
            test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void test() throws IOException, NoSuchAlgorithmException, KeyManagementException, CloneNotSupportedException {
        if(type.equals("mmhouse")){
            //MM公寓登录
            crawlerUtil.clientCreate("http","21mybbs.me","http://21mybbs.me/forum.php");
            mmLoging(username,password);
        }else{
            login(username, password);
            GcBean bean=JDBCSkydriveUtil.getCollectById("9");
            if("yes".equals(withFile)){
                upload(uploadUrl,bean);
            } else{
//            normalPost(uploadUrl, map);
            }
        }

    }

    /**
     * 登录MM公寓的方法
     */
    public void mmLoging(String username,String password) throws IOException, CloneNotSupportedException {
        // 创建httppost
        HttpPost httppost = new HttpPost(loginPostUrl);
        // 创建参数队列
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        //--新巴黎参数
//        list.add(new BasicNameValuePair("formhash", getFormhash(index,"formhash")));
        //--mm公寓的参数
        list.add(new BasicNameValuePair("fastloginfield", "username"));
        list.add(new BasicNameValuePair("quickforward", "yes"));
        list.add(new BasicNameValuePair("handlekey", "1s"));
        list.add(new BasicNameValuePair("username", username));
        list.add(new BasicNameValuePair("password", password));
        UrlEncodedFormEntity uefEntity;
        uefEntity = new UrlEncodedFormEntity(list, GBK);
        HttpResponse response=crawlerUtil.post(loginPostUrl, uefEntity);
        Document document=crawlerUtil.getDocGBK(response);
        System.out.println(document.toString());
    }

    /**
     * 初始化方法
     */
    public void init(){
        List<GcBean> rows=JDBCSkydriveUtil.getAllCollectLogs();
        login(username,password);
        for(int i=0;i<rows.size();i++){
            Map map= (Map) rows.get(i);
            System.out.println(map.toString());
            //界面选择是否上传附件:是
            if("yes".equals(this.withFile)){
                //如果包含附件的时候，则使用此方法上传
                if("phpwind".equals(type)){
                    uploadForPHPWind(uploadUrl,map);
                }   else{
//                    upload(uploadUrl,map);
                }
            }else{
                //界面选择是否上传附件:否
                //发布纯文本消息
                if("nomal".equals(type)){
                    normalPost(uploadUrl, map);
                }else if("phpwind".equals(type)){
                    uploadForPHPWindNoFile(uploadUrl,map);
                }
                else{
                    uploadNoFile(uploadUrl,map);
                }
            }
        }
    }
    /**
     * 针对于前端普通的form表单进行post提交
     * @param url
     * @param map
     */
    public void normalPost(String url,Map map){
        HttpPost httppost = new HttpPost(url);
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("formhash", getFormhash(postPage,"formhash")));
        list.add(new BasicNameValuePair("isblog", ""));
        list.add(new BasicNameValuePair("frombbs", "1"));
        list.add(new BasicNameValuePair("subject", map.get("title").toString()+"["+map.get("size")+"]"));
        list.add(new BasicNameValuePair("message",map.get("message").toString()));
        list.add(new BasicNameValuePair("tags", "1"));
        list.add(new BasicNameValuePair("readperm", "0"));
        list.add(new BasicNameValuePair("iconid", "0"));
        list.add(new BasicNameValuePair("wysiwyg", "0"));
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
                    if(doc!=null){
                        System.out.println(doc.select("div[class=box message]").toString());
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
     * 纯文本发布内容 不带附件
     * @param url
     * @param map
     */
    public void uploadNoFile(String url,Map map){
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置为浏览器兼容模式
        builder.setCharset(Charset.forName(GBK));// 设置请求的编码格式
        builder.addTextBody("formhash", getFormhash(postPage,"formhash"), ContentType.TEXT_PLAIN);//设置formhash，从发表帖子页面读取formhash参数填充
        builder.addTextBody("isblog", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("frombbs", "1", ContentType.TEXT_PLAIN);
        //设置StringBody 编码GBK防止乱码
        String subjectStr=map.get("title").toString()+"["+map.get("size")+"]";
        StringBody  subject=new StringBody(subjectStr,ContentType.create("text/plain",GBK));
        //内容 -此处的纯文本可以使用本地所存的txt文档的内容或者使用数据库中message字段的内容都行
//        String msg=map.get("title").toString()+"\r\n影片大小与类型:"+map.get("size")+"预览图片:\r\n"+"[img]"+map.get("picUrl")+"[/img]";
        String msg=map.get("message").toString()+"\r\n"+"[img]"+map.get("picUrl")+"[/img]";
        StringBody   message=new StringBody(msg,ContentType.create("text/plain", GBK));
        builder.addTextBody("selecttypeid", "33", ContentType.TEXT_PLAIN); //选择的主题类型
        builder.addTextBody("typeid", "33", ContentType.TEXT_PLAIN);//主题类型
        builder.addPart("subject", subject);//帖子标题
        builder.addPart("message", message); //帖子内容
        builder.addTextBody("localid[]", "1", ContentType.TEXT_PLAIN);
        builder.addTextBody("attachperm[]", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("attachprice[]", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("attachdesc[]", "111", ContentType.TEXT_PLAIN);
        builder.addTextBody("readperm", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("tags", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("price", "0", ContentType.TEXT_PLAIN);//单价
        builder.addTextBody("iconid", "0", ContentType.TEXT_PLAIN);//图标id
        builder.addTextBody("wysiwyg", "0", ContentType.TEXT_PLAIN);//
//
        HttpEntity entity = builder.build();
        post.setEntity(entity);
//        long length = entity.getContentLength();
//        System.out.println("长度..."+length);
        try {
            HttpResponse response=httpclient.execute(post);
            HttpEntity eee = response.getEntity();
            Document doc=crawlerUtil.getDocGBK(response);
//            System.out.println(doc.toString());
            if(doc!=null){

            }
//            if (eee != null) {
//                System.out.println("--------------------------------------");
//                System.out.println("Response content: " + EntityUtils.toString(eee, GBK));
//                fileOperate.appendMethodB(logPath, DateUtil.getStrOfDateTime() + "[" + doc.select("div[class=box message]").toString() + "]");//打印当前task线程日志
//                System.out.println("--------------------------------------");
//            } else{
//                System.out.println("upload error!");
//            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    public void uploadForPHPWindNoFile(String url,Map map){
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置为浏览器兼容模式
        builder.setCharset(Charset.forName(GBK));// 设置请求的编码格式
        builder.addTextBody("verify", getFormhash(postPage, "verify"), ContentType.TEXT_PLAIN);//设置formhash，从发表帖子页面读取formhash参数填充
        builder.addTextBody("magicname", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("magicid", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("p_type", "2", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_desc1", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_iconid", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_autourl", "1", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_usesign", "1", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_convert", "1", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_rvrc", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_money", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_credittype", "money", ContentType.TEXT_PLAIN);
        builder.addTextBody("step", "2", ContentType.TEXT_PLAIN);
        builder.addTextBody("pid", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("action", "new", ContentType.TEXT_PLAIN);
        builder.addTextBody("fid", fid, ContentType.TEXT_PLAIN);
        builder.addTextBody("tid", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("article", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("special", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_downrvrc1", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_downrvrc2", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_desc2", "", ContentType.TEXT_PLAIN);

        String subjectStr=map.get("title").toString()+"["+map.get("size")+"]";
        StringBody  subject=new StringBody(subjectStr,ContentType.create("text/plain",GBK));
        String msg=map.get("message").toString()+"\r\n"+"[img]"+map.get("picUrl")+"[/img]";
        StringBody   message=new StringBody(msg,ContentType.create("text/plain", GBK));
        builder.addPart("atc_title", subject);//帖子标题
        builder.addPart("atc_content", message); //帖子内容
        HttpEntity entity = builder.build();
        post.setEntity(entity);
//        long length = entity.getContentLength();
//        System.out.println("长度..."+length);
        try {
            HttpResponse response=httpclient.execute(post);
            HttpEntity eee = response.getEntity();
            Document doc=crawlerUtil.getDocGBK(response);
//            System.out.println(doc.toString());
            if(doc!=null){

            }
//            if (eee != null) {
//                System.out.println("--------------------------------------");
//                String content=EntityUtils.toString(eee, GBK);
//                System.out.println("Response content: " +content );
//                if(content.contains("发帖完毕")){
//                    UploadUI.jta.append("发布成功"+map.get("title")+"\n");
//                }
//                System.out.println("--------------------------------------");
//            } else{
//                System.out.println("upload error!");
//            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    /**
     * 针对phpwind的上传
     * @param url
     * @param map
     */
    public void uploadForPHPWind(String url,Map map){
        HttpPost post = new HttpPost(url);
//        File file = new File(folderpath+this.getName()+"\\"+map.get("title"));
        File file = new File(folderpath+map.get("title"));
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置为浏览器兼容模式
        builder.setCharset(Charset.forName(GBK));// 设置请求的编码格式
        builder.addTextBody("verify", getFormhash(postPage, "verify"), ContentType.TEXT_PLAIN);//设置formhash，从发表帖子页面读取formhash参数填充
        builder.addTextBody("magicname", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("magicid", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_iconid", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_autourl", "1", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_usesign", "1", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_convert", "1", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_rvrc", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_money", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_credittype", "money", ContentType.TEXT_PLAIN);
        builder.addTextBody("step", "2", ContentType.TEXT_PLAIN);
        builder.addTextBody("pid", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("action", "new", ContentType.TEXT_PLAIN);
        builder.addTextBody("fid", fid, ContentType.TEXT_PLAIN);
        builder.addTextBody("tid", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("article", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("special", "0", ContentType.TEXT_PLAIN);
        //
        builder.addBinaryBody("attachment_1", file, ContentType.APPLICATION_OCTET_STREAM, folderpath+map.get("title"));
        builder.addTextBody("p_type", "2", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_downrvrc1", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_downrvrc2", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("atc_desc2", "", ContentType.TEXT_PLAIN);

        String subjectStr=map.get("title").toString()+"["+map.get("size")+"]";
        StringBody subject=new StringBody(subjectStr,ContentType.create("text/plain",GBK));
        String msg=map.get("message").toString()+"\r\n"+"[img]"+map.get("picUrl")+"[/img]";
        StringBody   message=new StringBody(msg,ContentType.create("text/plain", GBK));
        builder.addPart("atc_title", subject);//帖子标题
        builder.addPart("atc_content", message); //帖子内容
        HttpEntity entity = builder.build();
        post.setEntity(entity);
//        long length = entity.getContentLength();
//        System.out.println("长度..."+length);
        try {
            HttpResponse response=httpclient.execute(post);
            HttpEntity eee = response.getEntity();
            Document doc=crawlerUtil.getDocGBK(response);
//           System.out.println(doc.toString());
            if(doc!=null){
            }
//           if (eee != null) {
//               System.out.println("--------------------------------------");
//               String content=EntityUtils.toString(eee, GBK);
//               System.out.println("Response content: " +content );
//               if(content.contains("发帖完毕")){
//                   UploadUI.jta.append("发布成功"+map.get("title")+"\n");
//               }
//               System.out.println("--------------------------------------");
//           } else{
//               System.out.println("upload error!");
//           }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * post接口上传附件和普通参数的方法
     * @param url 上传post接口
     */
    public  void upload(String url,GcBean bean){
        HttpPost post = new HttpPost(url);
//        File file = new File(folderpath+this.getName()+"\\"+map.get("title"));
        File file = new File(folderpath+bean.getName()+"."+bean.getType());
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置为浏览器兼容模式
        builder.setCharset(Charset.forName(GBK));// 设置请求的编码格式
        builder.addTextBody("formhash", getFormhash(postPage,"formhash"), ContentType.TEXT_PLAIN);//设置formhash，从发表帖子页面读取formhash参数填充
        builder.addTextBody("isblog", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("frombbs", "1", ContentType.TEXT_PLAIN);

        //设置StringBody 编码GBK防止乱码
        String subjectStr=bean.getName()+"."+bean.getType();
        StringBody  subject=new StringBody(subjectStr,ContentType.create("text/plain",GBK));
        //内容
//        String msg=map.get("title").toString()+"\r\n影片大小与类型:"+map.get("size")+"预览图片:\r\n"+"[img]"+map.get("picUrl")+"[/img]";
        String msg=bean.getContent();
        StringBody   message=new StringBody(msg,ContentType.create("text/plain", GBK));
        //typeid需要根据具体的读取
        builder.addTextBody("frombbs", "33", ContentType.TEXT_PLAIN); //选择的主题类型
        builder.addTextBody("typeid", "33", ContentType.TEXT_PLAIN);//主题类型
//        builder.addTextBody("selecttypeid", "2", ContentType.TEXT_PLAIN); //选择的主题类型
//        builder.addTextBody("typeid", "2", ContentType.TEXT_PLAIN);//主题类型
        builder.addPart("subject", subject);//帖子标题
        builder.addPart("message", message); //帖子内容
        builder.addBinaryBody("attach[]", file, ContentType.APPLICATION_OCTET_STREAM, folderpath + bean.getName()+"."+bean.getType());
        builder.addTextBody("localid[]", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("attachperm[]", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("attachprice[]", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("attachdesc[]", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("readperm", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("tags", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("price", "0", ContentType.TEXT_PLAIN);//单价
        builder.addTextBody("iconid", "0", ContentType.TEXT_PLAIN);//图标id
        builder.addTextBody("wysiwyg", "0", ContentType.TEXT_PLAIN);//
//
        HttpEntity entity = builder.build();
        post.setEntity(entity);
//        long length = entity.getContentLength();
//        System.out.println("长度..."+length);
        try {
            HttpResponse response=httpclient.execute(post);
            HttpEntity eee = response.getEntity();
            Document doc=crawlerUtil.getDocGBK(response);
//            System.out.println(doc.toString());
            if(doc!=null){
            }
//            if (eee != null) {
//                System.out.println("--------------------------------------");
//                System.out.println("Response content: " + EntityUtils.toString(eee, GBK));
//                if(eee.toString().length()<1000){
//                    UploadUI.jta.append("发布成功"+map.get("title")+"\n");
//                }
//                System.out.println("--------------------------------------");
//            } else{
//                System.out.println("upload error!");
//            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    /**
     * 针对PHP的登录程序
     * @param username
     * @param password
     */
    public void loginPHPWind(String username,String password){
        // 创建httppost
        HttpPost httppost = new HttpPost(loginPostUrl);
        // 创建参数队列
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("forward", "http://lianyu.org/post.php?fid-"+fid+".htm"));
        list.add(new BasicNameValuePair("jumpurl", "http://lianyu.org/post.php?fid-"+fid+".htm"));
        list.add(new BasicNameValuePair("step", "2"));
        list.add(new BasicNameValuePair("hideid", "0")); //炼狱岛
        list.add(new BasicNameValuePair("cktime", "31536000"));
        list.add(new BasicNameValuePair("lgt", "0"));
        list.add(new BasicNameValuePair("question", "0"));
        list.add(new BasicNameValuePair("customquest", ""));
        list.add(new BasicNameValuePair("answer", ""));
        list.add(new BasicNameValuePair("pwuser", username));
        list.add(new BasicNameValuePair("pwpwd", password));
        UrlEncodedFormEntity uefEntity;
        try {
            if(loginPostUrl.contains("maichun")){
                uefEntity = new UrlEncodedFormEntity(list, UTF8);
            }else{
                uefEntity = new UrlEncodedFormEntity(list, GBK);
            }
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    Document doc= crawlerUtil.getDocument(entity,GBK);
                    if(doc!=null&&doc.toString().contains("顺利")){
                        System.out.println(".."+doc.toString());
                        System.out.println(username + "login success");
                    }  else{
                        System.out.println("login fault:" + doc.toString());
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
        }  catch (NullPointerException e){
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
        //--新巴黎参数
        list.add(new BasicNameValuePair("formhash", getFormhash(index,"formhash")));
        list.add(new BasicNameValuePair("cookietime", "2592000"));
        list.add(new BasicNameValuePair("referer", "http://107.150.17.66"));
        list.add(new BasicNameValuePair("loginfield", "username"));
        list.add(new BasicNameValuePair("questionid", "0"));
        list.add(new BasicNameValuePair("answer", ""));
        list.add(new BasicNameValuePair("loginmode", ""));
        list.add(new BasicNameValuePair("styleid", ""));
        list.add(new BasicNameValuePair("loginsubmit", "true"));
        //--mm公寓的参数
//        list.add(new BasicNameValuePair("fastloginfield", "username"));
//        list.add(new BasicNameValuePair("quickforward", "yes"));
//        list.add(new BasicNameValuePair("handlekey", "1s"));

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
                        System.out.println(username + "login success");
                    }  else{
                        System.out.println("login faul:" + doc.toString());
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
        } catch (NullPointerException e){
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
            System.out.println("fff"+formhash);
            if(formhash.length()>10){
                formhash=formhash.substring(formhash.indexOf("value=\"")+7,formhash.indexOf("\" />"));
                System.out.println("keyname.."+keyname+":"+formhash);
            }
            return formhash;
        }
        return "";
    }
}
