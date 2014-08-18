package com.br.dong.swing.thread;

import javax.swing.JTextArea;

/** 
 * @author  hexd
 * 创建时间：2014-6-3 下午5:42:35 
 * 类说明 
 */
public class WriteLog implements Runnable{
    
    //记录日志的文本域
    JTextArea area;
     
    //一条记录
    String newRecord = "";
     
    private static WriteLog log;
     
    //单例模式返回日志对象
    public static WriteLog getInstance(){
        if(log == null){
            log = new WriteLog();
        }
        return log;
    }
     
    //获得新记录 无值则等待
    public synchronized String getNewRecord(){
        try {
            if(newRecord == ""){
                wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = newRecord;
        newRecord = "";
         
        notify();
         
        return result;
    }
    //设置新记录 有值则等待
    public synchronized void setNewRecord(String record){
        try {
            if(newRecord != ""){
                wait();
            }else{
                this.newRecord = record;
                notify();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    @Override
    public void run() {
        while(true){
        	
//            String newRecord = getNewRecord();
        	System.out.println("aaa");
            //日志框中打印日志
//            area = Login.area;
//            area.append(" "+newRecord+"\r\n");
//            area.setCaretPosition(area.getText().length());
        	area = MyJframe.area;
        	area.append(" 擦擦擦"+ "\r\n");
        	area.setCaretPosition(area.getText().length());
        }
    }
     
}
