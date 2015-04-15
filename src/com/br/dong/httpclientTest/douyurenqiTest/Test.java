package com.br.dong.httpclientTest.douyurenqiTest;

import com.br.dong.httpclientTest.CrawlerUtil;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-3-18
 * Time: 下午10:00
 * To change this template use File | Settings | File Templates.
 * 试试看刷斗鱼人气
 */
public class Test {
    private static String url="http://www.douyutv.com/216868";
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, IOException, CloneNotSupportedException, InterruptedException {
           CrawlerUtil client=new CrawlerUtil();
           client.clientCreate("http","www.douyutv.com",url);
           for(int i=0;i<500;i++){
               Thread.sleep(1100);
               Document doc=client.getDocUTF8(client.noProxyGetUrl(url));
               System.out.println(doc.toString());
//               Date dt= new Date();
//               Long time= dt.getTime();
//               String url2="http://www.douyutv.com/live_specific/get_room_show_info?device=94F242DDEB2EA828DDD1311DDB612567&time="+String.valueOf(time)+"&adv=897b61d52b9c0ae15800a1e63d1a3a86&roomid=116092";
//               Document doc2=client.getDocUTF8(client.noProxyGetUrl(url2));
               System.out.println("第"+"["+i+"]次！");
//               System.out.println(doc2.toString());
           }

    }
}
