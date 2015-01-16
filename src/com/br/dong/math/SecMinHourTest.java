package com.br.dong.math;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-1-14
 * Time: 下午1:34
 * To change this template use File | Settings | File Templates.
 * 分秒小时测试
 */
public class SecMinHourTest {

    public static void main(String[] args) {
        int  interval=6010;
        if(interval>60&&interval<(60*60)){
            //转分钟
            System.out.println(interval/60+"分");
        }else if(interval<=60){
            // 每多秒执行一次
            System.out.println(interval);
        }else if(interval>=(60*60)){
            //转小时
            System.out.println(interval/(60*60)+"小时");
        }
        System.out.println(change(interval));
        test();
    }
    public static void test(){
        String time="2015-01-14 14:23:12" ;
        String temp[]=time.split(" ");
        System.out.println(temp[0]+"..."+temp[1]);
    }

    public static String change(int second){
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second%3600;
        if(second>3600){
            h= second/3600;
            if(temp!=0){
                if(temp>60){
                    d = temp/60;
                    if(temp%60!=0){
                        s = temp%60;
                    }
                }else{
                    s = temp;
                }
            }
        }else{
            d = second/60;
            if(second%60!=0){
                s = second%60;
            }
        }

        return h+"时"+d+"分"+s+"秒";
    }
}
