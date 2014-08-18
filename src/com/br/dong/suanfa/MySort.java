package com.br.dong.suanfa;

import java.util.HashSet;
import java.util.Set;

/** 
 * @author  hexd
 * 创建时间：2014-6-18 上午11:42:20 
 * 类说明 
 */
public class MySort {
    public static void main(String[] args) {
        MySort sort = new MySort();
        int[] arr  = new int[]{3,22,11,5,400,99,20,22,5};
        sort.sort(arr);
        for(int i : arr){
            System.out.print(i+",");
        }
    }

    public void sort(int[] targetArr){//小到大的排序

        int temp = 0;
        for(int i = 0;i<targetArr.length;i++){
            for(int j = i;j<targetArr.length;j++){
            	System.out.println("i:"+i+"j:"+j);
                if(targetArr[i]>targetArr[j]){

                   //方法一：
                    temp = targetArr[i];
                    targetArr[i] = targetArr[j];
                    targetArr[j] = temp;

                    //方法二:
                    /* targetArr[i] = targetArr[i] + targetArr[j];
                    targetArr[j] = targetArr[i] - targetArr[j];
                    targetArr[i] = targetArr[i] - targetArr[j];*/
                   }

             }
        }
    }
}
