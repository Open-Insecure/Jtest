package com.br.dong.map_stringBuff;

import java.util.ArrayList;
import java.util.List;

public class StringBufferTest1 {
	public static void main(String[] args) {
		List<String> list=new ArrayList<String>();
		list.add("XXX1");
		list.add("XXX2");
		list.add("XXX3");
		list.add("XXX4");
		list.add("XXX5");
		list.add("XXX6");
		list.add("XXX7");
		list.add("XXX8");
		list.add("XXX9");
		list.add("XX10");
		//最后需要的格式 0,XGSS,W|0,TTLDQ,W 格式的字符串
		StringBuffer sb=new StringBuffer("0,");
		for(int i=0;i<list.size();i++){
			if(i<(list.size()-1)){
				sb.append(list.get(i)+",W|0,");
			}else{
				sb.append(list.get(i)+",W");
			}
			System.out.println(sb.toString());
		}
		System.out.println("最后："+sb.toString());
	}
}
