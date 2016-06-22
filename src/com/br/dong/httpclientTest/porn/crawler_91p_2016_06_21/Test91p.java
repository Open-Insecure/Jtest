package com.br.dong.httpclientTest.porn.crawler_91p_2016_06_21;

/**
 * Created by Administrator on 2016-06-16.
 */
public class Test91p {
    public static void main(String[] args) {

        int []arr=new int[]{8,2,1,0,3};//号码的
        int []index=new int[]{2,0,3,2,4,0,1,3,2,3,3};//下标
        String tel="";
        for(int i:index){
            System.out.println("每个循环的"+i);
            tel+=arr[i];
        }
        System.out.println(tel);
    }


}
