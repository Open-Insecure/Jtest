package com.br.dong.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-12-03
 * Time: 14:53
 */
public class ExecuteSql {
    private String driver;
    private String url;
    private String user;
    private String pass;
    Connection conn;
    Statement stmt;
    ResultSet rs;
    public void initParam(String paramFile) throws Exception {
        driver ="oracle.jdbc.driver.OracleDriver";
        url ="jdbc:oracle:thin:@127.0.0.1:1521/orcl";
        user ="ma";
        pass =  "ma";
    }

    public void executeSql(String sql) throws Exception{
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);
            stmt = conn.createStatement();
            boolean hasResultSet = stmt.execute(sql);
            if (hasResultSet) {
                rs = stmt.getResultSet();
                java.sql.ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                while (rs.next()) {
                    for (int i = 0; i < columnCount; i++) {
                        System.out.print(rs.getString(i+1) + "\t");
                    }
                    System.out.println();

                }
            }
            else {
                System.out.println("改SQL语句影响的记录有" + stmt.getUpdateCount() + "条");
            }
        }
        finally
        {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub

        ExecuteSql ed = new ExecuteSql();
        ed.initParam("src/mysql.ini");
        ed.executeSql("set serveroutput on\n" +
                "\n" +
                "declare\n" +
                "  cursor c1 is\n" +
                "    with ctetemp as\n" +
                "     (select a.object_owner,\n" +
                "             a.object_name,\n" +
                "             a.OBJECT_TYPE,\n" +
                "             a.sql_id,\n" +
                "             a.plan_hash_value,\n" +
                "             a.seg_mb,\n" +
                "             round(a.avg_elapsed_time / 1000000, 2) as avg_elapsed_time,\n" +
                "             a.executions,\n" +
                "             a.cost,\n" +
                "             b.dayaddmb,\n" +
                "             nvl(c.con_count, 0) as curr_count\n" +
                "        from (select dsp.object_owner,\n" +
                "                     dsp.object_name,\n" +
                "                     dsp.OBJECT_TYPE,\n" +
                "                     dsp.sql_id,\n" +
                "                     dsp.plan_hash_value,\n" +
                "                     seg_mb,\n" +
                "                     round(sum(dhs.elapsed_time_delta) /\n" +
                "                           sum(decode(dhs.executions_delta,\n" +
                "                                      0,\n" +
                "                                      1,\n" +
                "                                      dhs.executions_delta)),\n" +
                "                           2) as avg_elapsed_time,\n" +
                "                     sum(decode(dhs.executions_delta,\n" +
                "                                0,\n" +
                "                                1,\n" +
                "                                dhs.executions_delta)) as executions,\n" +
                "                     max(dsp2.cost) as cost\n" +
                "                from (select dsp.OBJECT_OWNER,\n" +
                "                             dsp.object_name,\n" +
                "                             dsp.OBJECT_TYPE,\n" +
                "                             dsp.sql_id,\n" +
                "                             round(ds.BYTES / 1024 / 1024, 2) as seg_mb,\n" +
                "                             dsp.plan_hash_value\n" +
                "                        from dba_hist_sql_plan dsp, dba_segments ds\n" +
                "                       where dsp.OBJECT_NAME = ds.segment_name\n" +
                "                         and dsp.object_type = ds.segment_type\n" +
                "                         and dsp.OBJECT_OWNER = ds.owner\n" +
                "                         and dsp.object_owner not in\n" +
                "                             ('SYS',\n" +
                "                              'SYSTEM',\n" +
                "                              'MDSYS',\n" +
                "                              'XDB',\n" +
                "                              'APEX_030200',\n" +
                "                              'DBSNMP',\n" +
                "                              'SYSMAN',\n" +
                "                              'WMSYS',\n" +
                "                              'APPQOSSYS',\n" +
                "                              'CTXSYS',\n" +
                "                              'DBSNMP',\n" +
                "                              'ORDSYS')\n" +
                "                         and dsp.options in ('FULL', 'ALL')\n" +
                "                       group by dsp.OBJECT_OWNER,\n" +
                "                                dsp.object_name,\n" +
                "                                dsp.OBJECT_TYPE,\n" +
                "                                round(ds.BYTES / 1024 / 1024, 2),\n" +
                "                                dsp.sql_id,\n" +
                "                                dsp.plan_hash_value) dsp,\n" +
                "                     dba_hist_sqlstat dhs,\n" +
                "                     dba_hist_sql_plan dsp2\n" +
                "               where dhs.sql_id = dsp.sql_id\n" +
                "                 and dhs.plan_hash_value = dsp.plan_hash_value\n" +
                "                 and dsp2.sql_id = dsp.sql_id\n" +
                "                 and dsp2.plan_hash_value = dsp.plan_hash_value\n" +
                "                 and dsp2.id = 0\n" +
                "               group by dsp.OBJECT_OWNER,\n" +
                "                        dsp.object_name,\n" +
                "                        dsp.OBJECT_TYPE,\n" +
                "                        dsp.sql_id,\n" +
                "                        dsp.plan_hash_value,\n" +
                "                        dsp.seg_mb\n" +
                "              having round(sum(dhs.elapsed_time_delta) / sum(decode(dhs.executions_delta, 0, 1, dhs.executions_delta))) > 1 and sum(decode(dhs.executions_delta, 0, 1, dhs.executions_delta)) > 1\n" +
                "              \n" +
                "              ) a,\n" +
                "             (\n" +
                "              \n" +
                "              select do.OBJECT_NAME,\n" +
                "                      do.owner,\n" +
                "                      round(round(sum(dh.space_used_delta) / 1024 / 1024, 2) /\n" +
                "                            (case\n" +
                "                               when round((select to_date(to_char(max(end_interval_time),\n" +
                "                                                                 'yyyy-mm-dd hh24:mi:ss'),\n" +
                "                                                         'yyyy-mm-dd hh24:mi:ss') -\n" +
                "                                                 to_date(to_char(min(end_interval_time),\n" +
                "                                                                 'yyyy-mm-dd hh24:mi:ss'),\n" +
                "                                                         'yyyy-mm-dd hh24:mi:ss')\n" +
                "                                            from dba_hist_snapshot)) = 0 then\n" +
                "                                1\n" +
                "                               else\n" +
                "                                round((select to_date(to_char(max(end_interval_time),\n" +
                "                                                             'yyyy-mm-dd hh24:mi:ss'),\n" +
                "                                                     'yyyy-mm-dd hh24:mi:ss') -\n" +
                "                                             to_date(to_char(min(end_interval_time),\n" +
                "                                                             'yyyy-mm-dd hh24:mi:ss'),\n" +
                "                                                     'yyyy-mm-dd hh24:mi:ss')\n" +
                "                                        from dba_hist_snapshot))\n" +
                "                             end),\n" +
                "                            2) as DayAddMb\n" +
                "                from dba_hist_seg_stat dh,\n" +
                "                      dba_objects       do,\n" +
                "                      dba_hist_snapshot dhs\n" +
                "               where dh.obj# = do.OBJECT_ID\n" +
                "                 and dhs.snap_id = dh.snap_id\n" +
                "                 and dh.space_used_delta <> 0\n" +
                "               group by do.owner, do.OBJECT_NAME, do.OBJECT_TYPE) b,\n" +
                "             (select sql_id, sum(con_count) as con_count\n" +
                "                from (\n" +
                "                      \n" +
                "                      select sql_id, sample_time, con_count\n" +
                "                        from (select con_count,\n" +
                "                                      row_number() over(partition by sql_id order by con_count desc) as rk,\n" +
                "                                      sample_time,\n" +
                "                                      sql_id\n" +
                "                                 from (select count(1) as con_count,\n" +
                "                                              sample_time,\n" +
                "                                              sql_id\n" +
                "                                         from dba_hist_active_sess_history\n" +
                "                                        where sql_id is not null\n" +
                "                                        GROUP BY sample_time, sql_id\n" +
                "                                       having count(*) > 1))\n" +
                "                       where rk = 1\n" +
                "                      union\n" +
                "                      select sql_id, sample_time, con_count\n" +
                "                        from (select con_count,\n" +
                "                                     row_number() over(partition by sql_id order by con_count desc) as rk,\n" +
                "                                     sample_time,\n" +
                "                                     sql_id\n" +
                "                                from (select count(1) as con_count,\n" +
                "                                             sample_time,\n" +
                "                                             sql_id\n" +
                "                                        from v$active_session_history\n" +
                "                                       where sql_id is not null\n" +
                "                                       GROUP BY sample_time, sql_id\n" +
                "                                      having count(*) > 1))\n" +
                "                       where rk = 1)\n" +
                "               group by sql_id) c\n" +
                "       where a.object_owner = b.owner(+)\n" +
                "         and a.object_name = b.object_name(+)\n" +
                "         and a.sql_id = c.sql_id(+)),\n" +
                "    cte as\n" +
                "     (select object_owner,\n" +
                "             object_name,\n" +
                "             OBJECT_TYPE,\n" +
                "             sql_id,\n" +
                "             plan_hash_value,\n" +
                "             seg_mb,\n" +
                "             avg_elapsed_time,\n" +
                "             executions,\n" +
                "             cost,\n" +
                "             dayaddmb,\n" +
                "             curr_count,\n" +
                "             (case\n" +
                "               when avg_elapsed_time >= 1 then\n" +
                "                1\n" +
                "               else\n" +
                "                0\n" +
                "             end) + (case\n" +
                "               when cost >= 100 then\n" +
                "                1\n" +
                "               else\n" +
                "                0\n" +
                "             end) + (case\n" +
                "               when executions >= 50 then\n" +
                "                1\n" +
                "               else\n" +
                "                0\n" +
                "             end) + (case\n" +
                "               when seg_mb >= 100 then\n" +
                "                2\n" +
                "               else\n" +
                "                0\n" +
                "             end) + (case\n" +
                "               when dayaddmb >= 1 then\n" +
                "                3\n" +
                "               else\n" +
                "                0\n" +
                "             end) + (case\n" +
                "               when curr_count >= 2 then\n" +
                "                2\n" +
                "               else\n" +
                "                0\n" +
                "             end) as score,\n" +
                "             case\n" +
                "               when dayaddmb >= 1 then\n" +
                "                3\n" +
                "               else\n" +
                "                0\n" +
                "             end as dayaddmbscore\n" +
                "        from ctetemp)\n" +
                "    select distinct 'select * from table(dbms_xplan.display_awr(' ||\n" +
                "                    chr(39) || sql_id || chr(39) || ',' || plan_hash_value || '))' sqltext\n" +
                "      from cte;\n" +
                "  sq varchar2(4000);\n" +
                "  resul varchar2(2000);\n" +
                "begin\n" +
                "  DBMS_OUTPUT.ENABLE(buffer_size => null) ;\n" +
                "  for cc in c1 loop\n" +
                "    DBMS_OUTPUT.put_line('++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++');\n" +
                "    sq := 'select listagg(plan_table_output, chr(10)) within group(order by rn) as a from (select rownum rn, count(*) over() cnt, a.* from (' || cc.sqltext || ') a) group by cnt';\n" +
                "    execute immediate sq into resul;\n" +
                "    DBMS_OUTPUT.put_line(resul);\n" +
                "  end loop;\n" +
                "end;\n" +
                "/\n");
//        ed.executeSql("select * from dual"); //(insertSql);
//        ed.executeSql("create table school(id int, name varchar(50), addr varchar(50))");
//        ed.executeSql("insert into school values(1, 'No1', 'BeiJing')");
//        ed.executeSql("select * from school");
    }
}
