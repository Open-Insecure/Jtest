package com.br.dong.swing;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-11
 * Time: 下午5:35
 * To change this template use File | Settings | File Templates.
 */
public class CheckBoxDemo {
    private JLabel likeLabel;
    private JCheckBox bookCheckBox;
    private JCheckBox musiCheckBox;
    private JCheckBox footballCheckBox;
    private JLabel answerLabel;
    public void add(JFrame frame) {
        frame.setTitle("复选框的使用方法");
        frame.setLayout(null);
        likeLabel = new JLabel("爱好：");
        likeLabel.setBounds(60, 100, 40, 20);
        ActionListener likeCheckBoxActionListener = new ActionListener() {// 创建一个公用的ActionEvent事件侦听器
            public void actionPerformed(ActionEvent e) {
                answerLabel.setText("您的爱好有：");
                if (bookCheckBox.isSelected())
                    answerLabel.setText(answerLabel.getText() + "    "
                            + bookCheckBox.getText());
                if (musiCheckBox.isSelected())
                    answerLabel.setText(answerLabel.getText() + "    "
                            + musiCheckBox.getText());
                if (footballCheckBox.isSelected())
                    answerLabel.setText(answerLabel.getText() + "    "
                            + footballCheckBox.getText());
            }
        };
        bookCheckBox = new JCheckBox("读书");// 创建指定文本的复选框
        bookCheckBox.setBounds(120, 100, 60, 20);
        bookCheckBox.addActionListener(likeCheckBoxActionListener);
        musiCheckBox = new JCheckBox("听音乐");// 创建指定文本的复选框
        musiCheckBox.setBounds(190, 100, 70, 20);
        musiCheckBox.addActionListener(likeCheckBoxActionListener);
        footballCheckBox = new JCheckBox("踢足球");// 创建指定文本的复选框
        footballCheckBox.setBounds(270, 100, 70, 20);
        footballCheckBox.addActionListener(likeCheckBoxActionListener);
        answerLabel = new JLabel("");
        answerLabel.setBounds(60, 140, 300, 20);
        frame.add(likeLabel);
        frame.add(bookCheckBox);
        frame.add(musiCheckBox);
        frame.add(footballCheckBox);
        frame.add(answerLabel);
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("利用JFrame创建窗口");// 创建指定标题的JFrame窗口对象
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭按钮的动作为退出窗口
        frame.setSize(400, 300);// 设置窗口大小
        Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();// 获得显示器大小对象
        Dimension frameSize = frame.getSize();// 获得窗口大小对象
        if (frameSize.width > displaySize.width)
            frameSize.width = displaySize.width;// 窗口的宽度不能大于显示器的宽度
        if (frameSize.height > displaySize.height)
            frameSize.height = displaySize.height;// 窗口的高度不能大于显示器的高度
        frame.setLocation((displaySize.width - frameSize.width) / 2,
                (displaySize.height - frameSize.height) / 2);// 设置窗口居中显示器显示
        CheckBoxDemo index = new CheckBoxDemo();
        index.add(frame);// 向JFrame窗口添加标签
        frame.setVisible(true);// 设置窗口为可见的，默认为不可见
    }
}
