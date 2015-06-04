package com.br.dong.string.test;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-5-14
 * Time: 14:08
 */
public class ClobTest {
    private static String c="with ctetemp as (\n" +
            "select a.object_owner,a.object_name,a.OBJECT_TYPE,a.sql_id,a.seg_mb,round(a.avg_elapsed_time/1000000,2) as avg_elapsed_time,\n" +
            "a.executions,a.cost,b.dayaddmb,nvl(c.con_count,0) as curr_count  from ( \n" +
            "select dsp.object_owner,\n" +
            "       dsp.object_name,\n" +
            "       dsp.OBJECT_TYPE,\n" +
            "       dsp.sql_id,\n" +
            "       seg_mb,\n" +
            "       round(sum(dhs.elapsed_time_delta) /\n" +
            "             sum(decode(dhs.executions_delta, 0, 1, dhs.executions_delta)),\n" +
            "             2) as avg_elapsed_time,\n" +
            "       sum(decode(dhs.executions_delta, 0, 1, dhs.executions_delta)) as executions,\n" +
            "       max(dsp2.cost) as cost\n" +
            "  from (select dsp.OBJECT_OWNER,\n" +
            "               dsp.object_name,\n" +
            "               dsp.OBJECT_TYPE,\n" +
            "               dsp.sql_id,\n" +
            "               round(ds.BYTES / 1024 / 1024, 2) as seg_mb,\n" +
            "               dsp.plan_hash_value \n" +
            "          from dba_hist_sql_plan dsp, dba_segments ds \n" +
            "         where dsp.OBJECT_NAME = ds.segment_name\n" +
            "           and dsp.object_type = ds.segment_type\n" +
            "           and dsp.OBJECT_OWNER = ds.owner \n" +
            "           and dsp.object_owner not in ('SYS',\n" +
            "                                        'SYSTEM',\n" +
            "                                        'MDSYS',\n" +
            "                                        'XDB',\n" +
            "                                        'APEX_030200',\n" +
            "                                         'DBSNMP',\n" +
            "                                        'SYSMAN',\n" +
            "                                        'WMSYS',\n" +
            "                                        'APPQOSSYS',\n" +
            "                                        'CTXSYS',\n" +
            "                                        'DBSNMP',\n" +
            "                                        'ORDSYS')\n" +
            "\n" +
            "\n" +
            "and  round(ds.BYTES / 1024 / 1024, 2)>100           and dsp.options in ('FULL', 'ALL') \n" +
            "         group by dsp.OBJECT_OWNER,\n" +
            "                  dsp.object_name,\n" +
            "                  dsp.OBJECT_TYPE,\n" +
            "                  round(ds.BYTES / 1024 / 1024, 2),\n" +
            "                  dsp.sql_id,\n" +
            "                  dsp.plan_hash_value) dsp,\n" +
            "       dba_hist_sqlstat dhs,dba_hist_sql_plan dsp2\n" +
            " where dhs.sql_id = dsp.sql_id\n" +
            "   and dhs.plan_hash_value = dsp.plan_hash_value\n" +
            "   and dsp2.sql_id = dsp.sql_id\n" +
            "   and dsp2.plan_hash_value = dsp.plan_hash_value\n" +
            "   and dsp2.id=0\n" +
            "group by dsp.OBJECT_OWNER,\n" +
            "                  dsp.object_name,\n" +
            "                  dsp.OBJECT_TYPE, \n" +
            "                  dsp.sql_id,\n" +
            "                  dsp.seg_mb   \n" +
            "having \n" +
            "          round(sum(dhs.elapsed_time_delta) /sum(decode(dhs.executions_delta, 0, 1, dhs.executions_delta)))>1\n" +
            "          and  sum(decode(dhs.executions_delta, 0, 1, dhs.executions_delta)) >1                        \n" +
            "                  \n" +
            "                   ) a,(\n" +
            "                  \n" +
            "select   do.OBJECT_NAME,\n" +
            "       do.owner,       \n" +
            "       round(round(sum(dh.space_used_delta) / 1024 / 1024, 2) /\n" +
            "             (case\n" +
            "                when round((select to_date(to_char(max(end_interval_time), 'yyyy-mm-dd hh24:mi:ss'),\n" +
            "                                          'yyyy-mm-dd hh24:mi:ss') -\n" +
            "                                  to_date(to_char(min(end_interval_time), 'yyyy-mm-dd hh24:mi:ss'),\n" +
            "                                          'yyyy-mm-dd hh24:mi:ss')\n" +
            "                             from dba_hist_snapshot)) = 0 then\n" +
            "                 1\n" +
            "                else\n" +
            "                 round((select to_date(to_char(max(end_interval_time), 'yyyy-mm-dd hh24:mi:ss'),\n" +
            "                                      'yyyy-mm-dd hh24:mi:ss') -\n" +
            "                              to_date(to_char(min(end_interval_time), 'yyyy-mm-dd hh24:mi:ss'),\n" +
            "                                      'yyyy-mm-dd hh24:mi:ss')\n" +
            "                         from dba_hist_snapshot))\n" +
            "              end),\n" +
            "             2) as DayAddMb\n" +
            "  from dba_hist_seg_stat dh, dba_objects do, dba_hist_snapshot dhs\n" +
            " where dh.obj# = do.OBJECT_ID\n" +
            "   and dhs.snap_id = dh.snap_id\n" +
            "   and dh.space_used_delta <> 0\n" +
            " group by do.owner, do.OBJECT_NAME, do.OBJECT_TYPE \n" +
            "    ) b,(select sql_id,sum(con_count) as con_count from (\n" +
            "    \n" +
            "    select sql_id, sample_time, con_count\n" +
            "  from (select con_count,\n" +
            "               row_number() over(partition by sql_id order by con_count desc) as rk,\n" +
            "               sample_time,\n" +
            "               sql_id\n" +
            "          from (select count(1) as con_count, sample_time, sql_id\n" +
            "                  from dba_hist_active_sess_history\n" +
            "                 where sql_id is not null\n" +
            "                 GROUP BY sample_time, sql_id\n" +
            "                having count(*) > 1))\n" +
            "where rk = 1\n" +
            "union \n" +
            "\n" +
            "\n" +
            "select sql_id, sample_time, con_count\n" +
            "  from (select con_count,\n" +
            "               row_number() over(partition by sql_id order by con_count desc) as rk,\n" +
            "               sample_time,\n" +
            "               sql_id\n" +
            "          from (select count(1) as con_count, sample_time, sql_id\n" +
            "                  from v$active_session_history\n" +
            "                 where sql_id is not null\n" +
            "                 GROUP BY sample_time, sql_id\n" +
            "                having count(*) > 1))\n" +
            "where rk = 1) group by sql_id) c\n" +
            "    where a.object_owner=b.owner(+)\n" +
            "    and a.object_name=b.object_name(+)\n" +
            "    and a.sql_id=c.sql_id(+)),\n" +
            "    cte as (\n" +
            "    select object_owner,object_name,OBJECT_TYPE,sql_id,seg_mb,avg_elapsed_time,\n" +
            "    executions,cost,dayaddmb,curr_count,\n" +
            "    (case when avg_elapsed_time>=1 then 1 else 0 end)+\n" +
            "    (case when cost>=100 then 1 else 0 end)+\n" +
            "    (case when executions>=50 then 1 else 0 end)+\n" +
            "    (case when seg_mb>=100 then 2 else 0 end)+\n" +
            "    (case when dayaddmb>=1 then 3 else 0 end)+\n" +
            "    (case when curr_count>=2 then 2 else 0 end) as score,\n" +
            "    case when dayaddmb>=1 then 3 else 0 end as dayaddmbscore  from ctetemp)\n" +
            "    select object_owner,object_name,OBJECT_TYPE,sql_id,seg_mb,avg_elapsed_time,\n" +
            "    executions,cost,dayaddmb,curr_count,score CNT,case when score between 1 and 2 then '风险很小'\n" +
            "    when (score between 3 and 4) or (score between 5 and 7 and dayaddmbscore=0) then '有一定风险' \n" +
            "    when score between 5 and 7 and dayaddmbscore=3 then '有较大风险'\n" +
            "    when score between 8 and 10 then '有极大风险' else '暂无风险' end as safelevel  from cte\n" +
            "    order by score desc\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n"  ;

    public static void main(String[] args) {
        System.out.println(c.trim().replace("\n","").length());
        System.out.println(c.trim().replace("\n", "").replace("\t","").length());
        System.out.println(c.trim().replace("\n", ""));
        System.out.println(c.trim().replace("\n","").replace("\t","").replaceAll("   ", ""));
    }


    private static  String clob2Str(Clob clob){
        String content = "";
        try {
            Reader is = clob.getCharacterStream();
            BufferedReader buff = new BufferedReader(is);// 得到流
            String line = buff.readLine();
            StringBuffer sb = new StringBuffer();
            while (line != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
                sb.append(line);
                line = buff.readLine();
            }
            content = sb.toString();
        } catch (Exception e) {
            System.out.println("java.sql.Clob类型转java.lang.String类型出错..."+e.getCause());
            e.printStackTrace();
        }
        return content;
    }




}
