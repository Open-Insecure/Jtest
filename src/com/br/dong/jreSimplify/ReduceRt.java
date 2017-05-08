package com.br.dong.jreSimplify;

/** 
 * @author  hexd
 * 创建时间：2014-8-5 下午3:02:38 
 * 类说明
 * 2
 */
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.LineNumberReader;  

import com.br.dong.file.FileOperate;
  
public class ReduceRt {  
    // 文件拷贝  
    public static boolean copy(String file1, String file2) {  
        try // must try and catch,otherwide will compile error  
        {  
            // instance the File as file_in and file_out  
            java.io.File file_in = new java.io.File(file1);  
            java.io.File file_out = new java.io.File(file2);  
            FileInputStream in1 = new FileInputStream(file_in);  
            FileOutputStream out1 = new FileOutputStream(file_out);  
            byte[] bytes = new byte[1024];  
            int c;  
            while ((c = in1.read(bytes)) != -1)  
                out1.write(bytes, 0, c);  
            in1.close();  
            out1.close();  
            return (true); // if success then return true  
        } catch (Exception e) {  
            System.out.println("Error!");  
            return (false); // if fail then return false  
        }  
    }  
  
    // 读取路径,copy  
    public static int dealClass(String needfile, String sdir, String odir) throws IOException {  
        int sn = 0; // 成功个数  
  
        File usedclass = new File(needfile);  
        if (usedclass.canRead()) {  
            String line = null;  
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(usedclass),  
                    "UTF-8"));  
            while ((line = reader.readLine()) != null) {  
                line = line.trim();  
                int dirpos = line.lastIndexOf("/");  
                if (dirpos > 0) {  
                    String dir = odir + line.substring(0, dirpos);  
                    File fdir = new File(dir);  
                    if (!fdir.exists())  
                        fdir.mkdirs();  
                    String sf = sdir + line + ".class";  
                    String of = odir + line + ".class";  
                    boolean copy_ok = copy(sf.trim(), of.trim());  
                    if (copy_ok)  
                        sn++;  
                    else {  
                        System.out.println(line);  
                    }  
  
                }  
  
            }  
        }  
        return sn;  
  
    }  
    public void preClass(){
    	FileOperate fo=new FileOperate();
    }
    
    public static void main(String[] args) {  
    	String basedir="D:\\jvm_simply\\精简jre测试三\\";
        String needfile = basedir+"reclasses.txt";//运行JAR生成的，应用程序所需类的txt文件
        String sdir =  "D:\\jvm_simply\\rt\\"; //rt.jar解压后的目录
        String odir =  basedir+"rt1\\";//抽取的类存放目录
        try {  
            int sn = dealClass(needfile, sdir, odir);  
            System.out.print(sn);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
  
    }  
}  