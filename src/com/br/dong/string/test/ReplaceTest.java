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
	  }  
}
