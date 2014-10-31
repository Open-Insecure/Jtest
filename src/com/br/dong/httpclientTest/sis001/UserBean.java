package com.br.dong.httpclientTest.sis001;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-10-30
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class UserBean {
    private String username;
    private String password;

    public UserBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
