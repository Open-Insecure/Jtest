package com.br.dong.string.test;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.string.test
 * AUTHOR: hexOr
 * DATE :2016-08-31 13:49
 * DESCRIPTION:
 */
public class ReplaceTest2 {
    public static void main(String[] args) {
        String rest="ORACLE_BASE=/oracle\n" +
                    "ORACLE_HOME=/oracle/product/11.2\n" +
                    "ORACLE_SID=fdsfsfs\n" +
                    "hhhhhhhhhh";
        String tmp[]=rest.split("\n");
        for(int i=0;i<tmp.length;i++){
            String temp=tmp[i];
            if(!temp.startsWith("ORACLE_")){
                System.out.println(temp);
            }
        }
    }
}
