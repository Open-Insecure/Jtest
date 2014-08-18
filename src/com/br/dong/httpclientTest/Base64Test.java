package com.br.dong.httpclientTest;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class Base64Test {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String a="information_jjh_jjxy_jjzs";
		byte[] b=a.getBytes("UTF8");
		System.out.println(b[0]);
		
	}
}
