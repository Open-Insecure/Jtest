package com.br.dong.swing.menu;
import java.awt.*;
import  java.awt.event.ActionEvent;
import  java.awt.event.ActionListener;    
import  java.awt.event.InputEvent;    
import  java.awt.event.KeyEvent;    
import  java.awt.event.MouseEvent;    
import  java.awt.event.MouseListener;    
            
import  javax.swing.ButtonGroup;    
import  javax.swing.JCheckBox;    
import  javax.swing.JCheckBoxMenuItem;    
import  javax.swing.JComboBox;    
import  javax.swing.JFrame;    
import  javax.swing.JMenu;    
import  javax.swing.JMenuBar;    
import  javax.swing.JMenuItem;    
import  javax.swing.JPopupMenu;    
import  javax.swing.JRadioButton;    
import  javax.swing.JRadioButtonMenuItem;    
import  javax.swing.JScrollPane;    
import  javax.swing.JTextArea;    
import  javax.swing.JToolBar;    
import  javax.swing.KeyStroke;
/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.swing.menu
 * AUTHOR: hexOr
 * DATE :2017/4/14 8:47
 * DESCRIPTION:
 */
public class EditorJFrame2 extends JFrame implements MouseListener{  
          
            private JComboBox combox_name,combox_size;//字体、字号组合框  
            private JCheckBox checkb_bold,checkb_italic;//粗体斜体复选框  
            private JRadioButton radiob_color[];//颜色单选按钮  
            private JTextArea text;  
            private Color color;
            private JPopupMenu popupmenu;//快捷菜单  
              
            public EditorJFrame2(){  
                super("文本编辑器");//默认BorderLayout布局  
                Dimension dim = getToolkit().getScreenSize();  
                setBounds(dim.width/4,dim.height/4,dim.width/2,dim.height/2);  
                setDefaultCloseOperation(EXIT_ON_CLOSE);  
                text = new JTextArea("Welcome 欢迎");  
                add(new JScrollPane(text));   
                  
                addmyMenu();//菜单栏  
                addmyJToolbar();//工具栏  
                addmyJPopupMenu();//文本域的快捷键  
                setVisible(true);  
            }  
              
            private void addmyMenu(){//菜单栏部分  
                JMenuBar menubar = new JMenuBar();//菜单栏  
                setJMenuBar(menubar);  
                String menustr[] = {"文件","编辑","帮助"};  
                JMenu menu[]=new JMenu[menustr.length];//菜单  
                for(int i=0;i<menustr.length;i++){  
                    menu[i] = new JMenu(menustr[i]);  
                    menubar.add(menu[i]);  
                }  
                  
                  
                menu[0].add(new JMenuItem("打开"));//菜单项加入到菜单"文件"里  
                menu[0].add(new JMenuItem("保存"));  
                menu[0].addSeparator();//添加分割线  
                JMenuItem menuitem_exit = new JMenuItem("退出");  
                menu[0].add(menuitem_exit);  
                //  
                  
                JMenu menu_style = new JMenu("字形");//菜单  
                menu_style.add(new JCheckBoxMenuItem("粗体"));  
                menu_style.add(new JCheckBoxMenuItem("斜体"));  
                menu[1].add(menu_style);  
                  
                JMenu menu_color = new JMenu("颜色");//菜单  
                menu[1].add(menu_color);  
                ButtonGroup buttongroup = new ButtonGroup();//按钮组  
                String colorstr[]={"红","绿","蓝"};  
                JRadioButtonMenuItem rbmi_color[]=new JRadioButtonMenuItem[colorstr.length];//单选菜单项  
                for(int i =0;i<colorstr.length;i++){  
                    rbmi_color[i] = new JRadioButtonMenuItem(colorstr[i]);  
                    buttongroup.add(rbmi_color[i]);//单选菜单项添加到按钮组中  
                    //  
                    menu_color.add(rbmi_color[i]);//单选菜单项添加到菜单中  
                }  
                  
                menu[2].add(new JMenuItem("关于作者"));  
            }  
              
