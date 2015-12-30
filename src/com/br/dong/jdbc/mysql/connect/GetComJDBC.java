package com.br.dong.jdbc.mysql.connect;


import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用此连接数据库
 */
public class GetComJDBC {
	
	public static Map JDBCMap = new HashMap();

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
        ComJDBC comJDBC = (ComJDBC) JDBCMap.get(IP);
        if (comJDBC == null) {
            comJDBC = new ComJDBC(user,password,IP,port,instance);
            JDBCMap.put(IP, comJDBC);
        }
        System.out.println("comJDBC:" + sql);
        return comJDBC.getExecuteQuery(sql);
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
    public static int ExecuteSql(String user, String password, String IP, String port, String instance, String sql) throws NullPointerException {
        ComJDBC comJDBC = (ComJDBC) JDBCMap.get(IP);
        if (comJDBC == null) {
            comJDBC = new ComJDBC(user,password,IP,port,instance);
            JDBCMap.put(IP, comJDBC);
        }
        return comJDBC.executeSql(sql);
    }

    /**
     * 获得一个jdbc的连接conn
     * @return
     */
    public static Connection getConn(String user,String password,String IP,String port,String instance){
        ComJDBC comJDBC = (ComJDBC) JDBCMap.get(IP+instance);
        if(comJDBC == null){
            comJDBC = new ComJDBC(user,password,IP,port,instance);
            JDBCMap.put(IP+instance, comJDBC);
        }
        return comJDBC.getConn();
    }
	
}
