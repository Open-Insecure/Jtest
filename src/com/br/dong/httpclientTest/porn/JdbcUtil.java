package com.br.dong.httpclientTest.porn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.br.dong.httpclientTest.sis001.SisTorrentBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.br.dong.utils.DateUtil;

import sun.applet.Main;


/** 
 * @author  hexd
 * 创建时间：2014-8-19 上午10:46:29 
 * 类说明 
 */
public class JdbcUtil {
	//jdbcTemplate 依赖注入实例
	private static JdbcTemplate jdbcAop;
	public JdbcTemplate getJdbcAop() {
		return jdbcAop;
	}
	public void setJdbcAop(JdbcTemplate jdbcAop) {
		this.jdbcAop = jdbcAop;
	}
	//获得配置
	private static ApplicationContext  ctx = new ClassPathXmlApplicationContext("com/br/dong/httpclientTest/porn/jdbcTemplate.xml");
	//拿到bean
	public static Object getBean(String beanName){
         return ctx.getBean(beanName);
    }
    /**
     * 批量插入代理信息
     * @param list
     */
    public static void insertBatch(List<ProxyBean> list){
    	final List<ProxyBean> batchlist=list;
    	String sql="insert into proxy(ip,port,type,updatetime) values(?,?,?,?)";
    	//批量插入
    	jdbcAop.batchUpdate(sql,new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				   String ip=batchlist.get(i).getIp(); 
				   int prot=batchlist.get(i).getPort();
				   String type=batchlist.get(i).getType(); 
				   String updatetime=batchlist.get(i).getUpdatetime(); 
				    ps.setString(1, ip); 
				    ps.setInt(2, prot);
				    ps.setString(3, type); 
				    ps.setString(4, updatetime); 
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

    /**
     * 删除指定代理
     * @param proxy
     */
    public static void deleteProxyByIp(ProxyBean proxy){
        String sql="delete from proxy where ip=?";
        System.out.println("delete "+proxy.toString()+" from proxy table");
       jdbcAop.update(sql, new Object[]{proxy.getIp()});
    }

    /**
     *清空表代理表
     */
    public static void deleteAll(){
        String sql="delete from proxy";
        jdbcAop.execute(sql);
    }

    /**
     * 检查代理是否已经存在于数据库中
     */
    public static List checkProxy(String ip){
        String sql="select * from proxy where ip=? " ;
        return jdbcAop.queryForList(sql,ip);
    }

    /**
     * 随机查找一条代理数据
     */
    public static ProxyBean getProxy(){
        String sql="select *, rand() as random from proxy order by random limit 1";
        return (ProxyBean)jdbcAop.queryForObject(sql,new ProxyRowMapper());
    }

    /**
     * 查找视频的数据
     * @param index 开始的下标
     * @param data  查找多少条数据
     * @return
     * 示例 0,20 表示从第0条查找到第19条 总共查找20条数据
     */
    public static List getVedios(int index,int data){
      String sql="select * from vedio order by updatetime and flag=0 desc limit "+index+","+data;
        return jdbcAop.queryForList(sql);
    }

    /**
     * 随机查找一条视频数据
     */
    public static VedioBean getVedioInfo(){
        String sql="select *, rand() as random from vedio order by random limit 1";
        return (VedioBean)jdbcAop.queryForObject(sql,new VedioRowMapper());
    }

    /**
     * 插入一条视频信息
     * @param video
     */
    public static void insertVideo(VedioBean video){
        String sql="insert into vedio(title,preImgSrc,vedioUrl,infotime,videoId,updatetime,flag) values(?,?,?,?,?,?,?)";
        jdbcAop.update(sql, new Object[] {video.getTitle(),video.getPreImgSrc(),video.getVedioUrl(),video.getInfotime(),video.getVideoId(),video.getUpdatetime(),video.getFlag()});
    }
    /**
     * 批量插入视频信息
     * */
    public static void insertVedioBatch(List<VedioBean> list){
        final List<VedioBean> batchlist=list;
        String sql="insert into vedio(title,preImgSrc,vedioUrl,infotime,videoId,updatetime,flag,type) values(?,?,?,?,?,?,?,?)";
        //批量插入
        jdbcAop.batchUpdate(sql,new BatchPreparedStatementSetter() {
            //此为匿名内部类的变量
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                // TODO Auto-generated method stub
                ResultSet rs;
                String title=batchlist.get(i).getTitle();
                String preImgSrc=batchlist.get(i).getPreImgSrc();
                String vedioUrl=batchlist.get(i).getVedioUrl();
                String infotime=batchlist.get(i).getInfotime();
                String videoId=batchlist.get(i).getVideoId();
                String updatetime=batchlist.get(i).getUpdatetime();
                int flag=batchlist.get(i).getFlag();
                String type=batchlist.get(i).getType();
                ps.setString(1, title);
                ps.setString(2, preImgSrc);
                ps.setString(3, vedioUrl);
                ps.setString(4, infotime);
                ps.setString(5, videoId);
                ps.setString(6, updatetime);
                ps.setInt(7,flag);
                ps.setString(8,type);
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

    /**********************************************************
     * 对应Sis001的数据库
     */
    /**
     * 针对urls表
     */
    /**
     * 查找种子列表中是否有相同的记录
     * @param url
     * @return
     */
    public static List checkSameUrl(String url){
        String sql="select * from torrents where url=?";
        return jdbcAop.queryForList(sql,url);
    }

    /**
     * 根据种子所属标记进行查询
     * @return
     */
    public static List getTorrentsByFlag(String []temp){
        String sql="select * from torrents where updatetime=? and temp=?";
        return jdbcAop.queryForList(sql,temp[0],temp[1]);
    }

    /**
     * 插入种子信息岛种子列表中
     * @param bean
     */
    public static void insertTorrent(SisTorrentBean bean){
       String sql="insert into torrents(flag,type,title,url,size,torrentUrl,time,picUrl,message,updatetime,temp) values(?,?,?,?,?,?,?,?,?,?,?)";
        try{
            jdbcAop.update(sql,new Object[]{bean.getFlag(),bean.getType(),bean.getTitle(),bean.getUrl(),bean.getSize(),bean.getTorrentUrl(),bean.getTime(),bean.getPicUrl(),bean.getMessage(),bean.getUpdatetime(),bean.getTemp()});

        }catch (DataIntegrityViolationException e){

        }
    }

    //测试
    public static void main(String[] args) {
        String temp[]={"2014-10-27","bt亚洲无码转帖"} ;
       List rows=getTorrentsByFlag(temp);
        for(int i=0;i<rows.size();i++){
            Map map= (Map) rows.get(i);
            System.out.println(map.toString());
        }
	}
		
}
