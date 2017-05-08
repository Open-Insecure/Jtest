package com.br.dong.jreSimplify;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.br.dong.properties.FileOperate;

/** 
 * @author  hexd
 * 创建时间：2014-8-5 下午2:45:53 
 * 类说明
 * 切换到jdk的执行bin目录下
cd D:\Program Files\Java6\jdk1.6.0_39\bin\
执行 java -jar  -classpath lib/*.jar; -verbose:class 91pSoft.jar >> class.txt
pause
 1
 */
public class Jres {
	public static void main(String[] args) throws CloneNotSupportedException {
		FileOperate fo=new FileOperate();
		readMainTxt("D:\\jvm_simply\\精简jre测试三\\classes.txt");
	}
	//读取文件
	public static void readMainTxt(String filename) throws CloneNotSupportedException{
//		System.out.println("read.."+filename);
		BufferedReader in=null;
		try{
			 in=new BufferedReader(new FileReader(filename));
			String line;
			try {
				while((line=in.readLine())!=null){
//					System.out.println(""+line);
					//url起始
//					if(line.contains("Loaded ")&&line.contains("rt.jar")){
						if(line.contains("Loaded ")){
//						System.out.println(line);
						int start=line.indexOf("Loaded ");
						int end=line.indexOf(" from");
						System.out.println(line.substring(start+7,end).replace(".", "/"));
					}
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		} 
	}
	
	
}
