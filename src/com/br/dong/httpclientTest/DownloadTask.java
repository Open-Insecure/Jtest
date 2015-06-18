package com.br.dong.httpclientTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;

import sun.rmi.runtime.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-8-20
 * Time: 下午4:56
 *
 * 实现一个多线程分段range下载一个文件
 *
 */
public class DownloadTask {
	public static void main(String[] args) {
	    String url="http://vip.youb66.com:81/media/you22/flv/8486.flv";
//	    String url="http://216.157.102.148/~yxgypic1/2014/2014-8/2014-8-17/65.jpg";
		String saveFile="E:\\video\\aa.flv";
		String type="http";
		String hosturl="vip.youb66.com:81";
//		String hosturl="91.v4p.co";//新地址！！！
		String refUrl="http://www.youbbb.net";
//		String refUrl="http://91.v4p.co/index.php";
		DownloadTask downloadTask= new DownloadTask(url, saveFile, 10,type,hosturl,refUrl);//10个线程
        try {
			//添加一个匿名内部类的实现
            downloadTask.addDownloadTaskListener(new DownloadTaskListener() {
                //实现接口
            	@Override
                public void downloadCompleted() {//下载完成
                    // TODO Auto-generated method stub
                    System.out.print("download completed");
                }
            });
            //开始下载
            CrawlerUtil client = new CrawlerUtil();
            downloadTask.startDown(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	long contentLength;//下载的资源大小
	String url;//下载链接
	boolean acceptRanges;//是否支持range断点续传
	int threadCount;//线程数
	String localPath;//存放的本地路径
	List<Thread> threads;//线程池list
	long receivedCount;//接收到文件的大小
	DownloadTaskListener listener;//下载任务监听器

	String type;//创建CrawlerUtil实例client的类型。hhtp或者https
	String hosturl;//
	String refUrl;//

	/**构造方法
	 * @param url 下载链接
	 * @param localPath 存放路径
	 * @param threadCount 线程数
	 */
	public DownloadTask(String url, String localPath, int threadCount,String type,String hosturl,String refUrl) {
		contentLength = -1;
		this.url = url;
		acceptRanges = false;
		this.threadCount = threadCount;
		this.localPath = localPath;
		threads = new ArrayList<Thread>();
		receivedCount = 0;
		this.type=type;
		this.hosturl=hosturl;
		this.refUrl=refUrl;
	}
	/**添加监听
	 * @param listener
	 */
	public  void addDownloadTaskListener(DownloadTaskListener listener) {
		this.listener = listener;
	}

	/**开始下载 使用CrawlerUtil 创建HttpClient实例
	 * @throws Exception
	 */
	public void  startDown(CrawlerUtil client) throws Exception {
//		CrawlerUtil client = new CrawlerUtil();
		//创建http请求的client
	     client.clientCreate(type,hosturl,refUrl);
		try {
			//判断下载链接是否有效
			if(getDownloadFileInfo(client)){
				//开始下载线程
				startDownloadThread(type,hosturl,refUrl);
			}
		} catch (Exception e) {
		} finally {
			client.closeClient();
		}
	}

	public static boolean getDebug() {
		return false;
	}

	/**
	 * @return the progree between 0 and 100;return -1 if download not started
	 * 下载进度
	 */
	public float getDownloadProgress() {
		float progress = 0;
		if (contentLength == -1) {
			return -1;
		}
		synchronized (this) {
			progress = (float) (DownloadTask.this.receivedCount * 100.0 / contentLength);
		}
		return progress;
	}

	/**
	 * 返回文件总大小
	 * @return
	 */
	public long getContentLength() {
		return contentLength;
	}

	/**
	 * 返回已经下载的文件大小
	 * @return
	 */
	public long getDownload() {
		long download;
		synchronized (this) {
			download = DownloadTask.this.receivedCount;
		}
		return download;
	}

	/**
	 * 获取下载文件信息
	 */
	private Boolean getDownloadFileInfo(CrawlerUtil client) throws IOException,
			ClientProtocolException, Exception {
		HttpHead httpHead = new HttpHead(url);  //创一个访问头
		HttpResponse response = client.executeHead(httpHead);
		// 获取HTTP状态码
		int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode != 200){
//			throw new Exception("资源不存在!");
			System.out.println("资源不存在："+url);
			return false;
		}
		
		if (getDebug()) {
			for (Header header : response.getAllHeaders()) {
				System.out.println(header.getName() + ":" + header.getValue());
			}
		}

		// Content-Length
		Header[] headers = response.getHeaders("Content-Length");
		if (headers.length > 0)

		{	//获得要下载的文件的大小
			contentLength = Long.valueOf(headers[0].getValue());
		}

		if (contentLength < 1024 * 100) {
			threadCount = 1;
		} else if (contentLength < 1024 * 1024) {
			threadCount = 2;
		} else if (contentLength < 1024 * 1024 * 10) {
			threadCount = 5;
		} else if (contentLength < 1024 * 1024 * 100) {
			threadCount = 10;
		} else {
			threadCount = 20;
		}
		httpHead.abort();
		httpHead = new HttpHead(url);
		httpHead.addHeader("Range", "bytes=0-" + (contentLength - 1));
		response = client.executeHead(httpHead);
		//检测是否支持range断点下载
		if (response.getStatusLine().getStatusCode() == 206) {
			acceptRanges = true;
			System.out.println("支持 range方式下载，目标文件大小："+contentLength+" 启用"+threadCount+"个线程下载");
		} else {
			acceptRanges = false;
			System.out.println("不支持range方式下载，目标文件大小："+contentLength);
		}
		httpHead.abort();
		return acceptRanges;
//		return true;
	}

	/**
	 * 启动多个下载线程
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	// DownloadThreadListener listener;
	private void startDownloadThread(String type,String hosturl,String refUrl) throws IOException,
			FileNotFoundException {
		// 创建下载文件
		final File file = new File(localPath);
		file.createNewFile();
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		raf.setLength(contentLength);//分配文件空间
		raf.close();

		// 定义下载线程事件实现类
		final Calendar time = Calendar.getInstance();
		final long startMili = System.currentTimeMillis();// 当前时间对应的毫秒数
		//下载线程监听器 构造方法
		final DownloadThreadListener threadListener = new DownloadThreadListener() {
			
			public void afterPerDown(DownloadThreadEvent event) {
				//线程同步更改此下载监听器监听事件下载的文件大小
				synchronized (this) {
					DownloadTask.this.receivedCount += event.getCount();
				}
			}

			/*  
			 * 当该监听到线程下载完成后
			 */
			public void downCompleted(DownloadThreadEvent event) {
				// 下载线程执行完毕后从主任务中移除该线程
				threads.remove(event.getTarget());
				//如果线程池全部移除
				if (threads.size() == 0) {
					long endMili = System.currentTimeMillis();
					Calendar time1 = Calendar.getInstance();
					System.out.println(file + "总耗时为：" + (endMili - startMili)
							+ "毫秒");
					//调用下载全部完成
					listener.downloadCompleted();
				}
				if (getDebug()) {
					System.out.println("剩余线程数：" + threads.size());
				}
			}
		};

		// --不支持多线程下载时
		if (!acceptRanges) {
			System.out.println("该地址不支持多线程下载");
			// 定义普通下载
			DownloadThread thread = new DownloadThread(url, 0, contentLength,
					file, false,type,hosturl,refUrl);
			//每个单独的线程都添加监听
			thread.addDownloadListener(threadListener);
			thread.start();
			threads.add(thread);
			return;//返回到DownloadThread 进行下载
		}
		//--支持多线程下载
		// 每个请求要下载的文件的大小
		long perThreadLength = contentLength / threadCount + 1;
		long startPosition = 0;//单线程起始下载标记
		long endPosition = perThreadLength;//单线程下载的结束标记
		// 循环创建多个下载线程
		do {
			if (endPosition >= contentLength)
				endPosition = contentLength - 1;
			//多线程下载
			DownloadThread thread = new DownloadThread(url, startPosition,
					endPosition, file,type,hosturl,refUrl);
			//添加监听
			thread.addDownloadListener(threadListener);
			thread.start();
			threads.add(thread);
			//每次循环创建完一个线程后，进入下一次循环的时候 startPosition为上一个线程的endPosition+1的位置开始下载
			startPosition = endPosition + 1;// 此处加 1,从结束位置的下一个地方开始请求
			endPosition += perThreadLength;
		} while (startPosition < contentLength);//当startPosition大于contentLength 则停止创建下载线程
	}

