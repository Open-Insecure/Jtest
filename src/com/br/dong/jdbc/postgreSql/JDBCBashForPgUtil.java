package com.br.dong.jdbc.postgreSql;


import com.br.dong.utils.PropertiesUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.hexor.util.jdbc.postgreSql
 * AUTHOR: hexOr
 * DATE :2016-09-20 18:42
 * DESCRIPTION:只针对本地postgresql数据库的批量插入
 */
public class JDBCBashForPgUtil {
    private static final Log log = LogFactory.getLog(JDBCBashForPgUtil.class);
    private static PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("/com/br/dong/jdbc/postgreSql/config/pg_config.properties");//读取配置文件
    private static final String ipaddress=(String) propertiesUtil.getPropValue("database.local.ipaddress");
    private static final String username=(String)propertiesUtil.getPropValue("database.local.username");
    private static final String password=(String)propertiesUtil.getPropValue("database.local.password");
    private static final String port=(String)propertiesUtil.getPropValue("database.local.port");
    private static final String instance=(String)propertiesUtil.getPropValue("database.local.instance");

    protected final String driver = "org.postgresql.Driver";
    private String urls;
    private Properties prop = new Properties();

    /**
     * 不能单例 因为需要断开链接
     *
     * @return
     */
    public static JDBCBashForPgUtil getInstance() {

        return new JDBCBashForPgUtil();
    }

    /**
     * 私有化构造方法
     */
    private JDBCBashForPgUtil() {
        urls = "jdbc:postgresql://" + ipaddress + ":" + port + "/" + instance + "";
        prop.put("user", username);
        prop.put("password", password);
        prop.put("user", username);
        log.info("Create " + urls + " User: " + username);
        jiaZaiQuDong();
    }

    /**
     * 加载class文件驱动
     */
    public void jiaZaiQuDong() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            log.error("Load oracle driver faild");
        }
    }

    /**
     * 创建链接
     */
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(urls, prop);
        } catch (Exception e) {
            log.error("connect database faild ,urls=" + urls + "prop=" + prop + " ");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 处理v&sqlplan批量插入
     *
     * @param con          本地pg库链接
     * @param dataBaseName 数据库名称
     * @param dateMinute   当前批量插入的统一时间
     * @param list         数据集合
     * @throws Exception
     */
//    public PreparedStatement handlerV$Sqlplan(Connection con, String dateMinute, String dataBaseName, List<Map> list) throws Exception {
//        if (null == list) return null;
//        String sql = "insert into V$SQL_PLAN(sysname,hash_value,sql_id,plan_hash_value,child_number,timestamp,operation,options,object_node,object,object_owner," +
//                "object_name,object_alias,object_type,optimizer,vid,parent_id,depth,position,search_columns,cost,cardinality,bytes,other_tag,partition_start," +
//                "partition_stop,partition_id,other,distribution,cpu_cost,io_cost,temp_space,access_predicates,filter_predicates,projection,time,qblock_name,cjtime,uni_sql_id,username)" +
//                "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//        PreparedStatement pst = null;
//        pst = con.prepareStatement(sql);
//        if (null == pst) {
//            log.error("create PreparedStatement faild for" + dataBaseName);
//            return null;
//        }
//
//        for (Map map : list) {
//            /**为了防止对方oracle数据库编码不一致导致的乱码问题，统一替换系统名称*/
//            V$SQL_PLAN v$SQL_plan = V$SQL_PLAN.buildByGetFromDb(map, dateMinute, dataBaseName);
//            pst.setString(1, v$SQL_plan.getSysName());
//            pst.setLong(2, v$SQL_plan.getHash_value());
//            pst.setString(3, v$SQL_plan.getSql_id());
//            pst.setLong(4, v$SQL_plan.getPlan_hash_value());
//            pst.setLong(5, v$SQL_plan.getChild_number());
//            pst.setString(6, v$SQL_plan.getTimestamp());
//            pst.setString(7, v$SQL_plan.getOperation());
//            pst.setString(8, v$SQL_plan.getOptions());
//            pst.setString(9, v$SQL_plan.getObject_node());
//            pst.setLong(10, v$SQL_plan.getObject());
//            pst.setString(11, v$SQL_plan.getObject_owner());
//            pst.setString(12, v$SQL_plan.getObject_name());
//            pst.setString(13, v$SQL_plan.getObject_alias());
//            pst.setString(14, v$SQL_plan.getObject_type());
//            pst.setString(15, v$SQL_plan.getOptimizer());
//            pst.setLong(16, v$SQL_plan.getVid());
//            pst.setLong(17, v$SQL_plan.getParent_id());
//            pst.setLong(18, v$SQL_plan.getDepth());
//            pst.setLong(19, v$SQL_plan.getPosition());
//            pst.setLong(20, v$SQL_plan.getSearch_columns());
//            pst.setLong(21, v$SQL_plan.getCost());
//            pst.setLong(22, v$SQL_plan.getCardinality());
//            pst.setDouble(23, v$SQL_plan.getBytes());
//            pst.setString(24, v$SQL_plan.getOther_tag());
//            pst.setString(25, v$SQL_plan.getPartition_start());
//            pst.setString(26, v$SQL_plan.getPartition_stop());
//            pst.setLong(27, v$SQL_plan.getParent_id());
//            pst.setString(28, v$SQL_plan.getOther());
//            pst.setString(29, v$SQL_plan.getDistribution());
//            pst.setDouble(30, v$SQL_plan.getCpu_cost());
//            pst.setDouble(31, v$SQL_plan.getIo_cost());
//            pst.setLong(32, v$SQL_plan.getTemp_space());
//            pst.setString(33, v$SQL_plan.getAccess_predicates());
//            pst.setString(34, v$SQL_plan.getFilter_predicates());
//            pst.setString(35, v$SQL_plan.getProjection());
//            pst.setLong(36, v$SQL_plan.getTime_());
//            pst.setString(37, v$SQL_plan.getQblock_name());
//            pst.setString(38, v$SQL_plan.getCjtime());
//            pst.setString(39, v$SQL_plan.getUni_sql_id());
//            pst.setString(40,v$SQL_plan.getUserName());
//            pst.addBatch();
//        }
//        return pst;
//    }

    /***
     * 测试批量插入
     * @param con
     * @return
     * @throws SQLException
     */
    public PreparedStatement test(Connection con) throws SQLException {
        PreparedStatement pst = null;
        String sql = "INSERT INTO public.testp(\n" +
                "            ID, NAME)\n" +
                "    VALUES (?, ?);\n";
        int number = 100;
        pst = (PreparedStatement) con.prepareStatement(sql.toString());
        for (int i = 0; i < number; i++) {
            pst.setInt(1, i);
            pst.setString(2, "value" + i);
            pst.addBatch();/**增加sql到sql任务列表中*/
        }
        return pst;
    }


    public static void main(String[] args) {

    }


}
