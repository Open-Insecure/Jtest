package com.br.dong.event;
/** 
 * @author  hexd
 * 创建时间：2014-8-19 下午2:40:35 
 * 类说明 
 */
public class DoorListenerImpl2 implements DoorListener{
	public void doorEvent(DoorEvent event) {  
        if(event.getDoorState()!=null&&event.getDoorState().equals("open"))  
        {  
             System.out.println("门2打开，同时打开走廊的灯");  
        }  
        else  
        {  
              System.out.println("门2关闭，同时关闭走廊的灯");  
        }  
    }  
}
