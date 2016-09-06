package com.br.dong.string.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.string.test
 * AUTHOR: hexOr
 * DATE :2016-09-05 20:36
 * DESCRIPTION:
 */
public class ReplaceTest3 {
    public static void main(String[] args) {

        String str="http://pic.av202.com/pic/upload/vod/2016-09-04/14729718287.jpg";
        String host=getHost(str);
        System.out.println(host);
    }

    public static String getHost(String url){
        if(url==null||url.trim().equals("")){
            return "";
        }
        String host = "";
        Pattern p =  Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
        Matcher matcher = p.matcher(url);
        if(matcher.find()){
            host = matcher.group();
        }
        return host;
    }
}
