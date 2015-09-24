package com.br.dong.jdbc.mysql.connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-4-11
 * Time: 17:27
 * 通用的jdbc连接工具类
 */
public class ComJDBC {
    private String quDong;//第三方驱动jar
    private String users;//登录用户名
    private String passwords;//登录密码
    private String urls;//链接url
    private Connection conn;//一个数据库链接

    /***
     * 构造方法
     * @param user
     * @param password
     * @param IP
     * @param port
     * @param instance
     */
    public ComJDBC(String user,String password,String IP,String port,String instance)
    {
        quDong="com.mysql.jdbc.Driver";
        users=user;
        passwords=password;
        urls="jdbc:mysql://"+IP+":"+port+"/"+instance+"?useUnicode=true&characterEncoding=UTF-8";   //:改成/
        try {
            jiaZaiQuDong();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        try
        {
            conn= DriverManager.getConnection(urls, users, passwords);
            System.out.println("Create connection succ");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("Create connection faild, urls=" + urls);
        }
    }

    /***
     * 加载驱动
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void jiaZaiQuDong() throws IllegalAccessException, InstantiationException {
        try
        {
            //DriverManager 驱动管理器进行链接
            Class.forName(quDong).newInstance();

        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Load mysql driver faild");
        }
    }

    /**
     * 执行数据库操作
     * @param sql sql语句
     * @return 返回操作影响的数据库条数
     */
    public int executeSql(String sql){

        System.out.println("SQL:"+sql);
        int rs=0;//返回的是更新的条数
        Statement st=null;
        try{
            st=conn.createStatement();
            rs=st.executeUpdate(sql);
        }catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }  finally{
            if(st!=null){
                try {
                    st.close();
                    System.out.println("关闭 st>>>" + new java.util.Date());
                } catch (SQLException e) {
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        }
        return rs;
    }

    /**
     * 执行返回结果的sql的
     * @param sql
     * @return
     * @throws Exception 当连接不上的时候 抛出异常
     */
    public List getExecuteQuery (String sql) throws NullPointerException{
        List list;
        list = new ArrayList();
        ResultSet rs=null;
        Statement st=null;
        try {
            st=conn.createStatement();  //连接不上的时候conn为null 抛出空指针异常
            Map map;
            rs=st.executeQuery(sql);

            while(rs.next())
            {
                map = new HashMap();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                {
                    if("ACTIVE_TIME".endsWith(rs.getMetaData().getColumnName(i))){
                        map.put(rs.getMetaData().getColumnName(i), String.valueOf(rs.getLong(i)));
                    }else{
                        map.put(rs.getMetaData().getColumnName(i), rs.getString(i));
                    }
                }
                list.add(map);
            }
            System.out.println("总共"+list.size() + "条>>>>>" + list.toString());
            System.out.println("Select End >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }finally{

            if(rs!=null){
                try {
                    rs.close();
                    System.out.println("关闭 rs>>>" + new java.util.Date());
                } catch (SQLException e) {
                    System.out.println(e);
                    e.printStackTrace();
                }

            }
            if(st!=null){
                try {
                    st.close();
                    System.out.println("关闭 st>>>" + new java.util.Date());
                } catch (SQLException e) {
                    System.out.println(e);
                    e.printStackTrace();
                }
            }

        }
        return list;
    }




    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
