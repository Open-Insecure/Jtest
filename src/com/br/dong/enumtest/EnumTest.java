package com.br.dong.enumtest;
/** 
 * @author  hexd
 * 创建时间：2014-6-30 下午4:01:28 
 * 类说明 
 */
public enum EnumTest {
    FRANK("The given name of me"),
    LIU("The family name of me");
    private String context;
    private String getContext(){
   	 return this.context;
    }
    private EnumTest(String context){
   	 this.context = context;
    }
    public static void main(String[] args){
   	 for(EnumTest name :EnumTest.values()){
   		 System.out.println(name+" : "+name.getContext());
   	 }
   	 System.out.println(EnumTest.FRANK.getDeclaringClass());
   	 System.out.println(EnumTest.valueOf("LIU").getContext());//找到枚举类中名字为LIU的内容
    }
} 
