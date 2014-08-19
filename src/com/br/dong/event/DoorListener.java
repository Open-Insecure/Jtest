package com.br.dong.event;
/** 
 * @author  hexd
 * 创建时间：2014-8-19 下午2:39:19 
 * 类说明 
 * 定义新的事件监听接口，该接口继承自EventListener；该接口包含对doorEvent事件的处理程序
 */
import java.util.EventListener;  
public interface DoorListener extends EventListener {  
    public void doorEvent(DoorEvent event);  
}
