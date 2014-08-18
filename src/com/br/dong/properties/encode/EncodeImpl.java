package com.br.dong.properties.encode;
/** 
 * @author  hexd
 * 创建时间：2014-6-4 上午11:13:20 
 * 类说明 
 */
import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.util.Enumeration;

import java.util.Properties;

 

public class EncodeImpl implements Encode {

 
   @Override
   public void decode(String fileInputName, String fileOutputName)

         throws Exception {

      FileInputStream in=new FileInputStream(fileInputName);

      FileOutputStream out=new FileOutputStream(fileOutputName);

      Properties prop=new Properties();

      prop.load(in);

      Enumeration<?> names=prop.propertyNames();

      while(names.hasMoreElements()){

         String name=(String)names.nextElement();

         String value=prop.getProperty(name);

         String result="";

         for(char c:value.toCharArray()){

            c-=10;

            result+=c;

         }

         prop.setProperty(name, result);

      }

      prop.store(out, "");

      System.out.println("解密完成");

   }

 

   @Override

   public void encode(String fileInputName, String fileOutputName)

         throws Exception {

      FileInputStream in=new FileInputStream(fileInputName);

      FileOutputStream out=new FileOutputStream(fileOutputName);

      Properties prop=new Properties();

      prop.load(in);

      Enumeration<?> names=prop.propertyNames();

      while(names.hasMoreElements()){

         String name=(String)names.nextElement();

         String value=prop.getProperty(name);

         String result="";

         for(char c:value.toCharArray()){

            c+=10;

            result+=c;

         }

         prop.setProperty(name, result);

      }

      prop.store(out, "");

      System.out.println("加密完成");

   }

   

   public static void main(String[] args) {

      EncodeImpl e=new EncodeImpl();

      try {

         e.encode("prop.properties", "encode.txt");

         e.decode("encode.txt", "decode.txt");

      } catch (Exception e1) {

         // TODO Auto-generated catch block

         e1.printStackTrace();

      }

   }
}
