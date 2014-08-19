package com.br.dong.httpclientTest.porn;

import java.util.concurrent.ExecutorService;

import com.br.dong.httpclientTest.CrawlerUtil;

/** 
 * @author  hexd
 * 创建时间：2014-8-19 上午11:10:35 
 * 类说明 
 * 从http://www.xici.net.co/ 获得代理列表插入到数据库中
 * 暂时只拿去http类型的代理
 */
public class ProxyGet {
	// 线程池
	private ExecutorService exe = null;
	// 线程池的容量
	private static final int POOL_SIZE = 20;
	
	private static CrawlerUtil client=new CrawlerUtil();
}
