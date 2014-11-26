package com.br.dong.swing.beautyeye;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-6
 * Time: 上午11:41
 * To change this template use File | Settings | File Templates.
 */
public class BeautyEyeTest1 {
    private JFrame f = null;
    private JLabel user = null;
    public BeautyEyeTest1(){
        f = new JFrame("Swing登录界面");
        user = new JLabel("用户名：");
        //条件
        JPanel p = new JPanel();
        //设置行列
        p.setLayout(new GridLayout(3, 2));
        p.add(user);
        f.add(p, BorderLayout.NORTH);
        f.setVisible(true);
        f.setBounds(400, 300, 400, 300);//设置大小
        f.pack();  //界面大小自动适应
    }
    public static void main(String[] args) {
        try{
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;//设置窗口边框类型
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();//将界面转化为Beauty Eye外观
        }catch (Exception e){
            System.out.println("初始化失败");
        }

        //隐藏设置按钮
        UIManager.put("RootPane.setupButtonVisible",false);
        new BeautyEyeTest1();
    }
}
