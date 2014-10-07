package com.br.dong.httpclientTest.sis001;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-6
 * Time: 下午12:01
 * To change this template use File | Settings | File Templates.
 */
import java.io.*;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.br.dong.httpclientTest.CrawlerUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Test {
    private static CrawlerUtil client=new CrawlerUtil();
    private static String loginPostUrl="http://107.150.17.66/logging.php?action=login&loginsubmit=true";
    public static void main(String args[]) throws Exception {
        Boolean loginflag=false;
        loginflag= login("aisi1985","asd123123");
        if(loginflag){
            System.out.println( "登录成功！");
        }else{
            System.out.println("登录失败！");
        }
        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,"----WebKitFormBoundary1ozYW8mBY4lbZLIL", Charset.forName("UTF-8"));
        FileBody bin = new FileBody(new File("F:\\TDDOWNLOAD\\sd\\B09FE10A5B50FBFA38EFC1C6FE4DE8D94454F040.torrent"),"application/octet-stream");
//        reqEntity.addPart("sid", new StringBody("1lX48m"));//
        reqEntity.addPart("formhash", new StringBody("a2da1e20", Charset.forName("UTF-8")));//
         reqEntity.addPart("isblog", new StringBody("", Charset.forName("UTF-8")));//
        reqEntity.addPart("frombbs", new StringBody("1", Charset.forName("UTF-8")));//
        reqEntity.addPart("typeid", new StringBody("33", Charset.forName("UTF-8")));//
        reqEntity.addPart("subject", new StringBody("testtttt", Charset.forName("UTF-8")));//filename1为请求后台的普通参数;属性
        reqEntity.addPart("selecttypeid", new StringBody("33", Charset.forName("UTF-8")));//
        reqEntity.addPart("message", new StringBody("testttttttttttttttt", Charset.forName("UTF-8")));//
        reqEntity.addPart("attach[]", bin);
        reqEntity.addPart("filename", new StringBody("B09FE10A5B50FBFA38EFC1C6FE4DE8D94454F040.torrent", Charset.forName("UTF-8")));
        reqEntity.addPart("localid[]", new StringBody("1", Charset.forName("UTF-8")));
        reqEntity.addPart("attachperm[]", new StringBody("0", Charset.forName("UTF-8")));
        reqEntity.addPart("attachprice[]", new StringBody("0", Charset.forName("UTF-8")));
        reqEntity.addPart("attachdesc[]", new StringBody("111", Charset.forName("UTF-8")));
//        reqEntity.addPart("localfile[]",  new StringBody("F:\\TDDOWNLOAD\\sd\\B09FE10A5B50FBFA38EFC1C6FE4DE8D94454F040.torrent"));
        reqEntity.addPart("readperm", new StringBody("0", Charset.forName("UTF-8")));//
        reqEntity.addPart("price", new StringBody("0", Charset.forName("UTF-8")));//
        reqEntity.addPart("iconid", new StringBody("0", Charset.forName("UTF-8")));//
        reqEntity.addPart("wysiwyg", new StringBody("0", Charset.forName("UTF-8")));//
        HttpPost request = new HttpPost("http://107.150.17.66/post.php?action=newthread&fid=25&extra=page%3D1&topicsubmit=yes");
        request.setEntity(reqEntity);
        request.setHeader("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary1ozYW8mBY4lbZLIL");
        //设置post请求头
        request.setHeader("Host","107.150.17.6");
        request.setHeader("Connection", "keep-alive");
        request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36");
        request.setHeader("Referer", "http://107.150.17.66/post.php?action=newthread&fid=25&extra=page%3D1");
        request.setHeader("Accept-Encoding",  "gzip,deflate");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        request.setHeader("Cookie","s1m_cookietime=2592000; s1m_auth=pa5YI5lRaxN%2BXfuRiI4LL7a0HHjB8LxOAv2AAJnBqA3jA2zRA9ItqoCl76hm6MI; s1m_visitedfid=25; s1m_sid=vqY77z; s1m_oldtopics=D71996D71995D; s1m_fid25=1412565280; s1m_smile=1D2");
        request.setHeader("Cache-Control","max-age=0");
        request.setHeader("Origin","http://107.150.17.66");
//        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse response =client.execute(request);
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
            System.out.println(doc.toString());
            if(doc.toString().toLowerCase().contains(username)){
                //登录成功
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        StringBuilder sb = new StringBuilder();

        String line = null;
        try {

            while ((line = reader.readLine()) != null) {

                sb.append(line + "/n");

            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                is.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }



        return sb.toString();

    }
}
