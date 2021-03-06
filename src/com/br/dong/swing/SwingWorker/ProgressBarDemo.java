package com.br.dong.swing.SwingWorker;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.swing.SwingWorker
 * AUTHOR: hexOr
 * DATE :2017/4/12 16:04
 * DESCRIPTION:
 */
public class ProgressBarDemo extends JApplet {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JProgressBar jpb = new JProgressBar();                      //定义一个进度条显示进度
    private JTextArea jtaResult = new JTextArea();                      //定义一个文本域显示素数
    private JTextField jtfPrimeCount = new JTextField(8);               //定义一个文本框用于用户填写素数个数
    private JButton jbtnDisplayPrime = new JButton("Display Prime");    //定义一个按钮执行任务

    public ProgressBarDemo() {
        jpb.setStringPainted(true);     //设置显示进度的百分比
        jpb.setValue(0);
        jpb.setMaximum(100);

        //设置文本域自动换行，并且断行不断字
        jtaResult.setWrapStyleWord(true);
        jtaResult.setLineWrap(true);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter the prime number count"));
        panel.add(jtfPrimeCount);
        panel.add(jbtnDisplayPrime);

        add(jpb, BorderLayout.NORTH);
        add(new JScrollPane(jtaResult), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        /***
         * 按钮新增点击事件
         */
        jbtnDisplayPrime.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ComputePrime task = new ComputePrime(Integer.parseInt(jtfPrimeCount.getText()), jtaResult);
                /***
                 * 属性变更方法
                 */
                task.addPropertyChangeListener(new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        //判断改变的属性是否是进度，如果是则获取进度的值并显示在进度条上
                        if("progress".equals(evt.getPropertyName())) {
                            System.out.println("!!!!:"+(Integer)evt.getNewValue());
                            jpb.setValue((Integer)evt.getNewValue());
                        }
                    }
                });

                task.execute();     //执行
            }
        });
    }

    static class ComputePrime extends SwingWorker<Integer, Integer> {

        private int count;
        private JTextArea result;

        public ComputePrime(int count, JTextArea result) {
            this.count = count;
            this.result = result;
        }

        @Override
        protected Integer doInBackground() throws Exception {
            System.out.println("doInBackground............");
            publishPrimeNumbers(count);
            return 0;
        }

        //把找到的素数全部显示出来
        @Override
        protected void process(List<Integer> list) {
            System.out.println("======="+list.size());
            for(int i=0; i<list.size(); i++) {
                result.append(list.get(i) + " ");
            }
            super.process(list);
        }

        private void publishPrimeNumbers(int n) {
            int count = 0;
            int number = 2;

            while(count <= n) {
                //如果是素数
                if(isPrime(number)) {
                    count ++;//素数的数量加一
                    //计算进度
                    System.out.println(100 * count / n+"............");
                    setProgress(100 * count / n);   //设置进度
                    publish(number);                //通过publish方法将找到的素数number发送给process方法
                }

                number ++;
            }
        }

        /***
         * 判断素数
         * @param number
         * @return
         */
        public static boolean isPrime(int number) {
            for(int divisor = 2; divisor <= number / 2; divisor++) {
                if(number % divisor == 0) {
                    return false;
                }
            }

            return true;
        }

    }


}
