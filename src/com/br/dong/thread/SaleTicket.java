package com.br.dong.thread;


/**
 * 模拟多线程火车票售票系统
 * @author administrator
 *
 */
public class SaleTicket {
	public static void main(String[] args) {
		Ticket ticket =new Ticket(20);
		for (int i = 0; i < 5; i++) {//5个线程
			new Thread(new Seller(ticket),String.valueOf(i)).start();//线程启动
		}
	}

}
/**
 * 卖票线程类
 * @author administrator
 *
 */
class Seller implements Runnable{
	Ticket ticket;
	public Seller(Ticket ticket) {
		// TODO Auto-generated constructor stub
		this.ticket =ticket ;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (ticket.has) {//如果有票继续售，否则终止售票
			ticket.sellerTicket();//
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
/**
 * 定义票信息类
 * @author administrator
 *
 */
class Ticket {
	private int ticketCount;    //票
	protected boolean has=true ;//是否有票
	public Ticket(int ticketCount) {
		// TODO Auto-generated constructor stub
		this.ticketCount=ticketCount;
	}
	//线程同步的方法
	public synchronized void sellerTicket() {
		if(ticketCount>0){
			System.out.println(Thread.currentThread().getName()+"窗口卖第"+ticketCount+"张票");
			ticketCount--;
		}else{
			System.out.println("票已经售完!");
			has=false;//票已售完
		}
	}
	
}//end

//输出结果
