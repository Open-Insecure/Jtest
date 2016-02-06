package com.br.dong.swing.layout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2016-01-21
 * Time: 9:55
 */
public class CardLayoutTest1 extends JFrame {
    public CardLayoutTest1() {
        this.setSize(400, 400);// 设置窗体大小
        this.setLocationRelativeTo(null);// 设置窗体居中显示
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 当点击窗体关闭按钮的时候，就退出程序
        Container c = this.getContentPane();// 在这里获取容器面板
        JButton jbtn1 = new JButton("上一个");//创建按钮对象
        c.add(jbtn1, BorderLayout.NORTH);//在容器的 南边添加按钮
        final JPanel mainJPanel = new JPanel(); // 默认布局是 流布局
        final CardLayout cardLayout = new CardLayout(); // 创建卡片布局的对象
        mainJPanel.setLayout(cardLayout);// 重新设置面板的布局 为卡片布局
        mainJPanel.add(new JLabel("西游记"), "one"); // 必须指定 标识符 ，如果没有标识符
        // 就会出现：Exception in thread
        // "main"
        // java.lang.IllegalArgumentException:
        // cannot add to layout:
        // constraint must be a
        // string
        mainJPanel.add(new JLabel("红楼梦"), "two");//在mainJpanel添加控件
        mainJPanel.add(new JLabel("三国演义"), "three");//在mainJpanel添加控件
        mainJPanel.add(new JLabel("水浒传"), "four");//在mainJpanel添加控件
        c.add(mainJPanel, BorderLayout.CENTER); //把面板对象，添加到容器的中间部分
        JButton jbtn2 = new JButton("下一个"); //创建按钮对象
        c.add(jbtn2, BorderLayout.SOUTH); //把按钮添加到容器的 北边

        // 注册的上一个事件
        jbtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.previous(mainJPanel);// 上一个
            }
        });
        // 注册的下一个事件
        jbtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.next(mainJPanel);// 下一个
            }
        });
        // 默认显示三国演义
        cardLayout.show(mainJPanel, "three"); // 显示指定的内容
        cardLayout.last(mainJPanel);// 显示最后一个内容
        cardLayout.first(mainJPanel);// 显示第一个内容
        this.setVisible(true);// 设置窗体可见
    }
    public static void main(String[] args) {
        new CardLayoutTest1();
    }

}
