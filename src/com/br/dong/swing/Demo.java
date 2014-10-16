package com.br.dong.swing;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;
public class Demo extends JFrame{

    private JComboBox yearBox;
    private JComboBox monthBox;
    private JComboBox dayBox;
    private int[] days=new int[]{31,28,31,30,31,30,31,31,30,31,30,31};

    public Demo(String title){
        super(title);
        setSize(400,100);
        setLocation(100,100);
        setLayout(new FlowLayout());
        yearBox=new JComboBox();
        monthBox=new JComboBox();
        dayBox=new JComboBox();
        for(int i=1980;i<2020;i++){
            yearBox.addItem(new String(""+i));
        }
        for(int i=1;i<=12;i++){
            monthBox.addItem(new String(""+i));
        }
        addDays();
        yearBox.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                addDays();
            }
        });
        monthBox.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                addDays();
            }
        });

        getContentPane().add(yearBox);
        getContentPane().add(new JLabel("年"));
        getContentPane().add(monthBox);
        getContentPane().add(new JLabel("月"));
        getContentPane().add(dayBox);
        getContentPane().add(new JLabel("日"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addDays(){
        int year=Integer.parseInt((String)yearBox.getSelectedItem());
        int month=Integer.parseInt((String)monthBox.getSelectedItem())-1;
        if(year%4==0 && year%100!=0 || year%400==0){
            days[1]=29;
        }else{
            days[1]=28;
        }
        dayBox.removeAllItems();
        for(int i=0;i<days[month];i++){
            dayBox.addItem(new String(""+(i+1)));
        }
    }
    public static void main(String[] args) {
        new Demo("请选择日期").setVisible(true);
    }
}
