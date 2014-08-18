package com.br.dong.jdbctemplate;
/** 
 * @author  hexd
 * 创建时间：2014-8-1 下午3:26:31 
 * 类说明 
 */
public class User {
	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}
	private String name;
	private String password;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
