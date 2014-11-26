package com.br.dong.swing.layout;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-26
 * Time: 下午4:56
 * To change this template use File | Settings | File Templates.
 * 布局测试
 */
public class LayoutTest {
    private static final int WIDTH=400;
    private static final int HEIGHT=300;
    public static void main(String[] args) {
        try{
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;//设置窗口边框类型
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();//将界面转化为Beauty Eye外观
        }catch (Exception e){
            System.out.println("初始化失败");
        }
        //初始化JFrame
        JFrame jf=new JFrame();
        jf.setSize(WIDTH,HEIGHT);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        JPanel contentPanel=new JPanel();
        jf.setContentPane(contentPanel);//设置jf的内容panel
        //
        JButton j1=new JButton("测试1");
        JButton j2=new JButton("测试2");
        JButton j3=new JButton("测试3");
        JButton j4=new JButton("测试4");
        JButton j5=new JButton("测试5");
        BorderLayout layout=new BorderLayout();
        jf.setLayout(layout);
        contentPanel.add(j1,"North");
        contentPanel.add(j2,"South");
        contentPanel.add(j3,"East");
        contentPanel.add(j4,"West");
        contentPanel.add(j5,"Center");

    }
}
