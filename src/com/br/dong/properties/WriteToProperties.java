package com.br.dong.properties;

/** 
 * @author  hexd
 * 创建时间：2014-6-3 下午2:03:03 
 * 类说明 
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WriteToProperties {
	public void addUser(String name, String password) {
		File f = new File(".");
		String absolutePath = f.getAbsolutePath();
		System.out.println(absolutePath);
		FileInputStream fis = null;
		Properties pro = new Properties();
		/*
		 * 用程序对properties做修改,先将properties加载到内存中
		 */
		try {
			//注意路径问题
			fis = new FileInputStream("user.properties");// 初始化输入流
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			pro.load(fis); // 加载
		} catch (IOException e) {
			e.printStackTrace();
		}
		pro.setProperty(name, password); // 修改properties
		/*
		 * 将改动后的properties写回硬盘
		 */
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("user.properties"); // 初始化一个输出流
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			pro.store(fos, "#"); // 写回硬盘
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fis.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		WriteToProperties wtp = new WriteToProperties();
		wtp.addUser("lucy", "aaaaa");
		wtp.addUser("lily", "aaaaa");
		wtp.addUser("cc", "aaaaa");
	}
}