package com.br.dong.swing.thread;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/** 
 * @author  hexd
 * 创建时间：2014-6-3 下午5:41:26 
 * 类说明 
 */
public class Login {
	JFrame f = new JFrame("测试窗口");
    JButton startBtn = new JButton("开  始");
    //用来显示日志记录的文本域
    public static JTextArea area = new JTextArea(10, 50);
     
    public static void main(String[] args){
        new Login().init();
    }
     
    public void init(){
        Box verticalBox = Box.createVerticalBox();
        JPanel p3 = new JPanel();
        p3.add(startBtn);
        //按钮添加事件
        startBtn.addActionListener((ActionListener) this);
        verticalBox.add(p3);
         
        JPanel p4 = new JPanel();
        JScrollPane scrollPane = new JScrollPane(area);
        p4.add(scrollPane);
        verticalBox.add(p4);
         
        f.add(verticalBox);
        f.setLocation(500, 300);
        f.setResizable(false);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
     
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startBtn){
            startBtn.setEnabled(false);
             
            Robticket robticket = new Robticket();
            new Thread("线程1").start();
            new Thread("线程2").start();
            /*new Thread(robticket,"线程3").start();
            new Thread(robticket,"线程4").start();
            new Thread(robticket,"线程5").start();
            new Thread(robticket,"线程6").start();*/
             
            WriteLog log =WriteLog.getInstance();
            Thread t = new Thread(log,"日志线程");
            t.start();
        }
    }
}