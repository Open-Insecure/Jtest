package com.br.dong.httpclientTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

public class DownloadFile {
	/**
	* 下载文件
	* 
	* @param url
	*            文件url
	* @param dest
	*            　文件保存路径
	* @return 未找到文件或出错返回null，否则返回目标文件
	* @throws ClientProtocolException
	*             　协议异常
	* @throws IOException
	*             　IO异常
	*/
	public static File downloadFile(String url, String dest)
	      throws ClientProtocolException, IOException {
	   if (url == null || url.trim().length() == 0) {
	      System.out.println("URL为空不做处理.");
	      return null;
	   }

	   HttpClient httpclient = new DefaultHttpClient();
	   // 设置超时时间(毫秒)
	   httpclient.getParams().setParameter(
	         CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
	   httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
	         60000);

	   File storeFile = null;
	   try {
	      HttpGet httpget = new HttpGet(url);
	      HttpResponse res = httpclient.execute(httpget);
	      if (HttpStatus.SC_OK == res.getStatusLine().getStatusCode()) {
	         //请求成功
	         HttpEntity entity = res.getEntity();

	         if (entity != null && entity.isStreaming()) {
	            // 　为目标文件创建目录
	            int lastSptAt = dest.lastIndexOf(File.separator);
	            File dir = new File(dest.substring(0, lastSptAt));
	            dir.mkdirs();
	            // 创建一个空的目标文件
	            storeFile = new File(dest);
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
	         } else {
	            System.out.println("[" + url + "] 未找到.");
	            return null;
	         }
	         if (entity != null) {
	            entity.consumeContent();
	         }

	      }
	   } catch (IllegalArgumentException e) {
	      e.printStackTrace();

	   }
	   return storeFile;

	}
}
