package com.br.dong.execall;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-3
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */
public class Demo{
     public static void main(String args[]){
         try {
             Runtime.getRuntime().exec("notepad.exe");
         } catch (IOException e) {
             e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
         }
     }
    }
