package com.br.dong.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * 两个frame切换
 */

public class DoubleFrame {
    public static void main(String[] args) {
        final JFrame jf1 = new JFrame("frame a");
        final JFrame jf2 = new JFrame("frame b");

        jf1.setSize(300, 200);
        jf1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton jb1 = new JButton("button a");
        jb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jf1.setVisible(!jf1.isShowing());
                jf2.setVisible(!jf2.isShowing());
            }
        });
        jf1.getContentPane().add(jb1);
        jf1.setVisible(true);


        jf2.setSize(300, 600);
        jf2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton jb2 = new JButton("button b");
        jb2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jf1.setVisible(!jf1.isShowing());
                jf2.setVisible(!jf2.isShowing());
            }
        });
        jf2.getContentPane().add(jb2);
    }
}

