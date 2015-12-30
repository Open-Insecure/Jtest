package com.br.dong.text_get_test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-25
 * Time: 16:09
 */
public class TextGetForKpd {
   /**sql匹配正则*/
   private static String reg = "(/\\*(?:.|[\\n\\r])*?\\*/)|"+ "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|exec|count|master|into|drop|execute|\\\\)\\b)";
   /**sql匹配正则pattern 大小写无关匹配*/
   private static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
   /**sql格式化正则 过滤其中的 "  \ 的符号 (不过滤，一般是折行的sql*/
   private static String replaceReg="(?:\"|\\\\|sql\\s=|sql\\s=\\ssql\\s+|\"|sSql=|sSql\\s=)";
   /**读取文本编码格式*/
   private static String encoding="GBK";
   /**生成的过滤文档路径*/
   private static String textPath="C:\\Users\\Administrator\\Desktop\\三方源码\\text_for_kpd.txt";
   /**当前目录下的文件列表 放在全局 防止fileList递归调用后被重新置空 */
   private static List<String> fileList=new ArrayList();
   /**当前文件目录*/
   private static String filePath=  "C:\\Users\\Administrator\\Desktop\\三方源码\\kkf\\";
    public static void main(String[] args) {
//        for(int i=2610;i<=2636;i++){
//            readFile("C:\\Users\\Administrator\\Desktop\\三方源码\\kkf\\bankreq_"+i+".kpd",i+".kpd");
//        }
        /**读取当前目录*/
        readFloder(new File(filePath));
        for(String fileName:fileList){
            /**只读去.kpd的文件过滤sql*/
            if(fileName.endsWith(".kpd")){
                readFile(filePath+fileName,fileName);
            }
        }
    }

    /***
     * 判断是否是sql
     * @param str 文本行
     * @param fileName 文本名
     */
    public static String isSql(String str,String fileName) {
        /**过滤 并格式化sql语句*/
        if (sqlPattern.matcher(str).find()) {
            /**过滤掉以'起始的注释代码*/
            if(!str.trim().startsWith("'")){
                System.out.println(fileName + "过滤的" + str);
                return str;
            }
        }
        return "";
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
            File file=new File(filePath);
            /**判断文件是否存在*/
            if(file.isFile() && file.exists()){
                /**设置读取的编码方式*/
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                /**是否结束的标志*/
                Boolean flag=false;
                while((lineTxt = bufferedReader.readLine()) != null){
                    /**当前获得的sql语句*/
                    String current=isSql(lineTxt, fileName);
                    if(current.equals("")) continue;
                    /**如果当前读取的行数包含 \  则表示这个sql未完结*/
                    if(current.endsWith("\\")){
                        flag=true;
                        appendMehtod(current.replaceAll(replaceReg, "").trim());
                    }else if(current.contains("sql = sql +")||current.contains("sSql = sSql +")){
                        appendMehtod(current.replaceAll(replaceReg, "").trim());
                    }
                    else{
                        /**如果当前行是一条完整的sql */
                        if(flag){
                            appendMehtod(current.replaceAll(replaceReg, "").trim());
                        }else{
                            appendMehtod("\r\n"+current.replaceAll(replaceReg, "").trim());
                        }
                    }
                    /**以行的形式读取文本 过滤出其中的sql行*/
                }

//                /**如果过滤的sql中最后一个为" 表示这是一条完整的sql需要换行 如果最后一个为 \ 表示这不是一条完整的sql 不需要换行 */
//                str=str.replaceAll(replaceReg, "");
//                System.out.println(fileName+"拼装的" + str);
//                /**此处插入到文本中*/
//                appendMehtod(str);
                read.close();
            }else{
                System.out.println(fileName + "找不到指定的文件");
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
