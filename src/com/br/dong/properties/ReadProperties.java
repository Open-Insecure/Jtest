package com.br.dong.properties;

import java.util.Properties; 
import java.io.InputStream; 
import java.io.IOException; 

/** 
* 读取Properties文件的例子 
* File: TestProperties.java 
* Date: 2008-2-15 18:38:40 
*/ 
public final class ReadProperties { 
    private static String param1; 
    private static String param2; 

    static { 
        Properties prop = new Properties(); 
        InputStream in = Object.class.getResourceAsStream("/config.properties"); 
        try { 
            prop.load(in); 
            param1 = prop.getProperty("path").trim(); 
            param2 = prop.getProperty("filename").trim(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
    } 

    /** 
     * 私有构造方法，不需要创建对象 
     */ 
    private ReadProperties() { 
    } 

    public static String getParam1() { 
        return param1; 
    } 

    public static String getParam2() { 
        return param2; 
    } 

    public static void main(String args[]){ 
        System.out.println(getParam1()); 
        System.out.println(getParam2()); 
    } 
}