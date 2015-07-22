package com.br.dong.httpclientTest.downloadUtil;

import com.br.dong.httpclientTest.CrawlerUtil;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-07-10
 * Time: 21:06
 * 下载线程类
 * 支持range下载的方式
 * 添加监听为了方便调用此类进行下载完成后的扩展，如插入下载数据到数据库中
 */
public class DownloadThread  extends Thread/**implements Callable<String>*/ {
    String tname;//线程名
    long startPosition;//开始标记
    long endPosition;//结束标记
    String url;//下载链接
    File file;//下载到本地文件
    float size;//文件大小
    float downloaded;//已经下载大小
    String title;//文件的标题
    String vkey;//该文件在数据库中的唯一标示key。下载文件到本地路径的子文件名字，如E:\video\vkey\vkey.flv
    String time;//如果是视频文件,则表示该文件的时长
    boolean isRange=false;//是否支持range true 表示支持,false 不支持
    DownloadThreadListener listener;//下载线程监听
    CrawlerUtil client = new CrawlerUtil();//CrawlerUtil实例client
    String type;//创建CrawlerUtil实例client的类型。hhtp或者https
    String hosturl;//主机host
    String refUrl;//主机ref url
    private final static int BUFFER = 1024;//缓冲数组大小
    private static Logger logger = Logger.getLogger(DownloadThread.class);//日志
    /**
     * 支持range下载的下载线程构造方法
     * @param tname 线程名
     * @param url 下载video链接
     * @param startPosition 文件起始标记
     * @param endPosition 文件结束标记
     * @param file 本地文件路径构造的file实例
     * @param type 创建CrawlerUtil实例client的类型
     * @param hosturl 主机host
     * @param refUrl 主机ref url
     */
    DownloadThread(String tname,String url, long startPosition, long endPosition,
                   File file,String type,String hosturl,String refUrl) {
        this.tname=tname;
        this.url = url;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.isRange = true;//支持range下载
        this.file = file;
        this.downloaded = 0;
        this.type=type;
        this.hosturl=hosturl;
        this.refUrl=refUrl;
    }
    /**
     * 不支持range多线程下载的下载线程构造方法
     * 此类构造方法，主要为了拿到title与videoTime两个参数
     * 好在DownloadThreadListener实现downCompleted时在主线程中插入记录到数据库中 方便拿到中文title与视频时长作为网站前端页面用的参数
     * @param tname 线程名
     * @param vkey 该文件在数据库中的唯一标示vkey
     * @param title 该文件在数据库中的中文标题描述
     * @param videoTime 如果该线程下载的是视频文件,该文件在数据库中的视频文件的时长
     * @param url 下载链接
     * @param file 本地文件路径构造的file实例
     * @param type 创建CrawlerUtil实例client的类型
     * @param hosturl 主机host
     * @param refUrl 主机ref url
     */
    public DownloadThread(String tname,String vkey, String title,String videoTime,String url, File file, String type, String hosturl, String refUrl) {
        this.tname=tname;
        this.vkey=vkey;
        this.title=title;
        this.time=videoTime;
        this.url = url;
        this.isRange = false;//不支持range下载
        this.file = file;
        this.downloaded = 0;
        this.type=type;
        this.hosturl=hosturl;
        this.refUrl=refUrl;
    }
    /**
     * 不支持range多线程下载的下载线程构造方法
     * 不存库
     * @param tname 线程名
     * @param url 下载链接
     * @param file 本地文件路径构造的file实例
     * @param type 创建CrawlerUtil实例client的类型
     * @param hosturl 主机host
     * @param refUrl 主机ref url
     */
    public DownloadThread(String tname, String url, File file, String type, String hosturl, String refUrl) {
        this.tname=tname;
        this.url = url;
        this.isRange = false;//不支持range下载
        this.file = file;
        this.downloaded = 0;
        this.type=type;
        this.hosturl=hosturl;
        this.refUrl=refUrl;
    }


    /**
     * 添加下载监听
     * 该监听的实例在创建该线程实例的类中进行初始化并调用该方法传入
     * 实现方便定制监听的作用
     * @param listener
     */
    public void addDownloadListener(DownloadThreadListener listener) {
        this.listener = listener;
    }

