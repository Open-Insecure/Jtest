package com.br.dong.iostream;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/** 
 * 逐行读取文件的每个字符
 * */
public class BufferredReaderTest {
	public static void main(String[] args) throws IOException {
		BufferedReader in=null;
		try{
			 in=new BufferedReader(new FileReader("D:/fuck2.xml"));
			String line;
			try {
				while((line=in.readLine())!=null){
					System.out.println(""+line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}finally{
			in.close();
		}
	}
}
