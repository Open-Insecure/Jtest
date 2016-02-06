package com.br.dong.swing.popwindow.test2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2016-01-20
 * Time: 17:11
 */
public class Main  extends JFrame  {
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

 class DDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private JTextArea area;
    private JButton okButton;
    private JButton cancelButton;

    private String rsString = "";

    private DDialog() {
        super();
        init();
        setModal(true);
        setSize(400, 300);
    }

    private void init() {
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        area = new JTextArea();
        c.add(new JScrollPane(area));

        JPanel p = new JPanel();
        FlowLayout fl = new FlowLayout();
        fl.setHgap(15);
        p.setLayout(fl);
        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rsString = area.getText();
                dispose();
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        p.add(okButton);
        p.add(cancelButton);

        c.add(p, BorderLayout.SOUTH);
    }

    public static String showDialog(Component relativeTo, String text) {
        DDialog d = new DDialog();
        d.area.setText(text);
        d.rsString = text;
        d.setLocationRelativeTo(relativeTo);
        d.setVisible(true);
        return d.rsString;
    }

}
