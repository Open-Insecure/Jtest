package com.br.dong.jdbc.mysql.connect;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-4-11
 * Time: 17:25
 */
public class JDBCTest {
    public static void main(String[] args) {
            GetComJDBC.getExecuteQuery("root","system","127.0.0.1","3306","test","select * from proxy");
    }
}