    /***
     * 返回当前已经下载的文件大小
     * @return
     */
    public float getdownLoaded() {
        return this.downloaded;
    }
    /**
     * 线程启动方法
     */
    public void run(){
        try {
            System.out.println("创建线程"+tname);
            client.clientCreate(type,hosturl , refUrl);//创建下载的client
        } catch (Exception e){
            logError(tname, "error-->create client fall" + e.getMessage());//创建client失败 直接返回信息
            return;
        }

//        HttpGet httpGet = new HttpGet(url);//创建下载get请求
        try{
            Thread.sleep(2000);
//           if (!readDownloadFileInfo()) {//检查文件大小
//               logError(tname, "error-->no resources find");
//                return;
//           }else{
////               logMsg(tname, file.getName()+" size:"+formatFloat(size / 1024)+"Kb");
//           }
            byte[] buffer = new byte[BUFFER];//文件缓冲区
            if (isRange) {//如果该线程是需要支持range下载的线程
//                httpGet.addHeader("Range", "bytes=" + startPosition + "-" + endPosition);// 设置get请求头 每次需要下载的range参数
            }
            HttpResponse response = client.noProxyGetUrl(url);//获得响应
            if(response==null)   logError(tname,  file.getName()+" error-->response is null");//如果response为空
            int statusCode = response.getStatusLine().getStatusCode();//如果不为空，拿去响应码
            if (statusCode == 206 ) {//206 表示采用range下载。需要在调用该线程的实例中循环创建不同的startPosition与endPosition
                InputStream inputStream = response.getEntity().getContent();//从响应中获得内容创建输入流
                RandomAccessFile outputStream = new RandomAccessFile(file,"rw");//创建文件输出流
                outputStream.seek(startPosition);//移动到文件的指定输出起始位置
                int count = 0;
                while ((count = inputStream.read(buffer, 0, buffer.length)) > 0) {
                    outputStream.write(buffer, 0, count);
                    downloaded += count;//叠加已下载数
                    listener.afterPerDown(new DownloadThreadEvent(this,getDownloadProgress()));// 下载过程的监听
                }
                inputStream.close();
                outputStream.close();
            }else if(statusCode == 200 && !isRange){//不采用range下载
                HttpEntity entity = response.getEntity();
                InputStream in = entity.getContent();//获得response中的内容转化为输入流
                FileOutputStream out = new FileOutputStream(file);//创建文件输出流
                int count = 0;
                while((count=in.read(buffer))!= -1){
                    out.write(buffer,0,count);
                    downloaded += count;//叠加已下载数
                    listener.afterPerDown(new DownloadThreadEvent(this,getDownloadProgress()));// 下载过程的监听
                }
                in.close();
                out.close();
            }else{//返回不正常
                logError(tname, "error-->response code:" + statusCode);//
                return;
            }
        }catch (Exception e){
            logError(tname, file.getName()+" error-->downloading break:" + e.getMessage());//
        }finally {
            //无论是否出现异常，都调用downCompleted
//            httpGet.abort();
            if(isRange){//range下载的片段
                listener.downCompleted(new DownloadThreadEvent(this,
                        endPosition));//结束的文件位置标记
            }else{//整个文件下载
                listener.downCompleted(new DownloadThreadEvent(this,
                        formatFloat(downloaded / 1024)));//下载的文件的总大小
            }

        }
        logMsg(tname, "finished-->"+file.getName()+" downloaded:" + formatFloat(downloaded / 1024)+"Kb" );
    }

    /**
     * 返回远程下载文件的文件信息
     * @return  true-文件可以下载 false-文件不可以下载
     * 设置size为当前检查的文件大小 B为单位
     */
    private  Boolean readDownloadFileInfo() throws IOException, CloneNotSupportedException {
        HttpHead httpHead = new HttpHead(url);//创一个访问头
        HttpResponse response = client.executeHead(httpHead);//返回头访问信息
        int statusCode = response==null?-1:response.getStatusLine().getStatusCode(); // 获取HTTP状态码
        if (statusCode != 200) {//响应码不正常的时候
            return false;
        }
        // Content-Length
        Header[] headers = response.getHeaders("Content-Length");
        if (headers.length > 0)
        {	//获得要下载的文件的大小
            size = Long.valueOf(headers[0].getValue());
            httpHead.abort();//释放
            return true;//表示文件可以下载
        }else{
            size=-1;
            return false;//表示文件不可下载
        }
    }
    /**
     * @return the progree between 0 and 100;return -1 if download not started
     * 下载进度 百分比
     */
    public float getDownloadProgress() {
        float progress = 0;
        if (size == -1) {
            return -1;
        }
        synchronized (this) {
//            progress = (float) (downloaded *100.0 / size);
            progress =  formatFloat(downloaded /1024);
        }
        return formatFloat(progress);
    }

    /**
     * 格式化float类型 精确到小数点后面两位
     * @param f
     * @return
     */
    public float formatFloat(float f){
        return (float)(Math.round(f*100))/100;
    }
    /**
     * 记录提示信息
     * @param t
     * @param msg
     * @return
     */
    public void logMsg(String t,String msg){
        logger.info(t + "[" + msg + "]");
    }

    /**
     * 记录错误日志信息
     * @param t
     * @param msg
     */
    public void  logError(String t,String msg){
        logger.error(t + "[" + msg + "]");
    }
    /**
     * 返回文件大小
     * @return
     */
    public float getSize() {return size;}
    /**
     * 返回当前的自定义线程名字
     * @return
     */
    public String getTname() {return tname;}
    /**
     * 返回当前线程创建的本地文件的信息
     * @return
     */
    public File getFile() {return file;}
    /**
     * 如果该线程下载的是视频文件，返回该文件在数据库中的的时长
     * @return
     */
    public String getTime() {return time;}
    /**
     * 返回该文件在数据库中的文标题描述
     * @return
     */
    public String getTitle() {return title;}

    /**
     * 返回该下载文件在数据库中的唯一标示vkey
     * @return
     */
    public String getVkey() { return vkey;}
}


