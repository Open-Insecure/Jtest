package com.br.dong.xstream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Reader 类读取该文件，逆序列化 XML 并把数据装入 Java 对象
 * */
public class Reader {
	public static void main(String[] args) {
		 XStream xs = new XStream(new DomDriver());
		 Employee e = new Employee();

		 try {
		 FileInputStream fis = new FileInputStream("f:\\employeedata.txt");
		 xs.fromXML(fis, e);

		 //print the data from the object that has been read
		 System.out.println(e.toString());

		 } catch (FileNotFoundException ex) {
		 ex.printStackTrace();
		 }

		 }
}
