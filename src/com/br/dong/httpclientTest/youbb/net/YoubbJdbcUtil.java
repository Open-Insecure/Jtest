package com.br.dong.httpclientTest.youbb.net;

import com.br.dong.jdbc.mysql.connect.GetComJDBC;
import com.br.dong.utils.PropertiesUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-07-17
 * Time: 14:42
 * 插入94lu数据库中的video表
 */
public class YoubbJdbcUtil {
    private static PropertiesUtil propertiesUtil=PropertiesUtil.getInstance("/com/br/dong/httpclientTest/youbb/net/properties/config.properties");//读取配置文件
    private static String DATABASE_USER=propertiesUtil.getPropValue("DATABASE_USER");//数据库登录用户
    private static String DATABASE_PASSWORD=propertiesUtil.getPropValue("DATABASE_PASSWORD");//数据库登录密码
    private static String DATABASE_HOST=propertiesUtil.getPropValue("DATABASE_HOST");//数据库ip
    private static String DATABASE_PORT=propertiesUtil.getPropValue("DATABASE_PORT");//数据库端口
    private static String DATABASE_INSTANCE=propertiesUtil.getPropValue("DATABASE_INSTANCE");//数据库实例

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
