package com.br.dong.math;
/** 
 * @author  hexd
 * 创建时间：2014-6-30 下午6:16:35 
 * 类说明 
 */
public class DoubleTest {
	public static void main(String[] args) {
		  double a=35;
		  double b=20;
		  double c = a/b;
		  System.out.println("c===>"+c);   //1.75
		  System.out.println("c===>"+Math.ceil(c)); //2.0
		  System.out.println(Math.floor(c));  //1.0
	}
}
