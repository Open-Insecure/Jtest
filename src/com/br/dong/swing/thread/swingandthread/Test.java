package com.br.dong.swing.thread.swingandthread;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-26
 * Time: 下午6:38
 * To change this template use File | Settings | File Templates.
 * 测试swing与线程
 */
public class Test {
    public static void main(String[] args) {
        try{
            //invokeAndWait程序必须等待此线程run方法执行完成后才继续往下面执行
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {

                   new MainFrame();
                }
            });

        }catch (Exception e){

        }
    }
}
class MainFrame extends Thread{
    private static final int WIDTH=700;
    private static final int HEIGHT=400;
    public MainFrame(){
       JFrame jf=new JFrame("测试");
       JPanel contentPanel=new JPanel();
       jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       jf.setVisible(true);
       jf.setContentPane(contentPanel);
       Toolkit toolkit=Toolkit.getDefaultToolkit();
       Dimension screenSize=toolkit.getScreenSize();//获得屏幕分辨率
       int width=screenSize.width;
       int height=screenSize.height;
       int x=(width-WIDTH)/2;
       int y=(height-HEIGHT)/2;
       jf.setLocation(x,y);
       jf.setSize(WIDTH,HEIGHT);
       jf.setResizable(false);
      //
       JButton button=new JButton("按钮");
       contentPanel.setLayout(null);
       button.setBounds(100,100,80,20);
       contentPanel.add(button);

    }
}
