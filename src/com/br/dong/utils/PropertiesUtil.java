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
 *
 */
public class PropertiesUtil {
   private static  String propertiesPath="";//properties的路径
   private   Properties properties=new Properties();
   private static PropertiesUtil propertiesUtil=null;


    /**
     * 私有化构造方法
     * @param propertiesPath
     * @throws IOException
     */
   private PropertiesUtil(String propertiesPath)  {
       this.propertiesPath=propertiesPath;
       InputStream inputStream = Object.class.getResourceAsStream(propertiesPath);
       try{
           properties.load(inputStream);
           if (inputStream == null) {
               throw new FileNotFoundException("property file '" + propertiesPath + "' not found in the classpath");
           }
       }catch (Exception e){
           e.getMessage();
       }

   }

    /**
     * 单例获得工具类
     * @param propertiesPath properties配置文件路径
     * @return
     * @throws IOException
     */
   public static PropertiesUtil getInstance(String propertiesPath)   {
       if(propertiesUtil==null) return new PropertiesUtil(propertiesPath);
       return propertiesUtil;
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

    /**
     * 查询properties中的key对应的value
     * @param proKey key
     * @return
     */
    public String getPropValue(String proKey){
        return properties.getProperty(proKey);
    }

    /**
     * 返回转化为int型的value 报错则返回-1
     * @param proKey
     * @return
     */
    public int getPropValueInt(String proKey){
        String value=properties.getProperty(proKey);
        int v=-1;
        try{
            v=Integer.parseInt(value);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return v;
        }

    }

    public static void main(String[] args) throws IOException {

//        PropertiesUtil util=PropertiesUtil.getInstance("/com/br/dong/properties/readResources/resources/c.properties");
        PropertiesUtil util=PropertiesUtil.getInstance("/com/br/dong/httpclientTest/youbb/net/properties/config.properties");
        System.out.println( util.getPropValue("IMG_ROOT_PATH","sssss"));
    }

}
