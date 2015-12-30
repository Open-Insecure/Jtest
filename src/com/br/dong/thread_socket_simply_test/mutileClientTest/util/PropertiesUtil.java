package com.br.dong.thread_socket_simply_test.mutileClientTest.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-10
 * Time: 13:29
 * 配置文件读取
 */
public class PropertiesUtil {
    private String propFileName="/com/br/dong/thread_socket_simply_test/mutileClientTest/config/config.properties";//配置文件相对路径
    private static  PropertiesUtil propertiesUtil=null;
    private Properties prop = new Properties();
    /***
     * 私有化构造方法
     */
    private PropertiesUtil() throws IOException {
        InputStream inputStream = Object.class.getResourceAsStream(propFileName);
        prop.load(inputStream);
        if (inputStream == null) {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }

    }
    /***
     * 单例方法
     * @return
     * @throws IOException
     */
    public static PropertiesUtil getInstance() throws IOException {
        if(propertiesUtil==null) return  new PropertiesUtil();
        return propertiesUtil;
    }

    public  String getPropertiesValue(String name){
        return prop.getProperty(name);
    }

    public static void main(String[] args) throws IOException {
        PropertiesUtil propertiesUtil=PropertiesUtil.getInstance();
        System.out.println(propertiesUtil.getPropertiesValue("test"));
    }

}
