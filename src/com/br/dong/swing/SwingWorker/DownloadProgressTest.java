package com.br.dong.swing.SwingWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.swing.SwingWorker
 * AUTHOR: hexOr
 * DATE :2017/4/12 17:03
 * DESCRIPTION:
 */
public class DownloadProgressTest extends JPanel {

    private JProgressBar jpb = new JProgressBar();
    private JButton button = new JButton("开始");

    public DownloadProgressTest() {
        super(new BorderLayout());
        jpb.setStringPainted(true);     //设置显示进度的百分比
        jpb.setValue(0);
        jpb.setMaximum(100);
        add(jpb, BorderLayout.NORTH);
        add(button, BorderLayout.SOUTH);

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                    MySwingWorker mySwingWorker = new MySwingWorker();
                    mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            //判断改变的属性是否是进度，如果是则获取进度的值并显示在进度条上
                            if ("progress".equals(evt.getPropertyName())) {
                                System.out.println("!!!!:" + (Integer) evt.getNewValue());
                                jpb.setValue((Integer) evt.getNewValue());
                            }
                        }
                    });
                    mySwingWorker.execute();
            }
        });
    }

    class MySwingWorker extends SwingWorker<Integer, Integer> {
        private int count = 0;

        @Override
        protected Integer doInBackground() throws Exception {
            System.out.println("MySwingWorker完成了！");
            publishPrimeNumbers(100);
            return null;
        }

        @Override
        protected void process(java.util.List<Integer> list) {
            System.out.println("=======" + list.size());
            for (int i = 0; i < list.size(); i++) {
                System.out.println("循环：" + list.get(i));
            }
            super.process(list);
        }

        private void publishPrimeNumbers(int n) throws InterruptedException {
            while (count <= n) {
                count++;
                Thread.sleep(1 * 100);
                setProgress(100 * count / n);
                publish(count);
            }

        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("下载测试");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new DownloadProgressTest());
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
