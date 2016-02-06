package com.br.dong.httpclientTest.porn.new_91porn_2016;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.VedioBean;
import com.br.dong.httpclientTest.porn.new_91porn_2016.componet.JImageComponent;
import com.br.dong.httpclientTest.porn.new_91porn_2016.thread.Crawler91pThread;
import com.br.dong.httpclientTest.porn.new_91porn_2016.thread.Thread91pEvent;
import com.br.dong.httpclientTest.porn.new_91porn_2016.thread.Thread91pListener;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2016-01-09
 * Time: 18:40
 * ava Swing中，界面刷新是线程同步的，也就是说同一时间。
 * 只有一个线程能执行刷新界面的代码。如果要多次不断地刷新界面，必须在多线程中调用刷新的方法
 * java swing中两大原则:
 *  1. 不要阻塞UI线程
 *  2. 不要在UI线程外的线程去操作UI控件
 */
public class SwingUI extends JFrame implements ActionListener {
    /**界面主panel*/
    private JPanel jp_main;
    /**设置视频列表panel*/
    private JPanel jp_list;
    /**视频列表右边的按钮panel*/
//    private JPanel jp_buttons_list;
    /**界面中部视频详情信息*/
    private JPanel jp_video_info;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JButton button1;
    /**视频列表中的一个视频单元*/
    /**线程监听*/
    private Thread91pListener listener;
    public static void main(String[] args) {
        try{
            /**设置窗口边框类型*/
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
            /**将界面转化为Beauty Eye外观*/
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        }catch (Exception e){
            System.out.println("初始化失败");
        }
        /**隐藏设置按钮*/
        UIManager.put("RootPane.setupButtonVisible", false);
        /**在单独的ui线程中进行线程的更新操作*/
        SwingUI ui=new SwingUI();

    }
    /**
     * 构造方法
     */
    public SwingUI(){
        /**获得系统屏分辨率参数*/
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dimension = kit.getScreenSize();
//        int width=dimension.width;
//        int height=dimension.height;
        int width=800;
        int height=600;
        /**主界面panel*/
        jp_main=new JPanel();
        jp_main.setLayout(null);
        this.add(jp_main);
        /**处理视频列表信息panel*/
        jp_list=new JPanel();
        jp_list.setBorder(BorderFactory.createTitledBorder("视频列表"));
        jp_list.setBounds(10, 30, width - 100, height / 2);
        jp_list.setLayout(new FlowLayout());
        /**处理视频列表右边的按钮panel 垂直的布局*/
//        jp_buttons_list=new JPanel();
//        GridLayout gridLayout=new GridLayout(4,4);
//        gridLayout.setVgap(10);
//        gridLayout.setHgap(10);
//        jp_buttons_list.setLayout(gridLayout);
//        jp_buttons_list.setBounds(width - (width / 4) + 40, 40, (width / 8), (height - (height / 2)) / 2);
//        JButton b1 = new JButton("click me");
//        b1.addActionListener(this);
//        jp_buttons_list.add(b1);
//        JButton b2 = new JButton("click me");
//        b1.addActionListener(this);
//        jp_buttons_list.add(b2);
//        JButton b3 = new JButton("click me");
//        b1.addActionListener(this);
//        jp_buttons_list.add(b3);
//        JButton b4 = new JButton("click me");
//        b1.addActionListener(this);
//        jp_buttons_list.add(b4);
//        JButton b5 = new JButton("click me");
//        b1.addActionListener(this);
//        jp_buttons_list.add(b5);
//        JButton b6 = new JButton("click me");
//        b1.addActionListener(this);
//        jp_buttons_list.add(b6);
//        JButton b7 = new JButton("click me");
//        b1.addActionListener(this);
//        jp_buttons_list.add(b7);
//        JButton b8 = new JButton("click me");
//        b1.addActionListener(this);
//        jp_buttons_list.add(b8);
        /**视频详情panel*/
        jp_video_info=new JPanel();
        jp_video_info.setBounds(10, height / 2 + 40, width / 2, height / 4);
        jp_video_info.setBorder(BorderFactory.createTitledBorder("视频信息"));
        /**设置字体*/
        Font font=new Font("微软雅黑",Font.PLAIN,18);
        label1 = new JLabel("视频标题:");
        label1.setFont(font);
        label2 = new JLabel("视频时长:");
        label2.setFont(font);
        label3 = new JLabel("测试标题！！！！sssssssssssssssssssssssssssss");
        label3.setFont(font);
        label4 = new JLabel("3:00");
        label4.setFont(font);
        button1=new JButton("复制");
        /**设置布局*/
        GroupLayout layout = new GroupLayout(jp_video_info);
        jp_video_info.setLayout(layout);
        /**垂直布局group*/
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        /**垂直布局的第一块垂直*/
        hGroup.addGroup(layout.createParallelGroup().addComponent(label1)
                .addComponent(label2));
        /**垂直布局的第二块垂直*/
        hGroup.addGroup(layout.createParallelGroup().addComponent(label3)
                .addComponent(label4));
        /**垂直布局的第三块垂直*/
        hGroup.addGroup(layout.createParallelGroup().addComponent(button1));
        /**水平布局group*/
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        /**水平布局的第一块垂直*/
        vGroup.addGroup(layout.createParallelGroup().addComponent(label1)
                .addComponent(label3).addComponent(button1));
        /**水平布局的第二块垂直*/
        vGroup.addGroup(layout.createParallelGroup().addComponent(label2)
                .addComponent(label4));
        /**设置此布局的*/
        layout.setHorizontalGroup(hGroup);
        layout.setVerticalGroup(vGroup);
        /**主界面添加*/
        jp_main.add(jp_list);
//        jp_main.add(jp_buttons_list);
        jp_main.add(jp_video_info);
        this.setTitle("91助手 V1.0");
        System.out.println(width + ".." + height);
        /**设置主界面大小*/
        this.setBounds(0, -10, width, height);
        /**获得视频信息线程的监听器*/
        listener=new Thread91pListener() {
            @Override
            public void complete(Thread91pEvent event) {
                /**获得完成线程后的视频列表信息*/
                final List<VedioBean> list=((Crawler91pThread)event.getTarget()).getList();
                /**启用ui线程去加载网络图片*/
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        CrawlerUtil crawlerUtil = new CrawlerUtil();
                        try {
                            crawlerUtil.clientCreate("http", "img2.t6k.co", "http://ch.u6p.co/v.php?next=watch");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        for (VedioBean bean : list) {
                            JImageComponent image = new JImageComponent();
                            try {
                                image.loadImage(bean, crawlerUtil);
                                jp_list.add(image);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
            @Override
            public void onError(Thread91pEvent event) {
                System.out.println(event.getMessage());
            }
        };
        Crawler91pThread thread=new Crawler91pThread("test",listener);
        thread.start();
        /**设置关闭后清空内存*/
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * 处理按钮的点击事件
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
