package com.br.dong.signletone;

public class Test {

	public Sigler sigler=null;
	public Sigler getInstance(){
		if(sigler==null){
			 sigler=new Sigler();
		}
		return sigler;
	}
	
	public static void main(String[] args) {
		Test test=new Test();
		System.out.println(""+test.getInstance().test);
		test.getInstance().test="xixi";
		System.out.println(""+test.getInstance().test);
	}
}
