package com.br.dong.httpclientTest.youbb.net;

import com.br.dong.jdbc.mysql.connect.GetComJDBC;

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
        String sql="insert into video (title,vkey,imgName,videoName,infotime,updatetime,rate,views,favourite,viewAuthority) values ('"+bean.getTitle()+"','"+bean.getVkey()+"','"+bean.getImgName()+"','"+bean.getVideoName()+"','"+bean.getInfotime()+"','"+bean.getUpdatetime()+"','"+bean.getRate()+"','"+bean.getViews()+"','"+bean.getFavourite()+"','"+bean.getViewAuthority()+"')";
       return GetComJDBC.ExecuteSql(DATABASE_USER, DATABASE_PASSWORD, DATABASE_HOST, DATABASE_PORT, DATABASE_INSTANCE, sql);//测试jdbc连接查询
    }

    public static void main(String[] args) {
        YoubbBean bean=new YoubbBean("test","10064","10064.jpg","10064.flv","00:60");
        System.out.println("操作影响条数:"+insertVideoInfo(bean));
    }
}
