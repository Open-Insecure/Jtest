package com.br.dong.swing.dialog;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2016-01-20
 * Time: 18:21
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
public class TestJOptionPane implements ActionListener{
    private JFrame jf = new JFrame("标准对话框测试");
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new TestJOptionPane().createUI();
    }
    public void createUI(){
        JToolBar jtb = new JToolBar();
        String[] s = {"错误", "退出确认1", "退出确认2", "警告", "输入", "选择"};
        int size = s.length;
        JButton[] button = new JButton[size];
        for(int i = 0; i < size; i++){
            button[i] = new JButton(s[i]);
            button[i].addActionListener(this);
            jtb.add(button[i]);
        }
        jf.add(jtb, "North");
        jf.setSize(350, 150);
        jf.setLocation(400, 200);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String s = e.getActionCommand();
        if(s.equals("错误")){
            JOptionPane.showMessageDialog(null, "要显示的错误信息---",
                    "错误提示",JOptionPane.ERROR_MESSAGE);
        }
        else if(s.equals("退出确认1")){
            int result = JOptionPane.showConfirmDialog(null,
                    "推出前是否保存程序？");
            if(result == JOptionPane.YES_OPTION){
                System.out.println("保存程序---");
                System.exit(0);
            }
            else if(result == JOptionPane.NO_OPTION){
                System.exit(0);
            }
        }
        else if(s.equals("退出确认2")){
            int result = JOptionPane.showConfirmDialog(null, "退出前是否保存程序？");
            if(result == JOptionPane.YES_OPTION){
                System.out.println("保存程序---");
                System.exit(0);
            }
            else if(result == JOptionPane.NO_OPTION){
                System.exit(0);
            }
        }
        else if(s.equals("警告")){
            Object[] options = {"继续", "撤销"};
            int result = JOptionPane.showOptionDialog(null,
                    "本操作可能导致数据丢失","Warning", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            if(result == 0){
                System.out.println("继续操作---");
            }
        }
        else if(s.equals("输入")){
            String name = JOptionPane.showInputDialog("请输入您的姓名：");
            if(name != null){
                System.out.println("姓名：" + name);
            }
        }
        else if(s.equals("选择")){
            Object[] possibleValues = {"体育", "政治", "经济", "文化"};
            Object selectedValue = JOptionPane.showInputDialog(null,
                    "Choose one","Input", JOptionPane.INFORMATION_MESSAGE, null,
                    possibleValues, possibleValues[0]);
            String choose = (String)selectedValue;
            if(choose != null){
                System.out.println("你选择的是："+ choose);
            }
        }
    }
}
