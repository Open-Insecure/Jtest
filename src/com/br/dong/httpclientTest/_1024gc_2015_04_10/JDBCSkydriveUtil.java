package com.br.dong.httpclientTest._1024gc_2015_04_10;

import com.mysql.jdbc.Statement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-4-30
 * Time: 16:26
 */
public class JDBCSkydriveUtil {

    public static void main(String[] args) {
        List<GcBean> list=getAllCollectLogs();
        System.out.println(list.size());
    }
    //jdbcTemplate 依赖注入实例
    private static JdbcTemplate jdbcAop;
    public JdbcTemplate getJdbcAop() {
        return jdbcAop;
    }
    public void setJdbcAop(JdbcTemplate jdbcAop) {
        this.jdbcAop = jdbcAop;
    }
    //获得配置
    private static ApplicationContext ctx = new ClassPathXmlApplicationContext("com/br/dong/httpclientTest/_1024gc_2015_04_10/jdbc_skydrive.xml");
    //拿到bean
    public static Object getBean(String beanName){
        return ctx.getBean(beanName);
    }
    /**
     * 根据id查找
     */
    public static GcBean getCollectById(String id){
        String sql="select *  from   collectlogs  where id="+id;
        return (GcBean)jdbcAop.queryForObject(sql,new GcBeanMapper());
    }
    /**
     * 检查该资源是否已经被采集过
     */
    public static List checkResourceUrl(String resourceUrl){
        String sql="select * from collectlogs where resourceUrl=? " ;
        return jdbcAop.queryForList(sql, resourceUrl);
    }

    /**
     * 查询所有的采集日志
     * @return
     */
    public static List getAllCollectLogs(){
        String sql="select * from collectlogs";
        return jdbcAop.queryForList(sql);
    }

    /**
     * 插入一条文件信息到网盘的数据库中,返回id
     */
    public static int insertSkydriveFileInfo(final FileBaseInfo bean){
        final String sql="insert into filebaseinfo(userId,username,uploadTime,fileName,fileSize,fileType,fileFlag,downloads) values(?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcAop.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                int i = 0;
                PreparedStatement ps=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//id
//                PreparedStatement ps=connection.prepareStatement(sql, new String[] {"id"});//id
                ps.setInt(1,bean.getUserId());
                ps.setString(2,bean.getUserName());
                ps.setString(3,bean.getUploadTime());
                ps.setString(4,bean.getFileName());
                ps.setString(5,bean.getFileSize());
                ps.setString(6,bean.getFileType());
                ps.setString(7,bean.getFileFlag());
                ps.setInt(8,bean.getDownloads());
                return ps;
            }
        },keyHolder);
        return keyHolder.getKey().intValue();
    }
    /**
     * 插入一条资源信息  插入成功后 返回id
     * @param bean
     */
    public static int insertResource(final GcBean bean){
        final String sql="insert into collectlogs(resourceSite,type,name,content,resourceUrl,origDownloadUrl,downloadUrl,imgUrls,updatetime,section) values(?,?,?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        //sql, new Object[] {bean.getResourceSite(),bean.getType(),bean.getName(),bean.getContent(),bean.getResourceUrl(),bean.getOrigDownloadUrl(),bean.getDownloadUrl(),bean.getImgUrls(),bean.getUpdatetime()}
        jdbcAop.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                int i = 0;
                PreparedStatement ps=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//id
//                PreparedStatement ps=connection.prepareStatement(sql, new String[] {"id"});//id
                ps.setString(1,bean.getResourceSite());
                ps.setString(2,bean.getType());
                ps.setString(3,bean.getName());
                ps.setString(4,bean.getContent());
                ps.setString(5,bean.getResourceUrl());
                ps.setString(6,bean.getOrigDownloadUrl());
                ps.setString(7,bean.getDownloadUrl());
                ps.setString(8,bean.getImgUrls());
                ps.setString(9,bean.getUpdatetime());
                ps.setString(10,bean.getSection());
                return ps;
            }
        },keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * 批量插入资源信息
     */
    public static  void insertResources(List<GcBean> list){
        final List<GcBean> batchlist=list;
        String sql="insert into collectlogs(resourceSite,type,name,content,resourceUrl,origDownloadUrl,downloadUrl,imgUrls,updatetime) values(?,?,?,?,?,?,?,?,?)";
        //批量插入
        jdbcAop.batchUpdate(sql,new BatchPreparedStatementSetter() {
            //此为匿名内部类的变量
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                // TODO Auto-generated method stub
                ResultSet rs;
                String resourceSite=batchlist.get(i).getResourceSite();
                String type=batchlist.get(i).getType();
                String name=batchlist.get(i).getName();
                String content=batchlist.get(i).getContent();
                String resourceUrl=batchlist.get(i).getResourceUrl();
                String origDownloadUrl=batchlist.get(i).getOrigDownloadUrl();
                String downloadUrl=batchlist.get(i).getDownloadUrl();
                String imgUrls=batchlist.get(i).getImgUrls();
                String updatetime=batchlist.get(i).getUpdatetime();
                ps.setString(1, resourceSite);
                ps.setString(2, type);
                ps.setString(3, name);
                ps.setString(4, content);
                ps.setString(5, resourceUrl);
                ps.setString(6, origDownloadUrl);
                ps.setString(7, downloadUrl);
                ps.setString(8, imgUrls);
                ps.setString(9, updatetime);
                //每1000条进行事物提交
                if (i%1000 == 0) {
                    System.out.println("进行一次插入操作");
                    ps.executeBatch(); //执行prepareStatement对象中所有的sql语句
                }
            }

            public int getBatchSize() {
                // TODO Auto-generated method stub
                return batchlist.size();
            }
        });
    }
}
class GcBeanMapper implements RowMapper {


    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        GcBean bean=new GcBean();
        bean.setResourceSite(rs.getString("resourceSite"));
        bean.setName(rs.getString("name"));
        bean.setType(rs.getString("type"));
        bean.setContent(rs.getString("content"));
        bean.setResourceUrl(rs.getString("resourceUrl"));
        bean.setOrigDownloadUrl(rs.getString("origDownloadUrl"));
        bean.setDownloadUrl(rs.getString("downloadUrl"));
        bean.setImgUrls(rs.getString("imgUrls"));
        bean.setUpdatetime(rs.getString("updatetime"));
        return bean;
    }
}
