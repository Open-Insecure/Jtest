package com.br.dong.swing.thread;

import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** 
 * @author  hexd
 * 创建时间：2014-6-3 下午5:42:10 
 * 类说明 
 */
public class Robticket {
	 //线程停止标记
    private  boolean stoped = false;
    //验证码
    private String code = null;
    //随机数
    Random random = new Random();
    //记录日志对象
    private WriteLog log;
     
    public Robticket(){
        log = WriteLog.getInstance();
    }
     
    public synchronized  void setCode(String code){
        this.code = code;
        notify();
    }
    public synchronized String getCode(){
        try {
            if(code == null){
                wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }
     
    public void run() {
        while(!stoped){
            String threadName = Thread.currentThread().getName();
            //产生一个随机数
            int x = random.nextInt(20);
             
            log.setNewRecord(threadName+" 正在运行..."+ x);
             
            //当随机数为15时，打开验证码窗口等待输入4位验证码
            if(x == 15){
                log.setNewRecord("-----"+threadName+" 需要验证码-----");
                 
                new VerifyCodeDialog(threadName);
                String code = getCode();
                 
                log.setNewRecord(threadName+" 验证码为："+code);
            }
            //当随机数为11时，请求获得数据，线程终止运行
            if(x == 11){
                stoped = true;
                log.setNewRecord("-----"+threadName+" 已获得数据,程序结束-----"+x);
                break;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
     
    //验证码窗口
    class VerifyCodeDialog extends WdListener implements TextListener{
        JDialog dialog = new JDialog();
        TextField codeTxt = new TextField(10);
         
        public VerifyCodeDialog(String theadName){
             
            codeTxt.addTextListener((TextListener) this);
             
            JPanel p = new JPanel();
            p.add(new JLabel("验证码："));
            p.add(codeTxt);
             
            dialog.setTitle(theadName);
            dialog.addWindowListener(this);
            dialog.add(p);
            dialog.setLocation(400, 300);
            dialog.setSize(new Dimension(200, 100));
            dialog.setModal(true);//设置父窗口不可编辑
            dialog.setVisible(true);
        }
 
        @Override
        public void windowClosing(WindowEvent e) {
            log.setNewRecord("验证码窗口关闭，运行停止...");
            stoped = true;
            code = null;
        }
 
        @Override
        public void textValueChanged(TextEvent e) {
            if(e.getSource() == codeTxt){
                String code = codeTxt.getText();
                //当输入验证码长度为4时,调用setCode设置验证码，关门对话框
                if(code.length()==4){
                    setCode(code);
                    dialog.dispose();
                }
            }
        }
    }
}
