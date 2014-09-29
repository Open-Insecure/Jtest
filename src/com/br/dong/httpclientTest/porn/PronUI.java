package com.br.dong.httpclientTest.porn;

import com.br.dong.utils.DateUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-9-18
 * Time: 下午4:58
 * To change this template use File | Settings | File Templates.
 * 视频采集器UI
 */
public class PronUI extends JFrame implements ActionListener {
    /**
     * @param args
     */
    private JPanel jp,jp1,jp2,jp3;  //开始退出panel，左上panel，右上panel，底部panel
    private JLabel jp1_jl1,jp1_jl2,jp1_jl3;
    private JTextField jp1_jtf1,jp1_jtf2,jp1_jtf3,end_jtf;
    private JButton jp1_jb1,jp1_jb2;
    private JLabel jp2_jl1,jp2_jl2;
    public static JLabel jp2_jl3,jp2_jl4;
    private JButton jp_jb1,jp_jb2; //开始，退出 按钮
    private JScrollPane jsp;
    public static JTextArea jta;

    public PronUI()
    {
        //处理最里面的jp1
        jp=new JPanel();
        jp.setLayout(null);
        jp_jb1=new JButton("开始");
        jp_jb1.addActionListener(this);
        jp_jb1.setBounds(195,200,100,40);

        jp_jb2=new JButton("退出");
        jp_jb2.addActionListener(this);
        jp_jb2.setBounds(345,200,100,40);

        jp.add(jp_jb1);
        jp.add(jp_jb2);

        //处理左上
        jp1=new JPanel();
        jp1.setBorder(BorderFactory.createTitledBorder("设置"));
        jp1.setBounds(10, 30, 300, 150);
        jp1.setLayout(null);
        jp1_jl1=new JLabel("采集起始页数:",JLabel.CENTER);
        jp1_jl1.setBounds(15, 30, 100, 30);
        jp1_jl3=new JLabel("采集结束页数:",JLabel.CENTER);
        jp1_jl3.setBounds(15, 65, 100, 30);
        jp1_jl2=new JLabel("视频存放路径:",JLabel.CENTER);
        jp1_jl2.setBounds(15, 100, 100, 30);
        jp1_jtf1=new JTextField(10);
        jp1_jtf1.setBounds(120, 30, 100, 25);
        jp1_jtf2=new JTextField(10);
        jp1_jtf2.setBounds(120, 65, 100, 25);
        jp1_jtf3=new JTextField(10);
        jp1_jtf3.setBounds(120, 100, 100, 25);
        //设置选择文件按钮
        jp1_jb1=new JButton("...");
        jp1_jb1.setBounds(225, 105, 30, 20);
        jp1_jb1.addActionListener(this);
        jp1.add(jp1_jl1);
        jp1.add(jp1_jtf1);
        jp1.add(jp1_jl2);
        jp1.add(jp1_jtf2);
        jp1.add(jp1_jl3);
        jp1.add(jp1_jtf3);
        jp1.add(jp1_jb1);


        //处理底部
        jp3=new JPanel();
        jp3.setBorder(BorderFactory.createTitledBorder("连接"));
        jp3.setBounds(10, 270, 620, 190);
        jp3.setLayout(null);
        jta=new JTextArea();
        jsp=new JScrollPane(jta);
        jsp.setBounds(10, 20, 600, 160);
        jp3.add(jsp);

        jp.add(jp1);
//        jp.add(jp2);
        jp.add(jp3);

        this.add(jp);
        //窗口设置
        int width= Toolkit.getDefaultToolkit().getScreenSize().width;
        int height=Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setTitle("Dialog");
        this.setSize(650, 500);
        this.setLocation(width/4, height/4-50);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        PronUI mainwin=new PronUI();
//        mainwin.jp1_jtf3.setText("F:\\vedios\\new");
        mainwin.jp1_jtf3.setText("c:\\vedios\\new20140830");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //开始按钮被点击
        if(e.getSource()==jp_jb1)
        {
            //new MyWebServer(jp1_jtf2.getText(),Integer.parseInt(jp1_jtf1.getText()));
            String startPage=jp1_jtf1.getText();
            String endPage=jp1_jtf2.getText();
            String path=jp1_jtf3.getText()+"\\";
            System.out.println("|"+startPage+"|"+endPage+"|"+path);
            //在此验证下日期 做个后备处理
            String today= DateUtil.getDateFolder();
            int deadday=20141201;
            if(Integer.parseInt(today)>deadday) {
                 jta.append("error"+today);
                return ;
            }
            //判断条件 开始下载
            if(startPage!=""&&endPage!=""&&path!=""){
                  PronVideo.getPaging(Integer.parseInt(startPage),Integer.parseInt(endPage),path);
            }
        }
        //退出按钮被点击
        else if(e.getSource()==jp_jb2)
        {
            System.exit(0);
            this.dispose();


        }
        //文件目录选择按钮被点击事件
        else if(e.getSource()==jp1_jb1)
        {
            //本地文件目录选择
            JFileChooser jfc=new JFileChooser();
            //只能选择目录
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.showOpenDialog(this);
            String path=jfc.getSelectedFile().getAbsolutePath();
            jp1_jtf3.setText(path);
        }

    }
}
