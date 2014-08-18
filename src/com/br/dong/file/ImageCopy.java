package com.br.dong.file;
import java.io.*;
/** 
 * @author  hexd
 * 创建时间：2014-7-31 下午12:05:48 
 * 类说明 
 */
public class ImageCopy {
	 /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //思路：先把图片读入到了内存————》》再把写到某个文件
        //因为是图片文件，必须要用字节流
        //输入流
        FileInputStream fis=null;
        //输出流
        FileOutputStream fos=null;
        try {
            fis=new FileInputStream("d:\\a.jpg");
            fos=new FileOutputStream("e:\\a.jpg");
            byte buf[]=new byte[1024];
            int n=0;
            try {
                if((n=fis.read(buf))!=-1)
                {//输出到指定文件
                    fos.write(buf);
                     
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
             
        }
     
 
    }
 
}
