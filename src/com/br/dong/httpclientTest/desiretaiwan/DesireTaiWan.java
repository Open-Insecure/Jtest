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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class DesireTaiWan implements ActionListener{
    private static String index="http://www.desire-taiwan.com";
    //获取登录界面地址
    private static String getLoginInterface="http://www.desire-taiwan.com/member.php?mod=logging&action=login&referer=&infloat=yes&handlekey=login&inajax=1&ajaxtarget=fwin_content_login";
    //登录post地址
    private static String loginPostUrl="http://www.desire-taiwan.com/member.php?mod=logging&action=login&loginsubmit=yes&handlekey=login&loginhash=&inajax=1";
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
    //公用的参数
    private static String formhash;
    private static String referer;
    private static String sechash;
    //构造方法
    public DesireTaiWan(){
        try {
            //构造出desire-taiwan的httpclient的工具类，由于登录验证是写在cookie里面，所以在crawlerUtil里面使设置本地cookie
            crawlerUtil.clientCreate("http","www.desire-taiwan.com","http://www.desire-taiwan.com/./");
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
        DesireTaiWan desireTaiWan=new DesireTaiWan();
        desireTaiWan.start(getLoginInterface);
    }

    /**
     * 登录从登录地址获得登陆参数
     * @param login_interface_url 网站登录界面地址
     */
    public  void start(String login_interface_url){
        Document doc= null;
        try {
            doc = crawlerUtil.getDocUTF8(crawlerUtil.noProxyGetUrl(login_interface_url));
            System.out.println("doc1"+doc.toString());
            parseLoginParam(doc);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        //需要从此界面上获得参数。然后再取得其中参数拼成http://www.desire-taiwan.com/misc.php?mod=seccode&update=32766&idhash=SAc6RbC10获得图片输出流

    }

    /**
     * 解析登录界面的参数
     * @param document
     */
    public void parseLoginParam(Document document) throws IOException, CloneNotSupportedException {
        String imageUrl_1="http://www.desire-taiwan.com/misc.php?mod=seccode&action=update&idhash=&inajax=1&ajaxtarget=seccode_"; //获得image访问地址1
        String imageUrl_2="http://www.desire-taiwan.com/";//真正的image的访问地址
         if(document!=null){
             String docString=document.toString();
             String loginhash=docString.substring(docString.indexOf("loginhash=")+"loginhash=".length(),docString.indexOf("&quot;&gt; &lt;div class"));
              sechash=docString.substring(docString.indexOf("sechash&quot; type=&quot;hidden&quot; value=&quot;")+"sechash&quot; type=&quot;hidden&quot; value=&quot;".length(),docString.indexOf("&quot; /&gt; &lt;div class=&quot;rfm&quot;&gt;&lt;table&gt;&lt;tr&gt;&lt;th&gt;验证码"));
               formhash=docString.substring(docString.indexOf("formhash&quot; value=&quot;")+"formhash&quot; value=&quot;".length(),docString.indexOf("&quot; /&gt; &lt;input type=&quot;hidden&quot; name=&quot;referer"));
               referer=docString.substring(docString.indexOf("name=&quot;referer&quot; value=&quot;") + "name=&quot;referer&quot; value=&quot;".length(), docString.indexOf("&quot; /&gt; &lt;div class=&quot;rfm&quot;&gt; &lt;table&gt; &lt;tr&gt; &lt;th&gt; &lt;span class=&quot;login_slc"));
             loginPostUrl=loginPostUrl.replace("loginhash=","loginhash="+loginhash);
               //拼装第一个image的地址
              imageUrl_1=imageUrl_1.replace("idhash=","idhash="+sechash)+sechash;
             System.out.println(loginhash+"|"+sechash+"|"+formhash+"|"+referer);
            Document doc1 = crawlerUtil.getDocUTF8(crawlerUtil.noProxyGetUrl(imageUrl_1));
            if(doc1!=null){
                String doc1_tostring=doc1.toString();
//                System.out.println(doc1_tostring);
                String src=doc1_tostring.substring(doc1_tostring.indexOf("src=&quot;")+"src=&quot;".length(),doc1_tostring.indexOf("&quot; class=&quot"));
                imageUrl_2=(imageUrl_2+src).replace("amp;","");
                System.out.println(imageUrl_2);
                //初始化登录界面
                loginUIInit(imageUrl_2);
            }
         }else{
             System.out.println("parseLoginParam document is null");
         }
    }


    /**
     * 登录界面初始化方法
     * @param getImgUrl
     */
    public  void loginUIInit(String getImgUrl){
        //在界面上获得图片后，输入验证码后点击登录调用login方法
        try {
           HttpResponse res= crawlerUtil.noProxyGetUrl(getImgUrl) ;
            if (HttpStatus.SC_OK == res.getStatusLine().getStatusCode()) {
                //请求成功
                HttpEntity entity = res.getEntity();
                if (entity != null && entity.isStreaming()) {
                    InputStream is = entity.getContent();
                    image = ImageIO.read(is);
                    System.out.println("image is:"+image);
                }
            }

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
        lbl.setBounds(30, 20, 200, 200);    //设置图片
        start_button=new JButton("开始采集");
        start_button.addActionListener(this);
        start_button.setBounds(50, 210, 100, 40);
        val_text.setBounds(100,30,100,30);  //设置输入框位置
        //窗口设置
        int width= Toolkit.getDefaultToolkit().getScreenSize().width;
        int height=Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setSize(300, 300);
        frame.setLocation(width / 4, height / 4 - 50);
        jp1_jl1=new JLabel("验证码:",JLabel.CENTER);
        jp1_jl1.setBounds(0, 30, 100, 30); //设置label的位置
        c.add(jp1_jl1);//添加label
        c.add(lbl); //添加图片
        c.add(val_text) ; //添加文本框
        c.add(start_button);//添加按钮
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * 验证输入的验证码是否正确
     * @param seccodeverify
     * @return
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public Boolean validate(String seccodeverify) throws IOException, CloneNotSupportedException {
        String url="http://www.desire-taiwan.com/misc.php?mod=seccode&action=check&inajax=1&&idhash="+sechash+"&secverify="+seccodeverify ;
        System.out.println(url);
        Document doc=crawlerUtil.getDocUTF8(crawlerUtil.noProxyGetUrl(url));
        System.out.println(doc.toString());
        if(doc.toString().contains("succeed")){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 登录程序
     * @param username
     * @param password
     */
    public  void login(String username,String password,String seccodeverify){
        // 创建参数队列
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("formhash", formhash));
        list.add(new BasicNameValuePair("referer", referer));
        list.add(new BasicNameValuePair("loginfield", "username"));
        list.add(new BasicNameValuePair("username", username));
        list.add(new BasicNameValuePair("password", password));
        list.add(new BasicNameValuePair("questionid", "0"));
        list.add(new BasicNameValuePair("answer", ""));
        list.add(new BasicNameValuePair("sechash", sechash));
        list.add(new BasicNameValuePair("seccodeverify", seccodeverify));
        list.add(new BasicNameValuePair("cookietime", "2592000"));
        list.add(new BasicNameValuePair("loginsubmit", "true"));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(list, UTF8);
            Document doc=crawlerUtil.getDocUTF8(crawlerUtil.post(loginPostUrl,uefEntity));
            System.out.println(doc.toString());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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

    @Override
    public void actionPerformed(ActionEvent e) {
         if(e.getSource()==start_button){
//             start_button.setEnabled(false);
             System.out.println(val_text.getText());
             //调用登录方法进行登录
             try {
                if( validate(val_text.getText())){
                    login("ckwison","456897",val_text.getText());
                }
             } catch (IOException e1) {
                 e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             } catch (CloneNotSupportedException e1) {
                 e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             }
//
         }
    }
}


