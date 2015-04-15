package com.br.dong.DateFormatToString;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatToString {
    protected static Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
 
     public static void main(String[] args) {
         Date date = new Date();
         //date to string
         System.out.println(format.format(date));
        try {
        	
           Date date1 = (Date) format.parseObject("2005-11-15 20:55:48");
           //date1
           System.out.println("date1:"+date1);
           //
            System.out.println("long:"+date1.getTime());
        } catch (ParseException e) {
             // TODO Auto-generated catch block

        }
         format = new SimpleDateFormat("yyyy-MM-dd");
       System.out.println(format.format(date));
       format = new SimpleDateFormat("hh:mm:ss");
        System.out.println(format.format(new Date()));

   }
 }
