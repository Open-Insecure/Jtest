package com.br.dong.exceptionTest;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.exceptionTest
 * AUTHOR: hexOr
 * DATE :2017/3/27 14:16
 * DESCRIPTION:
 */
public class Exception1 {
    public static void main(String[] args) {
        try{
            throw new Exception("我是测试exception");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            System.out.println("这个肯定会运行");
        }
        System.out.println("这个会运行吗？");
    }
}
