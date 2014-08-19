package com.br.dong.event;
/** 
 * @author  hexd
 * 创建时间：2014-8-19 下午2:41:31 
 * 类说明 
 */
public class DoorMain {
 public static void main(String[] args) {
	   {  
	        DoorManager manager = new DoorManager();  
	        manager.addDoorListener(new DoorListenerImpl());//给门1增加监听器  
	        manager.addDoorListener(new DoorListenerImpl2());//给门2增加监听器  
	        //开门  
	        manager.fireWorkspaceOpened();  
	        System.out.println("我已经进来了");  
	        //关门  
	        manager.fireWorkspaceClosed();  
	    } 
}
}
