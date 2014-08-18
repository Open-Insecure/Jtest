package com.br.dong.StringTonumber;

public class StringToNumber {
	public static void main(String[] args) {
		String fee1="123.456";
		String fee2="222";
		Double to=Double.valueOf(fee1)+Double.valueOf(fee2);
		String t=to.toString();
		System.out.println(to);  
		System.out.println(t);  
	}
}
