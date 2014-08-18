package com.br.dong.httpclientTest;

import java.io.*;
import java.util.*;
import java.net.*;

public class GetTitle {
  public static void main(String[] args)
 {
    try
  {
   URL url=new URL("http://www.sina.com");
   URLConnection conn=url.openConnection();
   BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()) );
   //StringBuffer document=null; 
                                               //StringBuffer 与String的区别
   String line;             //读入网页信息
   String document="";       //存取网页内容
      
while
((line=reader.readLine())!=null)
    document=line+document;
    //document.append(line+"\n");
   
   //System.out.println(document); 
   document = document.substring(document.indexOf("<title>")+7,document.indexOf("</title>"));
   System.out.println(document);

}
  catch(Exception e)
  {
  System.out.println("url error");
  }
 }

}