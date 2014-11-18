package com.br.dong.reflect;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-18
 * Time: 上午11:42
 * To change this template use File | Settings | File Templates.
 */
public class TestGetClass {
    public static void main(String[] args) {

        //拿到TestGetClass类运行class的根路径
        System.out.println(new TestGetClass().getClass().getResource("/"));
        //拿到此类运行的class的绝对路径
        System.out.println(new TestGetClass().getClass().getResource("").getPath().substring(1));
        //从根路径拿到资源文件的绝对路径
        System.out.println(new TestGetClass().getClass().getResource("/com/br/dong/test.html"));
    }
}
