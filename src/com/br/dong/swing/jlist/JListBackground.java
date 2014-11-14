package com.br.dong.swing.jlist;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;
/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-11
 * Time: 上午11:56
 * To change this template use File | Settings | File Templates.
 */
public class JListBackground extends JPanel {
    private static final Color lightBlue = new Color(153, 204, 255);

    public JListBackground() {
        super();
        setBackground(lightBlue);
    }

    public static void addComponentsToPane(Container pane) {
        String[] bruteForceCode = { "int count = 0",
                "int m = mPattern.length();",
                "int n = mSource .length();",
                "outer:",
                " ++count;",
                " }",
                " return count;",
                "}"
        };
        JList list = new JList(bruteForceCode);
        Border etch = BorderFactory.createEtchedBorder();
        list.setBorder(BorderFactory.createTitledBorder(etch, "Brute Force Code"));
        JPanel listPanel = new JPanel();
        listPanel.add(list);
        listPanel.setBackground(lightBlue);
        list.setBackground(lightBlue);

        pane.add(listPanel, BorderLayout.CENTER);
        pane.setBackground(lightBlue);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Brute Force Algorithm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToPane(frame.getContentPane());
        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
