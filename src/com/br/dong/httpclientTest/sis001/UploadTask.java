package com.br.dong.httpclientTest.sis001;

import com.br.dong.file.FileOperate;
import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.JdbcUtil;
import org.apache.http.Consts;
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
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-6
 * Time: 下午9:36
 * To change this template use File | Settings | File Templates.
 * 上传程序
 *
 */
public class UploadTask extends Thread{
    //编码方式
    private static String GBK="GBK";
    private  CloseableHttpClient httpclient = HttpClients.createDefault();
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
    //当前种子所在根目录
    private static String folderpath="";
    public static void main(String[] args) throws IOException {
        /**
         * yzwm   25
         * yzym   75
         * omwm 26
         * btyc 28
         * dm 27
         */
//        UploadTask task=new UploadTask("yzwmzt","ericchena","asd123123","http://107.150.17.66/","http://107.150.17.66/logging.php?action=login&loginsubmit=true","http://107.150.17.66/post.php?action=newthread&fid=75&extra=","http://107.150.17.66/post.php?action=newthread&fid=75&extra=page%3D1&topicsubmit=yes");
        UploadTask task=new UploadTask("yzwmzt","F:\\vedios\\torrent\\yzwmzt\\","yes","z1073021759","asd123123","http://162.220.13.9/","http://162.220.13.9/logging.php?action=login&loginsubmit=true","http://162.220.13.9/post.php?action=newthread&fid=26&extra=","http://162.220.13.9/post.php?action=newthread&fid=26&extra=page%3D1&topicsubmit=yes");
        task.start();
    }
    //重写run方法
    public void run(){
        init();

    }


    /**
     * 线程构造方法
     * @param name 该线程对应数据库中torrents表flag标志 对应本地存储的文件夹名字
     * @param withFile 是否包含附件上传的标志
     * @param index 首页登录页面用来获得登录表单formhash
     * @param loginPostUrl 登录post接口地址
     * @param postPage  发帖地址用来获得发帖表单的formhash
     * @param uploadUrl 发帖接口地址
     */
    public UploadTask(String name,String folderpath,String withFile,String username,String password,String index,String loginPostUrl,String postPage,String uploadUrl) {
        super(name);
        this.folderpath=folderpath;
        this.withFile=withFile;
        this.username=username;
        this.password=password;
        this.index=index;
        this.loginPostUrl=loginPostUrl;
        this.postPage=postPage;
        this.uploadUrl=uploadUrl;
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "UploadTask{" +
                "httpclient=" + httpclient +
                ", withFile='" + withFile + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", index='" + index + '\'' +
                ", loginPostUrl='" + loginPostUrl + '\'' +
                ", postPage='" + postPage + '\'' +
                ", uploadUrl='" + uploadUrl + '\'' +
                '}';
    }

    /**
     *
     */
    public void init(){
        List rows=getTorrentsByFlag(this.getName());
        System.out.println("片子:"+rows.size());
        login(username,password);
        for(int i=0;i<rows.size();i++){
            Map map= (Map) rows.get(i);
            System.out.println(map.toString());
            if("yes".equals(this.withFile)){
                //如果包含附件的时候，则使用此方法上传
                upload(uploadUrl,map);
            }else{
                //发布纯文本消息
                uploadNoFile(uploadUrl,map);
            }

        }
    };

    /**
     * 通过torrents表中flag标志来查询所对应的代表的版块标志
     * @param flag
     * @return
     */
    public List getTorrentsByFlag(String flag){
          return JdbcUtil.getTorrentsByFlag(flag);

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
        builder.addTextBody("formhash", getFormhash(postPage), ContentType.TEXT_PLAIN);//设置formhash，从发表帖子页面读取formhash参数填充
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
            if (eee != null) {
                System.out.println("--------------------------------------");
                System.out.println("Response content: " + EntityUtils.toString(eee, GBK));
                System.out.println("--------------------------------------");
            } else{
                System.out.println("upload error!");
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * post接口上传附件和普通参数的方法
     * @param url 上传post接口
     * @param map 种子信息map
     */
    public  void upload(String url,Map map){
        HttpPost post = new HttpPost(url);
//        File file = new File(folderpath+this.getName()+"\\"+map.get("title"));
        File file = new File(folderpath+map.get("title"));
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置为浏览器兼容模式
        builder.setCharset(Charset.forName(GBK));// 设置请求的编码格式
        builder.addTextBody("formhash", getFormhash(postPage), ContentType.TEXT_PLAIN);//设置formhash，从发表帖子页面读取formhash参数填充
        builder.addTextBody("isblog", "", ContentType.TEXT_PLAIN);
        builder.addTextBody("frombbs", "1", ContentType.TEXT_PLAIN);

        //设置StringBody 编码GBK防止乱码
        String subjectStr=map.get("title").toString()+"["+map.get("size")+"]";
        StringBody  subject=new StringBody(subjectStr,ContentType.create("text/plain",GBK));
        //内容
//        String msg=map.get("title").toString()+"\r\n影片大小与类型:"+map.get("size")+"预览图片:\r\n"+"[img]"+map.get("picUrl")+"[/img]";
        String msg=map.get("message").toString()+"\r\n"+"[img]"+map.get("picUrl")+"[/img]";
        StringBody   message=new StringBody(msg,ContentType.create("text/plain", GBK));
        builder.addTextBody("selecttypeid", "33", ContentType.TEXT_PLAIN); //选择的主题类型
        builder.addTextBody("typeid", "33", ContentType.TEXT_PLAIN);//主题类型
        builder.addPart("subject", subject);//帖子标题
        builder.addPart("message", message); //帖子内容
        builder.addBinaryBody("attach[]", file, ContentType.APPLICATION_OCTET_STREAM, folderpath+map.get("title"));
        builder.addTextBody("localid[]", "1", ContentType.TEXT_PLAIN);
        builder.addTextBody("attachperm[]", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("attachprice[]", "0", ContentType.TEXT_PLAIN);
        builder.addTextBody("attachdesc[]", "111", ContentType.TEXT_PLAIN);
        builder.addTextBody("readperm", "0", ContentType.TEXT_PLAIN);
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
            if (eee != null) {
                System.out.println("--------------------------------------");
                System.out.println("Response content: " + EntityUtils.toString(eee, GBK));
                UploadUI.jta.append("发布成功"+map.get("title")+"\n");
                System.out.println("--------------------------------------");
            } else{
                System.out.println("upload error!");
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * 登录方法
     */
    public  void login(String username,String password) {
        // 创建默认的httpClient实例.
        // 创建httppost
        HttpPost httppost = new HttpPost(loginPostUrl);
        // 创建参数队列
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("formhash", getFormhash(index)));
        list.add(new BasicNameValuePair("cookietime", "2592000"));
        list.add(new BasicNameValuePair("loginfield", "username"));
        list.add(new BasicNameValuePair("cookietime", "2592000"));
        list.add(new BasicNameValuePair("username", username));
        list.add(new BasicNameValuePair("password", password));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(list, "UTF-8");
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                   Document doc= crawlerUtil.getDocument(entity,GBK);
                   if(doc!=null&&doc.toString().contains(username)){
                       System.out.println(username+"登录成功");
                       UploadUI.jta.append(username+"登录成功"+"\n");
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
     * @throws IOException
     */
    public  String  getFormhash(String url)  {
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
                Elements elements=doc.select("form[method=post]").select("input[name=formhash]");
                String formhash=elements.toString();
                formhash=formhash.substring(formhash.indexOf("value=\"")+7,formhash.indexOf("\" />"));
//                System.out.println(formhash);
                return formhash;
            }
        return "";
    }

}