            private void addmyJToolbar(){//工具栏部分  
                JToolBar toolbar=new JToolBar(); //创建工具栏，默认水平方向  
                add(toolbar,"North"); //工具栏添加到框架内容窗格北部  
                  
                GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();  
                String[] fontsName=ge.getAvailableFontFamilyNames(); //获得系统字体  
                combox_name = new JComboBox(fontsName);    //组合框显示系统字体      
                //  
                toolbar.add(combox_name);//字体组合框加到工具栏里  
                //设置组合框的宽度200和高度30  
                combox_name.setMaximumSize(new   Dimension(260,30));  
                 
                String sizestr[]={"20","30","40","50","60","70"};  
                combox_size = new JComboBox(sizestr);      //数组加到字号组合框  
                //组合框默认不可编辑  
                combox_size.setEditable(true);       //设置组合框可编辑       
                //  
                combox_size.setMaximumSize(new   Dimension(180,30));  
                toolbar.add(combox_size);//字体组合框加到工具栏里  
          
                checkb_bold = new JCheckBox("粗体");               //字形复选框  
                toolbar.add(checkb_bold);  
                //  
                checkb_italic = new JCheckBox("斜体");  
                toolbar.add(checkb_italic);  
                //  
          
                String colorstr[]={"红","绿","蓝"};  
                ButtonGroup bgroup_color = new ButtonGroup();      //按钮组  
                radiob_color = new JRadioButton[colorstr.length];//颜色单选按钮数组  
                for (int i=0; i<radiob_color.length; i++)  
                {  
                    radiob_color[i]=new JRadioButton(colorstr[i]); //颜色单选按钮  
                    //  
                    bgroup_color.add(radiob_color[i]);        //单选按钮添加到按钮组  
                    toolbar.add(radiob_color[i]);      ////////单选按钮添加到工具栏  
                }          
                radiob_color[0].setSelected(true);     //设置单选按钮的选中状态  
            }  
              
            private void addmyJPopupMenu(){  
                popupmenu = new JPopupMenu();       //快捷菜单对象,或弹出菜单  
                String menuitemstr[]={"复制","粘贴","剪切"};  
                JMenuItem popmenuitem[] = new JMenuItem[menuitemstr.length];  
                for (int i=0; i<popmenuitem.length; i++)  
                {  
                    popmenuitem[i] = new JMenuItem(menuitemstr[i]); //菜单项  
                    popupmenu.add(popmenuitem[i]);   /////快捷菜单项加入弹出菜单  
                    //  
                }  
                popmenuitem[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));//设置快捷键Ctrl+X  
                popmenuitem[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));//设置快捷键Ctrl+C  
                popmenuitem[2].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));//设置快捷键Ctrl+V  
                text.add(popupmenu);      //弹出菜单加到文本域中  
                text.addMouseListener(this);  
            }  
              
            public static void main(String[] args) {  
                new EditorJFrame2();  
          
            }  
          
            @Override  
            public void mouseClicked(MouseEvent e) {  
                if (e.getModifiers()==MouseEvent.BUTTON3_MASK)   //单击的是鼠标右键  
                    popupmenu.show(text,e.getX(),e.getY());    //在鼠标单击处显示快捷菜单  
                  
            }  
          
            @Override  
            public void mouseEntered(MouseEvent e) {  
                // TODO Auto-generated method stub  
                  
            }  
          
            @Override  
            public void mouseExited(MouseEvent e) {  
                // TODO Auto-generated method stub  
                  
            }  
          
            @Override  
            public void mousePressed(MouseEvent e) {  
                // TODO Auto-generated method stub  
                  
            }  
          
            @Override  
            public void mouseReleased(MouseEvent e) {  
                // TODO Auto-generated method stub  
                  
            }  
              
              
        }  
