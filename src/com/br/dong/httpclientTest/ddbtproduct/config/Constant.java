package com.br.dong.httpclientTest.ddbtproduct.config;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-21
 * Time: 下午3:25
 * To change this template use File | Settings | File Templates.
 * 常量配置类
 */
public class Constant {
    //--公用的配置参数
    //html后缀
    private static final String COMMON_HTML=".html";
    //需要替换的字符,这些字符windows文件名不能出现
    private static final String COMMON_FILENAME_REG="(?:/|<|>|:|\\?|\\||\\*|\"|\\(|\\)|)";
    //默认存盘路径目录
    private static final  String COMMON_SAVE_PATH="D:\\蛋蛋bt\\download\\";
    //字节
    private final static int COMMON_BUFFER = 1024;
    //---www.sis001task.com ip: 38.103.161.188的配置
    private static final String SIS001_USERNAME="ckwison";
//    private static final String SIS001_PASSWORD="1234qwer!@#$";
    private static final String SIS001_PASSWORD="95b004";
    //相对路径资源前加绝对路径
    private static final String SIS001_ABSPRE="http://38.103.161.188/forum/";
}
