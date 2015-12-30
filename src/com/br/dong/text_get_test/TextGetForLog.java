package com.br.dong.text_get_test;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-28
 * Time: 11:21
 */
public class TextGetForLog {

    /**读取文本编码格式*/
    private static String encoding="GBK";
    /**当前目录下的文件列表 放在全局 防止fileList递归调用后被重新置空 */
    private static List<String> fileList=new ArrayList();
    /**当前文件目录*/
    private static String filePath=  "C:\\Users\\Administrator\\Desktop\\三方源码\\log\\";
    /**生成的过滤文档路径*/
    private static String textPath="C:\\Users\\Administrator\\Desktop\\三方源码\\log_sql.txt";
    public static void main(String[] args) {
        /**读取当前目录*/
        readFloder(new File(filePath));
        for(String fileName:fileList){
             readFile(filePath+fileName,fileName);
        }
    }
    /**以GBK编码添加内容到一个文件中
     * @param content 写入的内容
     */
    public static void appendMehtod(String content){
        try {
            /***设置write的写入的编码方式且设置为追加方式写入*/
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(textPath,true), encoding));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /***
     * 读取文本
     * @param filePath 文本绝对路径
     * @param fileName 文本名
     * @return
     */
    public static void readFile(String filePath,String fileName){
//        appendMehtod("\r\n--************文档:"+fileName+"获取sql**********\r\n");
        try {
            List<String> lines = FileUtils.readLines(new File(filePath), encoding);
            for(String line:lines){
                if(line.contains("sql:")){
                    appendMehtod(line.substring(line.indexOf("sql:")+"sql:".length(),line.length())+"\r\n");
                }
            }
        } catch (Exception e) {
            System.out.println(fileName+"读取文件内容出错");
            e.printStackTrace();
        }
    }
    /**
     *读取目录下面的所有文件，返回文件名的list
     * @param floder
     */
    public static List readFloder(File floder){
        /**判断传入对象是否为一个文件夹对象*/
        if(!floder.isDirectory()){
            System.out.println("你输入的不是一个文件夹，请检查路径是否有误！！");
        }
        else{
            File[] t = floder.listFiles();
            for(int i=0;i<t.length;i++){
                /**判断文件列表中的对象是否为文件夹对象，如果是则执行tree递归，直到把此文件夹中所有文件输出为止*/
                if(t[i].isDirectory()){
//                    System.out.println(t[i].getName()+"\tttdir");
                    /**递归调用*/
                    readFloder(t[i]);
                }
                else{
//                    System.out.println(t[i].getName()+"tFile");
                    fileList.add(t[i].getName());
                }
            }
        }
        return fileList;
    }
}
