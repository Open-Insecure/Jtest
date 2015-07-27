package com.br.dong.httpclientTest.youbb.net;

import com.br.dong.jdbc.mysql.connect.GetComJDBC;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-07-17
 * Time: 14:42
 * 插入94lu数据库中的video表
 */
public class YoubbJdbcUtil {
    private static String DATABASE_USER="root";//数据库登录用户
    private static String DATABASE_PASSWORD="system";//数据库登录密码
    private static String DATABASE_HOST="127.0.0.1";//数据库ip
    private static String DATABASE_PORT="3306";//数据库端口
    private static String DATABASE_INSTANCE="94lu";//数据库实例

    /**
     * 插入信息
     * @param bean
     */
    public static int insertVideoInfo(YoubbBean bean){
        String sql="insert into video (title,vkey,imgName,videoName,infotime,updatetime,rate,views,favourite,viewAuthority,tags) values ('"+bean.getTitle()+"','"+bean.getVkey()+"','"+bean.getImgName()+"','"+bean.getVideoName()+"','"+bean.getInfotime()+"','"+bean.getUpdatetime()+"','"+bean.getRate()+"','"+bean.getViews()+"','"+bean.getFavourite()+"','"+bean.getViewAuthority()+"','"+bean.getTags()+"')";
       return GetComJDBC.ExecuteSql(DATABASE_USER, DATABASE_PASSWORD, DATABASE_HOST, DATABASE_PORT, DATABASE_INSTANCE, sql);//测试jdbc连接查询
    }

    /**
     * 检查库中是否已经有存在的信息
     * @param vkey
     * @return true-表示当前库中没有此条记录，可以插入新纪录 false-表示当前库中有此条记录，不可以插入
     */
    public static Boolean checkVkey(String vkey){
        String sql="select * from video where vkey='"+vkey+"'";
        List list=GetComJDBC.getExecuteQuery(DATABASE_USER, DATABASE_PASSWORD, DATABASE_HOST, DATABASE_PORT, DATABASE_INSTANCE, sql);
        if(list==null||list.size()==0) return true;//没有存在
        return false;//有已经存在
    }
    public static void main(String[] args) {
        YoubbBean bean=new YoubbBean("test","10033","10033.jpg","10064.flv","00:60");
//        System.out.println("操作影响条数:"+insertVideoInfo(bean));
        System.out.println(checkVkey("1003333"));
    }
}
