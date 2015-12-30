package com.br.dong.thread_socket_simply_test.mutileClientTest.common;



/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-11-04
 * Time: 14:19
 */
public class SqlConstant {

        /**
         * 负载-当前值
         */
        public static String LOAD_CURRENT_VALUE="select *\n" +
                "  from (select cname, inst_id, end_time, round(sum(value), 2) as value\n" +
                "          from (select inst_id,\n" +
                "                       '当前值：' as cname,\n" +
                "                       value,\n" +
                "                       end_time,\n" +
                "                       row_number() over(partition by inst_id order by to_char(end_time, 'yyyy/mm/dd hh24:mi') desc) rn\n" +
                "                  from gv$sysmetric_history\n" +
                "                 where metric_name = 'Database Time Per Sec' and inst_id=1\n" +
                "                   and intsize_csec > 5900)\n" +
                "         WHERE RN = 1\n" +
                "         group by rollup(cname, inst_id, end_time)) tmp\n" +
                " where tmp.cname is not null\n" +
                "   and ((tmp.inst_id is not null and end_time is not null) or\n" +
                "       (inst_id is null and end_time is null))";
        /***
         * 负载值-一分钟以前
         */
        public static String LOAD_MINUTE_AGO="select *\n" +
                "  from (select cname, inst_id,  round(sum(value), 2) as value\n" +
                "          from (select inst_id,\n" +
                "                       '一分前：' as cname,\n" +
                "                       value,\n" +
                "                       end_time,\n" +
                "                       row_number() over(partition by inst_id order by to_char(end_time, 'yyyy/mm/dd hh24:mi') desc) rn\n" +
                "                  from gv$sysmetric_history\n" +
                "                 where metric_name = 'Database Time Per Sec' and inst_id=1\n" +
                "                   and intsize_csec > 5900)\n" +
                "         WHERE RN =\n" +
                "         group by rollup(cname, inst_id)) tmp\n" +
                " where tmp.cname is not null";
        /**
         * 负载一小时前
         */
        public static String LOAD_HOUR_AGO="select *\n" +
                "  from (select cname, inst_id, round(sum(value), 2) as value\n" +
                "          from (select inst_id,\n" +
                "                       '一时前：' as cname,\n" +
                "                       value,\n" +
                "                       end_time,\n" +
                "                       rank() over(order by to_char(end_time, 'yyyy/mm/dd hh24:mi') desc) rn\n" +
                "                  from gv$metric_history\n" +
                "                 where metric_name = 'Database Time Per Sec'\n" +
                "                   and to_char(end_time + 1 / 24, 'yyyy/mm/dd hh24:mi') =\n" +
                "                       (select to_char(max(end_time), 'yyyy/mm/dd hh24:mi')\n" +
                "                          from gv$sysmetric \n" +
                "                         where metric_name = 'Database Time Per Sec' and inst_id=1)\n" +
                "                   and intsize_csec > 5900 and inst_id=1)\n" +
                "         where rn = 1\n" +
                "         group by rollup(cname, inst_id)) tmp\n" +
                " where tmp.cname is not null";

        /***
         * 主机负载sql
         */
        public static String LOAD_IFNO="select ENTITY_ID,\n" +
                "       INST_ID  ,\n" +
                "       GROUP_ID,\n" +
                "       TO_CHAR(BEGIN_TIME, 'yyyy-mm-dd hh24:mi:ss') as BEGIN_TIME,\n" +
                "       VALUE,\n" +
                "       METRIC_NAME,\n" +
                "       ENTITY_SEQUENCE,\n" +
                "       METRIC_ID,\n" +
                "       INTSIZE_CSEC,\n" +
                "         TO_CHAR(END_TIME, 'yyyy-mm-dd hh24:mi:ss')  as END_TIME,\n" +
                "       METRIC_UNIT\n" +
                "  from gv$metric_history\n" +
                " where metric_name = 'Database Time Per Sec'\n" +
                "   and intsize_csec > 5900\n" +
                " order by end_time desc";
        /***
         * 获得几分钟的sql
         * @param minute
         * @return
         */
        public static String getLoadMinuteAgoSql(String minute){
                return LOAD_MINUTE_AGO.replace("RN =","RN ="+minute);
        }
//        public static List getExecuteQuery(HostDbInfo hostDbInfo,String sql){
//                return   GetComJDBC.getExecuteQuery(hostDbInfo.getOusername(), hostDbInfo.getPassword(), hostDbInfo.getIpaddress(), hostDbInfo.getPort(), hostDbInfo.getInstance(), sql);//查询结果
//        }

}
