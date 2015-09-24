package com.br.dong.httpclientTest._auto_sign_2015_09_03;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.JdbcUtil;
import com.br.dong.httpclientTest.porn.ProxyBean;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-09-03
 * Time: 9:44
 * 使用代理去注册58情人
 * 可用！！
 * http://hereyougo.com.cn/?id=a1-15574
 */
public class Signup58qingren extends Thread{
    private static String url="http://www.zzrhmy.com/Register.asp?id=a1-15780";//注册页面
    private static String imgUrl="http://www.zzrhmy.com/getcode.asp";//验证码
    private static String postUrl="http://www.zzrhmy.com/Register.asp?act=Add";//注册接口
    private static Random randGen = null;
    private static char[] numbersAndLetters = null;
    private static char[] numbers=null;
    private static Object initLock = new Object();
    public static String vB="238238238";//背景色
    public static String v0="138750";
    public static String v1="21700";
    public static String v2="199370";
    public static String v3="2109164";
    public static String v4="162880";
    public static String v5="062221";
    public static String v6="203682";
    public static String v7="162099";
    public static String v8="516469";
    public static String v9="0103230";
    public static String vcodes[]={v0,v1,v2,v3,v4,v5,v6,v7,v8,v9};//验证码组成的数组
    private static ExecutorService threads= Executors.newFixedThreadPool(20);

    public void run(){
        CrawlerUtil client=new CrawlerUtil();
        while(true){
            ProxyBean proxy= JdbcUtil.getProxy();
            try {
            if(proxy!=null){
                Thread.sleep(2000);
                System.out.println("start connet to proxy:[" + proxy.toString() + "]");
                client.clientCreate("http", "www.zzrhmy.com", "http://www.zzrhmy.com/Register.asp?id=a1-15780");
//                client.clientByProxyCreate("http", "www.zzrhmy.com", "http://www.zzrhmy.com/Register.asp?id=a1-15780");
                System.out.println("cokie111:"+client.getCookieStore().getCookies());
                    HttpResponse response=client.proxyGetUrl("http://www.zzrhmy.com/Register.asp?id=a1-15780",proxy.getIp(),proxy.getPort()) ;//注册页面
//                HttpResponse response=client.noProxyGetUrl(url);
                    Document document=client.getDocument(response.getEntity(), "gb2312");
//                System.out.println(document.toString());
                    System.out.println("cokie222:" + client.getCookieStore().getCookies());
                    String code=String.valueOf(getCode(loadCodeImg(imgUrl,proxy.getIp(),proxy.getPort(),client)));
                    System.out.println("ccccc:" + code);
                    System.out.println("cokie333:"+client.getCookieStore().getCookies());
                    String randomStr=randomString(8);
                    //填充登录参数
                    List<NameValuePair> list = new ArrayList<NameValuePair>();
                    list.add(new BasicNameValuePair("UserName",randomStr ));
                    list.add(new BasicNameValuePair("PassWord", randomStr));
                    list.add(new BasicNameValuePair("PassWord1", randomStr));
                    list.add(new BasicNameValuePair("xb","0"));
                    list.add(new BasicNameValuePair("QQ", "3623172"));
                    list.add(new BasicNameValuePair("QQ", "3623172@qq.com"));
                    list.add(new BasicNameValuePair("Jymd", "E夜情"));
                    list.add(new BasicNameValuePair("Xagn", "开放"));
                    list.add(new BasicNameValuePair("Xagn", "认同一夜情"));
                    list.add(new BasicNameValuePair("province", "北京"));
                    list.add(new BasicNameValuePair("city", "西城"));
                    list.add(new BasicNameValuePair("Jyxy", "4"));
                    list.add(new BasicNameValuePair("Code", code));
                    list.add(new BasicNameValuePair("Ms", "2"));
                    list.add(new BasicNameValuePair("T", "0"));
//                HttpResponse response2=client.noProxyPostUrl(postUrl,list);
                    HttpResponse response2=client.proxyPostUrl(postUrl, proxy.getIp(), proxy.getPort(),list);
                    Document document2=client.getDocument(response2.getEntity(), "gb2312");
                    System.out.println(document2.toString());
            }
                }catch (Exception e){
                    System.out.println(e);
                }
        }
    }
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, IOException, CloneNotSupportedException, InterruptedException {
      for(int i=0;i<10;i++){
          Signup58qingren thread=new Signup58qingren();
          thread.start();
      }
    }

    /***
     * 加载网络验证码图片
     * @param targetUrl
     */
    public static BufferedImage loadCodeImg(String targetUrl,String ip,int port,CrawlerUtil ct){
        try {
//            HttpResponse res= client.noProxyGetUrl(targetUrl) ;
            HttpResponse res= ct.proxyGetUrl(targetUrl, ip, port) ;
            if (HttpStatus.SC_OK == res.getStatusLine().getStatusCode()) {
                //请求成功
                HttpEntity entity = res.getEntity();
                if (entity != null && entity.isStreaming()) {
                    InputStream is = entity.getContent();
                    return ImageIO.read(is);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final String randomString(int length) {
        if (length < 1) {
            return null;
        }
        if (randGen == null) {
            synchronized (initLock) {
                if (randGen == null) {
                    randGen = new Random();
                    numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" +
                            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
                }
            }
        }
        char [] randBuffer = new char[length];
        for (int i=0; i<randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
//            randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
        }
        return new String(randBuffer);
    }

    /**
     * 获得验证码
     * @param image
     * @return
     */
    public static  char[] getCode(BufferedImage image){
        if (image == null) {
            return null;
        }
        BufferedImage[] checkCode=getCheckCodes(image);//将验证码分解为四个小图分别对应验证码的四个数字
        char []result={'-','-','-','-'};
        for(int i=0;i<checkCode.length;i++){
            for (int j = 0; j < checkCode[i].getWidth(); j++) {
                if(getRgb(checkCode[i].getRGB(j,0))!='-'){
                    result[i]= getRgb(checkCode[i].getRGB(j,0));
                    break;
                }
            }
        }
        return result;
    }

    /***
     * 解析每一个验证码碎片中的验证码数字
     * @param pixel
     * @return
     */
    public static char getRgb(int pixel){
        //获取R/G/B
        int r = (pixel >> 16) & 0xff;  //右移16位并且与0xff做与运算 0xff是十六进制，换算成十进制是255
        int g = (pixel >> 8) & 0xff;
        int b = (pixel) & 0xff;
        String rgb=String.valueOf(r)+String.valueOf(g)+String.valueOf(b);//图片像素点转换为rgb字符串
        String result = "";
        for(int i=0;i<vcodes.length;i++){
            if(vcodes[i].equals(rgb)){
                result=String.valueOf(i);
                char []chs=result.toCharArray();
                return chs[0];
            }
        }
        return '-';
    }
    /**
     * 将一张图片按照规则切分为4张
     * @param image
     * @return
     */
    public static BufferedImage[] getCheckCodes(BufferedImage image) {
        BufferedImage checkCode[] = new BufferedImage[4];
        int height = image.getHeight();
        int width = image.getWidth();
        checkCode[0] = image.getSubimage(0 * (width / checkCode.length), 0, width
                        / checkCode.length,
                height);
        checkCode[1] = image.getSubimage(1 * (width / checkCode.length) + 1, 0, width
                / checkCode.length
                - 1, height);
        checkCode[2] = image.getSubimage(2 * (width / checkCode.length), 0, width
                        / checkCode.length - 3,
                height);
        checkCode[3] = image.getSubimage(3 * (width / checkCode.length) - 2, 0, width
                        / checkCode.length,
                height);
        return checkCode;
    }
}

