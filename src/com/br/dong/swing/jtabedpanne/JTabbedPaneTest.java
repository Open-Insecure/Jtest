package com.br.dong.swing.jtabedpanne;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-14
 * Time: 上午10:55
 * To change this template use File | Settings | File Templates.
 */

import javax.swing.*;

public class JTabbedPaneTest extends JFrame {
    public JTabbedPaneTest() {
        super("JTabbedPane Test");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
//        YiTabbedPane book = new YiTabbedPane();
        JTabbedPane book=new JTabbedPane();
        book.addTab("Tab 1", null, new JLabel("Tab 1 Content"), "Tab 1");
        book.addTab("Tab 2", null, new JLabel("Tab 2 Content"), "Tab 2");
        book.addTab("Tab 3", null, new JLabel("Tab 3 Content"), "Tab 3");

        add(book);
    }

    public static void main(String[] args) {
        JTabbedPaneTest test = new JTabbedPaneTest();
        test.setVisible(true);
    }
}
