package com.br.dong.classtest;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.classtest
 * AUTHOR: hexOr
 * DATE :2017/4/12 11:02
 * DESCRIPTION:
 */
public class GetClassPathJar {
    public static void main(String[] args) throws UnsupportedEncodingException {
//        String filePath="";
//        URL url = GetClassPathJar.class.getProtectionDomain().getCodeSource().getLocation();
//        try {
//            filePath = URLDecoder.decode(url.getPath(), "utf-8");// 转化为utf-8编码，支持中文
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (filePath.endsWith(".jar")) {// 可执行jar包运行的结果里包含".jar"
//            // 获取jar包所在目录
//            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
//        }
//
//        File file = new File(filePath);
//        filePath = file.getAbsolutePath();//得到windows下的正确路径
//        System.out.println("jar包所在目录："+filePath);


        /**
         * 读取jar包中的资源文件
         */
        String jarFilePath = GetClassPathJar.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        jarFilePath = java.net.URLDecoder.decode(jarFilePath, "UTF-8");
        System.out.println("aaaaaaa"+jarFilePath);
    }
}
