package com.br.dong.httpclientTest._auto_sign_2015_09_03;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.ProxyBean;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
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
    public static void main(String[] args) {
//        System.out.println(getBuyProxy());
        try {
            System.out.println(getBuyProxyByApi("3"));
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
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
     */
    public static List<ProxyBean> getBuyProxyByApi(String num) throws KeyManagementException, NoSuchAlgorithmException, IOException, CloneNotSupportedException {
        CrawlerUtil crawlerUtil=new CrawlerUtil();
        crawlerUtil.clientCreate("http", "vxer.daili666.com", "http://vxer.daili666.com/ip/?tid=557529282956844&num="+num+"&category=2&sortby=time&filter=on");
        String url="http://vxer.daili666.com/ip/?tid=557529282956844&num="+num+"&category=2&sortby=time&filter=on";//在线提取api
        HttpResponse response=crawlerUtil.noProxyGetUrl(url);
        System.out.println(response.getStatusLine().getStatusCode());
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
            list.add(proxy);
        }
        return list;
    }
    public static List<ProxyBean> getBuyProxy(){
        String proxyStr="114.218.23.31:8090\n" +
                "183.145.245.157:8090\n" +
                "115.222.15.103:18186\n" +
                "218.92.227.165:19279\n" +
                "221.232.27.205:18186\n" +
                "222.188.198.15:8090\n" +
                "49.74.222.89:8080\n" +
                "114.224.73.186:18186\n" +
                "180.168.34.149:8118\n" +
                "175.152.201.38:18186\n" +
                "115.214.162.84:8090\n" +
                "106.81.134.225:8080\n" +
                "14.109.111.2:18186\n" +
                "115.218.68.141:8888\n" +
                "117.25.72.42:8090\n" +
                "114.228.46.77:8090\n" +
                "115.208.217.179:8090\n" +
                "110.83.193.57:18186\n" +
                "61.154.14.237:8086\n" +
                "183.141.77.29:3128\n" +
                "60.162.158.109:18186\n" +
                "101.71.224.224:8090\n" +
                "112.112.11.82:8080\n" +
                "218.92.227.177:10000\n" +
                "122.226.21.196:8080\n" +
                "115.228.53.22:3128\n" +
                "175.152.188.128:18186\n" +
                "218.18.121.3:3128\n" +
                "222.94.55.166:18186\n" +
                "180.161.102.141:8080\n" +
                "114.224.140.92:18186\n" +
                "49.85.160.95:8080\n" +
                "115.214.167.54:8090\n" +
                "106.86.92.114:18186\n" +
                "122.230.127.17:8090\n" +
                "183.139.184.100:8080\n" +
                "61.157.136.35:8000\n" +
                "122.235.46.231:18186\n" +
                "110.80.65.58:18186\n" +
                "218.63.208.223:3128\n" +
                "125.82.109.142:18186\n" +
                "61.224.144.104:8888\n" +
                "115.228.59.216:3128\n" +
                "61.149.182.102:8080\n" +
                "27.19.101.83:18186\n" +
                "58.221.184.114:1337\n" +
                "124.94.187.84:8090\n" +
                "124.160.251.245:8090\n" +
                "221.235.82.226:8090\n" +
                "115.228.49.221:3128\n" +
                "111.73.196.154:18186\n" +
                "101.225.95.230:8080\n" +
                "122.230.99.193:8090\n" +
                "182.40.49.77:8090\n" +
                "118.121.46.75:8090\n" +
                "119.96.235.179:8090\n" +
                "123.129.143.155:8090\n" +
                "183.141.66.75:3128\n" +
                "123.9.169.196:8090\n" +
                "14.104.46.5:18186\n" +
                "115.214.7.207:8090\n" +
                "218.64.136.95:18186\n" +
                "117.65.203.160:18186\n" +
                "117.43.248.4:8888\n" +
                "118.114.120.136:18186\n" +
                "119.97.164.48:8085\n" +
                "120.42.66.105:8888\n" +
                "123.139.56.154:63000\n" +
                "115.150.113.136:18186\n" +
                "115.150.138.189:8080\n" +
                "122.96.59.105:82\n" +
                "60.183.83.72:18186\n" +
                "221.224.90.172:9090\n" +
                "27.19.112.125:18186\n" +
                "27.19.104.81:18186\n" +
                "125.123.81.243:3128\n" +
                "118.248.5.5:8090\n" +
                "140.75.153.129:8090\n" +
                "27.17.236.163:18186\n" +
                "114.230.243.170:8090\n" +
                "183.141.74.154:3128\n" +
                "218.92.227.173:21724\n" +
                "183.141.68.236:3128\n" +
                "211.90.28.102:80\n" +
                "27.26.172.1:18186\n" +
                "218.92.227.172:21725\n" +
                "139.226.151.21:8090\n" +
                "14.109.126.58:18186\n" +
                "183.139.169.31:8080\n" +
                "61.53.22.206:80\n" +
                "183.140.166.66:3128\n" +
                "182.119.163.226:18186\n" +
                "218.92.227.169:33919\n" +
                "106.89.94.162:18186\n" +
                "58.209.184.51:8090\n" +
                "218.92.227.171:17945\n" +
                "182.87.107.151:18186\n" +
                "122.232.228.79:3128\n" +
                "125.119.103.223:8090\n" +
                "114.105.89.154:18186";
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
        String [] citys={"Windows%207","Windows%208","windows%20xp"};
        Random random = new Random();
        int lenght=citys.length;
        return citys[random.nextInt(lenght)];
    }

    /**
     * 随机获得一个屏幕分辨率
     * @return
     */
    public static String randomPb(){
        String [] citys={"1600x1200","1920x1080","1280x720","1280x800","1680x1050"};
        Random random = new Random();
        int lenght=citys.length;
        return citys[random.nextInt(lenght)];
    }

}
