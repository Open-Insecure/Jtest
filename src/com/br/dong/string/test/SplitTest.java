package com.br.dong.string.test;
/** 
 * @author  hexd
 * 创建时间：2014-8-19 上午9:35:54 
 * 类说明 
 */
public class SplitTest {
public static void main(String[] args) {
	String str="so.addParam('allowscriptaccess','always');so.addParam('allowfullscreen','true');so.addVariable('height','380');so.addVariable('width','465');so.addParam('wmode','transparent');so.addVariable('file','83954');so.addVariable('max_vid','84116');so.addVariable('type','lighttpd');so.addVariable('backcolor', '000000');so.addVariable(frontcolor, ffffff)so.addVariable('autostart','false');so.addVariable('seccode','a9355c53215e1f6a9296e5f083317b1a');so.addVariable('repeat', 'false');so.addVariable('showdigits','true');so.addVariable('location','1.swf');so.addVariable('enablejs','true');so.addVariable('mp4','0');";
	String [] arr=str.split(";");
	for(int i=0;i<arr.length;i++){
		if(arr[i].contains("file")){
			System.out.println(arr[i]);
			String temp=arr[i].replaceAll("(?:so.addVariable|\\(|file|,|'|\"|\\))", "");// 替换掉不相关的
			System.out.println(temp);
		}else if(arr[i].contains("max_vid")){
			System.out.println(arr[i]);
			String temp=arr[i].replaceAll("(?:so.addVariable|\\(|max_vid|,|'|\"|\\))", "");// 替换掉不相关的
			System.out.println(temp);
		}else if(arr[i].contains("seccode")){
			System.out.println(arr[i]);
			String temp=arr[i].replaceAll("(?:so.addVariable|\\(|seccode|,|'|\"|\\))", "");// 替换掉不相关的
			System.out.println(temp);
		}else if(arr[i].contains("mp4")){
			System.out.println(arr[i]);
			String temp=arr[i].replaceAll("(?:so.addVariable|\\(|mp4|,|'|\"|\\))", "");// 替换掉不相关的
			System.out.println(temp);
		}
	}
}
}
