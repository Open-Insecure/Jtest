package com.br.dong.jframe;
/** 
 * @author  hexd
 * 创建时间：2014-8-6 上午11:52:28 
 * 类说明 
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
public class NewThreadActionDemo extends JFrame {
    private JButton button;
    public static void main(String[] args) {
        new NewThreadActionDemo();
    }
    public NewThreadActionDemo() {
        this.setSize(800, 600);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        button = new JButton("耗时很长的事件");
        button.setBounds(300, 200, 200, 80);
        button.addActionListener(new ButtonAction());
        add(button);
        this.setVisible(true);
    }
    public class ButtonAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new Thread() {
                {
                    this.setDaemon(true);
                }
                public void run() {
                    button.setEnabled(false);
                    for (int i = 9; i > 0; i--) {
                        button.setText("运行中(" + i + " / 10)");
                        sleep();
                    }
                    button.setText("耗时很长的事件");
                    button.setEnabled(true);
                }
                private void sleep() {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
