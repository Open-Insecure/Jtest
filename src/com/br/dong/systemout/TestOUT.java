package com.br.dong.systemout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/** 
 * @author  hexd
 * 创建时间：2014-6-3 上午11:08:24 
 * 类说明 
 */
public class TestOUT {
	public static void main(String[] args) {
		try {
			//设置打印流
	        PrintStream out = new PrintStream(new FileOutputStream("out.log"));
	       
	        PrintStream tee = new TeeStream(System.out, out);
	    
	        System.setOut(tee);
	    
	        PrintStream err = new PrintStream(new FileOutputStream("err.log"));
	        tee = new TeeStream(System.err, err);
	    
	        System.setErr(tee);
	    } catch (FileNotFoundException e) {
	    }
	    
	    System.out.println("welcome");
	    System.err.println("error");
	}
}
