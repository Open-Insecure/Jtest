package com.br.dong.map;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** 
 * @author  hexd
 * 创建时间：2014-6-9 下午4:31:42 
 * 类说明 
 */
public class MapAndObject {
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Map map=new HashMap();
		map.put("NA_ME", "hhe");
		map.put("VA_LUE", "xixi");
		//创建一个对象实例
		 ObjOne o=new ObjOne();
		 //获得反射class对象
		 Class c=o.getClass();
		 //
		 Field fields[] = c.getDeclaredFields();  
		 //
		 
		 //第0个属性name
		 Field field = fields[0];   
		 //属性名字
		 String fieldName = field.getName();
		 System.out.println("ff:"+fieldName);
		 String firstLetter = fieldName.substring(0, 1).toUpperCase();//将属性的首字母大写  
     	String getMethodName = "get" + firstLetter + fieldName.substring(1);//拼写成属性的标准get方法名称  
        // 获得各属性对应的getXXX()方法  
         Method getMethod = c.getMethod(getMethodName, new Class[] {});  
         System.out.println(getMethodName);
         // 调用原对象的getXXX()方法  获得返回值
         Object value = getMethod.invoke(o, new Object[]{}); 
         //获得get方法的返回值进行检测
         	System.out.println("..."+value);
        //set方法
       String setName="set"+firstLetter+fieldName.substring(1);
       
       //循环class所有方法
       Method[] ms = c.getDeclaredMethods();
       for (Method method : ms) {
    	   //这里遍历一下 可有可以无 实验而已
    	   System.out.println(method);
    	  }
       Method method = c.getDeclaredMethod(setName, field.getType());
       method.invoke(o, "设置的name");
  	 System.out.println("反射"+o.getNa_me());
		Set<String> set=map.keySet();
		for(String s:set){
			if(set.contains("na_me".toUpperCase())){
				System.out.println("contains:"+s);
			}
			
			System.out.println(s.toUpperCase());
			System.out.println(map.get(s));
		}
		
	}
}
