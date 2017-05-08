package com.br.dong.swing.layout;
import java.awt.*;  
import javax.swing.*;  
import javax.swing.border.*; 
/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.swing.layout
 * AUTHOR: hexOr
 * DATE :2017/4/19 16:50
 * DESCRIPTION:
 */
/**3G版本的数据过滤面板，输入过滤值查询3G值.  
   *  
   *  @author  snail  
   *  @date  07-03-26  
   *  @version  1.0  
   */    
public  class  Fliter3GPanel  extends  JPanel  {    
              private  static  Font  defaultFont  =  new  Font("SimSun",  Font.PLAIN,  12);  //默认字体    
              TitledBorder  titledBorder1  =  new  TitledBorder("参数设置");    
              JTextField  xTextField;    
              JTextField  yTextField;    
              JTextField  zTextField;    
              JTextField  inteTextField;    
              JProgressBar  fliterLoadBar;
              JButton  okButton;    
              JButton  cancelButton;    
            
              private  void  jbInit()  throws  Exception  {    
                    Box  b  =  Box.createVerticalBox();    
                    JLabel  bannerLabel  =  new  JLabel("3G数据过滤查询");    
                    b.add(bannerLabel);    
            
                    //-------------setTheInputPanel------------------------------    
            
                    JPanel  inputPanel  =  new  JPanel();    
                    TitledBorder  inputPanelBorder  =  new  TitledBorder("设置参数");    
                    inputPanelBorder.setTitleFont(defaultFont);    
                    inputPanel.setBorder(inputPanelBorder);    
            
                    Box  vtemp  =  Box.createVerticalBox();    
                    Box  htemp1  =  Box.createHorizontalBox();    
                    Box  htemp2  =  Box.createHorizontalBox();    
                    Box  htemp3  =  Box.createHorizontalBox();    
                    Box  htemp4  =  Box.createHorizontalBox();    
                    Box  htemp5  =  Box.createHorizontalBox();    
                    Box  htemp6  =  Box.createHorizontalBox();    
                    Box  htemp7  =  Box.createHorizontalBox();    
                    Box  htemp8  =  Box.createHorizontalBox();    
            
                    xTextField  =  new  JTextField();    
                    xTextField.setPreferredSize(new  Dimension(50,  10));    
                    htemp1.add(new  JLabel("横向:"));    
                    htemp1.add(Box.createHorizontalStrut(10));          //创建label和textFied之间的距离    
                    htemp1.add(xTextField);    
                    htemp1.add(new  JLabel("(例:3.01)"));    
            
                    yTextField  =  new  JTextField();    
                    yTextField.setPreferredSize(new  Dimension(50,  10));    
                    htemp2.add(new  JLabel("纵向:"));                              
                    htemp2.add(Box.createHorizontalStrut(10));    
                    htemp2.add(yTextField);    
                    htemp2.add(new  JLabel("(例:4.01)"));    
            
                    zTextField  =  new  JTextField();    
                    zTextField.setPreferredSize(new  Dimension(50,  10));    
                    htemp3.add(new  JLabel("垂直:"));    
                    htemp3.add(Box.createHorizontalStrut(10));    
                    htemp3.add(zTextField);    
                    htemp3.add(new  JLabel("(例:3.01)"));    
            
                    inteTextField  =  new  JTextField();    
                    inteTextField.setPreferredSize(new  Dimension(50,  10));    
                    htemp4.add(new  JLabel("综合:"));    
                    htemp4.add(Box.createHorizontalStrut(10));    
                    htemp4.add(inteTextField);    
                    htemp4.add(new  JLabel("(例:5.01)"));    
            

                    fliterLoadBar  =  new  JProgressBar();    
                    JLabel  tmpLabel3  =  new  JLabel("进度:");    
                    htemp7.add(tmpLabel3);    
                    htemp7.add(fliterLoadBar);    
            
                    okButton  =  new  JButton("过滤");    
                    cancelButton  =  new  JButton("撤销");    
                    htemp8.add(okButton);    
                    htemp8.add(Box.createHorizontalStrut(10));    
                    htemp8.add(cancelButton);    
            
                    vtemp.add(htemp1);    
                    vtemp.add(Box.createVerticalStrut(10));                                    //创建上下空间距离    
                    vtemp.add(htemp2);    
                    vtemp.add(Box.createVerticalStrut(10));    
                    vtemp.add(htemp3);    
                    vtemp.add(Box.createVerticalStrut(10));    
                    vtemp.add(htemp4);    
                    vtemp.add(Box.createVerticalStrut(10));    
                    vtemp.add(htemp5);    
                    vtemp.add(Box.createVerticalStrut(10));    
                    vtemp.add(htemp6);    
                    vtemp.add(Box.createVerticalStrut(25));    
                    vtemp.add(htemp7);    
                    vtemp.add(Box.createVerticalStrut(5));    
                    vtemp.add(htemp8);    
            
                    inputPanel.add(vtemp);    
                    //----------------------------------------------------------    
            
                    //------------other  panel  init  here-------------------------    
            
                    //initCode!    
            
                    //----------------------------------------------------------    
            
            
                    b.add(inputPanel);    
                    //b.add(otherPanel);    
            
                    this.add(b,  BorderLayout.NORTH);    
              }    
            
            
              public  Fliter3GPanel()  {    
                    try  {    
                          jbInit();    
                    }    
                    catch  (Exception  ex)  {    
                          ex.printStackTrace();    
                    }    
              }    
            
        }   
