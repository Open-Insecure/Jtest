package com.br.dong.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;


/** 
 * @author  hexd
 * 创建时间：2014-6-3 下午5:33:31 
 * 类说明 
 */
public class FrameJTextArea extends JFrame{
	//设置jTextArea   
	   
	public static JTextArea  jtextarea;   
	private JPanel contentPane;
	   
	public FrameJTextArea (){//构造函数   

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 453, 600);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(null);
		setContentPane(contentPane);
	jtextarea =new  JTextArea ();   
	   
	   
	 ShowData();//假设创建主窗体时，就要获取，并显示数据   
	   
	   
	 }   
	   
	  public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						FrameJTextArea frame = new FrameJTextArea();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	}  
	   
	public void ShowData(){   
	   
	       new Thread(new Runnable() {//启动数据获取，更新显示进程   
	   
	                public void run() {   
	                		
	                	outData();
	                }   
	   
	            }).start(); //   
	}   
	//假设数据为一个自增数，不断输出数据   
	   
	   public void outData(){   
	   
	     for(int i=0; i<10000;i++)   
	   
	     jtextarea.append( "当前数据为："+i+"\n"); //调用主窗口的jtextarea进行添加数据，显示   
	   
	     try {   
	   
	            Thread.currentThread().sleep(100);//让当前的进程睡眠若干毫秒，更加显示出动态更新效果，当然这将耗时   
	   
	        } catch (InterruptedException ex) {   
	   
	     //捕获中断异常}   
	   
	   }   
	   
	}   
}
