package com.br.dong.string.test;

public class test {
	public static void main(String[] args) {
		String a="hehe";
		String b=a;
		String ss="@abc哈哈";
		String sb="ssssssssabc哈哈";
		System.out.println(processMessage(ss));
		System.out.println(processMessage(sb));
		
	}
	
	public static String processMessage(String content){
		if(content.startsWith("@")){
			//获得用户留言内容
			String sb=content.substring(1);
			//以下进行其他操作
			return sb;
		}
		return "";
	}
}
