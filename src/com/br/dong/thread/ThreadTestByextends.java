package com.br.dong.thread;

public class ThreadTestByextends extends Thread{
	private String threadname;
    
	public ThreadTestByextends(String threadname) {
		super();
		this.threadname = threadname;
	}

	public void run(){
		for (int i=0;i<5;i++){
			System.out.println(threadname+"thread.."+i);
		}
	}
	
	//测试方法 一般不推荐此种方法实现多线程 都用实现Runable接口方式
	public static void main(String[] args) {
		ThreadTestByextends thread1=new ThreadTestByextends("thread1");
		ThreadTestByextends thread2=new ThreadTestByextends("thread2");
		thread1.start();
		thread2.start();
	}
}
