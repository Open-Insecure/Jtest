package com.br.dong.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * @author  hexd
 * 创建时间：2014-7-4 下午4:00:59 
 * 类说明 
 */
public class Test1 {
	public static void main(String[] args) {//1404 461019491
		//long型 后面要加L
		long t=1426693316481L;
		Date now=new Date();
		Date date=new Date(t);
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(df.format(date).toString());
		System.out.println(now.getTime());
		
	}
}
