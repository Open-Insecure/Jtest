package com.br.dong.httpclientTest.proxy_2015_03_14;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.ConnectException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-3-24
 * Time: 下午4:24
 * To change this template use File | Settings | File Templates.
 * 检测代理连接是否正常
 */
public class ProxyCheckUtil {
    //超时时间
    private static int TIME_OUT_TIME=8000;//8秒连接超时
    public static  Boolean check(String ip,int port,String type,String target)  {
        Boolean flag=false;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT_TIME);
        //设定目标站点     www.baidu.com "http://www.firstgongyu.com/forum-2-1.html"
        HttpHost httpHost = new HttpHost(target);
        //设置代理对象 ip/代理名称,端口
        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(ip, port, type));
        //实例化验证
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        //设定验证内容
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials("test", "test");
        //创建验证
        credsProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);
        ((DefaultHttpClient) httpClient).setCredentialsProvider(credsProvider);
        // 目标地址
        HttpGet httpget = new HttpGet("/");
        try{
        HttpResponse response = httpClient.execute(httpHost, httpget);
            //请求成功
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            if(response.getStatusLine().getStatusCode()==200){
                flag=true;
                HttpEntity entity = response.getEntity();
//                if (entity != null) {
//                    System.out.println(EntityUtils.toString(entity));
//                }
            }
            System.out.println("ip:"+ip+":"+port+" status code:["+response.getStatusLine().getStatusCode()+"]" +"available:["+flag+"]");
        }
        }catch (ConnectException e){
//            e.printStackTrace();
            System.out.println("ip:"+ip+":"+port+"ConnectException");
        } catch (ClientProtocolException e) {
            System.out.println("ip:"+ip+":"+port+"ClientProtocolException");
        } catch (ConnectTimeoutException e){
            System.out.println("ip:"+ip+":"+port+"ConnectTimeoutException");
        } catch (IOException e) {
            System.out.println("ip:"+ip+":"+port+"IOException");
        }

        //释放资源
        httpClient.getConnectionManager().shutdown();
        return flag;
    }
}
