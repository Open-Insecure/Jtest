//package com.br.dong.xstream;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//
//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.io.xml.DomDriver;
//
//public class ConfigReader {
//	 String datasourcename = null;
//	 String ipaddress = null;
//	 String logfilename = null;
//	 String appender = null;
//
//	 @Override
//	 public String toString() {
//	 // This method prints out the values stored in the member variables
//	 return "Datasource Name : "+datasourcename+
//	 " \nIP Address : "+ipaddress+
//	 " \nLogfilename : "+logfilename+
//	 " \nAppender : "+appender;
//	 }
//
//	 /**
//	 * @param args
//	 * @throws FileNotFoundException
//	 */
//	 public static void main(String[] args) throws FileNotFoundException {
//	 XStream xs = new XStream(new DomDriver());
//	 InputStream inputStream = new ConfigReader().getClass().getResourceAsStream("Config.xml");//也可以根据类的编译文件相对路径去找xml
//	 System.out.println("inputStream"+inputStream);
//	 //指定xml中datasource-name对应ConfigReader.class这个.class文件的datasourcename类属性
//	 xs.aliasField("datasource-name", ConfigReader.class, "datasourcename");
//	 //指定xml的config根标签对应ConfigReader.class类
//	 xs.alias("config", ConfigReader.class);
//	 ConfigReader r = (ConfigReader)xs.fromXML(inputStream);
//
//	 System.out.println(r.toString());
//	 }
//}
