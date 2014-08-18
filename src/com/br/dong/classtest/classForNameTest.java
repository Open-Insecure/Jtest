package com.br.dong.classtest;

public class classForNameTest {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		//根据给出的包名+类名创建Rc实例
		String cc="com.br.dong.classtest.Rc";
		//Class.forName是静态方法
		Rc rc=(Rc)Class.forName(cc).newInstance();
		System.out.println("rc:"+rc.getRc());
	}
}
