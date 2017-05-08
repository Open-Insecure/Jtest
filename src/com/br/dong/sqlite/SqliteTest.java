package com.br.dong.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.sqlite
 * AUTHOR: hexOr
 * DATE :2017/4/17 17:06
 * DESCRIPTION:
 */
public class SqliteTest {
    public static void main(String[] args) throws ClassNotFoundException {
        //   load   the   sqlite-JDBC   driver   using   the   current   class   loader
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        try {
            //   create   a   database   connection
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);      //   set   timeout   to   30   sec.

//            statement.executeUpdate("drop   table   if   exists   person");
//            statement.executeUpdate("create   table   person   (id   integer,   name   string)");
            statement.executeUpdate("insert   into   person   values(3,   'leo2222')");
            statement.executeUpdate("insert   into   person   values(4,   'yui333333')");
            ResultSet rs = statement.executeQuery("select   *   from   person");
            while (rs.next()) {
                //   read   the   result   set
                System.out.println("name   =   " + rs.getString("name"));
                System.out.println("id   =   " + rs.getInt("id"));
            }
        } catch (SQLException e) {
            //   if   the   error   message   is   "out   of   memory",
            //   it   probably   means   no   database   file   is   found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                //   connection   close   failed.
                System.err.println(e);
            }
        }
    }
}
