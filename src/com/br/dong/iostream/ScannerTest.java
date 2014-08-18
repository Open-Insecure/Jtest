package com.br.dong.iostream;

import java.util.Scanner;

/**
 * 测试输入输出
 * */
public class ScannerTest {

	public static void main(String[] args) {
		//从控制台输入，构造一个Scanner对象。依附于标准输出流
		Scanner in=new Scanner(System.in);
		System.out.println("读取你输入的下一行");
		String name=in.nextLine();
		System.out.println("你输入的一行"+name);
		System.out.println("读取输入的一个词");
		String one=in.next();
		System.out.println("你输入的一个词"+one);
	}
}
