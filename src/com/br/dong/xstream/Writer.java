package com.br.dong.xstream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 * Writer 类使用 XStream API 把 Employee 类型的对象序列化为 XML 并存储到文件中
 * */
public class Writer {
	public static void main(String[] args) {
		 Employee e = new Employee();

		 //Set the properties using the setter methods
		 //Note: This can also be done with a constructor.
		 //Since we want to show that XStream can serialize
		 //even without a constructor, this approach is used.
		 e.setName("Jack");
		 e.setDesignation("Manager");
		 e.setDepartment("Finance");

		 //Serialize the object
		 XStream xs = new XStream();

		 //Write to a file in the file system
		 try {
		 FileOutputStream fs = new FileOutputStream("f:\\employeedata.txt");
		 xs.toXML(e, fs);
		 } catch (FileNotFoundException e1) {
		 e1.printStackTrace();
		 }
		 }

}
