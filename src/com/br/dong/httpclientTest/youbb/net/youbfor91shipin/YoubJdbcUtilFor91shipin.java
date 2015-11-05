package com.br.dong.httpclientTest.youbb.net.youbfor91shipin;

import com.br.dong.httpclientTest.youbb.net.YoubbBean;
import com.br.dong.jdbc.mysql.connect.GetComJDBC;
import com.br.dong.utils.PropertiesUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-10-12
 * Time: 13:55
 */
public class YoubJdbcUtilFor91shipin {
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
    public static int insertVideoInfo(YoubBeanFor91shipin bean){
        String sql="insert into vedio (title,preImgSrc,vedioUrl,infotime,updatetime,videoId,flag,rate,views,favourite,type) values ('"+bean.getTitle()+"','"+bean.getPreImgSrc()+"','"+bean.getVedioUrl()+"','"+bean.getInfotime()+"','"+bean.getUpdatetime()+"','"+bean.getVideoId()+"','"+bean.getFlag()+"','"+bean.getRate()+"','"+bean.getViews()+"','"+bean.getFavourite()+"','"+bean.getType()+"')";
        return GetComJDBC.ExecuteSql(DATABASE_USER, DATABASE_PASSWORD, DATABASE_HOST, DATABASE_PORT, DATABASE_INSTANCE, sql);//测试jdbc连接查询
    }

    /**
     * 检查库中是否已经有存在的信息
     * @param vkey
     * @return true-表示当前库中没有此条记录，可以插入新纪录 false-表示当前库中有此条记录，不可以插入
     */
    public static Boolean checkVkey(String vkey){
        String sql="select * from vedio where videoId='"+vkey+"'";
        List list=GetComJDBC.getExecuteQuery(DATABASE_USER, DATABASE_PASSWORD, DATABASE_HOST, DATABASE_PORT, DATABASE_INSTANCE, sql);
        if(list==null||list.size()==0) return true;//没有存在
        return false;//有已经存在
    }
    public static void main(String[] args) {
        System.out.println(checkVkey("111"));
    }
}
