package com.br.dong.httpclientTest._auto_sign_2015_09_03;

import com.br.dong.httpclientTest.CrawlerUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicExpiresHandler;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.TextUtils;
import org.jsoup.nodes.Document;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-09-10
 * Time: 16:51
 */
public class CookieTest {
    private static String url="http://www.zzrhmy.com/Register.asp?id=a1-15574";//注册页面
    private static String imgUrl="http://www.zzrhmy.com/getcode.asp?"+Math.random();//验证码
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
    private static CrawlerUtil crawlerUtil=new CrawlerUtil();
    private static DefaultHttpClient client=null;
    public static void main(String[] args) throws IOException {
        client =new DefaultHttpClient();
        HttpClientParams.setCookiePolicy(client.getParams(), CookiePolicy.BEST_MATCH);
        client.getCookieSpecs().register("chinasource", new CookieSpecFactory() {
            public CookieSpec newInstance(HttpParams params) {
                return new LenientCookieSpec();
            }
        });
        client.getParams().setParameter(ClientPNames.COOKIE_POLICY,
                "chinasource");
        HttpGet get =new HttpGet();
        get.setURI(URI.create(url));
        get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        get.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        get.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        get.setHeader("Cache-Control","max-age=0");
        get.setHeader("Connection","keep-alive");
        get.setHeader("Cookie","safedog-flow-item=B46D4010A987D3C5AE9AA267DDC5E755; ASPSESSIONIDQCSQRDSS=BCOIIIAAHAKBOJMOPPOEBBEA; a4093_pages=2; a4093_times=3");
        get.setHeader("Host","www.zzrhmy.com");
        get.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2251.0 Safari/537.36");
        HttpResponse response =client.execute(get);
        Document document=  crawlerUtil.getDocument(response.getEntity(), "gb2312");
        get.abort();
        System.out.println(document);
        System.out.println(client.getCookieStore().getCookies());
        get.setURI(URI.create(url));
        String code=String.valueOf(getCode(loadCodeImg(imgUrl)));
        System.out.println("ccccc:" + code);
        get.abort();
        //填充登录参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("UserName", randomString(8)));
        list.add(new BasicNameValuePair("PassWord", randomString(8)));
        list.add(new BasicNameValuePair("PassWord1", randomString(8)));
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

        HttpResponse response2=noProxyPostUrl(postUrl, list);
        Document document2=crawlerUtil.getDocument(response2.getEntity(), "gb2312");
        System.out.println(document2.toString());
    }
    public static  HttpResponse noProxyPostUrl(String targetUrl,List<NameValuePair> list) throws IOException {
        HttpPost post=new HttpPost();
        post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        post.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        post.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        post.setHeader("Cache-Control","max-age=0");
        post.setHeader("Connection","keep-alive");
        post.setHeader("Cookie","safedog-flow-item=B46D4010A987D3C5AE9AA267DDC5E755; ASPSESSIONIDQCSQRDSS=BCOIIIAAHAKBOJMOPPOEBBEA; a4093_pages=2; a4093_times=3");
        post.setHeader("Host","www.zzrhmy.com");
        post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2251.0 Safari/537.36");
        HttpResponse response=null;
        post.setURI(URI.create(targetUrl));
        response = client.execute(post);
        post.abort();
        return response;
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
    /***
     * 加载网络验证码图片
     * @param targetUrl
     */
    public static BufferedImage loadCodeImg(String targetUrl){
        HttpGet get =new HttpGet();
        get.setURI(URI.create(targetUrl));
        get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        get.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        get.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        get.setHeader("Cache-Control","max-age=0");
        get.setHeader("Connection", "keep-alive");
        get.setHeader("Cookie", "safedog-flow-item=B46D4010A987D3C5AE9AA267DDC5E755; ASPSESSIONIDQCSQRDSS=BCOIIIAAHAKBOJMOPPOEBBEA; a4093_pages=2; a4093_times=3");
        get.setHeader("Host","www.zzrhmy.com");
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2251.0 Safari/537.36");
        try {
            HttpResponse res= client.execute(get);
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


class LenientCookieSpec extends BrowserCompatSpec {
    public static final String[] DATE_PATTERNS = new String[] {
            "EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz",
            "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z",
            "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z",
            "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z",
            "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z",
            "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z",
            "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z",
            "E, dd-MMM-yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz"
    };

    public LenientCookieSpec() {
        super();
        registerAttribHandler(ClientCookie.EXPIRES_ATTR, new BasicExpiresHandler(DATE_PATTERNS) {
            @Override public void parse(SetCookie cookie, String value) throws MalformedCookieException {
                if (TextUtils.isEmpty(value)) {
                    // You should set whatever you want in cookie
                    System.out.println("vvvvvv11111111:"+value);
                    cookie.setExpiryDate(null);
                } else {
                    System.out.println("vvvvvv:"+value);
                    super.parse(cookie, value);
                }
            }
        });
    }
}
