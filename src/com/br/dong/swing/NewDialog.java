package com.br.dong.swing;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Frame;
/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-11
 * Time: 下午12:08
 * To change this template use File | Settings | File Templates.
 */
public class NewDialog extends JFrame {
    JButton button1 = new JButton("我是对话框");
    JButton button2 = new JButton("我是新窗口");

    public NewDialog() {
        try {
            init();
            setLocationRelativeTo(null);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    private void init() throws Exception {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new FlowLayout());
        this.getContentPane().add(button1);
        //   增加一个Listener
        button1.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        new NewDialog().setVisible(true);
                    }
                }
        );
        this.getContentPane().add(button2);
        //   增加一个Listener
        button2.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        new NewFrame().setVisible(true);
                    }
                }
        );
        this.pack();
        this.setVisible(true);
    }


    public static void main(String[] args) {
        new NewDialog();
    }
}

// 对话框
class NewDialog2 extends javax.swing.JDialog {

    //    public NewDialog(Frame owner, boolean modal) {
//        super(owner, modal);
    public NewDialog2() {
        try {
            init();
            setLocationRelativeTo(null);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void init() throws Exception {
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(new JLabel("我是对话框"), "Center");
        this.pack();
        this.setVisible(true);
    }
}
//新窗口
class NewFrame extends javax.swing.JFrame {

    public NewFrame() {
        try {
            init();
            setLocationRelativeTo(null);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void init() throws Exception {
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(new JLabel("我是新窗口"), "Center");
        this.pack();
        this.setVisible(true);
    }
}
