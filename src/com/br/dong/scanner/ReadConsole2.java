package com.br.dong.scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-8-22
 * Time: 下午3:23
 * To change this template use File | Settings | File Templates.
 */
public class ReadConsole2 {
    public static void main(String[] args) {
        //数组缓冲
        byte[] b = new byte[1024];
        //有效数据个数
        int n = 0;
        try{
            while(true){
                //提示信息
                System.out.println("请输入：");
                //读取数据
                n = System.in.read(b);
                //转换为字符串
                String s = new String(b,0,n - 1);
                //判断是否是quit
                if(s.equalsIgnoreCase("exit")){
                    break; //结束循环
                }
                //回显内容
                System.out.println("输入内容为：" + s);
            }
        }catch(Exception e){}
    }
}
