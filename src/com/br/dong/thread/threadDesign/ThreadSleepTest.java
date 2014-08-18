package com.br.dong.thread.threadDesign;
/** 
 * @author  hexd
 * 创建时间：2014-6-30 下午2:28:55 
 * 类说明 
 */
public class ThreadSleepTest {
	public static void main(String[] args) {
		for(int i=0;i<100;i++){
			System.out.println("test");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