	/**具体的对监听的事件类 继承EventListener
	 * @author Dong
	 *
	 */
	interface DownloadThreadListener extends EventListener {
		
		public void afterPerDown(DownloadThreadEvent event);
		public void downCompleted(DownloadThreadEvent event);

	}

	/**事件状态对象类
	 * @author Dong
	 *
	 */
	class DownloadThreadEvent extends EventObject {

		/** 
         *  
         */
		private static final long serialVersionUID = 1L;
		Object sourObject;//被监听的事件源
		long count;

		public DownloadThreadEvent(Object sourceObject, long count) {
			super(sourceObject);
			this.sourObject = sourceObject;
			this.count = count;
		}

		long getCount() {
			return count;
		}

		Object getTarget() {
			return sourObject;
		}
	}

	/**多线程下载类
	 * @author Dong
	 */
	class DownloadThread extends Thread {

		String url;//下载链接
		long startPosition;//开始标记
		long endPosition;//结束标记
		boolean isRange;//是否支持range
		File file;//下载到本地文件
		DownloadThreadListener listener;
		long downloaded;//已经下载大小
		
		String type;//创建CrawlerUtil实例client的类型。hhtp或者https
		String hosturl;//
		String refUrl;//
		/**添加下载监听
		 * @param listener
		 */
		void addDownloadListener(DownloadThreadListener listener) {
			this.listener = listener;
		}

