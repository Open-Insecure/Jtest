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
public class VedioRowMapper implements RowMapper {


    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        VedioBean vedio=new VedioBean();
        vedio.setTitle(rs.getString("title"));
        vedio.setPreImgSrc(rs.getString("preImgSrc") );
        vedio.setVedioUrl(rs.getString("vedioUrl"));
        vedio.setInfotime(rs.getString("infotime"));
        return vedio;
    }
}
