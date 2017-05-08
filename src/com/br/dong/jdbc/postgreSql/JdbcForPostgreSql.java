package com.br.dong.jdbc.postgreSql;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.*;
import java.util.Date;

/***
 * jdbc链接postgreSql工具
 */
public class JdbcForPostgreSql {
	private static final Log log= LogFactory.getLog(JdbcForPostgreSql.class);
	private String quDong;
	private String users;
	private String passwords;
	private String urls;
	private Properties prop = new Properties();
	Connection conn=null;

	public JdbcForPostgreSql(String user, String password, String IP, String port, String instance)
	{

			quDong="org.postgresql.Driver";
			users=user;
			passwords=password;
			urls="jdbc:postgresql://"+IP+":"+port+"/"+instance+"";
			prop.put("user", user);
			prop.put("password", password);
			prop.put("user", user);
			log.info("Create " + urls + " User: " + user);
			jiaZaiQuDong();


	}


	public void jiaZaiQuDong()
	{
		try
		{
			Class.forName(quDong);

		}
		catch (ClassNotFoundException e)
		{
			log.error("Load oracle driver faild");
		}
	}

	public Connection jianLianJie()
	{

		try
		{
			//conn=DriverManager.getConnection(urls, users, passwords);
			conn = DriverManager.getConnection(urls, prop);
			log.info("Create connection succ");
			log.info("prop============" + prop + "");
			return conn;
		}
		catch (SQLException e)
		{
			log.error("Create connection faild, urls="+urls);
			log.info("prop============" + prop + "");
		}
		return null;
	}
	public Statement shengStmt( Connection conn )
	{
		try
		{
			Statement stmt=conn.createStatement();
			return stmt;
		}
		catch (SQLException e)
		{
			log.error("Create statment faild");
		}
		return null;

	}
	public List getPagingExecuteQuery(String sql ,String page,String rows){
		log.info("SQL:"+sql);
		List list;
		list = new ArrayList();
		ResultSet rs=null;
		Statement st=null;
		try {
			st=shengStmt(jianLianJie());
			Map map;
			rs=st.executeQuery("SELECT * " +
					"	        FROM  " +
					"				( SELECT A.*, ROWNUM RN  " +
					"                 FROM " +
					"					("+sql+") A  " +
					"				  WHERE ROWNUM <= ("+page+") * "+rows+" ) " +
					"		    WHERE RN >= ("+page+"-1) * "+rows+"");
			System.out.println("SELECT * " +
					"	        FROM  " +
					"				( SELECT A.*, ROWNUM RN  " +
					"                 FROM " +
					"					("+sql+") A  " +
					"				  WHERE ROWNUM <= ("+page+") * "+rows+" ) " +
					"		    WHERE RN >= ("+page+"-1) * "+rows+"");
			while(rs.next())
			{
				map = new HashMap();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
				{
					//System.out.println("----------- key: " + rs.getMetaData().getColumnName(i) + "    value: " + rs.getString(i));
					if("ACTIVE_TIME".endsWith(rs.getMetaData().getColumnName(i))){
						//System.out.println("-------------------------------------------------" + String.valueOf(rs.getDouble(i)));
						map.put(rs.getMetaData().getColumnName(i), String.valueOf(rs.getLong(i)));
					}else{
						map.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					}
				}

				list.add(map);

			}
			log.info("总共"+list.size()+"条>>>>>"+list.toString());
			log.info("Select End >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}finally{

				if(rs!=null){
					try {
						rs.close();
						log.info("关闭 rs>>>"+new Date());
					} catch (SQLException e) {
						log.error(e);
						e.printStackTrace();
					}

				}
				if(st!=null){
					try {
						st.close();
						log.info("关闭 st>>>"+new Date());
					} catch (SQLException e) {
						log.error(e);
						e.printStackTrace();
					}

				}
				if(conn!=null){
					try {
						conn.close();
						log.info("关闭 conn>>"+new Date());
					} catch (SQLException e) {
						log.error(e);
						e.printStackTrace();
					}

				}

		}
		return list;
	}

	public List getTotalListExecuteQuery(String sql ){
		log.info("SQL:"+sql);
		List list;
		list = new ArrayList();
		ResultSet rs=null;
		Statement st=null;
		try {
			st=shengStmt(jianLianJie());
			Map map;
			rs=st.executeQuery("SELECT count(*) count " +
					"	        FROM  " +
					"				( SELECT A.*, ROWNUM RN  " +
					"                 FROM " +
					"					("+sql+") A ) " );

			while(rs.next())
			{
				map = new HashMap();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
				{
					//System.out.println("----------- key: " + rs.getMetaData().getColumnName(i) + "    value: " + rs.getString(i));
					if("ACTIVE_TIME".endsWith(rs.getMetaData().getColumnName(i))){
						//System.out.println("-------------------------------------------------" + String.valueOf(rs.getDouble(i)));
						map.put(rs.getMetaData().getColumnName(i), String.valueOf(rs.getLong(i)));
					}else{
						map.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					}
				}

				list.add(map);

			}
			log.info("总共"+list.size()+"条>>>>>"+list.toString());

			log.info("Select End >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}finally{

				if(rs!=null){
					try {
						rs.close();
						log.info("关闭 rs>>>"+new Date());
					} catch (SQLException e) {
						log.error(e);
						e.printStackTrace();
					}

				}
				if(st!=null){
					try {
						st.close();
						log.info("关闭 st>>>"+new Date());
					} catch (SQLException e) {
						log.error(e);
						e.printStackTrace();
					}

				}
				if(conn!=null){
					try {
						conn.close();
						log.info("关闭 conn>>"+new Date());
					} catch (SQLException e) {
						log.error(e);
						e.printStackTrace();
					}

				}

		}
		return list;
	}
	public void executeSql(String sql){

		log.info("SQL:"+sql);
		ResultSet rs=null;
		Statement st=null;
		try{
			st=shengStmt(jianLianJie());
			 st.execute(sql);
			log.info(sql);
		}catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}  finally{
			if(rs!=null){
				try {
					rs.close();
					log.info("关闭 rs>>>"+new Date());
				} catch (SQLException e) {
					log.error(e);
					e.printStackTrace();
				}
			}
			if(st!=null){
				try {
					st.close();
					log.info("关闭 st>>>"+new Date());
				} catch (SQLException e) {
					log.error(e);
					e.printStackTrace();
				}

			}

		}
	}
	public  List getExecuteQuery (String sql){
		log.info("SQL:"+sql);
		List list;
		list = new ArrayList();
		ResultSet rs=null;
		Statement st=null;
		try {
			st=shengStmt(jianLianJie());
			Map map;
			rs=st.executeQuery(sql);
//			log.info(sql);

			while(rs.next())
			{
				map = new HashMap();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
				{
					//System.out.println("----------- key: " + rs.getMetaData().getColumnName(i) + "    value: " + rs.getString(i));
					if("ACTIVE_TIME".endsWith(rs.getMetaData().getColumnName(i))){
						//System.out.println("-------------------------------------------------" + String.valueOf(rs.getDouble(i)));
						map.put(rs.getMetaData().getColumnName(i), String.valueOf(rs.getLong(i)));
					}else{
						map.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					}
				}

				list.add(map);

			}
			log.info("总共"+list.size()+"条>>>>>");
			log.info("Select End >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}finally{

				if(rs!=null){
					try {
						rs.close();
						log.info("关闭 rs>>>"+new Date());
					} catch (SQLException e) {
						log.error(e);
						e.printStackTrace();
					}

				}
				if(st!=null){
					try {
						st.close();
						log.info("关闭 st>>>"+new Date());
					} catch (SQLException e) {
						log.error(e);
						e.printStackTrace();
					}

				}
				if(conn!=null){
					try {
						conn.close();
						log.info("关闭 conn>>"+new Date());
					} catch (SQLException e) {
						log.error(e);
						e.printStackTrace();
					}

				}

		}
		return list;
	}
	//
	public  Integer executeUpdate (String ddl){
		log.info("DDL:"+ddl);
		Integer rs=0;
		Statement st=null;
		try {
			st=shengStmt(jianLianJie());
			rs = st.executeUpdate(ddl);
		}catch(SQLException e){
			log.error(e);
			e.printStackTrace();

		}finally{
			log.info("Execute Result :" + rs);
			/*if(rs != null){
				try {
					rs.close();
					log.info("关闭 rs>>>"+new Date());
				} catch (SQLException e) {
					log.error(e);
					e.printStackTrace();
				}
			}*/
			if(st!=null){
				try {
					st.close();
					log.info("关闭 st>>>"+new Date());
				} catch (SQLException e) {
					log.error(e);
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
					log.info("关闭 conn>>"+new Date());
				} catch (SQLException e) {
					log.error(e);
					e.printStackTrace();
				}
			}
		}
		return rs;
	}
	//重写 无须返回值 抛出异常 调用方法处理异常
	public  void executeUpdate2  (String ddl) throws Exception{
		log.info("DDL:"+ddl);
		Integer rs=0;
		Statement st=null;
		try {
			st=shengStmt(jianLianJie());
			rs = st.executeUpdate(ddl);
		}catch(SQLException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			log.info("Execute Result :" + rs);
			/*if(rs != null){
				try {
					rs.close();
					log.info("关闭 rs>>>"+new Date());
				} catch (SQLException e) {
					log.error(e);
					e.printStackTrace();
				}
			}*/
			if(st!=null){
				try {
					st.close();
					log.info("关闭 st>>>"+new Date());
				} catch (SQLException e) {
					log.error(e);
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
					log.info("关闭 conn>>"+new Date());
				} catch (SQLException e) {
					log.error(e);
					e.printStackTrace();
				}
			}
		}
	}


	public  void proc(String proc) throws SQLException{
		log.info(proc);
		CallableStatement cstmt=null;
		try {
			cstmt=jianLianJie().prepareCall(proc);
			cstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

		if(conn!=null){
			try {
				conn.close();
				log.info("关闭 conn>>"+new Date());
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
			}

		}

	}

	public String procedure(String proc) throws SQLException{

		log.info(proc);
		CallableStatement cstmt=null;

			cstmt=jianLianJie().prepareCall(proc);
			cstmt.setInt(1,20);
			cstmt.registerOutParameter(2,Types.VARCHAR);
			cstmt.registerOutParameter(3,Types.VARCHAR);
			cstmt.registerOutParameter(4,Types.VARCHAR);
			cstmt.execute();
			String testPrint= cstmt.getString(2);
			String testPrint2= cstmt.getString(3);
			String testPrint3= cstmt.getString(4);
		    System.out.println("=testPrint=is="+testPrint+testPrint2+testPrint3);


		if(conn!=null){
			try {
				conn.close();
				log.info("关闭 conn>>"+new Date());
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
			}

		}

		return testPrint;
	}


	public  void unNamSql(String unnamesql){
		log.info("unnamesql:"+unnamesql);
		PreparedStatement  ps=null;
		try {
			ps=jianLianJie().prepareCall(unnamesql);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(conn!=null){
			try {
				conn.close();
				log.info("关闭 conn>>"+new Date());
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
			}

		}

	}


	
	
}
