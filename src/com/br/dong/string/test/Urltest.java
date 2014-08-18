package com.br.dong.string.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Urltest {
public static void main(String[] args) {
	String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]" ;
	Pattern patt = Pattern.compile(regex );
	Matcher matcher = patt.matcher("111.com");
	boolean isMatch = matcher.matches();
	if (!isMatch) {
	     System.out.println( "您输入的URL地址不正确" );
	} else {
		  System.out.println( "您输入的URL地址正确" );
	}
}
}
