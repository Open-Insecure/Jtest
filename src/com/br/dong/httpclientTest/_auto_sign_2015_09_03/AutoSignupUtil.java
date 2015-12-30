package com.br.dong.httpclientTest._auto_sign_2015_09_03;

import com.br.dong.file.FileOperate;
import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.ProxyBean;
import com.br.dong.httpclientTest.youbb.net.YoubbBean;
import com.br.dong.jdbc.mysql.connect.GetComJDBC;
import com.br.dong.utils.PropertiesUtil;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import javax.print.Doc;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-09-16
 * Time: 13:45
 * 自动注册通用工具类
 */
public class AutoSignupUtil {
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
    private static final char[] eng_char = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};//用户尾
    private static final String[] first_name = new String[]{"zhao","qian","sun","li","zhou","wang","mao","zheng","feng","chen","chu","wei","jiang","shen","yang"
            ,"zhu","qin","you","shi","zhan","kong","cao","xie","jin","shu","fang","yuan","zhang","chang","liang","dong","kang"};//用户头
    private static   String[] pwds=null;//声明一个6000的数组
    private static final int max_pwd_leng=2543;//密码txt最多有5953个密码
    private static final String[] browsers=new String []{
            "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",//safari 5.1 – MAC
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)",//IE 9.0
            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0)",//IE 8.0
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",//Firefox 4.0.1 – MAC
            "Opera/9.80 (Windows NT 6.1; U; en) Presto/2.8.131 Version/11.11",//Opera 11.11 – Windows
            "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11",//Opera 11.11 – MAC
            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0)",//IE 8.0
            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0)",//IE 8.0
            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.2; Trident/4.0)",//IE 8.0
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11",//Chrome 17.0 – MAC
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)",//360 浏览器
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; 360SE)",//360 浏览器
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.2; 360SE)",//360 浏览器
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; The World)",//世界之窗（The World） 3.x
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)",//世界之窗（The World） 2.x
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; TencentTraveler 4.0)",//腾讯TT
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; TencentTraveler 4.0)",//腾讯TT
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.2; TencentTraveler 4.0)",//腾讯TT
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon 2.0)",//傲游（Maxthon）
    };
    private static PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("/com/br/dong/httpclientTest/_auto_sign_2015_09_03/properties/config.properties");//读取配置文件
    private static String DATABASE_USER=propertiesUtil.getPropValue("DATABASE_USER");//数据库登录用户
    private static String DATABASE_PASSWORD=propertiesUtil.getPropValue("DATABASE_PASSWORD");//数据库登录密码
    private static String DATABASE_HOST=propertiesUtil.getPropValue("DATABASE_HOST");//数据库ip
    private static String DATABASE_PORT=propertiesUtil.getPropValue("DATABASE_PORT");//数据库端口
    private static String DATABASE_INSTANCE=propertiesUtil.getPropValue("DATABASE_INSTANCE");//数据库实例
    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException, CloneNotSupportedException {
//        Random random = new Random();
//        int s = random.nextInt(max_pwd_leng)%(max_pwd_leng-0+1) + 0;
//        for(int i=0;i<pwds.length;i++){
//            if(!pwds[i].contains("19")){
//                System.out.println(pwds[i]);
//            }
//        }
        //--测试是否浏览器信息能被识别
//        for(int i=0;i<20;i++){
//            CrawlerUtil client=new CrawlerUtil();
//            String browser=randomBrower();
//            System.out.println(browser);
//            client.clientCreate("http","127.0.0.1","http://127.0.0.1",browser);
//            client.noProxyGetUrl("http://localhost:9088/v/contact/test");
//        }
        CrawlerUtil client=new CrawlerUtil();//http://dbeee.org/1?u=a65936&b=2
        client.clientCreate("http", "www.dbeee.org", "http://dbeee.org/1/?u=a65861&b=2");
        HttpResponse response=client.noProxyGetUrl("http://dbeee.org/1/?u=a65861&b=2");
        Document document=client.getDocument(response.getEntity(),"gb2312");
        System.out.println(document);
//        System.out.println(randomDatabseUserName());
    }
    static{
             //默认就运行一遍
//        pwds =  initPwds(propertiesUtil.getPropValue("pwdtxt"));
        initUserNames(propertiesUtil.getPropValue("usertxt"));//初始化加载用户信息txt文档到数据库中
    }

    /**
     * 从数据库中随机拿到一个用户信息
     * @return
     */
   public static Map randomDatabseUserName(){
       String sql="SELECT * FROM  signuser \n" +
               "WHERE id >= (SELECT floor( RAND() * ((SELECT MAX(id) FROM signuser)-(SELECT MIN(id) FROM signuser)) + (SELECT MIN(id) FROM signuser)))  \n" +
               "ORDER BY id LIMIT 1";
       List list= GetComJDBC.getExecuteQuery(DATABASE_USER, DATABASE_PASSWORD, DATABASE_HOST, DATABASE_PORT, DATABASE_INSTANCE, sql);
       Map map= (Map) list.get(0);
       return map;
   }

    /**
     * 检查已经使用过的ip地址
     * @return
     */
    public static boolean checkUsedProxy(String ip){
        String sql="select count(*) as count from usedproxy where ip='"+ip+"'";
        List list= GetComJDBC.getExecuteQuery(DATABASE_USER, DATABASE_PASSWORD, DATABASE_HOST, DATABASE_PORT, DATABASE_INSTANCE, sql);
        Map map= (Map) list.get(0);
        int count=Integer.parseInt((String)map.get("count"));
        if(count>0) return true;//如果已经有使用的则返回true
        return false;//未使用过此ip 返回false
    }

    /**
     * 插入一条已经使用过的代理ip信息
     * @param ip
     * @param port
     */
    public static void insertUsedProxy(String ip,String port){
        String sql="insert into usedproxy (ip,port) values ('"+ip+"','"+port+"')";
        GetComJDBC.ExecuteSql(DATABASE_USER, DATABASE_PASSWORD, DATABASE_HOST, DATABASE_PORT, DATABASE_INSTANCE, sql);
    }
    /**
     * 移除一条用户信息
     * @param username
     * @param password
     */
    public static void removeUserInfo(String username,String password){
        String sql="delete from signuser where username='"+username+"' and password='"+password+"'";
         GetComJDBC.ExecuteSql(DATABASE_USER, DATABASE_PASSWORD, DATABASE_HOST, DATABASE_PORT, DATABASE_INSTANCE, sql);
    }
    /**
     * 插入用户信息
     */
    public static int insertUserInfo(String username,String password,String email){
        String sql="insert into signuser (username,password,email) values ('"+username+"','"+password+"','"+email+"')";
        return GetComJDBC.ExecuteSql(DATABASE_USER, DATABASE_PASSWORD, DATABASE_HOST, DATABASE_PORT, DATABASE_INSTANCE, sql);//测试jdbc连接查询
    }
    /**
     * 检查库中是否已经有存在的信息
     * @return true-表示 表中无数据 可以导入  false -表中有数据 不要导入
     */
    public static Boolean checkUserCount(){
        String sql="select count(*) as count from signuser ";
        List list=GetComJDBC.getExecuteQuery(DATABASE_USER, DATABASE_PASSWORD, DATABASE_HOST, DATABASE_PORT, DATABASE_INSTANCE, sql);
        String count = (String) ((Map)list.get(0)).get("count");
        if(Integer.parseInt(count)==0) return true;
        return false;
    }
    /***
     * 从用户列表中初始化用户数据到表中
     */
    public static void initUserNames(String filePath){
        System.out.println("loading txt:"+filePath);
        if(!checkUserCount()) return;//表中已经有数据 不导入
        BufferedReader in=null;
        try{
            in=new BufferedReader(new FileReader(filePath));
            String line;
            try {
                while((line=in.readLine())!=null){
                    String user[]=line.split("#");
                    insertUserInfo(user[0], user[1],user[2]);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }finally {
            System.out.println("init table signuser complete!");
        }
    }

    /**
     * 初始化密码txt到内存中
     * @param filePath
     * @return
     */
    public static String[] initPwds(String filePath){
        System.out.println("loading txt:"+filePath);
        String temp[]=new String[max_pwd_leng+1];
        BufferedReader in=null;
        try{
            in=new BufferedReader(new FileReader(filePath));
            String line;
            int i=0;
            try {
                while((line=in.readLine())!=null){
                    temp[i]=line;
                    i++;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return temp;
    }
    /***
     * 加载网络验证码图片
     * @param targetUrl
     */
    public static BufferedImage loadCodeImg(String targetUrl,String ip,int port,CrawlerUtil ct){
        try {
            HttpResponse res= ct.proxyGetUrl(targetUrl, ip, port) ;
            if (res!=null&& HttpStatus.SC_OK == res.getStatusLine().getStatusCode()) {
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

    public static String randomPwd(){
        Random random = new Random();
        int lenght=pwds.length;
        return pwds[random.nextInt(lenght)];
    }

    /**
     * 随机获得一个用户名
     * @return
     */
    public static String randomUserName(){
        Random random = new Random();
        StringBuilder uName = new StringBuilder();//用户名
        uName.append(first_name[random.nextInt(Integer.MAX_VALUE)%first_name.length])
                .append(eng_char[random.nextInt(Integer.MAX_VALUE)%eng_char.length]);

        if(random.nextInt(Integer.MAX_VALUE)%2 == 0){
            uName.append(eng_char[random.nextInt(Integer.MAX_VALUE)%eng_char.length]);
        }

        // birthday
        if(random.nextInt(Integer.MAX_VALUE)%2 == 0){
            uName.append(String.valueOf(2014 - (random.nextInt(Integer.MAX_VALUE)%(50-15) + 15))); // 大于15小于50岁
        }
        if(random.nextInt(Integer.MAX_VALUE)%2 == 0){
            int month = random.nextInt(Integer.MAX_VALUE)%11 + 1;
            int day = random.nextInt(Integer.MAX_VALUE)%29 + 1;
            if(month < 10)
                uName.append("0");
            uName.append(month);
            if(day < 10)
                uName.append("0");
            uName.append(day);
        }
        return uName.toString();
    }
    /***
     * 获得指定长度的随机字符串
     * @param length
     * @return
     */
    public  static String randomString(int length) {
        if (length < 1) {
            return null;
        }
        char[] numbersAndLetters  = ("0123456789abcdefghijklmnopqrstuvwxyz" +
                "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
        char [] randBuffer = new char[length];
        for (int i=0; i<randBuffer.length; i++) {
            Random ranGen=new Random();
            randBuffer[i] = numbersAndLetters[ranGen.nextInt(71)];
        }
        return new String(randBuffer);
    }
    /***
     *返回随机的qq
     * @return
     */
    public static String randomNumber(){
        String number="123456789";
        Random random = new Random();
        int charsLength=number.length();
        StringBuilder qq = new StringBuilder();
        for(int i=0;i<9;i++){
            char qqChar = number.charAt(random.nextInt(charsLength));
            qq.append(qqChar);
        }
        return qq.toString();
    }
    /***
     * 随机返回城市
     * @return
     */
    public static  String randomProvince(){
        String [] citys={"北京","上海","广东"};
        Random random = new Random();
        int lenght=citys.length;
        return citys[random.nextInt(lenght)];
    }
    /***
     * 随机返回城市对应的市县
     */
    public static  String randomCity(String province){
        Random random = new Random();
        String [] beijing={"东城", "西城", "崇文", "宣武", "朝阳", "丰台", "石景山", "海淀", "门头沟", "房山", "通州", "顺义", "昌平", "大兴 ", "怀柔", "平谷", "密云县", "延庆县"};
        String [] shanghai={"广州市", "韶关市", "深圳市", "珠海市", "汕头市", "佛山市", "江门市", "湛江市", "茂名市", "肇庆市", "惠州市", "梅州市", "汕尾市", "河源市", "阳江市", "清远市", "东莞市", "中山市", "潮州市", "揭阳市", "云浮市"};
        String [] guangdong={"东城", "西城", "崇文", "宣武", "朝阳", "丰台", "石景山", "海淀", "门头沟", "房山", "通州", "顺义", "昌平", "大兴 ", "怀柔", "平谷", "密云县", "延庆县"};
        if(province.equals("北京")){
            return beijing[random.nextInt(beijing.length)];
        } else if(province.equals("上海")){
            return shanghai[random.nextInt(shanghai.length)];
        }else{
            return guangdong[random.nextInt(guangdong.length)];
        }
    }

    /**
     * 在线api提取代理
     * @param num
     * @return
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws CloneNotSupportedException
     * d
     */
    public static List<ProxyBean> getBuyProxyByApi(String num) throws KeyManagementException, NoSuchAlgorithmException, IOException, CloneNotSupportedException {
        CrawlerUtil crawlerUtil=new CrawlerUtil();
        String url="http://vxer.daili666api.com/ip/?tid=559943419055853&num="+num+"&filter=on";//在线提取api
        crawlerUtil.clientCreate("http", "vxer.daili666.com", url);
        HttpResponse response=crawlerUtil.noProxyGetUrl(url);
//        System.out.println(response.getStatusLine().getStatusCode());
        if(response==null) return null;
        Document document=crawlerUtil.getDocUTF8(response);
        String proxyStr=document.text();//获得代理字符串
        System.out.println(proxyStr);
        String temp[]=proxyStr.split(" ");
        List<ProxyBean> list=new ArrayList<ProxyBean>();
        for(int i=0;i<temp.length;i++){
            String p[]=temp[i].split(":");
            ProxyBean proxy=new ProxyBean();
            proxy.setIp(p[0]);
            proxy.setPort(Integer.parseInt(p[1]));
            //在此与插入的统
            list.add(proxy);
        }
        return list;
    }
    @Deprecated
    public static List<ProxyBean> getBuyProxy(){
        String proxyStr="139.227.150.33:8090";
        String temp[]=proxyStr.split("\n");
        List<ProxyBean> list=new ArrayList<ProxyBean>();
      for(int i=0;i<temp.length;i++){
          String p[]=temp[i].split(":");
          ProxyBean proxy=new ProxyBean();
          proxy.setIp(p[0]);
          proxy.setPort(Integer.parseInt(p[1]));
          list.add(proxy);
      }
        return list;
    }
    /**
     * 拿去最新的代理信息
     * @return
     */
    @Deprecated
    public static List<ProxyBean> getLastProxy(){
//        String url_xici="http://www.xicidaili.com/nn/2";//西刺代理最新国内代理
        String url_xici="http://www.xicidaili.com/wn/2";//西刺代理最新国外代理
        CrawlerUtil proxyClient=new CrawlerUtil();
        List<ProxyBean> list=new ArrayList<ProxyBean>() ;
        System.out.println("get proxy from"+url_xici);
        try {
            proxyClient.clientCreate("http","www.xicidaili.com",url_xici);
            Document doc=proxyClient.getDocUTF8(proxyClient.noProxyGetUrl(url_xici));
//            System.out.println(doc.toString());
            Elements iplist=doc.select("table[id*=ip_list]>tbody>tr");
            //暂时先拿去10页的
            for(Element e:iplist){
                String ip=e.select("td:eq(2)").text();//端口
                String tport=e.select("td:eq(3)").text();//端口
                String type=e.select("td:eq(6)").text();//类型
                String time=e.select("td:eq(9)").text();//时间
                //过滤出http的代理 插入数据库
                if("http".equals(type.toLowerCase())){
                    int port=Integer.parseInt(tport);
                    ProxyBean proxy=new ProxyBean(ip,port,type, time);
                    System.out.println(proxy.toString());
                    list.add(proxy);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return list;
    }

    /**
     * 随机获得一个操作系统的名字
     * @return
     */
    public static String randomOsName(){
        String [] citys={"Windows%207","Windows%208","Windows%20Me"};
        Random random = new Random();
        int lenght=citys.length;
        return citys[random.nextInt(lenght)];
    }

    /**
     * 随机获得一个屏幕分辨率
     * @return
     */
    public static String randomPb(){
        String [] citys={"1600*1200","1920*1080","1280*720","1280*800","1680*1050","1440*900","1680*1050","1366*768","1280*1024"};
        Random random = new Random();
        int lenght=citys.length;
        return citys[random.nextInt(lenght)];
    }
    /***
     * 随机获得一个操作系统/浏览器信息用来放置header中
     */
    public static String randomBrower(){
        Random random = new Random();
        int lenght=browsers.length;
        return browsers[random.nextInt(lenght)];
    }

    /***
     * 返回一个随机的randomllqok参数
     * @return
     */
    public static String randomllqok(){
        String []llqok={"%20360%20QQ","%20360%20搜狗%20谷歌360%20极速","%20火狐","%20360%20遨游","%20360%20uc","%20360%20uc%20谷歌360极速%20百度"};
        Random random = new Random();
        int lenght=llqok.length;
        return llqok[random.nextInt(lenght)];
    }

}
