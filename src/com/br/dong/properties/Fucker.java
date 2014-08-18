package com.br.dong.properties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * 读取指定目录下所有的文件 包含关键字符的一行输出到指定的一个新建文件中
 * */
public class Fucker {
	//要扫描的目录
	public static String path="d://test";
	//输出的文件 如果创建失败  
	public static String filename="d://pp.txt";
	
	public static void main(String[] args) {
		Fucker fucker=new Fucker();
		try {
			//读取配置文件 获得目录路径
			fucker.readConfig();
			//读取目录下文件
			fucker.readAll(path);
			//新建文件
    		fucker.newFile(filename, "--*--");
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
/**获取此类所在的路径		
 * File fileB = new File(fucker.getClass().getResource("").getPath());
 * */
	}
	//读取配置文件
	public void readConfig() throws IOException{
		//创建配置文件实例
		Properties prop=new Properties();
		System.out.println("读取配置文件");
		//注意this的问题
//        prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
//        System.out.println("要扫描的目录:"+ prop.getProperty("path"));
//        System.out.println("要输出的文件:"+prop.getProperty("filename"));
        //赋值
//        path=prop.getProperty("path");
//        filename=prop.getProperty("filename");
        
	}
	//读取目录下的所有文件
	public void readAll(String path){
		File f = new File(path);
		tree(f);
	}
	//循环读取目录
	public  void tree(File f){
		//判断传入对象是否为一个文件夹对象
		if(!f.isDirectory()){
			System.out.println("你输入的不是一个文件夹，请检查路径是否有误！！");
		}
		else{
			//获得文件列表
			File[] t = f.listFiles();
			for(int i=0;i<t.length;i++){
				//判断文件列表中的对象是否为文件夹对象，如果是则执行tree递归，直到把此文件夹中所有文件输出为止
				if(t[i].isDirectory()){
					System.out.println("目录"+t[i].getName());
					tree(t[i]);
				}
				else{
					System.out.println("正在读取文件"+t[i].getName());
					//将文件标题写入
					appendMethodB(filename,"文件"+t[i].getName()+"包含的内容");
					//获得文件的路径
					String abs=t[i].getAbsolutePath();
					//System.out.println(abs);
					readLine(abs);
				}
			}
		}

	}
	//读取文件的每一行字符
	public void readLine(String filePath){
		BufferedReader in=null;
		try{
			 in=new BufferedReader(new FileReader(filePath));
			String line;
			try {
				while((line=in.readLine())!=null){
					System.out.println(""+line);
					//在此判断是否包含sw
					if(line.contains("sw")){
						//写入到一个txt文件中
						appendMethodB(filename,line);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	 /** 
	   * 新建文件 
	    filePathAndName String 文件路径及名称 如c:/fqf.txt 
	    fileContent String 文件内容 
	   * 
	   */  
	  public void newFile(String filePathAndName, String fileContent) {  
	    try {  
	      String filePath = filePathAndName;  
	      filePath = filePath.toString();  
	      File myFilePath = new File(filePath);  
	      if (!myFilePath.exists()) {  
	        myFilePath.createNewFile();  
	      }  
	      
//	      FileWriter resultFile = new FileWriter(myFilePath);  
//	      PrintWriter myFile = new PrintWriter(resultFile);  
//	      String strContent = fileContent;  
//	      myFile.println(strContent);  
//	      resultFile.close();  
	    }  
	    catch (Exception e) {  
	      System.out.println("新建目录操作出错");  
	      e.printStackTrace();  
	    }  
	  }  
	   /**
	     * 追加文件：使用FileWriter
	     * fileName即为文件绝对路径 d://pp.txt
	     */
	    public static void appendMethodB(String fileName, String content) {
	        try {
	            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
	            FileWriter writer = new FileWriter(fileName, true);
	            //加入\r\n 换行
	            writer.write(content+"\r\n");
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
