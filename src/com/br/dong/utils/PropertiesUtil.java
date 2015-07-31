package com.br.dong.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-07-29
 * Time: 10:24
 * 加载读取properties的工具
 */
public class PropertiesUtil {
   private String propertiesPath="";//properties的路径
   private Properties properties=new Properties();//properties实例

    public PropertiesUtil(String propertiesPath) throws IOException {
        this.propertiesPath = propertiesPath;
        InputStream inputStream = Object.class.getResourceAsStream(propertiesPath);
        properties.load(inputStream);
        if (inputStream == null) {
            throw new FileNotFoundException("property file '" + propertiesPath + "' not found in the classpath");
        }

    }

    /**
     * 查询properties中的key对应的value
     * @param proKey key
     * @param defaultValue 默认值
     * @return
     */
    public String getPropValue(String proKey,String defaultValue){
        return properties.getProperty(proKey,defaultValue);
    }



    public static void main(String[] args) throws IOException {

        PropertiesUtil util=new PropertiesUtil("/com/br/dong/properties/readResources/resources/c.properties");

        System.out.println( util.getPropValue("user1","sssss"));
    }

}
