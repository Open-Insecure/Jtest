package com.br.dong.swing.dialog.dialogTest1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.swing.dialog.dialogTest1
 * AUTHOR: hexOr
 * DATE :2017/4/14 11:35
 * DESCRIPTION:
 */

public class Main extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextArea area1;
    private JTextArea area2;

    public Main() {
        super();
        initUI();
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        area1 = new JTextArea(5, 20);
        area2 = new JTextArea(5, 20);
        area1.addMouseListener(new DMouseAdapter());
        area2.addMouseListener(new DMouseAdapter());
        c.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, area1, area2));
    }

    public static void main(String args[]) {
        new Main();
    }

    private class DMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Object source = e.getSource();
            if (e.getClickCount() >= 2 && source instanceof JTextArea) {
                JTextArea sArea = (JTextArea) source;
                // show dialog window
                String rs = DDialog.showDialog(Main.this, sArea.getText());
                sArea.setText(rs);
            }
        }
    }
}
