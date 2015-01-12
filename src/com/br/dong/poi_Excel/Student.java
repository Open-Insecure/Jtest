package com.br.dong.poi_Excel;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-1-8
 * Time: 上午9:37
 * To change this template use File | Settings | File Templates.
 */
public class Student {
    private int id;
    private  String name;
    private int age;
    private Date brith;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBrith() {
        return brith;
    }

    public void setBrith(Date brith) {
        this.brith = brith;
    }

    public Student(int id, String name, int age, Date brith) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.brith = brith;
    }
}
