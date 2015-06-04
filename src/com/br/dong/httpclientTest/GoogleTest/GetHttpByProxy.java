package com.br.dong.httpclientTest.GoogleTest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.util.EntityUtils;
/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-4-27
 * Time: 17:13
 */
public class GetHttpByProxy {
    public static void main(String[] args) throws ClientProtocolException,
            IOException {
        //ʵ����һ��HttpClient
        HttpClient httpClient = new DefaultHttpClient();
        //�趨Ŀ��վ��
        HttpHost httpHost = new HttpHost("http://91.v4p.co/view_video.php?viewkey=55f767c19a4c6f588681");
        //���ô������ ip/��������,�˿�
        HttpHost proxy = new HttpHost("125.39.66.67", 80);
        //��HttpClient�������ô���
        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
                proxy);
        HttpGet httpGet = new HttpGet("/");
        //����Ҳ����ֱ��ʹ��httpGet�ľ��Ե�ַ����Ȼ������Ǿ����ַ��Ҫ����/��β
        //HttpGet httpGet = new HttpGet("http://www.shanhe114.com/");
        //HttpResponse response = httpClient.execute(httpGet);

        HttpResponse response = httpClient.execute(httpHost, httpGet);
        if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
            //����ɹ�
            //ȡ����������
            HttpEntity entity = response.getEntity();
            //��ʾ����
            if (entity != null) {
                // ��ʾ���
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity
                        .getContent(), "UTF-8"));
                String line = null;
                StringBuffer strBuf = new StringBuffer((int) entity.getContentLength());
                while ((line = reader.readLine()) != null) {
                    strBuf.append(line);
                }
                strBuf.trimToSize();
                System.out.println(strBuf.toString());
            }
            if (entity != null) {
                entity.consumeContent();
            }
        }else{
            System.out.println("aaaaa");
        }
    }
}
