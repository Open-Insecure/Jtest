package com.br.dong.httpclientTest.proxy_2016_04_01;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.ProxyBean;
import com.br.dong.thread_socket_simply_test.mutileClientTest.bean.SysMonitor;
import org.apache.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2016-04-01
 * Time: 10:05
 * http://b-l-east.iteye.com/blog/1199237
 * 118.114.77.47:8080
 */
public class ProxyTest1 {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException {
//        test1(getBuyProxy());
        test2(getBuyProxy());
    }
    public static List<ProxyBean> getBuyProxy(){
        String proxyStr="60.13.15.182:8090\n" +
                "59.57.169.200:8090\n" +
                "60.182.156.181:8090\n" +
                "61.223.125.182:8088\n" ;
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
    private static void test1(List<ProxyBean> list) throws IOException {
       for(ProxyBean proxy:list){
           Socket socket = new Socket(proxy.getIp(),proxy.getPort());
           //写与的内容就是遵循HTTP请求协议格式的内容，请求百度
        socket.getOutputStream().write(new String("GET http://www.baidu.com/ HTTP/1.1\r\n\r\n").getBytes());
//           socket.getOutputStream().write(new String(url).getBytes());
           byte[] bs = new byte[1024];
           InputStream is = socket.getInputStream();
           int i;
           while ((i = is.read(bs)) > 0) {
               System.out.println("ip:"+proxy.getIp()+new String(bs, 0, i));
           }
           is.close();
       }
    }
    private static void test2(List<ProxyBean> list) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        CrawlerUtil crawlerUtil=new CrawlerUtil();
        crawlerUtil.clientCreate("http", "www.59178.com","http://www.59178.com/ip.asp");
        System.out.println("初始的" + getIp());
        for(ProxyBean proxy:list){
          try{

              //设置代理
              System.setProperty("http.proxySet", "true");
              System.setProperty("http.proxyHost", proxy.getIp());
              System.setProperty("http.proxyPort", proxy.getPort() + "");
              System.out.println("设置的:"+ System.getProperty("http.proxyHost"));
              //直接访问目的地址
              URL url = new URL("http://www.59178.com/ip.asp");
              URLConnection con = url.openConnection();
              InputStreamReader isr = new InputStreamReader(con.getInputStream(),"gb2312");
              char[] cs = new char[1024];
              int i = 0;
              StringBuffer sb=new StringBuffer();
              while ((i = isr.read(cs)) > 0) {
//                  System.out.println(new String(cs, 0, i));
                  sb.append(new String(cs, 0, i));
              }
              isr.close();
            Document document= Jsoup.parse(sb.toString());
//              HttpResponse response= crawlerUtil.noProxyGetUrl("http://www.59178.com/ip.asp");
//              Document document=crawlerUtil.getDocument(response.getEntity(), "gb2312");
////              System.out.println(document);
              String content=document.select("font[face=Arial]").text();
              System.out.println("代理的:"+content);
          }catch (Exception e){
              e.printStackTrace();
          }
        }
    }
    /**
     * 多IP处理，可以得到最终ip
     * @return
     */
    public static String getIp() {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            InetAddress ip = null;
            boolean finded = false;// 是否找到外网IP
            while (netInterfaces.hasMoreElements() && !finded) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();
//                  System.out.println(ni.getName() + ";" + ip.getHostAddress()
//                          + ";ip.isSiteLocalAddress()="
//                          + ip.isSiteLocalAddress()
//                          + ";ip.isLoopbackAddress()="
//                          + ip.isLoopbackAddress());
                    if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                        netip = ip.getHostAddress();
                        finded = true;
                        break;
                    } else if (ip.isSiteLocalAddress()
                            && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                        localip = ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }
}
