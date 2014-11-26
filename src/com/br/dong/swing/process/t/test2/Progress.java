package com.br.dong.swing.process.t.test2;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-24
 * Time: 下午2:53
 * To change this template use File | Settings | File Templates.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Progress extends javax.swing.JDialog implements ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JProgressBar pro_JPB;

    private JPanel btn_JP;

    private JButton cancel_JB;

    private JLabel show_JL;

    NewJFrame jf;

    public Progress(NewJFrame frame) {
        super(frame, true);
        this.jf = frame;
        initGUI();
    }

    set se = new set();

    private void initGUI() {
        try {

            se.addObserver(new setObserver());
            BorderLayout thisLayout = new BorderLayout();
            getContentPane().setLayout(thisLayout);
            {
                show_JL = new JLabel();
                getContentPane().add(show_JL, BorderLayout.NORTH);
                show_JL.setPreferredSize(new java.awt.Dimension(354, 22));
            }
            {
                pro_JPB = new JProgressBar();
                pro_JPB.setMinimum(0);
                pro_JPB.setMaximum(100);
                getContentPane().add(pro_JPB, BorderLayout.CENTER);
                pro_JPB.setPreferredSize(new java.awt.Dimension(392, 27));
            }
            {
                btn_JP = new JPanel();
                getContentPane().add(btn_JP, BorderLayout.SOUTH);
                {
                    cancel_JB = new JButton();
                    btn_JP.add(cancel_JB);
                    cancel_JB.setText("aaa");
                    cancel_JB.addActionListener(this);
                }
            }
            this.setBackground(Color.DARK_GRAY.brighter());
            this.setUndecorated(true);
            this.setSize(392, 90);
            this.setLocationRelativeTo(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMinmum(int i) {
        pro_JPB.setMinimum(i);
    }

    public void setMaxmem(int i) {
        pro_JPB.setMaximum(i);
    }

    public void setVal(int i) {
        se.setPrice(i);
    }

    /**
     *
     * set Class->function:观察者模式的具体应用
     * 2007-4-17
     */
    class set extends Observable {
        private int price;

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
            setChanged();
            notifyObservers(new Integer(price));
        }

    }

    class setObserver implements Observer {
        public void update(Observable obj, Object arg) {
            if (arg instanceof Integer) {
                int i = ((Integer) arg).intValue();
                pro_JPB.setValue(i);
                show_JL.setText((i - pro_JPB.getMinimum()) * 100
                        / (float) (pro_JPB.getMaximum() - pro_JPB.getMinimum())
                        + "%");
            }
        }

    }

    public void actionPerformed(ActionEvent e) {
        jf.setTh_Flag(false);
        this.setVisible(false);
    }

}
