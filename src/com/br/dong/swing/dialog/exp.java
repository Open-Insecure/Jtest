package com.br.dong.swing.dialog;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2016-01-20
 * Time: 18:27
 */
import javax.swing.*;
public class exp
{
    public static void main(String[] args)
    {
        Object[] MyButtons = {new JButton("确定"),new JButton("取消")};
        Object[] MyChoice = {"红心","黑桃","方块","梅花"};
        JFrame jf = new JFrame("使用JOptionPane对话框");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().add(new JLabel("使用对话框很简单"));
        jf.setBounds(0,0,250,100);
        jf.setVisible(true);
        JOptionPane.showOptionDialog(jf, "我是自定义对话框", "我是标题字符串", JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE, null, MyButtons, MyButtons[0]);
        JOptionPane.showInputDialog(jf, "输入文本框","标题字符串在此",JOptionPane.INFORMATION_MESSAGE,null,MyChoice,MyChoice[2]);
        JOptionPane.showMessageDialog(jf, "你想知道什么信息呢？","知道我是谁了吧",JOptionPane.WARNING_MESSAGE,null);
        JOptionPane.showConfirmDialog(null, "快按确定吧", "你学会了吗", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null);
    }
}
