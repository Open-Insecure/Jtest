package com.br.dong.httpclientTest;

import java.io.File;     
import java.io.FileOutputStream;     
import java.io.IOException;     
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
  
public class TestDownImage {
	 public static void main(String[] args) throws IOException{     
			String url="http://images.sohu.com/uiue/sohu_logo/beijing2008/2008sohu.gif";
			 HttpClient httpclient = new DefaultHttpClient();
			   // 设置超时时间(毫秒)
			   httpclient.getParams().setParameter(
			         CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
			   httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
			         60000);

			   File storeFile = new File("F://2008sohu.gif"); ;
			   try {
				      HttpGet httpget = new HttpGet(url);
				      HttpResponse res = httpclient.execute(httpget);
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
				            System.out.println("[" + url + "] 未找到.");
				         }
				         if (entity != null) {
				            entity.consumeContent();
				         }

				      }
				   } catch (IllegalArgumentException e) {
				      e.printStackTrace();

				   }
	    }     
}
