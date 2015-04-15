package com.br.dong.httpclientTest.porn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/** 
 * @author  hexd
 * 创建时间：2014-8-12 上午9:17:42 
 * 类说明 
 * 测试下载文件
 */
public class FileDownTest {
	private final static String REMOTE_FILE_URL = "http://www.gjt.org/download/time/java/tar/javatar-2.5.tar.gz";  
	//http://91p.vido.ws/view_video.php?viewkey=521bc3bca29b6eab14fa&page=1&viewtype=basic&category=rf
	//http://91p.vido.ws/getfile.php?VID=83283&mp4=0&seccode=a90158c08fa3cb05ad5e53b9797bfb20&max_vid=83322
	private static String url="http://50.7.69.10//dl//12c385200841087af4636ceb81c11a55/54cfded3//91porn/mp43/102072.mp4";

    private final static int BUFFER = 10240;
  
    public static void main(String[] args) {  
  
       HttpClient client = new DefaultHttpClient();  
       HttpGet httpGet = new HttpGet(url);  
        try {  
            HttpResponse response= client.execute(httpGet);  
              
            //请求成功
	         HttpEntity entity = response.getEntity();
	         InputStream in = entity.getContent();
             
            FileOutputStream out = new FileOutputStream(new File("E:\\video\\aa.mp4"));
             
            byte[] b = new byte[BUFFER];  
            int len = 0;  
            while((len=in.read(b))!= -1){  
                out.write(b,0,len);  
            }  
            in.close();  
            out.close();  
              
        }catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            httpGet.abort();  
        }  
        System.out.println("download, success!!");  
       }  
}
