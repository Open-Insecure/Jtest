package com.br.dong.httpclientTest.porn;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
	 //进行批量插入
    public static void insertBatch(List<ProxyBean> list){
    	final List<ProxyBean> batchlist=list;
    	String sql="insert into proxy(ip,port,type,updatetime) values(?,?,?,?)";
    	//批量插入
    	jdbcAop.batchUpdate(sql,new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				   String ip=batchlist.get(i).getIp(); 
				   String prot=batchlist.get(i).getPort(); 
				   String type=batchlist.get(i).getType(); 
				   String updatetime=batchlist.get(i).getUpdatetime(); 
				    ps.setString(1, ip); 
				    ps.setString(2, prot); 
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
     *清空表
     */
    public static void deleteAll(){
        String sql="delete from proxy";
        jdbcAop.execute(sql);
    }

    /**
     * 随机查找一条数据
     */
    public static ProxyBean getProxy(){
        String sql="select *, rand() as random from proxy order by random limit 1";
        return (ProxyBean)jdbcAop.queryForObject(sql,new ProxyRowMapper());
    }


    /**
     * 随机查找一条数据
     */
    public static VedioBean getVedioInfo(){
        String sql="select *, rand() as random from vedio order by random limit 1";
        return (VedioBean)jdbcAop.queryForObject(sql,new VedioRowMapper());
    }
    /**
     * 批量插入视频信息
     * */
    public static void insertVedioBatch(List<VedioBean> list){
        final List<VedioBean> batchlist=list;
        String sql="insert into vedio(title,preImgSrc,vedioUrl,infotime,flag) values(?,?,?,?,?)";
        //批量插入
        jdbcAop.batchUpdate(sql,new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                // TODO Auto-generated method stub
                String title=batchlist.get(i).getTitle();
                String preImgSrc=batchlist.get(i).getPreImgSrc();
                String vedioUrl=batchlist.get(i).getVedioUrl();
                String infotime=batchlist.get(i).getInfotime();
                int flag=batchlist.get(i).getFlag();
                ps.setString(1, title);
                ps.setString(2, preImgSrc);
                ps.setString(3, vedioUrl);
                ps.setString(4, infotime);
                ps.setInt(5,flag);
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

    //测试插入
    public static void main(String[] args) {
//		List<ProxyBean> list=new ArrayList<ProxyBean>();
//		for(int i=0;i<100;i++){
//			list.add(new ProxyBean("ip","port","type",DateUtil.getCurrentDay()));
//		}
//		insertBatch(list);
		
	}
		
}
