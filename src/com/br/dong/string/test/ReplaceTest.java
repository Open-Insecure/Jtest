package com.br.dong.string.test;
/** 
 * @author  hexd
 * 创建时间：2014-8-18 下午5:21:11 
 * 类说明 
 */
public class ReplaceTest {
	public static void main(String[] args) throws Exception {  
	    String src = "南京市玄武区北京东路徐州市鼓楼区戏马台";   
	    src = src.replaceAll("(?:江苏省|玄武区|鼓楼区)", "");  
	    System.out.println(src);

        String src2="(?:<>/\\:\"\"*?";
        src2=src2.replaceAll("(?:/|<|>|:|\\?|\\||\\*|\"|\\(|\\)|)","");
        System.out.println(src2);

		String ss="2015-11-11 13:00-18:00";
		System.out.println(ss.replace(":",""));
		System.out.println("2015-11-11".length()+ss.substring(0,13)+"点");
	  }  
}
