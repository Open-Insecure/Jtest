package com.br.dong.httpclientTest.sis001;

import com.br.dong.httpclientTest.CrawlerUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-6
 * Time: 上午9:49
 * To change this template use File | Settings | File Templates.
 */
public class UpLoadTest {
    private static CrawlerUtil client=new CrawlerUtil();
    private static String loginPostUrl="http://107.150.17.66/logging.php?action=login&loginsubmit=true";
    private static String uploadUrl="http://107.150.17.66/post.php?action=newthread&fid=25&extra=page%3D1&topicsubmit=yes";
    public static void main(String[] a1rgs) {
        Boolean loginflag=false;
        loginflag= login("liang93370894","asd123123");
        if(loginflag){
            System.out.println( "登录成功！");
        }else{
            System.out.println("登录失败！");
        }

        try {
            test();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    public static void test() throws UnsupportedEncodingException {
            HttpResponse response= null;
            FileBody bin = new FileBody(new File("F:\\TDDOWNLOAD\\sd\\B09FE10A5B50FBFA38EFC1C6FE4DE8D94454F040.torrent"),"application/octet-stream");
            System.out.println(bin.getMimeType());
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("formhash", new StringBody("e7b3923e"));//
            reqEntity.addPart("isblog", new StringBody(""));//
            reqEntity.addPart("frombbs", new StringBody("1"));//
            reqEntity.addPart("typeid", new StringBody("33"));//
            reqEntity.addPart("subject", new StringBody("testtttt"));//filename1为请求后台的普通参数;属性
            reqEntity.addPart("selecttypeid", new StringBody("33"));//
            reqEntity.addPart("message", new StringBody("testttttttttttttttt"));//
            reqEntity.addPart("attach[]", bin);
            reqEntity.addPart("localid[]", new StringBody("1"));//
            reqEntity.addPart("attachperm[]", new StringBody("0"));//
            reqEntity.addPart("attachprice[]", new StringBody("0"));//
            reqEntity.addPart("attachdesc[]", new StringBody(""));//
            reqEntity.addPart("readperm", new StringBody("0"));//
            reqEntity.addPart("price", new StringBody("0"));//
            reqEntity.addPart("iconid", new StringBody("0"));//
          reqEntity.addPart("wysiwyg", new StringBody("0"));//
        try {
            System.out.println("aaaaaaaaaaa"+reqEntity.getContentLength());
            response=client.postByMultipartEntity(uploadUrl,reqEntity) ;
            System.out.println("bbbbbbbbbbbb");
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Document doc=client.getDocGBK(response);
        System.out.println(doc.toString());
    }



    /**
     * 登录
     */
    public static Boolean login(String username,String password) {
        try {
            client.clientCreatNoUrl("http");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        //填充登录参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("formhash", "3b843eb9"));
        list.add(new BasicNameValuePair("cookietime", "2592000"));
        list.add(new BasicNameValuePair("loginfield", "username"));
        list.add(new BasicNameValuePair("cookietime", "2592000"));
        list.add(new BasicNameValuePair("username", username));
        list.add(new BasicNameValuePair("password", password));
        HttpResponse responsepost= null;
        try {
            //发送登录请求
            responsepost = client.post(loginPostUrl, client.produceEntity(list));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } catch (SocketException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        if(responsepost!=null){
            Document doc=client.getDocGBK(responsepost);
//            System.out.println(doc.toString());
            if(doc.toString().toLowerCase().contains(username)){
                //登录成功
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
}
