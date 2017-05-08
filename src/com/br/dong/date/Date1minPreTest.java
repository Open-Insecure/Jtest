package com.br.dong.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.date
 * AUTHOR: hexOr
 * DATE :2017/3/21 15:51
 * DESCRIPTION: 一分钟之前
 */
public class Date1minPreTest {
    public static void main(String[] args) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c= Calendar.getInstance();
        System.out.println(sdf.format(c.getTime()));
        c.add(Calendar.HOUR_OF_DAY, -1);//1小时前
        System.out.println(sdf.format(c.getTime()));
        c=Calendar.getInstance();
        c.add(Calendar.MINUTE, -1);//1分钟前
        c.add(Calendar.SECOND,-10);//10秒之前
        System.out.println(sdf.format(c.getTime()));

    }
}
