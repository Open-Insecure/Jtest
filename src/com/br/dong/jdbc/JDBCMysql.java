package com.br.dong.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** 
 * @author  hexd
 * 创建时间：2014-7-25 下午1:19:48 
 * 类说明 
 */
public class JDBCMysql {
	 //app_name为创建的应用名
	public static void main(String[] args) {

//	     String URL="jdbc:mysql://127.0.0.1:3306/bdlogin?useUnicode=true&characterEncoding=UTF-8";
	     String URL="jdbc:mysql://107.150.21.148:3306/tanzi?useUnicode=true&characterEncoding=UTF-8";
	    
	     String Username="hexd";
	     String Password="hexd";
	     String Driver="com.mysql.jdbc.Driver";
	    try{
	    Class.forName(Driver).newInstance();
	    Connection con=DriverManager.getConnection(URL,Username,Password);	
	    PreparedStatement pstmt = con.prepareStatement("select * from user");
	    ResultSet rs = (ResultSet) pstmt.executeQuery();
	    while(rs.next()){
	    	System.out.println(""+rs.getString("username"));
	    }
	    }catch(Exception e){
	      e.printStackTrace();
	    }
	}
    
    
}
