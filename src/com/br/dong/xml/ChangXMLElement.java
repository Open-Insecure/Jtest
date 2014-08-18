package com.br.dong.xml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 如果有些时候需要获取xml中所有的文本信息，又或者别人传递的xml格式不规范，比如标签内名称大小写，虽然xml不区分大小写，但是必须成对出现，所以为了避免这种情况，索性可以将全部的标签名称换为大写，具体代码如下：
 * 改变xml标签的大小写问题
 * */
public class ChangXMLElement {
	public static void main(String[] args) {  
	    String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><modules id=\"123\"><module> 这个是module标签的文本信息<name>oa</name><value>系统基本配置</value><descript>对系统的基本配置根目录</descript></module></modules>";  
	    System.out.println(str.replaceAll("<[^<]*>", "_"));   
	    Pattern pattern = Pattern.compile("<[^<]*>");  
	    Matcher matcher = pattern.matcher(str);  
	    while(matcher.find()){  
	        str = str.replaceAll(matcher.group(0), matcher.group(0).toUpperCase());  
	    }  
	    System.out.println(str);  
	       
	}  
}
