package com.br.dong.utils;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-10-22
 * Time: 下午5:37
 * To change this template use File | Settings | File Templates.
 */
public class MysqlEncodingUtil {
    //从数据库取出用
    public static String latin1ToGBK(String str)
    {
        try {
            String temp_p = str;
            byte[] temp_t = temp_p.getBytes("ISO-8859-1");
            String temp = new String(temp_t,"GBK");
            return temp;
        }catch (UnsupportedEncodingException ex) {
            System.out.println(ex);
            return "";
        }
    }
    //存入中文数据时用
    public static String GBKToLatin1(String str)
    {
        if(str==null)
        {
            str="";
        }
        else{
            try{
                str=new String(str.getBytes("GBK"),"ISO-8859-1");
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return str;
    }
}
