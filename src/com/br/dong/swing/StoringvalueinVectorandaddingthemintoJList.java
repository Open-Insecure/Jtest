package com.br.dong.swing;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-11
 * Time: 下午12:01
 * To change this template use File | Settings | File Templates.
 */
public class StoringvalueinVectorandaddingthemintoJList {
    public static void main(String[] a) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Vector months = new Vector();
        JList list = new JList(months);

        //用Vector对象创建JList，可以在创建后增加内容，而采用数组创建则不可以
        months.addElement("January");
        months.addElement("December");

        frame.add(new JScrollPane(list));

        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
