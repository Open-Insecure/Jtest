package com.br.dong.jdbc.mysql.connect;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用此连接数据库
 */
public class GetComJDBC {
	
	public static Map JDBCMap = new HashMap();

	public static List getExecuteQuery(String user,String password,String IP,String port,String instance, String sql) throws NullPointerException {
        ComJDBC comJDBC = (ComJDBC) JDBCMap.get(IP);
        if (comJDBC == null) {
            comJDBC = new ComJDBC(user,password,IP,port,instance);
            JDBCMap.put(IP, comJDBC);
        }
        System.out.println("comJDBC:" + sql);
        return comJDBC.getExecuteQuery(sql);
    }


	
}
