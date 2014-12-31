package com.br.dong.quzart.dongtai;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-12-31
 * Time: 下午4:39
 * To change this template use File | Settings | File Templates.
 */
public class MyTask {
    private String name;

    public void run() {
        System.out.println("Run task: " + name + ".");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
