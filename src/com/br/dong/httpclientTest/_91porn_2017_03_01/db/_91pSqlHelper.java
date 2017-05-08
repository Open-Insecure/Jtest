package com.br.dong.httpclientTest._91porn_2017_03_01.db;

import com.br.dong.jdbc.postgreSql.GetComJDBCForPostgreSql;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.httpclientTest._91porn_2017_03_01.db
 * AUTHOR: hexOr
 * DATE :2017/3/5 15:44
 * DESCRIPTION:
 */
public class _91pSqlHelper {
    /** 单引号 Single quotation marks */
    public final static String SQM="'";
    public final static String CHECK_EXIST_VIDEO_SQL=" select * from videos where title = ";
    public final static String INSERT_VIDEO_SQL=" insert into videos (title,preimgsrc,videopageurl,infotime,updatetime,author,description,videopath,imgpath) " +
                                                              "values";

    /***
     * 检查该视频是否已经存在数据库中
     * @param title
     * @return true 存在 false 不存在
     */
    public static Boolean checkExistVideo(String title){
        String sql=CHECK_EXIST_VIDEO_SQL+SQM+title+SQM;
        List list=GetComJDBCForPostgreSql.getExecuteQuery(sql);
        if(null!=list&&list.size()>0){
            return true;
        }
        return false;
    }

    /**
     * 插入视频信息
     * @param title
     * @param preimgsrc
     * @param videopageurl
     * @param infotime
     * @param updatetime
     * @param author
     * @param description
     * @param videopath
     * @param imgpath
     */
    public static void addVideo(String title,String preimgsrc,String videopageurl,String infotime,String updatetime,String author,String description,String videopath,String imgpath){
        String sql=INSERT_VIDEO_SQL+"('"+title+"','"+preimgsrc+"','"+videopageurl+"','"+infotime+"','"+updatetime+"','"+author+"','"+description+"','"+videopath+"','"+imgpath+"')";
        GetComJDBCForPostgreSql.ExecuteSql(sql);
    }
    public static void main(String[] args) {
        _91pSqlHelper.addVideo("tttt","tttt","tttt","tttt","tttt","tttt","tttt","tttt","tttt");
        System.out.println(_91pSqlHelper.checkExistVideo("test"));
    }
}
