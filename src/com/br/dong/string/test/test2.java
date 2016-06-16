package com.br.dong.string.test;

public class test2 {
public static void main(String[] args) {
//    String a="a.jpg,b.jpg,c.jpg,,";
//    String b="aaaa";
//    String t[]=a.split(",");
//    String bb[]=b.split(",") ;
//    System.out.println(t.length);
//    System.out.println(bb.length+bb[0]);

    String test="2016-02-29 09:43,2016-02-29 09:44,";
    String []t=test.split(",");
    String ins="'";
    for(int i=0;i<t.length;i++){
        ins+=t[i]+"','";
    }
    System.out.println(ins.substring(0,ins.length()-2));
}
}
