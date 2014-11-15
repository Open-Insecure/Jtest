package com.br.dong.httpclientTest.desiretaiwan;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.sis001.UploadUI;
import com.br.dong.utils.DateUtil;
import com.sun.xml.internal.stream.writers.UTF8OutputStreamWriter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-12
 * Time: 下午1:20
 * To change this template use File | Settings | File Templates.
 */
public class DesireTaiWan {
    private static String index="http://www.desire-taiwan.com";
    private static String loginPost1="http://www.desire-taiwan.com/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes&inajax=1";
    private static CrawlerUtil crawlerUtil=new CrawlerUtil();
    //http 4.5
    private static  CloseableHttpClient httpclient = HttpClients.createDefault();
    //使用cookie
    private BasicCookieStore cookieStore = new BasicCookieStore();
    private static String UTF8="utf-8";
    public static void main(String[] args) {
//        HttpGet httpGet=new HttpGet(index);
//        try {
//            CloseableHttpResponse response=httpclient.execute(httpGet);
//            try {
//                HttpEntity entity = response.getEntity();
//                if (entity != null) {
//                    Document doc= crawlerUtil.getDocument(entity,UTF8);
//                    System.out.println(doc.toString());
//                }
//            } finally {
//                response.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        login("ckwison","456897");

         geta("http://www.desire-taiwan.com/member.php?mod=logging&action=login&referer=&infloat=yes&handlekey=login&inajax=1&ajaxtarget=fwin_content_login");
    }
    //执行登录程序 首页进行一次登录 ，再输入验证码 get验证验证码 最后在post二次登录
    public static void login(String username,String password){
        // 创建httppost
        HttpPost httppost = new HttpPost(loginPost1);
        // 创建参数队列

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("fastloginfield", "username"));
        list.add(new BasicNameValuePair("quickforward", "yes"));
        list.add(new BasicNameValuePair("handlekey", "1s"));
        list.add(new BasicNameValuePair("username", username));
        list.add(new BasicNameValuePair("password", password));
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
                    System.out.println("...:"+doc.toString());
                    geta(getReDirectUrl(doc.toString()));
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
     * 获得第一次登录重新指向的url地址
     * @return
     */
    public static  String getReDirectUrl(String docString){

        String redirectUrl="http://www.desire-taiwan.com/member.php?mod=logging&action=login&auth=&referer=&infloat=yes&handlekey=login&inajax=1&ajaxtarget=fwin_content_login";
        String auth=docString.substring(docString.indexOf("auth=")+"auth=".length(),docString.indexOf("&amp;referer="));
        String refer=docString.substring(docString.indexOf("&amp;referer=")+"&amp;referer=".length(),docString.indexOf("')&lt"));
        System.out.println(auth);
        System.out.println(refer);
        String result=redirectUrl.replace("auth=","auth="+auth).replace("referer=","referer="+refer);
        System.out.println(result);
        return result;
    }
    public static void geta(String result){
        try {
            crawlerUtil.clientCreate("http","www.desire-taiwan.com","http://www.desire-taiwan.com/forum.php?1");
           Document doc1=crawlerUtil.getDocUTF8(crawlerUtil.noProxyGetUrl("http://www.desire-taiwan.com/misc.php?mod=seccode&action=update&idhash=SAc6RbC10&inajax=1&ajaxtarget=seccode_SAc6RbC10"));
            System.out.println("doc1"+doc1.toString());
            getImg();
        } catch (KeyManagementException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SocketException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClientProtocolException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void getImg(){
        File storeFile = new File("F://2008sohu.png");
        try {
            HttpResponse res= crawlerUtil.noProxyGetUrl("http://www.desire-taiwan.com/misc.php?mod=seccode&update=32766&idhash=SAc6RbC10")  ;
            if (HttpStatus.SC_OK == res.getStatusLine().getStatusCode()) {
                //请求成功
                HttpEntity entity = res.getEntity();

                if (entity != null && entity.isStreaming()) {
                    // 　为目标文件创建目录
                    // 创建一个空的目标文件
                    storeFile.createNewFile();
                    FileOutputStream fos = new FileOutputStream(storeFile);

                    // 将取得的文件文件流写入目标文件
                    InputStream is = entity.getContent();
                    byte[] b = new byte[1024];
                    int j = 0;
                    while ((j = is.read(b)) != -1) {
                        fos.write(b, 0, j);
                    }

                    fos.flush();
                    fos.close();
                }

                else {
                }
                if (entity != null) {
                    entity.consumeContent();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
