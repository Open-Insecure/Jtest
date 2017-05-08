package com.br.dong.swing.JScrollPaneTest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.swing.JScrollPaneTest
 * AUTHOR: hexOr
 * DATE :2017/4/14 9:29
 * DESCRIPTION:
 */
public class JScrollPaneDemo {
    JScrollPane scrollPane;

    public JScrollPaneDemo() {
        JFrame f = new JFrame("JScrollpane1");
        Container contentPane = f.getContentPane();
        JLabel label = new JLabel("Label");
        JButton btn = new JButton("Button");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        panel.add(btn, BorderLayout.SOUTH);
        scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        f.setSize(new Dimension(350, 220));
        f.pack();
        f.setVisible(true);
    }

    public static void main(String[] args) {
        new JScrollPaneDemo();
    }
}
