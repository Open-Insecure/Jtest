package com.br.dong.jdbc.postgreSql;


import com.br.dong.utils.PropertiesUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2016-03-01
 * Time: 19:34
 * 链接postgreSql
 */
public class GetComJDBCForPostgreSql {
    private static final Log log= LogFactory.getLog(GetComJDBCForPostgreSql.class);
    private static PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("/com/br/dong/jdbc/postgreSql/config/pg_config.properties");//读取配置文件
    private static final String ipaddress=(String) propertiesUtil.getPropValue("database.local.ipaddress");
    private static final String username=(String)propertiesUtil.getPropValue("database.local.username");
    private static final String password=(String)propertiesUtil.getPropValue("database.local.password");
    private static final String port=(String)propertiesUtil.getPropValue("database.local.port");
    private static final String instance=(String)propertiesUtil.getPropValue("database.local.instance");

    public static void main(String[] args) {
//
        System.out.println(GetComJDBCForPostgreSql.getExecuteQuery("select * from score"));
    }
    public static Map JDBCMap = new HashMap();

    public static List getExecuteQuery(String sql)  {
        List list=null;
        try{
              list= getExecuteQuery(username,password,ipaddress,port,instance,sql);
        }catch (Exception e){
            log.error(e);
        }
        return list;
    }

    /**
     * 执行sql并返回结果
     * @param user
     * @param password
     * @param IP
     * @param port
     * @param instance
     * @param sql
     * @return
     * @throws NullPointerException
     */
    public static List getExecuteQuery(String user,String password,String IP,String port,String instance, String sql) throws NullPointerException {
        JdbcForPostgreSql comJDBC = (JdbcForPostgreSql) JDBCMap.get(IP);
        if (comJDBC == null) {
            comJDBC = new JdbcForPostgreSql(user,password,IP,port,instance);
            JDBCMap.put(IP, comJDBC);
        }
//        System.out.println("comJDBC:" + sql);
        return comJDBC.getExecuteQuery(sql);
    }

    public static void ExecuteSql(String sql){
        ExecuteSql(username,password,ipaddress,port,instance,sql);
    }
    /**
     * 执行sql不返回结果
     * @param user
     * @param password
     * @param IP
     * @param port
     * @param instance
     * @param sql
     * @throws NullPointerException
     */
    public static void ExecuteSql(String user, String password, String IP, String port, String instance, String sql) throws NullPointerException {
        JdbcForPostgreSql comJDBC = (JdbcForPostgreSql) JDBCMap.get(IP);
        if (comJDBC == null) {
            comJDBC = new JdbcForPostgreSql(user,password,IP,port,instance);
            JDBCMap.put(IP, comJDBC);
        }
        comJDBC.executeSql(sql);
    }
}
