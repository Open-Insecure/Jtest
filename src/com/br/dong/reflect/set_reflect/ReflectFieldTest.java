package com.br.dong.reflect.set_reflect;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-11-10
 * Time: 14:11
 */
public class ReflectFieldTest {
    public static void main(String[] args) throws Exception
    {    Student stu=new Student();
        stu.setId(1L);
        stu.setName("Josean");
        stu.setNo("201403185203344");
        stu.setCreatedate(new Date());
        stu.setNickname("copyman");
        //获得属性
        Field property1=stu.getClass().getDeclaredField("name");
        //System.out.println(property1);//out:private java.lang.String com.cx.test.Student.name
        Field property3=stu.getClass().getField("nickname");
        System.out.println("ddddddddd"+property3.get(stu));
        //System.out.println(property3);//out:public java.lang.String com.cx.test.Student.nickname
        //错误方法 getField系列方法只能获取公共字段
        //Field property2=stu.getClass().getField("name");
        //System.out.println(property2);
        //会抛java.lang.NoSuchFieldException
        Field [] prFields4=stu.getClass().getDeclaredFields();
        for(Field field:prFields4)
        {
            System.out.println("cccccccccc"+field);
            System.out.println(field.equals(property1));
            //私有变量必须先设置Accessible为true
            field.setAccessible(true);
            //获取用get类方法。
            System.out.println(field.get(stu));
        }
        //设置用set类方法
        property3.set(stu, "名字被我改了，哈哈");

        System.out.println(stu.getNickname());

    }
}
