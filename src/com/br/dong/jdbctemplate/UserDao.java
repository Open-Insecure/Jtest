package com.br.dong.jdbctemplate;
/** 
 * @author  hexd
 * 创建时间：2014-8-1 下午2:48:03 
 * 类说明 
 * 主要使用做来批量操作数据库
 */
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.br.dong.utils.DateUtil;


public class UserDao {
	//方法1实现JdbcTemplate 实例
//   private JdbcTemplate jdbcT = (JdbcTemplate)SpringUtil
//             .getBean("jdbcTemplate");
    //方法2 依赖注入 在applicationContext.xml 注入JdbcTemplate实例到UserDao中的jdbcAop属性中
    private JdbcTemplate jdbcAop;
    public JdbcTemplate getJdbcAop() {
		return jdbcAop;
	}
	public void setJdbcAop(JdbcTemplate jdbcAop) {
		this.jdbcAop = jdbcAop;
	}
	//查找所有
//    public List findALL() {
//        String sql = "select * from user";
//        return jdbcT.queryForList(sql);        
//    }
    public List findAll2(){
        String sql = "select * from user";
        return jdbcAop.queryForList(sql);      
    }
    //进行批量操作
    public void insertUser(List<Demo> list){
    	final List<Demo> batchlist=list;
    	String sql="insert into demo(demoName,demoDate,demoId) values(?,?,?)";
    	//批量插入
    	jdbcAop.batchUpdate(sql,new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				   String demoName=batchlist.get(i).getDemoName(); 
				   String demoDate=batchlist.get(i).getDemoDate(); 
				   String demoId=batchlist.get(i).getDemoId(); 
				    ps.setString(1, demoName); 
				    ps.setString(2, demoDate); 
				    ps.setString(3, demoId); 
				  //每1000条进行事物提交  
                    if (i%3000 == 0) {
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
    public static void main(String[] args) {       
    	 UserDao bd=(UserDao)SpringUtil.getBean("userDao");
//    	 System.out.println(bd.findALL().toString());
//    	List list=  bd.findAll2();
//    	Iterator it = list.iterator();  
//    	while(it.hasNext()) {  
//    	    Map userMap = (Map) it.next();  
//    	    System.out.print(userMap.get("name") + "\t");  
//    	    System.out.print(userMap.get("password") + "\t");  
//    	}  
    	 
    	 
    	
    	 //批量插入1000条
    	List<Demo> inList=new ArrayList<Demo>();
     	System.out.println();
     	for(int i=0;i<30000;i++){
    		inList.add(new Demo("name"+i,"date"+i,"id"+i));
     	}
     	 long start =DateUtil.getCurrentTimeMillis();
     	 System.out.println("开始进行插入...");
     	bd.insertUser(inList);
    	long end=DateUtil.getCurrentTimeMillis();
    	
    	System.out.println(DateUtil.getTaskTime(start, end));
    }
}
