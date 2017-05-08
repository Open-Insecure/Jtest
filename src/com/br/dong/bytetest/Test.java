package com.br.dong.bytetest;


/***
 *aaa1122
 */
public class Test {
	public static void main(String[] args) {
		String s = "fs123fdsa";//String变量 

		byte b[] = s.getBytes();//String转换为byte[] 

		String t = new String(b);//bytep[]转换为String
		
		System.out.println("t"+t);
		for (int i=0;i<b.length;i++){
			System.out.println("b["+i+"]"+b[i]);
		}
	}
}
