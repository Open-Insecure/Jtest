package com.br.dong.swing.layout;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.swing.layout
 * AUTHOR: hexOr
 * DATE :2017/4/20 10:16
 * DESCRIPTION:
 * GridLayout布局管理器是设定行*列的布局，中途可以增加行或列数，按照添加控件的顺序从左至右从上至下来添加
 *  * 可以设定整体行列之间的间隔，不能跨行跨列，适用于控件布局类似棋盘的样式。
 *  * @author HZ20232
 *  *
 *  
 */
public class TestGridLayout extends JFrame {
    private static final long serialVersionUID = 6819222900970457455L;
    private JLabel button1;
    private JTextField button2;
    private JLabel button3;
    private JLabel button4;
    private JTextField button5;
    private JLabel button6;

    public TestGridLayout() {
        this.setSize(600, 400);
        this.setTitle("测试");
        init();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void init() {
        button1 = new JLabel("NORTH");
        button2 = new JTextField("SOUTH");
        button3 = new JLabel("EAST");
        button4 = new JLabel("WEST");
        button5 = new JTextField("CENTER");
        button6 = new JLabel("CENTER6");
        GridLayout myLayout = new GridLayout(2, 3);
//        myLayout.setHgap(10);
//        myLayout.setVgap(10);
        this.setLayout(myLayout);
        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.add(button4);
        this.add(button5);
        this.add(button6);
    }

    public static void main(String args[]) {
        TestGridLayout test = new TestGridLayout();
    }

}
