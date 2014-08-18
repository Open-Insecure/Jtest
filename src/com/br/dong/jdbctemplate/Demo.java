package com.br.dong.jdbctemplate;
/** 
 * @author  hexd
 * 创建时间：2014-8-1 下午5:01:40 
 * 类说明 
 */
public class Demo {
	private String demoName;
	private String demoDate;
	private String demoId;
	public Demo(String demoName, String demoDate, String demoId) {
		super();
		this.demoName = demoName;
		this.demoDate = demoDate;
		this.demoId = demoId;
	}
	public String getDemoName() {
		return demoName;
	}
	public void setDemoName(String demoName) {
		this.demoName = demoName;
	}
	public String getDemoDate() {
		return demoDate;
	}
	public void setDemoDate(String demoDate) {
		this.demoDate = demoDate;
	}
	public String getDemoId() {
		return demoId;
	}
	public void setDemoId(String demoId) {
		this.demoId = demoId;
	}
}
