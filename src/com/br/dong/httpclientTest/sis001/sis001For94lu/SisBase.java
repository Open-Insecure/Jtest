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
 * Date: 2015-09-02
 * Time: 9:13
 * sis001站点的基础类
 */
public class SisBase {
    protected static PropertiesUtil propertiesUtil=PropertiesUtil.getInstance("/com/br/dong/httpclientTest/sis001/sis001For94lu/properties/config.properties");//读取配置文件
    private static Logger logger = Logger.getLogger(SisBase.class);//日志
    protected static CrawlerUtil client=new CrawlerUtil();
    protected static String baseUrl=propertiesUtil.getPropValue("baseurl");//网址根路径
    protected static String loginPostUrl=propertiesUtil.getPropValue("login_post_url");//登录post接口
    protected static String username=propertiesUtil.getPropValue("username");//登录用户名
    protected static String password=propertiesUtil.getPropValue("password");//登录密码

    /**
     * 登录sis001
     */
    public  static Boolean login(String username,String password) {
        try {
            client.clientCreatNoUrl("http");
            //先get执行一下
            HttpResponse response=client.noProxyGetUrl(""+baseUrl+"/forum/index.php");
            Document doc=client.getDocGBK(response);
//            logger.info(doc.toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClientProtocolException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //填充登录参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("formhash", "c27b368e"));
        list.add(new BasicNameValuePair("cookietime", "2592000"));
        list.add(new BasicNameValuePair("loginfield", "username"));
        list.add(new BasicNameValuePair("cookietime", "2592000"));
        list.add(new BasicNameValuePair("62838ebfea47071969cead9d87a2f1f7", username));
        list.add(new BasicNameValuePair("c95b1308bda0a3589f68f75d23b15938", password));
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
//            logger.info(doc.toString());
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
