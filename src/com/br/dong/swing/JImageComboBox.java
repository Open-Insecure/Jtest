package com.br.dong.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-10-15
 * Time: 下午1:34
 * To change this template use File | Settings | File Templates.
 */
public class JImageComboBox extends JComboBox {
    public JImageComboBox(Vector values) {
        super(values);
        ListCellRenderer render = new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(
                    JList list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ImagedComboBoxItem) {
                    ImagedComboBoxItem item = (ImagedComboBoxItem) value;
                    this.setText(item.getText());
                    this.setIcon(item.getIcon());
                    if (isPopupVisible()) {
                        int offset = 10 * item.getIndent();
                        this.setBorder(BorderFactory.createEmptyBorder(0, offset, 0, 0));
                    }
                }
                return this;
            }
        };
        this.setRenderer(render);
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("TWaver中文社区之Swing探秘");
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());
        Vector values = new Vector();
        Icon openIcon = new ImageIcon(JImageComboBox.class.getResource("favicon.ico"));
        Icon newIcon = new ImageIcon(JImageComboBox.class.getResource("favicon.ico"));
        for (int i = 0; i < 5; i++) {
            values.addElement(new ImagedComboBoxItem("一个目录" + i, openIcon, i));
        }
        for (int i = 0; i < 5; i++) {
            values.addElement( new ImagedComboBoxItem("一个叶子" + i, newIcon, 5));
        }
        JImageComboBox comboBox = new JImageComboBox(values);
        JPanel centerPane = new JPanel(new BorderLayout());
        centerPane.setPreferredSize(new Dimension(300, 200));
        centerPane.add(comboBox, BorderLayout.NORTH);
        frame.getContentPane().add(centerPane);
        frame.setVisible(true);
    }
}
