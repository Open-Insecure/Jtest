package com.br.dong.httpclientTest.porn;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-8-20
 * Time: 下午4:12
 * To change this template use File | Settings | File Templates.
 */
public class ProxyRowMapper implements RowMapper {


    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        ProxyBean proxy=new ProxyBean();
        proxy.setIp(rs.getString("ip"));
        proxy.setPort(rs.getString("port") );
        proxy.setType(rs.getString("type"));
        proxy.setUpdatetime(rs.getString("updatetime"));
        return proxy;
    }
}