		public long getdownLoaded() {
			return this.downloaded;
		}

		/**支持多线程下载的下载线程构造方法
		 */
		DownloadThread(String url, long startPosition, long endPosition,
				File file,String type,String hosturl,String refUrl) {
			this.url = url;
			this.startPosition = startPosition;
			this.endPosition = endPosition;
			this.isRange = true;
			this.file = file;
			this.downloaded = 0;
			this.type=type;
			this.hosturl=hosturl;
			this.refUrl=refUrl;
		}

		/**不支持多线程下载的下载线程构造方法
		 */
		DownloadThread(String url, long startPosition, long endPosition,
				File file, boolean isRange,String type,String hosturl,String refUrl) {
			this.url = url;
			this.startPosition = startPosition;
			this.endPosition = endPosition;
			this.isRange = isRange;
			this.file = file;
			this.downloaded = 0;
			this.type=type;
			this.hosturl=hosturl;
			this.refUrl=refUrl;
		}
		//重写run方法
		public void run() {

			// System.out.println("Start:" + startPosition + "-" + endPosition);
			CrawlerUtil client = new CrawlerUtil();
			//创建http请求的client
			try {
				client.clientCreate(type,hosturl , refUrl);
			} catch (KeyManagementException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				HttpGet httpGet = new HttpGet(url);
				if (isRange) {// 设置get请求头 每次需要下载的range参数
					httpGet.addHeader("Range", "bytes=" + startPosition + "-"
							+ endPosition);
				}
//				System.out.println("当前线程,startPosition"+startPosition+"endPosition"+endPosition);
				HttpResponse response = client.executeGetMethod(httpGet);
				//响应的状态码
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 206 || (statusCode == 200 && !isRange)) {
					java.io.InputStream inputStream = response.getEntity()
							.getContent();
					RandomAccessFile outputStream = new RandomAccessFile(file,
							"rw");
					outputStream.seek(startPosition);
					int count = 0;
					byte[] buffer = new byte[10240];
					while ((count = inputStream.read(buffer, 0, buffer.length)) > 0) {
						outputStream.write(buffer, 0, count);
						downloaded += count;
						listener.afterPerDown(new DownloadThreadEvent(this,
								count));
					}
					outputStream.close();
				}
				httpGet.abort();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				listener.downCompleted(new DownloadThreadEvent(this,
						endPosition));
				if (DownloadTask.getDebug()) {
					System.out.println("End:" + startPosition + "-"
							+ endPosition);
				}
				client.closeClient();
			}
		}
	}
}
