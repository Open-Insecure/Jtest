package com.br.dong.swing.page;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-11
 * Time: 上午11:39
 * To change this template use File | Settings | File Templates.
 */
public class Student {
    private Integer id;
    private String name;
    private String sex;
    private int age;
    public static List<Student> students = new ArrayList<Student>();

    public Student() {
    }

    public Student(Integer id, String name, String sex, int age) {
        super();
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
