package com.br.dong.httpclientTest.sis001;

import com.br.dong.file.FileOperate;
import com.br.dong.utils.DateUtil;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
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
    private JButton jp_jb1,jp_jb2,jb_clear; //开始，退出 ，清除日志 按钮
    private JScrollPane jsp;
    public static JTextArea jta;
    //线程池
    private static ExecutorService threadPool= Executors.newCachedThreadPool();
    //
    private List<UserBean> list_xbl,list_mm,list_yhwc,list_qm,list_yb,list_rxbs,list_mgqr,list_mct,list_lyd,list_cht;
    Random rand = new Random();
    /**
     * 构造方法初始化界面UI
     */
    public UploadUI(){
        //创建日志目录
       FileOperate.newFolderMuti("C:\\logs\\");
       initJcomboBox();   //初始化下拉框
        initAccList();  //初始化各网站账号
        //处理最里面的jp1
        jp=new JPanel();
        jp.setLayout(null);
        jp_jb1=new JButton("开始");
        jp_jb1.addActionListener(this);
        jp_jb1.setBounds(50,200,100,40);

        jb_clear=new JButton("清除日志");
        jb_clear.addActionListener(this);
        jb_clear.setBounds(245,200,100,40);

        jp_jb2=new JButton("退出");
        jp_jb2.addActionListener(this);
        jp_jb2.setBounds(405,200,100,40);



        jp.add(jp_jb1);
        jp.add(jb_clear);
        jp.add(jp_jb2);

        //处理左上
        jp1=new JPanel();
        jp1.setBorder(BorderFactory.createTitledBorder("设置"));
        jp1.setBounds(10, 30, 540, 230);
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
        jsp.setBounds(10, 20, 580, 160);
        jp3.add(jsp);

        jp.add(jp1);
//        jp.add(jp2);
        jp.add(jp3);

        this.add(jp);
        //窗口设置
        int width= Toolkit.getDefaultToolkit().getScreenSize().width;
        int height=Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setTitle("上传程序");
        this.setSize(700, 600);
        this.setLocation(width/4, height/4-50);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * 初始化各个网站账号
     */
    public void initAccList(){
        list_xbl=new ArrayList<UserBean>();
        list_xbl.add(new UserBean("liang93370894","asd123123")) ;
        list_xbl.add(new UserBean("aisi1985","asd123123")) ;
        list_xbl.add(new UserBean("ericchena","asd123123")) ;
        list_xbl.add(new UserBean("a706089578","asd123123")) ;
        list_xbl.add(new UserBean("nishiniya","asd123123")) ;
        list_xbl.add(new UserBean("pcs","asd123123")) ;
        list_mm=new ArrayList<UserBean>();
        list_mm.add(new UserBean("一品梅136","asd123123"));
        list_mm.add(new UserBean("难得装bi","asd123123"));
        list_mm.add(new UserBean("小乖乖3","asd123123"));
        list_mm.add(new UserBean("不是女人","asd123123"));
        list_mm.add(new UserBean("幽冥太子","asd123123"));
        list_mm.add(new UserBean("阿雄本色","asd123123"));
        list_yhwc=new ArrayList<UserBean>();
        list_yhwc.add(new UserBean("宇文化骨","asd123123"));
        list_yhwc.add(new UserBean("半城明月","asd123123"));
        list_yhwc.add(new UserBean("魔鬼中的天使888","asd123123"));
        list_yhwc.add(new UserBean("富贵人家","asd123123"));
        list_yhwc.add(new UserBean("宇文化骨","asd123123"));
        list_yhwc.add(new UserBean("z1073021759","asd123123"));
        list_qm=new ArrayList<UserBean>();
        list_qm.add(new UserBean("zhoushao120","qwert"));
        list_qm.add(new UserBean("ak47990","qwert"));
        list_qm.add(new UserBean("airen1999","qwert"));
        list_qm.add(new UserBean("loli2014","qwert"));
        list_yb=new ArrayList<UserBean>();
        list_yb.add(new UserBean("shj801220","qwert"));
        list_yb.add(new UserBean("7964915","qwert"));
        list_yb.add(new UserBean("feiliyax","qwert"));
        list_yb.add(new UserBean("nash110120","qwert"));
        list_yb.add(new UserBean("amd1800","qwert"));
        list_rxbs=new ArrayList<UserBean>();
        list_rxbs.add(new UserBean("rxbs001","aaa123456"));
        list_rxbs.add(new UserBean("rxbs999","aaa123456"));
        list_rxbs.add(new UserBean("zhaowei1982328","qwert"));
        list_rxbs.add(new UserBean("xiaws5693","qwert"));
        list_rxbs.add(new UserBean("zjjhuhao110","qwert"));
        list_mgqr=new ArrayList<UserBean>();
        list_mgqr.add(new UserBean("session1987","qwert"));
        list_mgqr.add(new UserBean("smilei34516","qwert"));
        list_mgqr.add(new UserBean("laoniu4510","qwert"));
        list_mgqr.add(new UserBean("youchouboy","qwert"));
        list_mct=new ArrayList<UserBean>();
        list_mct.add(new UserBean("天在看","qwerty"));
        list_mct.add(new UserBean("曲茎通幽","qwerty"));
        list_mct.add(new UserBean("操之过急","qwerty"));
        list_mct.add(new UserBean("前扑后鸡","qwerty"));
        list_mct.add(new UserBean("性图","qwerty"));
        list_lyd=new ArrayList<UserBean>();
        list_lyd.add(new UserBean("ycfbx","qwert"));
        list_lyd.add(new UserBean("yeyeye2","qwert"));
        list_lyd.add(new UserBean("liboguxin","qwert"));
        list_lyd.add(new UserBean("daigaochao","qwert"));
        list_lyd.add(new UserBean("zxxx1314","qwert"));
        list_cht=new ArrayList<UserBean>();
        list_cht.add(new UserBean("撕人野","qwerty"));
        list_cht.add(new UserBean("金三胖","qwerty"));
        list_cht.add(new UserBean("失联","qwerty"));
        list_cht.add(new UserBean("男上女下","qwerty"));
        list_cht.add(new UserBean("放开","qwerty"));
    }

    public void initJcomboBox(){
        //账号密码list
        List<ComboxBean> sitelist=new ArrayList<ComboxBean>();
        //discuz 6.0版本
        sitelist.add(new ComboxBean("新巴黎","http://107.150.17.66/"));
        sitelist.add(new ComboxBean("MM公寓","http://107.150.3.8/"));
        sitelist.add(new ComboxBean("御花王朝","http://162.220.13.9/"));
        sitelist.add(new ComboxBean("新亲密爱人","http://www.21mybbs.me/"));
        sitelist.add(new ComboxBean("夜吧","http://107.150.15.60/"));
        sitelist.add(new ComboxBean("人性本色","http://63.141.255.218:8085/"));
        sitelist.add(new ComboxBean("玫瑰情人","http://63.141.255.218:8086/"));
        sitelist.add(new ComboxBean("买春堂","http://maichun.org/"));
        sitelist.add(new ComboxBean("炼狱岛","http://lianyu.org/"));
        sitelist.add(new ComboxBean("采花堂","http://www.caihua.info/"));
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
        fidBox.removeAllItems();//移除以前加载的
        if("新巴黎".equals(sitename)){
            fidBox.addItem(new String("25|亚洲无码下载区"));
            fidBox.addItem(new String("75|亚洲有码下载区"));
            fidBox.addItem(new String("26|欧美无码下载区"));
            fidBox.addItem(new String("28|网盘下载区"));
            fidBox.addItem(new String("29|迅雷/电驴下载区"));
            fidBox.addItem(new String("30|情色三级转帖区"));
            fidBox.addItem(new String("27|动漫无码转贴区"));
            fidBox.addItem(new String("15|新人试贴区"));
            fidBox.addItem(new String("22|明星图片区"));
            fidBox.addItem(new String("18|亚洲图片区"));
            fidBox.addItem(new String("19|欧美图片区"));
            fidBox.addItem(new String("20|自拍偷拍区"));
            fidBox.addItem(new String("21|卡通漫画区"));
            fidBox.addItem(new String("39|精品套图区"));
        }else if("MM公寓".equals(sitename)){
            fidBox.addItem(new String("117|P 2 P 亞 視"));
            fidBox.addItem(new String("430|电 驴 下 载"));
            fidBox.addItem(new String("118|P 2 P 歐 美"));
            fidBox.addItem(new String("396|网 盘 免 空"));
            fidBox.addItem(new String("116|迅 雷 影 視"));
            fidBox.addItem(new String("397|P 2 P 三 级"));
            fidBox.addItem(new String("232|P 2 P 动 漫"));
            fidBox.addItem(new String("492|〓 新 人 图 片 试 帖 〓"));
            fidBox.addItem(new String("369|〓 會 員 自 拍 〓"));
            fidBox.addItem(new String("74|〓 套 圖 下 載 〓"));
            fidBox.addItem(new String("34|〓 東 方 美 眉 〓"));
            fidBox.addItem(new String("35|〓 西 方 美 女 〓"));
            fidBox.addItem(new String("36|〓 自 拍 偷 拍 〓"));
            fidBox.addItem(new String("73|〓 卡 通 動 漫 〓"));
            fidBox.addItem(new String("75|〓 泳 装 絲 襪 〓"));
            fidBox.addItem(new String("72|〓 明 星 貼 圖 〓"));
            fidBox.addItem(new String("76|〓 唯 美 貼 圖 〓"));
            fidBox.addItem(new String("491|〓 新 人 小 说 试 帖 〓"));
            fidBox.addItem(new String("389|〓 Ebook 小說 〓"));
            fidBox.addItem(new String("236|〓 有 声 有 色 〓"));
            fidBox.addItem(new String("77|〓 现 代 迷 情 〓"));
            fidBox.addItem(new String("44|〓 乱 伦 人 妻 〓"));
            fidBox.addItem(new String("47|〓 武 侠 玄 幻 〓"));

        } else if("御花王朝".equals(sitename)){
            fidBox.addItem(new String("26|王朝亚洲影视专区"));
            fidBox.addItem(new String("27|王朝欧美影视专区"));
            fidBox.addItem(new String("224|王朝网盘下载专区"));
            fidBox.addItem(new String("29|王朝迅雷影视专区"));
            fidBox.addItem(new String("64|王朝三级影视专区"));
            fidBox.addItem(new String("65|亚洲有码专区"));
            fidBox.addItem(new String("66|亚洲无码专区"));
            fidBox.addItem(new String("28|王朝动漫影视专区"));
            fidBox.addItem(new String("220|王朝自拍偷拍區"));
            fidBox.addItem(new String("18|王朝日韩貼圖區"));
            fidBox.addItem(new String("19|王朝欧洲貼圖區"));
            fidBox.addItem(new String("20|王朝写真丝袜區"));
            fidBox.addItem(new String("21|王朝卡通成人區"));
            fidBox.addItem(new String("23|王朝明星合成區"));
            fidBox.addItem(new String("302|王朝同性贴图區"));
            fidBox.addItem(new String("16|王朝激情乱伦區"));
            fidBox.addItem(new String("15|王朝现代都市區"));
            fidBox.addItem(new String("17|王朝武侠另类區"));
            fidBox.addItem(new String("298|Ebook-名家合集"));

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
        }else if("夜吧".equals(sitename)){
            fidBox.addItem(new String("59|迅雷成人下载区"));
            fidBox.addItem(new String("57|BT亚洲成人下载区"));
            fidBox.addItem(new String("81|欧美无码电影分享区"));
            fidBox.addItem(new String("84|BT动漫成人下载专区"));
            fidBox.addItem(new String("83|网盘成人下载区"));
            fidBox.addItem(new String("69|日韩辣妹贴图区"));
            fidBox.addItem(new String("70|欧美靓女贴图区"));
            fidBox.addItem(new String("71|高跟美足丝袜区"));
            fidBox.addItem(new String("72|网友自拍贴图分享区"));
            fidBox.addItem(new String("80|卡通动漫贴图区"));
            fidBox.addItem(new String("79|同性贴图区"));
            fidBox.addItem(new String("77|人体艺术集中营"));
            fidBox.addItem(new String("63|校园书生"));
            fidBox.addItem(new String("64|乱伦迷情"));
            fidBox.addItem(new String("65|武侠玄幻"));
            fidBox.addItem(new String("66|EBOOK"));
        } else if("人性本色".equals(sitename)){
            fidBox.addItem(new String("33|〓 亚 洲 影 视 〓"));
            fidBox.addItem(new String("34|〓 欧 美 影 视 〓"));
            fidBox.addItem(new String("37|〓 三 级 影 视 〓"));
            fidBox.addItem(new String("35| 〓 在 线 影 视 〓"));
            fidBox.addItem(new String("36| 〓 手 机 视 频 〓"));
            fidBox.addItem(new String("84|〓 卡 通 动 漫 〓"));
            fidBox.addItem(new String("38|〓 都 市 情 感 〓"));
            fidBox.addItem(new String("40|〓 乱 伦 天 地 〓"));
            fidBox.addItem(new String("39|〓 玄 幻 武 侠 〓"));
            fidBox.addItem(new String("41|〓 长 篇 连 载 〓"));
            fidBox.addItem(new String("83|〓 清 纯 丝 袜 〓"));
            fidBox.addItem(new String("28|〓 亚 洲 美 图 〓"));
            fidBox.addItem(new String("30|〓 自 拍 偷 窥 〓"));
            fidBox.addItem(new String("29|〓 欧 美 贴 图 〓"));
            fidBox.addItem(new String("31|〓 明 星 名 模 〓"));
            fidBox.addItem(new String("32|〓 卡 通 动 漫 〓"));
            fidBox.addItem(new String("77|〓 套 图 欣 赏 〓"));
        } else if("玫瑰情人".equals(sitename)){
            fidBox.addItem(new String("75|◇→清纯唯美←◇"));
            fidBox.addItem(new String("2|◇→东方丽人←◇"));
            fidBox.addItem(new String("4|◇→西洋靓女←◇"));
            fidBox.addItem(new String("3|◇→偷拍自拍←◇"));
            fidBox.addItem(new String("5|◇→卡通动漫←◇"));
            fidBox.addItem(new String("76|◇→套图欣赏←◇"));
            fidBox.addItem(new String("11|◇→亚洲BT←◇"));
            fidBox.addItem(new String("12|◇→欧美BT←◇"));
            fidBox.addItem(new String("72|◇→网盘影视←◇"));
            fidBox.addItem(new String("32|◇→在线影视←◇"));
            fidBox.addItem(new String("13|◇→讯雷下载←◇"));
            fidBox.addItem(new String("14|◇→动漫影视←◇"));
            fidBox.addItem(new String("89|◇→校园明星←◇"));
            fidBox.addItem(new String("16|◇→都市激情←◇"));
            fidBox.addItem(new String("21|◇→武侠玄幻←◇"));
            fidBox.addItem(new String("17|◇→熟女乱伦←◇"));
            fidBox.addItem(new String("22|◇→长篇小说←◇"));
            fidBox.addItem(new String("90|◇→IE BOOK←◇"));
        }else if("买春堂".equals(sitename)){
            fidBox.addItem(new String("1004|亚洲风情"));
            fidBox.addItem(new String("83|欧美风情"));
            fidBox.addItem(new String("615|自拍风情"));
            fidBox.addItem(new String("645|亚洲BT"));
            fidBox.addItem(new String("425|欧美BT"));
            fidBox.addItem(new String("1003|在线视频"));

        }  else if("炼狱岛".equals(sitename)){
            fidBox.addItem(new String("28|〓 亚 洲 美 图 〓"));
            fidBox.addItem(new String("29|〓 欧 美 贴 图 〓"));
            fidBox.addItem(new String("30|〓 自 拍 偷 窥 〓"));
            fidBox.addItem(new String("31|〓 明 星 名 模 〓"));
            fidBox.addItem(new String("32|〓 卡 通 动 漫 〓"));
            fidBox.addItem(new String("83|〓 清 纯 丝 袜 〓"));
            fidBox.addItem(new String("77|〓 套 图 欣 赏 〓"));
            fidBox.addItem(new String("33|〓 亚 洲 影 视 〓"));
            fidBox.addItem(new String("34|〓 欧 美 影 视 〓"));
            fidBox.addItem(new String("37|〓 三 级 影 视 〓"));
            fidBox.addItem(new String("35|〓 在 线 影 视 〓"));
            fidBox.addItem(new String("84|〓 卡 通 动 漫 〓"));
            fidBox.addItem(new String("38|〓 都 市 情 感 〓"));
            fidBox.addItem(new String("40|〓 乱 伦 天 地 〓"));
            fidBox.addItem(new String("39|〓 玄 幻 武 侠 〓"));
            fidBox.addItem(new String("41|〓 长 篇 连 载 〓"));
            fidBox.addItem(new String("42|〓 有 声 小 说 〓"));
        }else if("采花堂".equals(sitename)){
            fidBox.addItem(new String("392|〓 自 拍 偷 拍 〓"));
            fidBox.addItem(new String("423|〓 唯 美 原 创 〓"));
            fidBox.addItem(new String("173|〓 高 跟 丝 袜 〓"));
            fidBox.addItem(new String("417|〓 多 彩 动 漫 〓"));
            fidBox.addItem(new String("420|〓三级影视区〓"));
            fidBox.addItem(new String("675|〓在线视频区〓"));
            fidBox.addItem(new String("674|◇◇ 欧 美 B T 区"));
            fidBox.addItem(new String("675|◇◇ 亚 洲 B T 区"));
        }
    }
    public static void main(String[] args) {
        try{
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;//设置窗口边框类型
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();//将界面转化为Beauty Eye外观
        }catch (Exception e){
            System.out.println("初始化失败");
        }
        //隐藏设置按钮
        UIManager.put("RootPane.setupButtonVisible",false);
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
            jta.append("发布目录："+path+"\n");
            //发布类型
            String type="";
            //根据网站 放置不同账号
            String username="";
            String passowrd="";
            if("新巴黎".equals(site.getName())){
                UserBean bean = list_xbl.get(rand.nextInt(list_xbl.size()));
                username=bean.getUsername();
                 passowrd=bean.getPassword();
            }else if("MM公寓".equals(site.getName())){
                UserBean bean = list_mm.get(rand.nextInt(list_mm.size()));
                username=bean.getUsername();
                passowrd=bean.getPassword();
            } else if("御花王朝".equals(site.getName())){
                UserBean bean = list_yhwc.get(rand.nextInt(list_xbl.size()));
                username=bean.getUsername();
                passowrd=bean.getPassword();
            } else if("新亲密爱人".equals(site.getName())){
                UserBean bean = list_qm.get(rand.nextInt(list_qm.size()));
                username=bean.getUsername();
                passowrd=bean.getPassword();
//                type="nomal" ;
            }else if("夜吧".equals(site.getName())){
                UserBean bean = list_yb.get(rand.nextInt(list_yb.size()));
                username=bean.getUsername();
                passowrd=bean.getPassword();
            }else if("人性本色".equals(site.getName())){
                UserBean bean = list_rxbs.get(rand.nextInt(list_rxbs.size()));
                username=bean.getUsername();
                passowrd=bean.getPassword();
            } else if("玫瑰情人".equals(site.getName())){
                UserBean bean = list_mgqr.get(rand.nextInt(list_xbl.size()));
                username=bean.getUsername();
                passowrd=bean.getPassword();
            }     else if("炼狱岛".equals(site.getName())){
                UserBean bean = list_lyd.get(rand.nextInt(list_lyd.size()));
                username=bean.getUsername();
                passowrd=bean.getPassword();
                //调用线程开始上传
                UploadTask task=new  UploadTask(date+","+floderName,path,withfile.getValue(),username,passowrd,site.getValue()+"login.php?",site.getValue()+"post.php?fid="+fid,site.getValue()+"post.php?","phpwind,"+fid);
                task.start();
                return ;
            } else if("买春堂".equals(site.getName())){
                UserBean bean = list_mct.get(rand.nextInt(list_mct.size()));
                username=bean.getUsername();
                passowrd=bean.getPassword();
                //调用线程开始上传
                UploadTask task=new  UploadTask(date+","+floderName,path,withfile.getValue(),username,passowrd,site.getValue()+"login.php?",site.getValue()+"post.php?fid-"+fid+".htm",site.getValue()+"post.php?","phpwind,"+fid);
                task.start();
                return;
            } else if("采花堂".equals(site.getName())){
                UserBean bean = list_cht.get(rand.nextInt(list_cht.size()));
                username=bean.getUsername();
                passowrd=bean.getPassword();
                //调用线程开始上传
                UploadTask task=new  UploadTask(date+","+floderName,path,withfile.getValue(),username,passowrd,site.getValue()+"login.php?",site.getValue()+"post.php?fid="+fid,site.getValue()+"post.php?","phpwind,"+fid);
                task.start();
                return;
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
        //清除日志按钮被点击
        else if(e.getSource()==jb_clear){
            jta.setText("");
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
