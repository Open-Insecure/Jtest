package com.br.dong.utils;

import java.util.Date;

public class Test {
	public static void main(String []args){
 
		System.out.print(CommUtil.getDate());
		System.out.print(FormatUtils.formatDateForFileName(new Date()));
		System.out.print(""+IdCardUtils.getAgeByIdCard("36252219890818001X"));
	}
}
