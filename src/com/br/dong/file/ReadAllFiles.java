package com.br.dong.file;
import java.io.*;
public class ReadAllFiles {
	public static void main(String[] args){
//		File f = new File("d:/test");
		File f = new File("d://test");
		System.out.println(f.getAbsolutePath());
		String path="d://test";
		
		tree(f);
	}
	//显示目录的方法
	public static void tree(File f){
		//判断传入对象是否为一个文件夹对象
		if(!f.isDirectory()){
			System.out.println("你输入的不是一个文件夹，请检查路径是否有误！！");
		}
		else{
			File[] t = f.listFiles();
			for(int i=0;i<t.length;i++){
				//判断文件列表中的对象是否为文件夹对象，如果是则执行tree递归，直到把此文件夹中所有文件输出为止
				if(t[i].isDirectory()){
					System.out.println(t[i].getName()+"目录");
					tree(t[i]);
				}
				else{
					System.out.println(t[i].getName()+"文件");
					File readfile = new File("d://test" + "\\" + t[i]); 
					System.out.println(t[i].getAbsolutePath());
					String absolutepath = readfile.getAbsolutePath();//文件的绝对路径  
                    String filename = readfile.getName();//读到的文件名
                    System.out.println("文件路径"+absolutepath);
				}
			}
		}

	}
}



 