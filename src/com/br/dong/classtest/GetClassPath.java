package com.br.dong.classtest;

import com.br.dong.file.FileOperate;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-10-23
 * Time: 上午11:46
 * To change this template use File | Settings | File Templates.
 */
public class GetClassPath {
    public static void main(String[] args) {
        //获得当前工程目录
        System.out.println(System.getProperty("user.dir"));

        System.out.println(GetClassPath.class.getResource("test.txt").toString().substring(6));
        FileOperate.readLine(GetClassPath.class.getResource("test.txt").toString().substring(6));
    }
}
