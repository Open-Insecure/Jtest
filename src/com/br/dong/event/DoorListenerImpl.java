package com.br.dong.event;
/** 
 * @author  hexd
 * 创建时间：2014-8-19 下午2:39:41 
 * 类说明 
 */
public class DoorListenerImpl implements DoorListener{
    public void doorEvent(DoorEvent event) {  
        if(event.getDoorState()!=null&&event.getDoorState().equals("open"))  
        {  
             System.out.println("门1打开");  
        }  
        else  
        {  
              System.out.println("门1关闭");  
        }  
    }  
}
