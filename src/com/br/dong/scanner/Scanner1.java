package com.br.dong.scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-8-22
 * Time: 下午3:15
 * To change this template use File | Settings | File Templates.
 */
public class Scanner1 {
    public static void main(String[] args) {
        try{
            //提示信息
            System.out.println("请输入：");
            //数组缓冲
            byte[] b = new byte[1024];
            //读取数据
            int n = System.in.read(b);
            //转换为字符串
            String s = new String(b,0,n);
            //回显内容
            System.out.println("输入内容为：" + s);
        }catch(Exception e){}
    }
}
