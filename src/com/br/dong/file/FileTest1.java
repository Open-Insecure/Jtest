package com.br.dong.file;

import java.io.File;
import java.io.IOException;

public class FileTest1 {
 
	public  static void main(String []agrs) throws IOException{
		FileTest1 demo=new FileTest1();
		//在相对目录下创建文件
		 demo.createFile("2.dd");
		 //在指定目录下创建文件
//		 demo.createFile("d://test//test//1.txt");
		 //在相对目录下创建目录
		 demo.createDir("dir");
		 //在指定目录下创建目录
		 demo.createDirs("d://test//test");
	}
	//创建一个文件
	public void createFile(String fileName) throws IOException{
		File f = new File(fileName);
		f.createNewFile();
	}
	/**
	 * 创建此抽象路径名指定的目录
	 * 返回：
	 * 当且仅当已创建目录时，返回 true；否则返回 false
	 * 抛出 SecurityException 
	 * 创建根目录下面的文件夹 
	 *  File f = new File("c://test");
	 *  '/'要使用转义字符'//'来代替
	 * */
	public void createDir(String dirName) throws IOException{
		File f=new File(dirName);
		f.mkdir();
	}
	/**
	 * 创建一个根目录下的文件夹test
	 * */
	public void createDirs(String dirName) throws IOException{
		File f=new File(dirName);
		f.mkdirs();
	}
}
