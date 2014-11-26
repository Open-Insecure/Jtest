package com.br.dong.httpclientTest.jiucheng9c;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-7
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */
public class UploadUIByBE extends JFrame implements ActionListener {

    public UploadUIByBE(String title){
        super(title);
        //窗口设置
        int width= Toolkit.getDefaultToolkit().getScreenSize().width;
        int height=Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setSize(650, 500); //设置大小
        this.setLocation(width/4, height/4-50); //设置x，y的位置
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //点击关闭按钮 自动退出程序
        this.setResizable(false); //设置不改变大小
        this.setVisible(true);
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
        new UploadUIByBE("程序").setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
