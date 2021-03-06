package com.br.dong.swing.dynami;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2016-01-10
 * Time: 14:18
 */
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class DynamicAdd  extends JFrame{
    Container con=this.getContentPane();
    JButton jb1=new JButton("jb1");
    JButton jb2=new JButton("jb2");
    JLabel jl1=new JLabel("jl1");
    FlowLayout gly=new FlowLayout();
    JPanel jp=new JPanel(gly);
    public DynamicAdd(){
        con.add(jp);

        jp.add(jb1);
        jp.add(jb2);

        MyListener ml=new MyListener();
        jb1.addMouseListener(ml);
        this.setSize(200,200);
        this.setVisible(true);
    }

    private class MyListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            try
            {
                //主要就是下面的invalidate和validate
                //当然，用jp来invalidate和validatae也是可以的
                con.invalidate();
                JButton jb3=new JButton("jb3");
                jp.add(jb3);
                con.validate();                }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    public static void main(String s[]){
        DynamicAdd sss=new DynamicAdd();
        sss.setVisible(true);
    }
}
