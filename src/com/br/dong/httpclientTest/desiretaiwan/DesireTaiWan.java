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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.SocketException;
import java.net.URL;
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
    //获取登录界面地址
    private static String getLoginInterface="http://www.desire-taiwan.com/member.php?mod=logging&action=login&referer=&infloat=yes&handlekey=login&inajax=1&ajaxtarget=fwin_content_login";
    //登录post地址
    private static String loginPost1="http://www.desire-taiwan.com/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes&inajax=1";
    private static CrawlerUtil crawlerUtil=new CrawlerUtil();
    //http 4.5
    private static  CloseableHttpClient httpclient = HttpClients.createDefault();
    private static String UTF8="utf-8";
    //界面
    private JFrame frame;//主界面
    private   JLabel lbl ; //加载image的
    private  BufferedImage image ;
    private JButton start_button;//开始按钮
    private JScrollPane jsp;
    public static JTextArea jta;
    private JTextField val_text;
    private JLabel jp1_jl1;
    //构造方法
    public DesireTaiWan(){
        try {
            //构造出desire-taiwan的httpclient的工具类，由于登录验证是写在cookie里面，所以在crawlerUtil里面使设置本地cookie
            crawlerUtil.clientCreate("http","www.desire-taiwan.com","http://www.desire-taiwan.com/forum.php?1");
        } catch (KeyManagementException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //设置初始化页面
        frame=new JFrame("desire-taiwan采集");
        lbl = new JLabel();
    }
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
//         geta("http://www.desire-taiwan.com/member.php?mod=logging&action=login&referer=&infloat=yes&handlekey=login&inajax=1&ajaxtarget=fwin_content_login");
        DesireTaiWan desireTaiWan=new DesireTaiWan();
        desireTaiWan.start(getLoginInterface);
    }

    /**
     * 登录从登录地址获得登陆参数
     * @param login_interface_url 网站登录界面地址
     */
    public  void start(String login_interface_url){
        Document doc1= null;
        try {
            doc1 = crawlerUtil.getDocUTF8(crawlerUtil.noProxyGetUrl(login_interface_url));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("doc1"+doc1.toString());
        //需要从此界面上获得参数。然后再取得其中参数拼成http://www.desire-taiwan.com/misc.php?mod=seccode&update=32766&idhash=SAc6RbC10获得图片输出流
        login_ui_init("");

    }

    /**
     * 登录界面初始化方法
     * @param get_img_url
     */
    public  void login_ui_init(String get_img_url){
        //在界面上获得图片后，输入验证码后点击登录调用login方法
        try {
            URL imageURL = new URL("http://ptlogin2.qq.com/getimage");
            InputStream is = imageURL.openConnection().getInputStream();
            System.out.println();
            image = ImageIO.read(is);
            System.out.println("image is:"+image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        Container c= frame.getContentPane();
//        c.setLayout(new FlowLayout());
        c.setLayout(null);
        //
        val_text=new JTextField(10);
        lbl.setIcon(new ImageIcon(image));
        lbl.setBounds(30,20,200,200);    //设置图片
        val_text.setBounds(100,30,100,30);  //设置输入框位置
        frame.setBounds(300,400,250,300); //设置图片位置
        jp1_jl1=new JLabel("验证码:",JLabel.CENTER);
        jp1_jl1.setBounds(0, 30, 100, 30);
        c.add(jp1_jl1);//添加label
        c.add(lbl); //添加图片
        c.add(val_text) ; //添加文本框
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    //执行登录程序 首页进行一次登录 ，再输入验证码 get验证验证码 最后在post二次登录
    public  void login(String username,String password){
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
    public   String getReDirectUrl(String docString){

        String redirectUrl="http://www.desire-taiwan.com/member.php?mod=logging&action=login&auth=&referer=&infloat=yes&handlekey=login&inajax=1&ajaxtarget=fwin_content_login";
        String auth=docString.substring(docString.indexOf("auth=")+"auth=".length(),docString.indexOf("&amp;referer="));
        String refer=docString.substring(docString.indexOf("&amp;referer=")+"&amp;referer=".length(),docString.indexOf("')&lt"));
        System.out.println(auth);
        System.out.println(refer);
        String result=redirectUrl.replace("auth=","auth="+auth).replace("referer=","referer="+refer);
        System.out.println(result);
        return result;
    }

    public  void geta(String result){
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

    /**
     * 下载图片的测试
     */
    public  void getImg(){
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
