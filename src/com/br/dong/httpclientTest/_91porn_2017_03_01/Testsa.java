package com.br.dong.httpclientTest._91porn_2017_03_01;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.httpclientTest._91porn_2017_03_01
 * AUTHOR: hexOr
 * DATE :2017/3/3 10:49
 * DESCRIPTION:
 */
public class Testsa {
    public static void main(String[] args) {
        System.out.println(getHost("http://up.h9p.space/view_video.php?viewkey=06ecca99b336e62dbe08&page=1&viewtype=basic&category=hot"));
        System.out.println(getHost("http://23.237.32.114:443//dl//0e2a43378504d217dc444a2e814a2528/58877dd7//91porn/mp43/193099.mp4"));
        //获得文件名
        File tempFile =new File( "http://23.237.32.114//mp43/200636.mp4?st=0Z7JrGdXG1KVw6D9_RxqaA&e=1488519536".trim());

        String fileName = tempFile.getName();

        System.out.println("fileName = " + fileName);

        fileName=fileName.split("\\.mp4")[0]+".mp4";

        System.out.println("rrrrrrrr:  "+fileName);

    }


    /***
     * 正则表达式获取host
     * @param url
     * @return
     */
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
