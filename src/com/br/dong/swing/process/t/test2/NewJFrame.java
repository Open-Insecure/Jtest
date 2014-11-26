package com.br.dong.swing.process.t.test2;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-24
 * Time: 下午2:55
 * To change this template use File | Settings | File Templates.
 */
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class NewJFrame extends javax.swing.JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JPanel cen_JP;

    private JButton jb;

    public static void main(String[] args) {
        NewJFrame inst = new NewJFrame();
        inst.setVisible(true);
    }

    public NewJFrame() {
        super();
        initGUI();
    }

    Progress pro;

    private void initGUI() {
        try {
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            {
                cen_JP = new JPanel();
                getContentPane().add(cen_JP, BorderLayout.CENTER);
                {
                    jb = new JButton();
                    cen_JP.add(jb);
                    jb.setText("jButton1");
                    jbAction ja = new jbAction();
                    pro = new Progress(this);
                    jb.addActionListener(ja);
                }
            }
            pack();
            setSize(400, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean th_Flag = true;

    class jbAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            pro.setMaxmem(100);
            pro.setMinmum(0);
            new Thread() {
                public void run() {
                    for (int i = 1; i < 100; i++) {
                        if (!isTh_Flag()) {
                            setTh_Flag(true);

                            break;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        pro.setVal(i);
                        System.out.println("===" + i);
                    }
                }
            }.start();
            pro.setVal(0);
            pro.setVisible(true);
        }

    }

    public boolean isTh_Flag() {
        return th_Flag;
    }

    public void setTh_Flag(boolean th_Flag) {
        this.th_Flag = th_Flag;
    }
}
