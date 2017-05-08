package com.br.dong.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.pattern
 * AUTHOR: hexOr
 * DATE :2017/3/23 15:05
 * DESCRIPTION:
 */
public class UserNamePatternTest {
    public static void main(String[] args) {
        System.out.println(checkUserName("abcsdsda"));
        System.out.println(checkUserName("he7253997"));
        System.out.println(checkUserName("he.7253997"));
        System.out.println(checkUserName("he+7253997"));
        System.out.println(checkUserName("he_7253997"));
    }

    private static Boolean checkUserName(String userName){
        String regex="^[A-Za-z][A-Za-z1-9_-]+$";
        return match(regex,userName);
    }

    /**
     * @param regex 正则表达式字符串
     * @param str 要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
