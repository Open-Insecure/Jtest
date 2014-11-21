package com.br.dong.properties;
/** 
 * @author  hexd
 * 创建时间：2014-6-3 下午1:40:50 
 * 类说明 
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;
public class PropertyManager {
 
 private static PropertyManager manager = null;
 private static Object managerLock = new Object();
 private static String propsName = "/com/br/dong/properties/config.properties";

    public static void main(String[] args) {
        System.out.println(PropertyManager.getProperty("hexd"));
        PropertyManager.setProperty("hexd","呵呵");
    }

 /**
  * 初始化
  */
 public static void init() {
  if( manager == null ) { 
   synchronized (managerLock) {
    if( manager == null ) {
     manager = new PropertyManager( propsName );
    }
   }
  }
 }
 
 /**
  * 获取属性值
  * @param name
  * @return
  */
 public static String getProperty( String name ) {
  init();
  return manager.getProp(name);
 }
 /**
  * 添加属性值
  * @param name
  * @param value
  */
 public static void setProperty ( String name , String value ) {
  init();
  manager.setProp( name , value);
 }
 
 /**
  * 删除属性值
  * @param name
  */
 public static void deleteProterty( String name ) {
  init();
  manager.deleteProp( name );
 }
 
 public static Enumeration propertyNames(){
  init();
  return manager.propNames();
 }
 
 public static boolean propertyFileIsReadable( ) {
  init();
  return manager.propFileIsReadable();
 }
 
 private Properties properties = null;
 private Object propertiesLock = new Object();
 private String resourceURI ;
 
 private PropertyManager( String resourceURI ) {
  this.resourceURI = resourceURI;
 }
 
 private String getProp( String name ) {
  if( properties == null ) {
   synchronized ( propertiesLock ) {
    if( properties == null ) {
     loadProps();
    }
   }
  }
  String property = properties.getProperty( name );
  if( property != null ) {
   return property.trim();
  } else {
   return null;
  }
 }
 
 private void setProp( String name , String value ) {
  synchronized ( propertiesLock ) {
   if( properties == null ) {
    loadProps( );
   }
   properties.setProperty(name, value);
   saveProps();
  }
 }
 private void deleteProp( String name ) {
  synchronized ( propertiesLock ) {
   if( properties == null ) {
    loadProps( );
   }
   properties.remove( name );
   saveProps();
  }
 }
 
 private Enumeration propNames() {
  if( properties == null ) {
   synchronized ( propertiesLock ) {
    if( properties == null ) {
     loadProps( );
    }
   }
  }
  return properties.propertyNames();
 }
 
 private void loadProps( ) {
  properties = new Properties();
  InputStream in = null;
  try {
   File file4 = new File(getClass().getResource(resourceURI).getFile());
   in = getClass().getResourceAsStream( resourceURI);
   properties.load(in);
  } catch( Exception e ) {
   e.printStackTrace();
  } finally {
   try{
    in.close();
   } catch( Exception e ) {
    e.printStackTrace();
   }
  }
 }
 
 private void saveProps( ) {
  String path = properties.getProperty("path").trim();
  OutputStream out = null;
  try{
   out = new FileOutputStream( path );
   properties.store(out, "sys.properties----" + ( new java.util.Date()));
   
  } catch ( Exception e ) {
   e.printStackTrace();
  } finally {
   try{
    out.close();
   } catch( Exception e ) {
    e.printStackTrace();
   }
  }
 }
 private boolean propFileIsReadable( ) {
  try {
   InputStream in = getClass().getResourceAsStream( resourceURI);
   return true;
  } catch ( Exception e ) {
   return false;
  }
 }
 
}
