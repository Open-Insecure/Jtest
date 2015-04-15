package com.br.dong.string.test;

public class test2 {
public static void main(String[] args) {
    String a="a.jpg,b.jpg,c.jpg,,";
    String b="aaaa";
    String t[]=a.split(",");
    String bb[]=b.split(",") ;
    System.out.println(t.length);
    System.out.println(bb.length+bb[0]);
}
}
