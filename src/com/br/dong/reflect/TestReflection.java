package com.br.dong.reflect;

import java.lang.reflect.Field;  
import java.lang.reflect.Method;  

  
/** 
 *  
 * @author why 
 * 
 */  
public class TestReflection {  
    public static void main(String[] args) {  
        TeacherClass teacher = new TeacherClass();  
        teacher.setTeacherAge(25);  
        teacher.setTeacherName("why");  
          
        StudentClass student = new StudentClass();  
        student.setStudentAge(29);  
        student.setStudentName("tongjm");  
          
        try {  
            log(teacher);  
            log(student);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
          
    }  
      
      
    public static void log(Object obj) throws Exception{  
        Class ownerClass = obj.getClass();  
        //获得对象的属性，包括private级的，用getField()可以获得public级的所有属性。括号内可指定属性名称获得特定的属性  
        Field fields[] = ownerClass.getDeclaredFields();  
          
        String objName = ownerClass.getName();//获得对象的全路径名称  
          
        //System.out.print(objName + ": ");  
          
        for(int i = 0;i < fields.length;i ++){  
            Field field = fields[i];  
              
            String fieldName = field.getName();//获得属性名称  
              
            String firstLetter = fieldName.substring(0, 1).toUpperCase();//将属性的首字母大写  
            String getMethodName = "get" + firstLetter + fieldName.substring(1);//拼写成属性的标准get方法名称  
             System.out.println("getMethodName"+getMethodName); 
            // 获得各属性对应的getXXX()方法  
            Method getMethod = ownerClass.getMethod(getMethodName, new Class[] {});  
              
            // 调用原对象的getXXX()方法  
            Object value = getMethod.invoke(obj, new Object[]{});  
              
            System.out.print("--"+fieldName + "aaa=" + value.toString() + "; ");  
        }  
          
        System.out.println();  
    }  
}  
  
class StudentClass {  
  
    private String studentName;  
    private int studentAge;  
     public static String vv(){
    	Class a =StudentClass.class;
		return "";
     } 
    public int getStudentAge() {  
        return studentAge;  
    }  
    public void setStudentAge(int studentAge) {  
        this.studentAge = studentAge;  
    }  
    public String getStudentName() {  
        return studentName;  
    }  
    public void setStudentName(String studentName) {  
        this.studentName = studentName;  
    }  
}  
  
class TeacherClass {  
  
    private String teacherName;  
    private int teacherAge;  
      
    public int getTeacherAge() {  
    	System.out.print("被掉了");
        return teacherAge;  
    }  
    public void setTeacherAge(int teacherAge) {  
        this.teacherAge = teacherAge;  
    }  
    public String getTeacherName() {  
    	System.out.print("被掉了");
        return teacherName;  
    }  
    public void setTeacherName(String teacherName) {  
        this.teacherName = teacherName;  
    }  
      
      
}  
