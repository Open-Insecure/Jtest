package com.br.dong.thread.threadDesign;
/** 
 * @author  hexd
 * 创建时间：2014-6-30 下午2:42:58 
 * 类说明 
 */
public class Bank {
	private int money;
	private String name;
	//存款
	public synchronized void deposit(int m){
		money+=m;
	}
	//取款
	public synchronized boolean withdraw(int m){
		if(money>=m){
			money-=m;
			return true;
		}else{
			return false;//余额不足
		}
	}
	//锁定一部分
	public void d(){
		synchronized (name) {
			//部分锁定
		}
	}
	
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
