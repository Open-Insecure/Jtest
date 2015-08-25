package com.br.dong.utils;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-08-19
 * Time: 15:18
 */
public class MyStringUtil {
    /**
     *字符在字符串中出现的次数
     *
     *@paramstring
     *@parama
     *@return
     */
    public static int occurTimes(String string,String a){
        int pos=-2;
        int n=0;
        
        while(pos!=-1){
            if(pos==-2){
                pos=-1;
                }
            pos=string.indexOf(a,pos+1);
            if(pos!=-1){
                n++;
                }
            }
        return n;
    }
}
