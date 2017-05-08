package com.br.dong.swing.dialog.dialogTest1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.swing.dialog.dialogTest1
 * AUTHOR: hexOr
 * DATE :2017/4/14 11:36
 * DESCRIPTION:
 */

public class DDialog extends JDialog {
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


