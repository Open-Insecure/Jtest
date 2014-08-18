package com.br.dong.string.test;

public class test2 {
public static void main(String[] args) {
	String str="A|A.10万元（含）以下#B|B.10-30万元（含）#C|C.30-100万元（含）#D|D.100万元以上";
	String []s=str.split("#");
	for(int i=0;i<s.length;i++){
		System.out.println(s[i].substring(2));
	}
}
}
