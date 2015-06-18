package com.br.dong.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author  hexd
 * 创建时间：2014-8-11 下午5:55:28 
 * 类说明 
 */
public class Pt1 {
    public static void main(String[] args) {
//        test2();
        String testCommand="source /home/oracle/onekey/$SID$_profile $SID2$ 。$SID3$，";

        Pattern p2 = Pattern.compile("\\$(.*)\\$");
        Matcher m2 = p2.matcher(testCommand);
        String r="";
        while(m2.find()){
            r=r+m2.group();
        }
        System.out.println(r.replace("$",""));
    }
    public static void test1(){
        String s = "dsadsadas<peter>dsadasdas<lionel>\"www.163.com\"<kenny><>";
        Pattern p = Pattern.compile("(<[^>]*>)");
        Matcher m = p.matcher(s);
        List<String> result=new ArrayList<String>();
        while(m.find()){
            result.add(m.group());
        }
        for(String s1:result){
            System.out.println(s1);
        }
    }
    public static void test2(){
        String testStr = "12315<Text>show me</Text>";
        Pattern p2 = Pattern.compile(">(.*)<");
        Matcher m2 = p2.matcher(testStr);
        while(m2.find()){
            System.out.println(m2.group());
        }
    }
}
