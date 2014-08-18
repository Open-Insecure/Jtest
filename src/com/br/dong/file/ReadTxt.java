package com.br.dong.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ReadTxt {
	//要扫描的目录
	public static String path="d://test";
	//输出的文件 如果创建失败  
	public static String filename="d://pp.txt";
	//存放名字的set
	public Set<String> set=new HashSet<String>();
	public static void main(String[] args) throws UnsupportedEncodingException {
		ReadTxt fucker=new ReadTxt();
		//读取配置文件 获得目录路径
		//读取目录下文件
		fucker.readAll(path);
		//新建文件
		fucker.newFile(filename, "--*--");
	}
	
	//读取目录下的所有文件
		public void readAll(String path) throws UnsupportedEncodingException{
			File f = new File(path);
			tree(f);
		}
		//循环读取目录
		public  void tree(File f) throws UnsupportedEncodingException{
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
						
						appendMethodB(filename,"文件"+t[i].getName()+"包含的内容");
						//获得文件的路径
						String abs=t[i].getAbsolutePath();
						System.out.println(abs);
						readLine(abs);
				
					}
				}
			}

		}
		//读取文件的每一行字符
		public void readLine(String filePath) throws UnsupportedEncodingException{
			BufferedReader in=null;
			System.out.println("aaaaa");
			try{
			InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "UTF-8"); 
			in = new BufferedReader(isr);
			String line;
				System.out.println("bbbb");
				try {
					while((line=in.readLine())!=null){
						System.out.println("aaa"+line);
						//分割读到的line
						String [] l=line.split("、");
						System.out.println("l:---"+l.length);
						//循环获得分割的名字	
						for(int i=0;i<l.length;i++){
							set.add(l[i]);
							
//							appendMethodB(filename,l[i]);
						}
					}
					Iterator<String> it=set.iterator();
					while(it.hasNext()){
						appendMethodB(filename,it.next());
					}
					appendMethodB(filename,"总数:"+set.size());
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
		      
//		      FileWriter resultFile = new FileWriter(myFilePath);  
//		      PrintWriter myFile = new PrintWriter(resultFile);  
//		      String strContent = fileContent;  
//		      myFile.println(strContent);  
//		      resultFile.close();  
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
		            writer.write(content+"、");//\r\n
		            writer.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
}
