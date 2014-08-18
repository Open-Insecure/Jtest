package com.br.dong.reflect;

import java.lang.reflect.Field;  

public class ReflectTest {  
  
    public static void main(String[] args) {  
        try {  
            Class<?> obj = Class.forName("com.br.dong.reflect.Person");  
            Field[] f = obj.getDeclaredFields();  
            for(Field field : f){  
                field.setAccessible(true);  
                System.out.println(field.getName()+":"+field.get(obj.newInstance()));  
                if(field.getName().equals("hh")){
                	System.out.println("hhhhhh");
                	if(field.get(obj.newInstance())==null||field.get(obj.newInstance()).equals("")){
                		System.out.println("aaaaaaa");
                	}
                }
            }  
        } catch (ClassNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IllegalArgumentException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (InstantiationException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
}  
  
 
