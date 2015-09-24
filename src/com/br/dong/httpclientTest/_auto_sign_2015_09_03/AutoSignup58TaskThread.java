package com.br.dong.httpclientTest._auto_sign_2015_09_03;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.ProxyBean;
import com.br.dong.utils.PropertiesUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-09-11
 * Time: 13:48
 * 使用代理58情人自动注册线程
 * 注册完成后 记得跳转到主页 模仿出停留时间！！
 */
public class AutoSignup58TaskThread extends Thread {
    private static Logger logger = Logger.getLogger(AutoSignup58TaskThread.class);//日志
    private String host="";//主机host
    private String signPageUrl="";//注册页面地址
    private String signUpUrl="";//注册post地址
    private String vCodeUrl="";//获得验证码地址
    private String successUrl="";//获得验证码地址
    private ProxyBean proxy;//代理bean
    private CrawlerUtil client=new CrawlerUtil();
    /**
     * 构造方法
     * @param name 线程名
     * @param host 主机host
     * @param signPageUrl 注册页面url
     * @param signUpUrl 注解接口url
     * @param vCodeUrl 验证码接口url
     * @param successUrl 注册成功后跳转的url
     *
     */
    public AutoSignup58TaskThread(String name, String host, String signPageUrl, String signUpUrl, String vCodeUrl,String successUrl,ProxyBean proxy) {
        super(name);
        this.host = host;
        this.signPageUrl = signPageUrl;
        this.signUpUrl = signUpUrl;
        this.vCodeUrl = vCodeUrl;
        this.successUrl=successUrl;
        this.proxy=proxy;
    }

    public AutoSignup58TaskThread() {
    }

    public void run(){
        try{
            Random random=new Random();
            Thread.sleep((random.nextInt(100))*1000);//随机100秒到200秒之间注册一个账号
            this.client.clientCreate("http",host,signPageUrl);//创建该现成的httpclient
            HttpResponse response=client.proxyGetUrl(this.signPageUrl,this.proxy.getIp(),this.proxy.getPort()) ;//注册页面
            if(response!=null&&response.getStatusLine().getStatusCode()==200){
//                 String code=String.valueOf(getCode(loadCodeImg(vCodeUrl,proxy.getIp(),proxy.getPort(),client)));
                String code=String.valueOf(AutoSignupUtil.getCode(AutoSignupUtil.loadCodeImg(vCodeUrl, proxy.getIp(), proxy.getPort(), client)));
                System.out.println("cccc:"+code);
                logger.info(this.getName()+"connect["+this.proxy+"] success..start parse vcode["+code+"] cookie:"+client.getCookieStore().getCookies());//连接注册页面成功，开始解析注册码
                Thread.sleep(5000);//延迟10秒提交
                startSignUp(code);
            } else{
                logger.info(this.getName()+"connect["+this.proxy+"] fault");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e);
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, NoSuchAlgorithmException, CloneNotSupportedException, KeyManagementException {
        List<ProxyBean> list=AutoSignupUtil.getBuyProxyByApi(args[0]);//购买的代理ip
        if(list==null||list.size()==0) return;
        ExecutorService executor = Executors.newFixedThreadPool(5);
        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("/com/br/dong/httpclientTest/_auto_sign_2015_09_03/properties/config.properties");//读取配置文件
        for(ProxyBean proxy:list){
            Random random=new Random();
            Thread.sleep((random.nextInt(100))*1000);//随机100秒到200秒之间注册一个账号
//            ProxyBean proxy=new ProxyBean("58.222.254.11",3128,"","");
            AutoSignup58TaskThread thread=new AutoSignup58TaskThread("thread["+proxy.getIp()+"]",propertiesUtil.getPropValue("HOST"),propertiesUtil.getPropValue("SIGN_PAGE_URL"),propertiesUtil.getPropValue("SIGN_UP_URL"),propertiesUtil.getPropValue("VCODE_URL"),propertiesUtil.getPropValue("SUCCESS_URL"),proxy);
//            AutoSignup58TaskThread thread=new AutoSignup58TaskThread("thread["+proxy.getIp()+"]",propertiesUtil.getPropValue("AI_HOST"),propertiesUtil.getPropValue("AI_SIGN_PAGE_URL"),propertiesUtil.getPropValue("AI_SIGN_UP_URL"),propertiesUtil.getPropValue("AI_VCODE_URL"),propertiesUtil.getPropValue("AI_SUCCESS_URL"),proxy);
            executor.execute(thread);
        }
//      executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);//用于等待子线程结束，再继续执行下面的代码。
        executor.shutdown();

    }
    public void startSignUp(String code) throws IOException, CloneNotSupportedException, InterruptedException {
        String randomStr =AutoSignupUtil.randomUserName();//随机的用户名与密码
        String randomQq=AutoSignupUtil.randomNumber();//随机的qq号
        String randomPro=AutoSignupUtil.randomProvince();//随机省
        String randomCity=AutoSignupUtil.randomCity(randomPro);
        //填充登录参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("UserName",randomStr ));
        list.add(new BasicNameValuePair("PassWord", randomStr));
        list.add(new BasicNameValuePair("PassWord1", randomStr));
        list.add(new BasicNameValuePair("xb","0"));
        list.add(new BasicNameValuePair("QQ", randomQq));
        list.add(new BasicNameValuePair("QQ", randomQq+"qq.com"));
        list.add(new BasicNameValuePair("Jymd", "E夜情"));
        list.add(new BasicNameValuePair("Xagn", "开放"));
        list.add(new BasicNameValuePair("Xagn", "认同一夜情"));
        list.add(new BasicNameValuePair("province", randomPro));
        list.add(new BasicNameValuePair("city", randomCity));
        list.add(new BasicNameValuePair("Jyxy", "4"));
        list.add(new BasicNameValuePair("Code", code));
        list.add(new BasicNameValuePair("Ms", "2"));
        list.add(new BasicNameValuePair("T", "0"));
        HttpResponse response=client.proxyPostUrl(signUpUrl, proxy.getIp(), proxy.getPort(),list);
        Document document=client.getDocument(response.getEntity(), "gb2312");
        System.out.println(document.toString());
//        HttpResponse ending=client.proxyGetUrl(successUrl,proxy.getIp(),proxy.getPort());
//        Thread.sleep(1000);
//        Document endingDoc=client.getDocument(ending.getEntity(),"gb2312");
//        System.out.println(endingDoc.toString());
    }

}
