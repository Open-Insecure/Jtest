package com.br.dong.httpclientTest.proxy_2015_03_14;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.ProxyBean;
import com.br.dong.utils.DateUtil;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-3-14
 * Time: 下午2:26
 * To change this template use File | Settings | File Templates.
 * 采集http://www.proxy.com.ru/list_1.html的代理信息
 * 采集的过程中就验证代理是否可用
 */
public class ProxyGet2015_03_14 {
    private static int i=1;
    private static String url="http://www.proxy.com.ru/list_"+i+".html";//采集列表
    private static CrawlerUtil client=new CrawlerUtil();

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, IOException, CloneNotSupportedException {
//        getMaxList(url,10);
        client.clientCreate("http","www.proxy.com.ru","http://www.proxy.com.ru/list_1.html");
        for(int i=1;i<=4;i++){
            System.out.println("start i:"+i);
            parseTable("http://www.proxy.com.ru/list_"+i+".html");
        }

//       while(true){
//           doMySelf();
//       }
    }
    public static int getMaxList(String url, int max) throws IOException, CloneNotSupportedException {
//      if(max!=0) return max;
       Document doc= client.getDocument(client.noProxyGetUrl(url).getEntity(),"gb2312");
        System.out.println(doc.toString());
        return max;
    }
    public static void doMySelf() throws NoSuchAlgorithmException, KeyManagementException, IOException, CloneNotSupportedException {
        System.out.println("start.."+"http://www.10pan.cc/file-142264.html");
        CrawlerUtil cc=new CrawlerUtil();
        cc.clientCreate("http","www.10pan.cc","http://www.10pan.cc/file-142264.html");
        HttpResponse response=cc.noProxyGetUrl("http://www.10pan.cc/file-142264.html");
        if(response!=null){
            Document downdoc=cc.getDocUTF8(response);
            System.out.println(downdoc.toString());
        }
        List<NameValuePair> list2=new ArrayList<NameValuePair>();
        list2.add(new BasicNameValuePair("action", "down_process"));
        list2.add(new BasicNameValuePair("file_id", "142264"));
        list2.add(new BasicNameValuePair("ms", "626*79"));
        list2.add(new BasicNameValuePair("sc", "1366*768"));
        UrlEncodedFormEntity uefEntity;
        uefEntity = new UrlEncodedFormEntity(list2, "UTF-8");
        Document dd2=cc.getDocUTF8(cc.post("http://www.10pan.cc/ajax.php",uefEntity));
        System.out.println("dddd2"+dd2.toString());
    }

    public static void parseTable(String url) throws IOException, CloneNotSupportedException, NoSuchAlgorithmException, KeyManagementException {
        Document doc= client.getDocument(client.noProxyGetUrl(url).getEntity(),"gb2312");
        Elements elements=doc.select("font[size=2]:eq(0)").select("table>tbody>tr");
//        System.out.println(elements.html());
        for(int i=1;i<elements.size();i++){
            Element e=elements.get(i);
            String ip=e.select("td:eq(1)").text();
            String tport=e.select("td:eq(2)").text().trim();
            String type=e.select("td:eq(3)").text();
            System.out.println(ip+":"+tport+type);
            //开始检测代理是否可用
//            ProxyCheckUtil.check(ip,Integer.parseInt(tport),"http","91p.vido.ws");
            if(ProxyCheckUtil.check(ip,Integer.parseInt(tport),"http","www.10pan.cc")){
                System.out.println("start.."+"http://www.10pan.cc/file-142264.html");
                CrawlerUtil cc=new CrawlerUtil();
                cc.clientCreate("http","www.10pan.cc","http://www.10pan.cc/file-142264.html");
                HttpResponse response=cc.proxyGetUrl("http://www.10pan.cc/file-142264.html",ip,Integer.parseInt(tport));
                if(response!=null){
                    Document downdoc=cc.getDocUTF8(response);
                    System.out.println(downdoc.toString());
                }
                List<NameValuePair> list2=new ArrayList<NameValuePair>();
                list2.add(new BasicNameValuePair("action", "down_process"));
                list2.add(new BasicNameValuePair("file_id", "142264"));
                list2.add(new BasicNameValuePair("ms", "626*79"));
                list2.add(new BasicNameValuePair("sc", "1366*768"));
                HttpResponse response2=cc.proxyPostUrl("http://www.10pan.cc/ajax.php",ip,Integer.parseInt(tport),list2);
                if(response2!=null){
                    Document dd2=cc.getDocUTF8(response2);
                    System.out.println("dddd2"+dd2.toString());
                }

//                System.out.println("dddddddd"+dd.body().toString());
//                String href=dd.toString().substring(dd.toString().indexOf("<a href=\"") + "<a href=\"".length(), dd.toString().indexOf("\" onclick=\"down_process2"));
//                System.out.println("hhhref"+href);
//                String  href=downdoc.select("div[id=addr_list]").first().select("a").first().attr("abs:href");
//                System.out.println("href:"+href.toString());
//                HttpResponse response= cc.proxyGetUrl(href,ip,Integer.parseInt(tport));
                //模拟下载
//                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                    System.out.println("准备下载了:");
//                    byte[] result = EntityUtils.toByteArray(response.getEntity());
//                    BufferedOutputStream bw = null;
//                    try {
//                        // 创建文件对象
//                        File f = new File("D:\\aa.txt");
//                        // 创建文件路径
//                        if (!f.getParentFile().exists())
//                            f.getParentFile().mkdirs();
//                        // 写入文件
//                        bw = new BufferedOutputStream(new FileOutputStream("D:\\aa.txt"));
//                        bw.write(result);
//                    } catch (Exception se) {
//                        se.printStackTrace();
//                    } finally {
//                        try {
//                            if (bw != null)
//                                bw.close();
//                        } catch (Exception a) {
//
//                        }
//                    }
//                }
            }

         }

    }
}
