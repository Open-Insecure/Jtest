package com.br.dong.httpclientTest.sis001;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-10-10
 * Time: 下午5:00
 * To change this template use File | Settings | File Templates.
 */
public class UploadUI extends JFrame implements ActionListener {
    private JComboBox siteBox;//当前要上传的网站的地址
    private JComboBox witefileBox;//上传的版块是否包含附件，bt的上传附件。txt的不上传附件
    private JComboBox fidBox;//fid
    private JPanel jp,jp1,jp2,jp3;  //开始退出panel，左上panel，右上panel，底部panel
    private JLabel jp1_jl1,jp1_jl2,jp1_jl3,withfile;
    private JTextField jp1_jtf1,jp1_jtf2,jp1_jtf3,end_jtf;
    private JButton jp1_jb1,jp1_jb2;
    private JLabel jp2_jl1,jp2_jl2;
    public static JLabel jp2_jl3,jp2_jl4;
    private JButton jp_jb1,jp_jb2; //开始，退出 按钮
    private JScrollPane jsp;
    public static JTextArea jta;
    //线程池
    private static ExecutorService threadPool= Executors.newCachedThreadPool();

    /**
     * 构造方法初始化界面UI
     */
    public UploadUI(){
       //初始化下拉框
       initJcomboBox();
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
        jp1.setBounds(10, 30, 500, 230);
        jp1.setLayout(null);
        jp1_jl1=new JLabel("选择网站:",JLabel.CENTER);
        jp1_jl1.setBounds(15, 30, 100, 30);
        jp1_jl3=new JLabel("上传的版块的fid:",JLabel.CENTER);
        jp1_jl3.setBounds(15, 65, 100, 30);
        jp1_jl2=new JLabel("选择文件夹:",JLabel.CENTER);
        jp1_jl2.setBounds(15, 100, 100, 30);
        withfile=new JLabel("是否上传附件:",JLabel.CENTER);
        withfile.setBounds(15,130,100,30);
//        jp1_jtf1=new JTextField(10);
//        jp1_jtf1.setBounds(120, 30, 100, 25);
        siteBox.setBounds(120, 30, 100, 25);
        witefileBox.setBounds(120,130,100,25);
//        jp1_jtf2=new JTextField(10);
//        jp1_jtf2.setBounds(120, 65, 100, 25);
        fidBox.setBounds(120, 65, 150, 25);
        jp1_jtf3=new JTextField(10);
        jp1_jtf3.setBounds(120, 100, 100, 25);
        //设置选择文件按钮
        jp1_jb1=new JButton("...");
        jp1_jb1.setBounds(225, 105, 30, 20);
        jp1_jb1.addActionListener(this);
        jp1.add(jp1_jl1);
//        jp1.add(jp1_jtf1);
        jp1.add(siteBox);
        jp1.add(witefileBox);
        jp1.add(fidBox);
        jp1.add(jp1_jl2);
        jp1.add(withfile);
//        jp1.add(jp1_jtf2);
        jp1.add(jp1_jl3);
        jp1.add(jp1_jtf3);
        jp1.add(jp1_jb1);


        //处理底部
        jp3=new JPanel();
        jp3.setBorder(BorderFactory.createTitledBorder("日志"));
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
    public void initJcomboBox(){
        //账号密码list

        List<ComboxBean> sitelist=new ArrayList<ComboxBean>();
        sitelist.add(new ComboxBean("新巴黎","http://107.150.17.66/"));
        sitelist.add(new ComboxBean("MM公寓","http://107.150.3.8/"));
        sitelist.add(new ComboxBean("御花王朝","http://162.220.13.9/"));
        sitelist.add(new ComboxBean("新亲密爱人","http://www.21mybbs.me/"));
        List<ComboxBean> withfilelist=new ArrayList<ComboxBean>();
        withfilelist.add(new ComboxBean("是","yes"));
        withfilelist.add(new ComboxBean("否","no"));
        siteBox=new JComboBox(sitelist.toArray());
        witefileBox=new JComboBox(withfilelist.toArray());
        fidBox=new JComboBox();
        resetFidBox("新巴黎");
        //当网站选中变化的时候，实时更新fid下拉联动
        siteBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                //选中执行一次
                if(e.getStateChange() == ItemEvent.SELECTED){
                    //选中的项目
                    ComboxBean cb=(ComboxBean)e.getItem();
                    resetFidBox(cb.getName());//设置关联的下拉框
                }
            }
        });
    }

    /**
     * 重新设置关联下拉框
     * @param sitename
     */
    public void resetFidBox(String sitename){
        fidBox.removeAllItems();
        if("新巴黎".equals(sitename)){
            fidBox.addItem(new String("25|亚洲无码下载区"));
            fidBox.addItem(new String("75|亚洲有码下载区"));
            fidBox.addItem(new String("26|欧美无码下载区"));
            fidBox.addItem(new String("28|网盘下载区"));
            fidBox.addItem(new String("29|迅雷/电驴下载区"));
            fidBox.addItem(new String("30|情色三级转帖区"));
            fidBox.addItem(new String("27|动漫无码转贴区"));
        }else if("MM公寓".equals(sitename)){
            fidBox.addItem(new String("117|P 2 P 亞 視"));
            fidBox.addItem(new String("430|电 驴 下 载"));
            fidBox.addItem(new String("118|P 2 P 歐 美"));
            fidBox.addItem(new String("396|网 盘 免 空"));
            fidBox.addItem(new String("116|迅 雷 影 視"));
            fidBox.addItem(new String("397|P 2 P 三 级"));
            fidBox.addItem(new String("232|P 2 P 动 漫"));
        } else if("御花王朝".equals(sitename)){
            fidBox.addItem(new String("26|王朝亚洲影视专区"));
            fidBox.addItem(new String("27|王朝欧美影视专区"));
            fidBox.addItem(new String("224|王朝网盘下载专区"));
            fidBox.addItem(new String("29|王朝迅雷影视专区"));
            fidBox.addItem(new String("64|王朝三级影视专区"));
            fidBox.addItem(new String("65|亚洲有码专区"));
            fidBox.addItem(new String("66|亚洲无码专区"));
            fidBox.addItem(new String("28|王朝动漫影视专区"));
        } else if("新亲密爱人".equals(sitename)){
            fidBox.addItem(new String("19|亚洲转帖区"));
            fidBox.addItem(new String("20|欧美转帖区"));
            fidBox.addItem(new String("30|电驴转帖区"));
            fidBox.addItem(new String("28|迅雷转帖区"));
            fidBox.addItem(new String("34|东方情色"));
            fidBox.addItem(new String("35|欧美西洋"));
            fidBox.addItem(new String("36|唯美图区"));
            fidBox.addItem(new String("38|明星丝袜"));
            fidBox.addItem(new String("33|原创自拍"));
            fidBox.addItem(new String("43|都市人妻区"));
            fidBox.addItem(new String("45|青春校园区"));
            fidBox.addItem(new String("44|乱伦小说区"));
            fidBox.addItem(new String("46|电子书下载"));
        }


    }

    public static void main(String[] args) {

        UploadUI ui=new UploadUI();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //开始按钮被点击
        if(e.getSource()==jp_jb1)
        {
            ComboxBean site=(ComboxBean)siteBox.getSelectedItem();
            ComboxBean withfile=(ComboxBean)witefileBox.getSelectedItem();
            String fidselect[]=((String)fidBox.getSelectedItem()).split("\\|");
            System.out.println((String)fidBox.getSelectedItem()+fidselect[0]);
            String fid=fidselect[0];
            String path=jp1_jtf3.getText()+"\\";
            System.out.println(site.getValue()+"|"+withfile.getValue()+"|"+fid+"|"+path);
            //从UI里传来的文件夹绝对路径需要处理下 适配下上传程序
            if("".equals(fid)||"".equals(path)){
                jta.append("请输入fid和文件路径"+"\n");
                return ;
            }
            String temp[]=path.split("\\\\");
            int size=temp.length;
//            System.out.println("size"+size);
//            for(int i=0;i<size;i++){
//                System.out.println(temp[i]);
//            }
            //获得选择的路径的
            String date=temp[size-2];
            String floderName=temp[size-1];
            System.out.println("dd+"+date+"ff"+floderName);
            //发布类型
            String type="";

            //根据网站 放置不同账号
            String username="";
            String passowrd="asd123123";
            if("新巴黎".equals(site.getName())){
                username="ericchena";
            }else if("MM公寓".equals(site.getName())){
                username="一品梅136";
            } else if("御花王朝".equals(site.getName())){
                username="z1073021759";
            } else if("新亲密爱人".equals(site.getName())){
                username="yoii0";
                passowrd="kuangren";
                type="nomal" ;
            }
            //调用线程开始上传
            UploadTask task=new UploadTask(date+","+floderName,path,withfile.getValue(),username ,passowrd,site.getValue(),site.getValue()+"logging.php?action=login&loginsubmit=true",site.getValue()+"post.php?action=newthread&fid="+fid+"&extra=",site.getValue()+"post.php?action=newthread&fid="+fid+"&extra=page%3D1&topicsubmit=yes",type);
            task.start();
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

/**
 * 给Jcombox用的
 */
class ComboxBean{
     private String name;
     private String value;

    @Override
    public String toString() {
        return name;
    }

    ComboxBean(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
