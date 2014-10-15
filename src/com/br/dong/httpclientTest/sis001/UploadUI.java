package com.br.dong.httpclientTest.sis001;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-10-10
 * Time: 下午5:00
 * To change this template use File | Settings | File Templates.
 */
public class UploadUI extends JFrame implements ActionListener {
    private JPanel jp;
    private String site[]={"site1","site2","site3"};
    private JComboBox petList;
    public UploadUI(){
        jp=new JPanel();
        jp.setLayout(null);
        this.add(jp);
        //窗口设置
        int width= Toolkit.getDefaultToolkit().getScreenSize().width;
        int height=Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setTitle("Dialog");
        this.setSize(800, 600);
        this.setLocation(width/4, height/4-50);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args) {

        UploadUI ui=new UploadUI();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
