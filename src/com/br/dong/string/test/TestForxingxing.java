package com.br.dong.string.test;

import com.br.dong.utils.ValidateUtils;

/** 
 * @author  hexd
 * 创建时间：2014-5-30 下午5:51:05 
 * 类说明 
 * 根据用户名长度 替换其中几个位置为*
 */
public class TestForxingxing {
public static void main(String[] args) {
	String name1="dong7253997";
	String name2="837484691@qq.com";
	String name3="13011036936";
	String name4="aaaa";
	System.out.println(tr(name1));
	System.out.println(tr(name2));
	System.out.println(tr(name3));
	System.out.println(tr(name4));
}
	public static String tr(String n){
		String result="";
		//手机
		if(ValidateUtils.Mobile(n)){
			result=n.substring(0,3)+"****"+n.substring(7,11);
		}//邮箱
		else if(ValidateUtils.Email(n)){
			//
			String headd=n.substring(0,n.indexOf("@"));
			result=n.substring(0,n.indexOf("@"));
			String bottom=n.substring(n.indexOf("@"),n.length());
			int head=headd.length();
			result=headd.substring(0,head/2)+"****"+bottom;
		}//其他
		else{
			result=n.substring(0,n.length()/2)+"****";
		}
		return result;
	}
}
