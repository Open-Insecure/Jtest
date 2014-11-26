package com.br.dong.swing.menu;


import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-25
 * Time: 下午8:50
 * To change this template use File | Settings | File Templates.
 * 菜单栏测试
 */
public class MenuTest {
    private static final int WIDTH=400;
    private static final int HEIHHT=300;
    public static void main(String[] args) {
        try{
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;//设置窗口边框类型
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();//将界面转化为Beauty Eye外观
        }catch (Exception e){
            System.out.println("初始化失败");
        }
        //隐藏设置按钮
        UIManager.put("RootPane.setupButtonVisible",false);
         JFrame jFrame=new JFrame();
        jFrame.setSize(WIDTH,HEIHHT);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("测试标题");
        JMenuBar jMenuBar=new JMenuBar();
        JMenu jMenu1=new JMenu("菜单1"); //菜单父节点
        JMenuItem jMenuItem1=new JMenuItem("打开");  //菜单子节点
        JMenuItem jMenuItem2=new JMenuItem("关闭");
        JMenuItem jMenuItem3=new JMenuItem("保存");
        jMenu1.add(jMenuItem1);
        jMenu1.addSeparator();//菜单分隔符
        jMenu1.add(jMenuItem2);
        jMenu1.addSeparator();//菜单分隔符
        jMenu1.add(jMenuItem3);
        jMenuBar.add(jMenu1); //菜单父节点添加子节点
        jFrame.setJMenuBar(jMenuBar);//设置菜单
        jFrame.setVisible(true);
        //设置内容
        JButton jButton=new JButton("测试按钮");
        //按钮添加提示
        jButton.setToolTipText("这是一个测试按钮");
        //
        JLabel jLabel=new JLabel("测试标签",JLabel.CENTER);
        JPanel jPanel=new JPanel();
        jPanel.add(jLabel);
        jPanel.add(jButton);
        jFrame.setContentPane(jPanel);

    }
}
