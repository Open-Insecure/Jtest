package com.br.dong.reflect.set_reflect;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-11-10
 * Time: 14:11
 */
public class Student {
    private Long id;
    private String name;
    private Date createdate;
    private String no;
    public String nickname;
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Date getCreatedate()
    {
        return createdate;
    }
    public void setCreatedate(Date createdate)
    {
        this.createdate = createdate;
    }
    public String getNo()
    {
        return no;
    }
    public void setNo(String no)
    {
        this.no = no;
    }
    public String getNickname()
    {
        return nickname;
    }
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }
}
